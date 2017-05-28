package org.ankur.survey.service;

import java.util.List;

import org.ankur.survey.entity.User;
import org.ankur.survey.pojo.RoleMasterPojo;
import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.pojo.SearchSurveyResponse;
import org.ankur.survey.pojo.SurveyPojo;
import org.ankur.survey.pojo.UserPojo;

public interface AdminService {

	public List<User> loadUsersByRole(String roleName);

	public void saveSurveyDetails(SurveyPojo surveyPojo);

	public void saveUserDetails(UserPojo userPojo);

	public List<RoleMasterPojo> fetchRoles();

	public boolean validateSuperAdmin(UserPojo userPojo);

	public List<SearchSurveyResponse> fetchSurveyDataFilterBased(
			SearchSurveyRequest searchSurveyRequest);
	
	public RoleMasterPojo fetchRoles(String roleName);
}
