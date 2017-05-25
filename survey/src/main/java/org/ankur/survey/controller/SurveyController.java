package org.ankur.survey.controller;

import org.ankur.survey.pojo.SurveyPojo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST)
	public String addSurvey(@RequestBody SurveyPojo surveyPojo) {
		return surveyPojo.toString();
	}
}
