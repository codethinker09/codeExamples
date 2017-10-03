package com.jerseyClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@Path("/validate")
public class RestClient {

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Response getValidResp(@PathParam("id") Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:9090/fetch/" + id);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.readEntity(String.class);

		Map<String, String> mapData = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapData = mapper.readValue(output, HashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean validXml = validateXMLSchema("/Employee.xsd", mapData.get("xml"));

		System.out.println("===============================================================================");
		System.out.println("\n");
		System.out.println("XML Received is : " + mapData.get("xml"));
		System.out.println("Xml is Valid ?" + validXml);
		System.out.println("\n");
		System.out.println("===============================================================================");

		return Response.status(200).entity(mapData.get("xml")).build();

	}

	private boolean validateXMLSchema(String xsdPath, String xml) {

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			schema = factory.newSchema(new File(classLoader.getResource(xsdPath).getFile()));
			Validator validator = schema.newValidator();
			InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));
			validator.validate(new StreamSource(stream));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

}