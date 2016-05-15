package org.stb.service.impl;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hive2hive.client.util.ConsoleFileAgent;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.LoginService;
import org.stb.vo.UserCredential;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServiceImpl.
 */
public class LoginServiceImpl implements LoginService {

	/** The logger. */
	Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.stb.service.LoginService#loginToDHT(org.hive2hive.core.api.interfaces
	 * .IH2HNode, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginToDHT(IH2HNode node, UserCredential credentials) throws Exception {

		IUserManager userManager = node.getUserManager();
		if (userManager == null) {
			throw new Exception("User Manager Cannot be Null");
		}

		// TODO need to change this
		File root = new File(FileUtils.getTempDirectory(), UUID.randomUUID().toString());

		ConsoleFileAgent fileAgent = new ConsoleFileAgent(root);

		UserCredentials userCredentials = new UserCredentials(credentials.getUserName(),
				String.valueOf(credentials.getPassword()), credentials.getPin());
		IProcessComponent<Void> loginProcess = userManager.createLoginProcess(userCredentials, fileAgent);
		try {
			loginProcess.execute();
			return true;
		} catch (ProcessExecutionException e) {
			LOGGER.info("User - {} is Already Registered", userCredentials.getUserId());
			return false;
		}
	}

}
