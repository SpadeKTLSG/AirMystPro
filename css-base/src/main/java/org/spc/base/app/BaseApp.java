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
    @Autowired
    private List<BaseCompo> compoGroup;
    /**
     * 核心组件
     */
    @Autowired
    private CoreCompo coreCompo;
    /**
     * 动态配置器
     */
    @Autowired
    private IHamamap<String, String> dynamicConfiger;


    //! BaseApp Functions

    /**
     * 初始化
     */
    public void initial(Class<?> clazz, Object instance) {
        log.debug("Class: {} Instance: {} Group: {} is loading", clazz, instance, compoGroup);
        loadCompo(clazz, instance);
        loadConfig();
    }

    /**
     * 初始化1 加载组件
     */
    public abstract void loadCompo(Class<?> clazz, Object instance);

    /**
     * 初始化2 加载配置
     */
    public abstract void loadConfig();
}
