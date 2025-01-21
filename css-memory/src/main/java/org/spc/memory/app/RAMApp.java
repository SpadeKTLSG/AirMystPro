package org.spc.memory.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.sys.app.BaseApp;
import org.spc.memory.compo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * RAM内存应用
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class RAMApp extends BaseApp {

    //All Compos

    @Autowired
    InteractCompo interactCompo;

    @Autowired
    MemoryDisplayCompo memoryDisplayCompo;

    @Autowired
    MemoryUseCompo memoryUseCompo;

    @Autowired
    MemoryCheckerCompo memoryCheckerCompo;

    @Autowired
    MemoryClearCompo memoryClearCompo;


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
     * RAM 运行
     */
    public void power() throws IOException, InterruptedException {
        //内存不需要主动保持运行, 核心在CPU那里
    }
}
