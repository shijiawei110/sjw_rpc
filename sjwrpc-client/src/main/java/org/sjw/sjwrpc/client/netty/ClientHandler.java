package org.sjw.sjwrpc.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Response> {
    private Channel channel;

    public ClientHandler(){
        System.out.println("new a ClientHander");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-api netty channel active -> get channel");
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response) {
        log.info("sjwrpc-api netty channel receive a response from server -> response = {}", response);
        CommunicateContainer.setResponse(response.getRequestId(), response);
    }

//    /**
//     * 连接关闭回调
//     * 尝试重连
//     * @param ctx
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx){
//
//    }

    /**
     * 发送给netty服务器请求
     */
    public void sendRequest(Request request) throws InterruptedException {
        log.info("do send request -> server address={} , active={}", channel.remoteAddress(), channel.isActive());
        boolean flag=checkConnect(request);
        if(flag){
            channel.writeAndFlush(request);
        }
    }

    /**
     * 检查连接状态
     */
    private boolean checkConnect(Request request) throws InterruptedException {
        boolean isActive = channel.isActive();
        if (!isActive) {
            //重连尝试
            int i = 0;
            while (true) {
                i++;
                log.info("尝试第{}次重新连接服务器", i);
                boolean reConnectFlag = ClientHanderManager.getInstance().reConnect();
                if (reConnectFlag) {
                    log.info("重新连接rpc服务成功 ");
                    //重新拿到最新的通道hander请求
                    ClientHanderManager.getInstance().getClientHandler().sendRequest(request);
                    return false;
                }
                Thread.sleep(3000L);
            }
//            CommunicateContainer.setResponse(requestId, "RPC服务集群不可用.");
//            return false;
        } else {
            return true;
        }
    }


}
