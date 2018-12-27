package com.simback.dbbackup;

import org.apache.log4j.Logger;

public class DumpLogger {
	private final static Logger logger = Logger.getLogger(DumpJob.class);
	
	public static Logger getLogger() {
		return logger;
	}
}