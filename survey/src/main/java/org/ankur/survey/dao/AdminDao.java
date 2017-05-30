package org.ankur.survey.dao;

import java.util.List;

import org.ankur.survey.entity.RoleMaster;
import org.ankur.survey.entity.SurveyData;
import org.ankur.survey.entity.User;
import org.ankur.survey.pojo.SearchSurveyRequest;

public interface AdminDao {

	public List<User> loadUsersByRole(String roleName);

	public void saveSurveyDetails(SurveyData surveyData);

	public void saveUserDetails(User user);

	public List<RoleMaster> fetchRoles();

	public boolean validateSuperAdmin(String userName, Long roleId);

	public List<SurveyData> fetchSurveyDataFilterBased(SearchSurveyRequest searchSurveyRequest);

	public RoleMaster fetchRoles(String roleName);

	public int fetchSurveyDataCount(SearchSurveyRequest searchSurveyRequest);

	public List<SurveyData> fetchSurveyDataFilterBasedPaginated(SearchSurveyRequest searchSurveyRequest);
}
