package org.spc.front;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.util.AppLoader;
import org.spc.base.config.FeignConfig;
import org.spc.base.sys.app.BaseApp;
import org.spc.base.sys.app.CoreApp;
import org.spc.front.app.Screen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "org.spc.base.client", defaultConfiguration = FeignConfig.class)
@EnableAspectJAutoProxy()
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
        log.debug("FrontService Started...");


        // 登记应用
        List<Class<? extends BaseApp>> baseAppClazzList = List.of(
                CoreApp.class,
                Screen.class
        );

        // 加载应用
        baseAppClazzList.forEach(AppLoader::loadByClass);

    }
}

