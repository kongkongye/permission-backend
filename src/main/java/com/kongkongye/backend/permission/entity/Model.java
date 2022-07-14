package com.kongkongye.backend.permission.entity;

import lombok.Data;
import org.springframework.data.annotation.*;

import java.io.Serializable;

@Data
public class Model implements Serializable {
    @Id
    protected Long id;
    @CreatedBy
    protected Long createUser;
    @CreatedDate
    protected Long createTime;
    @LastModifiedBy
    protected Long updateUser;
    @LastModifiedDate
    protected Long updateTime;
    @Version
    protected Long version;

    public static String[] fieldNames() {
        return new String[]{"id", "createUser", "createTime", "updateUser", "updateTime", "version"};
    }
}
