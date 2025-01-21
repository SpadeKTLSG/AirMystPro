package org.spc.base.common.exception;

import org.spc.base.common.handle.ExMes;

/**
 * 构建异常
 */
public class BuildException extends BaseException {

    public BuildException() {
        super(ExMes.BUILD_FAILED);
    }

    public BuildException(String msg) {
        super(msg);
    }
}
