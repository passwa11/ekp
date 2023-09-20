package com.landray.kmss.hr.organization.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.constant.HrStaffConstant;
import com.landray.kmss.hr.organization.dao.IHrOrganizationElementDao;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationLog;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserAgentUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ClassUtils;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class HrOrgUtil implements SysOrgConstant, HrOrgConstant {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrgUtil.class);

	private static DecimalFormat formatter = new DecimalFormat("####################");

	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static IHrOrganizationElementService hrOrganizationElementService;

	private static IHrStaffTrackRecordService hrStaffTrackRecordService;

	private static IHrOrganizationPostService hrOrganizationPostService;

	public static IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	public static IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		if (hrStaffTrackRecordService == null) {
			hrStaffTrackRecordService = (IHrStaffTrackRecordService) SpringBeanUtil
					.getBean("hrStaffTrackRecordService");
		}
		return hrStaffTrackRecordService;
	}

	public static IHrOrganizationPostService getHrOrganizationPostService() {
		if (hrOrganizationPostService == null) {
			hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil
					.getBean("hrOrganizationPostService");
		}
		return hrOrganizationPostService;
	}

	/**
	 * 返回日志里面的详细的信息
	 *
	 * @param elemOld
	 * @param elemNew
	 * @return
	 * @throws Exception
	 */
	public static String getDetails(HrOrganizationElement elemOld, HrOrganizationElement elemNew,
									HrOrganizationElementForm formOld, HrOrganizationElementForm formNew) {
		StringBuffer sb = new StringBuffer();
		if (elemOld == null) {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.add", "sys-log"));
			sb.append(getOrgTypeInfo(elemNew));
			sb.append(elemNew.getFdName());
		} else {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.modify", "sys-log"));
			sb.append("【" + elemNew.getFdName() + "】");
			sb.append(getOrgTypeInfo(elemNew));
			try {
				String details = compare(formOld, formNew);
				if (StringUtil.isNull(details)) {
					return null;
				}
				sb.append(details);
			} catch (Exception e) {
				e.printStackTrace();
				return sb.toString();
			}
		}
		return sb.toString();
	}

	public static String getDetails(HrOrganizationElement elemNew, HrOrganizationElement elemOld) {
		StringBuffer sb = new StringBuffer();
		if (elemOld == null) {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.add", "sys-log"));
			sb.append(getOrgTypeInfo(elemNew));
			sb.append(elemNew.getFdName());
		} else {
			sb.append(ResourceUtil.getString("sysLogOaganization.operate.modify", "sys-log"));
			sb.append("【" + elemNew.getFdName() + "】");
			sb.append(getOrgTypeInfo(elemNew));
			try {
				sb.append(compare(elemNew, elemOld));
			} catch (Exception e) {
				e.printStackTrace();
				return sb.toString();
			}
		}
		return sb.toString();
	}

	/**
	 *
	 * 构建一条日志信息
	 *
	 * @param requestContext
	 * @return log
	 * @throws Exception
	 */
	public static HrOrganizationLog buildSysLog(RequestContext requestContext) {
		HrOrganizationLog log = new HrOrganizationLog();
		log.setFdCreateTime(new Date());
		log.setFdIp(requestContext.getRemoteAddr());
		log.setFdMethod(requestContext.getMethod());
		SysOrgPerson operator = UserUtil.getUser();
		if (operator == null) {
			log.setFdOperator(ResourceUtil.getString("sysLogOaganization.system", "sys-log"));
			log.setFdOperatorId("");
		} else {
			log.setFdOperator(operator.getFdName());
			log.setFdOperatorId(operator.getFdId());
		}
		log.setFdParaMethod(requestContext.getParameter("method"));
		log.setFdTargetId(requestContext.getParameter("fdId"));
		log.setFdUrl(requestContext.getRequest().getRequestURI() + "?" + requestContext.getRequest().getQueryString());
		log.setFdBrowser(UserAgentUtil.getBrowser());
		log.setFdEquipment(UserAgentUtil.getOperatingSystem());
		return log;
	}

	/**
	 * 返回组织架构类别信息
	 *
	 * @param elem
	 * @return
	 */
	public static String getOrgTypeInfo(HrOrganizationElement elem) {
		if (elem instanceof HrOrganizationDept) {
			return ResourceUtil.getString("sysLogOaganization.info.dept", "sys-log");
		} else if (elem instanceof HrOrganizationOrg) {
			return ResourceUtil.getString("sysLogOaganization.info.org", "sys-log");
		} else if (elem instanceof HrStaffPersonInfo) {
			return ResourceUtil.getString("sysLogOaganization.info.person", "sys-log");
		} else if (elem instanceof HrOrganizationPost) {
			return ResourceUtil.getString("sysLogOaganization.info.post", "sys-log");
		}
		return "";
	}

	/**
	 *
	 * 比较两个组织架构类里面相同属性的值，并返回有相同属性值有差异的字段名称
	 *
	 * @param formOld
	 * @return formNew
	 * @throws Exception
	 */

	public static String compare(HrOrganizationElementForm formOld, HrOrganizationElementForm formNew)
			throws Exception {

		String changesField = "";

		if (formNew instanceof HrOrganizationElementForm) {
			// 只有人员信息才会增加此校验
			changesField = StringUtil.linkString(changesField, " 、",
					compare(formOld, formNew, HrOrganizationElementForm.class.getName()));
		}

		if (PropertyUtils.isReadable((Object) formOld, "dynamicMap")
				&& PropertyUtils.isReadable((Object) formNew, "dynamicMap")) {
			Map nameOld = (Map) PropertyUtils.getProperty((Object) formOld, "dynamicMap");
			Map nameNew = (Map) PropertyUtils.getProperty((Object) formNew, "dynamicMap");

			for (Object keyOld : nameOld.keySet()) {
				for (Object keyNew : nameNew.keySet()) {
					if (keyOld.equals(keyNew)) {
						if (!(StringUtil.isNull((String) nameOld.get(keyOld))
								&& StringUtil.isNull((String) nameNew.get(keyNew)))) {
							if (StringUtil.isNotNull((String) nameOld.get(keyOld))
									&& StringUtil.isNotNull((String) nameNew.get(keyNew))) {
								if (!(nameOld.get(keyOld).equals(nameNew.get(keyNew)))) {
									String langName = SysLangUtil.getLangDisplayName(keyOld.toString().substring(6));
									changesField = StringUtil.linkString(changesField, " 、", langName
											+ ResourceUtil.getString("sysOrgRoleConf.fdName", "sys-organization"));
								}
							} else {
								String langName = SysLangUtil.getLangDisplayName(keyOld.toString().substring(6));
								changesField = StringUtil.linkString(changesField, " 、",
										langName + ResourceUtil.getString("sysOrgRoleConf.fdName", "sys-organization"));
							}
						}
					}
				}
			}
		}
		if (StringUtil.isNotNull(changesField)) {
			changesField += "。";
		}
		return changesField;
	}

	public static String compare(HrOrganizationElement elemNew, HrOrganizationElement elemOld) throws Exception {

		String changesField = "";

		/*if (elemNew instanceof HrOrganizationElement) {
			// 只有人员信息才会增加此校验
			changesField = StringUtil.linkString(changesField, " 、",
					compare(elemNew, elemOld, HrOrganizationElement.class.getName()));
		}*/

		if(null != elemOld){
			if(!elemNew.getFdIsAvailable().equals(elemOld.getFdIsAvailable())){
				changesField = StringUtil.linkString(changesField, " 、", ResourceUtil.getString("sysOrgRoleConf.fdIsAvailable", "sys-organization"));
			}
			if(elemNew.getFdParent() != elemOld.getFdParent()){
				changesField = StringUtil.linkString(changesField, " 、", ResourceUtil.getString("sysOrgMatrix.simulator.dept", "sys-organization"));
			}
		} else {
			changesField = "新增" + getOrgTypeInfo(elemNew);
		}
		if (StringUtil.isNotNull(changesField)) {
			changesField += "。";
		}
		return changesField;
	}

	/**
	 * 比较两个类里面相同属性的值，并返回有相同属性值有差异的字段名称
	 *
	 * @param objOld
	 * @param objNew
	 * @param clsName
	 * @return changesField
	 * @throws Exception
	 */
	public static String compare(Object objOld, Object objNew, String clsName) throws Exception {

		boolean flag = false;
		String changesField = "";
		java.lang.reflect.Field[] fields = null;
		if (objNew instanceof HrOrganizationElementForm) {
			fields = ClassUtils.forName(clsName).getDeclaredFields();
		} else {
			java.lang.reflect.Field[] elementFields = ClassUtils.forName(clsName).getDeclaredFields();
			java.lang.reflect.Field[] moreFields = ClassUtils.forName(objOld.getClass().getName()).getDeclaredFields();
			fields = (java.lang.reflect.Field[]) ArrayUtils.addAll(elementFields, moreFields);
		}

		for (int i = 0; i < fields.length; i++) {
			java.lang.reflect.Field fld = fields[i];
			String fieldName = fld.getName();

			Object valOld = null;
			Object valNew = null;
			try {
				valOld = BeanUtils.getProperty(objOld, fieldName);
				valNew = BeanUtils.getProperty(objNew, fieldName);
				if ("".equals(valNew)) {
					valNew = null;
				}
				if ("".equals(valOld)) {
					valOld = null;
				}
			} catch (Exception e) {
				if (e instanceof NoSuchMethodException) {
					continue;
				}
			}
			if (!ObjectUtil.equals(valOld, valNew)) {
				/* 取得数据字典 中的messageKey */
				SysDataDict dataDict = SysDataDict.getInstance();
				// 需要获取新模块的数据字典
				String newModelName = ((HrOrganizationElementForm) objNew).getModelClass().getName();
				if (newModelName.contains("$$")) {
					newModelName = newModelName.substring(0, newModelName.indexOf("$$"));
				}

				SysDictModel dictModel = dataDict.getModel(newModelName);

				Map<String, SysDictCommonProperty> propertyMap = dictModel.getPropertyMap();
				if ("fdPersonIds".equals(fieldName)) {
					fieldName = "hbmPersons";
				} else if ("fdParentId".equals(fieldName)) {
					fieldName = "hbmParent";
				} else if ("fdThisLeaderId".equals(fieldName)) {
					fieldName = "hbmThisLeader";
				} else if ("fdSuperLeaderId".equals(fieldName)) {
					fieldName = "hbmSuperLeader";
				} else if ("fdPostIds".equals(fieldName)) {
					fieldName = "hbmPosts";
				} else if ("fdMemberIds".equals(fieldName)) {
					fieldName = "hbmMembers";
				} else if ("fdStaffingLevelId".equals(fieldName)) {
					fieldName = "fdStaffingLevel";
				} else if ("fdBranLeaderId".equals(fieldName)) {
					fieldName = "fdBranLeader";
				}
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap.get(fieldName);
				if (commonProperty == null) {
					continue;
				}
				String messageKey = commonProperty.getMessageKey();
				if (messageKey == null) {
					continue;
				}
				String[] bundleAndKey = messageKey.split(":");
				// 返回有改动的字段名称
				changesField = StringUtil.linkString(changesField, " 、",
						bundleAndKey.length > 1 ? ResourceUtil.getString(bundleAndKey[1], bundleAndKey[0])
								: ResourceUtil.getString(bundleAndKey[0]));
			}
		}
		return changesField;
	}

	/**
	 * <p>复制EKP组织架构到人事组织架构</p>
	 * @param hrOrganizationElement
	 * @param element
	 * @throws Exception
	 * @author sunj
	 */
	public static void copyEkpOrgToHrOrg(HrOrganizationElement hrOrganizationElement, SysOrgElement element)
			throws Exception {
		if(!element.getFdId().equals(hrOrganizationElement.getFdId())) {
			hrOrganizationElement.setFdId(element.getFdId());
		}
		hrOrganizationElement.setFdName(element.getFdName());
		hrOrganizationElement.setFdNamePinYin(element.getFdNamePinYin());
		hrOrganizationElement.setFdNameSimplePinyin(element.getFdNameSimplePinyin());
		hrOrganizationElement.setFdOrder(element.getFdOrder());
		hrOrganizationElement.setFdNo(element.getFdNo());
		hrOrganizationElement.setFdKeyword(element.getFdKeyword());
		hrOrganizationElement.setFdIsAvailable(element.getFdIsAvailable());
		hrOrganizationElement.setFdIsAbandon(element.getFdIsAbandon());
		hrOrganizationElement.setFdIsBusiness(element.getFdIsBusiness());
		hrOrganizationElement.setFdSource("EKP"); //来源
		hrOrganizationElement.setFdMemo(element.getFdMemo());

		if (element.getFdOrgType().equals(ORG_TYPE_PERSON)
				&& hrOrganizationElement.getFdOrgType().equals(HR_TYPE_PERSON)) {
			SysOrgPerson orgPerson = (SysOrgPerson) element;
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrOrganizationElement;
			personInfo.setFdMobileNo(orgPerson.getFdMobileNo());
			personInfo.setFdEmail(orgPerson.getFdEmail());
			personInfo.setFdLoginName(orgPerson.getFdLoginName());
			personInfo.setFdSex(orgPerson.getFdSex());
			personInfo.setFdOrgPerson(orgPerson);
			if (element.getFdIsAvailable()) {
				if (EnumUtils.isValidEnum(HrStaffConstant.AllStaffStatus.class, personInfo.getFdStatus())) {
					personInfo.setFdStatus(personInfo.getFdStatus());
				}
			} else {
				personInfo.setFdStatus("leave");
			}
		}
		hrOrganizationElement.setFdCreateTime(element.getFdCreateTime());
	}

	public static void setHierarchy(HrOrganizationElement hrOrganizationElement, SysOrgElement element)
			throws Exception {
		HrOrgUtil.copyEkpOrgToHrOrg(hrOrganizationElement, element);
		HrOrganizationElement fdParent = null;
		if (null != element.getFdParent()) {
			fdParent = (HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(element.getFdParent().getFdId());
			hrOrganizationElement.setFdParent(fdParent);
		}
		if (null != element.getHbmParentOrg()) {
			hrOrganizationElement.setHbmParentOrg((HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(element.getHbmParentOrg().getFdId()));
		}
		if (null != element.getHbmThisLeader()) {
			//本级领导
			hrOrganizationElement.setHbmThisLeader((HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(element.getHbmThisLeader().getFdId()));
		}
		if (null != element.getHbmSuperLeader()) {
			//上级领导
			hrOrganizationElement.setHbmSuperLeader((HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(element.getHbmSuperLeader().getFdId()));
		}
		if (element.getFdOrgType().equals(ORG_TYPE_PERSON) && !ArrayUtil.isEmpty(element.getFdPosts())) {
			List<SysOrgElement> posts = element.getFdPosts();
			List<String> ids = new ArrayList<String>();
			List<HrOrganizationPost> hrPosts = new ArrayList<HrOrganizationPost>();
			for (SysOrgElement post : posts) {
				ids.add(post.getFdId());
			}
			hrPosts = getHrOrganizationPostService().findList(HQLUtil.buildLogicIN("fdId", ids), null);
			if (!ArrayUtil.isEmpty(hrPosts)) {
				HrStaffTrackRecord trackRecord = null;
				for (int i = 0; i < hrPosts.size(); i++) {
					String fdType = "2";
					if (i == 0) {
						List fdPosts = new ArrayList();
						fdPosts.add(hrPosts.get(i));
						hrOrganizationElement.setFdPosts(fdPosts);
						fdType = "1";
					}
					if (getHrStaffTrackRecordService().checkUnique(null, hrOrganizationElement.getFdId(),
							null == fdParent ? null : fdParent.getFdId(), hrPosts.get(i).getFdId(), null, fdType)) {
						trackRecord = new HrStaffTrackRecord();
						trackRecord.setFdPersonInfo((HrStaffPersonInfo) hrOrganizationElement);
						trackRecord.setFdEntranceBeginDate(new Date());
						trackRecord.setFdHrOrgDept(fdParent);
						trackRecord.setFdHrOrgPost(hrPosts.get(i));
						trackRecord.setFdRatifyDept(element.getFdParent());
						trackRecord.setFdOrgPosts(element.getFdPosts());
						trackRecord.setFdStatus("1");
						trackRecord.setFdType(fdType);
						getHrStaffTrackRecordService().add(trackRecord);
					}
				}
			}
		}
		//EKP岗位人员同步到人事组织架构兼岗
		/*if (element.getFdOrgType().equals(ORG_TYPE_POST)) {
			List<SysOrgPerson> orgPersons = element.getFdPersons();
			hrOrganizationElement.setFdPersons(orgPersons);
		}*/
		//最后更新时间
		hrOrganizationElement.setFdAlterTime(element.getFdAlterTime());
	}

	private static IHrOrganizationElementDao hrOrganizationElementDao;

	public static IHrOrganizationElementDao getHrOrganizationElementDao() {
		if (hrOrganizationElementDao == null) {
			hrOrganizationElementDao = (IHrOrganizationElementDao) SpringBeanUtil
					.getBean("hrOrganizationElementDao");
		}
		return hrOrganizationElementDao;
	}

	/**
	 * 根据机构或部门获取用户数
	 *
	 * @param parent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getPersonCountByOrgDept(HrOrganizationElement parent) {
		long count = 0;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			hqlInfo.setWhereBlock(
					"hrOrganizationElement.fdOrgType = 8 and hrOrganizationElement.fdIsAvailable is true and hrOrganizationElement.fdHierarchyId like :fdHierarchyId");
			hqlInfo.setParameter("fdHierarchyId",
					parent.getFdHierarchyId() + "%");
			List<Long> list = getHrOrganizationElementDao().findValue(hqlInfo);
			count = list.get(0);
		} catch (Exception e) {
			logger.error("", e);
		}
		return count + "";
	}

	public static String replaceCharacter(String oriString) throws Exception {
		Pattern p = Pattern.compile("\\\\n|\\\\r|\\\\r\\\\n");
		Matcher m = p.matcher(oriString);
		String result = m.replaceAll("<br>"); // 将内容中的换行符替换，避免前台JSON解析出错
		p = Pattern.compile("'");
		m = p.matcher(result);
		result = m.replaceAll("‘"); // 将内容中的'替换成‘
		return result;
	}

	public static String getStr(String key) {
		return ResourceUtil.getString(key, "hr-organization");
	}

	/**
	 * 获取Excel单元格的字符串值
	 *
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		String rtnStr;
		switch (cell.getCellType()) {
			case BOOLEAN:
				rtnStr = new Boolean(cell.getBooleanCellValue()).toString();
				break;
			case FORMULA: {
				rtnStr = formatter.format(cell.getNumericCellValue());
				break;
			}
			case NUMERIC: {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
					SimpleDateFormat sdf = null;
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					rtnStr = sdf.format(cell.getDateCellValue());
				} else {
					Double d = cell.getNumericCellValue();
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
					rtnStr = cell.getRichStringCellValue().getString();
					cell.setCellValue(d);
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
				}
				break;
			}
			case BLANK:
			case ERROR:
				rtnStr = "";
				break;
			default:
				rtnStr = cell.getRichStringCellValue().getString();
		}
		return formatString(rtnStr.trim());
	}

	/**
	 * 去除字符串中的无法辨认的字符
	 *
	 * @param s
	 * @return
	 */
	public static String formatString(String s) {
		StringBuffer rtnStr = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 128 && c <= 255 && EXCEPT_STRING.indexOf(c) == -1) {
				continue;
			}
			rtnStr.append(c);
		}
		return rtnStr.toString();
	}

	public static void addRowError(JSONObject errorRow, int rowIndex, int colIndex, String msg) {
		errorRow.put("errRowNumber", rowIndex);
		if (errorRow.get("errColNumbers") == null) {
			errorRow.put("errColNumbers", colIndex + "");
		} else {
			errorRow.put("errColNumbers", errorRow.getString("errColNumbers") + "," + colIndex);
		}
		if (errorRow.get("errInfos") == null) {
			errorRow.put("errInfos", msg);
		} else {
			errorRow.put("errInfos", errorRow.getString("errInfos") + "<br>" + msg);
		}
	}

	/**
	 * <p>解析岗位中的人员</p>
	 */
	public static List<HrStaffPersonInfo> getPersonByPosts(List<HrOrganizationPost> posts) {
		List<HrStaffPersonInfo> persons = new ArrayList<HrStaffPersonInfo>();
		for (HrOrganizationPost post : posts) {
			persons.addAll(post.getFdPersons());
		}
		return persons;
	}

}
