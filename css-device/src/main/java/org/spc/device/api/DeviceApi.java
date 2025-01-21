package org.spc.device.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.entity.device.Device;
import org.spc.base.entity.process.Process;
import org.spc.device.compo.DeviceManageCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 设备Controller
 */
@Slf4j
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceApi {

    @Autowired
    DeviceManageCompo deviceManageCompo;


    /**
     * 把进程使用到设备
     */
    @PostMapping("/putProcessUse2Device")
    void putProcessUse2Device(String target, Process process, long time) throws InterruptedException {
        deviceManageCompo.putProcessUse2Device(target, process, time);
    }


    /**
     * 给出设备
     */
    @PostMapping("/giveDevices")
    HashMap<String, Device> giveDevices() {
        return deviceManageCompo.giveDevices();
    }
}
