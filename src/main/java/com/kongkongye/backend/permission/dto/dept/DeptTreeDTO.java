package com.kongkongye.backend.permission.dto.dept;

import com.kongkongye.backend.permission.entity.dept.Dept;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeptTreeDTO {
    private Dept node;
    private List<DeptTreeDTO> children = new ArrayList<>();

    public DeptTreeDTO(Dept node) {
        this.node = node;
    }

    public void addChild(DeptTreeDTO value) {
        children.add(value);
    }
}
