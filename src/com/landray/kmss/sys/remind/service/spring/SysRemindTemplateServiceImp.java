package com.landray.kmss.sys.remind.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.sys.remind.service.ISysRemindMainService;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateRelationService;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateService;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提醒中心模板
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindTemplateServiceImp extends BaseCoreInnerServiceImp implements ISysRemindTemplateService {

	private ISysRemindMainService sysRemindMainService;

	private ISysRemindTemplateRelationService sysRemindTemplateRelationService;

	public void setSysRemindMainService(ISysRemindMainService sysRemindMainService) {
		this.sysRemindMainService = sysRemindMainService;
	}

	public void setSysRemindTemplateRelationService(
			ISysRemindTemplateRelationService sysRemindTemplateRelationService) {
		this.sysRemindTemplateRelationService = sysRemindTemplateRelationService;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		SysRemindTemplate template = (SysRemindTemplate) super.convertFormToModel(form, model, requestContext);
		List<SysRemindMain> mains = template.getFdMains();
		if (CollectionUtils.isNotEmpty(mains)) {
			for (SysRemindMain main : mains) {
				if (main.getDocCreateTime() == null) {
					main.setDocCreateTime(new Date());
				}
				if (main.getDocCreator() == null) {
					main.setDocCreator(UserUtil.getUser());
				}
			}
		}
		deleteMain(template);
		sysRemindTemplateRelationService.saveTemplateRelation(template);
		return template;
	}

	@Override
	public SysRemindTemplate findByTemplateNameAndId(String fdTemplateId, String fdTemplateName) throws Exception {
		List<SysRemindTemplate> list = listByTemplateNameAndId(fdTemplateId, fdTemplateName);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteByTemplateNameAndId(String fdTemplateId, String fdTemplateName) throws Exception {
		List<SysRemindTemplate> list = listByTemplateNameAndId(fdTemplateId, fdTemplateName);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysRemindTemplate temp : list) {
				delete(temp);
			}
		}
	}

	public List<SysRemindTemplate> listByTemplateNameAndId(String fdTemplateId, String fdTemplateName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTemplateId = :fdTemplateId and fdTemplateName = :fdTemplateName");
		hqlInfo.setParameter("fdTemplateId", fdTemplateId);
		hqlInfo.setParameter("fdTemplateName", fdTemplateName);
		return findList(hqlInfo);
	}

	@Override
	public SysRemindTemplate findByModelName(String fdModelName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdModelName = :fdModelName");
		hqlInfo.setParameter("fdModelName", fdModelName);
		SysRemindTemplate obj = (SysRemindTemplate)findFirstOne(hqlInfo);
		return obj;
	}

	@Override
	public void createRemindTask(SysRemindTemplate tmplModel, IBaseModel model) throws Exception {
		List<SysRemindMain> mains = tmplModel.getFdMains();
		if (CollectionUtils.isNotEmpty(mains)) {
			for (SysRemindMain main : mains) {
				if (BooleanUtils.isTrue(main.getFdIsEnable())) {
					sysRemindMainService.createRemindTask(main, model);
				}
			}
		}
	}

	/**
	 * 删除提醒设置
	 * 
	 * @param template
	 * @throws Exception
	 */
	private void deleteMain(SysRemindTemplate template) throws Exception {
		// 查询页面中不存在的提醒设置
		List<String> mainIds = new ArrayList<String>();
		List<SysRemindMain> mains = template.getFdMains();
		if (CollectionUtils.isNotEmpty(mains)) {
			for (SysRemindMain main : mains) {
				mainIds.add(main.getFdId());
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer where = new StringBuffer();
		where.append("fdTemplate.fdId is null");
		if (CollectionUtils.isNotEmpty(mainIds)) {
			where.append(" or (fdTemplate.fdId = :tplId and fdId not in (:ids))");
			hqlInfo.setParameter("ids", mainIds);
		} else {
			where.append(" or fdTemplate.fdId = :tplId");
		}
		hqlInfo.setParameter("tplId", template.getFdId());
		hqlInfo.setWhereBlock(where.toString());
		List<SysRemindMain> list = sysRemindMainService.findList(hqlInfo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysRemindMain main : list) {
				sysRemindMainService.delete(main);
			}
		}
	}

}
