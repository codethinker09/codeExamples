package com.springloginsecurity.service;

import com.springloginsecurity.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
