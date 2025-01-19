package org.spc.process.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.spc.base.common.constant.ProcessCT;

import java.util.HashMap;

/**
 * 进程控制块
 */
@Data
@Builder
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
    private String lines;
    /**
     * 阻塞原因
     */
    private String blockageCause;

    Pcb() {
        this.pcbId = ProcessCT.PID.getAndIncrement(); //获取进程标识符
        this.state = 0;
        register = new HashMap<String, Integer>();
        this.lines = null;
    }


}
