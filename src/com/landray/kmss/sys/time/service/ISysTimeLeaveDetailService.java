package com.landray.kmss.sys.time.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;

import java.util.Date;
import java.util.List;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public interface ISysTimeLeaveDetailService extends IBaseService {
	/**
	 * 修改假期额度明细
	 * @param amountItem
	 * @param days
	 * @param leaveDetail
	 * @param leaveRule
	 * @throws Exception
	 */
	public void updateAmountItem(SysTimeLeaveAmountItem amountItem, Float days,
								 SysTimeLeaveDetail leaveDetail,
								 SysTimeLeaveRule leaveRule, Boolean updateStatus) throws Exception;
	public SysTimeLeaveRule getLeaveaRule(String leaveType);
	public void updateDeduct(String[] ids) throws Exception;

	public String updateDeduct(String id) throws Exception;

	public String updateDeduct(SysTimeLeaveDetail leaveDetail) throws Exception;

	/**
	 * 数据验证与扣减额度
	 * @description:
	 * @param leaveDetail
	 * @param status
	 * @return: java.lang.String
	 * @author: wangjf
	 * @time: 2022/2/22 4:36 下午
	 */
	String updateDeduct(SysTimeLeaveDetail leaveDetail,int status) throws Exception;

	/**
	 *
	 * @description:  检查数据的有效性
	 * @param leaveDetail
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/2/22 2:07 下午
	 */
	boolean checkLeaveEffect(SysTimeLeaveDetail leaveDetail) throws Exception;

	public void updateAttend(String id) throws Exception;

	public void updateAttend(String[] ids) throws Exception;
	/**
	 * 根据人员和流程ID 加班、请假明细
	 * @param personId 人员id
	 * @param kmReviewId 流程id
	 * @param beginDate 流程单据的开始时间
	 * @param endDate 流程单据的结束时间
	 * @return
	 * @throws Exception
	 */
	public SysTimeLeaveDetail findLeaveDetail(String personId,String kmReviewId,Date beginDate,Date endDate) throws Exception;
	/**
	 * 获取请假明细
	 * 
	 * @param personId
	 *            用户id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param leaveType
	 *            假期类型编号
	 * @return
	 * @throws Exception
	 */
	List findLeaveDetail(String personId, Date startTime, Date endTime,
			String leaveType) throws Exception;

	/**
	 * 获取用户请假明细
	 * 
	 * @param personId
	 *            用户id
	 * @param startTime
	 *            开始时间
	 * @param isUpdateAttend
	 *            请假是否更新考勤(true时,只查询需要同步数据到考勤的请假明细)
	 * @return
	 * @throws Exception
	 */
	List findLeaveDetail(String personId, Date startTime,
			Boolean isUpdateAttend) throws Exception;

	/**
	 * 更新用户请假明细时长,若用户假期额度变更,并同步更新(重新计算请假时长场景使用)
	 * 
	 * @param leaveDetailId
	 *            请假明细ID
	 * @param newTotalTime
	 *            时长
	 * @param statType
	 *            按天,半天,小时
	 * @param startTime
	 *            重新计算请假开始时间
	 * @throws Exception
	 */
	public void updateLeaveDetail(String leaveDetailId, int newTotalTime,
			Integer statType, Date startTime) throws Exception;

	/**
	 *
	 * @description:  保存上一年度假期扣减情况
	 * @param leaveDetail
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/3/11 11:16 下午
	 */
	void saveLeaveLastAmount(SysTimeLeaveDetail leaveDetail)throws Exception;

}
