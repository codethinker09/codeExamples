package org.ankur.survey.controller;

import org.ankur.survey.pojo.Response;
import org.ankur.survey.pojo.SurveyPojo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST)
	public Response addSurvey(@RequestBody SurveyPojo surveyPojo) {

		Response response = null;

		try {
			response = new Response("message", "Success");
		} catch (Exception e) {
			response = new Response("message", "Error");
		}

		return response;
	}
}
