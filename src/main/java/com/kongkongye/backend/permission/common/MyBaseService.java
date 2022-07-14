package com.kongkongye.backend.permission.common;

import com.kongkongye.backend.common.BaseService;
import com.kongkongye.backend.permission.dao.*;
import com.kongkongye.backend.permission.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class MyBaseService extends BaseService {
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
}
