/**
 * 
 */
package com.perfecto.commons.testng;

/**
 * @author prasantsutaria
 *
 */
public class InvalidCommandLineArgumentException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public InvalidCommandLineArgumentException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return "Aborting Execution : Reason - " + this.getMessage();
	}

}
