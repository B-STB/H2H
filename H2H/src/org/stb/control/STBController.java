package org.stb.control;

import java.util.List;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.security.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.CredentialRegisterService;
import org.stb.service.DiscoveryService;
import org.stb.service.FileDHTService;
import org.stb.service.LoginService;
import org.stb.service.impl.CredentialRegisterServiceImpl;
import org.stb.service.impl.DiscoveryServiceImpl;
import org.stb.service.impl.FileDHTServiceImpl;
import org.stb.service.impl.LoginServiceImpl;

/**
 * The Class STBController.
 */
public final class STBController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(STBController.class);

	/** The Constant INSTANCE. */
	private static final STBController INSTANCE = new STBController();

	/** The discovery service. */
	private final DiscoveryService discoveryService;
	
	/** The file dht service. */
	private final FileDHTService fileDHTService;
	
	/** The login service. */
	private final LoginService loginService;
	
	private final CredentialRegisterService registerService;
	
	/**
	 * Instantiates a new STB controller.
	 */
	private STBController() {
		discoveryService = new DiscoveryServiceImpl();
		loginService = new LoginServiceImpl();
		fileDHTService = new FileDHTServiceImpl();
		registerService = new CredentialRegisterServiceImpl();
	}

	/**
	 * Gets the single instance of STBController.
	 *
	 * @return single instance of STBController
	 */
	public static STBController getInstance() {
		return INSTANCE;
	}

	/**
	 * Start.
	 * @throws Exception 
	 */
	public void start() throws Exception {
		//Run discovery
		//Get data center ips from properties file.
		String dataCentersIps = null;
		boolean connectToDataCenters = discoveryService.connectToDataCenters(dataCentersIps);
		
		//Read from properties file
		boolean isDataCenter = true;
		if (!connectToDataCenters) {
			if (isDataCenter) {
				discoveryService.startDHTNetwork();
			} else {
				String message = "Could not connect to DHT network as all DataCenters are down.";
				LOGGER.error(message);
				System.out.println(message);
				return;
			}
		} 
		
		//Get from stb.properties
		String userId = null;
		// TODO later use encryption for password in the properties file. Do
		// decryption before passing to method.
		byte[] password = null;
		String pin = null;
		
		UserCredentials userCredentials= new UserCredentials(userId, new String(password), pin);
		
		if(userCredentials.getUserId() ==null){
			throw new Exception("No user credentials specified");
		}
		
		// TODO Node should be the return type of connect or join network
		IH2HNode node = null;
		IUserManager userManager = node.getUserManager();
		if(userCredentials.getUserId() ==null){
			throw new Exception("User Manager cannot be Null");
		}
		
		boolean isRegistered =registerService.registerCredential(node,userManager, userCredentials);
		
		if(isRegistered){
			loginService.loginToDHT(userManager,userCredentials);
		}
		//Login to DHT network
		
		
		//Perform file sync
		List<String> fileList = fileDHTService.getFileList();
		
		//Get all files in STB share folder. Check if files in fileList are present there.
	}
}
