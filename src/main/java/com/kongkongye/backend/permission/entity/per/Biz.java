package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

/**
 * 业务对象
 */
@Data
@Table(name = "biz")
@QueryTable("biz")
@EqualsAndHashCode(callSuper = true)
public class Biz extends Model {
    /**
     * 业务对象所属目录code
     */
    @Nullable
    private String dirCode;
    private String code;
    private String name;
    private String note;
}
