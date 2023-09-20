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
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

public class KmArchivesDestroyTemplateServiceImp extends ExtendDataServiceImp
		implements IKmArchivesDestroyTemplateService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
		if (model instanceof KmArchivesDestroyTemplate) {
			KmArchivesDestroyTemplate KmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		KmArchivesDestroyTemplate KmArchivesDestroyTemplate = new KmArchivesDestroyTemplate();
		KmArchivesDestroyTemplate.setDocCreateTime(new Date());
		KmArchivesDestroyTemplate.setDocCreator(UserUtil.getUser());
		KmArchivesUtil.initModelFromRequest(KmArchivesDestroyTemplate,
				requestContext);
		return KmArchivesDestroyTemplate;
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
    	KmArchivesDestroyTemplate kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) modelObj;
    	updateCurrentMeeting(kmArchivesDestroyTemplate);
    	return super.add(modelObj);
    }
    
    @Override
    public void update(IBaseModel modelObj) throws Exception {
    	KmArchivesDestroyTemplate kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) modelObj;
    	updateCurrentMeeting(kmArchivesDestroyTemplate);
    	super.update(modelObj);
    }
    
    /**
	 * 修改之前的默认模板为非默认当前
	 * @param kmProposalMeeting
	 * @throws Exception 
	 */
	public void updateCurrentMeeting(KmArchivesDestroyTemplate kmArchivesDestroyTemplate) throws Exception {
		TransactionStatus tStatus = null;
		KmArchivesDestroyTemplate oldTemplate = null;
		if(kmArchivesDestroyTemplate.getFdDefaultFlag() == 1) {
			try {
				tStatus = TransactionUtils.beginNewTransaction();
				oldTemplate = (KmArchivesDestroyTemplate) getDefaultTemplate(kmArchivesDestroyTemplate.getFdId());
				if(oldTemplate != null) {
					oldTemplate.setFdDefaultFlag(0);
					super.update(oldTemplate);
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
		String whereBlock = "kmArchivesDestroyTemplate.fdDefaultFlag = 1 and kmArchivesDestroyTemplate.fdId <> :fdTemplateId";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdTemplateId", fdTemplateId);
//		List list = this.findList(hqlInfo);
		Object one = this.findFirstOne(hqlInfo);
		if(one != null){
			return (IBaseModel) one;
		}
//		if(list != null && list.size() > 0) {
//			return (IBaseModel) list.get(0);
//		}
		else {
			return null;
		}
	}

	@Override
	public KmArchivesDestroyTemplate getDefaultDestroyTemplate() throws Exception {
		KmArchivesDestroyTemplate kmArchivesDestroyTemplate = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
		hqlInfo.setWhereBlock("kmArchivesDestroyTemplate.fdDefaultFlag = 1");
//		List list = getBaseDao().findList(hqlInfo);
		Object one = getBaseDao().findFirstOne(hqlInfo);
		if(one != null){
			kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) one;
		}
//		if (list != null && !list.isEmpty()) {
//			kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) list.get(0);
//		}
		return kmArchivesDestroyTemplate;
	}
}
