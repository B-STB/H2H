package org.stb.main;

import java.io.IOException;

import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.stb.connect.CreateP2P;
import org.stb.connect.JoinP2P;

public class StartUp {

	public static void main(String[] args) throws InvalidProcessStateException, ProcessExecutionException, NoPeerConnectionException, NoSessionException, IllegalArgumentException, IOException {
		CreateP2P createP2P = new CreateP2P();
		createP2P.create();
		
		System.out.println("Created");
		
		
		
		//Uncomment at the other nodes to connect to the peer network .
		
		/*JoinP2P joinP2P = new JoinP2P();
		joinP2P.join();
		
		System.out.println("Joined");*/
	}

}
