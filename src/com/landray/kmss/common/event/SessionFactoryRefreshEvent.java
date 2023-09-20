package com.landray.kmss.common.event;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationEvent;

/**
 * Hibernate 刷新 SessionFactory 事件，是在创建动态表单的时候刷新 HibernateDaoSupport.sessionFactory
 * @author huangzz
 *
 */
public class SessionFactoryRefreshEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;

	public SessionFactoryRefreshEvent(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
