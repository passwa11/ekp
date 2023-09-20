package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IHrOrganizationElementService extends IExtendDataService {
	/**
	 * 更新组织的类型
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public boolean updateHrOrgType(String fdId,Integer fdOrgType) throws Exception;
	/**
	 * 根据SQL语句查询单个ID的值
	 * @param sql
	 * @return
	 */ 
	public List<String> getIdByJdbc(String sql,List<Object> params);

	/**
	 * <p>同步人事档案历史数据到人事组织架构</p>
	 * @throws Exception
	 * @author sunj
	 */
	public void initStaffPerson() throws Exception;

	/**
	 * <p>通过名称获取人事组织架构</p>
	 * @param fdName
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public HrOrganizationElement findOrgByName(String fdName) throws Exception;

	/**
	 * <p>查询组织架构，通过类型返回对象</p>
	 * @param fdId
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public IBaseModel findById(String fdId) throws Exception;

	/**
	 * <p>合并组织</p>
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public void updateMergeOrg(String currOrgId, String newOrgId) throws Exception;

	/**
	 * <p>禁用组织</p>
	 * @param currOrgId
	 * @param newOrgId
	 * @throws Exception
	 * @author sunj
	 */
	public boolean updateInvalidated(String fdId, RequestContext requestContext) throws Exception;

	/**
	 * <p>启用组织</p>
	 * @param fdId
	 * @param requestContext
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public boolean updateValid(String fdId, RequestContext requestContext) throws Exception;

	public HrOrganizationElement findOrgById(String fdId) throws Exception;


	/**
	 * <p>检查编码是否存在</p>
	 * @param fdId
	 * @param fdOrgType
	 * @param fdNo
	 * @throws Exception
	 * @author sunj
	 */
	public void checkFdNo(String fdId, Integer fdOrgType, String fdNo) throws Exception;

	/**
	 * <p>检查名称是否存在</p>
	 * @param fdId
	 * @param fdOrgType
	 * @param fdNo
	 * @throws Exception
	 * @author sunj
	 */
	public void checkFdName(String fdId, Integer fdOrgType, String fdName) throws Exception;

	public JSONObject addImportData(Workbook wb, Locale locale)
			throws Exception;

	public HSSFWorkbook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception;


	public abstract String addOrg(IBaseModel modelObj) throws Exception;

	public abstract void updateOrg(IBaseModel modelObj) throws Exception;

	public JSONObject saveElementImportData(InputStream inputStream,
			Locale locale)
			throws Exception;

	/**
	 * 将机构/部门/岗位/个人/群组展开成个人ID
	 * 
	 * @param orgList
	 *            组织架构列表
	 * @return 展开的结果，为个人ID列表
	 * @throws Exception
	 */
	public abstract List expandToPersonIds(List orgList) throws Exception;

	public HrOrganizationElement findByOrgNoAndName(String fdNo, String fdName) throws Exception;

	/**
	 * 离职用户
	 * 根据用户ID，设置组织架构中该人员是领导的数据设置为空
	 * @param userFdId
	 * @return
	 * @throws Exception
	 */
	public void updateHrOrgLeader(String userFdId) throws Exception;

	public void addSysOrgMdifyLog (HrOrganizationElement newElement) throws Exception;
}
