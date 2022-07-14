package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

/**
 * 业务对象目录(树结构)
 * <p>
 * (只是用来给业务对象归类，方便查找，跟权限分配无关)
 */
@Data
@Table(name = "biz_dir")
@QueryTable("biz_dir")
@EqualsAndHashCode(callSuper = true)
public class BizDir extends Model {
    private String code;
    private String name;
    @Nullable
    private String parent;
    private String note;
}
