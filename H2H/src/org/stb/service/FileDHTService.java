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
	 * Stop observer.
	 */
	void stopObserver();
	

	/**
	 * Start observer.
	 *
	 * @param node the node
	 * @param root the root
	 * @throws Exception the exception
	 */
	void startObserver(IH2HNode node, File root) throws Exception;

	void syncFilesWithDHT(IH2HNode node, List<String> fileListOnDHT, File file) throws NoPeerConnectionException,
			NoSessionException, IllegalArgumentException, InvalidProcessStateException, ProcessExecutionException;

}
