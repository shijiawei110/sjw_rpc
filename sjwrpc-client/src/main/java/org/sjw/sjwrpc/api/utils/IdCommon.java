package org.sjw.sjwrpc.api.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shijiawei
 * @version IdCommon.java, v 0.1
 * @date 2018/9/3
 */
public class IdCommon {
    public static final AtomicLong REQUEST_ID = new AtomicLong(0);
}
