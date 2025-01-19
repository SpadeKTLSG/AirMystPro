package org.spc.base.artifact;

import lombok.extern.slf4j.Slf4j;
import org.spc.api.IHamamap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工件基类
 */
@Slf4j
@Service
public abstract class BaseArtifact {

    //! Properties

    /**
     * 动态配置器
     */
    @Autowired
    IHamamap<String, String> dynamicConfiger;


    //! BaseArtifact Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        log.debug("Class: {} Instance: {} is loading", clazz, instance);
        loadConfig();
    }

    /**
     * 初始化0 子类实现
     */
    public abstract void initial();

    /**
     * 初始化2 加载配置
     */
    public abstract void loadConfig();
}
