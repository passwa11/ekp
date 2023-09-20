package com.landray.kmss.sys.time.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-12
 */
public interface ISysTimeLeaveAmountService extends IBaseService {

	/**
	 * 根据人员，年份，获取额度信息
	 * 
	 * @param year
	 * @param fdPersonId
	 * @return
	 * @throws Exception
	 */
	public SysTimeLeaveAmount getLeaveAmount(Integer year, String personId);

	/**
	 * 根据人员，年份，请假类型编号,获取某个假期的额度信息
	 * 
	 * @param year
	 * @param personId
	 * @param leaveType
	 * @return
	 */
	public SysTimeLeaveAmountItem getLeaveAmountItem(Integer year,
			String personId, String leaveType);

	/**
	 * 根据人员，年份，请假类型编号,获取某个假期的额度信息 (注:接口getLeaveAmountItem功能重复)
	 * 
	 * @param year
	 * @param personId
	 * @param leaveName
	 * @return
	 */
	public SysTimeLeaveAmountItem getLeaveAmountItemByType(Integer year,
			String personId, String leaveType);

	/**
	 * 根据假期规则，返回一个额度信息
	 * 
	 * @param amount
	 * @param leaveRule
	 * @return
	 * @throws Exception
	 */
	public SysTimeLeaveAmountItem createLeaveAmountItem(
			SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule,Map<String,String> peseionMap)
			throws Exception;

	/**
	 * 根据假期规则，更新额度信息
	 * 
	 * @param item
	 * @param leaveRule
	 * @throws Exception
	 */
	public void updateLeaveAmountItem(SysTimeLeaveAmountItem item,
			SysTimeLeaveAmount amount, SysTimeLeaveRule leaveRule,Map<String,String> peseionMap)
			throws Exception;

	/**
	 * 根据假期类型列表和人员列表更新假期额度信息
	 * @param ruleIds
	 * @param personIds
	 * @throws Exception
	 */
	public void updateOrAddAmomunt(Set<String> ruleIds,
								   List<String> personIds)
			throws Exception;
	/**
	 * 根据假期规则，更新已有的额度信息
	 * @param ruleId
	 * @throws Exception
	 */
	public void updateOrAddAmomunt(String ruleId)
			throws Exception;

	/**
	 * 获取所有有效人员
	 * 
	 * @return
	 */
	public Map<String, String> getPerson();

	/**
	 * 获取开启了额度管理的假期规则
	 * 
	 * @return
	 */
	public List getAllLeaveRule();

	public HSSFWorkbook buildWorkBook(RequestContext requestContext)
			throws Exception;

	/**
	 * 获取年度开始和结束时间
	 * @param year 年度
	 * @param personId 人员ID
	 * @return: java.util.Map<java.lang.String,java.util.Date>
	 * @author: wangjf
	 * @time: 2022/4/13 2:07 下午
	 */
	Map<String, Date> getSysTimeLeaveAmountCreateAndEndTime(int year, String personId) throws Exception;

	/**
	 * 是否是最后一年度
	 * @param year
	 * @param personId
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/4/13 4:38 下午
	 */
	boolean isLastYear(int year, String personId)throws Exception;

	/**
	 * 删除id数据组
	 * @param ids
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/4/27 11:47 上午
	 */
	void deleteIds(String[] ids) throws Exception;



	/**
	 * 根据人事档案计算当年应休年假
	 * @param hrStaffPersonInfo
	 * @throws Exception
	 */
	public Double getRemainingAmount(HrStaffPersonInfo hrStaffPersonInfo,String fdLeaveTime);

	/**
	 * (返聘)
	 * 根据假期类型列表和人员列表更新返聘假期额度信息
	 * @param leaveRule
	 * @param personList
	 * @throws Exception
	 */
	public void updateRehireAmomunt(SysTimeLeaveRule leaveRule,List<String> personList) throws Exception;

	/**
	 * 根据规则计算假期天数
	 * @param leaveRule
	 * @param createTime
	 * @param currentDate
	 * @return
	 * @throws Exception
	 */
	public Float getTotalDay(SysTimeLeaveRule leaveRule,String createTime, String currentDate) throws Exception;
}
