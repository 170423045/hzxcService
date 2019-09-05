package com.hzxc.api.sys;

import com.hzxc.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.api.sys
 * @ClassName: SysDictionary
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/23 14:41
 * @Version: 1.0
 */
@Api(value = "系统数据字典",description = "系统数据字典的管理",tags = {"数据字典"})
public interface SysDictionaryControllerApi {
    @ApiOperation("通过类型编码查询其分类内容")
    SysDictionary getDictionaryByDType(String dType);
}
