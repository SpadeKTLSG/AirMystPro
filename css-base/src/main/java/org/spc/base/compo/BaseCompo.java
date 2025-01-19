package org.spc.base.compo;

import lombok.extern.slf4j.Slf4j;
import org.spc.api.IHamamap;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.artifact.CoreArtifact;
import org.spc.base.sys.load.impl.ArtifactLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件基类
 */
@Slf4j
@Service
public abstract class BaseCompo {

    //? Artifacts
    /**
     * 核心工件
     */
    @Autowired
    CoreArtifact coreArtifact;


    //! Properties
    /**
     * 工件加载器
     */
    @Autowired
    ArtifactLoader artifactLoader;
    /**
     * 工件组
     */
    @Autowired
    List<BaseArtifact> artifactGroup;
    /**
     * 动态配置器
     */
    @Autowired
    IHamamap<String, String> dynamicConfiger;

    //! BaseCompo Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        log.debug("Class: {} Instance: {} Group: {} is loading", clazz, instance, artifactGroup);
        loadArtifact(clazz, instance);
        loadConfig();
    }

    /**
     * 初始化0 子类实现
     */
    public abstract void initial();

    /**
     * 初始化1 加载工件
     */
    public abstract void loadArtifact(Class<?> clazz, Object instance);

    /**
     * 初始化2 加载配置
     */
    public abstract void loadConfig();
}
