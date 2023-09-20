package com.landray.kmss.sys.time.service.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.time.service.ISysTimePatchworkService;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 补班设置业务接口实现
 */
public class SysTimePatchworkServiceImp extends BaseServiceImp implements
		ISysTimePatchworkService, ApplicationContextAware,
		IEventMulticasterAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private IEventMulticaster multicaster;

	@Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String id = super.add(modelObj);
		publishAttendEvent();
		return id;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		publishAttendEvent();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		publishAttendEvent();
	}

	private void publishAttendEvent() {
		this.flushHibernateSession();
		this.clearHibernateSession();
		multicaster.attatchEvent(
				new EventOfTransactionCommit(StringUtils.EMPTY),
				new IEventCallBack() {
					@Override
					public void execute(ApplicationEvent arg0)
							throws Throwable {
						// 发送事件通知
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("fdTimeAreaChange", "true");
						applicationContext.publishEvent(new Event_Common(
								"regenUserAttendMain", params));
					}
				});
	}

}
