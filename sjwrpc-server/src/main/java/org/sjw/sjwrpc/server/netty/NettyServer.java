package org.sjw.sjwrpc.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.sjw.sjwrpc.server.utils.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shijiawei
 * @version NettyServer.java, v 0.1
 * @date 2018/8/30
 * 服务端netty服务
 */
@Component
@Slf4j
public class NettyServer {

    @Value("${sjwrpc.netty.server.port}")
    private int serverPort;

    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workerGroup = null;
    //boss线程数,用来接收请求
    private static final int BOSSGROUPSIZE = 1;
    //任务线程,用来执行请求
    private static final int WORKGROUPSIZE = 3;

    private static ExecutorService NETTY_SERVER_SINGLE_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory("netty-server"));


    @PostConstruct
    public void init() {
        NETTY_SERVER_SINGLE_POOL.execute(() -> {
            try {
                run(serverPort);
            } catch (Exception e) {
                log.error("sjwrpc-server netty server start error -> msg={}", ExceptionUtils.getStackTrace(e));
            }
        });

    }

    @PreDestroy
    public void close() {
        if (NETTY_SERVER_SINGLE_POOL != null) {
            NETTY_SERVER_SINGLE_POOL.shutdown();
        }
        if (bossGroup != null && workerGroup != null) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private void run(int port) throws Exception {
        try {
            if (bossGroup == null) {
                if (Epoll.isAvailable()) {
                    bossGroup = new EpollEventLoopGroup(BOSSGROUPSIZE);
                    workerGroup = new EpollEventLoopGroup(WORKGROUPSIZE);
                } else {
                    bossGroup = new NioEventLoopGroup(BOSSGROUPSIZE);
                    workerGroup = new NioEventLoopGroup(WORKGROUPSIZE);
                }
            }
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            if (Epoll.isAvailable()) {
                bootstrap.channel(EpollServerSocketChannel.class);
            } else {
                bootstrap.channel(NioServerSocketChannel.class);
            }
            bootstrap.childHandler(new ServerChannelHandlers())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("sjwrpc-server netty server start -> port={}", port);
            //阻塞 等待连接关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            if (bossGroup != null && workerGroup != null) {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }
    }


}
