package com.kongkongye.backend.permission.entity.user;

import com.kongkongye.backend.permission.entity.Model;
import com.kongkongye.backend.queryer.dto.annotation.QueryTable;
import com.kongkongye.backend.queryer.dto.annotation.SelParse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Data
@Table(name = "user")
@QueryTable("user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model {
    private String code;
    private String name;
    @SelParse(enable = false)
    private String password;
    /**
     * @see com.kongkongye.backend.permission.en.PasswordEncoder
     */
    private String passwordEncoder;
    private String note;
    @Nullable
    private Boolean disabled;
}
