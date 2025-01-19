package org.spc.base.sys.load.impl;

import lombok.extern.slf4j.Slf4j;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.sys.load.Loader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ArtifactLoader implements Loader<BaseArtifact> {


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
                        log.error("load error", e); //todo polish!
                    }
                });
        artifactGroup.forEach(BaseArtifact::initial);

        return artifactGroup;

    }
}
