package com.hzxc.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms
 * @ClassName: DridFsTest
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 18:51
 * @Version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class DridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;


    //用以获取下载流
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 从数据库中将模板取出
     *
     *
     * 将模板文件存入数据库
     *
     * */
    @Test
    public void test1() throws FileNotFoundException {
        File file=new File("E:\\hzxc\\hzxcService\\test-freemarker\\src\\main\\resources\\templates\\course.ftl");
        FileInputStream fileInputStream=new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "course.ftl");
        System.out.println(objectId);
    }
    @Test
    public void test2() throws IOException {
        //根据ID查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5d25c604e19cc228048de508")));
        //获取下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //获取流
        GridFsResource gridFsResource=new GridFsResource(gridFSFile,gridFSDownloadStream);
        //将流中信息输出
        String content = IOUtils.toString(gridFsResource.getInputStream(),"utf-8");
        //打印查看
        System.out.println(content);
    }
}
