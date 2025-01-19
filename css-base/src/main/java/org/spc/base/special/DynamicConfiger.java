package org.spc.base.special;


import org.spc.api.IHamamap;
import org.spc.impl.Hamamap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 动态配置器
 */
@Component
public class DynamicConfiger {

    /**
     * 动态配置器, 用于读取KV配置文件
     */
    @Bean
    public IHamamap<String, String> getDynamicConfig() {
        return new Hamamap<>();
    }
}
