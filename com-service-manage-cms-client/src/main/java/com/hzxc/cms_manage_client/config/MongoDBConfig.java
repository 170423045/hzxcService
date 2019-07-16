package com.hzxc.cms_manage_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client.config
 * @ClassName: MongoDBConfig
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 21:51
 * @Version: 1.0
 */
@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient){
        //获取数据库对象
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database);
    }
}
