package com.boot.flyway.flywaydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.flyway.flywaydemo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
