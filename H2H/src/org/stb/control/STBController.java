package org.stb.control;

import java.io.File;
import java.util.List;

import org.hive2hive.core.api.interfaces.IH2HNode;
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
import org.stb.util.PropertyReader;
import org.stb.vo.UserCredential;

/**
 * The Class STBController.
 * @author aneesh.n
 */
public final class STBController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(STBController.class);

	/** The Constant INSTANCE. */
	private static final STBController INSTANCE = new STBController();

	private static final String BOOTSTRAP_IDS = "bootstrap.ips";

	/** The discovery service. */
	private final DiscoveryService discoveryService;
	
	/** The file dht service. */
	private final FileDHTService fileDHTService;
	
	/** The login service. */
	private final LoginService loginService;
	
	/** The register service. */
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
	 *
	 * @throws Exception the exception
	 */
	public void start() throws Exception {
		//Run discovery
		//Get bootstrap node ips from properties file.
		String bootstrapNodeIps = PropertyReader.getValue(BOOTSTRAP_IDS);
		IH2HNode connectedNode = discoveryService.connectToBootstrapNodes(bootstrapNodeIps);
		
		//Read from properties file
		boolean isBootstrapNode = true;
		if (connectedNode == null) {
			if (isBootstrapNode) {
				connectedNode = discoveryService.startDHTNetwork();
			} else {
				String message = "Could not connect to DHT network as all Bootstrap nodes are down.";
				LOGGER.error(message);
				System.out.println(message);
				return;
			}
		} 
		
		
		
		String userId = PropertyReader.getValue("stb.username");
		char[] password = PropertyReader.getValue("stb.password").toCharArray();
		String pin = PropertyReader.getValue("stb.pin");
		String root = PropertyReader.getValue("stb.shareFolder");
		UserCredential userCredential = new UserCredential(userId,password,pin);
		//Get from stb.properties
		
	
		
		boolean isRegistered =registerService.registerCredential(connectedNode,userCredential);
		
		if(isRegistered){
			loginService.loginToDHT(connectedNode,userCredential);
		}else{
			String message = "Couldnt Login as the User is not yet registered";
			LOGGER.error(message);
			System.out.println(message);
			return;
		}
		//Login to DHT network
		
		
		//Perform file sync
		List<String> fileListOnDHT = fileDHTService.getFileList(connectedNode);
		
		//Get all files in STB share folder. Check if files in fileList are present there.
		fileDHTService.syncFilesWithDHT(connectedNode,fileListOnDHT,new File(root));
		
		
		fileDHTService.startObserver(connectedNode,new File(root));
		
	}
	
	public void stop() throws Exception {
		// TODO
	}
}
