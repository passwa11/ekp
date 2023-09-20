package com.landray.kmss.common.actions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import com.landray.kmss.common.interceptors.KmssDefaultRequestHandleInterceptor;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;

public class KmssBeanNameUrlHandlerMapping extends BeanNameUrlHandlerMapping{
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(KmssBeanNameUrlHandlerMapping.class);
    private final String extId = "com.landray.kmss.common.actions";
    private final String extName = "interceptor";
    private final Class<?> requiredBeanClass = KmssDefaultRequestHandleInterceptor.class;
    
    private Object getRequiredBean(){
        Object requiredBean = null;
        try{
            String[] beanNamesForType = getApplicationContext().getBeanNamesForType(requiredBeanClass);
            requiredBean = getApplicationContext().getBean(beanNamesForType[0]);
        }catch(Exception e){
            throw new IllegalStateException("Cannot find a "+requiredBeanClass.getName()+" instance, but required.");
        }
        return requiredBean;
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" }) 
    protected void extendInterceptors(List interceptors) {
        if(interceptors.isEmpty()){
            interceptors.add(0,getRequiredBean());
        }
        IExtension[] exts = Plugin.getExtensions(extId, "*",extName);
        if(exts!=null && exts.length>0){
            List<IExtension> enabledExtensions = Arrays.asList(exts);
            /* 根据order参数排序，小的排前面 */
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
            /* 根据bean名称获取对应的bean，添加到临时列表中 */
            for (IExtension extesion : enabledExtensions) {
                String beanName = Plugin.getParamValueString(extesion, "bean");
                try{
                    Object bean = getApplicationContext().getBean(beanName,IActionInterceptor.class);
                    if(requiredBeanClass.isAssignableFrom(bean.getClass())){
                        interceptors.add(0,bean);
                    }else{
                        interceptors.add(bean);
                    }
                    if(logger.isInfoEnabled()){
                        logger.info("Find IActionInterceptor implement bean: "+beanName);
                    }
                }catch(Exception be){
                    if(logger.isWarnEnabled()){
                        logger.warn(be.getMessage());
                        ExceptionUtils.printRootCauseStackTrace(be);
                    }
                }
            }
        }
    }
}
