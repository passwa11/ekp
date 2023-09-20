package com.landray.kmss.hr.staff.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.Province;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IProvinceService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 人员信息工具类
 * 
 * @author 潘永辉 2016-12-28
 * 
 */
public class ProvinceUtil {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(HrStaffPersonUtil.class);
	/**
	 * 调休
	 */
	public static final int LEAVETYPE_TAKEWORKING = 1;
	/**
	 * 年假
	 */
	public static final int LEAVETYPE_ANNUALLEAVE = 2;
	/**
	 * 病假
	 */
	public static final int LEAVETYPE_SICKLEAVE = 3;

	/**
	 * 获取上级部门全路径名称
	 * 
	 * @param parent
	 * @return
	 */
	public static String getFdOrgParentsName(SysOrgElement parent) {
		if (parent == null) {
			return "";
		}
		String fdParentsName = parent.getFdParentsName();
		if (StringUtil.isNotNull(fdParentsName)) {
			fdParentsName += "_" + parent.getFdName();
		} else {
			fdParentsName = parent.getFdName();
		}
		return fdParentsName;
	}

	/**
	 * 比较两个类里面相同属性的值，并返回有相同属性值有差异的字段名称
	 * 
	 * @param oldForm
	 * @param newForm
	 * @return
	 * @throws Exception
	 */
	public static String compare(HrStaffPersonInfoForm oldForm,
			HrStaffPersonInfoForm newForm) throws Exception {
		String changesField = "";
		// 获取所有字段
		Field[] fields = HrStaffPersonInfoForm.class.getDeclaredFields();
		// 获取数据字典
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				newForm.getModelClass().getName());
		Map<String, SysDictCommonProperty> propertyMap = dictModel
				.getPropertyMap();
		for (Field field : fields) {
			String fieldName = field.getName();
			String valOld = null;
			String valNew = null;

			// 跳过不处理的字段
			if ("serialVersionUID".equals(fieldName)
					|| "toModelPropertyMap".equals(fieldName)
					|| "fdOrgPersonId".equals(fieldName)
					|| "fdOrgParentName".equals(fieldName)
					|| "fdOrgParentsName".equals(fieldName)
					|| "fdOrgPostNames".equals(fieldName)
					|| "fdStaffingLevelName".equals(fieldName)) {
				continue;
			}

			try {
				// 根据字段名称获取数据
				valOld = BeanUtils.getProperty(oldForm, fieldName);
				valNew = BeanUtils.getProperty(newForm, fieldName);
				if (valNew == null) {
					valNew = "";
				}
				if (valOld == null) {
					valOld = "";
				}
			} catch (Exception e) {
				if (e instanceof NoSuchMethodException) {
					continue;
				}
			}

			if (valOld != null && !valOld.equals(valNew)) {
				// 处理一下与model对应的字段
				if ("fdOrgParentId".equals(fieldName)) {
					fieldName = "fdOrgParent";
				}
				if ("fdOrgPostIds".equals(fieldName)) {
					fieldName = "fdOrgPosts";
				}
				if ("fdStaffingLevelId".equals(fieldName)) {
					fieldName = "fdStaffingLevel";
				}

				SysDictCommonProperty commonProperty = propertyMap
						.get(fieldName);
				if (commonProperty == null) {
					continue;
				}
				String messageKey = commonProperty.getMessageKey();
				if (messageKey == null) {
					continue;
				}
				// 返回有改动的字段名称
				changesField = StringUtil.linkString(changesField, " 、",
						ResourceUtil.getString(messageKey));
			}
		}

