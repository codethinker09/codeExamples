package org.ankur.survey.pojo;

public class SearchSurveyRequest {

	private String fromDate;

	private String toDate;

	private String servicerating;

	private String servicetimetating;

	public String getServicerating() {
		return servicerating;
	}

	public void setServicerating(String servicerating) {
		this.servicerating = servicerating;
	}

	public String getServicetimetating() {
		return servicetimetating;
	}

	public void setServicetimetating(String servicetimetating) {
		this.servicetimetating = servicetimetating;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
