package com.landray.kmss.km.archives.service.spring;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesRenewService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.archives.model.KmArchivesRenew;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;

public class KmArchivesRenewServiceImp extends ExtendDataServiceImp implements IKmArchivesRenewService {

	// 注入通知机制对象
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}
	
	private IKmArchivesDetailsService kmArchivesDetailsService;
	public void setKmArchivesDetailsService(IKmArchivesDetailsService kmArchivesDetailsService) {
		this.kmArchivesDetailsService = kmArchivesDetailsService;
	}
	
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesRenew) {
            KmArchivesRenew kmArchivesRenew = (KmArchivesRenew) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesRenew kmArchivesRenew = new KmArchivesRenew();
        kmArchivesRenew.setDocCreateTime(new Date());
        kmArchivesRenew.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesRenew, requestContext);
        return kmArchivesRenew;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesRenew kmArchivesRenew = (KmArchivesRenew) model;
    }

    @Override
    public List<KmArchivesRenew> findByFdDetails(KmArchivesDetails fdDetails) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmArchivesRenew.fdDetails.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdDetails.getFdId());
        return this.findList(hqlInfo);
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
    	KmArchivesRenew kmArchivesRenew = (KmArchivesRenew) modelObj;
    	sendNotifyRead(kmArchivesRenew);
    	return super.add(modelObj);
    }

    private void sendNotifyRead(KmArchivesRenew kmArchivesRenew) throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService.getContext("km-archives:kmArchivesRenew.renewNotify");
		// 通知人
		List<SysOrgElement> tempListNotify = new ArrayList<SysOrgElement>();
		
		KmArchivesDetails Details = (KmArchivesDetails) kmArchivesDetailsService.findByPrimaryKey(kmArchivesRenew.getFdDetailsId());
		
		KmArchivesBorrow kmArchivesBorrow = Details.getDocMain();
		KmArchivesMain archives = Details.getFdArchives();
		if(archives != null) {
			tempListNotify.add(archives.getFdStorekeeper());
		}
		
		notifyContext.setNotifyTarget(tempListNotify);
		// 通知方式
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);// 待阅
		notifyContext.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO);
		//发起人
		notifyContext.setDocCreator(kmArchivesRenew.getDocCreator());
		//替换内容中的可变文本

		HashMap<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("km-archives:kmArchivesBorrow.fdBorrower",kmArchivesRenew.getDocCreator().getFdName());
		sysNotifyMainCoreService.send(kmArchivesBorrow, notifyContext, replaceMap);
	}
}
