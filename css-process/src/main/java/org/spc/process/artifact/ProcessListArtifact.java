package org.spc.process.artifact;

import org.spc.base.artifact.BaseArtifact;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessListArtifact extends BaseArtifact {


    /**
     * 进程链表
     */
    private ConcurrentHashMap<Integer, Process> processList;

    /**
     * 初始化进程链表
     */
    public void initial() {
        this.processList = new ConcurrentHashMap<>();
    }

}
