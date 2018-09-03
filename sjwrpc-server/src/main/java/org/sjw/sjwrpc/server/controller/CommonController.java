package org.sjw.sjwrpc.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijiawei
 * @version CommonController.java, v 0.1
 * @date 2018/8/30
 */
@RestController
public class CommonController {

    @GetMapping("/hello")
    public String hello(){
        return "hello sjwrpc-server";
    }
}
