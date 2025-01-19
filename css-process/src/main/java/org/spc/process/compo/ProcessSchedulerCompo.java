package org.spc.process.compo;

import lombok.RequiredArgsConstructor;
import org.spc.base.compo.BaseCompo;
import org.spc.process.artifact.BlockQueueArtifact;
import org.spc.process.artifact.ReadyQueueArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 进程调度组件
 */
@Service
@RequiredArgsConstructor
public class ProcessSchedulerCompo extends BaseCompo {

    //? Artifacts
    @Autowired
    ReadyQueueArtifact readyQueueArtifact;

    @Autowired
    BlockQueueArtifact blockQueueArtifact;

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


}
