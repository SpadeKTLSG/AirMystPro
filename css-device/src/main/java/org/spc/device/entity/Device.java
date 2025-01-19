package org.spc.device.entity;


import css.core.process.Pcb;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 设备类
 */
@Slf4j
public class Device {


    /**
     * 设备名
     */
    public String name;

    /**
     * 当前使用设备的进程
     */
    public Pcb nowProcessPcb = null;

    /**
     * 设备阻塞队列
     */
    public ArrayBlockingQueue<ProcessDeviceUse> arrayBlockingQueue = new ArrayBlockingQueue<ProcessDeviceUse>(10);

    public Device(String name) {
        this.name = name;
        deviceManagement.devices.put(name, this);
    }

}
