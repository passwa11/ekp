package com.landray.kmss.common.convertor;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

/**
 * 将多个ID组成的字符串转换为有序的域模型列表，常用于多对多的关系
 * 
 * @author 叶中奇
 * 
 */
public class FormConvertor_IDsToModelList extends BaseFormConvertor implements
		IFormToModelConvertor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FormConvertor_IDsToModelList.class);

	private Class modelClass;

	private String splitStr;

	/**
	 * @param tPropertyName
	 *            目标属性名
	 * @param modelClass
	 *            目标列表中每个域模型的类
	 */
	public FormConvertor_IDsToModelList(String tPropertyName, Class modelClass) {
		this.tPropertyName = tPropertyName;
		this.modelClass = modelClass;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		String ids;
		try {
			ids = (String) PropertyUtils.getProperty(ctx.getSObject(), ctx
					.getSPropertyName());
		} catch (NestedNullException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取属性" + ctx.getSPropertyName()
						+ "的值时中间出现null值，不转换该属性");
			}
			return;
		}
		if (ids == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("属性" + ctx.getSPropertyName() + "的值为null，不转换该属性");
			}
			return;
		}
		List tList = (List) PropertyUtils.getProperty(ctx.getTObject(),
				getTPropertyName());
		List oldList = (List)cloneOldValue(tList);
		if (tList == null) {
			tList = new ArrayList();
		} else {
			tList.clear();
		}
		String[] idArr = null;
		if (!"".equals(ids.trim())) {
			idArr = ids.split(getSplitStr());
			for (int i = 0; i < idArr.length; i++) {
				IBaseModel tModel = ctx.getBaseService().findByPrimaryKey(
						idArr[i], getModelClass(), false);
				if (tModel != null) {
					tList.add(tModel);
				}
			}
		}
		// 记录日志
		saveOperLog(ctx, idArr, oldList);
		BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(), tList);
	}

	/**
	 * 由于tList为实例对象，tObject中的list会因tList而变动
	 * @param currentObj
	 * @return
	 */
	private Object cloneOldValue(Object currentObj) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return null;
		}
		List sList = new ArrayList();
		if (currentObj != null) {
			if (currentObj instanceof List) {
				for (Object bean : (List) currentObj) {
					sList.add(bean);
				}
			}
		}
		return sList;
	}

	/**
	 * 保存操作日志
	 * @param ctx
	 * @param oldList
	 * @param idArr
	 */
	private void saveOperLog(ConvertorContext ctx, String[] idArr, List oldList) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if (ctx.getLogOper() == null) {
			return;
		}
		String modelName = null;
		if (!ArrayUtil.isEmpty(oldList) && oldList.get(0) instanceof IBaseModel) {
			modelName = ModelUtil.getModelClassName(oldList.get(0));
		}
		Map<String, Object> newMap = UserOperHelper.createMapFromIdNames(ctx, "Id", "Name", idArr, getSplitStr(),
				modelName);
		Map<String, Object> oldMap = UserOperHelper.createMapFromList(oldList);
		UserOperHelper.putSimple(ctx.getLogOper(), ctx.getLogType(), getTPropertyName(), oldMap, newMap);
	}

	public Class getModelClass() {
		return modelClass;
	}

	public String getSplitStr() {
		if (splitStr == null) {
			return ";";
		}
		return splitStr;
	}

	public FormConvertor_IDsToModelList setModelClass(Class modelClass) {
		this.modelClass = modelClass;
		return this;
	}

	/**
	 * 设置ID字符串的分隔符号，默认为;
	 * 
	 * @param splitStr
	 * @return
	 */
	public FormConvertor_IDsToModelList setSplitStr(String splitStr) {
		this.splitStr = splitStr;
		return this;
	}

	public FormConvertor_IDsToModelList setTPropertyName(String propertyName) {
		tPropertyName = propertyName;
		return this;
	}

	@Override
    public void examine(ExamineContext context, Class fromClass, Class toClass) {
		String sProperty = (String) context.getParameter("sProperty");
		PropertyDescriptor descriptor = ObjectUtil.getPropertyDescriptor(
				fromClass, sProperty);
		if (descriptor == null || descriptor.getReadMethod() == null) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "源属性"
					+ sProperty + "无getter方法");
		} else if (!String.class.isAssignableFrom(descriptor.getPropertyType())) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "源属性"
					+ sProperty + "应该是String类型");
		}

		descriptor = ObjectUtil.getPropertyDescriptor(toClass, tPropertyName);
		if (descriptor == null || descriptor.getWriteMethod() == null) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "无setter方法");
		} else if (!List.class.isAssignableFrom(descriptor.getPropertyType())) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "必须是List类型");
		}
		if (!IBaseModel.class.isAssignableFrom(modelClass)) {
			context.addError(fromClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "对应的类必须是Model");
		}
	}
}
