package com.hzxc.manage_cms.mapper;

import com.hzxc.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.mapper
 * @ClassName: CmsConfigMapper
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 11:31
 * @Version: 1.0
 */
@Repository
public interface CmsConfigMapper extends MongoRepository<CmsConfig,String> {
}
