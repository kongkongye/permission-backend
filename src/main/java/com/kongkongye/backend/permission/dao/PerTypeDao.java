package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.PerTypeDTO;
import com.kongkongye.backend.permission.entity.per.PerType;
import com.kongkongye.backend.permission.query.PerTypeQuery;
import com.kongkongye.backend.permission.repository.PerTypeRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class PerTypeDao extends MyBaseDao<PerType> {
    public PerTypeDao(PerTypeRepository repository) {
        super(repository);
    }

    public SqlHelper<PerTypeDTO> help(PerTypeQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, PerTypeDTO.class);
    }
}
