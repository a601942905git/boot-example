package com.boot.example.core.service;

import com.boot.example.core.model.SystemPermission;
import com.boot.example.core.model.SystemRole;
import com.boot.example.core.model.SystemUser;
import com.boot.example.core.model.SystemUsernameConstants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * com.boot.example.core.service.UserService
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午2:40
 */
@Service
public class UserService {

    SystemRole adminRole = SystemRole.builder()
            .id(10001)
            .name("admin")
            .build();

    SystemRole developRole = SystemRole.builder()
            .id(10002)
            .name("develop")
            .build();


    {
        SystemPermission systemPermission1 =
                SystemPermission.builder().id(10002).name("查询图书信息").url("/books").build();

        SystemPermission systemPermission2 =
                SystemPermission.builder().id(10003).name("新增图书").url("/books/add").build();

        SystemPermission systemPermission3 =
                SystemPermission.builder().id(10004).name("修改图书").url("/books/update").build();

        SystemPermission systemPermission4 =
                SystemPermission.builder().id(10005).name("删除图书").url("/books/delete").build();

        adminRole.setPermissionList(Arrays.asList(systemPermission1,
                        systemPermission2, systemPermission3, systemPermission4));

        developRole.setPermissionList(Arrays.asList(systemPermission1));
    }

    public SystemUser getSystemByUsername(String username) {
        SystemUser user = null;
        // 管理员账户
        if (Objects.equals(username, SystemUsernameConstants.ADMIN_USER_NAME)) {
            user = SystemUser.builder()
                    .id(10001)
                    .username(username)
                    .password("$2a$10$nqFV2LWYvCckYOtrgm0jKO0PYazuO6tVmGgHleDBVSUjZVSkCl/3e")
                    .build();
            user.setRoleList(Arrays.asList(adminRole, developRole));
        }
        // 开发账户
        else if (Objects.equals(username, SystemUsernameConstants.DEVELOP_USER_NAME)) {
            user = SystemUser.builder()
                    .id(10002)
                    .username(username)
                    .password("$2a$10$nqFV2LWYvCckYOtrgm0jKO0PYazuO6tVmGgHleDBVSUjZVSkCl/3e")
                    .build();
            user.setRoleList(Arrays.asList(developRole));
        }

        return user;
    }
}
