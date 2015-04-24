package com.bostonangelclub.interceptor;

import com.bostonangelclub.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/23.
 */
public class AdminInterceptor implements Interceptor {
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        User user = controller.getSessionAttr("user");
        Long roleId = controller.getSessionAttr("roleId");
        if (user!= null && roleId != null && roleId == 1) {
            ai.invoke();
        }else{
            controller.setSessionAttr("msg", "You do not have permission ");
            controller.redirect("/login");

        }

    }
}