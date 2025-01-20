package org.spc.device.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.spc.device.artifact.DeviceLoadArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备管理器
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceManageCompo extends BaseCompo {


    //? Artifacts

    @Autowired
    DeviceLoadArtifact deviceLoadArtifact;

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
