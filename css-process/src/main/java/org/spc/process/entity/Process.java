package org.spc.process.entity;

public class Process {

    /**
     * 是否停止
     */
    volatile public boolean stop = false;

    /**
     * 进程控制块
     */
    public Pcb pcb;

}
