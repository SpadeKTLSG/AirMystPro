package org.spc.base.app;

import org.spc.base.artifact.CoreArtifact;
import org.spc.base.compo.BaseCompo;
import org.spc.base.sys.loader.impl.CompoLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private CoreArtifact coreArtifact;

    //! BaseApp Functions

    /**
     * 加载组件
     */
    public void loadCompo(Class<?> clazz, Object instance) {
        this.compoGroup = compoLoader.load(clazz, instance);
    }

}
