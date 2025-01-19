package org.spc.base.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 核心应用
 */
@Slf4j
@Service
public class CoreApp extends BaseApp {

    /**
     * 具体应用初始化方法
     */
    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    /**
     * 自定义加载组件处理
     */
    @Override
    public void loadCompo(Class<?> clazz, Object instance) {
    }

    /**
     * 自定义加载配置处理
     */
    @Override
    public void loadConfig() {
    }
}
