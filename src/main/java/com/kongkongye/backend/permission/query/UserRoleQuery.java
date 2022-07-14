package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import com.kongkongye.backend.queryer.query.annotation.QueryParse;
import lombok.Data;

import java.util.List;

@Data
@AutoQuery
public class UserRoleQuery extends MyBaseQuery {
    private String userCode;
    private String roleCode;
    @QueryParse(alias = "r", sqlFieldName = "name")
    private String roleName;

    @QueryParse(enable = false)
    private String deptCodes;
    @QueryParse(alias = "r", sqlFieldName = "dept_code")
    private List<String> deptCodeList;
    @QueryParse(enable = false)
    private Boolean containSubDept;
}
