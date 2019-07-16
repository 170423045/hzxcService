package com.hzxc.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms
 * @ClassName: MongoConfig
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 19:18
 * @Version: 1.0
 */

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase db = mongoClient.getDatabase(this.database);
        return GridFSBuckets.create(db);
    }
}
