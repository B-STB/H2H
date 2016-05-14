package org.stb.file.transfer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hive2hive.core.file.IFileAgent;

/**
 * Dummy file agent to demonstrate the examples
 * 
 * @author Nico
 *
 */
public class ExampleFileAgent implements IFileAgent {

	private final File root;

	public ExampleFileAgent(String path) {
		root = new File(path, UUID.randomUUID().toString());
		root.mkdirs();
	}

	@Override
	public File getRoot() {
		return root;
	}

	@Override
	public void writeCache(String key, byte[] data) throws IOException {
		// do nothing as examples don't depend on performance
	}

	@Override
	public byte[] readCache(String key) throws IOException {
		// do nothing as examples don't depend on performance
		return null;
	}

}
