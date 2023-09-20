package com.landray.kmss.sys.zone.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.UserUtil;

public class SysZoneAddressCateServiceImp extends BaseServiceImp
		implements ISysZoneAddressCateService, BaseTreeConstant {

	private ISysCategoryMainService sysCategoryMainService;

	public void setSysCategoryMainService(ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		if (!checkUniqueName(modelObj)) {
            throw new KmssRuntimeException(new KmssMessage("sys-category:error.nouniquename"));
        }

		// 创建时间
		IBaseCreateInfoModel createInfoModel = (IBaseCreateInfoModel) modelObj;
		createInfoModel.setDocCreateTime(new Date());
		createInfoModel.setDocCreator(UserUtil.getUser());
		// 最后修改时间
		SysCategoryBaseModel baseModel = (SysCategoryBaseModel) modelObj;
		baseModel.setDocAlterTime(new Date());
		baseModel.setDocAlteror(UserUtil.getUser());

		// 层级ID
		IBaseTreeModel treeModel = (IBaseTreeModel) modelObj;
		treeModel.setFdHierarchyId(getTreeHierarchyId(treeModel));

		// 重新计算阅读权限
		recalculateReaderField(modelObj);
		// 重新计算可编辑者
		recalculateEditorField(modelObj);

		sysCategoryMainService.getBaseDao().getHibernateTemplate().save(modelObj);
		return modelObj.getFdId();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		if (!checkUniqueName(modelObj)) {
            throw new KmssRuntimeException(new KmssMessage("sys-category:error.nouniquename"));
        }

		// 最后修改时间
		SysCategoryBaseModel baseModel = (SysCategoryBaseModel) modelObj;
		baseModel.setDocAlterTime(new Date());
		baseModel.setDocAlteror(UserUtil.getUser());

		// 重新计算阅读权限
		recalculateReaderField(modelObj);
		// 重新计算可编辑者
		recalculateEditorField(modelObj);

		sysCategoryMainService.getBaseDao().getHibernateTemplate().saveOrUpdate(modelObj);
	}

	protected void recalculateEditorField(IBaseModel modelObj) {
		SysCategoryMain main = (SysCategoryMain) modelObj;
		List authAllEditors = main.getAuthAllEditors();

		if (authAllEditors == null) {
            authAllEditors = new ArrayList();
        } else {
            authAllEditors.clear();
        }

		// 创建者可编辑
		authAllEditors.add(main.getDocCreator());

		List tmpList = main.getAuthOtherEditors();
		if (tmpList != null && !tmpList.isEmpty()) {
			// 增加“其它可编辑者”
			ArrayUtil.concatTwoList(tmpList, authAllEditors);
		}

		tmpList = main.getAuthEditors();
		if (tmpList != null && !tmpList.isEmpty()) {
			// 增加“可编辑者”
			ArrayUtil.concatTwoList(tmpList, authAllEditors);
		}

		main.setAuthAllEditors(authAllEditors);
	}

	// 这里的model是直接使用SysCategoryMain，而SysCategoryMain使用的权限机制中阅读权限是属于“为空时所有人不可阅读”
	// 在通讯录中的阅读权限应该要“为空时所有人可阅读”，所以这里重写权限机制的逻辑
	private void recalculateReaderField(IBaseModel modelObj) {
		SysCategoryMain main = (SysCategoryMain) modelObj;

		List authAllReaders = main.getAuthAllReaders();

		// 重新计算读者域
		if (authAllReaders == null) {
            authAllReaders = new ArrayList();
        } else {
            authAllReaders.clear();
        }
		List tmpList = main.getAuthOtherReaders();
		if (tmpList != null && !tmpList.isEmpty()) {
			// 增加“其它可阅读者”
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		tmpList = main.getAuthReaders();
		if (tmpList != null && !tmpList.isEmpty()) {
			// 增加“创建者”
			authAllReaders.add(main.getDocCreator());
			// 增加“可编辑者”
			ArrayUtil.concatTwoList(main.getAuthEditors(), authAllReaders);
			// 增加“所有可阅读者”
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		} else {
			// 可阅读者为空时，所有人可阅读
			authAllReaders.add(UserUtil.getEveryoneUser());
		}
		ArrayUtil.concatTwoList(main.getAuthAllEditors(), authAllReaders);

		main.setAuthAllReaders(authAllReaders);
	}

	private String getTreeHierarchyId(IBaseTreeModel treeModel) {
		if (treeModel.getFdParent() != null) {
            return treeModel.getFdParent().getFdHierarchyId()
                    + treeModel.getFdId() + HIERARCHY_ID_SPLIT;
        } else {
            return HIERARCHY_ID_SPLIT + treeModel.getFdId()
                    + HIERARCHY_ID_SPLIT;
        }
	}

	private boolean checkUniqueName(IBaseModel modelObj) throws Exception {
		SysCategoryMain categoryMain = (SysCategoryMain) modelObj;
		IBaseDao daoImp = sysCategoryMainService.getBaseDao();
		String whereBlock = "sysCategoryMain.fdName=:catename and sysCategoryMain.fdModelName=:modelName and sysCategoryMain.fdId<>'"
				+ categoryMain.getFdId() + "'";
		if (categoryMain.getFdParent() == null) {
            whereBlock += " and sysCategoryMain.hbmParent=null";
        } else {
            whereBlock += " and sysCategoryMain.hbmParent.fdId='"
                    + categoryMain.getFdParent().getFdId() + "'";
        }

		HQLInfo info = new HQLInfo();
		info.setWhereBlock(whereBlock);
		info.setParameter("catename", categoryMain.getFdName());
		info.setParameter("modelName", categoryMain.getFdModelName());
		List findList = daoImp.findList(info);
		if (!findList.isEmpty()) {
            return false;
        } else {
            return true;
        }
	}

}
