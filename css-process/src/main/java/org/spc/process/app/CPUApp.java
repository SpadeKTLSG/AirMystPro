package org.spc.process.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.app.BaseApp;
import org.spc.base.common.constant.ProcessCT;
import org.spc.process.compo.IOHandlerCompo;
import org.spc.process.compo.ProcessRunnerCompo;
import org.spc.process.compo.ProcessSchedulerCompo;
import org.spc.process.compo.ProcessWorkCompo;
import org.spc.process.entity.Process;
import org.spc.process.entity.struct.Pcb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CPUApp extends BaseApp {

    @Autowired
    ProcessSchedulerCompo processSchedulerCompo;

    @Autowired
    ProcessRunnerCompo processRunnerCompo;

    @Autowired
    ProcessWorkCompo processWorkCompo;

    @Autowired
    IOHandlerCompo ioHandlerCompo;


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
     * CPU 运行任务(线程)
     */

    public void power() throws IOException, InterruptedException {
        Process process = new Process(false, new Pcb());


        synchronized (this) { //单线程CPU, 单个线程对象

            for (int i = 0; i < ProcessCT.CPU_POWER; i++) {
                //如果当前没有正在运行的进程, 则从就绪队列中取出一个进程运行
                if (processSchedulerCompo.getRunningProcessArtifact().getRunningProcess() == null) {
                    processSchedulerCompo.getReadyToRun();
                }
                processWorkCompo.run(process);
            }

        }
    }

}
