package com.landray.kmss.sys.webservice2.service.spring;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.hibernate.type.StandardBasicTypes;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.webservice2.client.GenCode;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceMainDao;
import com.landray.kmss.sys.webservice2.interceptor.CallAfterInterceptor;
import com.landray.kmss.sys.webservice2.interceptor.CallBeforeInterceptor;
import com.landray.kmss.sys.webservice2.interceptor.CallInInterceptor;
import com.landray.kmss.sys.webservice2.interceptor.CallOutInterceptor;
import com.landray.kmss.sys.webservice2.interceptor.ErrorInterceptor;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * Web服务管理
 * 
 * @author Jeff
 */
public class SysWebserviceMainServiceImp extends BaseServiceImp implements
		ISysWebserviceMainService, IMessageReceiver {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebserviceMainServiceImp.class);

	// Web服务端缓存
	private Map<String, Server> sysWsMap = new Hashtable<String, Server>();

	private Bus bus;

	@Override
	public void setBus(Bus bus) {
		this.bus = bus;
	}

	private String urlPattern;

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	@Override
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * 启动服务并发送消息
	 */
	@Override
	public void startService(String fdId) throws Exception {
		SysWebserviceMain model = (SysWebserviceMain) findByPrimaryKey(fdId);

		// 服务未被禁用
		if (SysWsConstant.STARTUP_TYPE_AUTO.equals(model.getFdStartupType())
				|| SysWsConstant.STARTUP_TYPE_MANUAL.equals(model
						.getFdStartupType())) {

			startService(model);
			publishMessage(SysWebserviceClusterMessage.OPT_START, fdId);
		}
	}

	/**
	 * 启动服务
	 */
	private void startServiceWithoutMessage(String fdId) throws Exception {
		SysWebserviceMain model = (SysWebserviceMain) findByPrimaryKey(fdId);

		// 服务未被禁用
		if (SysWsConstant.STARTUP_TYPE_AUTO.equals(model.getFdStartupType())
				|| SysWsConstant.STARTUP_TYPE_MANUAL.equals(model
						.getFdStartupType())) {

			startService(model);
		}
	}

	/**
	 * 批量启动多个服务
	 */
	@Override
	public void startService(String[] fdIds) throws Exception {

		for (int i = 0; i < fdIds.length; i++) {
			startService(fdIds[i]);
		}
	}

	/**
	 * 启动服务
	 */
	@Override
	public synchronized void startService(SysWebserviceMain model)
			throws Exception {
		// 记录日志
		if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND,
				SysWebserviceMain.class.getName())) {
			UserOperContentHelper.putFind(model);
		}

		// 服务已经启动，直接返回
		if (sysWsMap.containsKey(model.getFdServiceBean())) {
			return;
		}

		Object serviceBean = SpringBeanUtil.getBean(model.getFdServiceBean());
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setBus(bus);

		// 配置拦截器
		setInterceptors(svrFactory, model.getFdSoapMsgLogging());

		Class clazz = com.landray.kmss.util.ClassUtils.forName(model.getFdServiceClass());
		svrFactory.setServiceClass(clazz);
		svrFactory.setAddress(model.getFdAddress());
		svrFactory.setServiceBean(serviceBean);
		Server server = svrFactory.create();

		model.setFdServiceStatus(SysWsConstant.SERVICE_STATUS_START);
		this.sysWsMap.put(model.getFdServiceBean(), server);
		getBaseDao().update(model);
	}

	/**
	 * 启动数据库中所有为自动类型的服务，并启动日志清理
	 */
	@Override
	public void startAllServices() {

		// 从数据库加载WebService注册信息
		ISysWebserviceMainDao dao = (ISysWebserviceMainDao) getBaseDao();
		List<SysWebserviceMain> dbWsList = dao.findServiceList();

		for (SysWebserviceMain model : dbWsList) {

			// 捕获异常，当一个服务启动出现异常不会影响其它服务
			try {
				// 将服务状态初始化为停止
				model.setFdServiceStatus(SysWsConstant.SERVICE_STATUS_STOP);
				getBaseDao().update(model);

				// 如果启动类型为自动模式，则启动服务
				if (SysWsConstant.STARTUP_TYPE_AUTO.equals(model
						.getFdStartupType())) {
					startService(model);
				}
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}

		// 启动日志清理的定时任务
		// startLogTask();
	}

	/**
	 * 停止服务并发送消息
	 */
	@Override
	public void stopService(String fdId) throws Exception {
		SysWebserviceMain model = (SysWebserviceMain) findByPrimaryKey(fdId);

		// 服务未被禁用
		if (SysWsConstant.STARTUP_TYPE_AUTO.equals(model.getFdStartupType())
				|| SysWsConstant.STARTUP_TYPE_MANUAL.equals(model
						.getFdStartupType())) {

			stopService(model);
			publishMessage(SysWebserviceClusterMessage.OPT_STOP, fdId);
		}
	}

	/**
	 * 停止服务
	 */
	private void stopServiceWithoutMessage(String fdId) throws Exception {
		SysWebserviceMain model = (SysWebserviceMain) findByPrimaryKey(fdId);

		// 服务未被禁用
		if (SysWsConstant.STARTUP_TYPE_AUTO.equals(model.getFdStartupType())
				|| SysWsConstant.STARTUP_TYPE_MANUAL.equals(model
						.getFdStartupType())) {

			stopService(model);
		}
	}

	/**
	 * 批量停止多个服务
	 */
	@Override
	public void stopService(String[] fdIds) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			stopService(fdIds[i]);
		}
	}

	/**
	 * 停止服务
	 */
	@Override
	public synchronized void stopService(SysWebserviceMain model)
			throws Exception {
		// 记录日志
		if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND,
				SysWebserviceMain.class.getName())) {
			UserOperContentHelper.putFind(model);
		}

		// 服务已经停止，直接返回
		String serviceBean = model.getFdServiceBean();
		if (!sysWsMap.containsKey(serviceBean)) {
			return;
		}

		Server server = sysWsMap.get(serviceBean);
		this.sysWsMap.remove(serviceBean);
		server.destroy();

		model.setFdServiceStatus(SysWsConstant.SERVICE_STATUS_STOP);
		getBaseDao().update(model);
	}

	/**
	 * 根据服务标识查找服务注册信息
	 */
	@Override
	public SysWebserviceMain findByServiceBean(String serviceBean)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereStr = "sysWebserviceMain.fdServiceBean=:fdServiceBean";
		// 绑定查询参数
		hqlInfo.setParameter("fdServiceBean", serviceBean, StandardBasicTypes.STRING);
		hqlInfo.setWhereBlock(whereStr);

		List<SysWebserviceMain> modelList = getBaseDao().findValue(hqlInfo);

		if (ArrayUtil.isEmpty(modelList)) {
			return null;
		}

		return modelList.get(0);
	}

	/**
	 * 生成Java客户端源码供用户下载
	 */
	@Override
	public String genClient(String fdId, String urlPrefix) throws Exception {
		SysWebserviceMain model = (SysWebserviceMain) findByPrimaryKey(fdId);
		if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND,
				SysWebserviceMain.class.getName())) {
			UserOperContentHelper.putFind(model);
		}
		String address = getAddress(urlPrefix, model.getFdAddress());
		String serviceName = model.getFdName();
		String serviceClass = model.getFdServiceClass();
		String serviceBean = model.getFdServiceBean();
		List<SysWebserviceUser> userList = model.getFdUser();

		String zipFileName;
		GenCode gen = new GenCode();
		if (ArrayUtil.isEmpty(userList)) {
			zipFileName = gen.main(address, serviceName, serviceClass,
					serviceBean);
		} else {
			SysWebserviceUser user = userList.get(0);
			zipFileName = gen.main(address, serviceName, serviceClass,
					serviceBean, user.getFdLoginId(), user.getFdPassword());
		}

		return zipFileName;
	}

	/**
	 * 获取WebService的完整访问地址
	 * 
	 * @param urlPrefix
	 *            URL上下文路径
	 * @param address
	 *            访问地址的相对路径
	 * @return
	 */
	private String getAddress(String urlPrefix, String address) {
		StringBuffer sb = new StringBuffer();
		sb.append(urlPrefix).append(urlPattern).append(address);

		return sb.toString();
	}

	/**
	 * 配置WebService服务端的拦截器组
	 * 
	 * @param svrFactory
	 */
	private void setInterceptors(JaxWsServerFactoryBean svrFactory,
			Boolean fdSoapMsgLogging) {

		if (fdSoapMsgLogging == null) {
			fdSoapMsgLogging = false;
		}

		// 添加拦截器
		svrFactory.getInInterceptors().add(new CallBeforeInterceptor());
		svrFactory.getInInterceptors().add(new CallAfterInterceptor());
		svrFactory.getInInterceptors().add(new CallInInterceptor());
		svrFactory.getInInterceptors().add(new ErrorInterceptor());
		svrFactory.getOutInterceptors().add(new CallOutInterceptor());
		// 记录消息报文调试日志
		if (fdSoapMsgLogging) {
			svrFactory.getInInterceptors().add(new LoggingInInterceptor());
			svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		}
	}

	/**
	 * 发布消息
	 */
	private void publishMessage(int opt, String id) {
		SysWebserviceClusterMessage message = new SysWebserviceClusterMessage(
				opt, id);
		try {
			MessageCenter.getInstance().sendToOther(message);
		} catch (Exception e) {
			logger.error("发布集群消息时发生错误", e);
		}
	}

	private IMessageQueue queue = new UniqueMessageQueue();

	@Override
	public IMessageQueue getMessageQueue() {
		return queue;
	}

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		SysWebserviceClusterMessage sysWsMsg = (SysWebserviceClusterMessage) message;
		if (logger.isDebugEnabled()) {
            logger.debug("接收信息：" + sysWsMsg);
        }

		if (sysWsMsg.getOperation() == SysWebserviceClusterMessage.OPT_STOP) {
			stopServiceWithoutMessage(sysWsMsg.getServiceId());
		} else if (sysWsMsg.getOperation() == SysWebserviceClusterMessage.OPT_START) {
			startServiceWithoutMessage(sysWsMsg.getServiceId());
		}
	}

}
