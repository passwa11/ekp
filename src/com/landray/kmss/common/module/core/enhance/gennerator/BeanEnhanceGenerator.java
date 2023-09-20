package com.landray.kmss.common.module.core.enhance.gennerator;

import com.landray.kmss.common.module.core.cache.ICachedProxyGenerator;
import com.landray.kmss.common.module.core.enhance.BeanEnhance;
import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.util.RuntimeClassUtil;
import org.springframework.util.Assert;

/**
 * @author 严明镜
 * @version 1.0 2021年03月11日
 */
public class BeanEnhanceGenerator implements ICachedProxyGenerator<IBeanEnhance<?>> {

	private final String modelFullName;

	public BeanEnhanceGenerator(String modelFullName) {
		Assert.isTrue(modelFullName != null, "modelFullName不可为空");
		this.modelFullName = modelFullName;
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
	public IBeanEnhance<?> createProxy() {
		Object instance = RuntimeClassUtil.newInstanceNoThorw(RuntimeClassUtil.forNameNoThrow(modelFullName));
		if (instance == null) {
			return null;
		}
		return new BeanEnhance<>(instance);
	}

}
