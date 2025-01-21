package org.spc.process.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.entity.process.Process;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.process.artifact.BlockQueueArtifact;
import org.spc.process.artifact.ReadyQueueArtifact;
import org.spc.process.artifact.RunningProcessArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 进程调度组件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessSchedulerCompo extends BaseCompo {

    //? Artifacts

    @Autowired
    ReadyQueueArtifact readyQueueArtifact;

    @Autowired
    BlockQueueArtifact blockQueueArtifact;

    @Autowired
    RunningProcessArtifact runningProcessArtifact;

    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }

    //! Func

    /**
     * 进程调度: 从就绪队列中取出一个进程运行
     */
    @Transactional
    public void getReadyToRun() throws InterruptedException {

        Process process = readyQueueArtifact.getArrayBlockingQueue().take(); //从就绪队列中取出一个进程
        runningProcessArtifact.setRunningProcess(process); //标记为正在运行的进程

        process.getPcb().setState(1);//设置进程状态为运行态
        synchronized (process) {
            process.notifyAll(); //唤醒进程
        }
    }


}
