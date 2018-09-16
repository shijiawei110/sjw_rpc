package org.sjw.sjwrpc.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.pojo.Response;
import org.sjw.sjwrpc.core.serialize.MyDecoder;
import org.sjw.sjwrpc.core.serialize.MyEncoder;

/**
 * @author shijiawei
 * @version ServerChannelHandlers.java, v 0.1
 * @date 2018/8/31
 * server处理管道
 */
public class ServerChannelHandlers extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 处理器1: tcp粘拆包 -> 规定报文前几位是 信息体长度,如果不一致 便会进行合并和拆分 包
         * 处理器2：序列化编码
         * 处理器3：序列化解码
         * 处理器4: 业务接收处理器
         */
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        pipeline.addLast(new MyEncoder(Response.class));
        pipeline.addLast(new MyDecoder(Request.class));
        pipeline.addLast(new ServerHandler());

    }
}
