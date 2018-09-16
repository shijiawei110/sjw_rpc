package org.sjw.sjwrpc.server.api;

import org.sjw.sjwrpc.api.api.BookApi;
import org.sjw.sjwrpc.core.Book;
import org.sjw.sjwrpc.server.annotation.SjwRpc;



/**
 * @author shijiawei
 * @version BookApiImpl.java, v 0.1
 * @date 2018/9/3
 */
@SjwRpc(BookApi.class)
public class BookApiImpl implements BookApi{
    @Override
    public Book getBook(int id){
        Book book=new Book();
        book.setBookId(100);
        book.setBookName("测试之书");
        book.setBookType("测试类型");
        return book;
    }
}
