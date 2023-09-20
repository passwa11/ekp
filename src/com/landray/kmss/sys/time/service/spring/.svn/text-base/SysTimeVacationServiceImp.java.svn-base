package com.landray.kmss.sys.time.service.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.time.service.ISysTimeVacationService;
import com.landray.kmss.util.TransactionUtils;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 休假设置业务接口实现
 */
public class SysTimeVacationServiceImp extends BaseServiceImp implements
		ISysTimeVacationService, ApplicationContextAware,
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
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			String id = super.add(modelObj);
			TransactionUtils.getTransactionManager().commit(status);
			publishAttendEvent();
			return id;
		}
		catch (Exception e){
			if(status != null){
				TransactionUtils.rollback(status);
			}
			throw e;
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		TransactionStatus status = null;
		try{
			status = TransactionUtils.beginNewTransaction();
			super.delete(modelObj);
			TransactionUtils.getTransactionManager().commit(status);
			publishAttendEvent();
		}
		catch (Exception e){
			if(status != null){
				TransactionUtils.rollback(status);
			}
			throw e;
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		TransactionStatus status = null;
		try{
			status = TransactionUtils.beginNewTransaction();
			super.update(modelObj);
			TransactionUtils.getTransactionManager().commit(status);
			publishAttendEvent();
		}
		catch (Exception e){
			if(status != null){
				TransactionUtils.rollback(status);
			}
			throw e;
		}
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
