package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.user.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {
    List<UserRole> findAllByUserCode(String userCode);

    UserRole findByUserCodeAndRoleCode(String userCode, String roleCode);
}
