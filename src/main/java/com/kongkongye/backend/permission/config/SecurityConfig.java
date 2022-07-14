package com.kongkongye.backend.permission.config;

import com.alibaba.fastjson.JSON;
import com.kongkongye.backend.common.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
        };
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource ds) {
//        return new MyUserDetailsService();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        return http
                //登陆
                .securityContext(context -> context.requireExplicitSave(true))
                .cors().and()//使用spring web默认cors配置
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> {
                    form.successHandler((req, res, auth) -> {
                        res.setContentType("application/json;charset=UTF-8");
                        res.getWriter().write(JSON.toJSONString(Result.success()));
                    });
                    form.failureHandler((req, res, e) -> {
                        res.setContentType("application/json;charset=UTF-8");
                        res.getWriter().write(JSON.toJSONString(Result.fail(e.getMessage())));
                    });
                })
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement().and()
                .authenticationProvider(daoAuthenticationProvider)
                .logout(logout -> logout.logoutSuccessHandler((req, res, authentication) -> {
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write(JSON.toJSONString(Result.success()));
                }))
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write(JSON.toJSONString(Result.fail("notLogin", "未登录")));
                }))
                //授权
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/all/**").permitAll()
                        .mvcMatchers("/auth/**").authenticated()
                        .mvcMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                //构建
                .build();
    }
}
