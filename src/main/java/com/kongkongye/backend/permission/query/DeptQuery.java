package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import lombok.Data;

@Data
@AutoQuery
public class DeptQuery extends MyBaseQuery {
    private String code;
    private String name;
    private String parent;
    private String note;
}
