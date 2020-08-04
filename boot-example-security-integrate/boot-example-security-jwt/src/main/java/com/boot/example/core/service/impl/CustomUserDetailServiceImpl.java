package com.boot.example.core.service.impl;

import com.boot.example.core.model.User;
import com.boot.example.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * com.boot.example.core.service.impl.CustomUserDetailServiceImpl
 *
 * @author lipeng
 * @date 2018/12/19 上午9:15
 */
@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == username || Objects.equals(username, "")) {
            throw new UsernameNotFoundException(getUsernameNotFoundExceptionMessage(username));
        }

        User user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(getUsernameNotFoundExceptionMessage(username));
        }

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    private String getUsernameNotFoundExceptionMessage(String username) {
        return String.format("用户名%s不存在", username);
    }
}
