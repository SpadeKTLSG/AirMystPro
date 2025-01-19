package org.spc.base.app;

import lombok.extern.slf4j.Slf4j;
import org.spc.api.IHamamap;
import org.spc.base.compo.BaseCompo;
import org.spc.base.compo.CoreCompo;
import org.spc.base.sys.loader.impl.CompoLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用基类
 */
@Slf4j
@Service
public abstract class BaseApp {

    //! Properties
    /**
     * 组件加载器
     */
    @Autowired
    private CompoLoader compoLoader;

    /**
     * 组件组
     */
    private List<BaseCompo> compoGroup;
    /**
     * 核心组件
     */
    private CoreCompo coreCompo;
    /**
     * 动态配置器
     */
    private IHamamap<String, String> dynamicConfig;


    //! BaseApp Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        loadCompo(clazz, instance);
        log.debug("Class: {} Instance: {} ArtifactGroup: {} has loaded", clazz, instance, compoGroup);
        //todo

    }

    /**
     * 初始化1 加载组件
     */
    public void loadCompo(Class<?> clazz, Object instance) {
        this.compoGroup = compoLoader.load(clazz, instance);
    }

    /**
     * 初始化2 加载配置
     */
    public void loadConfig() {
        //todo 读取配置文件的配置
    }
}
