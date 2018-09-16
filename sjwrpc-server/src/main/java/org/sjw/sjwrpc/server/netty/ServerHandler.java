package org.sjw.sjwrpc.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.sjw.sjwrpc.core.pojo.Request;
import org.sjw.sjwrpc.core.pojo.Response;
import org.sjw.sjwrpc.server.utils.SjwRpcBeanMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author shijiawei
 * @version ServerHandle.java, v 0.1
 * @date 2018/8/30
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Request> {


    /**
     * 重写连接激活
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-server netty server received a api:{} connected", ctx.channel().remoteAddress());
    }

    /**
     * 重写连接关闭,当与server断开连接时触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("sjwrpc-server netty server  a api:{} closed", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        if (null == request) {
            log.info("sjwrpc-server netty server error -> received msg is blank");
            return;
        }
        log.info("sjwrpc-server netty server receive a request -> request={}", request);
        Response response=new Response();
        response.setRequestId(request.getRequestId());
        try{
            Object resultData=handle(request);
            response.setResult(resultData);
        }catch (Exception e){
            response.setError(e.getMessage());
        }
        ctx.writeAndFlush(response);
    }

    /**
     * 反射调用远程处理请求
     * @param request
     * @return
     */
    private Object handle(Request request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String className = request.getClassName();
        Object serviceBean = SjwRpcBeanMap.map.get(className);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        Method m = serviceClass.getMethod(methodName,parameterTypes);
        Object result=m.invoke(serviceBean,parameters);
        return result;
    }


}
