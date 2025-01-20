package org.spc.device.compo;

import org.spc.base.entity.device.Device;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 设备管理器
 */
@Component
public class DeviceManagement {

    /**
     * HashMap存储的设备
     */
    public HashMap<String, Device> devices = new HashMap<String, Device>();


}
