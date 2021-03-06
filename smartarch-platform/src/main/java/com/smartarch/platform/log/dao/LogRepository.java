package com.smartarch.platform.log.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.smartarch.platform.log.bean.LogMessage;

public interface LogRepository extends ElasticsearchRepository<LogMessage, Long> {

}