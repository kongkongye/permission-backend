package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.per.BizDTO;
import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.permission.query.BizQuery;
import com.kongkongye.backend.permission.repository.BizRepository;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import org.springframework.stereotype.Component;

@Component
public class BizDao extends MyBaseDao<Biz> {
    public BizDao(BizRepository repository) {
        super(repository);
    }

    public SqlHelperBuilder<BizDTO> help(BizQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, BizDTO.class);
    }
}
