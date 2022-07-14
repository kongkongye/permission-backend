package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.PerBindBriefDTO;
import com.kongkongye.backend.permission.dto.per.PerBindDTO;
import com.kongkongye.backend.permission.entity.per.PerBind;
import com.kongkongye.backend.permission.query.PerBindQuery;
import com.kongkongye.backend.permission.repository.PerBindRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class PerBindDao extends MyBaseDao<PerBind> {
    public PerBindDao(PerBindRepository repository) {
        super(repository);
    }

    public SqlHelper<PerBindDTO> help(PerBindQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, PerBindDTO.class);
    }

    public SqlHelper<PerBindBriefDTO> helpBrief(PerBindQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, PerBindBriefDTO.class);
    }
}
