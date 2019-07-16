package com.hzxc.manage_cms.mapper;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.mapper
 * @ClassName: CmsTemplateMapper
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 21:07
 * @Version: 1.0
 */

import com.hzxc.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 获取template数据
 *
 * */
public interface CmsTemplateMapper extends MongoRepository<CmsTemplate,String> {
}
