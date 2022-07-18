package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

/**
 * 权限值(树结构)
 */
@Data
@Table(name = "per_value")
@QueryTable("per_value")
@EqualsAndHashCode(callSuper = true)
public class PerValue extends Model {
    /**
     * @see PerType
     */
    private String typeCode;

    private String code;
    private String name;
    /**
     * parent的typeCode一样
     */
    @Nullable
    private String parent;
    private String note;

    /**
     * 使用另一个PerType作为过滤器
     * 值为PerValue的code
     */
    private String filterCode;
}
