package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;

@Data
@AutoQuery
public class BizPerTypeQuery extends MyBaseQuery {
    private String bizCode;
    private String perTypeCode;
    @QueryParse(alias = "pt", sqlFieldName = "name")
    private String perTypeName;
}
