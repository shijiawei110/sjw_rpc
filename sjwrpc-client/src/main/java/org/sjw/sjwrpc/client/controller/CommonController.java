package org.sjw.sjwrpc.client.controller;

import org.sjw.sjwrpc.client.clientapi.BookClientApi;
import org.sjw.sjwrpc.client.netty.ClientServer;
import org.sjw.sjwrpc.client.pojo.Book;
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
    private ClientServer clientServer;

    @Autowired
    private BookClientApi bookClientApi;

    @GetMapping("/hello")
    public String hello(){
        return "hello sjwrpc-client";
    }

    @GetMapping("/sendmsg")
    public String sendMsg(){
        String msgraw="sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is sjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw issjw issjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is a good boy，sjw is ";
        String msg=msgraw+System.getProperty("line.separator");
        clientServer.getClientHandler().sendRequest(msg);
        return "success";
    }

    @GetMapping("/getBook")
    public String getBook(){
       Book book= bookClientApi.getBook(1);
       return book.toString();
    }
}
