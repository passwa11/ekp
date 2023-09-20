package com.landray.kmss.third.feishu.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.feishu.model.ThirdFeishuMsgMapp;
import com.landray.kmss.third.feishu.service.IThirdFeishuMsgMappService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdFeishuMsgMappServiceImp extends ExtendDataServiceImp implements IThirdFeishuMsgMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuMsgMapp) {
            ThirdFeishuMsgMapp thirdFeishuMsgMapp = (ThirdFeishuMsgMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuMsgMapp thirdFeishuMsgMapp = new ThirdFeishuMsgMapp();
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuMsgMapp, requestContext);
        return thirdFeishuMsgMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuMsgMapp thirdFeishuMsgMapp = (ThirdFeishuMsgMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<ThirdFeishuMsgMapp> findByNotifyId(String notifyId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("thirdFeishuMsgMapp.fdNotifyId=:notifyId");
		info.setParameter("notifyId", notifyId);
		return findList(info);
	}

	@Override
	public ThirdFeishuMsgMapp findByNotifyAndPerson(String notifyId,
			String personId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"thirdFeishuMsgMapp.fdNotifyId=:notifyId and thirdFeishuMsgMapp.fdPersonId=:personId");
		info.setParameter("notifyId", notifyId);
		info.setParameter("personId", personId);
		List list = findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("数据有问题，同一个待办同一个用户有多条记录");
		}
		return (ThirdFeishuMsgMapp) list.get(0);
	}

	@Override
	public ThirdFeishuMsgMapp findByMessageId(String messageId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"thirdFeishuMsgMapp.fdMessageId=:messageId");
		info.setParameter("messageId", messageId);
		List list = findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("数据有问题，同一个待办同一个用户有多条记录");
		}
		return (ThirdFeishuMsgMapp) list.get(0);
	}
}
