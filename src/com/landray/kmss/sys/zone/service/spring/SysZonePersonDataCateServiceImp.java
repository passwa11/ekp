package com.landray.kmss.sys.zone.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.zone.forms.SysZonePerDataTemplForm;
import com.landray.kmss.sys.zone.forms.SysZonePersonDataCateForm;
import com.landray.kmss.sys.zone.model.SysZonePerDataTempl;
import com.landray.kmss.sys.zone.model.SysZonePersonData;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;
import com.landray.kmss.sys.zone.service.ISysZonePersonDataCateService;
import com.landray.kmss.util.ArrayUtil;

/**
 * 个人资料目录设置业务接口实现
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataCateServiceImp extends BaseServiceImp implements
		ISysZonePersonDataCateService {
	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		deletePersonData(form);
		super.update(form, requestContext);
	}

	/**
	 * hql批量删除黄页员工的个人资料目录
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	private void deletePersonData(IExtendForm form) throws Exception {
		List<String> delTemplIds = getDelTemplIds(form);
		if (ArrayUtil.isEmpty(delTemplIds)) {
			return;
		}
		String fdId = form.getFdId();
		StringBuffer hql = new StringBuffer("delete from ");
		hql.append(SysZonePersonData.class.getName());
		hql.append(" t_zp_data where t_zp_data.fdName in(");
		hql.append(getSubQueryHql(fdId));
		hql.append(") and t_zp_data.fdDataCate.fdId = :data_cateId");
		Query query = this.getBaseDao().getHibernateSession().createQuery(
				hql.toString());
		query.setParameterList("temp_Ids", delTemplIds);
		query.setParameter("temp_cateId", fdId);
		query.setParameter("data_cateId", fdId);
		query.executeUpdate();
	}

	/**
	 * 子查询hql语句拼凑
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	private String getSubQueryHql(String fdId) throws Exception {
		StringBuffer hql = new StringBuffer(
				"select distinct t_z_dataTempl.fdName from ");
		hql.append(SysZonePerDataTempl.class.getName());
		hql.append(" t_z_dataTempl where ");
		hql.append("t_z_dataTempl.fdId in(:temp_Ids)");
		hql.append(" and t_z_dataTempl.fdPersonDataCate.fdId = :temp_cateId");
		return hql.toString();
	}

	/**
	 * 获取提交的表单中被删除的目录的ids
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	private List<String> getDelTemplIds(IExtendForm form) throws Exception {
		List<String> delTemplIds = new ArrayList<String>();
		SysZonePersonDataCateForm dataCateForm = (SysZonePersonDataCateForm) form;
		@SuppressWarnings("unchecked")
		List<SysZonePerDataTemplForm> templForms = dataCateForm.getFdDataCateTemplForms();
		SysZonePersonDataCate dataCate = (SysZonePersonDataCate) this.findByPrimaryKey(
				form.getFdId(), null, true);
		List<SysZonePerDataTempl> templs = dataCate.getFdDataCateTempls();
		if (null == templs) {
			return null;
		}
		for (SysZonePerDataTempl templ : templs) {
			if (!this.isContains(templForms, templ)) {
				delTemplIds.add(templ.getFdId());
			}
		}
		return delTemplIds;
	}

	private boolean isContains(List<? extends IExtendForm> forms,
			IBaseModel model) throws Exception {
		for (IExtendForm extendForm : forms) {
			if (extendForm.getFdId().equals(model.getFdId())) {
				return true;
			}
		}
		return false;
	}
}
