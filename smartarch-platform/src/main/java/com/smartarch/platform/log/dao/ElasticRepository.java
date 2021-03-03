package com.smartarch.platform.log.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartarch.platform.log.bean.LogMessage;
public interface ElasticRepository extends ElasticsearchRepository<LogMessage, Long> {

}