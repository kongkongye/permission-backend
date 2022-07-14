package com.kongkongye.backend.permission.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.dto.dept.DeptTreeDTO;
import com.kongkongye.backend.permission.entity.dept.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeptService extends MyBaseService implements InitializingBean {
    private Map<String, DeptTreeDTO> depts = new HashMap<>();
    private List<DeptTreeDTO> trees = new ArrayList<>();

    public void refreshTree() {
        //depts
        for (Dept dept : deptRepository.findAll()) {
            depts.put(dept.getCode(), new DeptTreeDTO(dept));
        }
        //trees
        for (Map.Entry<String, DeptTreeDTO> entry : depts.entrySet()) {
            DeptTreeDTO value = entry.getValue();
            if (!Strings.isNullOrEmpty(value.getNode().getParent())) {
                DeptTreeDTO parent = depts.get(value.getNode().getParent());
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
        return getDeptsRecursive(deptCodes).stream().map(DeptTreeDTO::getNode).map(Dept::getCode).collect(Collectors.toList());
    }

    /**
     * 获取所有的部门树节点，包括子部门
     */
    public List<DeptTreeDTO> getDeptsRecursive(@Nullable List<String> deptCodes) {
        if (deptCodes == null || deptCodes.isEmpty()) {
            return new ArrayList<>();
        }

        //本身
        List<DeptTreeDTO> result = deptCodes.stream().map(e -> depts.get(e)).filter(Objects::nonNull).collect(Collectors.toList());

        //children
        List<String> childrenDeptCodes = new ArrayList<>();
        for (DeptTreeDTO parent : new ArrayList<>(result)) {
            if (parent.getChildren() != null) {
                childrenDeptCodes.addAll(parent.getChildren().stream().map(e -> e.getNode().getCode()).collect(Collectors.toList()));
            }
        }
        result.addAll(getDeptsRecursive(childrenDeptCodes));

        return result;
    }

    public Dept save(Dept dept) {
        dept = deptDao.save(dept, "code");
        refreshTree();
        return dept;
    }

    public Dept del(Long id) {
        Dept dept = deptRepository.findById(id).orElse(null);
        if (dept != null) {
            Preconditions.checkArgument(Strings.isNullOrEmpty(dept.getCode()) || deptRepository.countByParent(dept.getCode()) == 0, "该部门下还有子部门，不能删除");
        }
        //删除
        deptDao.del(dept);
        refreshTree();
        return dept;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshTree();
    }
}
