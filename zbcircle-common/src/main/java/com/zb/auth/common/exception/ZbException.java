

package com.zb.auth.common.exception;

public class ZbException extends RuntimeException {
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public ZbException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new ZbException(commonError.getErrMessage());
    }

    public static void cast(String errMessage) {
        throw new ZbException(errMessage);
    }
}
