/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package org.sjw.sjwrpc.client.exception;

/**
 * @author wujn
 * @version AuthException.java, v 0.1 2018-05-07 22:12 wujn
 */
public class CommonException extends RuntimeException {
    private int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * 重写堆栈填充，不填充错误堆栈信息
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
