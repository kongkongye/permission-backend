package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryNull;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;

@Data
@AutoQuery
public class PerValueQuery extends MyBaseQuery {
    private String typeCode;
    private String code;
    private String name;
    private String parent;
    private String note;

    @QueryParse(sqlFieldName = "parent")
    @QueryNull
    private Boolean parentIsNull;
}
