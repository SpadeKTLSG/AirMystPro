package org.spc.base.sys.loader.impl;

import org.spc.base.artifact.BaseArtifact;
import org.spc.base.sys.loader.Loader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtifactLoader implements Loader<BaseArtifact> {


    @Override
    public List<BaseArtifact> load(Class<?> clazz, Object instance) {
        return null;
    }
}
