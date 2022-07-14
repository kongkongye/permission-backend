package com.kongkongye.backend.permission.entity.dept;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Data
@Table(name = "dept")
@QueryTable("dept")
@EqualsAndHashCode(callSuper = true)
public class Dept extends Model {
    private String code;
    private String name;
    @Nullable
    private String parent;
    private String note;
}
