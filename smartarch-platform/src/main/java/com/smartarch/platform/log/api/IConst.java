package com.smartarch.platform.log.api;

public interface IConst {
	Integer LOG_TYPE_OPER = 0;
	
	Integer LOG_TYPE_SYS = 1;
	
	String LOG_RANK_NOTICE = "notice";
	
	String LOG_RANK_WARN = "warn";
	
	String LOG_RANK_ERROR = "error";
	
	String LOG_RANK_CRITIC = "critic";

	long DEFAULT_RECONNECTION_DELAY_TIME = 5000L;
	
}