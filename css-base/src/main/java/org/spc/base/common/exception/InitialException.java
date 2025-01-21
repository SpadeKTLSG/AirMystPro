package org.spc.base.common.exception;

import org.spc.base.common.handle.ExMes;

/**
 * 初始化异常
 */
public class InitialException extends BaseException {

    public InitialException() {
        super(ExMes.INITIAL_FAILED);
    }

    public InitialException(String msg) {
        super(msg);
    }
}
