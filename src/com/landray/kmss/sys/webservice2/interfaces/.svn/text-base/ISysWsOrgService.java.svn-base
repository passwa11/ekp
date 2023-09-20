package com.landray.kmss.sys.webservice2.interfaces;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * WebService组织架构接口
 * 
 * @author Jeff
 * 
 */
public interface ISysWsOrgService extends IBaseService {

	/**
	 * 根据JSON对象字串查找人员组织
	 * 
	 * @param jsonObjStr
	 *            格式为{类型: 值}。支持的类型有主键（Id）；编号（PersonNo
	 *            、DeptNo、PostNo、GroupNo），登录名
	 *            （LoginName），关键字（Keyword），LDAP（LdapDN）
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgElement(String jsonObjStr) throws Exception;

	/**
	 * 根据JSON数组字串查找人员组织
	 * 
	 * @param jsonArrStr
	 *            格式为[{类型1: 值1} ,{类型2: 值2}...]。支持的类型有主键（Id）；编号（PersonNo
	 *            、DeptNo、PostNo
	 *            、GroupNo），登录名（LoginName），关键字（Keyword），LDAP（LdapDN）
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgList(String jsonArrStr)
			throws Exception;

	/**
	 * 根据ID查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgById(String value) throws Exception;

	/**
	 * 根据登录名查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByLoginName(String value) throws Exception;

	/**
	 * 根据关键字查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByKeyword(String value) throws Exception;

	/**
	 * 根据编号查找人员
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByPersonNo(String value) throws Exception;

	/**
	 * 根据编号查找部门
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByDeptNo(String value) throws Exception;

	/**
	 * 根据编号查找机构
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByOrgNo(String value) throws Exception;

	/**
	 * 根据编号查找岗位
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByPostNo(String value) throws Exception;

	/**
	 * 根据编号查找群组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByGroupNo(String value) throws Exception;

	/**
	 * 根据LDAP的DN值查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findSysOrgByLdapDN(String value) throws Exception;

	/**
	 * 根据ID查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByIds(String[] values)
			throws Exception;

	/**
	 * 根据登录名查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByLoginNames(String[] values)
			throws Exception;

	/**
	 * 根据关键字查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByKeywords(String[] values)
			throws Exception;

	/**
	 * 根据编号查找人员
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByPersonNos(String[] values)
			throws Exception;

	/**
	 * 根据编号查找部门
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByDeptNos(String[] values)
			throws Exception;

	/**
	 * 根据编号查找机构
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByOrgNos(String[] values)
			throws Exception;

	/**
	 * 根据编号查找岗位
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByPostNos(String[] values)
			throws Exception;

	/**
	 * 根据编号查找群组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByGroupNos(String[] values)
			throws Exception;

	/**
	 * 根据LDAP的DN值查找人员组织
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findSysOrgByLdapDNs(String[] values)
			throws Exception;
}
