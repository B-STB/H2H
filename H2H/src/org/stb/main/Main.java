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
	 */
	public static void main(String[] args) {
		STBController.getInstance().start();
	}
	
}
