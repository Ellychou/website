
 package com.bostonangelclub.controller;

import com.bostonangelclub.kit.SendMailKit;
import com.bostonangelclub.model.Industry;
import com.bostonangelclub.model.Project;
import com.google.common.io.Files;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/15.
 */
public class ProjectController extends Controller {
    private static final Logger log = Logger.getLogger(ProjectController.class);
    public void index() {
        setAttr("projectPage", Project.dao.paginate(getParaToInt(0, 1), 10));
        render("project.html");
    }

    public void add() {
    }

    public void save() {
        UploadFile file = getFile();
        if (!checkedPDF(file)) {
            log.info("not pdf");
            renderText("Please resubmit your project in PDF");
            return;
        }
        log.info("is pdf");
        getModel(Project.class).set("project_url",file.getSaveDirectory()).save();
        sendEmail();
        redirect("/project");
    }

    public void edit() {
        setAttr("project", Project.dao.findById(getParaToInt()));
        setAttr("industryList", Industry.dao.getList());
        setAttr("industryName", Industry.dao.findById(getPara("project_industry")));
    }
        public void setIndustry() {
            Integer[] industryIds = getParaValuesToInt("project_industry.industry_id");
            Integer projectId = getParaToInt("project.id");
            for (Integer id : industryIds) {
                Record projectIndustry = new Record();
                projectIndustry.set("project_id",projectId).set("industry_id",id);
                Db.save("project_industry", projectIndustry);
            }
            renderText("Set industry successfully");
        }
        public void update(){
            getModel(Project.class).update();
            redirect("/project");
        }
        public void delete(){
            Project.dao.deleteById(getParaToInt());
            redirect("/project");
        }

        public boolean checkedPDF(UploadFile file) {
            String filename = file.getFileName();
            log.info("filename" + filename);
            String ext = Files.getFileExtension(filename);
            log.info("extention" + ext);
            return ext.equalsIgnoreCase("pdf");
        }

        public void seeProject() {
            setAttr("industryList", Industry.dao.getList());
            render("projectByIndustry.html");
        }
        public void getProjectByIndustry() {
          Integer[] industryIds = getParaValuesToInt("project_industry.industry_id");
          //  List<Project> lists = new ArrayList<Project>();
           // for (Integer id : industryIds) {
            //    List<Project> projects = Project.dao.getProjectByIndustry(id);
            //    lists.addAll(projects);

           // }
           // setAttr("projectList", lists);
            //render("showList.html");
            renderText("yes");

        }

        public void showList(List<Project> lists) {


        }
    public void sendEmail() {
        String toEmail = "shanzhou321@gmail.com";
        String content = "A new project has been saved to the database, please set the industry for it.";
        SendMailKit.send(toEmail,content);


    }




    }




