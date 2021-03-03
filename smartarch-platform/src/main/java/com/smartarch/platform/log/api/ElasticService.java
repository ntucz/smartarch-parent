package com.smartarch.platform.log.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

import com.smartarch.platform.log.bean.LogMessage;
import com.smartarch.platform.log.dao.ElasticRepository;

@Service
public class ElasticService {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;
    
    @Autowired
    private ElasticRepository elasticRepository;

    public Boolean createIndex() {
		/*
		 * IndexOperations indexOperations =
		 * esTemplate.indexOps(IndexCoordinates.of(indexName)); Map<String, Object>
		 * indexSettings = new HashMap<String, Object>(); indexSettings.put("indexName",
		 * indexName); indexSettings.put("shards", indexName);
		 * indexSettings.put("replicas", 1);
		 */
    	if(!indexExists()) {
    		IndexOperations indexOperations = esTemplate.indexOps(LogMessage.class);
    		indexOperations.createMapping(LogMessage.class);
    		return indexOperations.create();
    	}
    	return true;
    }
    
    public Boolean indexExists() {
        IndexOperations indexOperations = esTemplate.indexOps(LogMessage.class);
        return indexOperations.exists();
    }

    public Boolean deleteIndex(String index) {
    	IndexOperations indexOperations = esTemplate.indexOps(LogMessage.class);
        return indexOperations.delete();
    }
    
    public void saveAll(List<LogMessage> list) {
        elasticRepository.saveAll(list);
    }

}