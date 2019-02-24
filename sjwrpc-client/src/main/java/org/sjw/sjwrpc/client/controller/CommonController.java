package org.sjw.sjwrpc.client.controller;

import org.sjw.sjwrpc.api.core.Book;
import org.sjw.sjwrpc.client.clientapi.BookClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijiawei
 * @version CommonController.java, v 0.1
 * @date 2018/8/30
 */
@RestController
public class CommonController {


    @Autowired
    private BookClientApi bookClientApi;

    @GetMapping("/hello")
    public String hello() {
        return "hello sjwrpc-api";
    }

    @GetMapping("/sendmsg")
    public String sendMsg() {
        return "success";
    }

    @GetMapping("/getBookOne")
    public String getBookOne() {
        Book book = bookClientApi.getBookOne(1);
        return book.toString();
    }

    @GetMapping("/getBookTwo")
    public String getBookTwo() {
        Book book = bookClientApi.getBookTwo(1);
        return book.toString();
    }
}
