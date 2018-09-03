package org.sjw.sjwrpc.client.clientapi;

import org.sjw.sjwrpc.client.api.BookApi;
import org.sjw.sjwrpc.client.pojo.Book;
import org.sjw.sjwrpc.client.request.RequestHandlerProxy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author shijiawei
 * @version BookClientApi.java, v 0.1
 * @date 2018/8/31
 */
@Component
public class BookClientApi {


    private BookApi bookApi;

    @PostConstruct
    public void init() {
        RequestHandlerProxy proxy = new RequestHandlerProxy();
        bookApi = proxy.create(BookApi.class.getName(),BookApi.class);
    }

    public Book getBook(int id) {
        Book book = bookApi.getBook(id);
        return book;
    }
}
