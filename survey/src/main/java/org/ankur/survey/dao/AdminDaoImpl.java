package org.ankur.survey.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.ankur.survey.entity.RoleMaster;
import org.ankur.survey.entity.SurveyData;
import org.ankur.survey.entity.User;
import org.ankur.survey.pojo.SearchSurveyRequest;
import org.ankur.survey.utils.DateHelper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Named(value = "adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl implements AdminDao {

	@Override
	public List<User> loadUsersByRole(String roleName) {
		return null;
	}

	@Override
	public void saveSurveyDetails(SurveyData surveyData) {
		em.persist(surveyData);
	}

	@Override
	public void saveUserDetails(User user) {
		em.persist(user);

	}

	@Override
	public List<RoleMaster> fetchRoles() {
		Query query = em.createQuery("SELECT rm FROM RoleMaster rm");
		return (List<RoleMaster>) query.getResultList();
	}

	@Override
	public boolean validateSuperAdmin(String userName, Long roleId) {
		Query query = em
				.createQuery("SELECT u FROM User u where u.userName ='" + userName + "'" + " AND u.roleId = roleId");

		List<User> userList = query.getResultList();

		if (!(CollectionUtils.isEmpty(userList)) && userList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<SurveyData> fetchSurveyDataFilterBased(SearchSurveyRequest searchSurveyRequest) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date frmDate = null;
		Date enDate = null;
		try {
			frmDate = format.parse(searchSurveyRequest.getFromDate());
			enDate = format.parse(searchSurveyRequest.getToDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String frmDateTemp = DateHelper.getFormattedFromDateTime(frmDate);
		String endDateTemp = DateHelper.getFormattedToDateTime(enDate);

		String queryStr = "SELECT sd FROM SurveyData sd where sd.created BETWEEN '" + frmDateTemp + "' AND '"
				+ endDateTemp + "'";

		if (!(StringUtils.isEmpty(searchSurveyRequest.getServicerating()))) {
			queryStr = queryStr + " AND sd.servicerating = '" + searchSurveyRequest.getServicerating() + "'";
		}

		if (!(StringUtils.isEmpty(searchSurveyRequest.getServicetimetating()))) {
			queryStr = queryStr + " AND sd.servicetimetating = '" + searchSurveyRequest.getServicetimetating() + "'";
		}
		// order by
		queryStr = queryStr + " order by sd.created desc";

		Query query = em.createQuery(queryStr);

		List<SurveyData> surveyDataList = query.getResultList();
		return surveyDataList;
	}

	@Override
	public RoleMaster fetchRoles(String roleName) {
		Query query = em.createQuery("SELECT rm FROM RoleMaster rm where rm.roleName = '" + roleName + "'");
		return (RoleMaster) query.getSingleResult();
	}
}
