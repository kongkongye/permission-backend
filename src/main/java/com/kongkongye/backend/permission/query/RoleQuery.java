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
public class RoleQuery extends MyBaseQuery {
    private String deptCode;
    @QueryParse(enable = false)
    private String deptCodes;
    @QueryParse(sqlFieldName = "dept_code")
    private List<String> deptCodeList;
    @QueryParse(enable = false)
    private Boolean containSubDept;

    private String code;
    private String name;
    private String note;

    public void handle() {
        if (!Strings.isNullOrEmpty(deptCodes)) {
            deptCodeList = JSON.parseArray(deptCodes, String.class);
        }
    }
}
