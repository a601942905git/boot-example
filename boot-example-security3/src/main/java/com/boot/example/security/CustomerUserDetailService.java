package com.boot.example.security;

import com.boot.example.core.model.SystemPermission;
import com.boot.example.core.model.SystemRole;
import com.boot.example.core.model.SystemUser;
import com.boot.example.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.security.CustomerUserDetailService
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午3:08
 */
@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = userService.getSystemByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for (SystemRole role : user.getRoleList()) {
            for (SystemPermission permission : role.getPermissionList()) {
                authorityList.add(new SimpleGrantedAuthority(permission.getUrl()));
            }
        }
        return new User(username, user.getPassword(), authorityList);
    }
}
