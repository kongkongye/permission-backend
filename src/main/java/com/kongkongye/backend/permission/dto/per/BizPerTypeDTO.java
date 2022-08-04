package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.permission.entity.per.BizPerType;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import com.kongkongye.backend.queryer.dto.annotation.SelParse;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class BizPerTypeDTO extends BizPerType {
    @SelParse(alias = "pt", sqlFieldName = "name")
    private String perTypeName;

    @SelParse(alias = "pt", sqlFieldName = "note")
    private String note;

    @SelParse(alias = "pt", sqlFieldName = "filter")
    private String filter;
}
