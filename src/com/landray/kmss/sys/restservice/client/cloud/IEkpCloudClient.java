package com.landray.kmss.sys.restservice.client.cloud;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestOperations;

import com.landray.kmss.sys.restservice.client.RestOperationsSupport;

/**
 * ekp云客户端接口，T为提供实现服务注册与发现的第三方客户端，比如Eureka和Dubbo
 * 建议使用
 * @author 陈进科
 * 2019-02-18
 * @param <T>
 */
public interface IEkpCloudClient extends RestOperationsSupport{

    /**
     * 获取Rest操作对象，只有当完全自定义发送消息的时候使用，一般不建议使用
     * 推荐优先使用{@link #getApiProxy(Class)}->{@link #request(String, Object, Class)}->{@link IEkpCloudClient#request(String, Object)}
     * @return
     */
    @Override
    public RestOperations getRestOperations();
    
    /** 
     * <pre>
     * 如果使用接口来描述APIClient，可通过此方法获取接口的实现类，从而使用本地方法调用的风格写代码
     * <code>
     * &#064;{@link EkpCloudApiClientDescriptor}
     * interface IApi{
     *    &#064;RequestMapping("/api/sys-job/schedule/addSchedule/...")
     *    public ReturnObject doSomething(ValueObject vo);
     * }
     * -------------------
     * IApi api = IEkpCloudClient.getApiProxy(IApi.class);
     * ReturnObject ro = api.doSomething(ValueObject vo);
     * doWith(ro);
     * </code>
     * </pre>
     * @param clazz  该接口必须是{@link EkpCloudApiClientDescriptor}注解的接口interface
     * @return null 如果不是一个EkpCloudApiClientDescriptor标记的接口
     */
    public <I> I getApiProxy(Class<I> clazz);

    /**
     * 在已知返回类型（当前虚拟机能加载到Class, ClassUtils.forName(**)!=null）的前提下，访问指定apiPath
     * @param apiPath  
     * @param requestObject
     * @param responseType
     * @return
     */
    public <V> V requestVO(String apiPath, Object requestObject, Class<V> responseType);
    
    /**
     * 等同于requestVO(apiPath,requestObject,String.class)
     * @param apiPath  
     * @param requestObject
     * @return
     */
    public String requestString(String apiPath, Object requestObject);
    
    /**
     * 等同于requestVO(apiPath,requestObject,List.class)
     * @param apiPath  
     * @param requestObject
     * @return
     */
    public List<?> requestList(String apiPath, Object requestObject);
    
    /**
     * 等同于requestVO(apiPath,requestObject,Map.class)
     * @param apiPath  
     * @param requestObject
     * @return
     */
    public Map<String,?> requestMap(String apiPath, Object requestObject);

	/**
	 * 获取存活的所有服务名列表
	 * 
	 * @return
	 */
	public List<String> getAppNames();
}
