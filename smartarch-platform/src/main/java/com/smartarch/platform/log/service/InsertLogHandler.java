package com.smartarch.platform.log.service;

import java.util.List;

import com.smartarch.platform.log.api.ElasticService;
import com.smartarch.platform.log.api.SpringContextHolder;
import com.smartarch.platform.log.bean.LogMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class InsertLogHandler {
	public void insertLog(List<LogMessage> list) {
		ElasticService esService = SpringContextHolder.getBean(ElasticService.class);
		if(esService!=null) {
			if(esService.createIndex()) {
				esService.saveAll(list);
			}
		}
	}

	public boolean available() {
		// es health check
		return true;
	}

}
