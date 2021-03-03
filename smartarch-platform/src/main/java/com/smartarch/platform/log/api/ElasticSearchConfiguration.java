package com.smartarch.platform.log.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "esConfig")
public class ElasticSearchConfiguration {

    @Value("${esConfig.logIndexName:log_}")
    private String logIndexNamePrefix;

    public String getLogIndexName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM");
        return logIndexNamePrefix + LocalDateTime.now().format(formatter);
    }
}
