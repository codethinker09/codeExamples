package com.jerseyClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Intializer {

	private static Properties prop;

	static {
		InputStream is = null;
		try {
			prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			is = loader.getResourceAsStream("/config.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}
}
