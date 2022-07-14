package com.kongkongye.backend.permission.controller.auth;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.entity.user.UserDept;
import com.kongkongye.backend.permission.query.UserQuery;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/user")
public class AuthUserController extends MyBaseController {
    @RequestMapping("/query")
    public Result query(UserQuery query, Paging paging) {
        query.handle();
        query.setDeptCodeList(isTrue(query.getContainSubDept()) ? deptService.getDeptCodesRecursive(query.getDeptCodeList()) : query.getDeptCodeList());
        return Result.success(QueryUtil.query(query.getQueryType(), paging, userDao.help(query)));
    }

    @RequestMapping("/queryUserDepts")
    public Result<List<String>> queryUserDepts(String userCode) {
        return Result.success(userDeptRepository.findAllByUserCode(userCode).stream().map(UserDept::getDeptCode).collect(Collectors.toList()));
    }
}
