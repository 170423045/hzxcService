package com.hzxc.framework.domain.cms.response;

import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.framework.model.response.ResultCode;
import lombok.Data;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode,CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
