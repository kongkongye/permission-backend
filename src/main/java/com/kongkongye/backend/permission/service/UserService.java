package com.kongkongye.backend.permission.service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kongkongye.backend.permission.common.MyBaseService;
import com.kongkongye.backend.permission.entity.user.User;
import com.kongkongye.backend.permission.entity.user.UserDept;
import com.kongkongye.backend.permission.entity.user.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService extends MyBaseService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (user.getId() == null) {//新增
            Preconditions.checkArgument(userRepository.findByName(user.getName()) == null, "用户名已存在");
            if (!Strings.isNullOrEmpty(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setPasswordEncoder(com.kongkongye.backend.permission.en.PasswordEncoder.plain.name());
            }
            user = userDao.save(user, "code", "disabled");
        } else {//修改
            User oldUser = userRepository.findByName(user.getName());
            if (oldUser != null && !Objects.equal(oldUser.getId(), user.getId())) {
                throw new IllegalArgumentException("用户名已存在");
            }

            if (!Strings.isNullOrEmpty(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setPasswordEncoder(com.kongkongye.backend.permission.en.PasswordEncoder.plain.name());
                user = userDao.save(user, "code", "disabled");
            } else {
                //不修改密码
                user = userDao.save(user, "code", "password", "disabled");
            }
        }
        return user;
    }

    public void doDisable(long userId, boolean disable) {
        User user = checkGetUser(userId);
        if (Objects.equal(user.getDisabled(), disable)) {
            return;
        }
        user.setDisabled(disable);
        userDao.save(user);
    }

    public void setUserDepts(String userCode, List<String> deptCodes) {
        List<UserDept> userDepts = userDeptRepository.findAllByUserCode(userCode);
        for (UserDept userDept : userDepts) {
            if (deptCodes.contains(userDept.getDeptCode())) {
                deptCodes.remove(userDept.getDeptCode());//无需再新增
            } else {//多余
                userDeptRepository.delete(userDept);
            }
        }
        //新增
        for (String deptCode : deptCodes) {
            UserDept userDept = new UserDept();
            userDept.setUserCode(userCode);
            userDept.setDeptCode(deptCode);
            userDeptDao.save(userDept);
        }
    }

    public void addUserRole(String userCode, String roleCode) {
        UserRole userRole = userRoleRepository.findByUserCodeAndRoleCode(userCode, roleCode);
        Preconditions.checkArgument(userRole == null, "用户已存在该角色: " + roleCode);

        userRole = new UserRole();
        userRole.setUserCode(userCode);
        userRole.setRoleCode(roleCode);
        userRoleDao.save(userRole);
    }

    public void delUserRole(String userCode, String roleCode) {
        UserRole userRole = userRoleRepository.findByUserCodeAndRoleCode(userCode, roleCode);
        Preconditions.checkArgument(userRole != null, "用户不存在该角色: " + roleCode);

        userRoleDao.del(userRole);
    }

    private User checkGetUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Preconditions.checkNotNull(user, "指定ID的用户不存在：" + userId);
        return user;
    }
}
