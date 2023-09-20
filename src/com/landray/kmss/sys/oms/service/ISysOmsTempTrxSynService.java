package com.landray.kmss.sys.oms.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;

public interface ISysOmsTempTrxSynService {
	
	/**
	 * 开启一个同步事务，后续新增部门/人员/岗位 需要传该方法产生的事务ID
	 * @param fdSynModel 同步模式，具体模式，请看OmsTempSynModel枚举类型说明
	 * @param synConfig 同步配置，对本次同步生效，具体配置，请看SysOmsSynConfig类说明
	 * @return
	 */
	public OmsTempSynResult<Object> begin(OmsTempSynModel fdSynModel,SysOmsSynConfig synConfig);
	
	/**
	 * 本次同步的部门列表
	 * @param fdTrxId begin 方法产生的事务ID
	 * @param tempDeptList
	 * @return
	 * @throws Exception
	 */
	public OmsTempSynResult<SysOmsTempDept> addTempDept(String fdTrxId, List<SysOmsTempDept> tempDeptList) throws Exception;
	
	/**
	 * 本次同步的岗位列表
	 * @param fdTrxId begin 方法产生的事务ID
	 * @param tempDeptList
	 * @return
	 * @throws Exception
	 */
	public OmsTempSynResult<SysOmsTempPost> addTempPost(String trxId,List<SysOmsTempPost> tempPostList) throws Exception;
	
	/**
	 * 本次同步的人员列表
	 * @param trxId begin 方法产生的事务ID
	 * @param tempPersonList
	 * @return
	 * @throws Exception
	 */
	public OmsTempSynResult<SysOmsTempPerson> addTempPerson(String trxId,List<SysOmsTempPerson> tempPersonList) throws Exception;
	
	/**
	 * 本次同步的岗位列表
	 * @param trxId  begin 方法产生的事务ID
	 * @param tempPpList
	 * @return
	 * @throws Exception
	 */
	public OmsTempSynResult<SysOmsTempPp> addTempPostPerson(String trxId,List<SysOmsTempPp> tempPpList) throws Exception;
	
	/**
	 * 本次同步的部门人员关系列表
	 * @param trxId begin 方法产生的事务ID
	 * @param tempDpList
	 * @return
	 * @throws Exception
	 */
	public OmsTempSynResult<SysOmsTempDp> addTempDeptPerson(String trxId,List<SysOmsTempDp> tempDpList) throws Exception;

	/**
	 * 结束同步事务
	 * @param trxId begin 方法产生的事务ID
	 * @return
	 * @throws Exception 
	 */
	public OmsTempSynResult<Object> end(String trxId) throws Exception;
	
}
