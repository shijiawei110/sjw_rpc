package org.sjw.sjwrpc.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author shijiawei
 * @version ServerHandle.java, v 0.1
 * @date 2018/8/30
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 重写连接激活
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-server netty server received a client:{} connected", ctx.channel().remoteAddress());
    }

    /**
     * 重写连接关闭,当与server断开连接时触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        log.info("sjwrpc-server netty server  a client:{} closed", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (StringUtils.isBlank(msg)) {
            log.info("sjwrpc-server netty server error -> received msg is blank");
            return;
        }
        log.info("sjwrpc-server netty server receive a msg -> msg={}",msg);
        String response="this is a server res.";
        ctx.writeAndFlush(response);
    }
}
