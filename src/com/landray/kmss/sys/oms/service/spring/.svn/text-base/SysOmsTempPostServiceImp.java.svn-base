package com.landray.kmss.sys.oms.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.service.ISysOmsTempPostService;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsTempPostServiceImp extends ExtendDataServiceImp implements ISysOmsTempPostService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempPost) {
            SysOmsTempPost sysOmsTempPost = (SysOmsTempPost) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempPost sysOmsTempPost = new SysOmsTempPost();
        sysOmsTempPost.setFdIsAvailable(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsTempPost, requestContext);
        return sysOmsTempPost;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempPost sysOmsTempPost = (SysOmsTempPost) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<SysOmsTempPost> findListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempPost findByPp(SysOmsTempPp sysOmsTempPp) throws Exception {
		SysOmsTempPost post = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempPost.fdPostId=:fdPostId and sysOmsTempPost.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId", sysOmsTempPp.getFdTrxId());
		hqlInfo.setParameter("fdPostId", sysOmsTempPp.getFdPostId());
		List<SysOmsTempPost> posts = findList(hqlInfo);
		if(posts != null && !posts.isEmpty()) {
            post = posts.get(0);
        }
		return post;
	}

	@Override
	public List<SysOmsTempPost> findFailListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId and fdStatus=:fdStatus");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdStatus", SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempPost findByPostId(String fdPostId, String fdTrxId) throws Exception {
		SysOmsTempPost post = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempPost.fdPostId=:fdPostId and sysOmsTempPost.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdPostId",fdPostId);
		List<SysOmsTempPost> posts = findList(hqlInfo);
		if(posts != null && !posts.isEmpty()) {
            post = posts.get(0);
        }
		return post;
	}
}
