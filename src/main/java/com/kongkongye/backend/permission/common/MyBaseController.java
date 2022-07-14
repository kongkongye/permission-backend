package com.kongkongye.backend.permission.common;

import com.kongkongye.backend.common.BaseController;
import com.kongkongye.backend.common.Result;
import com.kongkongye.backend.common.exception.CustomResultException;
import com.kongkongye.backend.permission.dao.*;
import com.kongkongye.backend.permission.repository.*;
import com.kongkongye.backend.permission.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class MyBaseController extends BaseController {
    //异常id生成器
    private static final AtomicLong EX_ID_GENERATOR = new AtomicLong();

    @Autowired
    protected PerService perService;
    @Autowired
    protected BizService bizService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected DeptService deptService;

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

    @ExceptionHandler(CustomResultException.class)
    public Result handleCustomEx(CustomResultException ex) {
//        ex.printStackTrace();
        String reason = wrap(ex.getMessage());
        log.error(reason, ex);
        return ex.getResult();
    }

    @ExceptionHandler(Exception.class)
    public Result handleEx(Exception ex) {
//        ex.printStackTrace();
        String reason = wrap(ex.getMessage());
        log.error(reason, ex);
        return Result.fail(reason);
    }

    private String wrap(String reason) {
        return reason + " (异常ID：" + EX_ID_GENERATOR.addAndGet(1) + ")";
    }
}
