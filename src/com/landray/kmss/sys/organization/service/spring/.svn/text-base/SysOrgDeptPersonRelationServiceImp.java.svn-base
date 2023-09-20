package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.dao.ISysOrgDeptPersonRelationDao;
import com.landray.kmss.sys.organization.model.SysOrgDeptPersonRelation;
import com.landray.kmss.sys.organization.service.ISysOrgDeptPersonRelationService;
import com.landray.kmss.util.StringUtil;

public class SysOrgDeptPersonRelationServiceImp extends BaseServiceImp
		implements
		ISysOrgDeptPersonRelationService {

	@Override
    public SysOrgDeptPersonRelation findRelation(String deptId,
                                                 String personId) throws Exception {
		if (StringUtil.isNull(deptId) && StringUtil.isNull(personId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdPersonId=:personId and fdDeptId=:deptId");
		info.setParameter("deptId", deptId);
		info.setParameter("personId", personId);
		List<SysOrgDeptPersonRelation> list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("数据有问题，存在多个关系");
		}
		return list.get(0);
	}

	@Override
	public String addRelation(String deptId, String personId,
			Integer order) throws Exception {
		SysOrgDeptPersonRelation relation = new SysOrgDeptPersonRelation();
		relation.setFdDeptId(deptId);
		relation.setFdPersonId(personId);
		relation.setFdOrder(order);
		return this.add(relation);
	}

	@Override
	public void delRelation(String personId) throws Exception {
		((ISysOrgDeptPersonRelationDao) getBaseDao()).delRelation(personId);
	}
	
	@Override
	public List<SysOrgDeptPersonRelation> findRelationList(String personId) throws Exception {
		if (StringUtil.isNull(personId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdPersonId=:personId");
		info.setParameter("personId", personId);
		List<SysOrgDeptPersonRelation> list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

}
