package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.PerValueBriefDTO;
import com.kongkongye.backend.permission.dto.per.PerValueDTO;
import com.kongkongye.backend.permission.entity.per.PerValue;
import com.kongkongye.backend.permission.query.PerValueQuery;
import com.kongkongye.backend.permission.repository.PerValueRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class PerValueDao extends MyBaseDao<PerValue> {
    public PerValueDao(PerValueRepository repository) {
        super(repository);
    }

    public SqlHelperBuilder<PerValueDTO> help(PerValueQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, PerValueDTO.class);
    }

    public SqlHelperBuilder<PerValueBriefDTO> helpBrief(PerValueQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, PerValueBriefDTO.class);
    }
}
