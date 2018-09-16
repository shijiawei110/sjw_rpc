package org.sjw.sjwrpc.core.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.utils.ProtoStuffUtil;

import java.util.List;

/**
 * @author shijiawei
 * @version MyDecoder.java, v 0.1
 * @date 2018/9/3
 * 自定义netty反序列化
 * * @ChannelHandler.Sharable 注解让这个handler不用再次new就可以加入再次加入pip 不然如果没有注解 再次加入pip就会报错
 */
@Slf4j
public class MyDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public MyDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int size = in.readableBytes();
        //出现粘包导致消息头长度不对(tcp粘包)，直接返回  约定了报文头长度为int是4个字节
        if (size < 4) {
            return;
        }
        //标记读索引位置
        in.markReaderIndex();
        //读取报文头(自己定义的长度) 会让读索引前进4位
        int dataLength = in.readInt();
        // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
        if (dataLength < 0) {
            ctx.close();
        }
        if (size < dataLength) {
            //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = ProtoStuffUtil.readObject(data, genericClass);
        out.add(obj);
    }
}
