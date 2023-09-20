package com.landray.kmss.sys.remind.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.sys.remind.model.SysRemindTemplateRelation;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateRelationService;
import com.landray.kmss.util.StringUtil;

import java.util.List;

/**
 * 提醒模板关系
 * 
 * @author panyh
 * @date Jun 30, 2020
 */
public class SysRemindTemplateRelationServiceImp extends BaseServiceImp implements ISysRemindTemplateRelationService {

	@Override
	public void saveTemplateRelation(SysRemindTemplate template) throws Exception {
		if (StringUtil.isNull(template.getFdModelName())) {
			return;
		}
		SysRemindTemplateRelation relation = getRelationByModel(template.getFdModelName());
		if (relation == null) {
			// 不存在时，才会新增
			relation = new SysRemindTemplateRelation();
			relation.setFdTemplateName(template.getFdTemplateName());
			relation.setFdModuleUrl(template.getFdModuleUrl());
			relation.setFdTemplateProperty(template.getFdTemplateProperty());
			relation.setFdModelName(template.getFdModelName());
			add(relation);
		}
	}

	@Override
	public SysRemindTemplateRelation getRelationByModel(String modelName) throws Exception {
		if (StringUtil.isNull(modelName)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTemplateName = :modelName or fdModelName = :modelName");
		hqlInfo.setParameter("modelName", modelName);
		SysRemindTemplateRelation obj = (SysRemindTemplateRelation)findFirstOne(hqlInfo);
		return obj;
	}

	@Override
	public List<SysRemindTemplateRelation> getRelationByModule(String moduleUrl) throws Exception {
		if (StringUtil.isNull(moduleUrl)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdModuleUrl = :moduleUrl");
		hqlInfo.setParameter("moduleUrl", moduleUrl);
		return findList(hqlInfo);
	}

}
