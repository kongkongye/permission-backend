package com.kongkongye.backend.permission;

import com.kongkongye.backend.permission.common.Base;
import com.kongkongye.backend.permission.common.Paging;
import com.kongkongye.backend.permission.dao.*;
import com.kongkongye.backend.permission.repository.*;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyBaseGraphqlResolver<T> extends Base implements GraphQLResolver<T> {
    @Autowired
    protected UserDeptDao userDeptDao;
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected DeptDao deptDao;
    @Autowired
    protected BizDirDao bizDirDao;
    @Autowired
    protected BizDao bizDao;
    @Autowired
    protected PerBindDao perBindDao;
    @Autowired
    protected PerTypeDao perTypeDao;
    @Autowired
    protected PerValueDao perValueDao;
    @Autowired
    protected RoleDao roleDao;
    @Autowired
    protected UserRoleDao userRoleDao;

    @Autowired
    protected BizPerTypeDao bizPerTypeDao;
    @Autowired
    protected UserDeptRepository userDeptRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DeptRepository deptRepository;
    @Autowired
    protected BizDirRepository bizDirRepository;
    @Autowired
    protected BizRepository bizRepository;
    @Autowired
    protected PerBindRepository perBindRepository;
    @Autowired
    protected PerTypeRepository perTypeRepository;
    @Autowired
    protected PerValueRepository perValueRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected UserRoleRepository userRoleRepository;

    protected PageRequest wrap(Paging paging) {
        return PageRequest.of(paging.getPage(), paging.getPageSize());
    }

    protected <From, To> Page<To> wrapPage(Page<From> page, Function<From, To> func) {
        List<To> list = page.getContent().stream().map(func).collect(Collectors.toList());
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
