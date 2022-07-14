package com.kongkongye.backend.permission.entity.role;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Data
@Table(name = "role")
@QueryTable("role")
@EqualsAndHashCode(callSuper = true)
public class Role extends Model {
    @Nullable
    private String deptCode;
    private String code;
    private String name;
    private String note;
}
