package org.stb.service;

/**
 * The Interface DiscoveryService.
 * 
 * @author aneesh.n
 */
public interface DiscoveryService {

	/**
	 * Connect to bootstrap nodes.
	 *
	 * @param bootstrapIps
	 *            the bootstrap node ips
	 * @return true, if any of the ips were successfully connected
	 */
	boolean connectToBootstrapNodes(String... bootstrapIps);

	/**
	 * Start dht network.
	 */
	void startDHTNetwork();

}
