package com.landray.kmss.common.convertor;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.ParseObjUtil;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.util.ObjectUtil;

/**
 * 将Form模型转换为域模型，常用于一对一的转换
 * 
 * @author 叶中奇
 * 
 */
public class FormConvertor_FormToModel extends BaseFormConvertor implements
		IFormToModelConvertor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FormConvertor_FormToModel.class);

	/**
	 * @param tPropertyName
	 *            目标属性名
	 */
	public FormConvertor_FormToModel(String tPropertyName) {
		this.tPropertyName = tPropertyName;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		IExtendForm sForm;
		try {
			sForm = (IExtendForm) PropertyUtils.getProperty(ctx.getSObject(),
					ctx.getSPropertyName());
		} catch (NestedNullException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取属性" + ctx.getSPropertyName()
						+ "的值时中间出现null值，不转换该属性");
			}
			return;
		}
		if (sForm == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("属性" + ctx.getSPropertyName() + "的值为null，不转换该属性");
			}
			return;
		}
		//日志操作对象
		IUserOper currentOper = getLogOper(ctx);
		//创建Model 并设置为当前上下文的处理对象
		createModel(ctx, sForm, currentOper, getTPropertyName());
		BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(), ctx
				.getBaseService().convertFormToModel(sForm, null, ctx));
	}

	/**
	 * 获取日志操作对象
	 * 
	 * @param ctx
	 * @return
	 */
	protected IUserOper getLogOper(ConvertorContext ctx) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return null;
		}
		if (ctx.getLogOper() == null) {
			return null;
		}
		return ctx.getLogOper();
	}

	/**
	 * List中创建Model 并设置到上下文对象中
	 * 
	 * @param ctx
	 * @param sForm
	 * @param oper
	 * @param propertyName
	 */
	protected void createModel(ConvertorContext ctx, IExtendForm sForm, IUserOper oper, String propertyName) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if (ctx.getLogOper() == null) {
			return;
		}
		if (oper == null) {
			return;
		}
		ctx.setLogOper(oper.createOper4Model(sForm, propertyName));
	}
	
	public FormConvertor_FormToModel setTPropertyName(String propertyName) {
		tPropertyName = propertyName;
		return this;
	}

	@Override
    public void examine(ExamineContext context, Class fromClass, Class toClass) {
		String sProperty = (String) context.getParameter("sProperty");
		Object form = context.getParameter("form");
		try {
			Object value = PropertyUtils.getProperty(form, sProperty);
			if (value == null) {
				context.addError(fromClass, sProperty, 1, "属性" + sProperty
						+ "必须有初值");
			} else if (!(value instanceof IExtendForm)) {
				context.addError(fromClass, sProperty, 1, "属性" + sProperty
						+ "必须初始化为一个Form的实体");
			} else {
				IExtendForm sPropertyObj = (IExtendForm) value;
				if (sPropertyObj.getModelClass() == null) {
					context.addError(fromClass, sProperty, 1, "属性" + sProperty
							+ "对应的Form的getModelClass不能返回null");
				}
			}
		} catch (Exception e) {
			context.addError(fromClass, sProperty, 1, "属性" + sProperty
					+ "的值无法获取");
		}

		PropertyDescriptor descriptor = ObjectUtil.getPropertyDescriptor(
				toClass, tPropertyName);
		if (descriptor == null || descriptor.getWriteMethod() == null) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "无setter方法");
		} else if (!IBaseModel.class.isAssignableFrom(descriptor
				.getPropertyType())) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "必须是一个Model");
		}
	}
}
