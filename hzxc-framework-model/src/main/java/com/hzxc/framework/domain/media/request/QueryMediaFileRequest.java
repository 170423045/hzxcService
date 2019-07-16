package com.hzxc.framework.domain.media.request;

import com.hzxc.framework.model.request.RequestData;
import lombok.Data;

@Data
public class QueryMediaFileRequest extends RequestData {

    private String fileOriginalName;
    private String processStatus;
    private String tag;
}
