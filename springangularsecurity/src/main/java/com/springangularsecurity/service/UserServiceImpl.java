package com.springangularsecurity.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springangularsecurity.model.Role;
import com.springangularsecurity.model.User;
import com.springangularsecurity.repository.RoleRepository;
import com.springangularsecurity.repository.UserRepository;
import com.springangularsecurity.web.UserDto;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(UserDto userDto) {
		User user = new User();
		user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setRoles(new HashSet<Role>(roleRepository.findAll()));
		user.setUsername(userDto.getUsername());
		userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
