package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.user.UserDept;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDeptRepository extends PagingAndSortingRepository<UserDept, Long> {
    List<UserDept> findAllByUserCode(String userCode);
}
