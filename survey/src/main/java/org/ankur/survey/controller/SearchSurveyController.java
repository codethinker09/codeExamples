package org.ankur.survey.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.pojo.SearchSurveyResponse;
import org.ankur.survey.pojo.SearchSurveyResponseWrapper;
import org.ankur.survey.service.AdminService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchSurveyController {

	@Inject
	@Named(value = "adminServiceImpl")
	private AdminService adminServiceImpl;

	@RequestMapping(value = "/searchSurvey", method = RequestMethod.POST)
	public SearchSurveyResponseWrapper addSurvey(
			@RequestBody SearchSurveyRequest searchSurveyRequest) {

		SearchSurveyResponseWrapper searchSurveyResponseWrapper = new SearchSurveyResponseWrapper();

		List<SearchSurveyResponse> searchSurveyResponses = adminServiceImpl
				.fetchSurveyDataFilterBased(searchSurveyRequest);
		searchSurveyResponseWrapper.setSearchSurveyResponseList(searchSurveyResponses);
		return searchSurveyResponseWrapper;
	}

}
