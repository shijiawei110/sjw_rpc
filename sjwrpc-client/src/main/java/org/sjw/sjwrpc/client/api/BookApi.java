package org.sjw.sjwrpc.client.api;

import org.sjw.sjwrpc.client.pojo.Book;

/**
 * @author shijiawei
 * @version BookApi.java, v 0.1
 * @date 2018/8/31
 */
public interface BookApi {
    Book getBook(int id);
}
