package org.sjw.sjwrpc.api.netty;

/**
 * @author shijiawei
 * @version ClientHanderManager.java, v 0.1
 * @date 2018/9/4
 * client处理器管理  单例模式
 */
public class ClientHanderManager {
    private  ClientHandler clientHandler;

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
}
