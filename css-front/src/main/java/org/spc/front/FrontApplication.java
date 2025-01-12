package org.spc.front;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "org.spc.front.client", defaultConfiguration = FeignConfig.class)
@EnableAspectJAutoProxy()
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
        log.debug("FrontService Started...");
    }
}

