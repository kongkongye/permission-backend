package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.user.UserRoleDTO;
import com.kongkongye.backend.permission.entity.user.UserRole;
import com.kongkongye.backend.permission.query.UserRoleQuery;
import com.kongkongye.backend.permission.repository.UserRoleRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class UserRoleDao extends MyBaseDao<UserRole> {
    public UserRoleDao(UserRoleRepository repository) {
        super(repository);
    }

    public SqlHelper<UserRoleDTO> help(UserRoleQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
            fromSql.append(" left join role r on r.code=a.role_code ");
        }, query, UserRoleDTO.class);
    }
}
