package org.spc.file.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.app.BaseApp;
import org.spc.base.entity.file.disk;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 磁盘应用
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class DiskSyS extends BaseApp {

    /**
     * 磁盘
     */
    public disk disk;

    //All Compos


    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        //通电
        try {
            this.power();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }


    //! Flow

    /**
     * DiskSyS通电
     */
    public void power() throws IOException, InterruptedException {
        //磁盘不需要主动保持运行, 核心在CPU那里
    }
}
