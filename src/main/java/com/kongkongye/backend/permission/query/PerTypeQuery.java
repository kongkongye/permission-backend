package com.kongkongye.backend.permission.query;

import com.kongkongye.backend.permission.common.MyBaseQuery;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import lombok.Data;

@Data
@AutoQuery
public class PerTypeQuery extends MyBaseQuery {
    private String code;
    private String name;
    private String note;
    private String filter;
}
