package com.springloginsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springloginsecurity.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
