package com.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
	@RequestMapping("/fetch/{id}")
	public Map<String, String> sample(@PathVariable("id") Long id) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("xml", fetchXML(id));
		result.put("name", id + "_xml");
		result.put("id", id.toString());
		return result;
	}

	private String fetchXML(Long id) {
		String content = "";

		if (id == 1L) {
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><empns:empRequest xmlns:empns=\"Employee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Employee.xsd\"><empns:id>5</empns:id><empns:formid>1</empns:formid></empns:empRequest>";
			content = xml;

		} else if (id == 2L) {
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><empns:empResponse xmlns:empns=\"Employee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"	xsi:schemaLocation=\"Employee.xsd\"><empns:id>1</empns:id><empns:role>Developer</empns:role><empns:fullName>Ankur Singhal</empns:fullName><empns:formid>1</empns:formid></empns:empResponse>";
			content = xml;

		} else if (id == 3L) {
			String xml = "<?xml version=\"1.0\"?><Employee>	<name>Pankaj</name>	<age>29</age>	<role>Java Developer</role>	<gender>Male</gender><empns:formid>1</empns:formid></Employee>";
			content = xml;
		}

		return content;
	}

}
