package com.landray.kmss.hr.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationDeptDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationElementDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationOrgDao;
import com.landray.kmss.hr.organization.dao.IHrOrganizationPostDao;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrgCoreService;
import com.landray.kmss.hr.organization.util.HrOrgHQLUtil;
import com.landray.kmss.hr.staff.dao.IHrStaffPersonInfoDao;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.util.ModelUtil;



@SuppressWarnings("unchecked")
public class HrOrgCoreServiceImp implements IHrOrgCoreService {

	private IHrOrganizationDeptDao deptDao;

	private IHrOrganizationElementDao elementDao;

	private IHrOrganizationOrgDao orgDao;

	private IHrStaffPersonInfoDao personDao;

	private IHrOrganizationPostDao postDao;

	private IBaseDao getOptimalDao(int rtnType) {
		switch (rtnType & HR_TYPE_ALL) {
		case HR_TYPE_ORG:
			return orgDao;
		case HR_TYPE_DEPT:
			return deptDao;
		case HR_TYPE_POST:
			return postDao;
		case HR_TYPE_PERSON:
			return personDao;
		default:
			return elementDao;
		}
	}

	@Override
    public List findAllChildren(HrOrganizationElement element, int rtnType)
			throws Exception {
		IBaseDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		return dao.findList(SysOrgHQLUtil.buildWhereBlock(rtnType,
				HrOrgHQLUtil.buildAllChildrenWhereBlock(element, null,
						tableName),
				tableName), null);
	}

	@Override
    public List findAllChildren(HrOrganizationElement element, int rtnType,
                                String whereBlock)
			throws Exception {
		IBaseDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		return dao.findList(SysOrgHQLUtil.buildWhereBlock(rtnType,
				HrOrgHQLUtil.buildAllChildrenWhereBlock(element, whereBlock,
						tableName),
				tableName), null);
	}




}
