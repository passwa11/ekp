package com.landray.kmss.third.im.kk.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
/**
 * kk消息推送业务对象接口
 * 
 * @author 
 * @version 1.0 2017-09-07
 */
public interface IKkImNotifyService extends IBaseService {

	/**
	 * 更新状态
	 */
	public void updateStatus(String notifyTypeId, Integer notifyType, Integer fdStatus);

	/**
	 * 通过待办id、待办类型删除待办
	 */
	public void deleteByNotifyId(String notifyId, Integer notifyType);

	/**
	 * 通过待办id删除通知
	 */
	public void deleteByNotifyId(String notifyId);

	/**
	 * <p>通过待办id、用户id删除记录</p>
	 * @param userId
	 * @author 孙佳
	 */
	public void deleteByUserId(String notifyId, String userId);

	/**
	 * <p>删除多条</p>
	 * @param notifyId
	 * @param userId
	 * @author 孙佳
	 */
	public void deleteByUserAll(String notifyId, List<SysOrgPerson> person);

}
