package com.hzxc.framework.domain.course.response;

import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.framework.domain.course.response
 * @ClassName: CmsPostPageResult
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/29 11:45
 * @Version: 1.0
 */
@NoArgsConstructor
@Data
@ToString
public class CmsPostPageResult extends ResponseResult {
    private String pageUrl;

    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
