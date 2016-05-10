package org.stb.connect;

import java.io.IOException;
import java.net.InetAddress;

import org.hive2hive.core.api.H2HNode;
import org.hive2hive.core.api.configs.FileConfiguration;
import org.hive2hive.core.api.configs.NetworkConfiguration;
import org.hive2hive.core.api.interfaces.IFileConfiguration;
import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.api.interfaces.INetworkConfiguration;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.stb.file.transfer.TransferFile;

public class JoinP2P {

	public void join() throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException,
			NoSessionException, IllegalArgumentException, IOException {
		// NetworkConfiguration netConfig2 =
		// NetworkConfiguration.create(InetAddress.getLocalHost()).setBootstrapPort(4777);

		/*
		 * IFileConfiguration fileConfig = FileConfiguration.createDefault();
		 * IH2HNode peerNode2 = H2HNode.createNode(fileConfig);
		 * peerNode2.connect();
		 */

		IFileConfiguration fileConfig = FileConfiguration.createDefault();

		INetworkConfiguration netConfig2 = NetworkConfiguration.create("second", InetAddress.getByName("192.168.1.9"));
		IH2HNode peerNode2 = H2HNode.createNode(fileConfig);
		peerNode2.connect(netConfig2);

		System.out.println("IS " + peerNode2.toString() + "NODE 2 CONNECTED --" + peerNode2.isConnected());

		TransferFile tf = new TransferFile();
		tf.initiateTransfer(peerNode2);
	}
}
