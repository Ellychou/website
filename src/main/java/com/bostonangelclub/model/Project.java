package com.bostonangelclub.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 04/14/2015
 */
public class Project extends Model<Project> {
    public static final Project dao = new Project();

    /**
     * Create new project
     */
    public List<Project> getProjectByIndustry(int industryId) {
       return dao.find("select * from project p join project_industry i on p.id=i.project_id where i.industry_id=?",industryId);
    }
    public String getFileName(int projectId) {
        Project project = dao.findFirst("select * from project where id=?", projectId);
        if (project != null) {
            return project.getStr("file");
        }
        return null;
    }

    public Page<Project> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "select *", "from project order by id asc");
    }


}
