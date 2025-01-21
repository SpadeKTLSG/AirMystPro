package org.spc.base.entity.device;


import lombok.Data;
import lombok.experimental.Accessors;
import org.spc.base.entity.device.struct.ProcessDeviceUse;
import org.spc.base.entity.process.struct.Pcb;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 设备类
 */
@Data
@Accessors(chain = true)
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
     * 设备上的阻塞队列
     */
    public ArrayBlockingQueue<ProcessDeviceUse> arrayBlockingQueue = new ArrayBlockingQueue<ProcessDeviceUse>(10);

    public Device(String name) {
        this.name = name;
    }

}
