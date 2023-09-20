package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.forms.SysAttendMainForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 签到表业务对象接口
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public interface ISysAttendMainService extends IBaseService {

	/**
	 * 保存用户打卡的记录
	 * 只保存考勤签到
	 * @param main
	 * @throws Exception
	 */
	public void addSignLog(SysAttendMainForm main) throws Exception;
	/**
	 * 获取某天签到记录
	 * 
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findList(String categoryId, Date date) throws Exception;
	/**
	 * 获取签到的有效记录
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List findCustList(String categoryId, Date date) throws Exception;
	/**
	 * @param list
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List format(List<Map<String, Object>> list,
			SysAttendCategory category, Date date) throws Exception;

	public JSONObject formatCalendarData(List<SysAttendMain> list,SysAttendCategory category) throws Exception;
	
	/**
	 * 构建考勤记录excel表头，返回最后一行的序号
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rowStartIdx
	 * @return
	 * @throws Exception
	 */
	public int buildAttendTitle(HSSFWorkbook workbook, HSSFSheet sheet,
			int rowStartIdx) throws Exception;

	/**
	 * 构建考勤记录excel内容，返回最后一行的序号
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rowStartIdx
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int buildAttendContent(HSSFWorkbook workbook, HSSFSheet sheet,
			int rowStartIdx, List list) throws Exception;

	/**
	 * 导出考勤记录
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildAttendWorkBook(List list)
			throws Exception;
	
	/**
	 * 更新考勤数据
	 * @param main
	 */
	public void resetUpdateDayReport(final SysOrgPerson p, final Date time);

	/**
	 * 导出签到记录
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildCustomWorkBook(List list)
			throws Exception;


	public JSONArray formatTrailData(List<SysAttendMain> list,
			RequestContext requestContext) throws Exception;

	public String add(IBaseModel modelObj, Date signTime)
			throws Exception;

	/**
	 * 考勤管理员更新打卡记录
	 * 
	 * @param form
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateByAdmin(IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 获取某天考勤组打卡时间点
	 * 
	 * @param category
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysAttendCategory category, Date date)
			throws Exception;

	/**
	 * 会期签到记录导出
	 * 
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildExtendWorkBook(RequestContext requestContext)
			throws Exception;

	/**
	 * 获取用户关联流程信息(出差/请假等)
	 * 
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public JSONArray findAttendBussList(String categoryId, Date date)
			throws Exception;

	/**
	 * 获取时间区间内用户的考勤记录
	 * 
	 * @param docCreatorId
	 * @param startTime
	 * @param endTime
	 *            结束日期
	 * @return
	 * @throws Exception
	 */
	public List findList(String docCreatorId, Date startDate, Date endDate)
			throws Exception;

	/**
	 * 获取用户打卡记录
	 * 
	 * @param statList
	 * @return
	 * @throws Exception
	 */
	public Map findList(List docCreatorId, Date startDate, Date endDate)
			throws Exception;

	/**
	 * 获取日期区间内用户列表的考勤记录
	 * 
	 * @param orgList
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param statusList
	 *            考勤状态
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendMain> findList(List orgList, Date startDate,
			Date endDate, List statusList) throws Exception;

	/**
	 * 获取用户关联流程信息(出差/请假/外出等)
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public JSONArray findAttendBussinessList(Date date)
			throws Exception;
	
	public long isExistRecord(List<String> userIds) throws Exception;

}
