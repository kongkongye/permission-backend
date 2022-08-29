package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {
    List<UserRole> findAllByUserCode(String userCode);

    Page<UserRole> findAllByUserCode(String userCode, Pageable pageable);

    UserRole findByUserCodeAndRoleCode(String userCode, String roleCode);
}
