package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.dept.DeptDTO;
import com.kongkongye.backend.permission.entity.dept.Dept;
import com.kongkongye.backend.permission.query.DeptQuery;
import com.kongkongye.backend.permission.repository.DeptRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class DeptDao extends MyBaseDao<Dept> {
    public DeptDao(DeptRepository repository) {
        super(repository);
    }

    public SqlHelper<DeptDTO> help(DeptQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, DeptDTO.class);
    }
}
