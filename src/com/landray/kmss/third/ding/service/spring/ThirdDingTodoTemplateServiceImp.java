package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.xform.base.model.SysFormCommonTemplate;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class ThirdDingTodoTemplateServiceImp extends ExtendDataServiceImp
		implements IThirdDingTodoTemplateService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private DictLoadService loader;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTodoTemplateServiceImp.class);

	/**
	 * @param loader 数据字典加载类
	 */
	public void setLoader(DictLoadService loader) {
		this.loader = loader;
	}

	protected ISysFormTemplateService sysFormTemplateService;

	protected ISysFormTemplateService getSysFormTemplateServiceImp() {
		if (sysFormTemplateService == null) {
            sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
                    .getBean("sysFormTemplateService");
        }
		return sysFormTemplateService;
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingTodoTemplate) {
            ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingTodoTemplate thirdDingTodoTemplate = new ThirdDingTodoTemplate();
        thirdDingTodoTemplate.setDocCreateTime(new Date());
        thirdDingTodoTemplate.setDocAlterTime(new Date());
        thirdDingTodoTemplate.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingTodoTemplate, requestContext);
        return thirdDingTodoTemplate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	// 数字字典获取
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String modelName = requestInfo.getParameter("fdTemplateClass");
		modelName = "com.landray.kmss.km.review.model.KmReviewMain";
		// String formFileName = requestInfo.getParameter("formFileName");
		String templateId = requestInfo.getParameter("fdTemplateId");
		String templateModelName = requestInfo
				.getParameter("fdTemplateClass");

		logger.debug("modelName: " + modelName + " templateId:" + templateId
				+ " templateModelName:" + templateModelName);
		String formFileName = getFormFilePath(templateId, templateModelName);

		logger.debug("formFileName:" + formFileName);
		List<Object> rtnList = new ArrayList<Object>();

		String type = ThirdDingXFormTemplateUtil.getXFormTemplateType(templateId);
		logger.info("套件类型：" + type);

		// 扩展字段集合
		List<SysDictCommonProperty> extendProperties = getExtendDataFormInfo(
				formFileName);
		for (int i = 0; i < extendProperties.size(); i++) {
			SysDictCommonProperty p = extendProperties.get(i);
			if (!(p instanceof SysDictExtendProperty)) {
                continue;
            }
			SysDictExtendProperty property = (SysDictExtendProperty) p;
			if (property instanceof SysDictExtendSubTableProperty) {
				SysDictExtendSubTableProperty subTable = (SysDictExtendSubTableProperty) property;
				// map = new HashMap();
				// map.put("text", subTable.getLabel());
				// map.put("value", subTable.getName() + ".%");//
				// 如果是明细表在value中加个.%进行区别,当单击明细表时用字表id进行取代
				// map.put("isShowCheckBox", "false");
				// rtnList.add(map);

				List<SysDictCommonProperty> propertyList = subTable
						.getElementDictExtendModel().getPropertyList();
				SysDictCommonProperty dictProperty = null;
				for (int j = 0; j < propertyList.size(); j++) {
					dictProperty = propertyList.get(j);
					if (!(dictProperty instanceof SysDictExtendSimpleProperty)) {
                        continue;
                    }
					SysDictExtendSimpleProperty dictExtendSimpleProperty = (SysDictExtendSimpleProperty) dictProperty;
					// map = new HashMap();
					// map.put("text", subTable.getLabel() + "."
					// + dictExtendSimpleProperty.getLabel());
					// map.put("value", "$" + subTable.getName() + "."
					// + dictExtendSimpleProperty.getName() + "$");
					// map.put("nodeType", "TEMPLATE");
					// rtnList.add(map);

					logger.debug("暂不支持推送明细表字段       " + subTable.getLabel() + "."
							+ dictExtendSimpleProperty.getLabel());
					// Object[] object = new Object[3];
					// object[0] = subTable.getName() + "."
					// + dictExtendSimpleProperty.getName();
					// object[1] = subTable.getLabel() + "."
					// + dictExtendSimpleProperty.getLabel();
					// object[2] = "table";
					// logger.debug(object[0] + " " + object[1]);
					// rtnList.add(object);
				}
			} else {
				// 套件明细
				if (isSuiteField(type, property.getName())) {
					continue;
				}
				Object[] object = new Object[5];
				object[0] = property.getName();
				object[1] = property.getLabel();
				object[2] = property.getType(); // 类型 String Date
				object[3] = property.isEnum(); // 是否是枚举
				object[4] = property.getEnumValues(); // 枚举的值 天|1;半天|2;小时|3
				logger.debug("Name:" + object[0] + "  getLabel:" + object[1]
						+ "   getType:" + object[2]
						+ "  isEnum: " + object[3] + "  getEnumValues: "
						+ object[4]);
				rtnList.add(object);
			}
		}
		// 添加套件特定的的的属性
		if (StringUtil.isNotNull(type)&& !"common".equals(type)) {
			addSuiteField(type, rtnList);
		}
		return rtnList;
	}

	private void addSuiteField(String type, List<Object> rtnList) {
		switch (type) {
			case "batchLeave": // 请假套件
				setField("$suiteTable$_leaveTime", "请假时间", rtnList);
				setField("$suiteTable$_leaveType", "请假类型", rtnList);
				setField("$suiteTable$_fdSumDuration", "请假总时长", rtnList);
				setField("$suiteTable$_leaveRemark", "请假事由", rtnList);
				break;
			case "batchWorkOverTime": // 加班字段
				setField("$suiteTable$_workOverTime", "加班时间", rtnList);
				setField("$suiteTable$_users", "加班人", rtnList);
				setField("$suiteTable$_compensation", "加班补偿", rtnList);
				setField("$suiteTable$_duration", "加班时长", rtnList);
				break;
			case "batchChange": // 批量换班
				setField("$suiteTable$_fdChangeApplyUser", "换班人", rtnList);
				setField("$suiteTable$_fdChangeSwapUser", "替班人", rtnList);
				setField("$suiteTable$_fdChangeDate", "换班日期", rtnList);
				setField("$suiteTable$_fdReturnDate", "还班时间", rtnList);
				setField("$suiteTable$_fdChangeRemark", "换班原因", rtnList);
				break;
			case "batchCancel": // 批量销假
				setField("$suiteTable$_fd_cancel_user", "销假人", rtnList);
				setField("$suiteTable$_fd_form_name", "销假单", rtnList);
				setField("$suiteTable$_fd_cancel_sum_time", "销假总时长", rtnList);
				setField("$suiteTable$_fd_cancel_remark", "销假理由", rtnList);
				break;
			case "batchReplacement": // 批量补卡
				setField("$suiteTable$_fdUser", "补卡人", rtnList);
				setField("$suiteTable$_fdReplacementCount", "补卡次数", rtnList);
				setField("$suiteTable$_fdReplacementTime", "补卡时间", rtnList);
				setField("$suiteTable$_fdReplacementReason", "补卡理由", rtnList);
				break;
		}
	}

	private void setField(String key, String name, List<Object> rtnList) {
		Object[] object = new Object[5];
		object[0] = key;
		object[1] = name;
		object[2] = "String"; // 类型 String Date
		object[3] = false; // 是否是枚举
		object[4] = null; // 枚举的值 天|1;半天|2;小时|3
		rtnList.add(object);
	}

	private boolean isSuiteField(String type, String name) {
		if (StringUtil.isNull(type)) {
            return false;
        }
		switch (type) {
			case "batchLeave": // 请假套件
				if ("batchLeave".equals(name) || "fd_leave_user".equals(name)
						|| "fd_batch_leave_table".equals(name)
						|| "fd_sum_duration".equals(name)
						|| "fd_leave_remark".equals(name) || "".equals(name)) {
					return true;
				}
				break;
			case "batchWorkOverTime":
				if ("batchWorkOverTime".equals(name)
						|| "fd_work_over_time_remark".equals(name)
						|| "fd_batch_change_flag".equals(name)) {
					return true;
				}
				break;
			case "batchCancel":
				if ("batchCancel".equals(name) || "fd_cancel_user".equals(name)
						|| "fd_leave_form".equals(name)
						|| "fd_select_form".equals(name)
						|| "fd_all_form".equals(name)
						|| "fd_form_name".equals(name)
						|| "fd_table_all_tr".equals(name)
						|| "fd_cancel_type".equals(name)
						|| "fd_cancel_sum_time".equals(name)
						|| "fd_cancel_surplus_time".equals(name)
						|| "fd_cancel_remark".equals(name)) {
					return true;
				}
				break;
			case "batchChange":
				if ("batchChange".equals(name)
						|| "fd_change_remark".equals(name)
						|| "fd_batch_change_flag".equals(name)) {
					return true;
				}
				break;
			case "batchReplacement":
				if ("batchReplacement".equals(name)
						|| "fd_replacement_count".equals(name)
						|| "fd_user".equals(name)
						|| "fd_batch_change_flag".equals(name)) {
					return true;
				}
				break;
			default:
				return false;
		}
		return false;
	}

	private String getFormFilePath(String templateId, String templateModelName)
			throws Exception {
		// 得到模板对应的自定义表单文件路径
		HQLInfo hqlInfoFormTemp = new HQLInfo();
		// hqlInfoFormTemp
		// .setSelectBlock("sysFormTemplate.fdFormFileName,sysFormTemplate.fdCommonTemplate");
		hqlInfoFormTemp
				.setWhereBlock(
						"sysFormTemplate.fdModelId=:fdModelId and sysFormTemplate.fdModelName=:fdModelName");
		hqlInfoFormTemp.setParameter("fdModelId", templateId);
		hqlInfoFormTemp.setParameter("fdModelName", templateModelName);
		List formTemplList = getSysFormTemplateServiceImp().findList(
				hqlInfoFormTemp);
		String fdFormFileName = "";
		if (!formTemplList.isEmpty()) {

			SysFormTemplate sysFormTemplate = (SysFormTemplate) formTemplList
					.get(0);
			fdFormFileName = sysFormTemplate.getFdFormFileName();
			if (StringUtil.isNull(fdFormFileName)) {
				SysFormCommonTemplate commonTemplate = sysFormTemplate
						.getFdCommonTemplate();
				if (commonTemplate != null) {
					fdFormFileName = commonTemplate.getFdFormFileName();
				}
			} else {
				for (int i = 1; i < formTemplList.size(); i++) {
					sysFormTemplate = (SysFormTemplate) formTemplList
							.get(i);
					String fileName = sysFormTemplate.getFdFormFileName();
					if (StringUtil.isNotNull(fileName) && fileName
							.contains(templateId + "/" + templateId)) {
						return fileName;
					}
				}
			}
			return fdFormFileName;
		}
		return null;
	}

	/**
	 * @param extendFilePath
	 * @return 扩展字段集合
	 * @throws Exception
	 */
	private List getExtendDataFormInfo(String extendFilePath) throws Exception {
		List properties = new ArrayList();
		if (StringUtil.isNull(extendFilePath)
				|| "null".equals(extendFilePath)) {
			return properties;// 如果为"null"则说明没有配置自定义表单
		}
		SysDictExtendModel dict = loader.loadDictByFileName(extendFilePath);
		properties = dict.getPropertyList();
		return properties;
	}

	/**
	 * @param modelName
	 * @return 数据字典字段集合
	 */
	private List getDictVarInfo(String modelName) {
		SysDictModel model = SysDataDict.getInstance().getModel(modelName);
		List properties = model.getPropertyList();
		return properties;
	}

	private String getModelName(Class<?> clazz) {
		String modelName = clazz.getName();
		modelName = modelName.substring(modelName.lastIndexOf('.') + 1);
		return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
	}

	@Override
	public List getDataList(String modelName, String templateId,
			String templateModelName) throws Exception {
		String formFileName = getFormFilePath(templateId, templateModelName);

		List<Object> rtnList = new ArrayList<Object>();

		// 扩展字段集合
		List<SysDictCommonProperty> extendProperties = getExtendDataFormInfo(
				formFileName);
		for (int i = 0; i < extendProperties.size(); i++) {
			SysDictCommonProperty p = extendProperties.get(i);
			if (!(p instanceof SysDictExtendProperty)) {
                continue;
            }
			SysDictExtendProperty property = (SysDictExtendProperty) p;
			if (property instanceof SysDictExtendSubTableProperty) {
				SysDictExtendSubTableProperty subTable = (SysDictExtendSubTableProperty) property;
				// map = new HashMap();
				// map.put("text", subTable.getLabel());
				// map.put("value", subTable.getName() + ".%");//
				// 如果是明细表在value中加个.%进行区别,当单击明细表时用字表id进行取代
				// map.put("isShowCheckBox", "false");
				// rtnList.add(map);

				List<SysDictCommonProperty> propertyList = subTable
						.getElementDictExtendModel().getPropertyList();
				SysDictCommonProperty dictProperty = null;
				for (int j = 0; j < propertyList.size(); j++) {
					dictProperty = propertyList.get(j);
					if (!(dictProperty instanceof SysDictExtendSimpleProperty)) {
                        continue;
                    }
					SysDictExtendSimpleProperty dictExtendSimpleProperty = (SysDictExtendSimpleProperty) dictProperty;

					Object[] object = new Object[2];
					object[0] = subTable.getName() + "."
							+ dictExtendSimpleProperty.getName();
					object[1] = subTable.getLabel() + "."
							+ dictExtendSimpleProperty.getLabel();

					rtnList.add(object);
				}
			} else {

				Object[] object = new Object[5];
				object[0] = property.getName();
				object[1] = property.getLabel();
				object[2] = property.getType(); // 类型 String Date
				object[3] = property.isEnum(); // 是否是枚举
				object[4] = property.getEnumValues(); // 枚举的值 天|1;半天|2;小时|3

				rtnList.add(object);
			}
		}
		return rtnList;
	}
}
