package org.sjw.sjwrpc.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version ServerHandle.java, v 0.1
 * @date 2018/8/30
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private Channel channel;
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-client netty channel active -> get channel");
        this.channel=ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)  {
        log.info("sjwrpc-client netty channel receive a response from server -> mag = {}",msg);
    }

    /**
     * 发送给netty服务器请求
     */
    public void sendRequest(Request request){
        log.info("do send request -> server address={} , active={}",channel.remoteAddress(),channel.isActive());
        channel.writeAndFlush(request);
    }

}
