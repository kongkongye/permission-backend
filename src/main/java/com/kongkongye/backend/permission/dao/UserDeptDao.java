package com.kongkongye.backend.permission.dao;

import com.kongkongye.backend.permission.common.MyBaseDao;
import com.kongkongye.backend.permission.entity.user.UserDept;
import com.kongkongye.backend.permission.repository.UserDeptRepository;
import org.springframework.stereotype.Component;

@Component
public class UserDeptDao extends MyBaseDao<UserDept> {
    public UserDeptDao(UserDeptRepository repository) {
        super(repository);
    }
}
