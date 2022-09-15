package com.zema.security;

import com.zema.commons.BasePath;
import com.zema.commons.exceptions.AppExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//enable secure annotation
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {


    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Autowired
    RESTAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.POST,BasePath.USER_CONTROLLER_PATH + "/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/v1/internal/users/**").permitAll()
                        .antMatchers(HttpMethod.POST, BasePath.AUTH_CONTROLLER_PATH+"/**").permitAll()
                        .antMatchers( BasePath.ROLE_CONTROLLER_PATH+"/**").permitAll()
                       // .antMatchers("/api/v1/roles").permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        http.addFilterBefore(new AuthorizationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class);

//        active h2 (enable 'pages')
//        http.headers().frameOptions().disable();
        return http.build();
    }

}