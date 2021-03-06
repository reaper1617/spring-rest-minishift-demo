package com.nexign.restdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/ready").permitAll()
                .mvcMatchers("/**").authenticated();
        http.formLogin().and().httpBasic();
    }
}
