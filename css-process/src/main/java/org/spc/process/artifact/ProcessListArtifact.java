package org.spc.process.artifact;

import org.spc.base.artifact.BaseArtifact;
import org.spc.base.common.constant.ProcessCT;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 进程链表工件
 */
@Service
public class ProcessListArtifact extends BaseArtifact {

    /**
     * 进程链表
     */
    ConcurrentHashMap<Integer, Process> processList;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
        processList = new ConcurrentHashMap<>(ProcessCT.Queue_LENGTH_DEFAULT);
    }

    @Override
    public void loadConfig() {

    }

}
