package org.sjw.sjwrpc.client.netty;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author shijiawei
 * @version CommunicateContauner.java, v 0.1
 * @date 2018/9/2
 * netty 请求和接收是异步的
 * 此为 中间通讯容器 map
 * 使用自定义自旋获取结果（可以改成自适应可变自旋）
 */
@Slf4j
public class CommunicateContainer {

    //主容器
    private static Map<Long, Response> container = Maps.newHashMap();
    //垃圾堆 容器（自旋全部失败后放入此区域）依照此区域 定时清除主容易的key
    private static Map<Long, Integer> containerWait = Maps.newHashMap();

    //自旋次数
    private static int spinNum = 50;
    //自旋间隔时间 ms
    private static long spinMs = 10L;

    public static Response getResponse(Long requestId) throws InterruptedException {
        Response result = null;
        for (int i = 0; i < spinNum; i++) {
            Response response = container.get(requestId);
            if (null == response) {
                Thread.sleep(spinMs);
                continue;
            } else {
                result=response;
                break;
            }
        }
        //自旋全部完成 如果失败则扔进垃圾map
        if(null == result){
            containerWait.put(requestId,0);
        }
        else{
            //删除map记录
            container.remove(requestId);
        }
        return result;
    }

}
