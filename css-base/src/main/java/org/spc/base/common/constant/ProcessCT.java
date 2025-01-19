package org.spc.base.common.constant;

import java.util.concurrent.atomic.AtomicInteger;

public interface ProcessCT {

    /**
     * AtomicInteger PID 用于生成进程的唯一标识
     */
    AtomicInteger PID = new AtomicInteger(1);

    /**
     * 默认队列长度
     */
    int Queue_LENGTH_DEFAULT = 10;

    /**
     * CPU 电量 (运行时间)
     */
    int CPU_POWER = 114514;
}
