package org.stb.connect;

import org.hive2hive.core.api.H2HNode;
import org.hive2hive.core.api.configs.FileConfiguration;
import org.hive2hive.core.api.configs.NetworkConfiguration;
import org.hive2hive.core.api.interfaces.IFileConfiguration;
import org.hive2hive.core.api.interfaces.IH2HNode;

public class CreateP2P {

	public void create(){
	//	INetworkConfiguration netConfig = NetworkConfiguration.createInitialLocalPeer("first");
		
		IFileConfiguration fileConfig = FileConfiguration.createDefault();

		IH2HNode peerNode = H2HNode.createNode(fileConfig);
		peerNode.connect(NetworkConfiguration.createInitial());
		
		
		System.out.println("Is Connected  "+peerNode.toString() + "  "+peerNode.isConnected());
	}
}
