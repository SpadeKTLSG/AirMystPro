package org.spc.base.artifact;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 工件基类
 */
@Slf4j
@Component
public abstract class BaseArtifact {

    //! BaseArtifact Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        log.debug("Class: {} Instance: {} is loading", clazz, instance);
        loadConfig();
    }

    /**
     * 初始化2 加载配置
     */
    public abstract void loadConfig();
}
