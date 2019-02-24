package org.sjw.sjwrpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version ClientHanderManager.java, v 0.1
 * @date 2018/9/4
 * client处理器管理  单例模式
 */
@Slf4j
public class ClientHanderManager {

    private Bootstrap clientBootStrap;
    private ClientHandler clientHandler;
    private String serverIp;
    private int serverPort;
    private boolean reconnectStatus;


    private ClientHanderManager() {
    }

    private volatile static ClientHanderManager instance;

    public static ClientHanderManager getInstance() {
        if (instance == null) {
            synchronized (ClientHanderManager.class) {
                if (instance == null) {
                    instance = new ClientHanderManager();
                }
            }
        }
        return instance;
    }


    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }


    public void setClientBootStrap(Bootstrap clientBootStrap) {
        this.clientBootStrap = clientBootStrap;
    }

    public void setServerIp(String ip) {
        this.serverIp = ip;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }


    public void doConnect() throws InterruptedException {
        if (clientBootStrap == null) {
            log.info("sjwrpc-api netty connect fail , clientBootStrap is null -> ip={} port={} ", serverIp, serverPort);
            return;
        }
        ChannelFuture channelFuture = clientBootStrap.connect(serverIp, serverPort).sync();
        //添加连接成功监听
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    log.info("sjwrpc-api netty connect success -> ip={} port={}", serverIp, serverPort);
                    ClientHanderManager clientHanderManager = ClientHanderManager.getInstance();
                    clientHanderManager.setClientHandler(channelFuture.channel().pipeline().get(ClientHandler.class));
                }
            }
        });
    }

    public boolean reConnect() {
        if (clientBootStrap == null) {
            log.info("sjwrpc-api netty connect fail , clientBootStrap is null -> ip={} port={} ", serverIp, serverPort);
            return false;
        }
        ChannelFuture channelFuture = clientBootStrap.connect(serverIp, serverPort);
        //添加监听
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) {
                boolean succeed = f.isSuccess();
                if(succeed){
                    ClientHanderManager clientHanderManager = ClientHanderManager.getInstance();
                    clientHanderManager.setClientHandler(channelFuture.channel().pipeline().get(ClientHandler.class));
                }
                reconnectStatus = succeed;
            }
        });
        return reconnectStatus;
    }

}
