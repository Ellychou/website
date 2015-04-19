package com.bostonangelclub.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;


/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/15.
 */
public class Industry extends Model<Industry> {
    public static final Industry dao = new Industry();

    public void getIndustry(int id) {
        dao.findById(id);
    }

    public List<Industry> getList() {
        return dao.find("select * from industry order by id asc");
    }
    public Page<Industry> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "select *", "from industry order by id asc");
    }

}
