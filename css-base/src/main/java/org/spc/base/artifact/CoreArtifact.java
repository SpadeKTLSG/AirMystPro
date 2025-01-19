package org.spc.base.artifact;

/**
 * 核心工件
 */
public class CoreArtifact extends BaseArtifact {

    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadConfig() {

    }
}
