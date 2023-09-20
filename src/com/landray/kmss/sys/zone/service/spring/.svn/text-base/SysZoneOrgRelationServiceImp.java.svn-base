package com.landray.kmss.sys.zone.service.spring;

import java.util.List;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.zone.model.SysZoneOrgRelation;
import com.landray.kmss.sys.zone.service.ISysZoneOrgOuterService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgRelationService;

public class SysZoneOrgRelationServiceImp extends BaseServiceImp implements ISysZoneOrgRelationService {

	private ISysZoneOrgOuterService sysZoneOrgOuterService = null;

	public void setSysZoneOrgOuterService(
			ISysZoneOrgOuterService sysZoneOrgOuterService) {
		this.sysZoneOrgOuterService = sysZoneOrgOuterService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllCateRelations(String cateId, String cateType)
			throws Exception {
		List<SysZoneOrgRelation> cateRelations = findList("fdCategoryId='" + cateId + "'", "");
		for (SysZoneOrgRelation relation : cateRelations) {
			String outerId = relation.getFdOrgId();
			delete(relation);
			if ("outer".equals(cateType)) {
			sysZoneOrgOuterService.deleteOuter(outerId);
			}
		}
	}

}
