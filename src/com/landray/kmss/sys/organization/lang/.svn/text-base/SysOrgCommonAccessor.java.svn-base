package com.landray.kmss.sys.organization.lang;

import com.landray.kmss.sys.language.interfaces.SysLangPropCommonSetter;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

public class SysOrgCommonAccessor implements PropertyAccess ,PropertyAccessStrategy{

	private SysOrgCommonGetter getter;
	private SysLangPropCommonSetter setter;
	
	public SysOrgCommonAccessor() {
		super();
	}
	
	public SysOrgCommonAccessor(String propertyName) {
		this.getter = new SysOrgCommonGetter(propertyName);
		this.setter = new SysLangPropCommonSetter(propertyName);
	}
	
	@Override
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return this;
	}

	@Override
	public Getter getGetter() {
		return getter;
	}

	@Override
	public Setter getSetter() {
		return setter;
	}

	@Override
	public PropertyAccess buildPropertyAccess(Class containerJavaType, String propertyName) {
		return new SysOrgCommonAccessor(propertyName);
	}

}