		if (StringUtil.isNotNull(changesField)) {
			changesField += "。";
		}
		return changesField;
	}

	private static IProvinceService provinceService;

	public static IProvinceService
			getProvinceService() {
		if (provinceService == null) {
			provinceService = (IProvinceService) SpringBeanUtil
					.getBean("provinceService");
		}
		return provinceService;
	}

	/**
	 * 根据类型构建表单下拉框
	 * 
	 * @param fdType
	 * @param defValue
	 * @return
	 * @throws Exception
	 */
	public static String buildProvinceHtml(String fieldName,
			HttpServletRequest request) throws Exception {
		HrStaffPersonInfoForm form = (HrStaffPersonInfoForm) request
				.getAttribute("hrStaffPersonInfoForm");
		String defValue = null;
		try {
			defValue = BeanUtils.getProperty(form, fieldName);
		} catch (Exception e) {
		}
		List<Province> settings = getProvinceService()
				.getByType(fieldName);

		if (request.getAttribute("mobileField") != null) {
			StringBuffer settingArr = new StringBuffer();
			for (int i = 0; i < settings.size(); i++) {
				settingArr.append(settings.get(i).getFdId());
				if (i != settings.size() - 1) {
					settingArr.append(";");
				}
			}
			return settingArr.toString();
		} else {
			return buildHtml(settings, fieldName, defValue);
		}

	}
	public static String buildProvinceHtml1(String fieldName,
			HttpServletRequest request) throws Exception {
		HrStaffPersonInfoForm form = (HrStaffPersonInfoForm) request
				.getAttribute("hrStaffPersonInfoForm");
		String defValue = null;
		try {
			defValue = BeanUtils.getProperty(form, fieldName);
		} catch (Exception e) {
		}
		List<Province> settings = getProvinceService()
				.getByType(fieldName);

		if (request.getAttribute("mobileField") != null) {
			StringBuffer settingArr = new StringBuffer();
			for (int i = 0; i < settings.size(); i++) {
				settingArr.append(settings.get(i).getFdId());
				if (i != settings.size() - 1) {
					settingArr.append(";");
				}
			}
			return settingArr.toString();
		} else {
			return buildHtml1(settings, fieldName, defValue);
		}

	}
	private static String buildHtml1(List<Province> settings,
			String fieldName, String defValue) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(
				"<select class='hr_select' onchange='setCities1(this)' name='")
				.append(fieldName)
				.append("'>");
		buf.append("<option value=''>").append(
				ResourceUtil.getString("hr-staff:hrStaff.robot.select"))
				.append("</option>");
		if (settings != null) {
			for (Province setting : settings) {
				buf.append("<option value='")
						.append(setting.getProvinceId())
						.append("'")
						.append(
								setting.getProvinceId().equals(defValue)
										? " selected"
										: "")
						.append(">").append(
								setting.getProvince())
						.append("</option>");
			}

		}
		buf.append("</select>");
		return buf.toString();
	}

	private static String buildHtml(List<Province> settings,
			String fieldName, String defValue) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(
				"<select class='hr_select' onchange='setCities(this)' name='")
				.append(fieldName)
				.append("'>");
		buf.append("<option value=''>").append(
				ResourceUtil.getString("hr-staff:hrStaff.robot.select"))
				.append("</option>");
		if (settings != null) {
			for (Province setting : settings) {
				buf.append("<option value='")
						.append(setting.getProvinceId())
						.append("'")
						.append(
								setting.getProvinceId().equals(defValue)
										? " selected"
										: "")
						.append(">").append(
								setting.getProvince())
						.append("</option>");
			}

		}
		buf.append("</select>");
		return buf.toString();
	}

	/**
	 * 是否需要显示“标签”
	 * 
	 * @return
	 */
	public static boolean isShowLabel(HttpServletRequest request) {
		boolean isShow = false;
		HrStaffPersonInfoForm personInfoForm = (HrStaffPersonInfoForm) request
				.getAttribute("hrStaffPersonInfoForm");
		if (personInfoForm != null
				&& personInfoForm.getFdOrgPersonId() != null) {
			IBaseService service = (IBaseService) SpringBeanUtil
					.getBean("KmssBaseService");
			Object obj = null;
			try {
				obj = service.findByPrimaryKey(personInfoForm.getFdId(),
						"com.landray.kmss.sys.zone.model.SysZonePersonInfo",
						true);
			} catch (Exception e) {
			}
			if (obj != null) {
				isShow = true;
			}
		}

		return isShow;
	}

	/**
	 * 获取员工头像url
	 * 
	 * @param expertId
	 * @return
	 * @throws Exception
	 */
	public static String getImgUrl(HrStaffPersonInfo staffInfo,
			HttpServletRequest request) throws Exception {
		String fdOrgId = staffInfo.getFdOrgPerson() == null ? ""
				: staffInfo.getFdOrgPerson().getFdId();// 非组织架构新建的员工没有组织架构人员ID
		String imgAttUrl = request.getContextPath()
				+ "/sys/person/image.jsp?personId=" + fdOrgId
				+ "&size=b&s_time=" + System.currentTimeMillis();
		return imgAttUrl;
	}

	/**
	 * 通过val获取单选,多选，下拉控件选中的文本值
	 */

	public static String getText(String fieldName, String val,
			String fdRelatedProcess) throws Exception {
		String rtnStr = "";
		IBaseModel baseModel = null;
		if (StringUtil.isNotNull(fdRelatedProcess)) {
			String[] values = fdRelatedProcess.split("fdId=");
			String url = values[0] + "fdId=${fdId}";
			String id = values[1];
			String serviceBean = getServiceBean(url);
			if (StringUtil.isNotNull(serviceBean)) {
				IBaseService service = (IBaseService) SpringBeanUtil
						.getBean(serviceBean);
				baseModel = service.findByPrimaryKey(id);
			}
		}
		if (baseModel != null) {
			String separator = ";";
			SysDictModel dictModel = null;
			if (baseModel instanceof IBaseModel) {
				IBaseModel mainModel = (IBaseModel) baseModel;
				try {
					dictModel = getSysMetadataParser().getDictModel(
							mainModel);
				} catch (Exception e) {
					logger.warn("对象无对应数据字典,对象信息"
							+ ModelUtil.getModelClassName(mainModel) + ":"
							+ mainModel.getFdId());
				}
			} else {
				String modelName = ModelUtil.getModelClassName(baseModel);
				dictModel = SysDataDict.getInstance()
						.getModel(modelName);
			}
			if (dictModel != null && val != null) {
				SysDictCommonProperty prop = dictModel.getPropertyMap()
						.get(fieldName);
				if (prop != null) {
					String enumValues = prop.getEnumValues();
					if (StringUtil.isNotNull(enumValues)) {
						String[] itemArr = enumValues.split(separator);
						List<String> valList = new ArrayList<String>();
						StringBuilder textBuilder = new StringBuilder();
						if (StringUtil.isNotNull(val)) {
							String[] valArr = val.split(separator);
							for (int i = 0; i < valArr.length; i++) {
								String str = valArr[i];
								valList.add(str.split("[.]")[0]);
							}
						}
						for (int i = 0; i < itemArr.length; i++) {
							String item = itemArr[i];
							String[] valAndText = item.split("\\|");
							if (valAndText.length == 2) {
								String itemVal = valAndText[1];
								String itemText = valAndText[0];
								if (valList.contains(itemVal)) {
									textBuilder.append(separator)
											.append(itemText);
								}
							}
						}
						if (textBuilder.length() > 0) {
							rtnStr = textBuilder.substring(1);
						}
					}
				}
			}
		}
		return rtnStr;
	}

	private static ISysMetadataParser sysMetadataParser = null;

	private static ISysMetadataParser getSysMetadataParser() {
		if (sysMetadataParser == null) {
			sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
					.getBean("sysMetadataParser");
		}
		return sysMetadataParser;
	}

	public static String getServiceBean(String url) throws Exception {
		Map<String, String> serviceBeanMap = SysDataDict.getInstance()
				.getServiceBeanMap();
		if (!serviceBeanMap.isEmpty()) {
			return serviceBeanMap.get(url);
		}
		return null;
	}

	private static IHrStaffPersonExperienceContractService contractService = null;

	private static IHrStaffPersonExperienceContractService
			getContractService() {
		if (contractService == null) {
			contractService = (IHrStaffPersonExperienceContractService) SpringBeanUtil
					.getBean("hrStaffPersonExperienceContractService");
		}
		return contractService;
	}

	private static ISysPrintMainCoreService sysPrintMainCoreService = null;

	private static ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
			sysPrintMainCoreService = (ISysPrintMainCoreService) SpringBeanUtil
					.getBean("sysPrintMainCoreService");
		}
		return sysPrintMainCoreService;
	}

	public static boolean canPrint(String contractId) throws Exception {
		boolean result = false;
		HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) getContractService()
				.findByPrimaryKey(contractId);
		HrStaffContractType contractType = contract
				.getFdStaffContType();
		if (contractType != null) {
			result = getSysPrintMainCoreService().isEnablePrintTemplate(
					contractType, null, Plugin.currentRequest());
		}
		return result;
	}

	/**
	 * 判断是否明细表内容
	 *
	 * @param key
	 * @param modelData
	 * @param detailData
	 * @return
	 */
	public static String getDetailKey(String key, Map<String, Object> modelData,
			Map<String, Object> detailData) {
		if (key.contains(".")) {
			String[] split = key.split("\\.");
			if (detailData.isEmpty()) {
				Object data = modelData.get(split[0]);
				if (data instanceof Map) {
					detailData.putAll((Map<String, Object>) data);
				} else if (data instanceof List) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) data;
					for (Map<String, Object> map : list) {
						detailData.putAll(map);
					}
				}

			}
			key = split[1];
		}
		return key;
	}

	/**
	 * 通过id获取对应的value
	 *
	 * @param id
	 * @param constant
	 * @param modelData
	 * @param detailData
	 * @param detailed
	 * @return
	 * @throws Exception
	 */
	public static Object processData(String id, String constant,
			Map<String, Object> modelData, Map<String, Object> detailData,
			Object detailed) throws Exception {
		Object result = modelData.get(id);
		if (result == null && !detailData.isEmpty()) {
			result = detailData.get(id);
		}
		if (detailed != null) {
			BeanUtils.setProperty(detailed, constant, result);
		}
		return result;

	}

	/**
	 * 获取请假人
	 *
	 * @param fieldValue
	 * @param modelData
	 * @return
	 * @throws Exception
	 */
	public static HrStaffPersonInfo getPersonInfo(String fieldValue,
			Map<String, Object> modelData, Map<String, Object> detailData,
			IHrStaffPersonInfoService service) throws Exception {
		Object fdApplicant = modelData.get(fieldValue);
		if (fdApplicant == null && !detailData.isEmpty()) {
			fdApplicant = detailData.get(fieldValue);
		}
		String fdApplicantId = BeanUtils.getProperty(fdApplicant, "id");
		String fdApplicantName = BeanUtils.getProperty(fdApplicant, "name");
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) service
				.findByPrimaryKey(fdApplicantId, null, true);
		if (personInfo == null) {
			throw new KmssException(new KmssMessage(ResourceUtil.getString(
					"hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
					"hr-staff", null, fdApplicantName)));
		}
		return personInfo;
	}
}
