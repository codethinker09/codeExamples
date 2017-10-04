package com.jerseyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.xml.sax.SAXException;

@Path("/validate")
public class RestClient {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getValidResp() {

		// Intializers
		List<Long> ids = parseIdFile();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("/invalid_xml.txt").getFile());
		FileWriter fw = null;
		BufferedWriter bw = null;

		Map<String, String> mapData = null;

		try {
			for (Long id : ids) {

				mapData = new HashMap<String, String>();

				fw = new FileWriter(file, true);
				bw = new BufferedWriter(fw);

				Client client = ClientBuilder.newClient();
				WebTarget webTarget = client.target(Intializer.getPropertyValue(Constants.SERVICE_URL) + id);

				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				Response response = invocationBuilder.get();

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}

				String output = response.readEntity(String.class);

				ObjectMapper mapper = new ObjectMapper();
				mapData = mapper.readValue(output, HashMap.class);
				
				String responseXML = mapData.get("xml");

				// parse XML here to fetch xsd name

				
				boolean validXml = validateXMLSchema(
						Intializer.getPropertyValue(Helper.getTagValue(responseXML, Constants.XSD_NAME_PATH_TAG)),
						responseXML);

				System.out.println("===============================================================================");
				System.out.println("\n");
				System.out.println("XML Received is : " + responseXML);
				System.out.println("Xml is Valid ?" + validXml);
				System.out.println("\n");
				System.out.println("===============================================================================");
				writeToFile(bw, mapData, "");
			}
		} catch (Exception e) {
			writeToFile(bw, mapData, e.getMessage());
			e.printStackTrace();
		}

		return Response.status(200).entity("Processing Done....").build();
	}

	private void writeToFile(BufferedWriter bw, Map<String, String> mapData, String errorMessage) {
		// write to file
		try {
			bw.write(mapData.get("name"));
			bw.newLine();
			bw.write("=====================================================================================");
			bw.newLine();
			bw.write(mapData.get("xml"));
			bw.newLine();
			bw.write("=====================================================================================");
			if (errorMessage != "") {
				bw.newLine();
				bw.write("Error Received ===>>>");
				bw.write("=====================================================================================");
				bw.write("errorMessage");
				bw.write("=====================================================================================");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean validateXMLSchema(String xsdPath, String xml) throws SAXException, IOException {

		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;

		URL schemaFile = new URL(xsdPath);
		schema = factory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));
		validator.validate(new StreamSource(stream));
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
			List<String> tempIds = Arrays.asList(commaSeparatedIds.split("\\s*,\\s*"));

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