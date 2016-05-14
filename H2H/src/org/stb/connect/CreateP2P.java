package org.stb.connect;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hive2hive.core.api.H2HNode;
import org.hive2hive.core.api.configs.FileConfiguration;
import org.hive2hive.core.api.configs.NetworkConfiguration;
import org.hive2hive.core.api.interfaces.IFileConfiguration;
import org.hive2hive.core.api.interfaces.IFileManager;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.events.framework.interfaces.IFileEventListener;
import org.hive2hive.core.events.framework.interfaces.file.IFileAddEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileDeleteEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileMoveEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileShareEvent;
import org.hive2hive.core.events.framework.interfaces.file.IFileUpdateEvent;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.security.UserCredentials;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.file.transfer.ExampleFileAgent;

import com.h2h.dht.util.ScannerUtil;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

public class CreateP2P {

	private static final Logger logger = LoggerFactory.getLogger(CreateP2P.class);
	
	public void create(String ip, String username, String password, String pin, String filePath) throws UnknownHostException,
			InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException {
		// INetworkConfiguration netConfig =
		// NetworkConfiguration.createInitialLocalPeer("first");

			
		IFileConfiguration fileConfig = FileConfiguration.createDefault();

		IH2HNode peerNode = H2HNode.createNode(fileConfig);

		// NetworkConfiguration networkConfiguration =
		// NetworkConfiguration.createInitial();

		String nodeId = UUID.randomUUID().toString();
		System.out.println("nodeId: " + nodeId);
		InetAddress address = InetAddress.getByName(ip);
		NetworkConfiguration networkConfiguration = NetworkConfiguration.create(nodeId, address);

		peerNode.connect(networkConfiguration);

		UserCredentials user = new UserCredentials(username, password, pin);
		peerNode.getUserManager().createRegisterProcess(user).execute();
		ExampleFileAgent node1FileAgent = new ExampleFileAgent(filePath);
		peerNode.getUserManager().createLoginProcess(user, node1FileAgent).execute();

		logger.info("Is Connected {}  - {}" , peerNode.toString() , peerNode.isConnected());

		logger.info("networkConfiguration.getNodeID() {} " , networkConfiguration.getNodeID());

		logger.info("networkConfiguration.getBootstapPeer() {} " , networkConfiguration.getBootstapPeer());
		logger.info("networkConfiguration.getBootstrapAddress() {}" ,networkConfiguration.getBootstrapAddress());

		peerNode.getFileManager().subscribeFileEvents(new ExampleEventListener(peerNode.getFileManager()));

		logger.info("Sunscribed to events");
	}

	// A Strong reference is necessary if this object is not held in any
	// variable, otherwise GC would clean it
	// and events are not triggered anymore. So keep either a reference to this
	// listener object or add the
	// strong reference annotation.
	@Listener(references = References.Strong)
	private static class ExampleEventListener implements IFileEventListener {

		private final IFileManager fileManager;

		public ExampleEventListener(IFileManager fileManager) {
			this.fileManager = fileManager;
		}

		@Override
		@Handler
		public void onFileAdd(IFileAddEvent fileEvent) {
			System.out.println("File was added: " + fileEvent.getFile().getName());
			try {
				// download the new file
				fileManager.createDownloadProcess(fileEvent.getFile()).execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		@Handler
		public void onFileUpdate(IFileUpdateEvent fileEvent) {
			System.out.println("File was updated: " + fileEvent.getFile().getName());
			try {
				// download the newest version
				fileManager.createDownloadProcess(fileEvent.getFile()).execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		@Handler
		public void onFileDelete(IFileDeleteEvent fileEvent) {
			System.out.println("File was deleted: " + fileEvent.getFile().getName());
			// delete it at the event receiver as well
			fileEvent.getFile().delete();
		}

		@Override
		@Handler
		public void onFileMove(IFileMoveEvent fileEvent) {
			try {
				// Move the file to the new destination if it exists
				if (fileEvent.isFile() && fileEvent.getSrcFile().exists()) {
					FileUtils.moveFile(fileEvent.getSrcFile(), fileEvent.getDstFile());
					System.out
							.println("File was moved from " + fileEvent.getSrcFile() + " to " + fileEvent.getDstFile());
				} else if (fileEvent.isFolder() && fileEvent.getSrcFile().exists()) {
					FileUtils.moveDirectory(fileEvent.getSrcFile(), fileEvent.getDstFile());
					System.out.println(
							"Folder was moved from " + fileEvent.getSrcFile() + " to " + fileEvent.getDstFile());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		@Handler
		public void onFileShare(IFileShareEvent fileEvent) {
			System.out.println("File was shared by " + fileEvent.getInvitedBy());
			// Currently, no further actions necessary. The invitation is
			// accepted
			// automatically and 'onFileAdd' is called in an instant.
		}

	}
}
