package org.stb.connect;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.hive2hive.core.api.H2HNode;
import org.hive2hive.core.api.configs.FileConfiguration;
import org.hive2hive.core.api.configs.NetworkConfiguration;
import org.hive2hive.core.api.interfaces.IFileConfiguration;
import org.hive2hive.core.api.interfaces.IFileManager;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.INetworkConfiguration;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.core.model.PermissionType;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.file.transfer.ExampleFileAgent;

public class JoinP2P {

	private static final Logger logger = LoggerFactory.getLogger(JoinP2P.class);
	
	public void join(String peerIp, String username, String password, String pin, String path, String fileToTransfer) throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException,
			NoSessionException, IllegalArgumentException, IOException {
		// NetworkConfiguration netConfig2 =
		// NetworkConfiguration.create(InetAddress.getLocalHost()).setBootstrapPort(4777);

		/*
		 * IFileConfiguration fileConfig = FileConfiguration.createDefault();
		 * IH2HNode peerNode2 = H2HNode.createNode(fileConfig);
		 * peerNode2.connect();
		 */

		IFileConfiguration fileConfig = FileConfiguration.createDefault();

		IH2HNode myPeerNode = H2HNode.createNode(fileConfig);
		INetworkConfiguration networkConfiguration = NetworkConfiguration.create(InetAddress.getByName(peerIp)).setBootstrapPort(4622);
		System.out.println("My nodeId: " + networkConfiguration.getNodeID());
	//	NetworkConfiguration networkConfigurationBootstrapNode = NetworkConfiguration.create(InetAddress.getByName("148.147.222.160"));
		
	//	System.out.println("networkConfigurationBootstrapNode - " + networkConfigurationBootstrapNode);
		//IH2HNode bootstrapPeerNode = H2HNode.createNode(fileConfig);
		
		myPeerNode.connect(networkConfiguration);
		logger.info("IS  {}    NODE 2 CONNECTED -- {}" , myPeerNode.toString() , myPeerNode.isConnected());
		logger.info("peerNode2 {}   NODE 2 CONNECTED -- {}" , myPeerNode.toString() , myPeerNode.getPeer().peer());

		IUserManager userManager = myPeerNode.getUserManager();
//		
//		UserCredentials credentials = new UserCredentials("ajitesh.k@avaya.com", "Betsol@2016", "secret-pin");
//
//		boolean isRegistered = userManager.isRegistered("ajitesh.k@avaya.com");

		UserCredentials credentials = new UserCredentials(username, password, pin);

		boolean isRegistered = userManager.isRegistered(username);

		if (!isRegistered) {
			IProcessComponent<Void> registerUser = userManager.createRegisterProcess(credentials);
			registerUser.execute();
		}

		ExampleFileAgent node1FileAgent = new ExampleFileAgent(path);

		logger.info("Is User Registered {}" , isRegistered);

		// Path rootDirectory = Paths.get("D://git1");
		// IProcessComponent<Void> loginUser =
		// userManager.login(credentials,rootDirectory).await;

		IProcessComponent<Void> loginUser = userManager.createLoginProcess(credentials, node1FileAgent);
		loginUser.execute();
		logger.info("Is User LOGGEDIN {} " , userManager.isLoggedIn());
		IFileManager fileManager = myPeerNode.getFileManager();
		
		logger.info("node1FileAgent.getRoot() {} ",node1FileAgent.getRoot());

		File file = new File(node1FileAgent.getRoot(), "afnhjymk");

		//FileUtils.write(file, "Hi THis is OptimUs Prime");
		File file2 = Paths.get(fileToTransfer).toFile();
		
		FileUtils.copyFile(file2, file);
		
		fileManager.createAddProcess(file).execute();
		
		logger.info("Added");
		/*File folderAtAlice = new File(node1FileAgent.getRoot(), "shared-folder");
		folderAtAlice.mkdirs();
//		fileManager.createShareProcess(folderAtAlice, "ajitesh.k@avaya.com", PermissionType.WRITE);
		fileManager.createShareProcess(folderAtAlice, "Alice", PermissionType.WRITE);*/
		
		
		
		//		TransferFile tf = new TransferFile();
//		tf.initiateTransfer(myPeerNode);
		
		
		
	}
}
