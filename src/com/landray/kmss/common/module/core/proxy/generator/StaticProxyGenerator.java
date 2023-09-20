package com.landray.kmss.common.module.core.proxy.generator;

import com.landray.kmss.common.module.core.cache.ICachedProxyGenerator;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.proxy.StaticProxy;
import com.landray.kmss.common.module.util.ExceptionUtil;
import com.landray.kmss.util.ClassUtils;
import org.springframework.util.Assert;

/**
 * @author 严明镜
 * @version 1.0 2021年03月11日
 */
public class StaticProxyGenerator implements ICachedProxyGenerator<IDynamicProxy> {

	private final String modelFullName;

	public StaticProxyGenerator(String modelFullName) {
		Assert.isTrue(modelFullName != null, "modelFullName不可为空");
		this.modelFullName = modelFullName;
	}

	@Override
	public String getKey() {
		return "STATIC" + "_" + modelFullName;
	}

	@Override
	public boolean valid() {
		return true;
	}

	@Override
	public IDynamicProxy createProxy() {
		try {
			return new StaticProxy(ClassUtils.forName(modelFullName).newInstance());
		} catch (ClassNotFoundException e) {
			ExceptionUtil.throwRuntimeException("未找到类:" + modelFullName, e);
		} catch (InstantiationException e) {
			ExceptionUtil.throwRuntimeException("无法实例化类:" + modelFullName, e);
		} catch (IllegalAccessException e) {
			ExceptionUtil.throwRuntimeException("无法访问类构造函数:" + modelFullName, e);
		} catch (Exception e) {
			ExceptionUtil.throwRuntimeException("创建类失败:" + modelFullName, e);
		}
		return null;
	}

}
