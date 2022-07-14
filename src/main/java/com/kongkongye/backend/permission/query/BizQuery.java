package com.kongkongye.backend.permission.query;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;

import java.util.List;

@Data
@AutoQuery
public class BizQuery extends MyBaseQuery {
    private String dirCode;
    private String code;
    private String name;
    private String note;

    @QueryParse(enable = false)
    private String dirCodes;
    @QueryParse(sqlFieldName = "dir_code")
    private List<String> dirCodeList;
    @QueryParse(enable = false)
    private Boolean containSubDir;

    public void handle() {
        if (!Strings.isNullOrEmpty(dirCodes)) {
            dirCodeList = JSON.parseArray(dirCodes, String.class);
        }
    }
}
