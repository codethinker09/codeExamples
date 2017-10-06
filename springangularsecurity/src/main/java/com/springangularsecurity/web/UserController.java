package com.springangularsecurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springangularsecurity.model.User;
import com.springangularsecurity.service.SecurityService;
import com.springangularsecurity.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public boolean registration(UserDto userDto) {
		try {
			userService.save(userDto);
			securityService.autologin(userDto.getUsername(), userDto.getPassword());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public boolean login(UserDto userDto) {
		User user = userService.findByUsername(userDto.getUsername());
		if (user != null) {
			return true;
		}
		return false;
	}

}
