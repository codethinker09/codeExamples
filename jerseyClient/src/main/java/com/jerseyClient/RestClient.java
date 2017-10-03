package com.jerseyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

import org.codehaus.jackson.map.ObjectMapper;

@Path("/validate")
public class RestClient {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getValidResp() {

		List<Long> ids = parseIdFile();

		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource("/invalid_xml.txt")
				.getFile());

		try {
			for (Long id : ids) {

				FileWriter fw = new FileWriter(file,true);
				BufferedWriter bw = new BufferedWriter(fw);

				Client client = ClientBuilder.newClient();
				WebTarget webTarget = client
						.target("http://localhost:9090/fetch/" + id);

				Invocation.Builder invocationBuilder = webTarget
						.request(MediaType.APPLICATION_JSON);
				Response response = invocationBuilder.get();

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = response.readEntity(String.class);

				Map<String, String> mapData = null;
				ObjectMapper mapper = new ObjectMapper();

				mapData = mapper.readValue(output, HashMap.class);
				boolean validXml = validateXMLSchema(
						"https://raw.githubusercontent.com/codethinker09/codeExamples/master/jerseyClient/src/main/resources/Employee.xsd",
						mapData.get("xml"));

				System.out
						.println("===============================================================================");
				System.out.println("\n");
				System.out.println("XML Received is : " + mapData.get("xml"));
				System.out.println("Xml is Valid ?" + validXml);
				System.out.println("\n");
				System.out
						.println("===============================================================================");

				if (!validXml) {
					// write to file
					bw.write(mapData.get("name"));
					bw.newLine();
					bw.write("=====================================================================================");
					bw.newLine();
					bw.write(mapData.get("xml"));
					bw.newLine();
					bw.write("=====================================================================================");
					bw.close();
				}else{
					bw.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity("Processing Done....").build();
	}

	private boolean validateXMLSchema(String xsdPath, String xml) {

		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;

		try {
			URL schemaFile = new URL(xsdPath);
			HttpsURLConnection schemaConn = (HttpsURLConnection) schemaFile
					.openConnection();
			// ClassLoader classLoader = getClass().getClassLoader();
			schema = factory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			InputStream stream = new ByteArrayInputStream(
					xml.getBytes(StandardCharsets.UTF_8.name()));
			validator.validate(new StreamSource(stream));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private List<Long> parseIdFile() {
		InputStream is;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("/id.txt").getFile());

			is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line);
				line = buf.readLine();
			}

			String commaSeparatedIds = sb.toString();
			List<String> tempIds = Arrays.asList(commaSeparatedIds
					.split("\\s*,\\s*"));

			List<Long> ids = new ArrayList<Long>();

			for (String number : tempIds) {
				ids.add(Long.parseLong(number));
			}

			return ids;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}