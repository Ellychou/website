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
public class AuthInterceptor implements Interceptor {
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        User user = controller.getSessionAttr("user");
        if (user != null) {
            ai.invoke();
        }else{
            //controller.setAttr("msg","Please login fist!");
            controller.redirect("user/login");
        }

    }
}
