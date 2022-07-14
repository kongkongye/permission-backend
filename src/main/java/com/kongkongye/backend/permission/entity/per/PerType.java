package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 权限类型(如小程序菜单权限等非数据权限，公司，仓库，组织等数据权限)
 */
@Data
@Table(name = "per_type")
@QueryTable("per_type")
@EqualsAndHashCode(callSuper = true)
public class PerType extends Model {
    private String code;
    private String name;
    private String note;
}
