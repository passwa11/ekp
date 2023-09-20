package com.landray.kmss.sys.organization.interfaces;

import java.util.List;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;

public interface ISysOrgCoreService extends SysOrgConstant {

	/**
	 * 获取匿名用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysOrgPerson getAnonymousPerson() throws Exception;

	/**
	 * 获取everyone用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysOrgPerson getEveryonePerson() throws Exception;

	/**
	 * 获取无状态的匿名用户，即与hibernate会话无关的，类型一个静态变量
	 *
	 * @return
	 * @throws Exception
	 */
	default SysOrgPerson getAnonymousPersonStateless() throws Exception{
		return getAnonymousPerson();
	}

	/**
	 * 获取无状态的everyone用户，即与hibernate会话无关的，类型一个静态变量
	 *
	 * @return
	 * @throws Exception
	 */
	default SysOrgPerson getEveryonePersonStateless() throws Exception{
		return getEveryonePerson();
	}

	/**
	 * 将机构/部门/岗位/个人/群组展开成个人
	 * 
	 * @param orgList
	 *            组织架构列表
	 * @return 展开的结果，为个人列表
	 * @throws Exception
	 */
	public abstract List expandToPerson(List orgList) throws Exception;

	/**
	 * 将机构/部门/岗位/个人/群组展开成个人ID
	 * 
	 * @param orgList
	 *            组织架构列表
	 * @return 展开的结果，为个人ID列表
	 * @throws Exception
	 */
	public abstract List expandToPersonIds(List orgList) throws Exception;

	/**
	 * 将机构/部门/岗位/个人/群组展开成个人ID以及岗位ID(不对岗位进行展开)
	 * 
	 * @param orgList
	 *            组织架构列表
	 * @return 展开的结果，为个人ID以及岗位ID的混合列表
	 * @throws Exception
	 */
	public abstract List expandToPostPersonIds(List orgList) throws Exception;

	/**
	 * 将机构/部门/岗位/个人/群组展开成个人以及岗位(不对岗位进行展开)
	 * 
	 * @param orgList
	 *            组织架构列表
	 * @return 展开的结果，为个人以及岗位的混合列表
	 * @throws Exception
	 */
	public abstract List expandToPostPerson(List orgList) throws Exception;

	/**
	 * 从个人列表中获取通讯方式
	 * 
	 * @param personList
	 *            个人列表
	 * @return 通讯方式
	 * @throws Exception
	 */
	public abstract PersonCommunicationInfo getPersonCommunicationInfo(
			List personList) throws Exception;

	/**
	 * 查找某个机构/部门下的所有子，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @return 查询结果，为域模型列表
	 * @throws Exception
	 */
	public abstract List findAllChildren(SysOrgElement element, int rtnType)
			throws Exception;

	/**
	 * 查找（附加特定查询条件）某个机构/部门下的所有子，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @param wherBlock
	 *            特定查询条件
	 * @return 查询结果，为域模型列表
	 * @throws Exception
	 */
	public abstract List findAllChildren(SysOrgElement element, int rtnType,
			String whereBlock)
			throws Exception;

	/**
	 * 查找某个机构/部门下的所有子的某些属性，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @param rtnItem
	 *            期望返回的值，如sysOrgElement.fdId,sysOrgElement.fdName
	 * @return 查询结果，为值列表
	 * @throws Exception
	 */
	public abstract List findAllChildrenItem(SysOrgElement element,
			int rtnType, String rtnItem) throws Exception;

	/**
	 * 根据关键字查找组织架构元素，用于单点登录SSO
	 * 
	 * @param keyword
	 *            关键字
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgElement findByKeyword(String keyword)
			throws Exception;

	/**
	 * 根据关键字查找组织架构元素，用于组织机构同步OMS
	 * 
	 * @param importInfo
	 *            关键字
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgElement findByImportInfo(String importInfo)
			throws Exception;

	/**
	 * 根据关键字查找组织架构元素，用于组织机构同步OMS
	 * 
	 * @param serviceName
	 *            OMS同步实现类的Spring中的Bean名字
	 * @param keyword
	 *            实现类中的关联唯一值
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgElement findByImportInfo(String serviceName,
			String keyword) throws Exception;

	/**
	 * 根据关键字查找组织架构元素，用于组织机构同步OMS
	 * 
	 * @param serviceName
	 *            OMS同步实现类的Spring中的Bean名字
	 * @param keyword
	 *            实现类中的关联唯一值
	 * @param keyword
	 *            实现类中的关联唯一值
	 * @param type
	 *            组织机构类型
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgElement findByImportInfo(String serviceName,
			String keyword, String type) throws Exception;

	/**
	 * 根据登录名查找个人
	 * 
	 * @param loginName
	 *            登录名
	 * @return 个人，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgPerson findByLoginName(String loginName)
			throws Exception;

	/**
	 * 根据编号查找组织架构元素
	 * 
	 * @param fno
	 *            编号
	 * @param rtnType
	 *            查找类型，为SysOrgConstant的常量
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract SysOrgElement findByNo(String fno, int rtnType)
			throws Exception;
	
	/**
	 * 根据名称查找组织架构元素
	 * 
	 * @param fdName
	 *            名称
	 * @param rtnType
	 *            查找类型，为SysOrgConstant的常量
	 * @return 组织架构对象列表
	 * @throws Exception
	 */
	public abstract List findByName(String fdName, int rtnType)
			throws Exception;

