package com.landray.kmss.sys.webservice2.service.spring;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceMainDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceInitService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;

/**
 * Web服务初始化导入
 *
 * @author Jeff
 */
public class SysWebserviceInitServiceImp extends BaseServiceImp implements
		ISysWebserviceInitService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebserviceInitServiceImp.class);

	// 扩展点
	private static final String EXTENSION_POINT = "com.landray.kmss.sys.webservice2";
	private static final String PARAM_SERVICE_NAME = "serviceName";
	private static final String PARAM_SERVICE_CLASS = "serviceClass";
	private static final String PARAM_SERVICE_BEAN = "serviceBean";
	private static final String PARAM_SERVICE_DOC = "serviceDoc";

	ISysWebserviceMainService sysWsMainService;

	public void setSysWsMainService(ISysWebserviceMainService sysWsMainService) {
		this.sysWsMainService = sysWsMainService;
	}

	/**
	 * 返回初始化任务名称
	 */
	@Override
    public String initName() {
		return ResourceUtil.getString("sysWebservice.init", "sys-webservice2");
	}

	/**
	 * 获取服务注册信息配置相关的扩展
	 */
	private IExtension[] getExtension() {
		IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT, "*");

		return extensions;
	}

	/**
	 * 从所有扩展配置中加载服务的注册信息
	 */
	private List<SysWebserviceMain> loadAllServicesFromXml(
			List<SysWebserviceMain> dbWsList) throws Exception {

		List<SysWebserviceMain> xmlWsList = new ArrayList<SysWebserviceMain>();
		IExtension[] extensions = getExtension();

		// 遍历系统中所有的WebService配置信息
		if (extensions != null) {
			for (int i = 0; i < extensions.length; i++) {
				String serviceName = Plugin.getParamValueString(extensions[i],
						PARAM_SERVICE_NAME);
				String serviceClass = Plugin.getParamValueString(extensions[i],
						PARAM_SERVICE_CLASS);
				String serviceBean = Plugin.getParamValueString(extensions[i],
						PARAM_SERVICE_BEAN);
				String serviceDoc = Plugin.getParamValueString(extensions[i],
						PARAM_SERVICE_DOC);

				// 加载WebService信息到内存中
				SysWebserviceMain service = LoadServiceFromXml(dbWsList,
						serviceName, serviceClass, serviceBean, serviceDoc);
				xmlWsList.add(service);
			}
		}

		return xmlWsList;
	}

	/**
	 * 根据扩展配置文件加载服务
	 */
	private SysWebserviceMain LoadServiceFromXml(
			List<SysWebserviceMain> dbWsList, String serviceName,
			String serviceClass, String serviceBean, String serviceDoc)
			throws Exception {

		// 更新数据库中已经存在的WebService
		for (SysWebserviceMain service : dbWsList) {
			if (service.getFdServiceBean().equals(serviceBean)) {
				return updateService(service, serviceName, serviceClass,
						serviceBean, serviceDoc);
			}
		}

		// 创建不存在的WebService
		return newService(serviceName, serviceClass, serviceBean, serviceDoc);
	}

	/**
	 * 更新WebService的注册信息
	 */
	private SysWebserviceMain updateService(SysWebserviceMain service,
											String serviceName, String serviceClass, String serviceBean,
											String serviceDoc) throws Exception {

		service.setFdName(serviceName);
		service.setFdServiceClass(serviceClass);
		service.setFdServiceBean(serviceBean);
		service.setFdServiceParam(serviceDoc);
		service.setFdAddress("/" + serviceBean);
		service.setFdServiceStatus(SysWsConstant.SERVICE_STATUS_STOP);

		// 数据修复
		if (service.getFdAnonymous() == null) {
			service.setFdAnonymous(false);
		}
		if (service.getFdStartupType() == null) {
			service.setFdStartupType(SysWsConstant.STARTUP_TYPE_AUTO);
		}
		if (service.getFdSoapMsgLogging() == null) {
			service.setFdSoapMsgLogging(false);
		}

		return service;
	}

	/**
	 * 创建新的WebService注册信息
	 */
	private SysWebserviceMain newService(String serviceName,
										 String serviceClass, String serviceBean, String serviceDoc)
			throws Exception {

		SysWebserviceMain service = new SysWebserviceMain();
		service.setFdName(serviceName);
		service.setFdServiceClass(serviceClass);
		service.setFdServiceBean(serviceBean);
		service.setFdServiceParam(serviceDoc);
		service.setFdAddress("/" + serviceBean);
		service.setFdAnonymous(false);
		service.setFdServiceStatus(SysWsConstant.SERVICE_STATUS_STOP);
		service.setFdStartupType(SysWsConstant.STARTUP_TYPE_AUTO);
		service.setFdSoapMsgLogging(false);

		return service;
	}

	/**
	 * 从扩展配置中加载服务信息并初始化所有服务
	 */
	@Override
    public KmssMessages initializeData() {
		KmssMessages messages = new KmssMessages();

		try {
			ISysWebserviceMainDao dao = (ISysWebserviceMainDao) sysWsMainService
					.getBaseDao();
			List<SysWebserviceMain> dbWsList = dao.findServiceList();
			List<SysWebserviceMain> xmlWsList = loadAllServicesFromXml(dbWsList);
			// 记录日志
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL,
					SysWebserviceMain.class.getName())) {
				UserOperContentHelper.putFinds(xmlWsList);
			}
			stopDatabaseServices(dbWsList);
			stopDatabaseServices(xmlWsList);
			startRegisterServices(xmlWsList);

			// 删除数据库中无效的WebService注册信息
			for (SysWebserviceMain model : dbWsList) {
				if (!xmlWsList.contains(model)) {
					dao.delete(model);
				}
			}

			messages.addMsg(new KmssMessage(
					"sys-webservice2:sysWebservice.init.success"));

		} catch (Exception e) {
			logger.error(e.toString());
			messages.addError(
					new KmssMessage(
							"sys-webservice2:sysWebservice.init.failure", e
							.toString()), e);
		}

		return messages;
	}

	/**
	 * 停止所有数据库的服务
	 */
	private void stopDatabaseServices(List<SysWebserviceMain> wsList)
			throws Exception {
		for (SysWebserviceMain model : wsList) {
			// 服务未被禁用
			if (SysWsConstant.STARTUP_TYPE_AUTO
					.equals(model.getFdStartupType())
					|| SysWsConstant.STARTUP_TYPE_MANUAL.equals(model
					.getFdStartupType())) {
				sysWsMainService.stopService(model);
			}
		}
	}

	/**
	 * 启动所有插件工厂的服务
	 *
	 * @param wsList
	 * @throws Exception
	 */
	private void startRegisterServices(List<SysWebserviceMain> wsList)
			throws Exception {
		List<Exception> es = new ArrayList<>();
		for (SysWebserviceMain model : wsList) {
			// 如果启动类型为自动模式，则启动服务
			if (SysWsConstant.STARTUP_TYPE_AUTO
					.equals(model.getFdStartupType())) {
				try {
					sysWsMainService.startService(model);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
					es.add(e);
				}
			}
		}
		if(!es.isEmpty()){
			if(es.size()==1){
				throw es.get(0);
			}else{
				StringBuilder messageBuilder = new StringBuilder();
				messageBuilder.append("有"+es.size()+"个服务启动失败，具体信息请查看日志文件。");
				for(Exception e:es){
					final Writer result = new StringWriter();
					final PrintWriter printWriter = new PrintWriter(result);
					e.printStackTrace(printWriter);
					messageBuilder.append(result.toString());
					messageBuilder.append("\r\n");
				}
				throw new RuntimeException(messageBuilder.toString());
			}
		}
	}

}
