package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.BizDirDTO;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.query.BizDirQuery;
import com.kongkongye.backend.permission.repository.BizDirRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class BizDirDao extends MyBaseDao<BizDir> {
    public BizDirDao(BizDirRepository repository) {
        super(repository);
    }

    public SqlHelperBuilder<BizDirDTO> help(BizDirQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, BizDirDTO.class);
    }
}
