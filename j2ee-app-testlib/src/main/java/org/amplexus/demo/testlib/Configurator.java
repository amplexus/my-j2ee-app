package org.amplexus.demo.testlib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Test configurator class.
 */
public class Configurator {
	
	public static final String TEST_PROPERTIES_FILE = "test.properties" ;
	public static final String PROP_ORDERMANAGER_WS_URL = "ordermanager.ws.url" ;

	/**
	 * Loads the test.properties file.
	 * @return the test properties.
	 */
	public static Properties getTestProperties() {
		// loading xmlProfileGen.properties from the classpath
		Properties props = new Properties();
		InputStream inputStream = Configurator.class.getClassLoader().getResourceAsStream(TEST_PROPERTIES_FILE);
		 
		if (inputStream == null) {
			throw new RuntimeException("property file '" + TEST_PROPERTIES_FILE + "' not found in the classpath");
		}
		 
		try {
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw new RuntimeException("error reading property file '" + TEST_PROPERTIES_FILE + "' - " + e.getMessage() ) ;
		}
	}
}
