package org.stb.service;

import java.io.File;
import java.util.List;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;

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
	 * @throws NoSessionException 
	 * @throws NoPeerConnectionException 
	 * @throws ProcessExecutionException 
	 * @throws InvalidProcessStateException 
	 */
	List<String> getFileList(IH2HNode node) throws NoPeerConnectionException, NoSessionException, InvalidProcessStateException, ProcessExecutionException;

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
	
	void syncFilesWithDHT(List<String> fileList, File file);

}
