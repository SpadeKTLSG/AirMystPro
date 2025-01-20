package org.spc.process.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.common.constant.ProcessCT;
import org.spc.base.entity.process.Process;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 就绪队列工件
 */

@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ReadyQueueArtifact extends BaseArtifact {

    /**
     * 就绪队列
     */
    volatile ArrayBlockingQueue<Process> arrayBlockingQueue;


    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
        arrayBlockingQueue = new ArrayBlockingQueue<>(ProcessCT.Queue_LENGTH_DEFAULT);
    }

    @Override
    public void loadConfig() {

    }
}
