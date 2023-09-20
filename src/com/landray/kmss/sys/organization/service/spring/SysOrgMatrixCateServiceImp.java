package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixCateService;
import com.landray.kmss.util.StringUtil;

/**
 * 矩阵分类
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixCateServiceImp extends BaseServiceImp
		implements ISysOrgMatrixCateService {

	@Override
	public List<SysOrgMatrixCate> findByParent(String parentId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNotNull(parentId)) {
			hqlInfo.setWhereBlock("hbmParent.fdId = :parentId");
			hqlInfo.setParameter("parentId", parentId);
		} else {
			hqlInfo.setWhereBlock("hbmParent.fdId is null");
		}

		return findList(hqlInfo);
	}

}
