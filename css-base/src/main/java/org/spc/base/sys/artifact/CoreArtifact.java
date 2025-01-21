package org.spc.base.sys.artifact;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 核心工件
 */
@Slf4j
@Service
public class CoreArtifact extends BaseArtifact {

    /**
     * 具体工件初始化方法
     */
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    /**
     * 自定义加载配制处理
     */
    @Override
    public void loadConfig() {
    }
}
