package com.landray.kmss.sys.organization.webservice.out;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestBody;

@WebService
public interface ISysSynchroGetOrgWebService extends ISysWebservice {

	/**
	 * 获取需要更新的组织架构详细信息
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getUpdatedElements(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;
	
	/**
	 * 获取需要更新的组织架构总数
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getUpdatedElementsCount(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	
	/**
	 * 对接口 getUpdatedElements 优化,getUpdatedElements 使用不变，新增
	 * 新增参数:endTimeStamp 表示结束时间
	 * 返回参数:total 表示根据入参查询的结果总数
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	//public SysSynchroOrgResult getUpdatedElements1605(SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 获取所有组织架构的基本信息
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getElementsBaseInfo(
			SysSynchroGetOrgBaseInfoContext orgContext) throws Exception;
	
	/**
	 * 对接口 getElementsBaseInfo 优化,getElementsBaseInfo 使用不变，新增
	 * 新增参数:endTimeStamp 表示结束时间
	 * 返回参数:total 表示根据入参查询的结果总数
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	//public SysSynchroOrgResult getElementsBaseInfo1605(SysSynchroGetOrgBaseInfoContext orgContext) throws Exception;

	/**
	 * 角色线配置
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getRoleConfInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 角色线
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getRoleLineInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 角色线可编辑者
	 * 
	 * @return
	 * @throws Exception
	 */
	// public SysSynchroOrgResult getRoleLineEditorInfo() throws Exception;
	/**
	 * 角色线默认角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getRoleLineDefaultRoleInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 通用岗位
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getRoleInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 群组分类
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getOrgGroupCateInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 职级
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getOrgStaffingLevelInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;

	/**
	 * 群组分类
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getRoleConfCateInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception;
	
	
	/**
	 * 获取需要更新的组织架构详细信息，按缓存分页模式
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgTokenResult getUpdatedElementsByToken(
			SysSynchroGetOrgInfoTokenContext orgContext) throws Exception;
	
	/**
	 * 生态组织基本数据
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getElementsBaseInfoForEco()
			throws Exception;
	
	
	/**
	 * 对接口 getElementsBaseInfoForEco 优化,getElementsBaseInfoForEco 使用不变，新增
	 * 新增参数:endTimeStamp 表示结束时间
	 * 返回参数:total 表示根据入参查询的结果总数
	 * 
	 * @return
	 * @throws Exception
	 */
	//public SysSynchroOrgResult getElementsBaseInfoForEco1605() throws Exception;
	
	/**
	 * 生态组织更新数据
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getUpdatedElementsForEco(SysSynchroGetOrgInfoContext orgContext) throws Exception;
	
	
	/**
	* 对接口 getUpdatedElementsForEco 优化,getUpdatedElementsForEco 使用不变，新增
	 * 新增参数:endTimeStamp 表示结束时间
	 * 返回参数:total 表示根据入参查询的结果总数
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	//public SysSynchroOrgResult getUpdatedElementsForEco1605(SysSynchroGetOrgInfoContext orgContext) throws Exception;
	
	
	/**
	 * 生态组织动态扩展属性
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult getDynamicExternalData() throws Exception;
	
	
	public SysSynchroOrgResult getElementsBaseInfoV2(
			SysSynchroGetOrgBaseInfoContextV2 orgContext) throws Exception;

	public SysSynchroOrgResult getUpdatedElementsV2(
			SysSynchroGetOrgInfoContextV2 orgContext) throws Exception;

	public SysSynchroOrgResult getRoleConfMemberInfo(
			SysSynchroGetOrgInfoContext orgContext)
			throws Exception;

	/**
	 * 根据扩展参数查询组织
	 *
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public SysSynchroOrgResult findByExtendPara(SysSynchroGetOrgInfoContextV2 orgContext) throws Exception;

}
