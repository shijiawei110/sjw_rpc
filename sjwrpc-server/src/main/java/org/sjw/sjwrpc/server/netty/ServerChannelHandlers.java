package org.sjw.sjwrpc.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

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
         * 处理器1: tcp粘拆包 -> 行尾符分割  字符尾加 System.getProperty("line.separator").getBytes()
         * 处理器2：序列化解码
         * 处理器3: 业务接收处理器
         */
        pipeline.addLast(new LineBasedFrameDecoder(2048));
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast(new ServerHandler());

    }
}
