package com.boot.example.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * com.boot.example.security.WebSecurityConfigure
 *
 * @author lipeng
 * @dateTime 2018/12/14 上午11:49
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfigure implements InitializingBean {

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
                {
                    try {
                        authorize.requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .formLogin()
                                .loginPage("/login")
                                .failureUrl("/login/error")
                                .permitAll()
                                .and()
                                .logout()
                                .logoutSuccessUrl("/login")
                                .permitAll();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customerUserDetailService);
        return provider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }
}
