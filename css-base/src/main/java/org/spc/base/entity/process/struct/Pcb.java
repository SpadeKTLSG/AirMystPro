package org.spc.base.entity.process.struct;


import lombok.Data;
import lombok.experimental.Accessors;
import org.spc.base.common.constant.ProcessCT;

import java.util.HashMap;

/**
 * 进程控制块
 */
@Data
@Accessors(chain = true)
public class Pcb {

    /**
     * 进程标识符
     */
    private int pcbId;

    /**
     * 进程状态：
     * 0：就绪态
     * 1：运行态
     * 2：阻塞态
     * 3：终止
     */
    private volatile int state;

    /**
     * 寄存器
     */
    private HashMap<String, Integer> register;

    /**
     * 运行哪条语句
     */
    private String targetLine;

    /**
     * 阻塞原因
     */
    private String blockReason;


    public Pcb() {
        this.pcbId = ProcessCT.PID.getAndIncrement(); //获取原子进程标识符
        this.state = 0;
        register = new HashMap<>();
        this.targetLine = null;
    }


}
