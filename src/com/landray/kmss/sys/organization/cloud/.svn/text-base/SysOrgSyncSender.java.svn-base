package com.landray.kmss.sys.organization.cloud;

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
import com.landray.kmss.common.dto.MetaModuleImpl;
import com.landray.kmss.common.dto.RemoteApi;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgWebServiceImp;
import com.landray.kmss.sys.portal.cloud.SysPortalDataSourceSender.PortalPointConfig;
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
 * 组织架构同步接口上报给cloud的注册中心
 * 
 * @author 潘永辉 2019年5月22日
 *
 */
public class SysOrgSyncSender implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgSyncSender.class);
	// 模块Code
	private final String fdModule = RequestUtils.getAppName() + ":sys-org";
	// cloud的门户（sys-portal中定义的扩展点）
	private final String pointId = "com.landray.sys.org.annotation.OrgSyncPoint";
	/** api的接口类 **/
	private final String apiInterfaceClassName = "com.landray.sys.org.api.ISysOrgSynchronizationEkpApi";
	/** apiInterfaceClassName对应实现类 **/
	private final String implClassName = SysSynchroGetOrgWebServiceImp.class.getName();

	private static AtomicBoolean REPORTED = new AtomicBoolean(false);

	private IRestClient ekpCloudClient;

	public void setEkpCloudClient(IRestClient ekpCloudClient) {
		this.ekpCloudClient = ekpCloudClient;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (REPORTED.compareAndSet(false, true) && EkpCloudConstants.CLOUD_ACCESSABLE) {
			// 判断admin.do是否开启组织同步
			String enabled = ResourceUtil.getKmssConfigString("ekp.cloud.org.sync.enabled");
			if (!"true".equals(enabled)) {
				// 未开启组织同步，不上报
				return;
			}
			try {
				DesignElementGroup group = new DesignElementGroup();
				// group中设置了，details中就不需要设置了
				group.setFdAppName(RequestUtils.getAppName());
				List<DesignElementDetail> saveList = new ArrayList<>();
				saveList.add(buildExtension());
				saveList.add(buildRemoteApi());
				saveList.add(buildModule());
				group.setSaveList(saveList);
				group.setDeleteList(new ArrayList<String>());
				this.ekpCloudClient.postForObject(
						PersistentUtil.SAVE_DESIGN_ELEMENT_URL, Void.class,
						group);
			} catch (Exception e) {
				logger.error("上报组织架构扩展失败！", e);
			}
		}
	}

	private String getContextPath() {
		WebApplicationContext wc = (WebApplicationContext) SpringBeanUtil.getApplicationContext();
		// 应用程序上下文，由于eureka注册中心获取的服务地址不带上下文，所以如果有，这里要带上
		String contextPath = wc.getServletContext().getContextPath();
		if (StringUtil.isNotNull(contextPath) && !"/".equals(contextPath)) {
			return contextPath;
		}
		return "";
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
		module.setLabel(
				ResourceUtil.getString("sys-org:module.sys.organization"));
		detail.setFdContent(JSONObject.fromObject(module).toString());
		return detail;
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
		// config设置值，系统访问前缀这里采用admin.do中的服务外网
		config.setPrefixUrl(ResourceUtil.getKmssConfigString("kmss.urlPrefix"));
		config.setSysCode(RequestUtils.getAppName());
		config.setSysDesc(RequestUtils.getAppName());
		config.setSysName(RequestUtils.getAppName());
		JSONObject json = JSONObject.fromObject(config);
		json.put("id", extension.getRefName());
		extension.setConfig(json);
		extension.setElementType(java.lang.annotation.ElementType.TYPE);
		extensionDetail.setFdModule(fdModule);
		extensionDetail.setFdId(PersistentUtil.toId(ElementType.Extension, pointId, extension.getId()));
		extensionDetail.setFdContent(JSONObject.fromObject(extension).toString());
		return extensionDetail;
	}

	/**
	 * 构建远程API
	 * 
	 * @return
	 */
	private DesignElementDetail buildRemoteApi() {
		DesignElementDetail detail = new DesignElementDetail();
		detail.setFdModule(fdModule);
		detail.setFdId(PersistentUtil.toId(ElementType.RemoteApi, implClassName));
		RemoteApi api = new RemoteApi();
		api.setClassName(implClassName);
		api.setModule(fdModule);
		String prefix = RequestUtils.getAppName() + getContextPath();
		api.setPath(String.format("http://%s/api/sys-organization/sysSynchroGetOrg", prefix));
		Map<String, Map<String, String>> interfaces = new HashMap<>();
		interfaces.put(apiInterfaceClassName, new HashMap<String, String>());
		api.setInterfaces(interfaces);

		detail.setFdContent(JSONObject.fromObject(api).toString());
		return detail;
	}


}
