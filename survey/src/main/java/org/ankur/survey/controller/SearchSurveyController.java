package org.ankur.survey.controller;

import java.util.ArrayList;
import java.util.List;

import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.pojo.SearchSurveyResponse;
import org.ankur.survey.pojo.SearchSurveyResponseWrapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchSurveyController {

	@RequestMapping(value = "/searchSurvey", method = RequestMethod.POST)
	public SearchSurveyResponseWrapper addSurvey(@RequestBody SearchSurveyRequest searchSurveyRequest) {

		SearchSurveyResponseWrapper searchSurveyResponseWrapper = new SearchSurveyResponseWrapper();

		List<SearchSurveyResponse> searchSurveyResponses = new ArrayList<SearchSurveyResponse>();

		SearchSurveyResponse searchSurveyResponse1 = new SearchSurveyResponse("ankur1", "issueType1", "1", "3",
				"feedback1", "optional1");
		SearchSurveyResponse searchSurveyResponse2 = new SearchSurveyResponse("ankur2", "issueType2", "3", "1",
				"feedback2", "optional2");
		SearchSurveyResponse searchSurveyResponse3 = new SearchSurveyResponse("ankur3", "issueType3", "4", "1",
				"feedback3", "optional3");
		SearchSurveyResponse searchSurveyResponse4 = new SearchSurveyResponse("ankur4", "issueType2", "2", "2",
				"feedback4", "optional4");

		searchSurveyResponses.add(searchSurveyResponse1);
		searchSurveyResponses.add(searchSurveyResponse2);
		searchSurveyResponses.add(searchSurveyResponse3);
		searchSurveyResponses.add(searchSurveyResponse4);

		searchSurveyResponseWrapper.setSearchSurveyResponseList(searchSurveyResponses);
		return searchSurveyResponseWrapper;
	}

}
