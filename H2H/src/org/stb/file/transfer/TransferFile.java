package org.stb.file.transfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.hive2hive.core.api.interfaces.IFileManager;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.IUserManager;
import org.hive2hive.core.events.framework.interfaces.IFileEventListener;
import org.hive2hive.core.events.framework.interfaces.file.IFileAddEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileDeleteEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileMoveEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileShareEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileUpdateEvent;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.core.model.PermissionType;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import net.tomp2p.dht.PeerDHT;

public class TransferFile {
	public void initiateTransfer(IH2HNode peerNode, PeerDHT connectedPeer) throws IOException, InvalidProcessStateException,
			ProcessExecutionException, NoPeerConnectionException, NoSessionException, IllegalArgumentException {

		ExampleFileAgent node1FileAgent = doLoginAndRegister(peerNode);

		IFileManager fileManager = peerNode.getFileManager();

		File file = new File(node1FileAgent.getRoot(), "a.txt");

		FileUtils.write(file, "Hi");
		//fileManager.createAddProcess(file).execute();
		
		
		
		System.out.println("Shared ----");
		
	}

	public void downloadFilesFromNetwork(PeerDHT connectedPeer) {
		System.out.println("Starting with File Manager" + connectedPeer.toString());
	}

	private ExampleFileAgent doLoginAndRegister(IH2HNode peerNode)
			throws NoPeerConnectionException, InvalidProcessStateException, ProcessExecutionException, NoSessionException, IllegalArgumentException {
		IUserManager userManager = peerNode.getUserManager();

		UserCredentials credentials = new UserCredentials("hakim.s@betsol.com", "asd", "secret-pin");

		boolean isRegistered = userManager.isRegistered("hakim.s@betsol.com");

		if (!isRegistered) {
			IProcessComponent<Void> registerUser = userManager.createRegisterProcess(credentials);
			registerUser.execute();
		}

		ExampleFileAgent node1FileAgent = new ExampleFileAgent("C:\\Temp");

		System.out.println("Is User Registered" + isRegistered);
		
		// Path rootDirectory = Paths.get("D://git1");
		// IProcessComponent<Void> loginUser =
		// userManager.login(credentials,rootDirectory).await;

		IProcessComponent<Void> loginUser = userManager.createLoginProcess(credentials, node1FileAgent);
		loginUser.execute();
		System.out.println("Is User LOGGEDIN" + userManager.isLoggedIn());
		
		IFileManager fileManager = peerNode.getFileManager();
		File f = new File(node1FileAgent.getRoot(),"Tests");
		f.mkdirs();
		fileManager.createShareProcess(f, "ajitesh.k@betsol.com",PermissionType.READ).execute();
		return node1FileAgent;
	}


}
