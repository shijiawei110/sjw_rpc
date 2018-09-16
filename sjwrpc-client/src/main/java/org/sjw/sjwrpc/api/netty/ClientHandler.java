package org.sjw.sjwrpc.api.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.pojo.Response;

/**
 * @author shijiawei
 * @version ServerHandle.java, v 0.1
 * @date 2018/8/30
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Response> {
    private Channel channel;
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-api netty channel active -> get channel");
        this.channel=ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response)  {
        log.info("sjwrpc-api netty channel receive a response from server -> response = {}",response);
        CommunicateContainer.setResponse(response.getRequestId(),response);
    }

    /**
     * 发送给netty服务器请求
     */
    public void sendRequest(Request request){
        log.info("do send request -> server address={} , active={}",channel.remoteAddress(),channel.isActive());
        channel.writeAndFlush(request);
    }

}
