package org.sjw.sjwrpc.api.api;

import org.sjw.sjwrpc.api.core.Book;


/**
 * @author shijiawei
 * @version BookApi.java, v 0.1
 * @date 2018/8/31
 */
public interface BookApi {
    Book getBookOne(int id);

    Book getBookTwo(int id);
}
