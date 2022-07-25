package com.kongkongye.backend.permission.controller.admin;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.entity.dept.Dept;
import com.kongkongye.backend.permission.query.DeptQuery;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dept")
public class AdminDeptController extends MyBaseController {
    @RequestMapping("/query")
    public Result query(DeptQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, deptDao.help(query)));
    }

    @RequestMapping("/save")
    public Result<Dept> save(Dept dept) {
        return Result.success(deptService.save(dept));
    }

    @RequestMapping("/del")
    public Result<Dept> del(Long id) {
        return Result.success(deptService.del(id));
    }
}
