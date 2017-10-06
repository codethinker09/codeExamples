package com.clientstandalone;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		getRequest();
	}

	static Client client = Client.create();

	static String getUrl = "http://www.google.com";

	public static void getRequest() {
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		System.out.println("Response from the Server: ");
		System.out.println(result);
	}
}
