package com.landray.kmss.km.archives.util;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesFileConfig;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.IRuleProvider;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.RuleFact;
import com.landray.kmss.sys.lbpm.engine.manager.NoExecutionEnvironment;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpmservice.node.autobranchnode.ModelVarProviderExtend;
import com.landray.kmss.sys.lbpmservice.node.support.rules.ModelDataFiller;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectXML;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KmArchivesUtil{
	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static final String MODEL_FILLER = "com.landray.kmss.sys.lbpmservice.node.support.rules.routeDecision";

	private static DecimalFormat formatter = new DecimalFormat(
			"####################");

	private static IKmArchivesDetailsService kmArchivesDetailsService;

	protected static IKmArchivesDetailsService getKmArchivesDetailsService() {
		if (kmArchivesDetailsService == null) {
			kmArchivesDetailsService = (IKmArchivesDetailsService) SpringBeanUtil
					.getBean("kmArchivesDetailsService");
		}
		return kmArchivesDetailsService;
	}

	private static IKmArchivesTemplateService kmArchivesTemplateService;

	protected static IKmArchivesTemplateService getKmArchivesTemplateService() {
		if (kmArchivesTemplateService == null) {
			kmArchivesTemplateService = (IKmArchivesTemplateService) SpringBeanUtil
					.getBean("kmArchivesTemplateService");
		}
		return kmArchivesTemplateService;
	}

	private static IKmArchivesBorrowService kmArchivesBorrowService;

	protected static IKmArchivesBorrowService getKmArchivesBorrowService() {
		if (kmArchivesBorrowService == null) {
			kmArchivesBorrowService = (IKmArchivesBorrowService) SpringBeanUtil.getBean("kmArchivesBorrowService");
		}
		return kmArchivesBorrowService;
	}

	private static ProcessExecuteService processExecuteService;

	protected static ProcessExecuteService getProcessExecuteService() {
		if (processExecuteService == null) {
			processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteServiceTarget");
		}
		return processExecuteService;
	}

	private static ProcessServiceManager processServiceManager;

	protected static ProcessServiceManager getProcessServiceManager() {
		if (processServiceManager == null) {
			processServiceManager = (ProcessServiceManager) SpringBeanUtil
					.getBean("lbpmProcessServiceManager");
		}
		return processServiceManager;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmArchivesUtil.class);
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
			if (DateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
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

	public static String getTemplateDense(KmArchivesTemplate kmArchivesTemplate) {
		String fdDenses = "";
		List<KmArchivesDense> fdDenseList= kmArchivesTemplate.getListDenseLevel();
		if(fdDenseList!=null && !fdDenseList.isEmpty()){
			for(KmArchivesDense d:fdDenseList){
				fdDenses += d.getFdName() + ";";
			}
			fdDenses = fdDenses.substring(0, fdDenses.length() - 1);
		}
		return fdDenses;
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

	public static void initModelFromRequest(IBaseModel model,
			RequestContext request) throws Exception {
		String modelName = ModelUtil.getModelClassName(model);
		SysDataDict dataDict = SysDataDict.getInstance();
		Map<String, SysDictCommonProperty> propMap = dataDict
				.getModel(modelName).getPropertyMap();
		for (Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {
			// key以i.开头
			String key = entry.getKey();
			if (key.length() < 3 || !key.startsWith("i.")) {
				continue;
			}
			String[] values = entry.getValue();
			if (values == null || values.length == 0
					|| StringUtil.isNull(values[0])) {
				continue;
			}
			// 属性可写
			String propName = key.substring(2);
			if (!PropertyUtils.isWriteable(model, propName)) {
				continue;
			}
			// 数据字典为简单类型或对象类型
			SysDictCommonProperty prop = propMap.get(propName);
			if (prop == null || prop.isReadOnly() || !prop.isCanDisplay()) {
				continue;
			}
			if (prop instanceof SysDictSimpleProperty) {
				BeanUtils.copyProperty(model, propName, values[0]);
			} else if (prop instanceof SysDictModelProperty) {
				SysDictModel dictModel = dataDict.getModel(prop.getType());
				if (dictModel == null) {
					continue;
				}
				// 获取数据
				IBaseService service = null;
				String beanName = dictModel.getServiceBean();
				if (StringUtil.isNotNull(beanName)) {
					service = (IBaseService) SpringBeanUtil.getBean(beanName);
				}
				if (service == null) {
					continue;
				}
				IBaseModel value = service.findByPrimaryKey(values[0]);
				BeanUtils.copyProperty(model, propName, value);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static String buildCriteria(String serviceBean, String selectBlock,
			String whereBlock, String orderBy) throws Exception {
		// 查询
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceBean);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_EDITOR);
		List rtnList = service.findValue(hqlInfo);
		JSONArray result = new JSONArray();
		for (Object rtnVal : rtnList) {
			if (rtnVal == null) {
				continue;
			}
			String optValue = null;
			String optText = null;
			if (rtnVal instanceof Object[]) {
				Object[] rtnVals = (Object[]) rtnVal;
				if (rtnVals.length == 0 || rtnVals[0] == null) {
					continue;
				}
				optValue = rtnVals[0].toString();
				if (rtnVals.length > 1 && rtnVals[1] != null) {
					optText = rtnVals[1].toString();
				} else {
					optText = optValue;
				}
			} else {
				optValue = rtnVal.toString();
				optText = optValue;
			}
			JSONObject json = new JSONObject();
			json.put("value", optValue);
			json.put("text", optText);
			result.add(json);
		}
		return result.toString();
	}

	public static void buildHql(HttpServletRequest request, CriteriaValue cv,
			HQLInfo hqlInfo,
			Class<?> clazz) throws Exception {
		String modelName = clazz.getName();
		String shortName = ModelUtil.getModelTableName(modelName);
		Map<String, SysDictCommonProperty> propMap = SysDataDict.getInstance()
				.getModel(modelName).getPropertyMap();
		StringBuilder whereBlock = new StringBuilder();

		for (Entry<String, String[]> entry : new ArrayList<Entry<String, String[]>>(
				cv.entrySet())) {
			String key = entry.getKey();
			if ("docTemplate".equals(key)) {
				String nodeType = request.getParameter("nodeType");
				if ("CATEGORY".equals(nodeType)) {// 全局分类下才有的参数
					SysCategoryMain category = (SysCategoryMain) ((IBaseService) SpringBeanUtil
							.getBean("sysCategoryMainService"))
									.findByPrimaryKey(entry.getValue()[0], null,
											true);
					if (category != null) {
						whereBlock
								.append(" and ").append(shortName)
								.append(".docTemplate.docCategory.fdHierarchyId like :category");
						hqlInfo.setParameter("category",
								category.getFdHierarchyId()
										+ "%");
						cv.remove(key);
						continue;
					}
				}
			}
			// key带.的
			int index = key.indexOf(".");
			if (index == -1) {
				continue;
			}
			// value不为空
			String[] values = entry.getValue();
			if (values == null || values.length == 0
					|| StringUtil.isNull(values[0])) {
				continue;
			}
			// 数据字典为对象类型，用like语句查询
			String propName = key.substring(0, index);
			SysDictCommonProperty prop = propMap.get(propName);
			if (prop != null && prop instanceof SysDictModelProperty) {
				whereBlock.append(" and ").append(shortName).append(".")
						.append(key).append(" like :").append(propName);
				hqlInfo.setParameter(propName,
						"%" + values[0].trim() + "%");
				cv.remove(key);
			}
		}
		if (whereBlock.length() > 0) {
			String where = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(where)) {
				hqlInfo.setWhereBlock(whereBlock.substring(5));
			} else {
				hqlInfo.setWhereBlock("(" + where + ")" + whereBlock);
			}
		}
		CriteriaUtil.buildHql(cv, hqlInfo, modelName, shortName);
	}

	/**
	 * 判断档案是否是当前用户借阅过的
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public static KmArchivesDetails getBorrowDetail(String fdId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmArchivesDetails.fdArchives.fdId = :fdArchivesId and kmArchivesDetails.fdBorrower.fdId = :fdBorrowerId and kmArchivesDetails.fdStatus = :fdStatus and kmArchivesDetails.docMain.docStatus=:docStatus");
		hqlInfo.setParameter("fdArchivesId", fdId);
		hqlInfo.setParameter("fdBorrowerId", UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdStatus",
				KmArchivesConstant.BORROW_STATUS_LOANING);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
//		List<KmArchivesDetails> list = (List<KmArchivesDetails>) getKmArchivesDetailsService()
//				.findList(hqlInfo);
		Object one = getKmArchivesDetailsService().findFirstOne(hqlInfo);
		if(one != null){
			return (KmArchivesDetails) one;
		}
//		if (list != null && list.size() > 0) {
//			return list.get(0);
//		}
		return null;
	}

	public static Boolean isCanBorrowByTemplate(String fdId) throws Exception {
		boolean canBorrow = false;
		List<KmArchivesTemplate> kmArchivesTemplateList = getKmArchivesTemplateService().getTemplateByMainDense(fdId);
		if (kmArchivesTemplateList != null && kmArchivesTemplateList.size() > 0) {
			canBorrow = true;
		}
		return canBorrow;
	}

	public static Boolean isCanRenew(String fdId) throws Exception {
		boolean canRenew = false;
		KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) getKmArchivesBorrowService().findByPrimaryKey(fdId);
		List fdBorrowDetails = kmArchivesBorrow.getFdBorrowDetails();
		Date nowDate = new Date();
		// 过期范围
		String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		int range = 0;
		if (StringUtil.isNotNull(expireRange)) {
			try {
				range = Integer.parseInt(expireRange);
			} catch (Exception e) {

			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + range * 24 * 60 * 60 * 1000);
		if (fdBorrowDetails.size() > 0) {
			for (int i = 0; i < fdBorrowDetails.size(); i++) {
				KmArchivesDetails kad = (KmArchivesDetails) fdBorrowDetails.get(i);
				Boolean fdExpired = true;
				KmArchivesMain kmArchivesMain = kad.getFdArchives();
				String authUrl = ModelUtil.getModelUrl(kmArchivesMain);
				if (UserUtil.checkAuthentication(authUrl, "GET")) {
					Boolean fdDstroyed = kmArchivesMain.getFdDestroyed();
					if (kmArchivesMain.getFdValidityDate() == null || (kmArchivesMain.getFdValidityDate() != null
							&& (kmArchivesMain.getFdValidityDate().getTime() > cal.getTimeInMillis()))) {
						fdExpired = false;
					}
					if (!KmArchivesConstant.BORROW_STATUS_EXPIRED.equals(kad.getFdStatus()) && !fdDstroyed
							&& !fdExpired) {
						Date fdRenewReturnDate = kad.getFdRenewReturnDate();
						Date fdReturnDate = kad.getFdReturnDate();
						if (fdRenewReturnDate != null) {
							if (fdRenewReturnDate.getTime() > nowDate.getTime()) {
								canRenew = true;
							}
						} else if (fdReturnDate != null) {
							if (fdReturnDate.getTime() > nowDate.getTime()) {
								canRenew = true;
							}
						}
					}
				}
			}
		}
		return canRenew;
	}

	public static Boolean isCanReBorrow(String fdId) throws Exception {
		boolean canReBorrow = false;
		KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) getKmArchivesBorrowService().findByPrimaryKey(fdId);
		List fdBorrowDetails = kmArchivesBorrow.getFdBorrowDetails();
		// 过期范围
		String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		int range = 0;
		if (StringUtil.isNotNull(expireRange)) {
			try {
				range = Integer.parseInt(expireRange);
			} catch (Exception e) {
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + range * 24 * 60 * 60 * 1000);
		if (fdBorrowDetails.size() > 0) {
			for (int i = 0; i < fdBorrowDetails.size(); i++) {
				KmArchivesDetails kad = (KmArchivesDetails) fdBorrowDetails.get(i);
				KmArchivesMain kmArchivesMain = kad.getFdArchives();
				Boolean fdExpired = true;
				String authUrl = ModelUtil.getModelUrl(kmArchivesMain);
				if (UserUtil.checkAuthentication(authUrl, "GET")) {
					Boolean fdDstroyed = kmArchivesMain.getFdDestroyed();
					if (kmArchivesMain.getFdValidityDate() == null || (kmArchivesMain.getFdValidityDate() != null
							&& (kmArchivesMain.getFdValidityDate().getTime() > cal.getTimeInMillis()))) {
						fdExpired = false;
					}
					if (!fdDstroyed && !fdExpired) {
						canReBorrow = true;
					}
				}
			}

		}
		return canReBorrow;
	}

	/**
	 * 判断当前用户是不是已处理人和历史处理人
	 * 
	 * @param fdId
	 *            文档ID
	 * @return
	 */
	public static boolean isWorkUser(String fdId) {
		ProcessInstanceInfo processInstanceInfo = getProcessExecuteService()
				.load(fdId);
		if (processInstanceInfo != null) {
			return processInstanceInfo.isHandler() || processInstanceInfo.isHistoryhandler();
		}
		return false;
	}

	/**
	 * 判断模块是否开启归档
	 *
	 * @param moduleUrl 模块路径，如:km/review
	 */
	public static boolean isStartFile(String moduleUrl) throws Exception {
		if (StringUtil.isNull(moduleUrl)) {
			return false;
		}
		//业务建模无需通过归档模块开启
		if ("sys/modeling".equals(moduleUrl)) {
			return true;
		}
		KmArchivesFileConfig config = new KmArchivesFileConfig();
		String startFile = config.getFdStartFile();
		String fdModules = config.getFdFileModels();
		return "true".equals(startFile)
				&& StringUtil.isNotNull(fdModules)
				&& fdModules.contains(moduleUrl);
	}

	/**
	 * 归档时获得档案分类扩展属性对应的主文档的字段名
	 * 
	 * @param fileTemplate
	 * @param key
	 * @return
	 */
	public static String getFieldName(KmArchivesFileTemplate fileTemplate,
			String key) {
		String fieldName = null;
		String tmpXml = fileTemplate.getFdTmpXml();
		if (StringUtil.isNotNull(tmpXml)) {
			JSONArray array = JSONArray.fromObject(tmpXml);
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				if (obj.getString("fdField").equals(key)) {
					fieldName = obj.getString("value");
					break;
				}
			}
		}
		return fieldName;
	}

	public static List<SysOrgElement> getFormulaValue(IBaseModel mainModel,
			String script)
			throws Exception {
		List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
		String modelName = ModelUtil.getModelClassName(mainModel);
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			List list = ObjectXML
					.objectXMLDecoderByString(model.getExtendDataXML());
			Map<String, Object> params = new HashMap<String, Object>();
			if (list != null && list.size() > 0) {
				Object obj = list.get(0);
				if (obj instanceof Map) {
					params = (Map<String, Object>) obj;

					// 扩展的数据填充
					mainModel = fillupDataToModel(mainModel, params);
				}
			}
			// 规则提供器
			IRuleProvider ruleProvider = getProcessServiceManager()
					.getRuleService()
					.getRuleProvider(new NoExecutionEnvironment(mainModel));
			// 追加解析器
			ruleProvider.addRuleParser(LbpmFunction.class.getName());
			// 规则事实参数
			RuleFact fact = new RuleFact(mainModel);
			fact.addParameter(new ModelVarProviderExtend(params));
			fact.addParameter(mainModel);
			fact.setModelName(modelName);
			fact.setScript(script);
			fact.setReturnType(SysOrgElement.class.getName() + "[]");
			try {
				rtnVal = (List<SysOrgElement>) ruleProvider
						.executeRules(fact);
			} catch (Exception e) {
				logger.error("公式解析出错", e);
			}
		}
		return rtnVal;
	}

	private static IBaseModel fillupDataToModel(IBaseModel mainModel,
			Map<String, Object> params) {
		IExtension[] extensions = Plugin.getExtensions(MODEL_FILLER, "*",
				"decision");
		for (IExtension extension : extensions) {
			ModelDataFiller filler = Plugin.getParamValue(extension,
					"modelFiller");
			mainModel = filler.fillupDataToModel(mainModel, params);
		}
		return mainModel;
	}

	private static IKmArchivesMainService kmArchivesMainService;

	protected static IKmArchivesMainService getKmArchivesMainService() {
		if (kmArchivesMainService == null) {
			kmArchivesMainService = (IKmArchivesMainService) SpringBeanUtil
					.getBean("kmArchivesMainService");
		}
		return kmArchivesMainService;
	}

	public static Calendar getCalendarInstance() {
		Calendar cal = Calendar.getInstance();
		KmArchivesConfig config;
		try {
			config = new KmArchivesConfig();
			String expireRange = config.getFdSoonExpireDate();
			int range = 0;
			if (StringUtil.isNotNull(expireRange)) {
				try {
					range = Integer.parseInt(expireRange);
				} catch (Exception el) {

				}
			}
			cal.setTimeInMillis(
					cal.getTimeInMillis() + range * 24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal;
	}

	// 判断该档案信息是否有效并且当前用户可借
	public static boolean isValidity(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmArchivesMain.fdId=:fdId and (kmArchivesMain.fdValidityDate > :maxValidityDate or kmArchivesMain.fdValidityDate is null) and kmArchivesMain.docStatus =:docStatus");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("maxValidityDate", KmArchivesUtil.getCalendarInstance().getTime());
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		List<KmArchivesMain> list = (List<KmArchivesMain>) getKmArchivesMainService()
				.findList(hqlInfo);
		// 当前用户正在借阅则不显示
		SysOrgPerson fdBorrower = UserUtil.getKMSSUser().getPerson();
		List<KmArchivesDetails> detailsList = (List<KmArchivesDetails>) getKmArchivesDetailsService()
				.findByFdBorrower(fdBorrower,
						new String[] {
								KmArchivesConstant.BORROW_STATUS_LOANING },
						fdId);
		return !list.isEmpty() && detailsList.isEmpty();
	}

	public static JSONArray toDataArray(List kList)
			throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (int i = 0; i < kList.size(); i++) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) kList
					.get(i);
			JSONObject map = new JSONObject();
			map.put("fdId", kmArchivesMain.getFdId());
			map.put("docSubject", kmArchivesMain.getDocSubject());
			map.put("docTemplate", kmArchivesMain.getDocTemplate().getFdName());
			Date fdValidityDate = kmArchivesMain.getFdValidityDate();
			if (fdValidityDate != null) {
				map.put("fdValidityDate", com.landray.kmss.util.DateUtil.convertDateToString(
						fdValidityDate, com.landray.kmss.util.DateUtil.TYPE_DATE,
						ResourceUtil.getLocaleByUser()));
			} else {
				map.put("fdValidityDate",
						ResourceUtil.getString(
								"kmArchivesMain.fdValidityDate.forever",
								"km-archives"));
			}
			KmArchivesConfig config = new KmArchivesConfig();
			String fdDefaultRange = config.getFdDefaultRange();
			StringBuffer trHTMLCopyPre = new StringBuffer();
			trHTMLCopyPre.append(
					"<label style=\"display: inline-block;height:20px;line-height:20px;\"><input style=\"display: inline-block;vertical-align: middle;\" type=\"checkbox\" name=\"_fdBorrowDetail_Form[");
			StringBuffer trHTMLCopyLast = new StringBuffer();
			trHTMLCopyLast.append(
					"].fdAuthorityRange\" onclick=\"__cbClick(this.name.substring(1),'null',false,null);\" value=\"copy\"");
			if (fdDefaultRange.contains("copy")) {
				trHTMLCopyLast.append(" checked>");
			} else {
				trHTMLCopyLast.append(">");
			}
			trHTMLCopyLast.append(
					"<span>" + ResourceUtil.getString(
							"kmArchivesConfig.fdDefaultRange.copy",
							"km-archives")
							+ "</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			StringBuffer trHTMLDownloadPre = new StringBuffer();
			trHTMLDownloadPre.append(
					"<label style=\"display: inline-block;height:20px;line-height:20px;\"><input style=\"display: inline-block;vertical-align: middle;\" type=\"checkbox\" name=\"_fdBorrowDetail_Form[");
			StringBuffer trHTMLDownloadLast = new StringBuffer();
			trHTMLDownloadLast.append(
					"].fdAuthorityRange\" onclick=\"__cbClick(this.name.substring(1),'null',false,null);\" value=\"download\"");
			if (fdDefaultRange.contains("download")) {
				trHTMLDownloadLast.append(" checked>");
			} else {
				trHTMLDownloadLast.append(">");
			}
			trHTMLDownloadLast.append(
					"<span>" + ResourceUtil.getString(
							"kmArchivesConfig.fdDefaultRange.download",
							"km-archives")
							+ "</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			StringBuffer trHTMLPrintPre = new StringBuffer();
			trHTMLPrintPre.append(
					"<label style=\"display: inline-block;height:20px;line-height:20px;\"><input style=\"display: inline-block;vertical-align: middle;\" type=\"checkbox\" name=\"_fdBorrowDetail_Form[");
			StringBuffer trHTMLPrintLast = new StringBuffer();
			trHTMLPrintLast.append(
					"].fdAuthorityRange\" onclick=\"__cbClick(this.name.substring(1),'null',false,null);\" value=\"print\"");
			if (fdDefaultRange.contains("print")) {
				trHTMLPrintLast.append(" checked>");
			} else {
				trHTMLPrintLast.append(">");
			}
			trHTMLPrintLast.append(
					"<span>" + ResourceUtil.getString(
							"kmArchivesConfig.fdDefaultRange.print",
							"km-archives")
							+ "</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			StringBuffer trHTMLDivPre = new StringBuffer();
			trHTMLDivPre.append("<div id=\"div_fdBorrowDetail_Form[");
			StringBuffer trHTMLDivMid = new StringBuffer();
			trHTMLDivMid.append(
					"].fdAuthorityRange\" style=\"display:none\"><input name=\"fdBorrowDetail_Form[");
			StringBuffer trHTMLDivLast = new StringBuffer();
			trHTMLDivLast.append(
					"].fdAuthorityRange\" type=\"hidden\" value=\""
							+ fdDefaultRange + "\"></div>");
			map.put("copyPre", trHTMLCopyPre.toString());
			map.put("copyLast", trHTMLCopyLast.toString());
			map.put("downloadPre", trHTMLDownloadPre.toString());
			map.put("downloadLast", trHTMLDownloadLast.toString());
			map.put("printPre", trHTMLPrintPre.toString());
			map.put("printLast", trHTMLPrintLast.toString());
			map.put("divPre", trHTMLDivPre.toString());
			map.put("divMid", trHTMLDivMid.toString());
			map.put("divLast", trHTMLDivLast.toString());
			map.put("fdBorrowerId", UserUtil.getKMSSUser().getUserId());
			rtnArray.add(map);
		}
		return rtnArray;
	}

	public static JSONArray toDataArraySimpl(List kList)
			throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (int i = 0; i < kList.size(); i++) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) kList.get(i);
			JSONObject map = new JSONObject();
			map.put("fdId", kmArchivesMain.getFdId());
			map.put("docSubject", kmArchivesMain.getDocSubject());
			if(kmArchivesMain.getFdDense() !=null) {
				map.put("fdDenseName", kmArchivesMain.getFdDense().getFdName());
			}
			map.put("docNumber", kmArchivesMain.getDocNumber()); 
			if(kmArchivesMain.getDocCreator() !=null) {
				map.put("docCreatorName", kmArchivesMain.getDocCreator().getFdName());
			}
			Date fdFileDate = kmArchivesMain.getFdFileDate();
			if (fdFileDate != null) {
				map.put("fdFileDate", com.landray.kmss.util.DateUtil.convertDateToString(
						fdFileDate, com.landray.kmss.util.DateUtil.TYPE_DATE,
						ResourceUtil.getLocaleByUser()));
			}  
			rtnArray.add(map);
		}
		return rtnArray;
	}
	
	public static JSONArray toAppraiseDataArray(List kList)
			throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (int i = 0; i < kList.size(); i++) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) kList
					.get(i);
			JSONObject map = new JSONObject();
			map.put("fdId", kmArchivesMain.getFdId());
			map.put("docSubject", kmArchivesMain.getDocSubject());
			map.put("docNumber", kmArchivesMain.getDocNumber());
			Date fdValidityDate = kmArchivesMain.getFdValidityDate();
			if (fdValidityDate != null) {
				map.put("fdValidityDate", com.landray.kmss.util.DateUtil.convertDateToString(
						fdValidityDate, com.landray.kmss.util.DateUtil.TYPE_DATE,
						ResourceUtil.getLocaleByUser()));
			} else {
				map.put("fdValidityDate",
						ResourceUtil.getString(
								"kmArchivesMain.fdValidityDate.forever",
								"km-archives"));
			}
			rtnArray.add(map);
		}
		return rtnArray;
	}

	public static JSONArray toDestroyDataArray(List kList)
			throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (int i = 0; i < kList.size(); i++) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) kList
					.get(i);
			JSONObject map = new JSONObject();
			map.put("fdId", kmArchivesMain.getFdId());
			map.put("docSubject", kmArchivesMain.getDocSubject());// 名称
			map.put("docNumber", kmArchivesMain.getDocNumber());// 编号
			map.put("docTemplate", kmArchivesMain.getDocTemplate().getFdName());// 所属分类
			Date fdFileDate = kmArchivesMain.getFdFileDate();// 归档日期
			map.put("fdReturnDate", com.landray.kmss.util.DateUtil.convertDateToString(
					fdFileDate, com.landray.kmss.util.DateUtil.TYPE_DATE,
						ResourceUtil.getLocaleByUser()));
			map.put("fdReturnPerson",
					kmArchivesMain.getDocCreator().getFdName());// 归档人

			rtnArray.add(map);
		}
		return rtnArray;
	}

	/**
	 * 附件的浅拷贝
	 */
	public static SysAttMain clone(SysAttMain sysAttMain) throws Exception {
		SysAttMain copyAtt = (SysAttMain) sysAttMain.clone();
		copyAtt.setFdId(IDGenerator.generateID());
		copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
		copyAtt.setDocCreateTime(new Date());
		copyAtt.setFdModelId(null);
		copyAtt.setFdFilePath(null);
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		sysAttMainService.add(copyAtt);
		return copyAtt;
	}
}
