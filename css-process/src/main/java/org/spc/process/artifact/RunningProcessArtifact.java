package org.spc.process.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.entity.process.Process;
import org.spc.base.sys.artifact.BaseArtifact;
import org.springframework.stereotype.Service;

/**
 * 运行进程暂存工件
 */

@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class RunningProcessArtifact extends BaseArtifact {

    /**
     * 正在运行的进程
     */
    Process runningProcess;


    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
        runningProcess = null;
    }

    @Override
    public void loadConfig() {

    }
}
