package com.bostonangelclub.controller;

import com.bostonangelclub.model.Industry;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/15.
 */
public class IndustryController extends Controller {
    public void index() {
        setAttr("industryList", Industry.dao.getList());
        render("industry.html");
    }
    public void save() {
        getModel(Industry.class).save();
        redirect("/industry");
    }
    public void edit() {
        setAttr("Industry", Industry.dao.findById(getParaToInt()));
    }
    public void update() {
        getModel(Industry.class).update();
        redirect("/industry");
    }

    public void delete() {
        Industry.dao.deleteById(getParaToInt());
        redirect("/industry");
    }


}
