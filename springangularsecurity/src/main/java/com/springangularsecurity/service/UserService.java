package com.springangularsecurity.service;

import com.springangularsecurity.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
