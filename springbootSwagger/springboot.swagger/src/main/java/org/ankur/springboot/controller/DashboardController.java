package org.ankur.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "dc", description = "Operations with Dashboard")
@RestController
@RequestMapping(value = "/dc")
public class DashboardController {

	@ApiOperation(value = "View a list of available products", response = String.class)
	@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public String sayHello() {
		return "Swagger Hello World";
	}

}