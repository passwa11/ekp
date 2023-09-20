package com.landray.kmss.sys.simplecategory.service;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 返回类别的可维护人员和可使用人员，可供给其它业务类别的service继承
 * 
 * @author wubing 2009－07－22
 */
public class SysSimpleCategoryAuthServiceDecorator {

	/**
	 * 获取域模型对应的服务类
	 * 
	 * @return
	 */
	private IBaseService baseService;

	protected IBaseService getBaseService() {
		return baseService;
	}

	public SysSimpleCategoryAuthServiceDecorator(IBaseService baseService) {
		this.baseService = baseService;
	}

	/**
	 * 获取对应的域模型
	 */
	public String getModelName() {
		return getBaseService().getModelName();
	}

	private String adminRoleName;

	public void setAdminRoleName(String roleName) {
		this.adminRoleName = roleName;
	}

	public String getAdminRoleName() {
		return adminRoleName;
	}

	public boolean isAdmin() throws Exception {
		if (UserUtil.getKMSSUser().isAdmin()
				|| (StringUtil.isNotNull(adminRoleName) && UserUtil
						.checkRole(adminRoleName))) {
			return true;
		}
		return false;
	}

	public boolean isAdmin(IBaseModel model) throws Exception {
		if (UserUtil.getKMSSUser().isAdmin()
				|| (StringUtil.isNotNull(adminRoleName) && UserUtil
						.checkAreaRole(adminRoleName, model,
								SysAuthConstant.AreaIsolation.CHILD))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param id
	 *            类别文档的ID
	 * 
	 * @return 对应类别的所有的可维护者
	 * @throws Exception
	 */
	public List getAllEditors(String id) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) getBaseService()
				.findByPrimaryKey(id);
		List allEditors = new ArrayList();
		allEditors.addAll(categoryModel.getAuthAllEditors());
		while (categoryModel.getFdIsinheritMaintainer().booleanValue()
				&& categoryModel.getFdParent() != null) {
			categoryModel = (ISysSimpleCategoryModel) categoryModel
					.getFdParent();
			ArrayUtil.concatTwoList(categoryModel.getAuthEditors(), allEditors);
		}
		return allEditors;
	}

