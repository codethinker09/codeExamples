package com.clientstandalone;

import javax.ws.rs.core.Response;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		RestClient restClient = new RestClient();
		Response response = restClient.getValidResp();
		System.out.println(response.getEntity());
	}
}
