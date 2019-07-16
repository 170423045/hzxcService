package com.hzxc.manage_cms.mapper;


import com.hzxc.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("cmsSiteMapper")
public interface CmsSiteMapper extends MongoRepository<CmsSite, String>{


}
