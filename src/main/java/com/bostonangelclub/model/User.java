package com.bostonangelclub.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/19.
 */
public class User extends Model<User> {
    public static final User dao = new User();

    public Page<User> paginate(int pageNumber, int pageSize) {

        return paginate(pageNumber, pageSize, "select *", "from user order by id asc");
    }
}
