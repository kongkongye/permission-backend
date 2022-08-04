package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.BizPerTypeDTO;
import com.kongkongye.backend.permission.entity.per.BizPerType;
import com.kongkongye.backend.permission.query.BizPerTypeQuery;
import com.kongkongye.backend.permission.repository.BizPerTypeRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class BizPerTypeDao extends MyBaseDao<BizPerType> {
    public BizPerTypeDao(BizPerTypeRepository repository) {
        super(repository);
    }

    public SqlHelperBuilder<BizPerTypeDTO> help(BizPerTypeQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
            fromSql.append("left join per_type pt on pt.code = a.per_type_code");
        }, query, BizPerTypeDTO.class);
    }
}
