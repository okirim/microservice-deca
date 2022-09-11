package com.zema.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class GatewayApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(GatewayApplication.class, args);
    }



//@Bean
//public AuthenticationConfiguration authenticationManagerBean() throws Exception {
//    return new AuthenticationConfiguration();
//}

}
