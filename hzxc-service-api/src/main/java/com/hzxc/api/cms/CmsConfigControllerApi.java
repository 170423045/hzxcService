package com.hzxc.api.cms;

import com.hzxc.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.api.cms
 * @ClassName: CmsConfigControllerApi
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 11:21
 * @Version: 1.0
 */
@Api(value = "cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理，查询接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据ID查询CMS配置信息")
    public CmsConfig getModel(String id);
}
