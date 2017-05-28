package org.ankur.survey.pojo;

import java.util.List;

public class SearchSurveyResponseWrapper {
	private List<SearchSurveyResponse> searchSurveyResponseList;

	private PieWrapperPojo serviceRating;

	private PieWrapperPojo serviceTimeRating;

	public PieWrapperPojo getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(PieWrapperPojo serviceRating) {
		this.serviceRating = serviceRating;
	}

	public PieWrapperPojo getServiceTimeRating() {
		return serviceTimeRating;
	}

	public void setServiceTimeRating(PieWrapperPojo serviceTimeRating) {
		this.serviceTimeRating = serviceTimeRating;
	}

	public List<SearchSurveyResponse> getSearchSurveyResponseList() {
		return searchSurveyResponseList;
	}

	public void setSearchSurveyResponseList(
			List<SearchSurveyResponse> searchSurveyResponseList) {
		this.searchSurveyResponseList = searchSurveyResponseList;
	}

}
