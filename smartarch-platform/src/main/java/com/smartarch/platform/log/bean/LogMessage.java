package com.smartarch.platform.log.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "${esConfig.getLogIndexName()}", shards = 3, replicas = 1)
public class LogMessage {

    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private Integer logType;
    
    @Field(type = FieldType.Keyword)
    private String appModule;
    
    @Field(type = FieldType.Keyword)
    private String rank;

    @Field(type = FieldType.Keyword)
    private String userName;
    
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ",timezone="GMT+8")
    private Date logDate;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
    
    private int retry = 0;

	public LogMessage(Long id, Integer logType, String appModule, String rank, String userName, Date logDate,
			String content) {
		this.id = id;
		this.logType = logType;
		this.appModule = appModule;
		this.rank = rank;
		this.userName = userName;
		this.logDate = logDate;
		this.content = content;
	}

}
