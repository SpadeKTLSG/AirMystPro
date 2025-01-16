package org.spc.base.compo;

import org.spc.base.artifact.BaseArtifact;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseCompo {


    BaseArtifact baseArtifact;

    public void initialArtifact() {
        baseArtifact.initial();
    }

    public void initial() {
    }
}
