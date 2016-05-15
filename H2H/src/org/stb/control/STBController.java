package org.stb.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.DiscoveryService;
import org.stb.service.FileDHTService;
import org.stb.service.LoginService;
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
	
	/**
	 * Instantiates a new STB controller.
	 */
	private STBController() {
		discoveryService = new DiscoveryServiceImpl();
		loginService = new LoginServiceImpl();
		fileDHTService = new FileDHTServiceImpl();
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
	 */
	public void start() {
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
		//Login to DHT network
		loginService.loginToDHT(userId, password, pin);
		
		//Perform file sync
		List<String> fileList = fileDHTService.getFileList();
		
		//Get all files in STB share folder. Check if files in fileList are present there.
	}
}
