package org.stb.util;

public final class FileUtils {

	private static final FileUtils INSTANCE = new FileUtils();

	private FileUtils() {
		// NO OP
	}

	public static FileUtils getInstance() {
		return INSTANCE;
	}

}