package org.spc.process.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.app.BaseApp;
import org.spc.process.compo.IOHandlerCompo;
import org.spc.process.compo.ProcessSchedulerCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CPUApp extends BaseApp {

    @Autowired
    ProcessSchedulerCompo processScheduling;

    @Autowired
    IOHandlerCompo ioHandlerCompo;

    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }

    //! Flow

    /**
     * CPU 运行任务(线程)
     */
    @Transactional
    public void CPU() throws IOException, InterruptedException {


        synchronized (this) { //模拟单线程CPU


        }
    }

}
