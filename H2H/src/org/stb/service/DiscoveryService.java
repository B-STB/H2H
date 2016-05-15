package org.stb.service;

import org.hive2hive.core.api.interfaces.IH2HNode;

public interface DiscoveryService {

	/**
	 * Connect to bootstrap nodes.
	 *
	 * @param bootstrapIps the bootstrap node ips
	 * @return true, if any of the ips were successfully connected
	 */
	IH2HNode connectToBootstrapNodes(String... bootstrapIps);
	
	/**
	 * Start dht network.
	 * @return 
	 */
	IH2HNode startDHTNetwork();
	
}
