package com.landray.kmss.km.archives.service.spring;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.archives.forms.KmArchivesBorrowForm;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCoreService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class KmArchivesBorrowServiceImp extends ExtendDataServiceImp implements IKmArchivesBorrowService {

	private ILbpmProcessCoreService lbpmProcessCoreService;

	public ILbpmProcessCoreService getLbpmProcessCoreService() {
		if (lbpmProcessCoreService == null) {
			lbpmProcessCoreService = (ILbpmProcessCoreService) SpringBeanUtil
					.getBean("lbpmProcessCoreService");
		}
		return lbpmProcessCoreService;
	}

	private IKmArchivesDetailsService kmArchivesDetailsService;

	public void setKmArchivesDetailsService(
			IKmArchivesDetailsService kmArchivesDetailsService) {
		this.kmArchivesDetailsService = kmArchivesDetailsService;
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesBorrow) {
            KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) model;
            KmArchivesBorrowForm kmArchivesBorrowForm = (KmArchivesBorrowForm) form;
            if (kmArchivesBorrow.getDocStatus() == null || kmArchivesBorrow.getDocStatus().startsWith("1")) {
                if (kmArchivesBorrowForm.getDocStatus() != null && (kmArchivesBorrowForm.getDocStatus().startsWith("1") || kmArchivesBorrowForm.getDocStatus().startsWith("2"))) {
                    kmArchivesBorrow.setDocStatus(kmArchivesBorrowForm.getDocStatus());
                }
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesBorrow kmArchivesBorrow = new KmArchivesBorrow();
        kmArchivesBorrow.setDocCreateTime(new Date());
        kmArchivesBorrow.setFdBorrowDate(new Date());
        kmArchivesBorrow.setDocCreator(UserUtil.getUser());
        kmArchivesBorrow.setDocDept(UserUtil.getUser().getFdParent());
        kmArchivesBorrow.setFdBorrower(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesBorrow, requestContext);
        return kmArchivesBorrow;
    }

	private IKmArchivesTemplateService kmArchivesTemplateService;

	public IKmArchivesTemplateService getKmArchivesTemplateService() {
		return kmArchivesTemplateService;
	}

	public void setKmArchivesTemplateService(
			IKmArchivesTemplateService kmArchivesTemplateService) {
		this.kmArchivesTemplateService = kmArchivesTemplateService;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) model;
        
        KmArchivesTemplate fdDefaultTemplate = kmArchivesTemplateService.getDefaultTemplate();
        if (fdDefaultTemplate != null) {
        	kmArchivesBorrow.setDocTemplate(fdDefaultTemplate);
        } else {
        	List<KmArchivesTemplate> list = (List<KmArchivesTemplate>) kmArchivesTemplateService.getTemplateByDenses(null,
    				true);
    		if (list != null && list.size() > 0) {
    			kmArchivesBorrow.setDocTemplate(list.get(0));
    		}
        }
        if (kmArchivesBorrow.getDocTemplate() != null) {
            dispatchCoreService.initFormSetting(form, "kmArchivesBorrow", kmArchivesBorrow.getDocTemplate(), "kmArchivesBorrow", requestContext);
		} else {
			getLbpmProcessCoreService().initFormDefaultSetting(form,
					"kmArchivesBorrow", "kmArchivesBorrow", requestContext);
        }
    }

    @Override
    public List<KmArchivesBorrow> findByDocTemplate(KmArchivesTemplate docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmArchivesBorrow.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<KmArchivesBorrow> findByFdBorrowDetails(KmArchivesDetails fdBorrowDetails) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmArchivesBorrow.fdBorrowDetails.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBorrowDetails.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) modelObj;
		List<KmArchivesDetails> detailsList = kmArchivesBorrow
				.getFdBorrowDetails();
		if (null != detailsList && detailsList.size() > 0) {
			Iterator<KmArchivesDetails> iter = detailsList.iterator();
			while (iter.hasNext()) {
				KmArchivesDetails details = iter.next();
				if (details.getFdArchives() == null) {
					iter.remove();
				}
			}
		}
		return super.add(modelObj);
	}

	@Override
	public void deleteHard(IBaseModel modelObj) throws Exception {
		this.kmArchivesDetailsService.deleteByBorrowId(modelObj.getFdId());
		super.deleteHard(modelObj);
	}

	@Override
	public void deleteByArchivesId(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("docMain.fdId");
		hqlInfo.setWhereBlock("fdArchives.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		List list = this.kmArchivesDetailsService.findValue(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				IBaseModel model = findByPrimaryKey(
						String.valueOf(list.get(i)));
				deleteHard(model);
			}
		}
	}
	
    /**
     * 获取(我已审)档案借阅统计数字
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
			SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesBorrow", hql);
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
