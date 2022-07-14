package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
@QueryTable("per_bind")
public class PerBindBriefDTO {
    private String perCode;
}
