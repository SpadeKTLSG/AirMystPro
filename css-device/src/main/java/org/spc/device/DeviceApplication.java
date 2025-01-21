package org.spc.device;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.util.AppLoader;
import org.spc.base.config.FeignConfig;
import org.spc.base.sys.app.BaseApp;
import org.spc.base.sys.app.CoreApp;
import org.spc.device.app.USBApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "org.spc.base.client", defaultConfiguration = FeignConfig.class)
@EnableAspectJAutoProxy()
public class DeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceApplication.class, args);
        log.debug("DeviceService Started...");


        // 登记应用
        List<Class<? extends BaseApp>> baseAppClazzList = List.of(
                CoreApp.class,
                USBApp.class
        );

        // 加载应用
        baseAppClazzList.forEach(AppLoader::loadByClass);
    }
}

