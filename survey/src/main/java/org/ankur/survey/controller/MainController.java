package org.ankur.survey.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping("/home")
	public String homepage() {
		return "homepage";
	}
}
