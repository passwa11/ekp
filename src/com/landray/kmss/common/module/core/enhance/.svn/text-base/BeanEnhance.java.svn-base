package com.landray.kmss.common.module.core.enhance;

import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseCoreInnerModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.proxy.callback.IProxyCallBack;
import com.landray.kmss.common.module.core.proxy.invoker.MethodInvoker;
import com.landray.kmss.common.module.core.util.RuntimeClassUtil;
import com.landray.kmss.common.module.util.ExceptionUtil;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 严明镜
 * @version 1.0 2021年02月19日
 */
public class BeanEnhance<M> implements IBeanEnhance<M> {

	protected final M object;

	private final MethodInvoker invoker;

	private IProxyCallBack callBack;

	public BeanEnhance(M object) {
		this.object = object;
		this.invoker = new MethodInvoker(object);
		this.callBack = null;
	}

	/**
	 * set字段
	 */
	@Override
	public void setProperty(String property, Object value) {
		try {
			PropertyUtils.setProperty(object, property, value);
		} catch (IllegalAccessException e) {
			ExceptionUtil.throwRuntimeException("无法访问该字段的set方法:" + property, e);
		} catch (InvocationTargetException e) {
			ExceptionUtil.throwRuntimeException("调用字段的set方法时发生异常:" + property, e);
		} catch (NoSuchMethodException e) {
			ExceptionUtil.throwRuntimeException("找不到该字段（请注意大小写），或找不到其set方法:" + property, e);
		}
	}

	/**
	 * get字段
	 */
	@Override
	public Object getProperty(String property) {
		try {
			return PropertyUtils.getProperty(object, property);
		} catch (IllegalAccessException e) {
			ExceptionUtil.throwRuntimeException("无法访问该字段的get方法:" + property, e);
		} catch (InvocationTargetException | IllegalArgumentException e) {
			ExceptionUtil.throwRuntimeException("调用字段的get方法时发生异常:" + property, e);
		} catch (NoSuchMethodException e) {
			ExceptionUtil.throwRuntimeException("找不到该字段的get方法:" + property, e);
		}
		return null;
	}

	/**
	 * get字段
	 */
	@Override
	public <T> T getProperty(String property, Class<T> clz) {
		Object propertyValue = getProperty(property);
		if(propertyValue != null) {
			return clz.cast(propertyValue);
		}
		return null;
	}

	/**
	 * 直接将对象机制类放入机制Map中(Model/Form)
	 *
	 * @param mechName   机制名
	 * @param mechObject 机制对象
	 */
	@Override
	public void setMechanism(String mechName, Object mechObject) {
		getMechanismMap().put(mechName, mechObject);
	}

	/**
	 * set机制Model
	 */
	@Override
	public void setMechanism(IBaseCoreInnerModel model) {
		setMechanism(getMechanismName(model.getClass()), model);
	}

	/**
	 * 自动取出包装类中的机制对象，并设置到当前包装类的机制Map中
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void setMechanism(IBeanEnhance enhance) {
		Object object = enhance.get();
		if (object instanceof IBaseCoreInnerModel) {
			setMechanism((IBaseCoreInnerModel) object);
		} else if (object instanceof BaseCoreInnerForm) {
			setMechanism((BaseCoreInnerForm) object);
		} else {
			throw new IllegalArgumentException("该方法传入的增强对象必须是IBaseCoreInnerModel或BaseCoreInnerForm，否则请使用IBeanEnhance#setMechanism(mechName, mechObject)");
		}
	}

	/**
	 * get机制Model
	 */
	@Override
	public <T extends IBaseCoreInnerModel> T getMechanismModel(Class<T> clz) {
		return getMechanism(getMechanismName(clz), clz);
	}

