package org.spc.process.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.app.BaseApp;
import org.spc.process.artifact.ProcessListArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CPUApp extends BaseApp {

    @Autowired
    ProcessListArtifact processListArtifact;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }
}
