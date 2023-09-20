package com.landray.kmss.third.ding.scenegroup.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupMappService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ThirdDingScenegroupMappServiceImp extends ExtendDataServiceImp implements IThirdDingScenegroupMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingScenegroupMapp) {
            ThirdDingScenegroupMapp thirdDingScenegroupMapp = (ThirdDingScenegroupMapp) model;
            thirdDingScenegroupMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingScenegroupMapp thirdDingScenegroupMapp = new ThirdDingScenegroupMapp();
        thirdDingScenegroupMapp.setDocCreateTime(new Date());
        thirdDingScenegroupMapp.setDocAlterTime(new Date());
        thirdDingScenegroupMapp.setFdStatus(String.valueOf("1"));
        thirdDingScenegroupMapp.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingScenegroupMapp, requestContext);
        return thirdDingScenegroupMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingScenegroupMapp thirdDingScenegroupMapp = (ThirdDingScenegroupMapp) model;
    }

    @Override
    public List<ThirdDingScenegroupMapp> findByFdModuleId(ThirdDingScenegroupModule fdModuleId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("thirdDingScenegroupMapp.fdModuleId.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdModuleId.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdDingScenegroupMapp findByModel(String modelName,
                                               String modelId, String fdKey) throws Exception {
		if (StringUtil.isNull(modelName) && StringUtil.isNull(modelId)
				&& StringUtil.isNull(fdKey)) {
			throw new Exception("modelName、modelId、fdKey不能都为空");
		}
		HQLInfo info = new HQLInfo();
		String where = " fdStatus='1'";
		if(StringUtil.isNotNull(modelName)){
			where += " and fdModelName=:modelName";
			info.setParameter("modelName",modelName);
		}
		if(StringUtil.isNotNull(modelId)){
			where += " and fdModelId=:modelId";
			info.setParameter("modelId",modelId);
		}
		if(StringUtil.isNotNull(fdKey)){
			where += " and fdKey=:fdKey";
			info.setParameter("fdKey",fdKey);
		}
		info.setWhereBlock(where);
		return (ThirdDingScenegroupMapp) findFirstOne(info);
	}

	@Override
    public ThirdDingScenegroupMapp findByChatId(String chatId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		String where = " 1=1";
		where += " and fdChatId=:chatId";
		info.setParameter("chatId", chatId);
		info.setWhereBlock(where);
		return (ThirdDingScenegroupMapp) findFirstOne(info);
	}

	@Override
	public void updateByCallback(JSONObject plainTextJson) throws Exception {
		// TODO Auto-generated method stub
		String chatId = plainTextJson.getString("ChatId");
		if (StringUtil.isNull(chatId)) {
			return;
		}
		ThirdDingScenegroupMapp mapp = findByChatId(chatId);
		if (mapp == null) {
			return;
		}
		String eventType = plainTextJson.getString("EventType");
		if ("chat_update_title".equals(eventType)) {
			String Title = plainTextJson.getString("Title");
			mapp.setFdName(Title);
			update(mapp);
		} else if ("chat_disband".equals(eventType)) {
			mapp.setFdStatus("2");
			update(mapp);
		}
	}
}
