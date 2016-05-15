package org.stb.service;

import java.util.List;

/**
 * The Interface FileDHTService.
 * 
 * @author aneesh.n
 */
public interface FileDHTService {

	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	List<String> getFileList();

	/**
	 * Adds the file.
	 *
	 * @param fileName
	 *            the file name
	 */
	void addFile(String fileName);

	/**
	 * Download file.
	 *
	 * @param fileName
	 *            the file name
	 */
	void downloadFile(String fileName);

	/**
	 * Start observer.
	 */
	void startObserver();

	/**
	 * Stop observer.
	 */
	void stopObserver();

}
