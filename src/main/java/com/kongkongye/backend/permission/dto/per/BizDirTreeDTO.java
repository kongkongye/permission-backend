package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.permission.entity.per.BizDir;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BizDirTreeDTO {
    private BizDir node;
    private List<BizDirTreeDTO> children = new ArrayList<>();

    public BizDirTreeDTO(BizDir node) {
        this.node = node;
    }

    public void addChild(BizDirTreeDTO value) {
        children.add(value);
    }
}
