package com.hzxc.cms_manage_client.mapper;

import com.hzxc.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client.mapper
 * @ClassName: CmsPageMapper
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 21:19
 * @Version: 1.0
 */
@Repository
public interface CmsPageMapper extends MongoRepository<CmsPage,String> {
}
