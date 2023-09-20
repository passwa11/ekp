package com.landray.kmss.sys.portal.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import com.landray.kmss.common.dto.DesignElementDetail;
import com.landray.kmss.common.dto.DesignElementGroup;
import com.landray.kmss.common.dto.ExtensionImpl;
import com.landray.kmss.common.dto.MetaApplicationImpl;
import com.landray.kmss.common.dto.MetaModuleImpl;
import com.landray.kmss.common.dto.RemoteApi;
import com.landray.kmss.sys.portal.cloud.controller.SysPortalDataSourceController;
import com.landray.kmss.sys.restservice.client.IRestClient;
import com.landray.kmss.sys.restservice.client.cloud.EkpCloudConstants;
import com.landray.kmss.sys.restservice.client.util.PersistentUtil;
import com.landray.kmss.sys.restservice.client.util.PersistentUtil.ElementType;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;

/**
 * 将门户数据源的扩展点实现上报给cloud的注册中心
 *
 * @author chao
 */
public class SysPortalDataSourceSender
        implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalDataSourceSender.class);

    private IRestClient ekpCloudClient;

    // 模块Code
    private final String fdModule = RequestUtils.getAppName() + ":sys-portal";
    // cloud的门户（sys-portal中定义的扩展点）
    private final String pointId = "com.landray.sys.portal.annotation.PorletPoint";
    /**
     * api的接口类
     **/
    private final String apiInterfaceClassName = "com.landray.sys.portal.spi.IPortletExtensionPointApi";
    /**
     * apiInterfaceClassName对应实现类
     **/
    private final String implClassName = SysPortalDataSourceController.class
            .getName();

    private static AtomicBoolean REPORTED = new AtomicBoolean(false);

    public void setEkpCloudClient(IRestClient ekpCloudClient) {
        this.ekpCloudClient = ekpCloudClient;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (REPORTED.compareAndSet(false, true)
                && EkpCloudConstants.CLOUD_ACCESSABLE) {
            try {
                DesignElementGroup group = new DesignElementGroup();
                // group中设置了，details中就不需要设置了
                group.setFdAppName(RequestUtils.getAppName());
                List<DesignElementDetail> saveList = new ArrayList<>();
                saveList.add(buildExtension());
                saveList.add(buildRemoteApi());
                saveList.add(buildApplication());
                saveList.add(buildModule());
                group.setSaveList(saveList);
                group.setDeleteList(new ArrayList<String>());
                logger.info("准备发送给注册中心...");
                logger.info(JSONObject.fromObject(group).toString());
                this.ekpCloudClient.postForObject(
                        PersistentUtil.SAVE_DESIGN_ELEMENT_URL, Void.class,
                        group);
                logger.info("====================上报完成=====================");
            } catch (Exception e) {
                logger.error("上报门户数据源扩展失败！", e);
            }
        }
    }

    private String getContextPath() {
        WebApplicationContext wc = (WebApplicationContext) SpringBeanUtil
                .getApplicationContext();
        // 应用程序上下文，由于eureka注册中心获取的服务地址不带上下文，所以如果有，这里要带上
        String contextPath = wc.getServletContext().getContextPath();
        if (StringUtil.isNotNull(contextPath) && !"/".equals(contextPath)) {
            return contextPath;
        }
        return "";
    }


    /**
     * 构建扩展
     */
    private DesignElementDetail buildExtension() {
        DesignElementDetail extensionDetail = new DesignElementDetail();
        ExtensionImpl extension = new ExtensionImpl();
        extension.setModule(fdModule);
        extension.setRefName(PersistentUtil.shortName(implClassName));
        PortalPointConfig config = new PortalPointConfig();
        // config设置值
        // 系统访问前缀这里采用admin.do中的服务外网
        config.setPrefixUrl(
                ResourceUtil.getKmssConfigString("kmss.urlPrefix"));
        config.setSysCode(RequestUtils.getAppName());
        config.setSysDesc(RequestUtils.getAppName());
        config.setSysName(RequestUtils.getAppName());
        JSONObject json = JSONObject.fromObject(config);
        json.put("id", extension.getRefName());
        extension.setConfig(json);
        extension.setElementType(java.lang.annotation.ElementType.TYPE);

        extensionDetail.setFdModule(fdModule);
        extensionDetail.setFdId(PersistentUtil.toId(ElementType.Extension, pointId, extension.getId()));
        extensionDetail
                .setFdContent(JSONObject.fromObject(extension).toString());
        return extensionDetail;
    }

    /**
     * 构建应用
     *
     * @return
     */
    private DesignElementDetail buildApplication() {
        DesignElementDetail detail = new DesignElementDetail();
        String appName = RequestUtils.getAppName();
        detail.setFdId(
                PersistentUtil.toId(ElementType.MetaApplication, appName));
        MetaApplicationImpl app = new MetaApplicationImpl();
        app.setAppName(appName);
        detail.setFdContent(JSONObject.fromObject(app).toString());
        return detail;
    }

    /**
     * 构建模块
     *
     * @return
     */
    private DesignElementDetail buildModule() {
        DesignElementDetail detail = new DesignElementDetail();
        String appName = RequestUtils.getAppName();
        detail.setFdId(
                PersistentUtil.toId(ElementType.MetaModule, fdModule));
        MetaModuleImpl module = new MetaModuleImpl();
        module.setAppName(appName);
        module.setName(fdModule);
        module.setLabel(ResourceUtil.getString("sys-portal:module.sys.portal"));
        detail.setFdContent(JSONObject.fromObject(module).toString());
        return detail;
    }

    /**
     * 构建远程API
     *
     * @return
     */
    private DesignElementDetail buildRemoteApi() {
        DesignElementDetail detail = new DesignElementDetail();
        detail.setFdModule(fdModule);
        detail.setFdId(
                PersistentUtil.toId(ElementType.RemoteApi, implClassName));

        RemoteApi api = new RemoteApi();
        api.setClassName(implClassName);
        api.setModule(fdModule);
        String prefix = RequestUtils.getAppName() + getContextPath();
        api.setPath(String.format("http://%s/api/sys-portal/portalDataSource",
                prefix));
        Map<String, Map<String, String>> interfaces = new HashMap<>();
        interfaces.put(apiInterfaceClassName, new HashMap<String, String>());
        api.setInterfaces(interfaces);

        detail.setFdContent(JSONObject.fromObject(api).toString());
        return detail;
    }

    /**
     * 可查看cloud中com.landray.sys.portal.annotation.PorletPoint
     *
     * @author chao
     */
    public static class PortalPointConfig {
        /**
         * 系统标识
         */
        private String sysCode;
        /**
         * 系统名称
         */
        private String sysName;
        /**
         * 系统名称 国际化key
         */
        private String sysMessageKey;
        /**
         * 系统描述
         */
        private String sysDesc;
        /**
         * 系统访问前缀
         */
        private String prefixUrl;
        /**
         * 组织架构id对应属性
         */
        private String fdOrgField = "fdOriId";

        public String getFdOrgField() {
            return fdOrgField;
        }

        public void setFdOrgField(String fdOrgField) {
            this.fdOrgField = fdOrgField;
        }

        public String getSysCode() {
            return sysCode;
        }

        public void setSysCode(String sysCode) {
            this.sysCode = sysCode;
        }

        public String getSysName() {
            return sysName;
        }

        public void setSysName(String sysName) {
            this.sysName = sysName;
        }

        public String getSysMessageKey() {
            return sysMessageKey;
        }

        public void setSysMessageKey(String sysMessageKey) {
            this.sysMessageKey = sysMessageKey;
        }

        public String getSysDesc() {
            return sysDesc;
        }

        public void setSysDesc(String sysDesc) {
            this.sysDesc = sysDesc;
        }

        public String getPrefixUrl() {
            return prefixUrl;
        }

        public void setPrefixUrl(String prefixUrl) {
            this.prefixUrl = prefixUrl;
        }
    }
}
