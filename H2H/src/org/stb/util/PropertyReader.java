/**
 * 
 */
package org.stb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ajitesh.k
 *
 */
public class PropertyReader {

	private static PropertyReader instance = null;

	private Properties props = null;

	private PropertyReader() {
		try (InputStream input = new FileInputStream("stb.properties")) {
			props.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static synchronized PropertyReader getInstance() {
		if (instance == null)
			instance = new PropertyReader();
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}

}
