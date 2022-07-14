package com.kongkongye.backend.permission.controller.all;

import com.kongkongye.backend.common.Result;
import com.kongkongye.backend.common.exception.CustomResultException;
import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.config.jdbc.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all/user")
public class AllUserController extends MyBaseController {
    @GetMapping("/currentUser")
    public Result<MyUser> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomResultException(Result.fail("notLogin", "未登录"));
        }
        return Result.success((MyUser) authentication.getPrincipal());
    }
}
