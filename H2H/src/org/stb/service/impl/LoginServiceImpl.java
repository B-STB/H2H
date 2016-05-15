package org.stb.service.impl;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hive2hive.client.util.ConsoleFileAgent;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.LoginService;

public class LoginServiceImpl implements LoginService {

	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Override
	public boolean loginToDHT(IUserManager userManager, UserCredentials userCredentials)
			throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException {

		File root = new File(FileUtils.getTempDirectory(), UUID.randomUUID().toString());

		ConsoleFileAgent fileAgent = new ConsoleFileAgent(root);
		IProcessComponent<Void> loginProcess = userManager.createLoginProcess(userCredentials, fileAgent);
		try {
			loginProcess.execute();
			return true;
		} catch (ProcessExecutionException e) {
			logger.info("User - {} is Already Registered", userCredentials.getUserId());
			return false;
		}
	}

}
