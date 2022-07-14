package com.kongkongye.backend.permission.controller.admin;

import com.alibaba.fastjson.JSON;
import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.permission.query.UserRoleQuery;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController extends MyBaseController {
    @RequestMapping("/save")
    public Result<User> save(User user) {
        return Result.success(userService.save(user));
    }

    @RequestMapping("/disable")
    public Result disable(long userId, boolean disable) {
        userService.doDisable(userId, disable);
        return Result.success();
    }

    @RequestMapping("/setUserDepts")
    public Result setUserDepts(String userCode, String deptCodesStr) {
        List<String> deptCodes = JSON.parseArray(deptCodesStr, String.class);
        userService.setUserDepts(userCode, deptCodes);
        return Result.success();
    }

    @RequestMapping("/queryUserRole")
    public Result queryUserRole(UserRoleQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQueryType(), paging, userRoleDao.help(query)));
    }

    @RequestMapping("/addUserRole")
    public Result addUserRole(String userCode, String roleCode) {
        userService.addUserRole(userCode, roleCode);
        return Result.success();
    }

    @RequestMapping("/delUserRole")
    public Result delUserRole(String userCode, String roleCode) {
        userService.delUserRole(userCode, roleCode);
        return Result.success();
    }

}
