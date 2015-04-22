package com.bostonangelclub.controller;

import com.bostonangelclub.kit.SendMailKit;
import com.bostonangelclub.model.Industry;
import com.bostonangelclub.model.Project;
import com.bostonangelclub.model.User;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/19.
 */
public class UserController extends Controller {
    public void index() {
        setAttr("userPage", User.dao.paginate(getParaToInt(0, 1), 20));
        render("user.html");

    }

    public void addInvestor() {

    }
    public void save() {
        String randomPass = RandomStringUtils.randomAlphanumeric(12);
        User user =  getModel(User.class);
        user.set("password",randomPass).save();
        String email = getPara("user.email");
        User userexist = User.dao.findFirst("select email from user where email = ?",email);
        if (userexist != null) {
            renderText("An account with this email has aready been created, please create account with another email");
            return;
        }
       // sendPassword(email, randomPass);
        Long id = user.getLong("id");
        setAttr("user_id", id);
        setAttr("industryList", Industry.dao.getList());
        render("setUserIndustry.html");
    }
    public void setUserIndustry() {
        setAttr("industryList", Industry.dao.getList());
    }
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
        String content = "Your Boston Angel Club account has been created, please log in bostonangelclub.com with your eamil" +
                email + " and your password " + password + " to reset your password.";
        SendMailKit.send(email, content);
    }

    public void freeze() {
        User user = User.dao.findById(getParaToInt());
        user.set("frozen",1).update();
        renderText("Freeze account successfully!");
    }

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

    public void login() {

    }

    public void logincheck() {
        String email = getPara("email");
        String password = getPara("password");
        User user = User.dao.findFirst("select * from user where email = ?", email);
        if (user == null) {
            renderText("Can not find this email");
            return;
        }
        Integer frozen = user.getInt("frozen");
        if (frozen == 1) {
            renderText("Your account has been frozen, please contact administrator");
            return;
        }
        String savedpw = user.getStr("password");
        if (password.equals(savedpw)){
            Long userId = user.getLong("id");
            setAttr("user.id",userId);
            redirect("/user/update");
        }else{
            renderText("Password is incorrect");
        }
    }

    public void update() {

    }
    public void updateUsername() {
        Long userId = getParaToLong("userId");
        User user = User.dao.findById(userId);
        user.set("name",getPara("user.name")).update();
    }



}
