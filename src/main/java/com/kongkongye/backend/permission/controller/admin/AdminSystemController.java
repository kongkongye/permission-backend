package com.kongkongye.backend.permission.controller.admin;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/system")
public class AdminSystemController extends MyBaseController {
    @RequestMapping("/refreshCache")
    public Result refreshCache() {
        bizService.getBizDirCache().refreshCache();
        deptService.getDeptCache().refreshCache();
        perService.getPerValueCache().refreshCache();
        return Result.success();
    }
}
