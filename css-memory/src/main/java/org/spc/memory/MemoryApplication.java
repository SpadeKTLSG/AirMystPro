package org.spc.memory;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.app.BaseApp;
import org.spc.base.app.CoreApp;
import org.spc.base.common.util.AppLoader;
import org.spc.base.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "org.spc.base.client", defaultConfiguration = FeignConfig.class)
@EnableAspectJAutoProxy()
public class MemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemoryApplication.class, args);
        log.debug("MemoryService Started...");


        // 登记应用
        List<Class<? extends BaseApp>> baseAppClazzList = List.of(
                CoreApp.class
        );

        // 加载应用
        baseAppClazzList.forEach(AppLoader::loadByClass);

    }
}

