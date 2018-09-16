package org.sjw.sjwrpc.server.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shijiawei
 * @version SjwRpc.java, v 0.1
 * @date 2018/9/3
 * sjw 远程服务注解
 *
 */
@Target({ElementType.TYPE})
@Service
@Retention(RetentionPolicy.RUNTIME)
public @interface SjwRpc {
    Class<?> value();
}
