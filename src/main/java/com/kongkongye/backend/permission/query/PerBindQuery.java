package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryNull;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;
import java.util.List;

@Data
@AutoQuery
public class PerBindQuery extends MyBaseQuery {
    private String bizCode;
    private String bindType;
    private String bindCode;
    private String typeCode;
    private String perCode;

    @QueryNull
    @QueryParse(sqlFieldName = "biz_code")
    private Boolean bizCodeIsNull;

    @QueryParse(sqlFieldName = "bind_code")
    private List<String> bindCodeList;
}
