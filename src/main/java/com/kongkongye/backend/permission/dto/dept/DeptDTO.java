package com.kongkongye.backend.permission.dto.dept;

import com.kongkongye.backend.permission.entity.dept.Dept;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class DeptDTO extends Dept {
}
