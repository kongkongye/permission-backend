package com.kongkongye.backend.permission.dto.role;

import com.kongkongye.backend.permission.entity.role.Role;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class RoleDTO extends Role {
}
