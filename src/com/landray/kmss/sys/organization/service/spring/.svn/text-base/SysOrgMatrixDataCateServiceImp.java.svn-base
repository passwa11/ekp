package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixDataCateForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrixDataCate;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixDataCateService;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 矩阵数据分组
 * 
 * @author panyh
 * @date 2020-05-11
 *
 */
public class SysOrgMatrixDataCateServiceImp extends BaseServiceImp implements ISysOrgMatrixDataCateService {

	/**
	 * 根据矩阵id获取对应的数据类别
	 * 
	 * @param matrixId
	 * @return
	 */
	@Override
	public AutoArrayList getDataCates(String matrixId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hbmMatrix.fdId = :matrixId");
		hqlInfo.setParameter("matrixId", matrixId);
		// 校验矩阵可编辑者
		String url = "/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=edit&fdId=" + matrixId;
		if (!UserUtil.checkAuthentication(url, "GET")) {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "hbmElement.fdId in (:orgIds)"));
			hqlInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		}
		List<SysOrgMatrixDataCate> list = findList(hqlInfo);
		AutoArrayList rtnList = new AutoArrayList(SysOrgMatrixDataCateForm.class);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysOrgMatrixDataCate cate : list) {
				rtnList.add(toForm(cate));
			}
		}
		return rtnList;
	}

	private SysOrgMatrixDataCateForm toForm(SysOrgMatrixDataCate dataCate) throws Exception {
		SysOrgMatrixDataCateForm form = new SysOrgMatrixDataCateForm();
		form = (SysOrgMatrixDataCateForm) convertModelToForm(form, dataCate, new RequestContext(Plugin.currentRequest()));
		return form;
	}

	@Override
	public void deleteByNotMatrix() throws Exception {
		String hql = "delete from SysOrgMatrixDataCate where hbmMatrix.fdId is null";
		getBaseDao().getHibernateSession().createQuery(hql).executeUpdate();
	}
}
