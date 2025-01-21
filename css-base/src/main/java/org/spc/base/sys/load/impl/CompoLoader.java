package org.spc.base.sys.load.impl;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.exception.InitialException;
import org.spc.base.common.handle.ExMes;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.base.sys.load.Loader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 应用使用的组件加载器
 */
@Component
@Slf4j
public class CompoLoader implements Loader<BaseCompo> {

    /**
     * 加载组件
     *
     * @note 通过反射寻找BaseCompo的子类字段, 并将其添加到compoGroup中
     */
    @Override
    @Bean
    public List<BaseCompo> load(Class<?> clazz, Object instance) {
        List<BaseCompo> compoGroup = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> BaseCompo.class.isAssignableFrom(field.getType()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        BaseCompo compo = (BaseCompo) field.get(instance);
                        if (compo != null) {
                            compoGroup.add(compo);
                        }
                    } catch (IllegalAccessException e) {
                        log.error(ExMes.INITIAL_FAILED, e);
                        throw new InitialException();
                    }
                });
        // 组件加载
        compoGroup.forEach(BaseCompo::initial);

        return compoGroup;
    }
}
