package org.stb.service.impl;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.CredentialRegisterService;

public class CredentialRegisterServiceImpl implements CredentialRegisterService {

	Logger logger = LoggerFactory.getLogger(CredentialRegisterServiceImpl.class);

	@Override
	public boolean registerCredential(IH2HNode node,IUserManager userManager,UserCredentials userCredentials) throws NoPeerConnectionException, InvalidProcessStateException {


		if (!userManager.isRegistered(userCredentials.getUserId())) {
			logger.info("User - {} is not Registered", userCredentials.getUserId());
			IProcessComponent<Void> registerProcess = userManager.createRegisterProcess(userCredentials);
			try {
				registerProcess.execute();
				return true;
			} catch (ProcessExecutionException e) {
				return false;
			}
		} else {
			logger.info("User - {} is Already Registered", userCredentials.getUserId());
		}
		return false;

	}

}
