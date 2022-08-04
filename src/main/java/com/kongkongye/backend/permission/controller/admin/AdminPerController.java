package com.kongkongye.backend.permission.controller.admin;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.dao.BizPerTypeDao;
import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.entity.per.PerValue;
import com.kongkongye.backend.permission.query.*;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/per")
public class AdminPerController extends MyBaseController {
    @RequestMapping("/queryBizDir")
    public Result queryBizDir(BizDirQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, bizDirDao.help(query)));
    }

    @RequestMapping("/saveBizDir")
    public Result<BizDir> saveBizDir(BizDir entity) {
        return Result.success(bizService.saveBizDir(entity));
    }

    @RequestMapping("/queryBiz")
    public Result queryBiz(BizQuery query, Paging paging) {
        query.handle();
        query.setDirCodeList(isTrue(query.getContainSubDir()) ? bizService.getCodesRecursive(query.getDirCodeList()) : query.getDirCodeList());
        return Result.success(QueryUtil.query(query.getQType(), paging, bizDao.help(query).build()));
    }

    @RequestMapping("/saveBiz")
    public Result<Biz> saveBiz(Biz entity) {
        return Result.success(bizService.saveBiz(entity));
    }

    @RequestMapping("/queryPerType")
    public Result queryPerType(PerTypeQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, perTypeDao.help(query)));
    }

    @RequestMapping("/queryPerValue")
    public Result queryPerValue(PerValueQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, perValueDao.help(query)));
    }

    @RequestMapping("/queryPerValueBrief")
    public Result queryPerValueBrief(PerValueQuery query, Paging paging) {
        query.handle();
        query.setFilterCodeList(isTrue(query.getFilterContainSub()) && query.getFilterCodeList() != null ? perService.getCodesRecursive(query.getFilterCodeList()) : query.getFilterCodeList());
        return Result.success(QueryUtil.query(query.getQType(), paging, perValueDao.helpBrief(query)));
    }

    @RequestMapping("/savePerValue")
    public Result<PerValue> savePerValue(PerValue entity) {
        return Result.success(perService.savePerValue(entity));
    }

    @RequestMapping("/queryPerBind")
    public Result queryPerBind(PerBindQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, perBindDao.help(query)));
    }

    @RequestMapping("/queryPerBindBrief")
    public Result queryPerBindBrief(PerBindQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, perBindDao.helpBrief(query)));
    }

    @RequestMapping("/addPerBind")
    public Result addPerBind(String bizCode, String bindType, String bindCode, String typeCode, String perCode) {
        perService.addPerBind(bizCode, bindType, bindCode, typeCode, perCode);
        return Result.success();
    }

    @RequestMapping("/delPerBind")
    public Result delPerBind(String bizCode, String bindType, String bindCode, String typeCode, String perCode) {
        perService.delPerBind(bizCode, bindType, bindCode, typeCode, perCode);
        return Result.success();
    }
    @RequestMapping("queryBizPerType")
    public Result queryBizPerType(BizPerTypeQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, bizPerTypeDao.help(query)));
    }
    @RequestMapping("addBizPerType")
    public Result addBizPerType(String bizCode, String perTypeCode) {
        bizService.addBizPerType(bizCode, perTypeCode);
        return Result.success();
    }
    @RequestMapping("delBizPerType")
    public Result delBizPerType(String bizCode, String perTypeCode) {
        bizService.delBizPerType(bizCode, perTypeCode);
        return Result.success();
    }

}
