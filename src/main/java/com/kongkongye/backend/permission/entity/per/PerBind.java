package com.kongkongye.backend.permission.entity.per;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

/**
 * 权限绑定
 */
@Data
@Table(name = "per_bind")
@QueryTable("per_bind")
@EqualsAndHashCode(callSuper = true)
public class PerBind extends Model {
    /**
     * 业务对象code
     */
    @Nullable
    private String bizCode;
    /**
     * 绑定类型
     *
     * @see com.kongkongye.backend.permission.en.BindTypeEn
     */
    private String bindType;
    /**
     * 绑定code
     * <p>
     * 部门code/角色code/用户code
     */
    private String bindCode;

    /**
     * @see PerType
     */
    private String typeCode;
    /**
     * 权限值的code
     *
     * @see PerValue
     */
    private String perCode;
}
