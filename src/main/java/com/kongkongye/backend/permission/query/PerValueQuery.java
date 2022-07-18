package com.kongkongye.backend.permission.query;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryNull;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;

import java.util.List;

@Data
@AutoQuery
public class PerValueQuery extends MyBaseQuery {
    private String typeCode;
    private String code;
    private String name;
    private String parent;
    private String filterCode;
    @QueryParse(enable = false)
    private String filterCodes;
    @QueryParse(sqlFieldName = "filter_code")
    private List<String> filterCodeList;
    @QueryParse(enable = false)
    private Boolean filterContainSub;

    private String note;

    @QueryParse(sqlFieldName = "parent")
    @QueryNull
    private Boolean parentIsNull;

    public void handle() {
        if (!Strings.isNullOrEmpty(filterCodes)) {
            filterCodeList = JSON.parseArray(filterCodes, String.class);
        }
    }
}
