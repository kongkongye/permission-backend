package com.kongkongye.backend.permission.controller.admin;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.entity.role.Role;
import com.kongkongye.backend.permission.query.RoleQuery;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/role")
public class AdminRoleController extends MyBaseController {
    @RequestMapping("/query")
    public Result query(RoleQuery query, Paging paging) {
        query.handle();
        query.setDeptCodeList(isTrue(query.getContainSubDept()) ? deptService.getCodesRecursive(query.getDeptCodeList()) : query.getDeptCodeList());
        return Result.success(QueryUtil.query(query.getQueryType(), paging, roleDao.help(query)));
    }

    @RequestMapping("/save")
    public Result<Role> save(Role role) {
        return Result.success(roleDao.save(role));
    }
}
