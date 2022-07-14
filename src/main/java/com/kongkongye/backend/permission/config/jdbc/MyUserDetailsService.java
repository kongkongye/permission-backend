package com.kongkongye.backend.permission.config.jdbc;

import com.google.common.collect.Lists;
import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.permission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new MyUser(user.getId(), user.getName(), user.getPassword(), Lists.newArrayList("ADMIN").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
