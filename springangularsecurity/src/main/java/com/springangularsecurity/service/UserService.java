package com.springangularsecurity.service;

import com.springangularsecurity.model.User;
import com.springangularsecurity.web.UserDto;

public interface UserService {
	void save(UserDto userDto);

	User findByUsername(String username);
}
