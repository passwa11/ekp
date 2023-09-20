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
import com.landray.kmss.km.archives.forms.KmArchivesDestroyForm;
import com.landray.kmss.km.archives.model.KmArchivesDestroy;
import com.landray.kmss.km.archives.model.KmArchivesDestroyDetails;
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyTemplateService;
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

public class KmArchivesDestroyServiceImp extends ExtendDataServiceImp
		implements IKmArchivesDestroyService, ApplicationListener {

	private IKmArchivesMainService kmArchivesMainService;

	public void setKmArchivesMainService(
			IKmArchivesMainService kmArchivesMainService) {
		this.kmArchivesMainService = kmArchivesMainService;
	}

	private IKmArchivesDestroyTemplateService kmArchivesDestroyTemplateService;

	public void setKmArchivesDestroyTemplateService(
			IKmArchivesDestroyTemplateService kmArchivesDestroyTemplateService) {
		this.kmArchivesDestroyTemplateService = kmArchivesDestroyTemplateService;
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
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesDestroy) {
            KmArchivesDestroy kmArchivesDestroy = (KmArchivesDestroy) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesDestroy kmArchivesDestroy = new KmArchivesDestroy();
        kmArchivesDestroy.setDocCreateTime(new Date());
		SysOrgPerson user = UserUtil.getUser();
		kmArchivesDestroy.setDocCreator(user);
		if (user.getFdParent() != null) {
			kmArchivesDestroy.setDocDept(user.getFdParent());
		}
        KmArchivesUtil.initModelFromRequest(kmArchivesDestroy, requestContext);
        return kmArchivesDestroy;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesDestroy kmArchivesDestroy = (KmArchivesDestroy) model;
        
        KmArchivesDestroyTemplate fdDefaultTemplate = kmArchivesDestroyTemplateService.getDefaultDestroyTemplate();
        if (fdDefaultTemplate != null) {
        	KmArchivesDestroyForm kmArchivesDestroyForm = (KmArchivesDestroyForm) form;
        	kmArchivesDestroyForm.setDocTemplateId(fdDefaultTemplate.getFdId());
        	kmArchivesDestroyForm.setDocTemplateName(fdDefaultTemplate.getFdName());
        	kmArchivesDestroy.setDocTemplate(fdDefaultTemplate);
        } else {
        	HQLInfo hqlInfo = new HQLInfo();
    		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
//    		List<KmArchivesDestroyTemplate> list = (List<KmArchivesDestroyTemplate>) kmArchivesDestroyTemplateService
//    				.findList(hqlInfo);
			Object one = kmArchivesDestroyTemplateService.findFirstOne(hqlInfo);
			if(one != null){
				kmArchivesDestroy.setDocTemplate((KmArchivesDestroyTemplate) one);
			}
//    		if (list != null && list.size() > 0) {
//    			kmArchivesDestroy.setDocTemplate(list.get(0));
//    		}
        }
		if (kmArchivesDestroy.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form, "kmArchivesDestroy",
					kmArchivesDestroy.getDocTemplate(), "kmArchivesDestroy",
					requestContext);
		}
//		} else {
//			getLbpmProcessCoreService().initFormDefaultSetting(form,
//					"kmArchivesDestroy", "kmArchivesDestroy", requestContext);
//		}
    }

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		deleteRecord(modelObj.getFdId());
		super.delete(modelObj);
	}

	private void deleteRecord(String fdOriginId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmArchivesDestroy.fdId");
		hqlInfo.setWhereBlock(" kmArchivesDestroy.fdOriginId=:fdOriginId ");
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
		if (!(obj instanceof KmArchivesDestroy)) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			KmArchivesDestroy kmArchivesDestroy = (KmArchivesDestroy) obj;
			List authAllReaders = kmArchivesDestroy.getAuthAllReaders();
			if (kmArchivesDestroy instanceof ISysLbpmMainModel) {
				try {
					List<KmArchivesDestroyDetails> details = kmArchivesDestroy
							.getFdDestroyDetails();
					for (KmArchivesDestroyDetails detail : details) {
						KmArchivesMain fdArchives = detail.getFdArchives();
						// 将档案销毁标记设置为true
						fdArchives.setFdDestroyed(true);
						kmArchivesMainService.update(fdArchives);

						KmArchivesDestroy destroy = new KmArchivesDestroy();
						destroy.setFdArchivesId(fdArchives.getFdId());
						destroy.setFdArchivesName(fdArchives.getDocSubject());
						destroy.setFdArchivesNumber(fdArchives.getDocNumber());
						destroy.setFdCategoryName(
								fdArchives.getDocTemplate().getFdName());
						destroy.setFdReturnDate(fdArchives.getFdFileDate());
						destroy.setFdReturnPerson(
								fdArchives.getDocCreator().getFdName());
						destroy.setFdDestroyIdea(
								kmArchivesDestroy.getFdDestroyIdea());
						destroy.setDocCreateTime(
								kmArchivesDestroy.getDocCreateTime());
						destroy.setDocCreator(
								kmArchivesDestroy.getDocCreator());
						destroy.setFdOriginId(kmArchivesDestroy.getFdId());
						List allReaders = new ArrayList<>();
						allReaders.addAll(authAllReaders);
						destroy.setAuthAllReaders(allReaders);
						add(destroy);
					}
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			}
		}
	}
	
    /**
     * 获取(我已审)档案销毁统计数字
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
			SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesDestroy", hql);
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
