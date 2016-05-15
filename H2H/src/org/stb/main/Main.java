package org.stb.main;

import org.stb.control.STBController;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		STBController.getInstance().start();
	}
	
}
