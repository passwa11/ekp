package com.landray.kmss.common.convertor;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.ParseObjUtil;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.oper.IUserAddOper;
import com.landray.kmss.sys.log.util.oper.IUserDetailOper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.IUserUpdateOper;
import com.landray.kmss.sys.log.xml.model.LogConvertContext;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.ObjectUtil;
import com.landray.sso.client.util.StringUtil;

/**
 * 将Form模型Map转换为域模型Map，常用于一对多的转换
 * 
 * @author 叶中奇
 * 
 */
public class FormConvertor_FormMapToModelMap extends BaseFormConvertor
		implements IFormToModelConvertor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FormConvertor_FormMapToModelMap.class);

	private String tChildToParentProperty;

	/**
	 * @param tPropertyName
	 *            目标属性名
	 * @param tChildToParentProperty
	 *            子域模型到父域模型的属性名
	 */
	public FormConvertor_FormMapToModelMap(String tPropertyName,
			String tChildToParentProperty) {
		this.tPropertyName = tPropertyName;
		this.tChildToParentProperty = tChildToParentProperty;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		Map sMap;
		try {
			sMap = (Map) PropertyUtils.getProperty(ctx.getSObject(), ctx
					.getSPropertyName());
		} catch (NestedNullException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取属性" + ctx.getSPropertyName()
						+ "的值时中间出现null值，不转换该属性");
			}
			return;
		}
		if (sMap == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("属性" + ctx.getSPropertyName() + "的值为null，不转换该属性");
			}
			return;
		}
		Map tMap = (Map) PropertyUtils.getProperty(ctx.getTObject(),
				getTPropertyName());
		Map cloneMap = (Map)cloneOldValue(tMap);
		if (tMap == null) {
			tMap = new HashMap();
		} else {
			tMap.clear();
		}

		//日志操作对象
		IUserOper currentOper = getLogOper(ctx);
		IUserDetailOper operDetail = null;
		Iterator keys = sMap.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			IExtendForm sForm = (IExtendForm) sMap.get(key);
			if (sForm != null) {
				// 创建List 放在此处防止sList无数据时出现空detail
				operDetail = createDetail(operDetail, ctx, currentOper);
				// List中创建Model 并设置到上下文对象中
				IUserOper oper = createAddOrUpdateOper(ctx, sForm, operDetail, cloneMap);
				IBaseModel tModel = ctx.getBaseService().convertFormToModel(
						sForm, null, ctx);
				setAddFdId(oper, tModel);
				BeanUtils.copyProperty(tModel, getTChildToParentProperty(), ctx
						.getTObject());
				tMap.put(key, tModel);
			}
		}
		//未转换的model为delete类型
		createDeleteOper(operDetail, cloneMap, tMap);
		BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(), tMap);
	}

	/**
	 * 若fdId为空，则从model中获取fdId
	 * 
	 * @param oper
	 * @param tModel
	 */
	protected void setAddFdId(IUserOper oper, IBaseModel tModel) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if(oper == null || tModel == null ){
			return;
		}
		if (StringUtil.isNull(oper.getFdId())) {
			oper.setFdId(tModel.getFdId());
		}
	}

	/**
	 * 由于原list会被清空，日志要做增删改的比较判断，则需先记录原list
	 * 
	 * @param currentObj
	 * @return
	 */
	protected Object cloneOldValue(Object currentObj) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return null;
		}
		Map sMap = new HashMap();
		if (currentObj instanceof Map) {
			Set keySet = ((Map) currentObj).keySet();
			for (Object key : keySet) {
				Object value = ((Map) currentObj).get(key);
				sMap.put(key, value);
			}
		}
		return sMap;
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
	 * 创建List 相同currentOper,propertyName不会创建多余List
	 * 
	 * @param operDetail
	 *            如果operDetail不为空，则直接return operDetail
	 * @param ctx
	 * @param currentOper
	 * @return
	 */
	protected IUserDetailOper createDetail(IUserDetailOper operDetail, ConvertorContext ctx, IUserOper currentOper) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return null;
		}
		if (operDetail != null) {
			return operDetail;
		}
		if (currentOper == null) {
			return null;
		}
		return currentOper.createOper4Detail(ctx.getSPropertyName());
	}

	/**
	 * Map中根据原map，判断为新增、修改，创建对应Oper 并设置到上下文对象中
	 * 
	 * @param ctx
	 * @param sForm
	 * @param operDetail
	 * @param cloneMap
	 */
	protected IUserOper createAddOrUpdateOper(ConvertorContext ctx, IExtendForm sForm, IUserDetailOper operDetail,
			Map cloneMap) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return null;
		}
		if (ctx.getLogOper() == null) {
			return null;
		}
		if (operDetail == null) {
			return null;
		}
		try {
			String fdId = sForm.getFdId();
			String displayName = ParseObjUtil.getDisplayName(sForm);

			Set keySet = cloneMap.keySet();
			for (Object key : keySet) {
				Object value = cloneMap.get(key);
				if (value instanceof IBaseModel) {
					String oldMapFdId = ((IBaseModel) value).getFdId();
					if (fdId.equals(oldMapFdId)) {
						// 修改(原list中存在)
						IUserUpdateOper oper = operDetail.putUpdate(fdId, displayName);
						ctx.setLogOper(oper);
						ctx.setLogType(LogConvertContext.CONVERTTYPE_UPDATE);
						return oper;
					}
				}
			}
			// 新增
			IUserAddOper oper = operDetail.putAdd(fdId, displayName);
			ctx.setLogOper(oper);
			ctx.setLogType(LogConvertContext.CONVERTTYPE_ADD);
			return oper;
		} catch (Exception e) {
			logger.error("创建明细新增/修改日志时出错：", e);
		}
		return null;
	}

	/**
	 * 对比旧map与新map，将旧map中有，新map中没有的对象记录为删除的对象
	 * 
	 * @param operDetail
	 * @param cloneMap
	 * @param tMap
	 */
	protected void createDeleteOper(IUserDetailOper operDetail, Map cloneMap, Map tMap) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if (cloneMap == null || cloneMap.size() <= 0) {
			//旧值为空，没有可删除的对象
			return;
		}
		if (operDetail == null) {
			return;
		}
		try {
			Set keySet = cloneMap.keySet();
			for (Object key : keySet) {
				if (!tMap.containsKey(key)) {
					Object obj = cloneMap.get(key);
					if (obj instanceof IBaseModel) {
						operDetail.putDelete((IBaseModel) obj);
					}
				}
			}
		} catch (Exception e) {
			logger.error("创建明细删除日志时出错：", e);
		}
	}

	public String getTChildToParentProperty() {
		return tChildToParentProperty;
	}

	public FormConvertor_FormMapToModelMap setTChildToParentProperty(
			String childToParentProperty) {
		tChildToParentProperty = childToParentProperty;
		return this;
	}

	public FormConvertor_FormMapToModelMap setTPropertyName(String propertyName) {
		tPropertyName = propertyName;
		return this;
	}

	@Override
    public void examine(ExamineContext context, Class formClass,
                        Class modelClass) {
		PropertyDescriptor descriptor = ObjectUtil.getPropertyDescriptor(
				modelClass, tPropertyName);
		if (descriptor == null || descriptor.getWriteMethod() == null) {
			context.addError(formClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "无setter方法");
		} else if (!Map.class.isAssignableFrom(descriptor.getPropertyType())) {
			context.addError(formClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "必须是Map类型");
		}

		Object form = context.getParameter("form");
		String sProperty = (String) context.getParameter("sProperty");
		Map sPropertyMap = null;
		try {
			Object value = PropertyUtils.getProperty(form, sProperty);
			if (value == null) {
				context.addError(formClass, sProperty, 1, "属性" + sProperty
						+ "必须有初值");
			} else if (!(value instanceof AutoHashMap)) {
				context.addError(formClass, sProperty, 1, "属性" + sProperty
						+ "初始化为一个AutoHashMap的实体");
			} else {
				sPropertyMap = (Map) value;
			}
		} catch (Exception e) {
			context.addError(formClass, sProperty, 1, "属性" + sProperty
					+ "的值无法读取");
		}
		if (sPropertyMap != null) {
			Object child = null;
			try {
				child = sPropertyMap.get("key");
			} catch (Exception e) {
				context.addError(formClass, sProperty, 1, "属性" + sProperty
						+ "的元素无法被实例化");
			}
			if (child != null) {
				if (child instanceof IExtendForm) {
					Class childModelClass = ((IExtendForm) child)
							.getModelClass();
					if (childModelClass == null) {
						context.addError(formClass, sProperty, 1, "属性"
								+ sProperty + "对应的Form中getModelClass不能返回null");
					} else {
						descriptor = ObjectUtil.getPropertyDescriptor(
								childModelClass, tChildToParentProperty);
						if (descriptor == null
								|| descriptor.getWriteMethod() == null) {
							context.addError(formClass,
									"getToModelPropertyMap", 1, "属性"
											+ sProperty + "对应域模型中的属性"
											+ tChildToParentProperty
											+ "无setter方法");
						}
					}
				} else {
					context.addError(formClass, sProperty, 1, "属性" + sProperty
							+ "的元素必须为Form");
				}
			}
		}
	}
}
