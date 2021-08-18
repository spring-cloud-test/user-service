package com.example.userservice.config;

import com.example.userservice.security.CustomAuthenticationFilter;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 인증 관련
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests().antMatchers("/actuator/**").permitAll();

        // 인증에 대한 요청을 하는 IP에 대해 권한 필터 추가
        http.authorizeRequests()
                .antMatchers("/**").hasIpAddress("172.16.100.248")
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private CustomAuthenticationFilter getAuthenticationFilter() throws Exception {
        return new CustomAuthenticationFilter(authenticationManager(), userService, env);
    }

}
