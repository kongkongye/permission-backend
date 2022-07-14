package com.kongkongye.backend.permission.dto.per;

import com.kongkongye.backend.permission.entity.per.Biz;
import com.kongkongye.backend.queryer.dto.annotation.AutoFrom;
import com.kongkongye.backend.queryer.dto.annotation.AutoSel;
import lombok.Data;

@AutoSel
@AutoFrom
@Data
public class BizDTO extends Biz {
}
