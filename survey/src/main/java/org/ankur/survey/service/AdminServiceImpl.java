package org.ankur.survey.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ankur.survey.dao.AdminDao;
import org.ankur.survey.entity.RoleMaster;
import org.ankur.survey.entity.SurveyData;
import org.ankur.survey.entity.User;
import org.ankur.survey.pojo.RoleMasterPojo;
import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.pojo.SearchSurveyResponse;
import org.ankur.survey.pojo.SurveyPojo;
import org.ankur.survey.pojo.UserPojo;
import org.springframework.util.CollectionUtils;

@Named(value = "adminServiceImpl")
public class AdminServiceImpl implements AdminService {

	@Inject
	@Named(value = "adminDaoImpl")
	private AdminDao adminDaoImpl;

	@Override
	public List<User> loadUsersByRole(String roleName) {
		return adminDaoImpl.loadUsersByRole(roleName);
	}

	@Override
	public void saveSurveyDetails(SurveyPojo surveyPojo) {
		SurveyData surveyData = new SurveyData();

		surveyData.setFeedback(surveyPojo.getFeedback());
		surveyData.setIssue(surveyPojo.getIssue());
		surveyData.setOptional(surveyPojo.getOptional());
		surveyData.setServicerating(surveyPojo.getServicerating());
		surveyData.setServicetimetating(surveyPojo.getServicetimetating());
		surveyData.setUsername(surveyPojo.getUsername());

		adminDaoImpl.saveSurveyDetails(surveyData);
	}

	@Override
	public void saveUserDetails(UserPojo userPojo) {
		User user = new User();
		user.setRoleId(userPojo.getRoleId());
		user.setUserName(userPojo.getUserName());

		adminDaoImpl.saveUserDetails(user);

	}

	@Override
	public List<RoleMasterPojo> fetchRoles() {
		List<RoleMaster> roleMasterList = adminDaoImpl.fetchRoles();

		List<RoleMasterPojo> roleMasterPojoList = new ArrayList<RoleMasterPojo>();

		if (!CollectionUtils.isEmpty(roleMasterList)) {
			for (RoleMaster rm : roleMasterList) {

				RoleMasterPojo roleMasterPojo = new RoleMasterPojo();
				roleMasterPojo.setId(rm.getId());
				roleMasterPojo.setRoleName(rm.getRoleName());

				roleMasterPojoList.add(roleMasterPojo);
			}
		}

		return roleMasterPojoList;
	}

	@Override
	public boolean validateSuperAdmin(UserPojo userPojo) {
		return adminDaoImpl.validateSuperAdmin(userPojo.getUserName(),
				userPojo.getRoleId());
	}

	@Override
	public List<SearchSurveyResponse> fetchSurveyDataFilterBased(
			SearchSurveyRequest searchSurveyRequest) {
		List<SearchSurveyResponse> searchSurveyResponses = new ArrayList<SearchSurveyResponse>();

		List<SurveyData> surveyDataList = adminDaoImpl
				.fetchSurveyDataFilterBased(searchSurveyRequest);
		if (!(CollectionUtils.isEmpty(surveyDataList))) {
			for (SurveyData surveyData : surveyDataList) {
				SearchSurveyResponse searchSurveyResponse = new SearchSurveyResponse(
						surveyData.getUsername(), surveyData.getIssue(),
						surveyData.getServicerating(),
						surveyData.getServicetimetating(),
						surveyData.getFeedback(), surveyData.getOptional());

				searchSurveyResponses.add(searchSurveyResponse);
			}
		}
		return searchSurveyResponses;
	}

	@Override
	public RoleMasterPojo fetchRoles(String roleName) {
		RoleMaster roleMaster = adminDaoImpl.fetchRoles(roleName);

		RoleMasterPojo roleMasterPojo = new RoleMasterPojo();
		roleMasterPojo.setId(roleMaster.getId());
		roleMasterPojo.setRoleName(roleMaster.getRoleName());

		return roleMasterPojo;
	}
}
