package com.smartarch.platform.log.service;

import com.smartarch.platform.log.bean.LogMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogService {
    private static LogService logService = new LogService();
    
    private static volatile boolean start = false;
    
    private LogService() {
    	
    }
    
    public long recordOperLog(LogMessage message) {
    	log.info("receive an operlog to be inserted:{}", message.getId());
    	startLogThread();
    	RecordLogThread.getInstance().addLog(message);
    	return message.getId();
    }

	private synchronized static void startLogThread() {
		if(!start) {
			RecordLogThread.getInstance().start();
			log.info("Start log thread successfully.");
		}
		start = true;
	}
    
	public static LogService getInstance() {
		return logService;
	}
    
}