	/**
	 * 根据主键查找记录。
	 * 
	 * @param id
	 * @return model对象
	 * @throws Exception
	 */
	public abstract SysOrgElement findByPrimaryKey(String id) throws Exception;

	/**
	 * 根据主键查找记录。
	 * 
	 * @param id
	 * @param classObj
	 *            指定返回类型
	 * @return
	 * @throws Exception
	 */
	public abstract SysOrgElement findByPrimaryKey(String id, Class classObj)
			throws Exception;

	public SysOrgElement findByPrimaryKey(String id, Class classObj,
			boolean noLazy) throws Exception;

	/**
	 * 根据主键数组查找记录列表。
	 * 
	 * @param ids
	 *            ID数组
	 * @return 组织架构对象列表
	 * @throws Exception
	 */
	public abstract List findByPrimaryKeys(String[] ids) throws Exception;

	/**
	 * 查找某个机构/部门下的直级子
	 * 
	 * @param deptId
	 *            机构/部门ID
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @return 查询结果，为域模型列表
	 * @throws Exception
	 */
	public abstract List findDirectChildren(String deptId, int rtnType)
			throws Exception;

	/**
	 * 查找某个机构/部门下的直级子的某些属性，不对岗位进行展开
	 * 
	 * @param deptId
	 *            机构/部门ID
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @param rtnItem
	 *            期望返回的值，如sysOrgElement.fdId,sysOrgElement.fdName
	 * @return 查询结果，为值列表
	 * @throws Exception
	 */
	public abstract List findDirectChildrenItem(String deptId, int rtnType,
			String rtnItem) throws Exception;

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

	/**
	 * 查找某个机构/部门下的所有子的个数，不对岗位进行展开
	 * 
	 * @param element
	 *            机构/部门
	 * @param rtnType
	 *            返回类型，为SysOrgConstant的常量组合
	 * @return 子的个数
	 * @throws Exception
	 */
	public abstract int getAllChildrenCount(SysOrgElement element, int rtnType)
			throws Exception;

	int getAllCount(int rtnType) throws Exception;

	/**
	 * 带附加查询条件的查询组织架构总数
	 * @param rtnType
	 * @param whereBlock
	 * @return
	 * @throws Exception
	 */
	int getAllCount(int rtnType ,String whereBlock) throws Exception;

	/**
	 * 获取与指定个人相关的组织架构信息
	 * 
	 * @param person
	 * @return 组织架构相关的授权信息
	 * @throws Exception
	 */
	public abstract UserAuthInfo getOrgsUserAuthInfo(SysOrgElement person)
			throws Exception;
	
	/**
	 * 获取与指定个人相关的组织架构信息
	 * 
	 * @param person
	 * @param isContainsPost 是否包含兼岗
	 * @return 组织架构相关的授权信息
	 * @throws Exception
	 */
	public abstract UserAuthInfo getOrgsUserAuthInfo(SysOrgElement element,
			boolean isContainsPost) throws Exception;

	/**
	 * 获取与指定岗位相关的组织架构ID列表
	 * 
	 * @param person
	 * @return 组织架构ID列表
	 * @throws Exception
	 */
	public abstract List getPostAuthOrgIds(SysOrgElement post)
			throws Exception;

	/**
	 * 解释组织架构中的角色信息<br>
	 * 例子：originElement输入“直线领导”的角色，baseElement输入“张三”对应的个人，返回张三的直线领导
	 * 
	 * @param originElement
	 *            需要解释的原始信息，可以为任何类型的组织架构元素
	 * @param baseElement
	 *            基于解释的用户信息，可为个人、岗位、部门、机构
	 * @return 解释的结果
	 * @throws Exception
	 */
	public List parseSysOrgRole(SysOrgElement originElement,
			SysOrgElement baseElement) throws Exception;

	/**
	 * 根据ID列表查找名称
	 * 
	 * @param ids
	 * @return 返回一个列表，每个元素是一个数组，数组的第一个值为name，第二个值为id，
	 * @throws Exception
	 */
	public List<Object[]> getNamesByIds(List<String> ids) throws Exception;

