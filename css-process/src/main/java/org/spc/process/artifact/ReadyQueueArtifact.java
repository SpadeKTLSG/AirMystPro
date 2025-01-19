package org.spc.process.artifact;

import lombok.RequiredArgsConstructor;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.common.constant.ProcessCT;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 就绪队列工件
 */
@Service
@RequiredArgsConstructor
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
