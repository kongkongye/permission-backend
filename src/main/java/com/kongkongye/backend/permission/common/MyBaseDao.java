package com.kongkongye.backend.permission.common;

import com.kongkongye.backend.permission.common.util.Util;
import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.SqlHelper;
import com.kongkongye.backend.queryer.SqlHelperBuilder;
import com.kongkongye.backend.queryer.jdbc.JdbcSqlHelperBuilder;
import com.kongkongye.backend.queryer.query.Query;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;

public class MyBaseDao<T extends Model> extends Base {
    @Autowired
    protected NamedParameterJdbcTemplate template;

    protected CrudRepository<T, Long> repository;

    public MyBaseDao(CrudRepository<T, Long> repository) {
        this.repository = repository;
    }

    public <R> SqlHelperBuilder<R> help(SqlHelper.HelpBuilder helpBuilder, @Nullable Query query, @Nullable Class<R> cls) {
        return new JdbcSqlHelperBuilder<>(template, helpBuilder, query, cls);
    }

    /**
     * 保存（新增或更新）
     *
     * @param ignoreFieldNamesOnUpdate 更新时忽略的字段名称
     */
    public T save(T t, String... ignoreFieldNamesOnUpdate) {
        T old = null;
        if (t.getId() != null) {
            old = repository.findById(t.getId()).orElse(null);
        }
        if (old == null) {//新增
            t.setId(null);
            t.setCreateTime(null);
            t.setCreateUser(null);
            t.setUpdateTime(null);
            t.setUpdateUser(null);
            t.setVersion(null);
            return repository.save(t);
        } else {//更新
            //复制字段（除Model里的字段外）
            String[] ignoreFieldNames_ = ArrayUtils.addAll(ignoreFieldNamesOnUpdate, Model.fieldNames());
            Util.toEqualFields(t, old, ignoreFieldNames_);
            return repository.save(old);
        }
    }

    public void del(T t) {
        if (t != null) {
            repository.delete(t);
        }
    }

    public void del(Long id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }
}
