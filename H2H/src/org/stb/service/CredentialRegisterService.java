package org.stb.service;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.stb.vo.UserCredential;

/**
 * The Interface CredentialRegisterService.
 * 
 * @author aneesh.n
 */
public interface CredentialRegisterService {

	boolean registerCredential(IH2HNode node, UserCredential userCredential)
			throws NoPeerConnectionException, InvalidProcessStateException;

}
