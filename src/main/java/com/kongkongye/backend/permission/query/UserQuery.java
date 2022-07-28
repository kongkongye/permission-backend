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
public class UserQuery extends MyBaseQuery {
    private String code;
    private String name;
    private String nickname;
    private Boolean disabled;

    @QueryParse(enable = false)
    private String deptCodes;
    @QueryParse(enable = false)
    private List<String> deptCodeList;
    @QueryParse(enable = false)
    private Boolean containSubDept;

    public void handle() {
        if (!Strings.isNullOrEmpty(deptCodes)) {
            deptCodeList = JSON.parseArray(deptCodes, String.class);
        }
    }
}
