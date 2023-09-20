package com.landray.kmss.sys.organization.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.property.access.spi.Getter;

public class SysOrgCommonGetter implements Getter {

	String propertyName = null;

	public SysOrgCommonGetter(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public Object get(Object model) throws HibernateException {
		if (model == null) {
			return null;
		}
		try {
			return (String) PropertyUtils.getProperty(model,
					propertyName + "Ori");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Method getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getReturnType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getMember() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getForInsert(Object owner, Map mergeMap, SharedSessionContractImplementor session) {
		return get(owner);
	}

}
