package com.example.demo.utils;

import java.io.IOException;

/**
 * This class was added to help with with the testability of the Runtime class
 */
public class Shell {

	 /**
  	 * Execute the process on the command line based the string arguments.
  	 *
  	 * @param command the command
  	 * @return the process
  	 * @throws IOException Signals that an I/O exception has occurred.
  	 */
  	public Process exec(String command) throws IOException {
	    return Runtime.getRuntime().exec(command);
	  }

}