package org.spc.base.compo;

import lombok.extern.slf4j.Slf4j;
import org.spc.api.IHamamap;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.sys.loader.impl.ArtifactLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件基类
 */
@Slf4j
@Service
public abstract class BaseCompo {

    //! Properties
    /**
     * 组件加载器
     */
    @Autowired
    private ArtifactLoader artifactLoader;
    /**
     * 工件组
     */
    private List<BaseArtifact> artifactGroup;
    /**
     * 核心工件
     */
    private BaseArtifact coreArtifact;
    /**
     * 动态配置器
     */
    private IHamamap<String, String> dynamicConfig;

    //! BaseCompo Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        loadArtifact(clazz, instance);
        log.debug("Class: {} Instance: {} ArtifactGroup: {} has loaded", clazz, instance, artifactGroup);
        //todo

    }

    /**
     * 初始化1 加载工件
     */
    public void loadArtifact(Class<?> clazz, Object instance) {
        this.artifactGroup = artifactLoader.load(clazz, instance);
    }


    /**
     * 初始化2 加载配置
     */
    public void loadConfig() {
        //todo 读取配置文件的配置
    }
}
