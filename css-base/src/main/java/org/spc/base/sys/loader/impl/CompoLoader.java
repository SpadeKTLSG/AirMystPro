package org.spc.base.sys.loader.impl;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.spc.base.sys.loader.Loader;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用使用的组件加载器
 */
@Component
@Slf4j
public class CompoLoader implements Loader<BaseCompo> {

    @Override
    public List<BaseCompo> load(Class<?> clazz, Object instance) {
        List<BaseCompo> compoGroup = new ArrayList<>();

        // 通过反射寻找BaseCompo的子类字段, 并将其添加到compoGroup中
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (BaseCompo.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    BaseCompo compo = (BaseCompo) field.get(instance);
                    if (compo != null) {
                        compoGroup.add(compo);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        compoGroup.forEach(BaseCompo::initial);

        return compoGroup;
    }
}
