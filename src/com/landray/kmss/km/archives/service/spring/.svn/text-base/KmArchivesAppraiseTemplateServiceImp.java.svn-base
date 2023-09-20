package com.landray.kmss.km.archives.service.spring;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

public class KmArchivesAppraiseTemplateServiceImp extends ExtendDataServiceImp
		implements IKmArchivesAppraiseTemplateService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
		if (model instanceof KmArchivesAppraiseTemplate) {
			KmArchivesAppraiseTemplate KmArchivesAppraiseTemplate = (KmArchivesAppraiseTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		KmArchivesAppraiseTemplate KmArchivesAppraiseTemplate = new KmArchivesAppraiseTemplate();
		KmArchivesAppraiseTemplate.setDocCreateTime(new Date());
		KmArchivesAppraiseTemplate.setDocCreator(UserUtil.getUser());
		KmArchivesUtil.initModelFromRequest(KmArchivesAppraiseTemplate,
				requestContext);
		return KmArchivesAppraiseTemplate;
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
    	KmArchivesAppraiseTemplate fdTemplate = (KmArchivesAppraiseTemplate) modelObj;
    	updateCurrentMeeting(fdTemplate);
    	return super.add(modelObj);
    }
    
    @Override
    public void update(IBaseModel modelObj) throws Exception {
    	KmArchivesAppraiseTemplate fdTemplate = (KmArchivesAppraiseTemplate) modelObj;
    	updateCurrentMeeting(fdTemplate);
    	super.update(modelObj);
    }
    
    /**
	 * 修改之前的默认模板为非默认当前
	 * @param kmProposalMeeting
	 * @throws Exception 
	 */
	public void updateCurrentMeeting(KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate) throws Exception {
		TransactionStatus tStatus = null;
		KmArchivesAppraiseTemplate oldAppraiseTemplate = null;
		if(kmArchivesAppraiseTemplate.getFdDefaultFlag() == 1) {
			try {
				tStatus = TransactionUtils.beginNewTransaction();
				oldAppraiseTemplate = (KmArchivesAppraiseTemplate) getDefaultTemplate(kmArchivesAppraiseTemplate.getFdId());
				if(oldAppraiseTemplate != null) {
					oldAppraiseTemplate.setFdDefaultFlag(0);
					super.update(oldAppraiseTemplate);
				}
				TransactionUtils.getTransactionManager().commit(tStatus);
			}catch (Exception e) {
				e.printStackTrace();
				if(tStatus != null){
					TransactionUtils.getTransactionManager().rollback(tStatus);
				}
			}
		}
	}
	
	/**
	 *  获取已有的默认模板
	 * @param fdTemplateId 当前编辑的模板meeting对象的Id
	 * @return 1、如果有默认模板，返回 IBaseModel；2、没有返回 null
	 * @throws Exception
	 */
	public IBaseModel getDefaultTemplate(String fdTemplateId) throws Exception {
		String whereBlock = "kmArchivesAppraiseTemplate.fdDefaultFlag = 1 and kmArchivesAppraiseTemplate.fdId <> :fdTemplateId";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdTemplateId", fdTemplateId);
//		List list = this.findList(hqlInfo);
		Object one = this.findFirstOne(hqlInfo);
		if(one != null){
			return (IBaseModel) one;
		}else {
			return null;
		}
	}

	@Override
	public KmArchivesAppraiseTemplate getDefaultAppraiseTemplate() throws Exception {
		KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
		hqlInfo.setWhereBlock("kmArchivesAppraiseTemplate.fdDefaultFlag = 1");
//		List list = getBaseDao().findList(hqlInfo);
		Object one = getBaseDao().findFirstOne(hqlInfo);
		if(one != null){
			kmArchivesAppraiseTemplate = (KmArchivesAppraiseTemplate) one;
		}
//		if(list != null && !list.isEmpty()) {
//			kmArchivesAppraiseTemplate = (KmArchivesAppraiseTemplate) list.get(0);
//		}
		return kmArchivesAppraiseTemplate;
	}
}
