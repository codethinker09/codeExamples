package org.ankur.survey.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.pojo.SearchSurveyResponse;
import org.ankur.survey.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CSVFileDownloadController {

	@Inject
	@Named(value = "adminServiceImpl")
	private AdminService adminServiceImpl;

	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response,
			@RequestBody SearchSurveyRequest searchSurveyRequest)
			throws IOException {

		response.setContentType("text/csv");
		String reportName = "Service_rating.csv";
		response.setHeader("Content-disposition", "attachment;filename="
				+ reportName);

		List<String> rows = new ArrayList<String>();
		rows.add("Person Name,Service Name,Service Rating,Service Time Rating,Feedback,Optional issue link,Date");
		rows.add("\n");

		List<SearchSurveyResponse> searchSurveyResponses = adminServiceImpl
				.fetchSurveyDataFilterBased(searchSurveyRequest);

		for (SearchSurveyResponse searchSurveyResponse : searchSurveyResponses) {
			rows.add(searchSurveyResponse.getUser() + ","
					+ searchSurveyResponse.getIssueType() + ","
					+ searchSurveyResponse.getServicerating() + ","
					+ searchSurveyResponse.getServicetimetating() + ","
					+ searchSurveyResponse.getFeedback() + ","
					+ searchSurveyResponse.getOptional() + ","
					+ searchSurveyResponse.getCreatedDate());
			rows.add("\n");
		}

		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}

		response.getOutputStream().flush();

	}
}
