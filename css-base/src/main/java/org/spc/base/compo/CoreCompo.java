package org.spc.base.compo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 核心组件
 */
@Slf4j
@Service
public class CoreCompo extends BaseCompo {

    /**
     * 具体组件初始化方法
     */
    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }


    /**
     * 自定义加载工件处理
     */
    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {
    }

    /**
     * 自定义加载配置处理
     */
    @Override
    public void loadConfig() {
    }
}
