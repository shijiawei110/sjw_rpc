package org.sjw.sjwrpc.api.controller;

import org.sjw.sjwrpc.api.clientapi.BookClientApi;
import org.sjw.sjwrpc.core.Book;
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
//        String msgraw="sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw issjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is ";
//        String msg=msgraw+System.getProperty("line.separator");

        return "success";
    }

    @GetMapping("/getBook")
    public String getBook() {
        Book book = bookClientApi.getBook(1);
        return book.toString();
    }
}
