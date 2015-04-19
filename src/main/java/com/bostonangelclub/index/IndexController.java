package com.bostonangelclub.index;

import com.bostonangelclub.blog.Blog;
import com.jfinal.core.Controller;

/**
 * IndexController
 */
public class IndexController extends Controller {
	public void index() {
		render("index.html");
	}
	
	/**
	 * 测试在 maven 下的 jstl 输出功能
	 * 1：确保 web.xml 内容的 app-app 标记中的servlet版本号至少为 2.5，内容如下：
	 *     <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
	 * 2：确保 jsp 页面中引用了 jstl 标记库，内容如下：
	 *    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
	 * 3：确保 jetty 下开发时引入了正确的依赖，详情在 pom.xml 中有注释说明
	 */
	public void jstl_test() {
		setAttr("blogList", Blog.me.find("select * from blog"));
		renderJsp("/jstl_test.jsp");
	}
}



