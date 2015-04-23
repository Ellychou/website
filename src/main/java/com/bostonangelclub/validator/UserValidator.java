package com.bostonangelclub.validator;

import com.bostonangelclub.blog.Blog;
import com.bostonangelclub.model.User;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class UserValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateEmail("user.email", "emailMsg", "Email format Error");
		validateRequiredString("user.password", "passwordMsg", "Password cannot be null");
		//validateRegex("user.name", "[a-zA-Z0-9_]{2,8}", "usernameMsg", "The length of user name should be 2~8, and it can conly contain numbers, alphabets, and _.");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);

		String actionKey = getActionKey();
		if (actionKey.equals("/user/save"))
			controller.render("addInvestor.html");
		else if (actionKey.equals("/user/updateUsername"))
			controller.render("update.html");
		else if (actionKey.equals("/user/updatePassword"))
			controller.render("update.html");
		else if (actionKey.equals("/user/logincheck"))
			controller.render("login.html");
	}
}
