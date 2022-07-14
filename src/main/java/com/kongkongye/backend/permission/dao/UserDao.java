package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.dto.user.UserDTO;
import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.permission.query.UserQuery;
import com.kongkongye.backend.permission.repository.UserRepository;
import com.kongkongye.backend.queryer.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends MyBaseDao<User> {
    public UserDao(UserRepository repository) {
        super(repository);
    }

    public SqlHelper<UserDTO> help(UserQuery query) {
        return help((selSql, fromSql, whereSql, groupSql, params) -> {
            //deptCodeList
            if (query.getDeptCodeList() != null && !query.getDeptCodeList().isEmpty()) {
                whereSql.append(" and exists (select 1 from user_dept ud where ud.user_code=a.code and ud.dept_code in (:deptCodeList)) ");
                params.put("deptCodeList", query.getDeptCodeList());
            }
        }, query, UserDTO.class);
    }
}
