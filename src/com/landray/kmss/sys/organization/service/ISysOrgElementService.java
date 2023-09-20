package com.landray.kmss.sys.organization.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.log.forms.SysLogChangePwdForm;
import com.landray.kmss.sys.log.forms.SysLogLoginForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * 组织架构元素业务对象接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgElementService extends IBaseService {
	/**
	 * 格式化组织架构元素，在组织架构类型转换前必须执行
	 * 
	 * @param element
	 *            组织架构元素
	 * @return 组织架构元素
	 * @throws Exception
	 */
	public abstract SysOrgElement format(SysOrgElement element)
			throws Exception;

	public abstract void flushElement(SysOrgElement element) throws Exception;

	public void setNotToUpdateRelation(Boolean notToUpdateRelation);

	public void updateRelation() throws Exception;

	public List findAllChildElement(SysOrgElement element, int orgType)
			throws Exception;

	public void setNotToUpdateHierarchy(Boolean notToUpdateHierarchy);

	/**
	 * 置为无效
	 * 
	 * @param id
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String id, RequestContext requestContext)
			throws Exception;

	/**
	 * 批量置为无效
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String[] ids, RequestContext requestContext)
			throws Exception;
	
	/**
	 * 批量置为有效
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateValidated(String[] ids, RequestContext requestContext) throws Exception;

	/**
	 * 升级element表数据，将fdName的值转化为拼音给fd_name_pinyin赋值
	 * 
	 * @author limh
	 * 
	 */
	public void updatePinYinField() throws Exception;
	
	// 获取当前operatorId登录日志信息
	public List<SysLogLoginForm> getLoginLogByOperatorList(String operatorId,int count,String orderBy) throws Exception;
	
	// 获取当前operatorId操作日志的敏感信息
	public List<SysLogChangePwdForm> getSysLogChangePwdList(String operatorId,int count,String orderBy) throws Exception;
	
	/**
	 * 获取所有上级
	 * 
	 * @param isEcoPth 是否是生态组织地址本
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public JSONArray getParentOrgs(boolean isEcoPth, String fdId) throws Exception;
	
	/**
	 * 获取最近的联系人
	 * @param orgTypePara
	 * @param selectCount
	 * @return
	 * @throws Exception
	 */
	public JSONArray getRecentContactList(String orgTypePara,String selectCount) throws Exception;
	
	/**
	 * 获取最近的联系人
	 * @param orgTypePara
	 * @param selectCount
	 * @return
	 * @throws Exception
	 */
	public JSONArray getRecentContactList(String orgTypePara,String selectCount,String exceptValue) throws Exception;
	
	/**
	 * 获取最近的联系人
	 * 
	 * @param orgTypePara
	 * @param selectCount
	 * @param exceptValue
	 * @param cateId      限定查询的生态组织类型
	 * @return
	 * @throws Exception
	 */
	public JSONArray getRecentContactList(String orgTypePara, String selectCount, String exceptValue, String cateId, String isExternal)
			throws Exception;

	/**
	 * 检查编号是否合法
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void checkFdNo(String fdId, Integer fdOrgType, String fdNo) throws Exception;

	/**
	 * 根据配置获取部门名称
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public JSONArray getDeptName(RequestContext requestContext)
			throws Exception;

	public void setEventEco(Boolean flag);

	public void removeEventEco();

	public abstract JSONObject getIconInfo(String orgId) throws Exception;
	/**
	 * 根据类型查询组织架构主键列表 
	 * @param tableName sys_org_element，hr_org_element
	 * @param orderBy 例如：fd_alter_time desc 
	 * @param orgType in的字符串格式 例如：'1','2','3' 如果为空则无查询条件
	 * @return
	 */
	public List<String> getFdIdByOrgType(String tableName,String orderBy , List<Integer> orgTypes);

	public void updateRelationBatch(Boolean isExternal) throws Exception;

	/**
	 * 非页面操作添加组织架构变更日志
	 * @param element
	 * @param addFlag
	 * @throws Exception
	 */
	public void addOrgModifyLog(SysOrgElement element, boolean addFlag) throws Exception;
}
