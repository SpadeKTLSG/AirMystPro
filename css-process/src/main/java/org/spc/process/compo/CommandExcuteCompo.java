package org.spc.process.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.client.DeviceClient;
import org.spc.base.client.FileClient;
import org.spc.base.entity.device.Device;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.process.artifact.ProcessListArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 命令执行组件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CommandExcuteCompo extends BaseCompo {


    @Autowired
    DeviceClient deviceClient;

    @Autowired
    FileClient fileClient;

    //? Artifacts

    @Autowired
    ProcessListArtifact processListArtifact;

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

    //! Func


    /**
     * 命令执行
     */
    public void commandExecution(String order) {
        String[] s = order.split(" ");


        switch (s[0]) {
            case "$add" -> {
                deviceClient.giveDevices().put(s[1], new Device(s[1]));
            }
            case "$remove" -> {
                deviceClient.giveDevices().remove(s[1]);
            }
            case "stop" -> {
                processListArtifact.getProcessList().get(s[1]).setStop(true);
            }
            default -> {
                fileClient.getFrontRequest(order);
            }
        }

    }
}
