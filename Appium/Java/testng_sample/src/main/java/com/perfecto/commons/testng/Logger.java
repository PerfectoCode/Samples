package com.perfecto.commons.testng;

public class Logger {
	
	public static enum STATUS{

		PASS("PASS"),
		FAIL("FAIL"),
		INFO("INFO"),
		FATAL("FATAL");
		
		public String status;
		
		STATUS(String status) {
			this.status = status;
		}
		
		public String getStatus() {
	        return this.status;
	    }
		
	}

	public static void log(STATUS status, String message) {
		
		System.out.println("[" + status.getStatus() + "] :: " + message);
		if(status.getStatus().equals("FATAL")) {
			System.exit(0);
		}
	}
}
