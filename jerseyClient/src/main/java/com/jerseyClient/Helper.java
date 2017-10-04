package com.jerseyClient;

public class Helper {

	public static String getTagValue(String xml, String tagName) {
		return xml.split("<" + tagName + ">")[1].split("</" + tagName + ">")[0];
	}

}
