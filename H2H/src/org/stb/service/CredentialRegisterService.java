package org.stb.service;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;

/**
 * The Interface CredentialRegisterService.
 */
public interface CredentialRegisterService {


	boolean registerCredential(IH2HNode node, String userId, String string, String pin) throws NoPeerConnectionException, InvalidProcessStateException;
	
}
