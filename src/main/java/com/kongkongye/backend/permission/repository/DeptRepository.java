package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.dept.Dept;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeptRepository extends PagingAndSortingRepository<Dept, Long> {
    long countByParent(String parent);
}
