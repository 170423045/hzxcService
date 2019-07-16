package com.hzxc.framework.exception;

import com.hzxc.framework.model.response.ResultCode;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.framework.exception
 * @ClassName: CustomException
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/9 16:42
 * @Version: 1.0
 */
public class CustomException  extends RuntimeException{
    private ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
