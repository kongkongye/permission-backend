package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.role.RoleDTO;
import com.kongkongye.backend.permission.entity.role.Role;
import com.kongkongye.backend.permission.query.RoleQuery;
import com.kongkongye.backend.permission.repository.RoleRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class RoleDao extends MyBaseDao<Role> {
    public RoleDao(RoleRepository repository) {
        super(repository);
    }

    public SqlHelper<RoleDTO> help(RoleQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
        }, query, RoleDTO.class);
    }
}
