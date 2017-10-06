package com.clientstandalone;

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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

	public Response getValidResp() {

		// Intializers
		List<Long> ids = parseIdFile();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("/invalid_xml.txt")
				.getFile());
		FileWriter fw = null;
		BufferedWriter bw = null;

		Map<String, String> mapData = null;

		try {
			for (Long id : ids) {

				mapData = new HashMap<String, String>();
				fw = new FileWriter(file, true);
				bw = new BufferedWriter(fw);

				bw.newLine();
				bw.newLine();
				bw.write("Input id =>" + id);
				bw.newLine();

				Client client = Client.create();
				WebResource webResource = client.resource(Intializer
						.getPropertyValue(Constants.SERVICE_URL) + id);
				ClientResponse response = webResource
						.accept("application/json").get(ClientResponse.class);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatus());
				}

				String output = response.getEntity(String.class);

				ObjectMapper mapper = new ObjectMapper();
				mapData = mapper.readValue(output, HashMap.class);

				String responseXML = mapData.get("xml");

				bw.write(mapData.get("name"));
				bw.newLine();
				bw.write("=====================================================================================");
				bw.newLine();
				bw.write(responseXML);
				bw.newLine();
				bw.write("=====================================================================================");

				// Basic Validation
				boolean basicValidation = validateXMLSchema(
						Intializer.getPropertyValue(Constants.BASIC_XSD),
						responseXML, bw);
				bw.newLine();
				bw.write("Basic validation = " + basicValidation);

				if (basicValidation) {
					// perform 2nd validation based on formid

					boolean secondValidation = validateXMLSchema(
							Intializer.getPropertyValue(Constants.XSD_MAPPING_INITIAL
									+ Helper.getTagValue(responseXML,
											Constants.XSD_NAME_PATH_TAG)),
							responseXML, bw);
					bw.newLine();
					bw.write("Second validation = " + secondValidation);
				}

				bw.close();

			}
		} catch (Exception e) {
			writeToFile(bw, mapData, e.getMessage());
			e.printStackTrace();
		}

		return Response.status(200).entity("Processing Done....").build();
	}

	private void writeToFile(BufferedWriter bw, Map<String, String> mapData,
			String errorMessage) {
		// write to file
		try {
			bw.newLine();
			bw.newLine();
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
				bw.newLine();
				bw.write("=====================================================================================");
				bw.newLine();
				bw.write(errorMessage);
				bw.newLine();
				bw.write("=====================================================================================");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean validateXMLSchema(String xsdPath, String xml,
			BufferedWriter bw) throws SAXException, IOException {

		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema;

			URL schemaFile = new URL(xsdPath);
			schema = factory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			InputStream stream = new ByteArrayInputStream(
					xml.getBytes(StandardCharsets.UTF_8.name()));
			validator.validate(new StreamSource(stream));
			return true;
		} catch (Exception e) {
			bw.newLine();
			bw.write("Error Received ===>>>");
			bw.newLine();
			bw.write("=====================================================================================");
			bw.newLine();
			bw.write(e.getMessage());
			bw.newLine();
			bw.write("=====================================================================================");
			return false;
		}
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