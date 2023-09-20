package com.landray.kmss.sys.organization.webservice.eco;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

/**
 * 生态组织WebService接口
 * 
 * @author panyh
 *
 *         2020年9月7日 下午3:05:27
 */
@WebService
public interface ISysSynchroEcoWebService extends ISysWebservice {

	/**
	 * 新增(更新)生态组织
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public SysSynchroEcoResult saveEcoElement(SysSynchroEcoContext context) throws Exception;

	/**
	 * 获取生态组织
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public SysSynchroEcoResult findEcoElement(SysSynchroEcoContext context) throws Exception;

	/**
	 * 外转内
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public SysSynchroEcoResult updateOutToIn(SysSynchroEcoContext context) throws Exception;

	/**
	 * 内转外
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public SysSynchroEcoResult updateInToOut(SysSynchroEcoContext context) throws Exception;

	/**
	 * 外转外
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public SysSynchroEcoResult updateOutToOut(SysSynchroEcoContext context) throws Exception;

}
