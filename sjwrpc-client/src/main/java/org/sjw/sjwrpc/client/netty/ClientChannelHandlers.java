package org.sjw.sjwrpc.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.pojo.Response;
import org.sjw.sjwrpc.core.serialize.MyDecoder;
import org.sjw.sjwrpc.core.serialize.MyEncoder;

/**
 * @author shijiawei
 * @version ServerChannelHandlers.java, v 0.1
 * @date 2018/8/31
 * client处理管道
 */
public class ClientChannelHandlers extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        pipeline.addLast(new MyEncoder(Request.class));
        pipeline.addLast(new MyDecoder(Response.class));
        pipeline.addLast(new ClientHandler());
    }
}
