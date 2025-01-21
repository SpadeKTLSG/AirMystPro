package org.spc.device.app;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.sys.app.BaseApp;
import org.spc.device.compo.DeviceManageCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * USB设备应用
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class USBApp extends BaseApp {


    //All Compos
    @Autowired
    DeviceManageCompo deviceManageCompo;


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
     * USB 运行
     */
    public void power() throws IOException, InterruptedException {
        //设备模块不需要主动保持运行, 核心在CPU那里
    }
}
