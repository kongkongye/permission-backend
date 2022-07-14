package com.kongkongye.backend.permission.dto.user;

import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class UserDTO extends User {
}
