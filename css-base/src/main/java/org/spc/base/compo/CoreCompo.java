package org.spc.base.compo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoreCompo extends BaseCompo {

    @Override
    public void initial(Class<?> clazz, Object instance) {
        super.initial(clazz, instance);
    }

    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {
        super.loadArtifact(clazz, instance);
    }

    @Override
    public void loadConfig() {
        super.loadConfig();
    }
}
