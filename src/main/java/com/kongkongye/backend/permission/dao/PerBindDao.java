package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.PerBindBriefDTO;
import com.kongkongye.backend.permission.dto.per.PerBindDTO;
import com.kongkongye.backend.permission.entity.per.PerBind;
import com.kongkongye.backend.permission.query.PerBindQuery;
import com.kongkongye.backend.permission.repository.PerBindRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class PerBindDao extends MyBaseDao<PerBind> {
    public PerBindDao(PerBindRepository repository) {
        super(repository);
    }

    private <T> SqlHelperBuilder<T> help(PerBindQuery query, Class<T> cls) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, cls);
    }

    public SqlHelperBuilder<PerBindDTO> help(PerBindQuery query) {
        return help(query, PerBindDTO.class).then((selSql, fromSql, whereSql, groupSql, params) -> {
        });
    }

    public SqlHelperBuilder<PerBindBriefDTO> helpBrief(PerBindQuery query) {
        return help(query, PerBindBriefDTO.class).then((selSql, fromSql, whereSql, groupSql, params) -> {
        });
    }
}
