package com.kongkongye.backend.permission.graphql;

import com.kongkongye.backend.permission.MyBaseGraphqlResolver;
import com.kongkongye.backend.permission.common.Paging;
import com.kongkongye.backend.permission.entity.role.Role;
import com.kongkongye.backend.permission.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UserResolver extends MyBaseGraphqlResolver<User> {
    CompletableFuture<List<Role>> roles(User user) {
        return CompletableFuture.supplyAsync(() -> userRoleRepository.findAllByUserCode(user.getCode()).stream().map(e -> roleRepository.findByCode(e.getRoleCode())).collect(Collectors.toList()));
    }

    CompletableFuture<Page<Role>> roleNodes(User user, Paging paging) {
        return CompletableFuture.supplyAsync(() -> wrapPage(
                userRoleRepository.findAllByUserCode(user.getCode(), wrap(paging)),
                e -> roleRepository.findByCode(e.getRoleCode())
        ));
    }
}
