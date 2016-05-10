package org.stb.file.transfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.hive2hive.core.api.interfaces.IFileManager;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;

public class TransferFile {
	public void initiateTransfer(IH2HNode peerNode) throws IOException, InvalidProcessStateException,
			ProcessExecutionException, NoPeerConnectionException, NoSessionException, IllegalArgumentException {

		IUserManager userManager = peerNode.getUserManager();

		UserCredentials credentials = new UserCredentials("ajitesh.k@betsol.com", "#####", "secret-pin");
		
		boolean isRegistered = userManager.isRegistered("ajitesh.k@betsol.com");
		
		if(!isRegistered){
			IProcessComponent<Void> registerUser = userManager.createRegisterProcess(credentials);
			registerUser.execute();
		}
	
		
		ExampleFileAgent node1FileAgent = new ExampleFileAgent();
		
		System.out.println("Is User Registered"+isRegistered);
		
		//Path rootDirectory = Paths.get("D://git1");
	//	IProcessComponent<Void> loginUser = userManager.login(credentials,rootDirectory).await;
		
		IProcessComponent<Void> loginUser = userManager.createLoginProcess(credentials,node1FileAgent);
		loginUser.execute();
		System.out.println("Is User LOGGEDIN"+userManager.isLoggedIn());
		IFileManager fileManager = peerNode.getFileManager();

		File file = new File(node1FileAgent.getRoot(), "a.txt");

		FileUtils.write(file, "Hi");
		fileManager.createAddProcess(file).execute();

	}
}
