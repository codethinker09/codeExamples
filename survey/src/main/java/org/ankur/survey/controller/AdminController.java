package org.ankur.survey.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.ankur.survey.pojo.Response;
import org.ankur.survey.pojo.UserPojo;
import org.ankur.survey.service.AdminService;
import org.ankur.survey.service.ApplicationIntializer;
import org.ankur.survey.utils.Constants;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@Inject
	@Named(value = "adminServiceImpl")
	private AdminService adminServiceImpl;

	@Inject
	@Named(value = "applicationIntializer")
	private ApplicationIntializer applicationIntializer;

	@RequestMapping(value = "/validateSuperAdmin", method = RequestMethod.POST)
	public Response addSurvey(@RequestBody String superAdmin) {

		Response response = null;

		try {
			UserPojo userPojo = new UserPojo();
			userPojo.setUserName(superAdmin);

			if (null != applicationIntializer.roleNameIdMap
					.get(Constants.ADMIN_ROLE)) {
				userPojo.setRoleId(applicationIntializer.roleNameIdMap
						.get(Constants.ADMIN_ROLE));
			} else {
				Long id = adminServiceImpl.fetchRoles(Constants.ADMIN_ROLE)
						.getId();
				userPojo.setRoleId(id);
			}

			boolean isSuperAdmin = adminServiceImpl
					.validateSuperAdmin(userPojo);
			if (isSuperAdmin) {
				response = new Response("message", "Success");
			} else {
				response = new Response("message", "Error");
			}

		} catch (Exception e) {
			response = new Response("message", "Error");
		}

		return response;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public Response saveUser(@RequestBody UserPojo userPojo) {

		Response response = null;

		try {
			adminServiceImpl.saveUserDetails(userPojo);
			response = new Response("message", "Success");
		} catch (Exception e) {
			response = new Response("message", "Error");
		}

		return response;
	}
}