	/**
	 * 获取机制名
	 */
	private <T extends IBaseCoreInnerModel> String getMechanismName(Class<T> clz) {
		Assert.isTrue(clz != null, "机制对象不能为空");
		String mechanismName = RuntimeClassUtil.newInstance(clz).getMechanismName();
		Assert.isTrue(StringUtil.isNotNull(mechanismName), clz + ".getMechanismName()获取的机制名为空");
		return mechanismName;
	}

	/**
	 * 直接获取机制类
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public IBeanEnhance getMechanism(String mechanismKey) {
		Object mechBean = getMechanismBean(mechanismKey);
		if (mechBean == null) {
			return null;
		}
		return ModuleCenter.enhanceBean(mechBean);
	}

	/**
	 * set机制Form
	 */
	@Override
	public void setMechanism(BaseCoreInnerForm form) {
		setMechanism(getMechanismName4Form(form.getClass()), form);
	}

	/**
	 * get机制Form
	 */
	@Override
	public <T extends BaseCoreInnerForm> T getMechanismForm(Class<T> clz) {
		return getMechanism(getMechanismName4Form(clz), clz);
	}

	/**
	 * 获取机制名
	 */
	private <T extends BaseCoreInnerForm> String getMechanismName4Form(Class<T> clz) {
		Assert.isTrue(clz != null, "机制对象不能为空");
		Class<?> modelClass = RuntimeClassUtil.newInstance(clz).getModelClass();
		Assert.isTrue(modelClass != null, "机制Form(" + clz + ")对应ModelClass不能为空！");
		Assert.isAssignable(IBaseCoreInnerModel.class, modelClass, "机制Form(" + clz + ")对应ModelClass(" + modelClass + ")应实现IBaseCoreInnerModel");
		String mechanismName = ((IBaseCoreInnerModel) RuntimeClassUtil.newInstance(modelClass)).getMechanismName();
		Assert.isTrue(StringUtil.isNotNull(mechanismName), clz + " 通过(" + modelClass + ".getMechanismName())获取的机制名为空");
		return mechanismName;
	}

	/**
	 * 直接获取并转换机制类，如果map中不存在该机制类，则根据clz创建一个空机制类，并存入map中
	 */
	protected <T> T getMechanism(String mechName, Class<T> clz) {
		Object mechBean = getMechanismBean(mechName);
		if (mechBean == null) {
			T mechanism = RuntimeClassUtil.newInstanceNoThorw(clz);
			getMechanismMap().put(mechName, mechanism);
			return mechanism;
		}
		return clz.cast(mechBean);
	}

	protected Object getMechanismBean(String mechName) {
		Map<String, Object> mechanismMap = getMechanismMap();
		if (mechanismMap != null && mechanismMap.containsKey(mechName)) {
			return mechanismMap.get(mechName);
		}
		return null;
	}

	protected Map<String, Object> getMechanismMap() {
		if (object instanceof IBaseModel) {
			return ((IBaseModel) object).getMechanismMap();
		} else if (object instanceof IExtendForm) {
			return ((IExtendForm) object).getMechanismMap();
		}
		throw new RuntimeException("Model/Form为空或未继承于IBaseModel/IExtendForm");
	}

	@Override
	public M get() {
		return object;
	}

	@Override
	public <T> T get(Class<T> clz) {
		return clz.cast(object);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<M> getBeanClass() {
		return (Class<M>) object.getClass();
	}

	@Override
	public Object invoke(String methodName, Object... parameters) {
		try {
			invoker.prepare(methodName, parameters);
			if(callBack != null) {
				callBack.afterPrepare(invoker);
			}
			return invoker.invoke();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("未找到" + object.getClass() + "的" + methodName + "方法", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("无法访问" + object.getClass() + "的" + methodName + "方法", e);
		} catch (Exception e) {
			throw new RuntimeException("调用" + object.getClass() + "的" + methodName + "方法出错", e);
		}
	}

	@Override
	public void setPrepareCallBack(IProxyCallBack callBack) {
		this.callBack = callBack;
	}
}
