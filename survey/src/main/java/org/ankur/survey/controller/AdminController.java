package org.ankur.survey.controller;

import org.ankur.survey.pojo.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@RequestMapping(value = "/validateSuperAdmin", method = RequestMethod.POST)
	public Response addSurvey(@RequestBody String superAdmin) {

		Response response = null;

		try {
			if (superAdmin.equalsIgnoreCase("ankur")) {
				response = new Response("message", "Success");
			} else {
				response = new Response("message", "Error");
			}

		} catch (Exception e) {
			response = new Response("message", "Error");
		}

		return response;
	}
}
