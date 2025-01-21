package org.spc.device.artifact;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.entity.device.Device;
import org.spc.base.sys.artifact.BaseArtifact;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 设备负载工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceLoadArtifact extends BaseArtifact {

    /**
     * HashMap存储的设备
     */
    public HashMap<String, Device> devices;


    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
        devices = new HashMap<>();
    }

    @Override
    public void loadConfig() {

    }
}
