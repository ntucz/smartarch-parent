package com.smartarch.platform;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.smartarch.platform")
@EnableElasticsearchRepositories(basePackages = "com.smartarch.platform")
public class CommonConfig {

}
