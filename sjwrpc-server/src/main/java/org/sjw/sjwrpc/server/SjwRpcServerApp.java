package org.sjw.sjwrpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shijiawei
 * @version SjwRpcServerApp.java, v 0.1
 * @date 2018/8/30
 */
@SpringBootApplication
public class SjwRpcServerApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SjwRpcServerApp.class);
        //添加监听事件
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
