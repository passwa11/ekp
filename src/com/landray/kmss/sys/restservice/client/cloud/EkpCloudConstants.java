package com.landray.kmss.sys.restservice.client.cloud;

import com.landray.kmss.util.ResourceUtil;

/**
 * 定义在kmssconfig.properties文件中的微服务配置项
 * @author 陈进科
 * 2019-02-25
 */
public interface EkpCloudConstants {

    /**
     * 原始EKP注册到cloud中的appname
     */
    public static final String DEFAULT_EKP_APPNAME = "origin-ekp";
    
    /**
     * 原始KMS注册到cloud中的appname
     */
    public static final String DEFAULT_KMS_APPNAME = "origin-kms";
    
    /**
     * 原始FS注册到cloud中的appname
     */
    public static final String DEFAULT_FS_APPNAME = "origin-fs";

    /**
     * {@link EkpCloudConstants#CLOUD_AUTH_TYPE_KEY}为空时的默认值
     */
    public static final String DEFAULT_AUTH_TYPE = "header";

    /**
     * {@link EkpCloudConstants#CLOUD_SECURITY_HEADER_KEY}为空时的默认值
     */
	public static final String DEFAULT_SECURITY_HEADER = "X-SERVICE-NAME";
    
    /**
     * 固定的密钥补充内容
     */
    public static final String CLOUD_DEFAULT_KEY_TAIL = "abcdefghijklmnopqrstuvwxyz1234567890";
    
    /**
     * 云配置项用的前缀
     */
    public static final String CLOUD_CONFIG_KEY_PREFIX = "ekp.cloud.";
    
    public static final String CLOUD_ACCESSABLE_KEY = CLOUD_CONFIG_KEY_PREFIX+"accessable"; 
    
   	/**
	 * 注册中心默认使用的appName
	 */
   public static final String CLOUD_DEFAULT_DISCOVERY_CENTER_APPNAME="discovery-center";
    
    /**
     * 注册中心的http地址，格式为http://hostname:port/，注册中心如有多个，以英文逗号','分隔
     * CLOUD_ACCESSABLE_KEY==true时必填
     */
    public static final String CLOUD_SITE_KEY = CLOUD_CONFIG_KEY_PREFIX+"discovercenter.http.host";
    
    /**
     * 接入云的认证方式，目前只支持header，此配置暂不在页面显示
     * header : 特定加密认证，默认值
     * （basic : Basic认证）
     * CLOUD_ACCESSABLE_KEY==true时必填  二选一
     */
    public static final String CLOUD_AUTH_TYPE_KEY = CLOUD_CONFIG_KEY_PREFIX+"auth.type";

    /**
     * BASIC认证的username
     * CLOUD_AUTH_TYPE_KEY==basic时必填
     */
    public static final String CLOUD_AUTH_USER_KEY = CLOUD_CONFIG_KEY_PREFIX+"auth.user";
    
    /**
     * BASIC认证的pw
     * CLOUD_AUTH_TYPE_KEY==basic时必填
     */
    public static final String CLOUD_AUTH_PW_KEY = CLOUD_CONFIG_KEY_PREFIX+"auth.password";
    
    /**
     * 加密认证的headername，此配置暂不在页面显示
     * CLOUD_ACCESSABLE_KEY==true时必填
     */
    public static final String CLOUD_SECURITY_HEADER_KEY = CLOUD_CONFIG_KEY_PREFIX+"security.headername";
    
    /**
     * 加密认证的密钥
     * CLOUD_ACCESSABLE_KEY==true时必填
     */
    public static final String CLOUD_SECURITY_KEY = CLOUD_CONFIG_KEY_PREFIX+"security.secretkey";
    
    /**
     * 注册到eureka的服务名称，一个EKP集群使用同一个名称
     * CLOUD_ACCESSABLE_KEY==true时必填
     */
    public static final String CLOUD_EXPOSE_APPNAME_KEY = CLOUD_CONFIG_KEY_PREFIX+"expose.appname";
    
    /**
     * 提供服务的端口，一般与web服务器配置的http端口保持一致，比如80
     * CLOUD_ACCESSABLE_KEY==true时必填
     */
    public static final String CLOUD_EXPOSE_PORT_KEY = CLOUD_CONFIG_KEY_PREFIX+"expose.port";
    
    /**
     * 注册到eureka的服务实例名称，注意集群中不要有重复的id
     */
    public static final String CLOUD_EXPOSE_INSTANCEID_KEY = CLOUD_CONFIG_KEY_PREFIX+"expose.instanceid";
    
    /**
     * 注册到eureka的物理主机名或DNS，用于构造http请求的hostname部分，所以请确定它能在网络中使用，
     * 建议填写本机用于通信的IP地址，如果不填系统会默认选一个非本地环的IP
     * CLOUD_ACCESSABLE_KEY==true时可空
     */
    public static final String CLOUD_EXPOSE_HOSTNAME_KEY = CLOUD_CONFIG_KEY_PREFIX+"expose.hostname";
    
    /**
     * 从注册中心根据模块名查找服务名的URL,默认为/api/module2appname
     * 该配置暂时不暴露
     */
    public static final String CLOUD_MODULE_TO_APPNAME_URL_KEY = CLOUD_CONFIG_KEY_PREFIX+"discovercenter.module2app.url";
    
    /**
     * 是否开放接入ekp-cloud
     */
    public static final boolean CLOUD_ACCESSABLE = "true".equals(ResourceUtil.getKmssConfigString(CLOUD_ACCESSABLE_KEY));

}
