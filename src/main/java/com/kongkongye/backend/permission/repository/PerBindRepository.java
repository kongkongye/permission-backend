package com.kongkongye.backend.permission.repository;

import com.kongkongye.backend.permission.entity.per.PerBind;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PerBindRepository extends PagingAndSortingRepository<PerBind, Long> {
    @Query("select * from per_bind where biz_code=:bizCode and bind_type=:bindType and bind_code=:bindCode and type_code=:typeCode and per_code=:perCode")
    PerBind findWithBiz(String bizCode, String bindType, String bindCode, String typeCode, String perCode);

    @Query("select * from per_bind where biz_code is null and bind_type=:bindType and bind_code=:bindCode and type_code=:typeCode and per_code=:perCode")
    PerBind findWithBizNull(String bindType, String bindCode, String typeCode, String perCode);
}
