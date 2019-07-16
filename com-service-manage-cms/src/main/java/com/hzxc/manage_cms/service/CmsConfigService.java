package com.hzxc.manage_cms.service;

import com.hzxc.framework.domain.cms.CmsConfig;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.service
 * @ClassName: CmsConfigService
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 11:33
 * @Version: 1.0
 */
public interface CmsConfigService {
    CmsConfig getModel(String id);
}
