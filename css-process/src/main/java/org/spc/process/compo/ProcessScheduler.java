package org.spc.process.compo;

import lombok.RequiredArgsConstructor;
import org.spc.base.compo.BaseCompo;
import org.spc.process.artifact.ProcessListArtifact;
import org.spc.process.artifact.QueueArtifact;
import org.springframework.stereotype.Component;

@Component()
@RequiredArgsConstructor
public class ProcessScheduler extends BaseCompo {

    private ProcessListArtifact processList;
    private QueueArtifact queueArtifact;
//
//    public void initialArtifact(Process process) {
//        processList.setProcessList();
//    }
}
