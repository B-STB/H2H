package org.stb.service;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoginService.
 */
public interface LoginService {

	/**
	 * Login to dht.
	 *
	 * @param node the node
	 * @param userId the user id
	 * @param string the string
	 * @param pin the pin
	 * @return true, if successful
	 * @throws InvalidProcessStateException the invalid process state exception
	 * @throws ProcessExecutionException the process execution exception
	 * @throws NoPeerConnectionException the no peer connection exception
	 * @throws Exception 
	 */
	boolean loginToDHT(IH2HNode node, String userId, String string, String pin) throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException, Exception;
	
}
