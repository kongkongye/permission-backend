package com.kongkongye.backend.permission.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.common.RedisCacheManager;
import com.kongkongye.backend.permission.dto.per.PerBindBriefDTO;
import com.kongkongye.backend.permission.dto.per.PerValueBriefDTO;
import com.kongkongye.backend.permission.dto.per.PerValueTreeDTO;
import com.kongkongye.backend.permission.entity.per.PerBind;
import com.kongkongye.backend.permission.entity.per.PerValue;
import com.kongkongye.backend.permission.entity.user.UserRole;
import com.kongkongye.backend.permission.query.PerBindQuery;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PerService extends MyBaseService implements InitializingBean {
    private Map<String, PerValueTreeDTO> codes = new HashMap<>();
    private List<PerValueTreeDTO> trees = new ArrayList<>();

    @Autowired
    @Qualifier("perValueCacheManager")
    @Getter
    private RedisCacheManager<List<PerValueBriefDTO>> perValueCache;

    public void refreshTree() {
        Map<String, PerValueTreeDTO> newCodes = new HashMap<>();
        List<PerValueTreeDTO> newTrees = new ArrayList<>();

        List<PerValueBriefDTO> data = perValueCache.getData();
        //depts
        for (PerValueBriefDTO value : data) {
            newCodes.put(value.getCode(), new PerValueTreeDTO(value));
        }
        //trees
        for (Map.Entry<String, PerValueTreeDTO> entry : newCodes.entrySet()) {
            PerValueTreeDTO value = entry.getValue();
            if (!Strings.isNullOrEmpty(value.getNode().getParent())) {
                PerValueTreeDTO parent = newCodes.get(value.getNode().getParent());
                if (parent != null) {
                    parent.addChild(value);
                }
            } else {
                newTrees.add(value);
            }
        }

        this.codes = newCodes;
        this.trees = newTrees;

        //修正等级
        for (PerValueTreeDTO tree : trees) {
            tree.fixLv(1);
        }
    }

    /**
     * 获取所有的编码，包括子
     */
    public List<String> getCodesRecursive(@Nullable List<String> codes) {
        return getRecursive(codes).stream().map(PerValueTreeDTO::getNode).map(PerValueBriefDTO::getCode).collect(Collectors.toList());
    }

    /**
     * 获取所有的树节点，包括子
     */
    public List<PerValueTreeDTO> getRecursive(@Nullable List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new ArrayList<>();
        }

        //本身
        List<PerValueTreeDTO> result = codes.stream().map(e -> this.codes.get(e)).filter(Objects::nonNull).collect(Collectors.toList());

        //children
        List<String> childrenCodes = new ArrayList<>();
        for (PerValueTreeDTO parent : new ArrayList<>(result)) {
            if (parent.getChildren() != null) {
                childrenCodes.addAll(parent.getChildren().stream().map(e -> e.getNode().getCode()).collect(Collectors.toList()));
            }
        }
        result.addAll(getRecursive(childrenCodes));

        return result;
    }

    public void addPerBind(String bizCode, String bindType, String bindCode, String typeCode, String perCode) {
        PerBind old = getPerBind(bizCode, bindType, bindCode, typeCode, perCode);
        Preconditions.checkArgument(old == null, "已经存在该绑定");

        PerBind perBind = new PerBind();
        perBind.setBizCode(bizCode);
        perBind.setBindType(bindType);
        perBind.setBindCode(bindCode);
        perBind.setTypeCode(typeCode);
        perBind.setPerCode(perCode);
        perBindDao.save(perBind);
    }

    public void delPerBind(String bizCode, String bindType, String bindCode, String typeCode, String perCode) {
        PerBind old = getPerBind(bizCode, bindType, bindCode, typeCode, perCode);
        Preconditions.checkArgument(old != null, "不存在该绑定");

        perBindDao.del(old);
    }

    private PerBind getPerBind(String bizCode, String bindType, String bindCode, String typeCode, String perCode) {
        PerBind old;
        if (Strings.isNullOrEmpty(bizCode)) {
            old = perBindRepository.findWithBizNull(bindType, bindCode, typeCode, perCode);
        } else {
            old = perBindRepository.findWithBiz(bizCode, bindType, bindCode, typeCode, perCode);
        }
        return old;
    }

    public PerValue savePerValue(PerValue entity) {
        entity = perValueDao.save(entity, "code");
        perValueCache.refreshCache();
        return entity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        perValueCache.addUpdateNotifier(this::refreshTree);
        refreshTree();
    }

    public List<String> getUserPerList(PerBindQuery query) {
        List<String> userPerList = perBindDao.helpBrief(query).build().getListParsed().stream().map(PerBindBriefDTO::getPerCode).collect(Collectors.toList());
        List<String> roleList = userRoleRepository.findAllByUserCode(query.getBindCode()).stream().map(UserRole::getRoleCode).collect(Collectors.toList());
        PerBindQuery newQuery = new PerBindQuery();
        newQuery.setBizCode(query.getBizCode());
        newQuery.setTypeCode(query.getTypeCode());
        newQuery.setBindType("role");
        newQuery.setBindCodeList(roleList);
        List<String> rolePerList = perBindDao.helpBrief(newQuery).build().getListParsed().stream().map(PerBindBriefDTO::getPerCode).collect(Collectors.toList());
        List<String> perList = new ArrayList<>(userPerList);
        perList.addAll(rolePerList);
        return perList.stream().distinct().collect(Collectors.toList());
    }

    public List<String> filterByLv(List<String> userPerList, @Nullable Integer lv) {
        if (lv == null || lv <= 0) {
            return userPerList;
        }
        return userPerList.stream().filter(per -> {
            PerValueTreeDTO perValueTreeDTO = codes.get(per);
            return perValueTreeDTO != null && com.google.common.base.Objects.equal(perValueTreeDTO.getLv(), lv);
        }).collect(Collectors.toList());
    }

    public List<String> getNames(List<String> userPerList) {
        return userPerList.stream().map(per -> {
            PerValueTreeDTO perValueTreeDTO = codes.get(per);
            return perValueTreeDTO != null ? perValueTreeDTO.getNode().getName() : null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
