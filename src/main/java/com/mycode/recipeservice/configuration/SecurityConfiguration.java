package com.mycode.recipeservice.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/recipes").permitAll()
                .antMatchers(HttpMethod.PUT, "/recipes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/recipes").permitAll()
                .antMatchers(HttpMethod.DELETE, "/recipes/**").permitAll()
                .and()
                .csrf().disable();
    }
}

