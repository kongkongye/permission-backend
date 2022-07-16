package com.kongkongye.backend.permission.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.common.RedisCacheManager;
import com.kongkongye.backend.permission.dto.per.PerValueTreeDTO;
import com.kongkongye.backend.permission.entity.per.PerBind;
import com.kongkongye.backend.permission.entity.per.PerValue;
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
    private RedisCacheManager<List<PerValue>> perValueCache;

    public void refreshTree() {
        List<PerValue> data = perValueCache.getData();
        //depts
        for (PerValue value : data) {
            codes.put(value.getCode(), new PerValueTreeDTO(value));
        }
        //trees
        for (Map.Entry<String, PerValueTreeDTO> entry : codes.entrySet()) {
            PerValueTreeDTO value = entry.getValue();
            if (!Strings.isNullOrEmpty(value.getNode().getParent())) {
                PerValueTreeDTO parent = codes.get(value.getNode().getParent());
                if (parent != null) {
                    parent.addChild(value);
                }
            } else {
                trees.add(value);
            }
        }
    }

    /**
     * 获取所有的部门编码，包括子部门
     */
    public List<String> getDeptCodesRecursive(@Nullable List<String> deptCodes) {
        return getDeptsRecursive(deptCodes).stream().map(PerValueTreeDTO::getNode).map(PerValue::getCode).collect(Collectors.toList());
    }

    /**
     * 获取所有的部门树节点，包括子部门
     */
    public List<PerValueTreeDTO> getDeptsRecursive(@Nullable List<String> deptCodes) {
        if (deptCodes == null || deptCodes.isEmpty()) {
            return new ArrayList<>();
        }

        //本身
        List<PerValueTreeDTO> result = deptCodes.stream().map(e -> codes.get(e)).filter(Objects::nonNull).collect(Collectors.toList());

        //children
        List<String> childrenDeptCodes = new ArrayList<>();
        for (PerValueTreeDTO parent : new ArrayList<>(result)) {
            if (parent.getChildren() != null) {
                childrenDeptCodes.addAll(parent.getChildren().stream().map(e -> e.getNode().getCode()).collect(Collectors.toList()));
            }
        }
        result.addAll(getDeptsRecursive(childrenDeptCodes));

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
}
