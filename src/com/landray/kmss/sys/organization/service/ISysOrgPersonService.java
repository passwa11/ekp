package com.landray.kmss.sys.organization.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPersonPrivilege;

import java.util.List;

/**
 * 个人业务对象接口
 * 
 * @author 叶中奇
 */
public interface ISysOrgPersonService extends ISysOrgElementService {
	/**
	 * 保存密码
	 * 
	 * @param id
	 *            个人ID
	 * @param oldPassword
	 *            原密码
	 * @param newPassword
	 *            新密码
	 * @throws Exception
	 */
	public abstract void savePassword(String id, String oldPassword,
			String newPassword, RequestContext requestContext) throws Exception;

	/**
	 * 保存密码
	 * 
	 * @param id
	 *            个人ID
	 * @param newPassword
	 *            新密码
	 * @throws Exception
	 */
	public abstract void savePassword(String id, String newPassword,
			RequestContext requestContext)
			throws Exception;

	/**
	 * 排除fdImportInfo中的这条记录在外的，检测登录名是否存在，
	 * 
	 * @param fdLoginName
	 * @param fdImportInfo
	 *            值为:HrSynchroInProviderImp.class.getName()+fdId
	 * @throws Exception
	 */
	public abstract boolean isExistLoginName(String fdLoginName,
			String fdImportInfo) throws Exception;
	
	/**
	 * 用户解锁操作
	 * @param person
	 * @param requestContext
	 * @throws Exception
	 */
	public void savePersonUnLock(SysOrgPerson person,
			RequestContext requestContext) throws Exception;
	
	public void saveNewPassword(String id, String newPassword,
			RequestContext requestContext) throws Exception;
	
	public void checkMobileNo(SysOrgPerson person) throws Exception;
	
	public boolean validatePassword(String id,String password) throws Exception;

	/**
	 * 检查用户是否需要二次认证
	 * 
	 * @param fdLoginName
	 * @return
	 * @throws Exception
	 */
	public boolean doubleValidation(HttpServletRequest request,
			String personId) throws Exception;

	/**
	 * 批量调动部门
	 * 
	 * @param personIds
	 * @param deptId
	 * @throws Exception
	 */
	public void updateDeptByPersons(String[] personIds, String deptId, RequestContext requestContext)
			throws Exception;
	
	/**
	 * 恢复启用人员
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public SysOrgPerson resumePerson(String fdId) throws Exception;

	/**
	 * 激活三员管理账号
	 * 
	 * @param person
	 * @param isAvailable
	 * @throws Exception
	 */
	public void updateTripartiteAdminActivation(String fdId) throws Exception;

	/**
	 * 普通人员激活
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public void updatePersonActivation(String fdId) throws Exception;
	
	/**
	 * 修改恢复人员并保存组织架构日志
	 * 
	 * @param fdId
	 * @param requestContext
	 * @throws Exception
	 */
	public void saveResumePerson(SysOrgPerson person,RequestContext requestContext) throws Exception;

	/**
	 * 保存特权用户
	 * @param personIds
	 * @throws Exception
	 */
	public int savePrivilege(String[] personIds) throws Exception;

	/**
	 * 根据用户获取特权信息
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public SysOrgPersonPrivilege getByPerson(String personId) throws Exception;

	/**
	 * 删除特权用户
	 * @param personId
	 * @throws Exception
	 */
	public void deletePrivilege(String personId) throws Exception;

	/**
	 * 获取所有特权用户
	 * @return
	 * @throws Exception
	 */
	public JSONArray getPrivileges(HttpServletRequest request) throws Exception;

	/**
	 * 获取特权用户数量
	 * @return
	 * @throws Exception
	 */
	public JSONObject getPrivilegeCounts() throws Exception;

}
