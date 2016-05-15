package org.stb.service;

public interface DiscoveryService {

	/**
	 * Connect to data centers.
	 *
	 * @param dataCentersIps the data centers ips
	 * @return true, if any of the ips were successfully connected
	 */
	boolean connectToDataCenters(String... dataCentersIps);
	
	/**
	 * Start dht network.
	 */
	void startDHTNetwork();
	
}
