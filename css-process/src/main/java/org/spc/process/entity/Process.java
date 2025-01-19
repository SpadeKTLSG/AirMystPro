package org.spc.process.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 进程实体
 */
@Data
@Builder
@Accessors(chain = true)
public class Process {

    /**
     * 是否停止
     * volatile 保证可见性
     */
    private volatile boolean stop;

    /**
     * 进程控制块
     */
    private Pcb pcb;

}
