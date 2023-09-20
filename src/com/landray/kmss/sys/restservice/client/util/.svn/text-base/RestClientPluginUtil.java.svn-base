package com.landray.kmss.sys.restservice.client.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.restservice.client.IConnectionSocketFactoryBuilder;
import com.landray.kmss.util.SpringBeanUtil;

public class RestClientPluginUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RestClientPluginUtil.class);
    
    public static final String EXTEND_ID = "com.landray.kmss.sys.restservice.client";
    
    public static final String ITEM_ConnectionSocketFactory_Builder = "connectionSocketFactoryBuilder";
    
    public static IConnectionSocketFactoryBuilder getConnectionSockectFactoryBuilder(){
        try{
            IExtension[] extensions = Plugin.getExtensions(EXTEND_ID,"*",ITEM_ConnectionSocketFactory_Builder);
            List<IExtension> enabledExtensions = Arrays.asList(extensions);
            sortByOrder(enabledExtensions);
            String beanName = Plugin.getParamValueString(enabledExtensions.get(0), "bean");
            IConnectionSocketFactoryBuilder bean = (IConnectionSocketFactoryBuilder)SpringBeanUtil.getBean(beanName);
            return bean;
        }catch(Exception e){
            logger.error("无法获取链接工厂构造器的扩展,"+e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
    
    
    /* 根据order参数排序，小的排前面 */
    private static void sortByOrder(List<IExtension> enabledExtensions){
        Collections.sort(enabledExtensions, new Comparator<IExtension>() {
            @Override
            public int compare(IExtension o1, IExtension o2) {
                int order1, order2;
                Object obj1 = Plugin.getParamValue(o1, "order");
                Object obj2 = Plugin.getParamValue(o2, "order");
                if (obj1 instanceof Integer) {
                    order1 = (Integer) obj1;
                    order2 = (Integer) obj2;
                } else {
                    order1 = Integer.valueOf((String) obj1);
                    order2 = Integer.valueOf((String) obj2);
                }
                return order1 - order2;
            }
        });
    }
}
