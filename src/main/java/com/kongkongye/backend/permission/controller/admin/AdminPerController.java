package com.kongkongye.backend.permission.controller.admin;

import com.kongkongye.backend.permission.common.MyBaseController;
import com.kongkongye.backend.permission.common.Result;
import com.kongkongye.backend.permission.dto.per.PerValueTreeDTO;
import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.entity.per.PerType;
import com.kongkongye.backend.permission.entity.per.PerValue;
import com.kongkongye.backend.permission.query.*;
import com.kongkongye.backend.queryer.QueryUtil;
import com.kongkongye.backend.queryer.common.Paging;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    @RequestMapping("/savePerType")
    public Result<PerType> savePerType(PerType perType){
        return Result.success(perTypeDao.save(perType));
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

    @RequestMapping("/queryBizPerType")
    public Result queryBizPerType(BizPerTypeQuery query, Paging paging) {
        return Result.success(QueryUtil.query(query.getQType(), paging, bizPerTypeDao.help(query)));
    }

    @RequestMapping("/addBizPerType")
    public Result addBizPerType(String bizCode, String perTypeCode) {
        bizService.addBizPerType(bizCode, perTypeCode);
        return Result.success();
    }

    @RequestMapping("/delBizPerType")
    public Result delBizPerType(String bizCode, String perTypeCode) {
        bizService.delBizPerType(bizCode, perTypeCode);
        return Result.success();
    }

    /**
     * @param seeName 传了表示要看名字
     * @param recursive 传了表示要递归（包括子节点）
     * @param lv 传了表示只查这个层级的
     */
    @RequestMapping("/queryUserPerBindBrief")
    public Result<List<String>> queryUserPerBindBrief(PerBindQuery query, Boolean seeName, Boolean recursive, @Nullable Integer lv) {
        List<String> userPerList = perService.getUserPerList(query);
        if (isTrue(recursive)) {
            userPerList = perService.getCodesRecursive(userPerList);
        }
        if (lv != null && lv > 0) {
            userPerList = perService.filterByLv(userPerList, lv);
        }
        if (isTrue(seeName)) {
            userPerList = perService.getNames(userPerList);
        }
        return Result.success(userPerList);
    }

    /**
     * @param recursive 传了表示要递归（包括子节点）
     * @param lv 传了表示只查这个层级的
     */
    @RequestMapping("/queryUserPerBindTree")
    public Result<List<PerValueTreeDTO>> queryUserPerBindTree(PerBindQuery query, Boolean recursive, @Nullable Integer lv) {
        List<String> userPerList = perService.getUserPerList(query);
        if (isTrue(recursive)) {
            userPerList = perService.getCodesRecursive(userPerList);
        }
        if (lv != null && lv > 0) {
            userPerList = perService.filterByLv(userPerList, lv);
        }
        return Result.success(perService.getBriefRootTrees(userPerList));
    }

}
