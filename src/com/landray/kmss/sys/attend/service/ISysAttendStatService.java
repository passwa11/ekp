package com.landray.kmss.sys.attend.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONObject;
/**
 * 人员统计表业务对象接口
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public interface ISysAttendStatService extends IBaseService {

	/**
	 * 统计考勤日/月记录数
	 * 
	 * @param dateTime
	 * @param statType
	 *            1:日统计,2:月统计
	 * @param fdDeptId
	 *            部门ID
	 * @return
	 * @throws Exception
	 */
	public JSONObject sumAttendCount(Date dateTime, String statType,
			String fdDeptId, HttpServletRequest request) throws Exception;

	/**
	 * 判断当前用户是否拥有查看所有统计权限(mobile使用)
	 * 
	 * @return
	 */
	public boolean isStatAllReader() throws Exception;

	/**
	 * 判断当前用户是否拥有查看统计权限(mobile使用)
	 * 
	 * @return
	 */
	public boolean isStatReader() throws Exception;

	/**
	 * 判断当前用户是否拥有查看签到统计权限(mobile使用)
	 * 
	 * @return
	 */
	public boolean isSignStatReader() throws Exception;

	/**
	 * 判断当前用户是否是否领导
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isStatDeptLeader() throws Exception;

	/**
	 * 判断当前用户是否是否能查看某个考勤组
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isStatCateReader() throws Exception;

	/**
	 * 获取当前用户负责的考勤组ID列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List findCategoryIds() throws Exception;

	/**
	 * 移动端统计，地址本数据，显示有权限查看的组织架构
	 * 
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	public List addressList(RequestContext xmlContext) throws Exception;
}
