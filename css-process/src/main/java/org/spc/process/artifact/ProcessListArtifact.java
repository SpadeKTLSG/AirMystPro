package org.spc.process.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.common.constant.ProcessCT;
import org.spc.base.entity.process.Process;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 进程链表工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
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
