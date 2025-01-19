package org.spc.base.special;


import org.spc.api.IHamamap;
import org.spc.impl.Hamamap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DynamicConfiger {

    @Bean
    public IHamamap<String, String> getDynamicConfig() {
        return new Hamamap<>();
    }
}
