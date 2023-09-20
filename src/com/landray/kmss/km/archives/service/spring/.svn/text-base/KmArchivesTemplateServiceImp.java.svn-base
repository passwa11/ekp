package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

public class KmArchivesTemplateServiceImp extends ExtendDataServiceImp
		implements IKmArchivesTemplateService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesTemplate) {
            KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesTemplate kmArchivesTemplate = new KmArchivesTemplate();
        kmArchivesTemplate.setDocCreateTime(new Date());
        kmArchivesTemplate.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesTemplate, requestContext);
        return kmArchivesTemplate;
    }

	@Override
	public List<KmArchivesTemplate> getTemplateByMainDense(String fdMainId) throws Exception {
		KmArchivesMain kmArchivesMain = (KmArchivesMain) ((IKmArchivesMainService) SpringBeanUtil
				.getBean("kmArchivesMainService")).findByPrimaryKey(fdMainId);
		KmArchivesDense kmArchivesDense = kmArchivesMain.getFdDense();
		List<KmArchivesTemplate> kmArchivesTemplateList = new ArrayList();
		if (kmArchivesDense != null) {
			List fdDenseIds = new ArrayList();
			fdDenseIds.add(kmArchivesDense.getFdId());
			kmArchivesTemplateList = this.getTemplateByDenses(fdDenseIds, false);
		} else {
			kmArchivesTemplateList = this.getTemplateByDenses(null, false);
		}
		return kmArchivesTemplateList;
	}

	@Override
	public List<KmArchivesTemplate> getTemplateByDenses(List denseIds, Boolean getAll) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "1=1";
		if (!getAll) {
			if (denseIds != null && denseIds.size() > 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						HQLUtil.buildLogicIN("kmArchivesTemplate.listDenseLevel.fdId", denseIds));

			}else{
				hqlInfo.setJoinBlock("left join kmArchivesTemplate.listDenseLevel listDenseLevel");
				whereBlock = StringUtil.linkString(whereBlock, " and ", "listDenseLevel.fdId is null");
			}
		}
		
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
		List<KmArchivesTemplate> kmArchivesTemplateList = this.findList(hqlInfo);
		return kmArchivesTemplateList;
	}
	
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) modelObj;
		updateCurrentMeeting(kmArchivesTemplate);
		return super.add(modelObj);
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) modelObj;
		updateCurrentMeeting(kmArchivesTemplate);
		super.update(modelObj);
	}
	
	/**
	 * 修改之前的默认模板为非默认当前
	 * @param kmProposalMeeting
	 * @throws Exception 
	 */
	public void updateCurrentMeeting(KmArchivesTemplate kmArchivesTemplate) throws Exception {
		TransactionStatus tStatus = null;
		KmArchivesTemplate oldTemplate = null;
		if(kmArchivesTemplate.getFdDefaultFlag() == 1) {
			try {
				tStatus = TransactionUtils.beginNewTransaction();
				oldTemplate = (KmArchivesTemplate) getDefaultTemplate(kmArchivesTemplate.getFdId());
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
		String whereBlock = "kmArchivesTemplate.fdDefaultFlag = 1 and kmArchivesTemplate.fdId <> :fdTemplateId";
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
	public KmArchivesTemplate getDefaultTemplate() throws Exception {
		KmArchivesTemplate kmArchivesTemplate = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmArchivesTemplate.fdDefaultFlag = 1");
//		List list = getBaseDao().findList(hqlInfo);
		Object one = getBaseDao().findFirstOne(hqlInfo);
		if(one != null){
			kmArchivesTemplate = (KmArchivesTemplate) one;
		}
//		if (list != null && !list.isEmpty()) {
//			kmArchivesTemplate = (KmArchivesTemplate) list.get(0);
//		}
		return kmArchivesTemplate;
	}
}
