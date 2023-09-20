package com.landray.kmss.third.ding.scenegroup.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupModuleService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class ThirdDingScenegroupModuleServiceImp extends ExtendDataServiceImp
		implements IThirdDingScenegroupModuleService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingScenegroupModule) {
            ThirdDingScenegroupModule thirdDingScenegroupModule = (ThirdDingScenegroupModule) model;
            thirdDingScenegroupModule.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingScenegroupModule thirdDingScenegroupModule = new ThirdDingScenegroupModule();
        thirdDingScenegroupModule.setDocCreateTime(new Date());
        thirdDingScenegroupModule.setDocAlterTime(new Date());
        thirdDingScenegroupModule.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingScenegroupModule, requestContext);
        return thirdDingScenegroupModule;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingScenegroupModule thirdDingScenegroupModule = (ThirdDingScenegroupModule) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdDingScenegroupModule findByKey(String key) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdKey=:fdKey");
		info.setParameter("fdKey", key);
		return (ThirdDingScenegroupModule) findFirstOne(info);
	}

	private String checkFdKey(String fdKey, String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdKey");
		hqlInfo.setWhereBlock("fdKey = :fdKey and fdId != :fdId");
		hqlInfo.setParameter("fdKey", fdKey);
		hqlInfo.setParameter("fdId", fdId);
		return (String) findFirstOne(hqlInfo);
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		String fdKey = requestInfo.getParameter("value");
		String fdId = requestInfo.getParameter("fdId");
		if (StringUtil.isNull(fdKey) || StringUtil.isNull(fdId)) {
			map.put("result", "");
			return list;
		}
		String result = checkFdKey(fdKey, fdId);
		map.put("result", result);
		list.add(map);
		return list;
	}
}
