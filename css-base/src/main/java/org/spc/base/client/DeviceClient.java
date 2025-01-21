package org.spc.base.client;

import org.spc.base.entity.process.Process;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "device", url = "http://localhost:11481")
public interface DeviceClient {

    /**
     * 将进程使用到设备
     */
    @PostMapping("/putProcessUse2Device")
    void putProcessUse2Device(String target, Process process, long time);

}
