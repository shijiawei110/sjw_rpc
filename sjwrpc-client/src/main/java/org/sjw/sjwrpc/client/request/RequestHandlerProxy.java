package org.sjw.sjwrpc.client.request;

import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.client.netty.ClientServer;
import org.sjw.sjwrpc.client.netty.CommunicateContainer;
import org.sjw.sjwrpc.client.netty.Request;
import org.sjw.sjwrpc.client.netty.Response;
import org.sjw.sjwrpc.client.pojo.Book;
import org.sjw.sjwrpc.client.utils.IdCommon;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shijiawei
 * @version RequestHandlerProxy.java, v 0.1
 * @date 2018/8/31
 * 调用netty远程请求  动态代理
 */
@Slf4j
public class RequestHandlerProxy implements InvocationHandler {

    @Autowired
    private ClientServer clientServer;

    //被代理的类名
    private String apiName;

    public <T> T create(String apiName, Class<T> clazz) {
        this.apiName = apiName;
        //绑定该类实现的所有接口，取得代理类
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        log.info("do netty request start-> class={} method={}", apiName, method.getName());
        //先检查是不是object的那些方法,是的话 不执行远程调用
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return null;
            } else if ("hashCode".equals(name)) {
                return null;
            } else if ("toString".equals(name)) {
                return null;
            } else {
                return null;
            }
        }
        /**
         * 进行netty远程获取运算结果
         */
        Response response = doRemote(method, args);
        log.info("do netty request end-> class={} method={}", apiName, method.getName());
        return response;
    }

    /**
     * 执行远程调用
     *
     * @return
     */
    private Response doRemote(Method method, Object[] args) throws InterruptedException {
        Request request = new Request();
        Long requestId = IdCommon.REQUEST_ID.incrementAndGet();
        request.setRequestId(requestId);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        //执行netty调用
        clientServer.getClientHandler().sendRequest(request);
        //自旋获取结果 结果为null就说明没有获取到 直接return
        Response response = CommunicateContainer.getResponse(requestId);
        return response;
    }


}
