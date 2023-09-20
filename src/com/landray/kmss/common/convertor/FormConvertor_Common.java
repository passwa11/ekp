package com.landray.kmss.common.convertor;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 将Form模型的简单属性转换为域模型的属性，常用于普通类型的转换
 * 
 * @author 叶中奇
 */
public class FormConvertor_Common extends BaseFormConvertor implements
		IFormToModelConvertor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FormConvertor_Common.class);

	private String dateTimeType;

	/**
	 * @param tPropertyName
	 *            目标属性名
	 */
	public FormConvertor_Common(String tPropertyName) {
		this.tPropertyName = tPropertyName;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		Object obj;
		try {
			obj = PropertyUtils.getProperty(ctx.getSObject(), ctx
					.getSPropertyName());
		} catch (NestedNullException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取属性" + ctx.getSPropertyName()
						+ "的值时中间出现null值，不转换该属性");
			}
			return;
		}
		if (obj != null) {
			if (obj instanceof String) {
				Class propertyType = PropertyUtils.getPropertyType(ctx
						.getTObject(), getTPropertyName());
				if (Date.class.isAssignableFrom(propertyType)) {
					obj = DateUtil.convertStringToDate((String) obj,
							getDateTimeType(), ctx.getRequestContext()
									.getLocale());
				} else if (Number.class.isAssignableFrom(propertyType)) {
					if (StringUtil.isNull(obj.toString())) {
						PropertyUtils.setSimpleProperty(ctx.getTObject(),
								getTPropertyName(), null);
						// 记录日志
						saveOperLog(ctx, null);
						return;
					}
				} 
			}
			
			if (obj instanceof Map && "dynamicMap".equals(getTPropertyName())
					&& "dynamicMap".equals(ctx
							.getSPropertyName())) {
				Map dynamicMap_model = (Map) PropertyUtils
						.getProperty(ctx.getTObject(), "dynamicMap");
				dynamicMap_model.putAll((Map) obj);
			} else {
				// 记录日志
				saveOperLog(ctx, obj);
				BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(),
						obj);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("属性" + ctx.getSPropertyName() + "的值为null，不转换该属性");
			}
		}
	}

	/**
	 * 保存操作日志
	 * 
	 * @param ctx
	 * @param obj
	 */
	public void saveOperLog(ConvertorContext ctx, Object obj) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if(ctx.getLogOper()==null){
			return;
		}
		UserOperConvertHelper.convertCommon(ctx, ctx.getTObject(), getTPropertyName(), ctx.getSObject(),
				ctx.getSPropertyName(), obj);
	}

	public String getDateTimeType() {
		return dateTimeType;
	}

	/**
	 * 设置日期的转换格式，为DateUtil中的常量
	 * 
	 * @param dateTimeType
	 * @return
	 */
	public FormConvertor_Common setDateTimeType(String dateTimeType) {
		this.dateTimeType = dateTimeType;
		return this;
	}

	public FormConvertor_Common setTPropertyName(String propertyName) {
		tPropertyName = propertyName;
		return this;
	}
}