	/**
	 * 查询id对应的层级id
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<String> getHierarchyIdsByIds(List<String> ids) throws Exception;

	/**
	 * 批量解释组织架构中的角色信息
	 * 
	 * @param originElementList
	 *            需要解释的原始信息，可以为任何类型的组织架构元素
	 * @param baseElement
	 *            基于解释的用户信息，可为个人、岗位、部门、机构
	 * @return 解释的结果
	 * @throws Exception
	 */
	public List parseSysOrgRole(List originElementList,
			SysOrgElement baseElement) throws Exception;

	/**
	 * 根据角色线名称+角色线关系名称获取角色关系对象
	 * 
	 * @param confName
	 *            角色线名称，为空则是通用岗位
	 * @param roleName
	 *            角色关系名称
	 * @return
	 * @throws Exception
	 */
	public SysOrgRole getRoleByName(String confName, String roleName)
			throws Exception;

	/**
	 * 根据LDAP的DN值查找人员组织
	 * 
	 * @param dn
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findByLdapDN(String dn) throws Exception;

	/**
	 * 校验用户是否指定机构/部门的管理员
	 * 
	 * @param sysOrgPerson
	 * @param sysOrgElement
	 * @return
	 * @throws Exception
	 */
	public boolean checkPersonIsOrgAdmin(SysOrgPerson sysOrgPerson,
			SysOrgElement sysOrgElement) throws Exception;

	/**
	 * 校验用户是否指定机构/部门的管理员
	 * 
	 * @param sysOrgPerson
	 * @param sysOrgElement
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public boolean checkPersonIsOrgAdmin(SysOrgPerson sysOrgPerson, SysOrgElement sysOrgElement, String method)
			throws Exception;

	/**
	 * huangwq 2012-10-23 根据importInfo和组织类型查数据
	 * 
	 * @param importInfo
	 * @param orgType
	 * @return
	 * @throws Exception
	 */
	public SysOrgElement findByImportInfoAndOrgtype(String importInfo,
			int orgType) throws Exception;

	/**
	 * 获取用户职级大小
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public int getStaffingLevelValue(SysOrgPerson person) throws Exception;
	
	public SysOrganizationStaffingLevel getStaffingLevel(SysOrgPerson person) throws Exception;
	
	public SysOrgPerson findByLoginNameOrMobileNo(String name) throws Exception;

	/**
	 * 根据登录名查找个人
	 * 
	 * @param loginName
	 *            登录名
	 * @return 个人，找不到返回null
	 * @throws Exception
	 */
	public abstract List<SysOrgElement> findByLoginName(String[] loginName)
			throws Exception;

	/**
	 * 根据关键字查找组织架构元素，用于单点登录SSO
	 * 
	 * @param keyword
	 *            关键字
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract List<SysOrgElement> findByKeyword(String[] keyword)
			throws Exception;

	/**
	 * 根据编号查找组织架构元素
	 * 
	 * @param fno
	 *            编号
	 * @param rtnType
	 *            查找类型，为SysOrgConstant的常量
	 * @return 组织架构元素，找不到返回null
	 * @throws Exception
	 */
	public abstract List<SysOrgElement> findByNo(String[] fno, int rtnType)
			throws Exception;

	/**
	 * 根据LDAP的DN值查找人员组织
	 * 
	 * @param dn
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> findByLdapDN(String[] dn) throws Exception;
	
	/**
	 * 根据elemId获取管理员ids
	 * @param elemId
	 * @return
	 */
	public List<String> findAdminIdsByElemId(String elemId);
	
	/**
	 * 判断当前人是否是创建人
	 * @param elem
	 * @param currElem
	 * @return
	 */
	public Boolean isElemCreator(SysOrgElement elem, SysOrgElement currElem) throws Exception;
	
	/**
	 * 判断当前人是否为可使用人员并且是否是自己创建
	 * @param elem
	 * @param currElem
	 * @param method
	 * @return
	 */
	public Boolean isAvailablePerson(SysOrgElement elem, SysOrgElement currElem, String method) throws Exception ;
	
	public List<String> findByCreator(SysOrgElement elem);
	
	/**
	 * 获取当前用户是哪些组织类型或者组织的管理员
	 * @param elem
	 * @return
	 */
	public List<String> findOrgAdminByElem(SysOrgElement elem);

	/**
	 * 获取注册用户数量（由于计算注册用户数量逻辑有变更，需要单独编写）
	 * @param isExternal
	 * @param exclude
	 * @return
	 * @throws Exception
	 */
	public int getCountByRegistered(Boolean isExternal, Boolean exclude) throws Exception;
}
