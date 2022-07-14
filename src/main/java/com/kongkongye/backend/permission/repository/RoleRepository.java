package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.role.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
