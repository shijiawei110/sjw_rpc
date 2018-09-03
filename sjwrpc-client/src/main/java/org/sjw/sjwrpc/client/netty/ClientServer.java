package org.sjw.sjwrpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    private ClientHandler clientHandler;
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
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("encoder", new StringEncoder());
                p.addLast("decoder", new StringDecoder());
                p.addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = b.connect(serverIp, serverPort).sync();
        //添加连接成功监听
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("sjwrpc-client netty connect success -> ip={} port={}", serverIp, serverPort);
                    clientHandler = channelFuture.channel().pipeline().get(ClientHandler.class);
                }
            }
        });


    }

    @PreDestroy
    public void close() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }


    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
