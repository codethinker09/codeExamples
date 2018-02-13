package com.boot.flyway.flywaydemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.flyway.flywaydemo.entity.Customer;
import com.boot.flyway.flywaydemo.repository.CustomerRepository;

@RestController

public class CustomerController {

    @Autowired

    private CustomerRepository customerRepository;

    @RequestMapping(path = "/customers/", method = RequestMethod.GET)

    public List<Customer> getCustomers() {

        return customerRepository.findAll();

    }

}