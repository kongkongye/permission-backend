package com.kongkongye.backend.permission.graphql;

import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.permission.repository.UserRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private UserRepository userRepository;

    CompletableFuture<User> getUser(Long id) {
        return CompletableFuture.supplyAsync(() -> userRepository.findById(id).orElse(null));
    }
}
