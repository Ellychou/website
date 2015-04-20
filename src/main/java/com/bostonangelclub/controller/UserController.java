package com.bostonangelclub.controller;

import com.bostonangelclub.kit.SendMailKit;
import com.bostonangelclub.model.Industry;
import com.bostonangelclub.model.Project;
import com.bostonangelclub.model.User;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.RandomStringUtils;

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
        sendPassword(email, randomPass);
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
        SendMailKit.send(email,content);
    }

    public void delete() {
        User.dao.deleteById(getParaToInt());
        redirect("/user");
    }


}
