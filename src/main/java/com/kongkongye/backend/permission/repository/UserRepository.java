package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByName(String name);

    User findByCode(String name);
}
