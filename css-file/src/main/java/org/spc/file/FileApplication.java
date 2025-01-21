package org.spc.file;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.app.BaseApp;
import org.spc.base.app.CoreApp;
import org.spc.base.common.util.AppLoader;
import org.spc.base.config.FeignConfig;
import org.spc.file.app.DiskSyS;
import org.spc.file.app.FileApp;
import org.spc.file.app.FileSyS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "org.spc.base.client", defaultConfiguration = FeignConfig.class)
@EnableAspectJAutoProxy()
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
        log.debug("FileService Started...");


        // 登记应用
        List<Class<? extends BaseApp>> baseAppClazzList = List.of(
                CoreApp.class,
                DiskSyS.class,
                FileSyS.class,
                FileApp.class
        );

        // 加载应用
        baseAppClazzList.forEach(AppLoader::loadByClass);

    }

    //! 下面是原FileApplication的内容, 因为三级架构的原因, 无法再抽取, 于是放这里:


}

