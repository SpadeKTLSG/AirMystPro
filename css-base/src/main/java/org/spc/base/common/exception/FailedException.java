package org.spc.base.common.exception;

import static org.spc.base.common.handle.ExMes.NOT_SUPPORT;

public class FailedException extends BaseException {

    public FailedException() {
        super(NOT_SUPPORT);
    }

    public FailedException(String msg) {
        super(msg);
    }
}
