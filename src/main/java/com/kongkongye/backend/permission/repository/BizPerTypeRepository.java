package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.per.BizPerType;
import com.kongkongye.backend.queryer.query.annotation.AutoQuery;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BizPerTypeRepository extends PagingAndSortingRepository<BizPerType,Long> {
    BizPerType findByBizCodeAndPerTypeCode(String bizCode, String perTypeCode);
}
