package org.stb.service;

public interface LoginService {

	/**
	 * Login to dht.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @param pin the pin
	 */
	void loginToDHT(String userId, byte[] password, String pin);
	
}
