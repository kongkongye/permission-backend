package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.permission.entity.per.PerType;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class PerTypeDTO extends PerType {
}
