package org.stb.control;

import java.io.File;
import java.util.List;

import org.hive2hive.client.util.FileObserver;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.CredentialRegisterService;
import org.stb.service.DiscoveryService;
import org.stb.service.FileDHTService;
import org.stb.service.LoginService;
import org.stb.service.ShutDownService;
import org.stb.service.impl.CredentialRegisterServiceImpl;
import org.stb.service.impl.DiscoveryServiceImpl;
import org.stb.service.impl.FileDHTServiceImpl;
import org.stb.service.impl.LoginServiceImpl;
import org.stb.service.impl.ShutDownServiceImpl;
import org.stb.util.PropertyReader;
import org.stb.vo.UserCredential;

/**
 * The Class STBController.
 * 
 * @author aneesh.n
 */
public final class STBController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(STBController.class);

	/** The Constant INSTANCE. */
	private static final STBController INSTANCE = new STBController();

	/** The Constant BOOTSTRAP_IDS. */
	private static final String BOOTSTRAP_IDS = "bootstrap.ips";

	/** The discovery service. */
	private final DiscoveryService discoveryService;

	/** The file dht service. */
	private final FileDHTService fileDHTService;

	/** The login service. */
	private final LoginService loginService;

	/** The register service. */
	private final CredentialRegisterService registerService;

	/** The shut down service. */
	private final ShutDownService shutDownService;

	/** The connected node. */
	private IH2HNode connectedNode = null;

	/** The observer. */
	private FileObserver observer;

	/**
	 * Instantiates a new STB controller.
	 */
	private STBController() {
		discoveryService = new DiscoveryServiceImpl();
		loginService = new LoginServiceImpl();
		fileDHTService = new FileDHTServiceImpl();
		registerService = new CredentialRegisterServiceImpl();
		shutDownService = new ShutDownServiceImpl();
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
	 * @throws Exception
	 *             the exception
	 */
	public void start() throws Exception {
		// Run discovery
		// Get bootstrap node ips from properties file.
		boolean isBootstrapNode = Boolean.valueOf(PropertyReader.getValue("stb.isBootstrap"));
		if (!isBootstrapNode) {
			String bootstrapNodeIpsString = PropertyReader.getValue(BOOTSTRAP_IDS);
			String[] bootstrapNodeIps = null;
			if (bootstrapNodeIpsString == null || bootstrapNodeIpsString.trim().isEmpty()) {
				LOGGER.error("No bootstrap node ids found in properties file.");
				return;
			} else {
				bootstrapNodeIps = bootstrapNodeIpsString.split(",");
			}
			connectedNode = discoveryService.connectToBootstrapNodes(bootstrapNodeIps);

			if (connectedNode == null) {
				String message = "Could not connect to DHT network as all Bootstrap nodes are down.";
				LOGGER.error(message);
				System.out.println(message);
				return;
			}
		} else {
			LOGGER.info("Could not connect to any bootstrap nodes, starting node: {}", connectedNode);
			connectedNode = discoveryService.startDHTNetwork();
		}
		LOGGER.info("Connected node: {}", connectedNode);

		// Get from stb.properties
		String userId = PropertyReader.getValue("stb.username");
		char[] password = PropertyReader.getValue("stb.password").toCharArray();
		String pin = PropertyReader.getValue("stb.pin");
		String root = PropertyReader.getValue("stb.shareFolder");
		UserCredential userCredential = new UserCredential(userId, password, pin);

		// Register to DHT network
		boolean isRegistered = registerService.registerCredential(connectedNode, userCredential);

		// Login to DHT network
		if (isRegistered) {
			loginService.loginToDHT(connectedNode, userCredential, root);
		} else {
			String message = "Couldnt Login as the User is not yet registered";
			LOGGER.error(message);
			System.out.println(message);
			return;
		}

		// get file list from DHT
		List<String> fileListOnDHT = fileDHTService.getFileList(connectedNode);

		// Get all files in STB share folder. Check if files in fileList are
		// present there.
		// Perform file sync
		if (fileListOnDHT != null && !fileListOnDHT.isEmpty()) {
			fileDHTService.syncFilesWithDHT(connectedNode, fileListOnDHT, new File(root));
		} else {
			LOGGER.info("No files found on DHT.");
		}

		observer = fileDHTService.startObserver(connectedNode, new File(root));
	}

	/**
	 * Stop.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void stop() throws Exception {
		shutDownService.stop(connectedNode, observer);
	}
}
