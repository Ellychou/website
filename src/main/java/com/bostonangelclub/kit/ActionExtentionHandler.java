package com.bostonangelclub.kit;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shan Zhou
 * @version 1.0.1
 * @since 2015/4/18.
 */
public class ActionExtentionHandler extends Handler {

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.endsWith(".pdf"))
            target = target.substring(0, target.length() - 4);
        nextHandler.handle(target, request, response, isHandled);
    }
}