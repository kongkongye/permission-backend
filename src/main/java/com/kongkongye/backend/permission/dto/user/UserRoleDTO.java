package com.kongkongye.backend.permission.dto.user;

import com.kongkongye.backend.permission.entity.user.UserRole;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import com.kongkongye.backend.queryer.dto.annotation.SelParse;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class UserRoleDTO extends UserRole {
    @SelParse(alias = "r", sqlFieldName = "dept_code")
    private String deptCode;
    @SelParse(alias = "r", sqlFieldName = "name")
    private String roleName;
}
