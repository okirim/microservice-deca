package com.zema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)//enable secure annotation
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
                        .antMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

        //.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
//                .exceptionHandling((ex) -> ex
//                        .authenticationEntryPoint(restAuthenticationEntryPoint)
//                );
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll()
//                .anyRequest().authenticated()
//                .and()

//        http.addFilterBefore(new AuthorizationFilter(authenticationManager),
//                UsernamePasswordAuthenticationFilter.class);

        //active h2 (enable 'pages')
        //http.headers().frameOptions().disable();
        return http.build();
    }

}