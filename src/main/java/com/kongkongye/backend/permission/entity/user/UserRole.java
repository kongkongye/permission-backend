package com.kongkongye.backend.permission.entity.user;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "user_role")
@QueryTable("user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Model {
    private String userCode;
    private String roleCode;
}
