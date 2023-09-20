package com.landray.kmss.sys.restservice.server.service.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.restservice.server.constant.SysRsConstant;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerMainDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerInitService;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import com.landray.kmss.web.util.RequestUtils;

public class SysRestserviceServerInitServiceImp extends BaseServiceImp
		implements ISysRestserviceServerInitService, ApplicationContextAware {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysRestserviceServerInitServiceImp.class);
	private ApplicationContext applicationContext;
	private boolean throwIfconflict = true;

	ISysRestserviceServerMainService sysRsMainService;

	public void setSysRsMainService(ISysRestserviceServerMainService sysRsMainService) {
		this.sysRsMainService = sysRsMainService;
	}

	/**
	 * 返回初始化任务名称
	 */
	@Override
	public String initName() {
		return ResourceUtil.getString("sysRestservice.init", "sys-restservice-server");
	}
	
	@Override
	public KmssMessages initializeData() {
		KmssMessages messages = new KmssMessages();
		try {
			ISysRestserviceServerMainDao dao = (ISysRestserviceServerMainDao) sysRsMainService.getBaseDao();
			List<SysRestserviceServerMain> dbRsList = dao.findServiceList();
			List<SysRestserviceServerMain> apiRsList = loadAllServicesFromApi(dbRsList);
			// 记录日志
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL, SysRestserviceServerMain.class.getName())) {
				UserOperContentHelper.putFinds(apiRsList);
			}

			// 删除数据库中无效的RestService注册信息
			for (SysRestserviceServerMain model : dbRsList) {
				if (!apiRsList.contains(model)) {
					dao.delete(model);
				}
			}

			messages.addMsg(new KmssMessage("sys-restservice-server:sysRestserviceServer.init.success"));
		} catch (Exception e) {
			logger.error(e.toString());
			messages.addError(new KmssMessage("sys-restservice-server:sysRestserviceServer.init.failure", e.toString()), e);
		}
		return messages;
	}

	public List<SysRestserviceServerMain> loadAllServicesFromApi(List<SysRestserviceServerMain> dbRsList) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for RestApi in application context: " + applicationContext);
		}
		String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
		Map<String, SysRestserviceServerMain> restServiceMap = new HashMap<>();
		Map<String, SysRestserviceServerMain> uriPrefixMap = new HashMap<>();
		Map<String, List<SysRestserviceServerMain>> conflictMap = new HashMap<>();
		SysRestserviceServerMain model;
		for (String beanName : beanNames) {
			model = createRestApi(applicationContext.getType(beanName), dbRsList);
			if (model != null) {
				//检查URI冲突
				String uri = model.getFdUriPrefix();
				if(checkConflict(uri, model, uriPrefixMap, conflictMap)){
					continue;
				}
				//检查服务名冲突
				String fdName = model.getFdServiceName();
				if(checkConflict(fdName, model, restServiceMap, conflictMap)){
					continue;
				}
			}
		}

		if (!conflictMap.isEmpty()) {
			processConfliction(conflictMap);
		}
		Collection<SysRestserviceServerMain> values = restServiceMap.values();
		List<SysRestserviceServerMain> list = new ArrayList<>(values);
		return list;
	}

	/**
	 * 如果checkMap中已有该key，则conflictMap.put(key, model)，并return true;
	 * @param key
	 * @param model
	 * @param checkMap
	 * @param conflictMap
	 * @return
	 */
	private boolean checkConflict(String key, SysRestserviceServerMain model,
			Map<String, SysRestserviceServerMain> restServiceMap,
			Map<String, List<SysRestserviceServerMain>> conflictMap) {
		boolean containsKey = restServiceMap.containsKey(key);
		if (containsKey) {
			List<SysRestserviceServerMain> list = conflictMap.get(key);
			if (list == null) {
				list = new ArrayList<>();
				conflictMap.put(key, list);
			}
			list.add(model);
			return true;
		} else {
			restServiceMap.put(key, model);
		}
		return false;
	}

	protected void processConfliction(Map<String, List<SysRestserviceServerMain>> conflictMap) {
		if (throwIfconflict) {
			throw new IllegalArgumentException("There are some conflict RestApis: \r\n" + conflictMap.toString());
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("There are some conflict RestApis will be ignored: \r\n" + conflictMap);
			}
		}
	}

	private SysRestserviceServerMain createRestApi(Class<?> beanType, List<SysRestserviceServerMain> dbRsList) throws Exception {
		Controller c = AnnotationUtils.findAnnotation(beanType, Controller.class);
		RequestMapping rm = AnnotationUtils.findAnnotation(beanType, RequestMapping.class);
		RestApi rs = AnnotationUtils.findAnnotation(beanType, RestApi.class);
		if (c == null || rm == null || rs == null) {
			return null;
		}

		String[] value = rm.value();
		String uriPrefix = null;
		for (int i = 0; i < value.length; i++) {
			if (RequestUtils.isApiUri(value[i])) {
				uriPrefix = value[i];
				break;
			}
		}
		if (uriPrefix == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("No api url prefix be found on " + beanType.getName()
						+ "'s RequestMapping annotation, do not " + "resolve it as RestApi.");
			}
			return null;
		}
		if (StringUtil.isNull(rs.name())) {
			if (logger.isWarnEnabled()) {
				logger.warn("No rest api name be found on " + beanType.getName() + "'s RestApi annotation, do not "
						+ "resolve it as RestApi.");
			}
			return null;
		}

		// 更新数据库中已经存在的RestService
		for (SysRestserviceServerMain service : dbRsList) {
			String fdServiceName = StringUtil.getString(service.getFdServiceName()).trim().toLowerCase();
			String rsName = StringUtil.getString(rs.name()).trim().toLowerCase();
			if (fdServiceName.equals(rsName)) {
				return updateService(service, rs, beanType.getName(), uriPrefix);
			}
		}

		// 创建不存在的RestService
		return addService(rs, beanType.getName(), uriPrefix);
	}

	/**
	 * 更新RestService的注册信息
	 */
	private SysRestserviceServerMain updateService(SysRestserviceServerMain service, RestApi rs, String className, String uriPrefix)
			throws Exception {
		service.setFdServiceName(rs.name());
		service.setFdResourceKey(rs.resourceKey());
		service.setFdName(getFdName(rs.resourceKey()));
		service.setFdServiceClass(className);
		service.setFdDocUrl(rs.docUrl());
		service.setFdUriPrefix(uriPrefix);

		// 服务状态改变
		if (SysWsConstant.STARTUP_TYPE_MANUAL.equals(service.getFdStartupType())
				|| SysWsConstant.STARTUP_TYPE_DISABLE.equals(service.getFdStartupType())) {
			// 关闭手动/禁用类型的服务
			service.setFdServiceStatus(SysRsConstant.SERVICE_STATUS_STOP);

		} else if (SysWsConstant.STARTUP_TYPE_AUTO.equals(service.getFdStartupType())) {
			// 启动自动类型的服务
			service.setFdServiceStatus(SysRsConstant.SERVICE_STATUS_START);
		}
		sysRsMainService.update(service);
		return service;
	}

	private String getFdName(String resourceKey) {
		String fdName = ResourceUtil.getString(resourceKey);
		if (StringUtil.isNull(fdName)) {
            fdName = resourceKey;
        }
		return fdName;
	}

	/**
	 * 创建新的RestService注册信息
	 */
	private SysRestserviceServerMain addService(RestApi rs, String className, String uriPrefix) throws Exception {
		SysRestserviceServerMain service = new SysRestserviceServerMain();
		service.setFdServiceName(rs.name());
		service.setFdResourceKey(rs.resourceKey());
		service.setFdName(getFdName(rs.resourceKey()));
		service.setFdServiceClass(className);
		service.setFdDocUrl(rs.docUrl());
		service.setFdUriPrefix(uriPrefix);
		service.setFdServiceStatus(SysRsConstant.SERVICE_STATUS_START);
		if(rs.disabled()){
		    service.setFdStartupType(SysRsConstant.STARTUP_TYPE_DISABLE);
		}else{
		    if(rs.autoStart()){
		        service.setFdStartupType(SysRsConstant.STARTUP_TYPE_AUTO);    
		    }else{
		        service.setFdStartupType(SysRsConstant.STARTUP_TYPE_MANUAL);
		    }
		}
		
		sysRsMainService.add(service);
		return service;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