	/**
	 * 
	 * @param id
	 *            类别文档的ID
	 * @return 返回类别的所有可使用者
	 * @throws Exception
	 */
	public List getAllReaders(String id) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) getBaseService()
				.findByPrimaryKey(id);

		List allReaders = new ArrayList();
		allReaders.addAll(categoryModel.getAuthAllReaders());
		boolean fdIsinheritUser = categoryModel.getFdIsinheritUser()
				.booleanValue();
		boolean fdIsinheritMaintainer = categoryModel
				.getFdIsinheritMaintainer().booleanValue();
		while ((fdIsinheritUser || fdIsinheritMaintainer)
				&& categoryModel.getFdParent() != null) {
			categoryModel = (ISysSimpleCategoryModel) categoryModel
					.getFdParent();
			if (fdIsinheritUser) {
				ArrayUtil.concatTwoList(categoryModel.getAuthReaders(),
						allReaders);
			}
			if (fdIsinheritMaintainer) {
				ArrayUtil.concatTwoList(categoryModel.getAuthEditors(),
						allReaders);
			}
			fdIsinheritUser = categoryModel.getFdIsinheritUser().booleanValue();
			fdIsinheritMaintainer = categoryModel.getFdIsinheritMaintainer()
					.booleanValue();
		}
		return allReaders;
	}

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
	public boolean isEditors(String id, List ids) throws Exception {
		List allEditors = getAllEditors(id);
		for (int i = 0; i < allEditors.size(); i++) {
			if (ids.indexOf(((SysOrgElement) allEditors.get(i)).getFdId()) > 0) {
                return true;
            }
		}

		return false;
	}

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
	public boolean isReaders(String id, List ids) throws Exception {
		List allReaders = getAllReaders(id);
		for (int i = 0; i < allReaders.size(); i++) {
			if (ids.indexOf(((SysOrgElement) allReaders.get(i)).getFdId()) > 0) {
                return true;
            }
		}

		return false;
	}

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
	public boolean isEditors(String id, String userId) throws Exception {
		List ids = new ArrayList();
		ids.add(userId);
		return isEditors(id, ids);
	}

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
	public boolean isReaders(String id, String userId) throws Exception {
		List ids = new ArrayList();
		ids.add(userId);
		return isReaders(id, ids);
	}

	/**
	 * 
	 */
	public boolean isEditors(String id) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) getBaseService()
				.findByPrimaryKey(id);
		boolean editor = UserUtil.checkUserModels(categoryModel
				.getAuthAllEditors());
		if (editor) {
            return true;
        }
		while (!editor
				&& categoryModel.getFdIsinheritMaintainer().booleanValue()
				&& categoryModel.getFdParent() != null) {
			categoryModel = (ISysSimpleCategoryModel) categoryModel
					.getFdParent();
			editor = UserUtil.checkUserModels(categoryModel.getAuthEditors());
		}
		return editor;
	}

	/**
	 * 
	 */
	public boolean isReaders(String id) throws Exception {
		ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) getBaseService()
				.findByPrimaryKey(id);
		boolean reader = false;
		if (categoryModel.getAuthReaderFlag().booleanValue()) {
			reader = true;
		} else {
			reader = UserUtil
					.checkUserModels(categoryModel.getAuthAllReaders());
		}
		if (reader) {
            return true;
        }

		boolean fdIsinheritUser = categoryModel.getFdIsinheritUser()
				.booleanValue();
		boolean fdIsinheritMaintainer = categoryModel
				.getFdIsinheritMaintainer().booleanValue();
		while ((fdIsinheritUser || fdIsinheritMaintainer)
				&& categoryModel.getFdParent() != null) {
			categoryModel = (ISysSimpleCategoryModel) categoryModel
					.getFdParent();
			if (fdIsinheritUser) {
				reader = UserUtil.checkUserModels(categoryModel
						.getAuthReaders());
				if (reader) {
                    break;
                }
			}
			if (fdIsinheritMaintainer) {
				reader = UserUtil.checkUserModels(categoryModel
						.getAuthEditors());
				if (reader) {
                    break;
                }
			}
			fdIsinheritUser = categoryModel.getFdIsinheritUser().booleanValue();
			fdIsinheritMaintainer = categoryModel.getFdIsinheritMaintainer()
					.booleanValue();
		}
		return reader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.landray.kmss.sys.category.service.ISysCategoryBaseAuthService#
	 * getAllManageCateIds(java.lang.String, boolean)
	 */
	public List getAllManageCateIds(String modelName, boolean isIncludeSub)
			throws Exception {
		List docs = getAllManageCate(modelName, isIncludeSub);
		List retVal = new ArrayList();
		for (int i = 0; i < docs.size(); i++) {
			ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) docs
					.get(i);
			retVal.add(categoryModel.getFdId());
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.landray.kmss.sys.category.service.ISysCategoryBaseAuthService#
	 * getAllManageCate(java.lang.String, boolean)
	 */
	public List getAllManageCate(String modelName, boolean isIncludeSub)
			throws Exception {
		List docs = new ArrayList();
		String whereBlock = "";
		if (!isAdmin()) {
			String authOrgs = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthOrgIds().toString();
			authOrgs = " in (" + authOrgs.substring(1, authOrgs.length() - 1)
					+ ")";
			whereBlock = "authAllEditors.fdId " + authOrgs;
		} else {
            whereBlock = null;
        }
		docs = getBaseService().findList(whereBlock, null);
		if (isIncludeSub) {
			if (docs.size() > 0) {
				String subWhereBlock = "";
				for (int i = 0; i < docs.size(); i++) {
					ISysSimpleCategoryModel categoryModel = (ISysSimpleCategoryModel) docs
							.get(i);
//					subWhereBlock += (subWhereBlock.equals("") ? "" : " or ")
//							+ "substring(fdHierarchyId,1,"
//							+ categoryModel.getFdHierarchyId().length() + ")='"
//							+ categoryModel.getFdHierarchyId() + "'";
					
					subWhereBlock += ("".equals(subWhereBlock) ? "" : " or ")
							+ " fdHierarchyId like '"
							+ categoryModel.getFdHierarchyId() + "%'";
				}
				List subdocs = getBaseService().findList(subWhereBlock, null);
				ArrayUtil.concatTwoList(subdocs, docs);
			}
		}
		return docs;
	}
}
