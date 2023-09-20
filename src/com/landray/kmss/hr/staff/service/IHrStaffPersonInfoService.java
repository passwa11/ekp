package com.landray.kmss.hr.staff.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.excel.WorkBook;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 员工信息
 * 
 * @author 潘永辉 2016-12-27
 * 
 */
public interface IHrStaffPersonInfoService extends IHrOrganizationElementService {
	/**
	 * 根据周期查询对应的生日人列表、分页查询
	 * @param searchDateType 周期类型
	 * @return
	 * @throws Exception
	 */
	Page findByTrialPage(String searchDateType,Date beginDate,int rowSize,int pageNo) throws Exception;
	/**
	 * 根据周期查询对应的生日人列表、分页查询
	 * @param searchDateType 周期类型
	 * @return
	 * @throws Exception
	 */
	Page findByBirthdayPage(String searchDateType, Date beginDate, int rowSize, int pageNo) throws Exception;
	/**
	 * 构建一个导入模板文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildTempletWorkBook(HttpServletRequest request)
			throws Exception;

	/**
	 * 导入数据
	 * 
	 * @param personInfoForm
	 * @return
	 * @throws Exception
	 */
	public KmssMessage saveImportData(HrStaffPersonInfoForm personInfoForm)
			throws Exception;

	/**
	 * 根据登录名查询员工信息
	 * 
	 * @param fdLoginName
	 * @return
	 * @throws Exception
	 */
	public HrStaffPersonInfo findPersonInfoByLoginName(String fdLoginName)
			throws Exception;

	public HrStaffPersonInfo findPersonInfoByLoginNameAndNo(String fdLoginName,
			String fdNo) throws Exception;

	/**
	 * 根据工号查询员工信息
	 * 
	 * @param staffNo
	 * @return
	 * @throws Exception
	 */
	public HrStaffPersonInfo findPersonInfoByStaffNo(String staffNo)
			throws Exception;

	public HrStaffPersonInfo findByOrgPersonId(String fdOrgPersonId)
			throws Exception;

	public HrStaffPersonInfo findByStaffEntryId(String fdStaffEntryId)
			throws Exception;

	public List<HrStaffPersonInfo> findByPost(String fdOrgPostId)
			throws Exception;

	public Page obtainPersons(HQLInfo hqlInfo, String parentId,
			String fdSearchName) throws Exception;

	public List<HrStaffPersonInfo> findByTrial(String EndDate) throws Exception;

	public List<HrStaffPersonInfo> findByBirthday(String EndDate)
	throws Exception;
	
	public List<HrStaffPersonInfo> findByBirthdayToday()
	throws Exception;
	
	public JSONArray getDocCount(String fdPersonId)throws Exception;
	
	public String dataIntrgrity(String fdPersonId)throws Exception;

	/**
	 * 导出花名册
	 * 
	 * @param request
	 * @return
	 */

	public void exportList(HttpServletRequest request,
			HttpServletResponse response, List<HrStaffPersonInfo> rtnList);

	/**
	 * 导出花名册
	 */
	public void exportMap(HttpServletRequest request,
			HttpServletResponse response,
			Map<String, List<HrStaffPersonInfo>> rtnMap);

	public void updatePersonInfo(HrStaffPersonInfoForm personInfoForm,
			HttpServletRequest request) throws Exception;

	public HrStaffPersonInfo updateGetPersonInfo(String fdOrgPersonId)
			throws Exception;

	public JSONArray getStaffMobileIndex() throws Exception;

	public void updatePersonInfPositive() throws Exception;

	public JSONArray getStaffMobileStat(String[] ids) throws Exception;

	/**
	 * 合同导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public WorkBook exportContract(HttpServletRequest request) throws Exception;

	/**
	 * <p>获取在职、待入职、待离职人数</p>
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public Map<String, String> getPersonNum(String fdId) throws Exception;
	
	/**
	 * 设置员工薪资定时任务
	 * @param context
	 */
	public void setSalarySchedulerJob(SysQuartzJobContext context);

	/* 获取组织下人员统计 */
	public JSONObject getPersonStat(String fdId) throws Exception;

	/**
	 * <p>获取当前部门人员数据</p>
	 * @param hqlInfo
	 * @param request 
	 * @return
	 */
	public Page findPersonList(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;

	public String[] getImportFields(boolean isOrg);

	public KmssMessage saveImportData(Sheet sheet,
			HrStaffPersonInfoForm staffForm) throws Exception;

	public void updateSyncHireDate() throws Exception;

	/**
	 * <p>不写任职记录更新人事组织</p>
	 * @throws Exception
	 * @author sunj
	 */
	public void updatePersonInfo(HrStaffPersonInfo personInfo) throws Exception;
	
	/**
	 * 计算登录用户的工龄天数
	 * @return
	 * @throws Exception
	 */
	public JSONObject findWorkDaysByPersonInfo() throws Exception;
	
	/**
	 * 同步离职日期
	 * @param context
	 * @throws Exception
	 */
	public void updateSyncLeaveDate(SysQuartzJobContext context) throws Exception;

	public void exportPersonList(HttpServletRequest request, HttpServletResponse response, List<HrStaffPersonInfo> rtnList,String fdShowCols);

}
