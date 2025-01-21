package org.spc.file.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.springframework.stereotype.Service;

/**
 * 与前端交互组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class toFrontCompo extends BaseCompo {


    //? Artifacts


    //? Default Methods

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }
}
