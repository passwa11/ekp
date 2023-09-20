package com.landray.kmss.common.module.core.proxy.generator;

import com.landray.kmss.common.module.core.cache.ICachedProxyGenerator;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.proxy.ServiceProxy;
import com.landray.kmss.common.module.util.ExceptionUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.springframework.util.Assert;


/**
 * @author 严明镜
 * @version 1.0 2021年03月11日
 */
public class ServiceProxyGenerator implements ICachedProxyGenerator<IDynamicProxy> {

	private final String beanName;

	public ServiceProxyGenerator(String beanName) {
		Assert.isTrue(beanName != null, "beanName不可为空");
		this.beanName = beanName;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public boolean valid() {
		return true;
	}

	@Override
	public IDynamicProxy createProxy() {
		try {
			Object service = SpringBeanUtil.getBean(beanName);
			//安全模式下返回null
			if (service == null) {
				return null;
			}
			return new ServiceProxy(service);
		} catch (Exception e) {
			ExceptionUtil.printException("获取ServiceBean失败:" + beanName, e);
		}
		return null;
	}

}
