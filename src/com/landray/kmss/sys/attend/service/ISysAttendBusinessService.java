package com.landray.kmss.sys.attend.service;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.Date;
import java.util.List;

public interface ISysAttendBusinessService extends IBaseService {
	/**
	 * 根据人员和时间以及类型 查询出对应的流程数据。包含在途的
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @param fdTypes
	 *            类型 出差,请假,外出等
	 * @param isHaveIng 是否包含在流程中的
	 * @return
	 * @throws Exception
	 */
	List<SysAttendBusiness> findBussList(List orgIdList, Date startTime,
												Date endTime, List fdTypes,boolean isHaveIng) throws Exception;
	/**
	 * 根据流程ID 查询考勤的业务表数据
	 * @param processId 流程ID
	 * @return 返回考勤流程业务存储的数据
	 * @throws Exception
	 */
	List<SysAttendBusiness> findBusinessByProcessId(String processId,Integer overFlag)
			throws Exception;
	/**
	 * 计算加班工时
	 * @param bus 根据流程信息
	 * @param statMap 数据信息
	 * @throws Exception
	 */
	public void addOvertime(SysAttendBusiness bus, JSONObject statMap);

	public List<SysAttendBusiness> findByProcessId(String processId)
			throws Exception;

	/**
	 * 根据用户,开始时间,类型获取有效的出差/请假等流程记录
	 * 
	 * @param orgIdList
	 *            用户id集合.为空则获取所有生效的出差流程
	 * @param date
	 *            开始时间,为空默认为当天
	 * @param fdType
	 *            类型 出差,请假等
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendBusiness> findList(List orgIdList, Date date,
			Integer fdType) throws Exception;

	/**
	 * 获取请假相关记录(可能包括无效数据，如请假时长为0，需要根据业务需求过滤)
	 * 
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendBusiness> findLeaveList(List orgIdList, Date startTime,
			Date endTime) throws Exception;

	/**
	 * 获取相关业务记录(出差,请假,出差等)
	 * 
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @param fdTypes
	 *            类型 出差,请假,外出等
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendBusiness> findBussList(List orgIdList, Date startTime,
			Date endTime, List fdTypes) throws Exception;

	/**
	 * 筛选有效流程数据
	 * 
	 * @param docCreator
	 * @param date
	 * @param busList
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendBusiness> genUserBusiness(SysOrgElement docCreator,
			Date date, List<SysAttendBusiness> busList) throws Exception;

	/**
	 * 过滤当前用户列表某一时间段是否存在流程
	 * @param orgIdList
	 * @param startTime
	 * @param endTime
	 * @param fdTypes
	 * @param isHaveIng 是否正在进行中
	 * @return
	 * @throws Exception
	 */
	public List<String> findBussTargetList(List orgIdList, Date startTime,
										   Date endTime, List fdTypes,boolean isHaveIng) throws Exception;
}
