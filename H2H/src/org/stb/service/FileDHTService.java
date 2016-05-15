package org.stb.service;

import java.io.File;
import java.util.List;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;

// TODO: Auto-generated Javadoc
/**
 * The Interface FileDHTService.
 * 
 * @author aneesh.n
 */
public interface FileDHTService {

	/**
	 * Gets the file list.
	 *
	 * @param node the node
	 * @return the file list
	 * @throws NoPeerConnectionException the no peer connection exception
	 * @throws NoSessionException the no session exception
	 * @throws InvalidProcessStateException the invalid process state exception
	 * @throws ProcessExecutionException the process execution exception
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
	
	/**
	 * Sync files with dht.
	 *
	 * @param node the node
	 * @param fileList the file list
	 * @param file the file
	 */
	void syncFilesWithDHT(IH2HNode node,List<String> fileList, File file);

}
