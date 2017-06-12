package org.ankur.survey.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.ankur.survey.pojo.Response;
import org.ankur.survey.pojo.UserPojo;
import org.ankur.survey.service.AdminService;
import org.ankur.survey.service.ApplicationIntializer;
import org.ankur.survey.utils.Constants;
import org.springframework.util.StringUtils;
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
	public List<Response> addSurvey(HttpServletRequest request) {

		List<Response> respList = new ArrayList<Response>();

		try {
			String kerberosUser = request.getRemoteUser() == null ? null
					: request.getRemoteUser();

			kerberosUser = getUsername(kerberosUser);

			UserPojo userPojo = new UserPojo();
			userPojo.setUserName(kerberosUser);

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

			Response response = new Response("username", kerberosUser);
			respList.add(response);

			if (isSuperAdmin) {
				Response response1 = new Response("message", "Success");
				respList.add(response1);
			} else {
				Response response1 = new Response("message", "Error");
				respList.add(response1);
			}

		} catch (Exception e) {
			Response response1 = new Response("message", "Error");
		}

		return respList;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public Response saveUser(@RequestBody UserPojo userPojo) {

		Response response = null;

		try {

			if (null != applicationIntializer.roleNameIdMap
					.get(Constants.ADMIN_ROLE)) {
				userPojo.setRoleId(applicationIntializer.roleNameIdMap
						.get(Constants.ADMIN_ROLE));
			} else {
				Long id = adminServiceImpl.fetchRoles(Constants.ADMIN_ROLE)
						.getId();
				userPojo.setRoleId(id);
			}

			adminServiceImpl.saveUserDetails(userPojo);
			response = new Response("message", "Success");
		} catch (Exception e) {
			response = new Response("message", "Error");
		}

		return response;
	}

	private static String getUsername(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}

		String[] ss = email.split("@", 2);
		if (ss.length != 2 || StringUtils.isEmpty(ss[0])) {
			return null;
		}

		return ss[0];
	}

}
