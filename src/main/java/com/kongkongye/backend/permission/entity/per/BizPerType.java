package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "biz_per_type")
@QueryTable("biz_per_type")
@EqualsAndHashCode(callSuper = true)
public class BizPerType extends Model {
    private String bizCode;
    private String perTypeCode;
}
