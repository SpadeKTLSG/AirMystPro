package org.spc.base.sys.load.impl;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.exception.InitialException;
import org.spc.base.common.handle.ExMes;
import org.spc.base.sys.artifact.BaseArtifact;
import org.spc.base.sys.load.Loader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 应用使用的工件加载器
 */
@Component
@Slf4j
public class ArtifactLoader implements Loader<BaseArtifact> {

    /**
     * 加载组件
     *
     * @note 通过反射寻找BaseArtifact的子类字段, 并将其添加到artifactGroup中
     */
    @Override
    @Bean
    public List<BaseArtifact> load(Class<?> clazz, Object instance) {
        List<BaseArtifact> artifactGroup = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> BaseArtifact.class.isAssignableFrom(field.getType()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        BaseArtifact artifact = (BaseArtifact) field.get(instance);
                        if (artifact != null) {
                            artifactGroup.add(artifact);
                        }
                    } catch (IllegalAccessException e) {
                        log.error(ExMes.INITIAL_FAILED, e);
                        throw new InitialException();
                    }
                });
        // 工件加载
        artifactGroup.forEach(BaseArtifact::initial);

        return artifactGroup;

    }
}
