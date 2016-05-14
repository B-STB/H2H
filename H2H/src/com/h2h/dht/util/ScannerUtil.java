package com.h2h.dht.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.connect.CreateP2P;
import org.stb.connect.JoinP2P;

public class ScannerUtil {

	private static final Logger logger = LoggerFactory.getLogger(ScannerUtil.class);

	public static void main(String[] arguments) {

		String ip = null, username, password, pin, path, fileToTransfer;

		System.out.println("Select Option");
		System.out.println("1.Create P2P");
		System.out.println("2.Join P2P");

		String option = null;
		try (Scanner scanOption = new Scanner(System.in)) {
			option = scanOption.next();
			System.out.println("User has selected option : " + option);
			// scanOption.close();

			if (!option.contains("1")) {
				System.out.print("Enter the Ip of the node to create : ");
				ip = scanOption.next();
			}
			// scanIp.close();

			System.out.print("Enter username : ");
			username = scanOption.next();
			// scanUsername.close();
			System.out.print("Enter password : ");
			password = scanOption.next();
			// scanPassword.close();

			System.out.print("Enter pin : ");
			pin = scanOption.next();
			// scanPin.close();1

			System.out.print("Enter path to store a file : ");
			path = scanOption.next();

			logger.info("Input : -------------");
			logger.info("IP : {}", ip);
			logger.info("Username : {}", username);
			logger.info("Password : {}", password);
			logger.info("Pin : {}", pin);
			logger.info("File path : {}", path);

			if (option.contains("1")) {
				logger.info("Creating a node.");
				CreateP2P createPeerNode = new CreateP2P();
				try {

					createPeerNode.create(username, password, pin, path);
					logger.info("------------Node Created----------");
				} catch (UnknownHostException | InvalidProcessStateException | ProcessExecutionException
						| NoPeerConnectionException e) {
					e.printStackTrace();
				}
			} else {

				System.out.print("Path of the file to  transfer : ");

				fileToTransfer = scanOption.next();
				JoinP2P joinNode = new JoinP2P();
				try {
					logger.info("Joining with a node.");
					logger.info("File to transfer : {}", fileToTransfer);
					joinNode.join(ip, username, password, pin, path, fileToTransfer);
					logger.info("------------Peer Joined----------");
				} catch (InvalidProcessStateException | ProcessExecutionException | NoPeerConnectionException
						| NoSessionException | IllegalArgumentException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
