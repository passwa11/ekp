package com.landray.kmss.sys.organization.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelFilterForm;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.KmssMessage;

/**
 * 职级配置业务对象接口
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public interface ISysOrganizationStaffingLevelService extends IBaseService {

	public SysOrganizationStaffingLevel getDefaultStaffingLevel()
			throws Exception;

	public void updateFilterSetting(
			SysOrganizationStaffingLevelFilterForm sysOrganizationStaffingLevelFilterForm,
			RequestContext requestContext) throws Exception;

	public SysOrganizationStaffingLevel getStaffingLevel(SysOrgPerson person)
			throws Exception;

	public List<SysOrgElement> getStaffingLevelFilterResult(
			List<SysOrgElement> list) throws Exception;

	public List<SysOrgPerson> getPersons(String staffingLevelId)
			throws Exception;

	public void updateCacheLocal(SysOrgConfig orgConfig) throws Exception;

	public HQLInfo getPersonStaffingLevelFilterHQLInfo(HQLInfo info)
			throws Exception;

	/**
	 * 判断是否执行职级过滤
	 * 
	 * @return
	 * @throws Exception
	 */
	public Boolean isStaffingLevelFilter() throws Exception;

	/**
	 * 构建职级过滤查询语句，员工黄页使用，代码写得有点尴尬，请不要随意使用
	 * 
	 * @param sb
	 * @return
	 * @throws Exception
	 */
	public int buildStaffingLevelWhereBlock(StringBuffer sb) throws Exception;

	/**
	 * 构建下载模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildTempletWorkBook() throws Exception;

	/**
	 * 导入数据
	 * 
	 * @param staffingLevelForm
	 * @return
	 * @throws Exception
	 */
	public KmssMessage
			saveImportData(SysOrganizationStaffingLevelForm staffingLevelForm)
					throws Exception;

	SysOrganizationStaffingLevel findStaffLevelByName(String fdName)
			throws Exception;

}
