package org.spc.base.common.util;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.app.BaseApp;

/**
 * 应用加载器
 */
@Slf4j
public class AppLoader {

    /**
     * 根据传入的App实例, 调用对应AppList描述里面的initial()方法
     */
    public static void loadByClass(Class<? extends BaseApp> clazz) {
        BaseApp app = SpringContextUtil.getBean(clazz);
        app.initial();
    }
}
