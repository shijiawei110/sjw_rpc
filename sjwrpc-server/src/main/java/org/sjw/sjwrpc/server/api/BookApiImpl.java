package org.sjw.sjwrpc.server.api;

import org.sjw.sjwrpc.api.api.BookApi;
import org.sjw.sjwrpc.api.core.Book;
import org.sjw.sjwrpc.server.annotation.SjwRpc;



/**
 * @author shijiawei
 * @version BookApiImpl.java, v 0.1
 * @date 2018/9/3
 */
@SjwRpc(BookApi.class)
public class BookApiImpl implements BookApi{
    @Override
    public Book getBookOne(int id){
        Book book=new Book();
        book.setBookId(1);
        book.setBookName("测试之书【1】");
        book.setBookType("测试类型【1】");
        return book;
    }

    @Override
    public Book getBookTwo(int id){
        Book book=new Book();
        book.setBookId(2);
        book.setBookName("测试之书【2】");
        book.setBookType("测试类型【2】");
        return book;
    }
}
