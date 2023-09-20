package com.landray.kmss.third.pda.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.pda.model.PdaMessagePushMember;

public interface IPdaMessagePushMemberService extends IBaseService {
	
	/**
	 * 功能：获取需要推送的人员信息列表
	 * @param int deviceType 设备类型
	 * @return List<PdaMessagePushMember> 需要推送的人员信息列表
	 */
	public abstract List<PdaMessagePushMember> getPdaMessagePushMemberList(int deviceType)throws Exception;
	
	/**
	 * 功能：删除所有的有关该设备或该个人的信息
	 * @param String fdDeviceToken 设备token
	 * @param String fdPersonFdId  个人ID
	 * @return void 
	 */
	public abstract void deletePdaMessagePushMemberList(String fdDeviceToken,String fdPersonFdId)throws Exception;
	
	/**
	 * 功能：新增推送人员信息
	 * @param String fdDeviceToken 设备token
	 * @param SysOrgPerson fdPerson  组织架构对象
	 * @param int appType 设备类型
	 * @return void
	 */
	public abstract void addPdaMessagePushMember(String fdDeviceToken,SysOrgPerson fdPerson,int appType)throws Exception;

}
