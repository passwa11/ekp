package com.landray.kmss.hr.organization.service.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.model.HrOrgFileAuthor;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
/**
 * 档案授权业务接口实现
 * 
 * @author 
 * @version 1.0 2017-11-10
 */
public class HrOrgFileAuthorServiceImp extends ExtendDataServiceImp implements IHrOrgFileAuthorService {

	protected IHrStaffFileAuthorService hrStaffFileAuthorService;

	protected IBaseService getHrStaffFileAuthorServiceImp() {
		if (hrStaffFileAuthorService == null) {
			hrStaffFileAuthorService = (IHrStaffFileAuthorService) SpringBeanUtil.getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementServiceImp() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}
	

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		//获取历史的model
		HrOrgFileAuthor fileAuthor = (HrOrgFileAuthor) this.findByPrimaryKey(form.getFdId(), null, true);
		HrOrgFileAuthor fileAuthorOld = null;
		if (fileAuthor != null) {
			fileAuthorOld = cloneHrOrgFileAuthor(fileAuthor);
		}
		HrOrgFileAuthor fileAuthorNew = (HrOrgFileAuthor) super.convertFormToModel(form, model, requestContext);

		syncHrStaffAuthor(fileAuthorOld, fileAuthorNew);
		return super.convertFormToModel(form, model, requestContext);

	}

	private HrOrgFileAuthor cloneHrOrgFileAuthor(HrOrgFileAuthor fileAuthor) {
		HrOrgFileAuthor fileAuthorOld = new HrOrgFileAuthor();
		fileAuthorOld.setFdId(fileAuthor.getFdId());
		fileAuthorOld.setFdName(fileAuthor.getFdName());
		fileAuthorOld.setAuthorDetail(new ArrayList(fileAuthor.getAuthorDetail()));
		return fileAuthorOld;
	}

	/**
	 * 同步授权数据到人事档案
	 * <p>TODO</p>
	 * @author sunj
	 * @throws Exception 
	 */
	private void syncHrStaffAuthor(HrOrgFileAuthor fileAuthorOld, HrOrgFileAuthor fileAuthorNew) throws Exception {
		IBaseModel model = getSysOrgElementServiceImp().findByPrimaryKey(fileAuthorNew.getFdName(), null, true);
		if (null != model) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdName = :fdName");
			hqlInfo.setParameter("fdName", fileAuthorNew.getFdName());
			List<HrStaffFileAuthor> list = getHrStaffFileAuthorServiceImp().findList(hqlInfo);
			if (!ArrayUtil.isEmpty(list)) {
				HrStaffFileAuthor modelObj = list.get(0);
				Set<SysOrgElement> authors = new HashSet<SysOrgElement>();
				authors.addAll(modelObj.getAuthorDetail());
				//authors.addAll(orgFileAuthor.getAuthorDetail());
				if (null != fileAuthorOld && !ArrayUtil.isEmpty(fileAuthorOld.getAuthorDetail())) {
					authors.removeAll(fileAuthorOld.getAuthorDetail());
				}
				authors.addAll(fileAuthorNew.getAuthorDetail());
				modelObj.setAuthorDetail(new ArrayList<SysOrgElement>(authors));
				getHrStaffFileAuthorServiceImp().update(modelObj);
			} else {
				HrStaffFileAuthor modelObj = new HrStaffFileAuthor();
				modelObj.setFdId(fileAuthorNew.getFdId());
				modelObj.setFdName(fileAuthorNew.getFdName());
				modelObj.setAuthorDetail(fileAuthorNew.getAuthorDetail());
				getHrStaffFileAuthorServiceImp().add(modelObj);
			}
		}
	}

}
