package com.landray.kmss.third.weixin.work.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyTaskcardService;
import com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Date;
import java.util.List;

public class ThirdWeixinNotifyTaskcardServiceImp extends ExtendDataServiceImp implements IThirdWeixinNotifyTaskcardService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinNotifyTaskcard) {
            ThirdWeixinNotifyTaskcard thirdWeixinNotifyTaskcard = (ThirdWeixinNotifyTaskcard) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyTaskcard thirdWeixinNotifyTaskcard = new ThirdWeixinNotifyTaskcard();
        thirdWeixinNotifyTaskcard.setDocCreateTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinNotifyTaskcard, requestContext);
        return thirdWeixinNotifyTaskcard;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyTaskcard thirdWeixinNotifyTaskcard = (ThirdWeixinNotifyTaskcard) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<ThirdWeixinNotifyTaskcard> findByNotifyId(String notifyId)
			throws Exception {
		if (StringUtil.isNull(notifyId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("thirdWeixinNotifyTaskcard.fdNotifyId=:notifyId");
		info.setParameter("notifyId", notifyId);
		return this.findList(info);
	}

	public ThirdWeixinNotifyTaskcard findByNotifyIdAndUseridOld(String notifyId,
			String userId)
			throws Exception {
		if (StringUtil.isNull(notifyId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"thirdWeixinNotifyTaskcard.fdNotifyId=:notifyId and (thirdWeixinNotifyTaskcard.fdTouser like :userId1 or thirdWeixinNotifyTaskcard.fdTouser like :userId2 or thirdWeixinNotifyTaskcard.fdTouser like :userId3)");
		info.setParameter("notifyId", notifyId);
		info.setParameter("userId1", "%|" + userId + "%");
		info.setParameter("userId2", "%" + userId + "|%");
		info.setParameter("userId3", userId);
		return (ThirdWeixinNotifyTaskcard) this.findFirstOne(info);
	}

	@Override
	public ThirdWeixinNotifyTaskcard findByNotifyIdAndUserid(String notifyId,
			String userId)
			throws Exception {
		if (StringUtil.isNull(notifyId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"thirdWeixinNotifyTaskcard.fdNotifyId=:notifyId");
		info.setParameter("notifyId", notifyId);
		List<ThirdWeixinNotifyTaskcard> list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		for (ThirdWeixinNotifyTaskcard taskcard : list) {
			String touser = taskcard.getFdTouser();
			if (StringUtil.isNull(touser)) {
				continue;
			}
			if (touser.startsWith(userId + "|")
					|| touser.endsWith("|" + userId)
					|| touser.contains("|" + userId + "|")
					|| touser.equals(userId)) {
				return taskcard;
			}
		}
		return null;
	}

	@Override
	public ThirdWeixinNotifyTaskcard findByTaskcardId(String taskcardId)
			throws Exception {
		if (StringUtil.isNull(taskcardId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("thirdWeixinNotifyTaskcard.fdTaskcardId=:taskcardId");
		info.setParameter("taskcardId", taskcardId);
		return (ThirdWeixinNotifyTaskcard) this.findFirstOne(info);
	}

	@Override
	public ThirdWeixinNotifyTaskcard findByTaskcardId(String corpId, String taskcardId) throws Exception {
    	if(StringUtil.isNull(corpId)){
    		return findByTaskcardId(taskcardId);
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("thirdWeixinNotifyTaskcard.fdCorpId=:corpId and thirdWeixinNotifyTaskcard.fdTaskcardId=:taskcardId");
		info.setParameter("corpId",corpId);
		info.setParameter("taskcardId", taskcardId);
		return (ThirdWeixinNotifyTaskcard) this.findFirstOne(info);
	}
}
