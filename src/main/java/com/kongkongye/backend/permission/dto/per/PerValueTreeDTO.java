package com.kongkongye.backend.permission.dto.per;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PerValueTreeDTO {
    private PerValueBriefDTO node;
    private List<PerValueTreeDTO> children = new ArrayList<>();

    public PerValueTreeDTO(PerValueBriefDTO node) {
        this.node = node;
    }

    public void addChild(PerValueTreeDTO value) {
        children.add(value);
    }
}
