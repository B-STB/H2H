package org.stb.service;

import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;

/**
 * The Interface LoginService.
 */
public interface LoginService {

	/**
	 * Login to dht.
	 *
	 * @param userManager the user manager
	 * @param userCredentials the user credentials
	 * @return true, if successful
	 * @throws InvalidProcessStateException the invalid process state exception
	 * @throws ProcessExecutionException the process execution exception
	 * @throws NoPeerConnectionException the no peer connection exception
	 */
	boolean loginToDHT(IUserManager userManager, UserCredentials userCredentials) throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException;
	
}
