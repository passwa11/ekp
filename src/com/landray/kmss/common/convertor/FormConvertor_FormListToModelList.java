package com.landray.kmss.common.convertor;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

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
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.ObjectUtil;
import com.landray.sso.client.util.StringUtil;

/**
 * 将Form模型列表转换为域模型列表，常用于一对多的转换
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class FormConvertor_FormListToModelList extends BaseFormConvertor
		implements IFormToModelConvertor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FormConvertor_FormListToModelList.class);

	private String tChildToParentProperty;
	private String flagProperty;

	/**
	 * @param tPropertyName
	 *            目标属性名
	 * @param tChildToParentProperty
	 *            子域模型到父域模型的属性名
	 */
	public FormConvertor_FormListToModelList(String tPropertyName,
			String tChildToParentProperty) {
		this.tPropertyName = tPropertyName;
		this.tChildToParentProperty = tChildToParentProperty;
	}

	/**
	 * @param tPropertyName
	 *            目标属性名
	 * @param tChildToParentProperty
	 *            子域模型到父域模型的属性名
	 * @param flagProperty
	 *            转换标记属性名，若定义了该属性，当取值为0时不转换，当取值为1时完全转换，当取值为2时，当出现行变动时抛出错误
	 */
	public FormConvertor_FormListToModelList(String tPropertyName,
			String tChildToParentProperty, String flagProperty) {
		this(tPropertyName, tChildToParentProperty);
		this.flagProperty = flagProperty;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		// 读取form属性
		List sList;
		try {
			sList = (List) PropertyUtils.getProperty(ctx.getSObject(), ctx
					.getSPropertyName());
		} catch (NestedNullException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取属性" + ctx.getSPropertyName()
						+ "的值时中间出现null值，不转换该属性");
			}
			return;
		}
		if (sList == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("属性" + ctx.getSPropertyName() + "的值为null，不转换该属性");
			}
			return;
		}
		// 读取标记
		String flag;
		if (flagProperty != null) {
			flag = (String) PropertyUtils.getProperty(ctx.getSObject(),
					flagProperty);
			if (flag == null) {
				flag = "2";
			}
		} else {
			flag = "1";
		}
		if ("0".equals(flag)) {
			return;
		}
		// 设置model属性
		List tList = (List) PropertyUtils.getProperty(ctx.getTObject(),
				getTPropertyName());
		List cloneList = (List)cloneOldValue(tList);
		if (tList == null) {
			tList = new ArrayList();
		} else if ("1".equals(flag)) {
			tList.clear();
		}
		if ("2".equals(flag) && tList.size() != sList.size()) {
			throw new Exception("明细表更改标记标记为不增删行，但明细表行数却发生了改变。");
		}
		//日志操作对象
		IUserOper currentOper = getLogOper(ctx);
		IUserDetailOper operDetail = null;
		for (int i = 0; i < sList.size(); i++) {
			IExtendForm sForm = (IExtendForm) sList.get(i);
			if (sForm != null) {
				// 创建List 放在此处防止sList无数据时出现空detail
				operDetail = createDetail(operDetail, ctx, currentOper);
				//List中创建Model 并设置到上下文对象中
				IUserOper oper = createAddOrUpdateOper(ctx, sForm, operDetail, cloneList);
				IBaseModel tModel = ctx.getBaseService().convertFormToModel(
						sForm, null, ctx);
				setAddFdId(oper, tModel);
				BeanUtils.copyProperty(tModel, getTChildToParentProperty(), ctx
						.getTObject());
				int index = tList.indexOf(tModel);
				if (index == -1) {
					if ("2".equals(flag)) {
						throw new Exception("明细表更改标记标记为不增删行，但明细表的第" + (i + 1)
								+ "行记录却在数据库中无法找到。");
					} else {
						tList.add(tModel);
					}
				} else {
					tList.set(index, tModel);
				}
			}
		}
		//未转换的model为delete类型
		createDeleteOper(operDetail, cloneList, tList);
		BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(), tList);
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
		if (oper == null || tModel == null) {
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
		List sList = new ArrayList();
		if (currentObj instanceof List) {
			for (Object bean : (List) currentObj) {
				sList.add(bean);
			}
		}
		return sList;
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
			if (logger.isDebugEnabled()) {
				logger.debug("日志上下文中，无操作对象，返回空IUserOper对象");
			}
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
	 * List中根据原list，判断为新增、修改，创建对应Oper 并设置到上下文对象中
	 * 
	 * @param ctx
	 * @param sForm
	 * @param operDetail
	 * @param cloneList
	 */
	protected IUserOper createAddOrUpdateOper(ConvertorContext ctx, IExtendForm sForm, IUserDetailOper operDetail,
			List cloneList) {
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

			for (Object object : cloneList) {
				if (object instanceof IBaseModel) {
					String oldListFdId = ((IBaseModel) object).getFdId();
					if (fdId.equals(oldListFdId)) {
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
	 * 对比旧list与新list，将旧list中有，而新list中没有的对象记录为删除的对象
	 * 
	 * @param operDetail
	 * @param cloneList
	 *            旧list
	 * @param tList
	 *            新list
	 */
	private void createDeleteOper(IUserDetailOper operDetail, List cloneList, List tList) {
		if (!UserOperConvertHelper.isConvertAllow()) {
			return;
		}
		if (ArrayUtil.isEmpty(cloneList)) {
			//旧值为空，没有可删除的对象
			return;
		}
		if (operDetail == null) {
			return;
		}
		try {
			StringBuilder newIds = new StringBuilder();
			for (Object object : tList) {
				if (object instanceof IBaseModel) {
					String fdId = ((IBaseModel) object).getFdId();
					newIds.append(fdId).append(";");
				}
			}
			for (Object object : cloneList) {
				if (object instanceof IBaseModel) {
					String fdId = ((IBaseModel) object).getFdId();
					if (newIds.indexOf(fdId) == -1) {
						operDetail.putDelete((IBaseModel) object);
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

	public FormConvertor_FormListToModelList setTChildToParentProperty(
			String childToParentProperty) {
		tChildToParentProperty = childToParentProperty;
		return this;
	}

	public FormConvertor_FormListToModelList setTPropertyName(
			String propertyName) {
		tPropertyName = propertyName;
		return this;
	}

	public FormConvertor_FormListToModelList setFlagProperty(String flagProperty) {
		this.flagProperty = flagProperty;
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
		} else if (!List.class.isAssignableFrom(descriptor.getPropertyType())) {
			context.addError(formClass, "getToModelPropertyMap", 1, "目标属性"
					+ tPropertyName + "必须是List类型");
		}

		Object form = context.getParameter("form");
		String sProperty = (String) context.getParameter("sProperty");
		List sPropertyList = null;
		try {
			Object value = PropertyUtils.getProperty(form, sProperty);
			if (value == null) {
				context.addError(formClass, sProperty, 1, "属性" + sProperty
						+ "必须有初值");
			} else if (!(value instanceof AutoArrayList)) {
				context.addError(formClass, sProperty, 1, "属性" + sProperty
						+ "初始化为一个AutoArrayList的实体");
			} else {
				sPropertyList = (List) value;
			}
		} catch (Exception e) {
			context.addError(formClass, sProperty, 1, "属性" + sProperty
					+ "的值无法获取");
		}
		if (sPropertyList != null) {
			Object child = null;
			try {
				child = sPropertyList.get(0);
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
							+ "的元素必须为Form对象");
				}
			}
		}
	}
}
