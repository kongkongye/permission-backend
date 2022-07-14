package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.permission.entity.per.PerValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PerValueTreeDTO {
    private PerValue node;
    private List<PerValueTreeDTO> children = new ArrayList<>();

    public PerValueTreeDTO(PerValue node) {
        this.node = node;
    }

    public void addChild(PerValueTreeDTO value) {
        children.add(value);
    }
}
