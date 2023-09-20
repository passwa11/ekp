package com.landray.kmss.sys.organization.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgDeptPersonRelation;

public interface ISysOrgDeptPersonRelationService extends IBaseService {

	public SysOrgDeptPersonRelation findRelation(String deptId,
			String personId) throws Exception;

	public String addRelation(String deptId,
			String personId, Integer order) throws Exception;

	public void delRelation(String personId) throws Exception;
	
	public List<SysOrgDeptPersonRelation> findRelationList(String personId) throws Exception;

}
