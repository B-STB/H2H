/**
 * 
 */
package org.stb.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class UserCredential.
 *
 * @author Ajitesh.k
 */
public class UserCredential {


	/** The user name. */
	private String userName;

	/** The password. */
	private String password;

	/** The pin. */
	private String pin;

	

	/**
	 * Instantiates a new user credential.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @param pin the pin
	 */
	public UserCredential(String userName, String password, String pin) {
		super();
		this.userName = userName;
		this.password = password;
		this.pin = pin;
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the pin.
	 *
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * Sets the pin.
	 *
	 * @param pin
	 *            the new pin
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

}
