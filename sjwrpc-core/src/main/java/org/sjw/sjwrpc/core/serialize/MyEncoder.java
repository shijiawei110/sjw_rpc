package org.sjw.sjwrpc.core.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.core.utils.ProtoStuffUtil;

/**
 * @author shijiawei
 * @version MyEncoder.java, v 0.1
 * @date 2018/9/3
 * 自定义netty序列化
 * @ChannelHandler.Sharable 注解让这个handler不用再次new就可以加入再次加入pip 不然如果没有注解 再次加入pip就会报错
 */
@ChannelHandler.Sharable
@Slf4j
public class MyEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    public MyEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = ProtoStuffUtil.writeObject(in);
            //int是32位也就是 32/8 = 4字节, 所有我们在解码的时候加一个 tcp拆包 LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0)
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

}
