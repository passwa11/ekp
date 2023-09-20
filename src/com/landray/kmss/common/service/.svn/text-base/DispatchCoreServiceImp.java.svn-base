package com.landray.kmss.common.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当主域模型做了一定的改动时，由该类通知所有“已注册”的机制响应动作
 * 
 * @author 叶中奇
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class DispatchCoreServiceImp implements IDispatchCoreService,
		ApplicationContextAware, ApplicationListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DispatchCoreServiceImp.class);

	private List mechanismServices;

	private ThreadLocal allServices = new ThreadLocal();

	@Override
    public void add(IBaseModel model) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发add");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.add(model);
		}
	}

	@Override
    public void cloneModelToForm(IExtendForm form, IBaseModel model,
                                 RequestContext requestContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发cloneModelToForm");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.cloneModelToForm(form, model, requestContext);
		}
	}

	@Override
    public void convertFormToModel(IExtendForm form, IBaseModel model,
                                   RequestContext requestContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发convertFormToModel");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.convertFormToModel(form, model, requestContext);
		}
	}

	@Override
    public void convertModelToForm(IExtendForm form, IBaseModel model,
                                   RequestContext requestContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发convertModelToForm");
		}
		List serviceList = getServiceList();

		// 用于性能的调试（必须开启info级别的日志和URL增加debugger参数）
		boolean isDebugger = false;
		if (logger.isInfoEnabled() && requestContext != null && StringUtil.isNotNull(requestContext.getParameter("debugger"))) {
			isDebugger = true;
		}
		// 秒表计时器
		StopWatch sw = null;
		if (isDebugger) {
			// 性能调试时，创建一个基本主文档的秒表
			sw = new StopWatch(model.getClass().getName() + "|" + model.getFdId());
		}
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			if (isDebugger) {
				// 性能调试时，监测所有机制的转换性能
				String serviceName = null;
				if (!outerServiceMap.containsKey(service)) {
					serviceName = getTargetBean(service).getClass().getName();
					outerServiceMap.put(service, serviceName);
				} else {
					serviceName = outerServiceMap.get(service);
				}
				// 每个机制做一次计时统计
				sw.start(serviceName);
			}
			service.convertModelToForm(form, model, requestContext);
			if (isDebugger) {
				sw.stop();
			}
		}
		if (isDebugger) {
			// 日志中打印秒表计时信息
			logger.info("秒表执行的数量：{}", sw.getTaskCount());
			logger.info("详细耗时情况：{}", sw.prettyPrint());
		}
	}

	/**
	 * 用于秒表计时的机制类名缓存
	 */
	private Map<IBaseCoreOuterService, String> outerServiceMap = new HashMap<>(16);

	/**
	 * 获取原始对象
	 *
	 * @param bean
	 * @return
	 */
	private Object getTargetBean(Object bean) {
		if (bean != null) {
			// 不是代理对象
			if (!AopUtils.isAopProxy(bean)) {
				return bean;
			}
			try {
				if (AopUtils.isJdkDynamicProxy(bean)) {
					// jdk代理
					Field h = bean.getClass().getSuperclass().getDeclaredField("h");
					h.setAccessible(true);
					AopProxy aopProxy = (AopProxy) h.get(bean);

					Field advised = aopProxy.getClass().getDeclaredField("advised");
					advised.setAccessible(true);

					bean = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
				} else {
					// cglib代理
					Field h = bean.getClass().getDeclaredField("CGLIB$CALLBACK_0");
					h.setAccessible(true);
					Object dynamicAdvisedInterceptor = h.get(bean);

					Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
					advised.setAccessible(true);

					bean = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
				}
				return getTargetBean(bean);
			} catch (Exception e) {
				logger.debug("获取原始对象失败", e);
			}
		}
		return null;
	}

	@Override
    public void delete(IBaseModel model) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发delete");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.delete(model);
		}
	}

	private List getServiceList() {
		if (mechanismServices != null) {
			// spring启动完成才会调用机制的service
			List serviceList = (List) allServices.get();
			if (serviceList == null) {
				serviceList = new ArrayList();
				serviceList.addAll(mechanismServices);
				allServices.set(serviceList);
			}
			return serviceList;
		}
		return new ArrayList();
	}

	@Override
    public void initFormSetting(IExtendForm mainForm, String mainKey,
                                IBaseModel settingModel, String settingKey,
                                RequestContext requestContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发initFormSetting");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.initFormSetting(mainForm, mainKey, settingModel,
					settingKey, requestContext);
		}
	}

	@Override
    public void initModelSetting(IBaseModel mainModel, String mainKey,
                                 IBaseModel settingModel, String settingKey) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发initModelSetting");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.initModelSetting(mainModel, mainKey, settingModel,
					settingKey);
		}
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringBeanUtil.setApplicationContext(applicationContext);
	}

	@Override
    public void update(IBaseModel model) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发update");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.update(model);
		}
	}

	@Override
    public void addService(IAdditionalService service) {
		List serviceList = getServiceList();
		if (!serviceList.contains(service)) {
			serviceList.add(service);
		}
	}

	@Override
    public void removeService(IAdditionalService service) {
		getServiceList().remove(service);
	}

	@Override
    public void resetService() {
		allServices.remove();
	}

	@Override
    public List<?> exportData(String id, String modelName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发exportToFile");
		}
		List<Object> rtn = new ArrayList<Object>();
		List<?> serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			List<?> datas = service.exportData(id, modelName);
			if (datas != null && !datas.isEmpty()) {
				rtn.addAll(datas);
			}
		}
		return rtn;
	}

	@Override
    public Class<?> getSourceClass() {
		return getClass();
	}

	@Override
    public void onApplicationEvent(ApplicationEvent event) {
		if (event == null || !(event instanceof ContextRefreshedEvent)) {
			return;
		}
		mechanismServices = SpringBeanUtil.getBeansForType(
				ICoreOuterService.class, "dispatchCoreService");
	}

	@Override
	public void deleteSoft(IBaseModel model) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发deleteSoft");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.deleteSoft(model);
		}
	}

	@Override
	public void update2Recover(IBaseModel modelObj) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行机制分发update2Recover");
		}
		List serviceList = getServiceList();
		for (int i = 0; i < serviceList.size(); i++) {
			IBaseCoreOuterService service = (IBaseCoreOuterService) serviceList
					.get(i);
			service.update2Recover(modelObj);
		}
	}
}
