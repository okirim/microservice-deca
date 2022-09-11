package com.zema.eurekaserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerDiscovery {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServerDiscovery.class, args);
    }
}
