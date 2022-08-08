package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.PerTypeDTO;
import com.kongkongye.backend.permission.entity.per.PerType;
import com.kongkongye.backend.permission.query.PerTypeQuery;
import com.kongkongye.backend.permission.repository.PerTypeRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class PerTypeDao extends MyBaseDao<PerType> {
    public PerTypeDao(PerTypeRepository repository) {
        super(repository);
    }

    public SqlHelperBuilder<PerTypeDTO> help(PerTypeQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
            if (query.getBizCode()!=null && !query.getBizCode().isEmpty()) {
                whereSql.append("and exists(select 1 from biz_per_type bpt where bpt.per_type_code =  a.`code` and bpt.biz_code = :bizCode)");
                params.put("bizCode", query.getBizCode());
            }
        }, query, PerTypeDTO.class);
    }
}
