package org.sjw.sjwrpc.client.pojo;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author shijiawei
 * @version Book.java, v 0.1
 * @date 2018/8/31
 */
@Data
public class Book implements Serializable{

    private static final long serialVersionUID = 1L;

    private int bookId;

    private String bookName;

    private String bookType;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
