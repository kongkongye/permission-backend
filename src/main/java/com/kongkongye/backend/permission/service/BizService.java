package com.kongkongye.backend.permission.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.common.RedisCacheManager;
import com.kongkongye.backend.permission.dto.per.BizDirTreeDTO;
import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.entity.per.BizPerType;
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
public class BizService extends MyBaseService implements InitializingBean {
    private Map<String, BizDirTreeDTO> codes = new HashMap<>();
    private List<BizDirTreeDTO> trees = new ArrayList<>();

    @Autowired
    @Qualifier("bizDirTreeCacheManager")
    @Getter
    private RedisCacheManager<List<BizDir>> bizDirCache;

    public void refreshTree() {
        //读取缓存数据
        List<BizDir> data = bizDirCache.getData();
        //codeToTrees
        for (BizDir e : data) {
            codes.put(e.getCode(), new BizDirTreeDTO(e));
        }
        //trees
        for (Map.Entry<String, BizDirTreeDTO> entry : codes.entrySet()) {
            BizDirTreeDTO value = entry.getValue();
            if (!Strings.isNullOrEmpty(value.getNode().getParent())) {
                BizDirTreeDTO parent = codes.get(value.getNode().getParent());
                if (parent != null) {
                    parent.addChild(value);
                }
            } else {
                trees.add(value);
            }
        }
    }

    /**
     * 获取所有的编码，包括子
     */
    public List<String> getCodesRecursive(@Nullable List<String> codes) {
        return getRecursive(codes).stream().map(BizDirTreeDTO::getNode).map(BizDir::getCode).collect(Collectors.toList());
    }

    /**
     * 获取所有的树节点，包括子
     */
    public List<BizDirTreeDTO> getRecursive(@Nullable List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new ArrayList<>();
        }

        //本身
        List<BizDirTreeDTO> result = codes.stream().map(e -> this.codes.get(e)).filter(Objects::nonNull).collect(Collectors.toList());

        //children
        List<String> childrenCodes = new ArrayList<>();
        for (BizDirTreeDTO parent : new ArrayList<>(result)) {
            if (parent.getChildren() != null) {
                childrenCodes.addAll(parent.getChildren().stream().map(e -> e.getNode().getCode()).collect(Collectors.toList()));
            }
        }
        result.addAll(getRecursive(childrenCodes));

        return result;
    }

    public BizDir saveBizDir(BizDir entity) {
        entity = bizDirDao.save(entity, "code");
        bizDirCache.refreshCache();
        return entity;
    }

    public Biz saveBiz(Biz entity) {
        return bizDao.save(entity, "code");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        bizDirCache.addUpdateNotifier(this::refreshTree);
        refreshTree();
    }

    public void addBizPerType(String bizCode, String perTypeCode) {
        BizPerType bizPerType = bizPerTypeRepository.findByBizCodeAndPerTypeCode(bizCode, perTypeCode);
        Preconditions.checkArgument(bizPerType == null, "业务对象已存在该可见类型权限: " + bizCode);

        bizPerType = new BizPerType();
        bizPerType.setBizCode(bizCode);
        bizPerType.setPerTypeCode(perTypeCode);
        bizPerTypeDao.save(bizPerType);
    }

    public void delBizPerType(String bizCode, String perTypeCode) {
        BizPerType bizPerType = bizPerTypeRepository.findByBizCodeAndPerTypeCode(bizCode, perTypeCode);
        Preconditions.checkArgument(bizPerType != null, "业务对象不存在该可见类型权限: " + bizCode);
        bizPerTypeDao.del(bizPerType);
    }

}
