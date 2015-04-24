package com.bostonangelclub.controller;

import com.bostonangelclub.interceptor.AdminInterceptor;
import com.bostonangelclub.interceptor.AuthInterceptor;
import com.bostonangelclub.kit.SendMailKit;
import com.bostonangelclub.model.Industry;
import com.bostonangelclub.model.User;
import com.bostonangelclub.validator.UserValidator;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/19.
 */

public class UserController extends Controller {
    @Before(AuthInterceptor.class)
    public void index() {
        setAttr("userPage", User.dao.paginate(getParaToInt(0, 1), 20));
        render("user.html");

    }

    @Before(AdminInterceptor.class)
    public void addInvestor() {

    }

    @Before({AdminInterceptor.class,UserValidator.class})
    public void save() {
        String email = getPara("user.email");
        User userexist = User.dao.findFirst("select email from user where email = ?",email);
        if (userexist != null) {
            response("An account with this email has already been created, please create account with another email", "addInvestor.html");
            return;
        }
        String randomPass = RandomStringUtils.randomAlphanumeric(12);
        User user =  getModel(User.class);
        user.set("password",randomPass).save();
       // sendPassword(email, randomPass);
        Long id = user.getLong("id");
        setAttr("user_id", id);
        setAttr("industryList", Industry.dao.getList());
        render("setUserIndustry.html");
    }

    @Before(AdminInterceptor.class)
    public void setUserIndustry() {
        setAttr("industryList", Industry.dao.getList());
    }
    @Before(AdminInterceptor.class)
    public void setIndustry() {
        Integer[] industryIds = getParaValuesToInt("user_industry.industry_id");
        Integer userId = getParaToInt("user_id");
        for (Integer id : industryIds) {
            Record userIndustry = new Record();
            userIndustry.set("user_id", userId).set("industry_id", id);
            Db.save("user_industry", userIndustry);
        }
        renderText("Set industry successfully");
    }

    public void sendPassword(String email, String password) {
        String content = "Please log in bostonangelclub.com with your eamil" +
                email + " and your password " + password + " to reset your password.";
        SendMailKit.send(email, content);
    }

    @Before(AdminInterceptor.class)
    public void freeze() {
        User user = User.dao.findById(getParaToInt());
        user.set("frozen",1).update();
        setAttr("userPage", User.dao.paginate(getParaToInt(0, 1), 20));
        response("Freeze account successfully!", "user.html");
    }

    @Before(AdminInterceptor.class)
    public void viewIndustry() {
        Long userId = getParaToLong();
        //List<String> industries = new ArrayList<String>();
        //List<Record> userIndustries =  Db.find("select * from user_industry where user_id = ?",userId);
        //for (Record userIndustry : userIndustries) {
         //   Long industryId = userIndustry.getLong("industry_id");
         //   String industryName = Industry.dao.findById(industryId).getStr("industry");
         //   industries.add(industryName);
       // }
        List<Record> industries = Db.find("select i.industry \n" +
                "from industry i join \n" +
                "( select industry_id from user_industry\n" +
                "where user_id = ?) as u on i.id = u.industry_id", userId);
        setAttr("industryList", industries);
    }
    @ActionKey("/login")
    public void login() {
        setAttr("msg", getSessionAttr("msg"));
        removeSessionAttr("msg");
    }

    @Before(UserValidator.class)
    public void logincheck() {
        String email = getPara("user.email");
        String password = getPara("user.password");
        String url = "login.html";

        User user = User.dao.findByEmail(email);
        if (user == null) {
            response("Can not find this email", url);
            return;
        }
        Integer frozen = user.getInt("frozen");
        if (frozen == 1) {
            response("Your account has been frozen, please contact administrator", url);
            return;
        }
        String savedpw = user.getStr("password");
        if (!password.equals(savedpw)) {
            response("Password is incorrect", url);
        }
        setSessionAttr("user",user);
        setSessionAttr("userId",user.get("id"));
        setSessionAttr("roleId",user.get("role_id"));
        setAttr("user",user);
        render("update.html");
    }

    @Before(AuthInterceptor.class)
    public void logout(){
        removeSessionAttr("user");
        removeSessionAttr("userID");
        removeSessionAttr("roleID");
        redirect("/");
    }

    @Before(AuthInterceptor.class)
    public void update() {

    }


    @Before(AuthInterceptor.class)
    public void updateUsername() {
        User user = getModel(User.class);
        user.update();
        response("Update user name successfully","update.html");
        //forwardAction("/user");
    }
  //  @Before(UserValidator.class)
   @Before(AuthInterceptor.class)
    public void updatePassword() {
        if(!getPara("password2").equals(getPara("user.password"))) {
            response("Your two passwords are not the same", "update.html");
            return;
        }
        Long userId = getSessionAttr("userId");
        User user = User.dao.findById(userId);
        user.set("password", getPara("user.password")).update();
        response("Update password successfully","update.html");
       // forwardAction("/user");
    }

    public void response(String msg, String url) {
        setAttr("msg", msg);
        render(url);
    }

    public void forgetPassword() {

    }
    public void setNewPassword() {
        String email = getPara("user.email");
        User user =  User.dao.findByEmail(email);
        if (user == null) {
            response("Can not find this email","forgetPassword.html");
            return;
        }
        String randomPass = RandomStringUtils.randomAlphanumeric(12);
        user.set("password",randomPass).update();

        sendPassword(email, randomPass);
        response("New password has already been sent to your email, please check your email","forgetPassword.html");

    }



}
