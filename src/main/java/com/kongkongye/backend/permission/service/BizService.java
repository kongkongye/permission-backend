package com.kongkongye.backend.permission.service;

import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.dto.per.BizDirTreeDTO;
import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.permission.entity.per.BizDir;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BizService extends MyBaseService implements InitializingBean {
    private Map<String, BizDirTreeDTO> codeToTrees = new HashMap<>();
    private List<BizDirTreeDTO> trees = new ArrayList<>();

    public void refreshTree() {
        //codeToTrees
        for (BizDir e : bizDirRepository.findAll()) {
            codeToTrees.put(e.getCode(), new BizDirTreeDTO(e));
        }
        //trees
        for (Map.Entry<String, BizDirTreeDTO> entry : codeToTrees.entrySet()) {
            BizDirTreeDTO value = entry.getValue();
            if (!Strings.isNullOrEmpty(value.getNode().getParent())) {
                BizDirTreeDTO parent = codeToTrees.get(value.getNode().getParent());
                if (parent != null) {
                    parent.addChild(value);
                }
            } else {
                trees.add(value);
            }
        }
    }

    /**
     * 获取所有的编码，包括子级
     */
    public List<String> getCodesRecursive(@Nullable List<String> codes) {
        return getRecursive(codes).stream().map(BizDirTreeDTO::getNode).map(BizDir::getCode).collect(Collectors.toList());
    }

    /**
     * 获取所有的树节点，包括子级
     */
    public List<BizDirTreeDTO> getRecursive(@Nullable List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new ArrayList<>();
        }

        //本身
        List<BizDirTreeDTO> result = codes.stream().map(e -> codeToTrees.get(e)).filter(Objects::nonNull).collect(Collectors.toList());

        //children
        List<String> childrenDeptCodes = new ArrayList<>();
        for (BizDirTreeDTO parent : new ArrayList<>(result)) {
            if (parent.getChildren() != null) {
                childrenDeptCodes.addAll(parent.getChildren().stream().map(e -> e.getNode().getCode()).collect(Collectors.toList()));
            }
        }
        result.addAll(getRecursive(childrenDeptCodes));

        return result;
    }

    public BizDir saveBizDir(BizDir entity) {
        entity = bizDirDao.save(entity, "code");
        refreshTree();
        return entity;
    }

    public Biz saveBiz(Biz entity) {
        return bizDao.save(entity, "code");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshTree();
    }
}