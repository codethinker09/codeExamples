package com.springangularsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springangularsecurity.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
