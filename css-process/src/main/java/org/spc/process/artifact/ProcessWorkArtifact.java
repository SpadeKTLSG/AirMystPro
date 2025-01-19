package org.spc.process.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.artifact.BaseArtifact;
import org.spc.process.compo.ProcessRunnerCompo;
import org.spc.process.entity.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 进程(本身)执行工件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessWorkArtifact extends BaseArtifact {

    @Autowired
    ProcessRunnerCompo processRunnerCompo;
    @Autowired
    ReadyQueueArtifact readyQueueArtifact;
    @Autowired
    ProcessListArtifact processListArtifact;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadConfig() {

    }


    /**
     * 进程本身操作
     */
    public void run(Process process) throws InterruptedException {

        synchronized (this) {
            readyQueueArtifact.getArrayBlockingQueue().add(process); //登记就绪队列
            processListArtifact.getProcessList().put(process.getPcb().getPcbId(), process); //登记进程链表
            process.wait();
        }

        while (!process.isStop()) { //持续占用CPU
            processRunnerCompo.runProcess(process);
        }

        //释放CPU, 清理内存 todo
//            MemoryManager.releaseMemory(pcb.pcbId);
    }

}
