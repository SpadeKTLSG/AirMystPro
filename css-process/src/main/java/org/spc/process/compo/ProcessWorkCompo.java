package org.spc.process.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.MemoryClient;
import org.spc.base.compo.BaseCompo;
import org.spc.base.entity.process.Process;
import org.spc.process.artifact.ProcessListArtifact;
import org.spc.process.artifact.ReadyQueueArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 进程(本身)执行工件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessWorkCompo extends BaseCompo {

    @Autowired
    MemoryClient memoryClient;

    @Autowired
    ProcessRunnerCompo processRunnerCompo;

    //? Artifacts

    @Autowired
    ReadyQueueArtifact readyQueueArtifact;

    @Autowired
    ProcessListArtifact processListArtifact;

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


    /**
     * 进程本身运行
     */
    @Transactional
    public void run(Process process) throws InterruptedException, IOException {
        synchronized (this) {
            readyQueueArtifact.getArrayBlockingQueue().add(process); //登记就绪队列
            processListArtifact.getProcessList().put(process.getPcb().getPcbId(), process); //登记进程链表
            process.wait();
        }

        while (!process.isStop()) { //持续占用CPU
            processRunnerCompo.runProcess(process);
        }

        memoryClient.releaseMemory(process.getPcb().getPcbId());
    }

}
