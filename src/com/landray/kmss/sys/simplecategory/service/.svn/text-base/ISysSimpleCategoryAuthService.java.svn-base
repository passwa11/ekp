package com.landray.kmss.sys.simplecategory.service;

import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;

public interface ISysSimpleCategoryAuthService {
	/**
	 * 获取域模型对应的服务类
	 * 
	 * @return
	 */
	public abstract String getModelName();

	public abstract void setBaseService(IBaseService baseService);

	/**
	 * 设置分类的系统管理员的角色名
	 * 
	 * @param roleName
	 */
	public abstract void setAdminRoleName(String roleName);

	public abstract String getAdminRoleName();

	/**
	 * 
	 * @param id
	 *            类别文档的ID
	 * 
	 * @return 对应类别的所有的可维护者
	 * @throws Exception
	 */
	public abstract List getAllEditors(String id) throws Exception;

	/**
	 * 
	 * @param id
	 *            类别文档的ID
	 * @return 返回类别的所有可使用者
	 * @throws Exception
	 */
	public abstract List getAllReaders(String id) throws Exception;

	/**
	 * 判断一群用户是否属于指定类别的可维护者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param ids
	 *            用户群的ID列表，每个元素都为long类型的ID
	 * @return 是则返回true，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isEditors(String id, List ids) throws Exception;

	/**
	 * 判断一群用户是否属于指定类别的可使用者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param ids
	 *            用户群的ID列表，每个元素都为long类型的ID
	 * @return 是则返回True，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isReaders(String id, List ids) throws Exception;

	/**
	 * 判断一个用户是否属于指定类别的可维护者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param Userid
	 *            用户的ID
	 * @return 是则返回true，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isEditors(String id, String userId)
			throws Exception;

	/**
	 * 判断一个用户是否属于指定类别的可使用者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param Userid
	 *            用户的ID
	 * @return 是则返回true，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isReaders(String id, String userId)
			throws Exception;

	/**
	 * 判断当前用户是否属于指定类别的可维护者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param Userid
	 *            用户的ID
	 * @return 是则返回true，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isEditors(String id) throws Exception;

	/**
	 * 判断当前用户是否属于指定类别的可使用者
	 * 
	 * @param id
	 *            类别文档的ID
	 * @param Userid
	 *            用户的ID
	 * @return 是则返回true，否则返回false
	 * @throws Exception
	 */
	public abstract boolean isReaders(String id) throws Exception;

	/**
	 * 当前用户是否具有类别的系统管理员角色
	 * 
	 * @return
	 */
	public abstract boolean isAdmin() throws Exception;

	/**
	 * 当前用户是否具有类别的系统管理员角色
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public boolean isAdmin(IBaseModel model) throws Exception;
}
