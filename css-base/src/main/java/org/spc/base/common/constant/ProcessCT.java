package org.spc.base.common.constant;

import java.util.concurrent.atomic.AtomicInteger;

public interface ProcessCT {

    /**
     * AtomicInteger PID 用于生成进程的唯一标识
     */
    AtomicInteger PID = new AtomicInteger(1);
}
