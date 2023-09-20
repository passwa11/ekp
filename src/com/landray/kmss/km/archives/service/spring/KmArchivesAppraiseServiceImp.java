package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseForm;
import com.landray.kmss.km.archives.model.KmArchivesAppraise;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseDetails;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseService;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseTemplateService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCoreService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class KmArchivesAppraiseServiceImp extends ExtendDataServiceImp
		implements IKmArchivesAppraiseService, ApplicationListener {

	private IKmArchivesMainService kmArchivesMainService;

	public void setKmArchivesMainService(
			IKmArchivesMainService kmArchivesMainService) {
		this.kmArchivesMainService = kmArchivesMainService;
	}

	private IKmArchivesAppraiseTemplateService kmArchivesAppraiseTemplateService;

	public void setKmArchivesAppraiseTemplateService(
			IKmArchivesAppraiseTemplateService kmArchivesAppraiseTemplateService) {
		this.kmArchivesAppraiseTemplateService = kmArchivesAppraiseTemplateService;
	}

	private ILbpmProcessCoreService lbpmProcessCoreService;

	public ILbpmProcessCoreService getLbpmProcessCoreService() {
		if (lbpmProcessCoreService == null) {
			lbpmProcessCoreService = (ILbpmProcessCoreService) SpringBeanUtil
					.getBean("lbpmProcessCoreService");
		}
		return lbpmProcessCoreService;
	}

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
											ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesAppraise) {
            KmArchivesAppraise kmArchivesAppraise = (KmArchivesAppraise) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesAppraise kmArchivesAppraise = new KmArchivesAppraise();
        kmArchivesAppraise.setDocCreateTime(new Date());
		SysOrgPerson user = UserUtil.getUser();
		kmArchivesAppraise.setDocCreator(user);
		if (user.getFdParent() != null) {
			kmArchivesAppraise.setDocDept(user.getFdParent());
		}
        KmArchivesUtil.initModelFromRequest(kmArchivesAppraise, requestContext);
        return kmArchivesAppraise;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesAppraise kmArchivesAppraise = (KmArchivesAppraise) model;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
		
		KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate = kmArchivesAppraiseTemplateService.getDefaultAppraiseTemplate();
		if (kmArchivesAppraiseTemplate != null) {
			KmArchivesAppraiseForm kmArchivesAppraiseForm = (KmArchivesAppraiseForm) form;
			kmArchivesAppraiseForm.setDocTemplateId(kmArchivesAppraiseTemplate.getFdId());
			kmArchivesAppraiseForm.setDocTemplateName(kmArchivesAppraiseTemplate.getFdName());
			kmArchivesAppraise.setDocTemplate(kmArchivesAppraiseTemplate);
		} else {
//			List<KmArchivesAppraiseTemplate> list = (List<KmArchivesAppraiseTemplate>) kmArchivesAppraiseTemplateService
//					.findList(hqlInfo);
			Object one = kmArchivesAppraiseTemplateService.findFirstOne(hqlInfo);
			if(one != null){
				kmArchivesAppraise.setDocTemplate((KmArchivesAppraiseTemplate) one);
			}
//			if (list != null && list.size() > 0) {
//				kmArchivesAppraise.setDocTemplate(list.get(0));
//			}
		}
		if (kmArchivesAppraise.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form, "kmArchivesAppraise",
					kmArchivesAppraise.getDocTemplate(), "kmArchivesAppraise",
					requestContext);
		}
    }

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		deleteRecord(modelObj.getFdId());
		super.delete(modelObj);
	}

	private void deleteRecord(String fdOriginId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmArchivesAppraise.fdId");
		hqlInfo.setWhereBlock(" kmArchivesAppraise.fdOriginId=:fdOriginId ");
		hqlInfo.setParameter("fdOriginId", fdOriginId);
		List<String> list = this.findList(hqlInfo);
		if (null != list && list.size() > 0) {
			this.delete(list.toArray(new String[0]));
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		if (!(obj instanceof KmArchivesAppraise)) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			KmArchivesAppraise kmArchivesAppraise = (KmArchivesAppraise) obj;
			List authAllReaders = kmArchivesAppraise.getAuthAllReaders();
			if (kmArchivesAppraise instanceof ISysLbpmMainModel) {
				try {
					List<KmArchivesAppraiseDetails> details = kmArchivesAppraise
							.getFdAppraiseDetails();
					for (KmArchivesAppraiseDetails detail : details) {
						KmArchivesMain fdArchives = detail.getFdArchives();
						fdArchives
								.setFdValidityDate(
										detail.getFdAfterAppraiseDate());
						kmArchivesMainService.update(fdArchives);

						KmArchivesAppraise appraise = new KmArchivesAppraise();
						appraise.setFdArchivesId(fdArchives.getFdId());
						appraise.setFdArchivesName(fdArchives.getDocSubject());
						appraise.setFdArchivesNumber(fdArchives.getDocNumber());
						appraise.setFdOriginalDate(
								fdArchives.getFdValidityDate());
						appraise.setFdAfterAppraiseDate(
								detail.getFdAfterAppraiseDate());
						appraise.setFdAppraiseIdea(
								kmArchivesAppraise.getFdAppraiseIdea());
						appraise.setDocCreateTime(
								kmArchivesAppraise.getDocCreateTime());
						appraise.setDocCreator(
								kmArchivesAppraise.getDocCreator());
						appraise.setFdOriginId(kmArchivesAppraise.getFdId());
						List allReaders = new ArrayList<>();
						allReaders.addAll(authAllReaders);
						appraise.setAuthAllReaders(allReaders);
						add(appraise);
					}
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			}
		}
	}
	
    /**
     * 获取(我已审)档案鉴定统计数字
     * @param startTime  统计范围起始时间（可为空）
     * @param endTime 统计范围截至时间（可为空） 
     * @return
     * @throws Exception
     */
	@Override
	public Long getApprovedStatisticalCount(Date startTime, Date endTime) throws Exception {
		Long count = 0L;
		try {
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock(" 1=1 ");
			hql.setGettingCount(true);
			SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesAppraise", hql);
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			StringBuffer whereBlock = new StringBuffer();
			if(startTime!=null || endTime!=null){
				whereBlock.append(" and myWorkitem.fdFinishDate is not null ");
				if(startTime!=null){
					whereBlock.append(" and myWorkitem.fdFinishDate >= :startTime ");
					hql.setParameter("startTime", startTime);
				}
				if(endTime!=null){
					whereBlock.append(" and myWorkitem.fdFinishDate <= :endTime ");
					hql.setParameter("endTime", endTime);
				}
			}
			hql.setWhereBlock(hql.getWhereBlock()+whereBlock.toString());			
	
			List<Long> list = this.getBaseDao().findValue(hql);
			if (list.size() > 0) {
				count = list.get(0);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
