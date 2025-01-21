package org.spc.device.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.spc.base.entity.device.struct.ProcessDeviceUse;
import org.spc.base.entity.process.Process;
import org.spc.device.artifact.DeviceLoadArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备管理器组件
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


    /**
     * 把进程使用到设备
     */
    public void putProcessUse2Device(String target, Process process, long time) throws InterruptedException {
        deviceLoadArtifact.getDevices().get(target).arrayBlockingQueue.put(new ProcessDeviceUse(process, time));
    }
}
