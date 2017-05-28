package org.ankur.survey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.ankur.survey.entity.User;
import org.ankur.survey.pojo.RoleMasterPojo;
import org.ankur.survey.utils.Constants;

@Named(value = "applicationIntializer")
public class ApplicationIntializer {

	@Inject
	@Named(value = "adminServiceImpl")
	private AdminService adminServiceImpl;

	public static Map<String, List<User>> userRolesMap = new HashMap<String, List<User>>();

	public static Map<String, Long> roleNameIdMap = new HashMap<String, Long>();

	public static List<RoleMasterPojo> roleMasterPojoList;

	@PostConstruct
	public void loadDate() {
		// Fetch admin users
		List<User> adminUsers = adminServiceImpl
				.loadUsersByRole(Constants.ADMIN_ROLE);
		userRolesMap.put(Constants.ADMIN_ROLE, adminUsers);

		// Fetch all roles
		roleMasterPojoList = adminServiceImpl.fetchRoles();

		for (RoleMasterPojo roleMasterPojo : roleMasterPojoList) {
			roleNameIdMap.put(roleMasterPojo.getRoleName(),
					roleMasterPojo.getId());
		}

	}
}
