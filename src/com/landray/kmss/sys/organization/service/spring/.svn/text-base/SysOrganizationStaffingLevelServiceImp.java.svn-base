package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrganizationStaffingLevelDao;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelFilterForm;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgStaffingLevelFilterConstant;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.upload.FormFile;
import com.landray.sso.client.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 职级配置业务接口实现
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevelServiceImp extends BaseServiceImp
		implements ISysOrganizationStaffingLevelService, ICheckUniqueBean {

	private ISysOrgPersonService sysOrgPersonService = null;

	private RoleValidator roleValidator;

	private SysOrganizationStaffingLevelCache cache = null;

	private Map<String, String> importLangMaps = null;

	private SysOrganizationStaffingLevelCache getSysOrganizationStaffingLevelCache() {
		if (cache == null) {
			cache = new SysOrganizationStaffingLevelCache();
			try {
				updateCacheLocal(null);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
		return cache;
	}

	public RoleValidator getRoleValidator() {
		return roleValidator;
	}

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	private void checkUniqueName(IBaseModel modelObj) throws Exception {
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) modelObj;
		String fdId = sysOrganizationStaffingLevel.getFdId();
		String fdName = sysOrganizationStaffingLevel.getFdNameOri();

		// 检查是否有重名
		if (!checkUniqueName(fdId, fdName)) {
			throw new Exception(
					ResourceUtil.getString("sys.organization.mustUnique.error",
							"sys-organization", null, fdName));
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		checkUniqueName(modelObj);
		String fdId = super.add(modelObj);
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) modelObj;

		((ISysOrganizationStaffingLevelDao) getBaseDao())
				.clearDefaultFlag(sysOrganizationStaffingLevel);
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		checkUniqueName(modelObj);
		super.update(modelObj);
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) modelObj;
		((ISysOrganizationStaffingLevelDao) getBaseDao())
				.clearDefaultFlag(sysOrganizationStaffingLevel);
	}

	@Override
	public SysOrganizationStaffingLevel getDefaultStaffingLevel()
			throws Exception {
		// TODO 自动生成的方法存根
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("sysOrganizationStaffingLevel.fdIsDefault= :fdIsDefault");
		info.setParameter("fdIsDefault", Boolean.TRUE);
		List list = findList(info);
		if (list != null && !list.isEmpty()) {
			return (SysOrganizationStaffingLevel) list.get(0);
		}
		return null;
	}

	@Override
	public void updateFilterSetting(
			SysOrganizationStaffingLevelFilterForm sysOrganizationStaffingLevelFilterForm,
			RequestContext requestContext) throws Exception {

		SysOrgConfig orgConfig = new SysOrgConfig();
		orgConfig.setFdType(2);
		orgConfig
				.setOrgStaffingLevelFilterEnable(sysOrganizationStaffingLevelFilterForm
						.getIsOrgStaffingLevelFilterEnable());
		orgConfig
				.setOrgStaffingLevelFilterSub(sysOrganizationStaffingLevelFilterForm
						.getOrgStaffingLevelFilterSub());
		orgConfig
				.setOrgStaffingLevelFilterDirection(sysOrganizationStaffingLevelFilterForm
						.getOrgStaffingLevelFilterDirection());

		orgConfig.save();

		MessageCenter.getInstance().sendToOther(
				new SysOrgMessage(
						SysOrgMessageType.ORG_MESSAGE_STAFFING_LEVEL_UPDATE));
		updateCacheLocal(orgConfig);
	}

	@Override
	public void updateCacheLocal(SysOrgConfig orgConfig) throws Exception {
		if (orgConfig == null) {
			orgConfig = new SysOrgConfig();
		}
		SysOrganizationStaffingLevelCache cache = getSysOrganizationStaffingLevelCache();
		boolean isOrgStaffingLevelFilterEnable = getOrgStaffingLevelFilterEnable(orgConfig);
		cache.setOrgStaffingLevelFilterEnable(isOrgStaffingLevelFilterEnable);
		int staffingLevelFilterSub = getStaffingLevelFilterSub(orgConfig);
		cache.setStaffingLevelFilterSub(staffingLevelFilterSub);
		cache.setStaffingLevelFilterDirection(orgConfig
				.getOrgStaffingLevelFilterDirection());

	}

	private boolean getOrgStaffingLevelFilterEnable(SysOrgConfig orgConfig)
			throws Exception {
		boolean isOrgStaffingLevelFilterEnable = false;
		String orgStaffingLevelFilterEnable_str = orgConfig
				.getOrgStaffingLevelFilterEnable();
		if (StringUtil.isNotNull(orgStaffingLevelFilterEnable_str)) {
			isOrgStaffingLevelFilterEnable = Boolean
					.parseBoolean(orgStaffingLevelFilterEnable_str);
		}
		return isOrgStaffingLevelFilterEnable;
	}

	private int getStaffingLevelFilterSub(SysOrgConfig orgConfig)
			throws Exception {
		int staffingLevelFilterSub = 0;
		String staffingLevelFilterSub_str = orgConfig
				.getOrgStaffingLevelFilterSub();
		if (StringUtil.isNotNull(staffingLevelFilterSub_str)) {
			staffingLevelFilterSub = Integer
					.parseInt(staffingLevelFilterSub_str);
		}
		return staffingLevelFilterSub;
	}

	@Override
	public List<SysOrgElement> getStaffingLevelFilterResult(
			List<SysOrgElement> list) throws Exception {

		SysOrganizationStaffingLevelCache cache = getSysOrganizationStaffingLevelCache();

		boolean isOrgStaffingLevelFilterEnable = cache
				.isOrgStaffingLevelFilterEnable();
		if (!isOrgStaffingLevelFilterEnable) {
			return list;
		}

		KMSSUser kmssUser = UserUtil.getKMSSUser();
		if (kmssUser != null && kmssUser.isAdmin()) {
			return list;
		}

		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(kmssUser);
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return list;
		}

		int staffingLevelFilterSub = cache.getStaffingLevelFilterSub();
		String staffingLevelFilterDirection = cache
				.getStaffingLevelFilterDirection();
		SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return new ArrayList<SysOrgElement>();
		}
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getStaffingLevel(user);
		if (sysOrganizationStaffingLevel == null) {
			return list;
		}
		List<SysOrgElement> result = new ArrayList<SysOrgElement>();
		int level = sysOrganizationStaffingLevel.getFdLevel();
		if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_POSITIVE)) {
			level += staffingLevelFilterSub;
			for (SysOrgElement elem : list) {
				if (elem.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
					SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(elem.getFdId());
					sysOrganizationStaffingLevel = getStaffingLevel(person);
					if (sysOrganizationStaffingLevel == null) {
						result.add(elem);
					} else {
						if (sysOrganizationStaffingLevel.getFdLevel() <= level) {
							result.add(elem);
						}
					}
				} else {
					result.add(elem);
				}
			}
		} else if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_NEGATIVE)) {
			level -= staffingLevelFilterSub;
			for (SysOrgElement elem : list) {
				if (elem.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
					SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(elem.getFdId());
					sysOrganizationStaffingLevel = getStaffingLevel(person);
					if (sysOrganizationStaffingLevel == null) {
						result.add(elem);
					} else {
						if (sysOrganizationStaffingLevel.getFdLevel() >= level) {
							result.add(elem);
						}
					}
				} else {
					result.add(elem);
				}
			}
		}

		return result;
	}

	@Override
	public SysOrganizationStaffingLevel getStaffingLevel(SysOrgPerson person)
			throws Exception {
		// TODO 自动生成的方法存根
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person
				.getFdStaffingLevel();
		if (sysOrganizationStaffingLevel != null) {
			return sysOrganizationStaffingLevel;
		}
		sysOrganizationStaffingLevel = getDefaultStaffingLevel();
		if (sysOrganizationStaffingLevel != null) {
			return sysOrganizationStaffingLevel;
		}
		return null;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	@Override
	public List<SysOrgPerson> getPersons(String staffingLevelId)
			throws Exception {
		// TODO 自动生成的方法存根
		if (StringUtil.isNull(staffingLevelId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("sysOrgPerson.fdStaffingLevel.fdId =:staffingLevelId");
		info.setParameter("staffingLevelId", staffingLevelId);
		return sysOrgPersonService.findList(info);
	}

	@Override
	public HQLInfo getPersonStaffingLevelFilterHQLInfo(HQLInfo info)
			throws Exception {
		SysOrganizationStaffingLevelCache cache = getSysOrganizationStaffingLevelCache();

		boolean isOrgStaffingLevelFilterEnable = cache
				.isOrgStaffingLevelFilterEnable();
		if (!isOrgStaffingLevelFilterEnable) {
			return null;
		}

		KMSSUser kmssUser = UserUtil.getKMSSUser();

		if (kmssUser != null && kmssUser.isAdmin()) {
			return null;
		}

		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(kmssUser);
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return info;
		}

		SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return info;
		}
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getStaffingLevel(user);
		if (sysOrganizationStaffingLevel == null) {
			return info;
		}

		int staffingLevelFilterSub = cache.getStaffingLevelFilterSub();
		String staffingLevelFilterDirection = cache
				.getStaffingLevelFilterDirection();

		int level = sysOrganizationStaffingLevel.getFdLevel();
		SysOrganizationStaffingLevel defaultStaffingLevel = getDefaultStaffingLevel();

		info.setJoinBlock("left join sysOrgPerson.fdStaffingLevel staffingLevel");
		if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_POSITIVE)) {
			level += staffingLevelFilterSub;
			if (defaultStaffingLevel == null) {
				info.setWhereBlock(info.getWhereBlock()
						+ " and (staffingLevel is null or staffingLevel.fdLevel <=:level)");
			} else {
				if (level <= defaultStaffingLevel.getFdLevel()) {
					info.setWhereBlock(info.getWhereBlock()
							+ " and (staffingLevel is null or staffingLevel.fdLevel <=:level)");
				} else {
					info.setWhereBlock(info.getWhereBlock()
							+ " and staffingLevel.fdLevel <=:level");
				}
			}

		} else if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_NEGATIVE)) {
			level -= staffingLevelFilterSub;
			if (defaultStaffingLevel == null) {
				info.setWhereBlock(info.getWhereBlock()
						+ " and (staffingLevel is null or staffingLevel.fdLevel >=:level)");
			} else {
				if (level > defaultStaffingLevel.getFdLevel()) {
					info.setWhereBlock(info.getWhereBlock()
							+ " and staffingLevel.fdLevel >=:level");
				} else {
					info.setWhereBlock(info.getWhereBlock()
							+ " and (staffingLevel is null or staffingLevel.fdLevel >=:level)");
				}
			}
		}
		info.setParameter("level", level);
		return info;
	}

	@Override
	public int buildStaffingLevelWhereBlock(StringBuffer sb) throws Exception {

		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getStaffingLevel(UserUtil
				.getUser());

		int staffingLevelFilterSub = cache.getStaffingLevelFilterSub();
		String staffingLevelFilterDirection = cache
				.getStaffingLevelFilterDirection();

		int level = sysOrganizationStaffingLevel.getFdLevel();
		SysOrganizationStaffingLevel defaultStaffingLevel = getDefaultStaffingLevel();

		if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_POSITIVE)) {
			level += staffingLevelFilterSub;
			if (defaultStaffingLevel == null) {
				sb.append(" and (staffingLevel is null or staffingLevel.fdLevel <=:level)");
			} else {
				if (level <= defaultStaffingLevel.getFdLevel()) {
					sb.append(" and (staffingLevel is null or staffingLevel.fdLevel <=:level)");
				} else {
					sb.append(" and staffingLevel.fdLevel <=:level");
				}
			}

		} else if (staffingLevelFilterDirection
				.equals(SysOrgStaffingLevelFilterConstant.ORG_STAFFING_LEVEL_FILTER_DIRECTION_NEGATIVE)) {
			level -= staffingLevelFilterSub;
			if (defaultStaffingLevel == null) {
				sb.append(" and (staffingLevel is null or staffingLevel.fdLevel >=:level)");
			} else {
				if (level > defaultStaffingLevel.getFdLevel()) {
					sb.append(" and staffingLevel.fdLevel >=:level");
				} else {
					sb.append(" and (staffingLevel is null or staffingLevel.fdLevel >=:level)");
				}
			}
		}	
		return level;
	}

	@Override
	public Boolean isStaffingLevelFilter() throws Exception {

		SysOrganizationStaffingLevelCache cache = getSysOrganizationStaffingLevelCache();

		boolean isOrgStaffingLevelFilterEnable = cache
				.isOrgStaffingLevelFilterEnable();
		if (!isOrgStaffingLevelFilterEnable) {
			return false;
		}

		KMSSUser kmssUser = UserUtil.getKMSSUser();

		if (kmssUser != null && kmssUser.isAdmin()) {
			return false;
		}

		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(kmssUser);
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return false;
		}

		SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return false;
		}
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getStaffingLevel(user);
		if (sysOrganizationStaffingLevel == null) {
			return false;
		}
		return true;
	}

	/**
	 * 批量导入字段
	 */
	private String[] importFields = { "fdName", "fdLevel", "fdDescription" };

	@Override
	public HSSFWorkbook buildTempletWorkBook() throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 定义必填字体效果
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗

		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色

		// 备注
		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		HSSFCellStyle style = null;

		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
				.getModel(getBaseDao().getModelName()).getPropertyMap();
		String val = null;
		StringBuffer comments = null;
		int cellIndex = 0;
		for (int i = 0; i < importFields.length; i++) {
			cell = row.createCell(cellIndex++);
			val = importFields[i];
			SysDictCommonProperty property = map.get(val);
			style = getStyle(wb);
			comments = new StringBuffer();
			if (property.isNotNull()) {
				// 必填字段，字体设置为红色
				style.setFont(font2);
				comments.append(ResourceUtil
						.getString(
								"sys-organization:sysOrganizationStaffingLevel.import.required"));
			} else {
				style.setFont(font1);
			}
			try {
				String _comment = ResourceUtil
						.getString(
								"sys-organization:sysOrganizationStaffingLevel.import.comment."
										+ importFields[i]);
				if (StringUtil.isNotNull(_comment)) {
					if (StringUtil.isNotNull(comments.toString())) {
						comments.append(", ");
					}
					comments.append(_comment);
				}
			} catch (Exception e) {
			}
			if (StringUtil.isNotNull(comments.toString())) {
				cell.setCellComment(buildComment(patr, comments.toString()));
			}

			cell.setCellValue(ResourceUtil.getString(property.getMessageKey()));
			cell.setCellStyle(style);

			// 多语言
			if (SysLangUtil.isLangEnabled() && property.isLangSupport()) {
				Map<String, String> langMaps = getLangFields();
				for (String key : langMaps.keySet()) {
					cell = row.createCell(cellIndex++);
					cell.setCellValue(
							ResourceUtil.getString(property.getMessageKey())
									+ "(" + langMaps.get(key) + ")");
					style = getStyle(wb);
					style.setFont(font1);
					cell.setCellStyle(style);
				}
			}
		}
		return wb;
	}

	private Map<String, String> getLangFields() {
		if (importLangMaps == null) {
			importLangMaps = new LinkedHashMap<String, String>();
			Map<String, String> langMaps = SysLangUtil.getSupportedLangs();
			String officialLang = SysLangUtil.getOfficialLang();
			for (String key : langMaps.keySet()) {
				if (!officialLang.equals(key)) { // 要排除官方语言
					importLangMaps.put(key, langMaps.get(key));
				}
			}
		}
		return importLangMaps;
	}

	@Override
	public KmssMessage saveImportData(SysOrganizationStaffingLevelForm staffingLevelForm)
					throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		Map<String, String> langMaps = getLangFields();
		FormFile file = null;
		InputStream is =null;
		if(staffingLevelForm!=null){
			file = staffingLevelForm.getFile();
			if(file!=null) {
                is = file.getInputStream();
            }
		}
		try {
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(is);
			sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			throw new RuntimeException(ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.error",
					"sys-organization"));
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(is);
		}

		// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
		int rowNum = sheet.getLastRowNum();
		if (rowNum < 1) {
			throw new RuntimeException(ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.empty",
					"sys-organization"));
		}

		// 检查文件是否是下载的模板文件
		// 正常来说，第一行是标题
		if (sheet.getRow(0).getLastCellNum() != importFields.length + langMaps.size()) {
			throw new RuntimeException(ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.errFile",
					"sys-organization"));
		}

		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
				.getModel(getModelName()).getPropertyMap();
		int count = 0;
		KmssMessages messages = null;
		StringBuffer errorMsg = new StringBuffer();
		String value = null;
		SysDictCommonProperty property = null;
		SysOrganizationStaffingLevel staffingLevel = null;
		// 从第二行开始取数据
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			messages = new KmssMessages();
			Row row = sheet.getRow(i);
			if (row == null) { // 跳过空行
				continue;
			}
			staffingLevel = new SysOrganizationStaffingLevel();

			int fieldIndex = 0;
			boolean fdLevelError = false;

			for (int j = 0; j < row.getLastCellNum(); j++) {
				boolean hasError = false;
				value = ImportUtil.getCellValue(row.getCell(j));
				String propertyName = importFields[fieldIndex];
				property = map.get(propertyName);
				// 非空判断
				if (property.isNotNull() && StringUtil.isNull(value)) {
					hasError = true;
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"sysOrganizationStaffingLevel.import.error.notNull",
							"sys-organization", null,
							ResourceUtil.getString(property.getMessageKey()))));
				}
				// 整数判断
				if ("Integer".equals(property.getType())
						&& StringUtil.isNotNull(value)) {
					if(!value.matches("\\d+")||Integer.valueOf(value)==0) {
						fdLevelError = true;
						hasError = true;
						messages.addError(new KmssMessage(ResourceUtil.getString(
								"sysOrganizationStaffingLevel.import.error.integer",
								"sys-organization", null,
								ResourceUtil.getString(property.getMessageKey()))));
					}
				}
				if ("fdName".equals(propertyName)
						&& !checkUniqueName(null, value)) { // 名称判断是否唯一
					hasError = true;
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"sys.organization.mustUnique.error",
							"sys-organization", null, value)));
				}

				// 多语言
				if (SysLangUtil.isLangEnabled() && property.isLangSupport()) {
					for (String key : langMaps.keySet()) {
						String langValue = ImportUtil.getCellValue(row.getCell(++j));
						if (StringUtil.isNotNull(langValue)) {
                            BeanUtils.setProperty(staffingLevel, "dynamicMap("
                                    + propertyName + key + ")", langValue);
                        }
					}
				}
				// 这里必须在continue之前，否则会出现属性与列匹配不上的情况
				fieldIndex++;
				if (StringUtil.isNull(value) || hasError) {
					continue;
				}

				BeanUtils.setProperty(staffingLevel, propertyName, value);
			}

			if (!fdLevelError && staffingLevel.getFdLevel() == null) { // 判断是否有级别
				messages.addError(new KmssMessage(ResourceUtil.getString(
						"sysOrganizationStaffingLevel.import.error.notNull",
						"sys-organization", null,
						ResourceUtil.getString(
								"sys-organization:sysOrganizationStaffingLevel.fdLevel"))));
			}

			// 如果有错误，就不进行导入
			if (!messages.hasError()) {
				if (UserOperHelper.allowLogOper("Service_Add", getModelName())) {
					UserOperContentHelper.putAdd(staffingLevel, "fdName", "fdLevel", "fdDescription");
				}
				super.add(staffingLevel);
				count++;
			} else {
				errorMsg.append(ResourceUtil.getString(
						"sysOrganizationStaffingLevel.import.error.num",
						"sys-organization", null, i));
				// 解析错误信息
				for (KmssMessage message : messages.getMessages()) {
					errorMsg.append(message.getMessageKey());
				}
				errorMsg.append("<br>");
			}
		}

		KmssMessage message = null;
		if (errorMsg.length() > 0 && count == 0) {
			errorMsg.insert(0, ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.failed",
					"sys-organization")
					+ "<br>");
			message = new KmssMessage(errorMsg.toString());
			message.setMessageType(KmssMessage.MESSAGE_ERROR);
		} else if (errorMsg.length() > 0 && count > 0) {
			errorMsg.insert(0, ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.portion.failed",
					"sys-organization", null, count)
					+ "<br>");
			message = new KmssMessage(errorMsg.toString());
			message.setMessageType(KmssMessage.MESSAGE_COMMON);
		} else {
			message = new KmssMessage(ResourceUtil.getString(
					"sysOrganizationStaffingLevel.import.success",
					"sys-organization", null, count));
			message.setMessageType(KmssMessage.MESSAGE_COMMON);
		}

		return message;
	}

	/**
	 * 获取带背景和居中的样式
	 * 
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getStyle(HSSFWorkbook wb) {
		// 单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		// 背景色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);

		return style;
	}

	@Override
	public SysOrganizationStaffingLevel findStaffLevelByName(String fdName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrganizationStaffingLevel.fdName = :fdName");
		hqlInfo.setParameter("fdName", fdName);
		List<SysOrganizationStaffingLevel> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 构建批注
	 * 
	 * @param patr
	 * @param value
	 * @return
	 */
	private static HSSFComment buildComment(HSSFPatriarch patr, String value) {
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFClientAnchor anrhor = null;
		if (value.length() < 50) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 2,
					4);
		} else if (value.length() > 150) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					7);
		} else {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					5);
		}
		HSSFComment comment = patr.createComment(anrhor);
		comment.setString(new HSSFRichTextString(value));
		return comment;
	}

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String fdName = requestInfo.getParameter("fdName");
		return checkUniqueName(fdId, fdName) ? "" : fdName;
	}

	private boolean checkUniqueName(String fdId, String fdName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String hql = " sysOrganizationStaffingLevel.fdName=:fdName ";
		hqlInfo.setParameter("fdName", fdName);
		if (StringUtil.isNotNull(fdId)) {
			hql += " and sysOrganizationStaffingLevel.fdId!=:fdId ";
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setWhereBlock(hql);
		List list = findList(hqlInfo);
		if ((list != null) && (list.size() > 0)) {
			return false;
		}
		return true;
	}

}
