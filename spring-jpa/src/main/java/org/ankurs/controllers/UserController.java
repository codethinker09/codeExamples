package org.ankurs.controllers;

import org.ankurs.dao.UserDaoImpl;
import org.ankurs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	UserDaoImpl userDaoImpl;

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUsers(@RequestParam(value = "userName") String userName ,
			@RequestParam(value = "userDetails") String userDetails) {

		User user = new User();
		user.setUserName(userName);
		user.setUserDetails(userDetails);

		userDaoImpl.saveOrUpdate(user);
		return "success";
	}

	@RequestMapping(value = "/testUser/{id}", method = RequestMethod.GET)
	public String testUser(@PathVariable String id) {
		return id;
	}
}
