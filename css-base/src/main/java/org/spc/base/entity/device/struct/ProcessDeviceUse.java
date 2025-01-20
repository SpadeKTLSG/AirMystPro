package org.spc.base.entity.device.struct;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 进程 - 设备使用
 */
@Data
@Builder
@Accessors(chain = true)
public class ProcessDeviceUse {


    /**
     * 对应处理进程
     */
    public Process process;

    /**
     * 需要使用时间
     */
    public long longTime;


    public ProcessDeviceUse(Process process, long l) {
        this.process = process;
        longTime = l;
    }
}
