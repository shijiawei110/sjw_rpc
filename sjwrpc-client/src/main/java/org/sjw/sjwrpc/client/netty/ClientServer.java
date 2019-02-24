package org.sjw.sjwrpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.pojo.Response;
import org.sjw.sjwrpc.core.serialize.MyDecoder;
import org.sjw.sjwrpc.core.serialize.MyEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author shijiawei
 * @version ClientServer.java, v 0.1
 * @date 2018/8/30
 * 服务端netty服务
 */
@Component
@Slf4j
public class ClientServer {

    @Value("${sjwrpc.netty.server.port}")
    private int serverPort;
    @Value("${sjwrpc.netty.server.ip}")
    private String serverIp;

    private EventLoopGroup eventLoopGroup;
    private static final int workerGroupSize = 3;

    /**
     * 绑定netty服务器连接
     */
    @PostConstruct
    public void init() throws Exception {
        if (Epoll.isAvailable()) {
            eventLoopGroup = new EpollEventLoopGroup(workerGroupSize);
        } else {
            eventLoopGroup = new NioEventLoopGroup(workerGroupSize);
        }

        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup);
        if (Epoll.isAvailable()) {
            b.channel(EpollSocketChannel.class);
        } else {
            b.channel(NioSocketChannel.class);
        }
        b.handler(new ClientChannelHandlers());
        //connect server and add listener
        ClientHanderManager clientHanderManager = ClientHanderManager.getInstance();
        clientHanderManager.setClientBootStrap(b);
        clientHanderManager.setServerIp(serverIp);
        clientHanderManager.setServerPort(serverPort);
        clientHanderManager.doConnect();

    }

    @PreDestroy
    public void close() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
