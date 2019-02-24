package org.sjw.sjwrpc.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.sjw.sjwrpc.server.annotation.SjwRpc;
import org.sjw.sjwrpc.server.utils.SjwRpcBeanMap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

/**
 * @author shijiawei
 * @version ApplicationStartup.java, v 0.1
 * @date 2018/9/3
 * 在spring容器加载完成后执行一些事件
 */
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //在容器加载完毕后执行
        // 获取配置文件中的配置
        Map<String, Object> serviceBeanMap = event.getApplicationContext().getBeansWithAnnotation(SjwRpc.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(SjwRpc.class).value().getName();
                //init将注解加入map中
                log.info("load sjwrpc bean to bean map -> name={}", interfaceName);
                SjwRpcBeanMap.map.put(interfaceName, serviceBean);
            }
        }
    }

}
