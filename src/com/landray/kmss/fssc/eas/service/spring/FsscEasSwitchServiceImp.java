package com.landray.kmss.fssc.eas.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.eas.model.FsscEasSwitch;
import com.landray.kmss.fssc.eas.service.IFsscEasSwitchService;
import com.landray.kmss.fssc.eas.util.FsscEasUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FsscEasSwitchServiceImp extends ExtendDataServiceImp implements IFsscEasSwitchService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof FsscEasSwitch) {
			FsscEasSwitch fsscEasSwitch = (FsscEasSwitch) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		FsscEasSwitch fsscEasSwitch = new FsscEasSwitch();
		FsscEasUtil.initModelFromRequest(fsscEasSwitch, requestContext);
		return fsscEasSwitch;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		FsscEasSwitch fsscEasSwitch = (FsscEasSwitch) model;
	}
	@Override
    public void updateSwitch(IExtendForm form, RequestContext requestContext)
			throws Exception {
		List<FsscEasSwitch> switchList=this.findList(new HQLInfo());
		Map<String,FsscEasSwitch> resultMap=new ConcurrentHashMap();
		for (FsscEasSwitch eopBasedataSwitch : switchList) {
			resultMap.put(eopBasedataSwitch.getFdProperty(), eopBasedataSwitch);
		}
		Map<String, String[]> parameterMap = requestContext.getParameterMap();
		Iterator<Map.Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
        	Object oldValue=null;  //修改前的值
			Object newValue=null;  //修改后的值
			Map.Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			if (StringUtil.isNotNull(key) && (key.startsWith("fd") || key.startsWith("_fd"))) {
				if (key.startsWith("_fd")) {
					key = key.substring(1, key.length());
				}
				FsscEasSwitch fsscEasSwitch = new FsscEasSwitch();
				if (resultMap.containsKey(key)) {
					fsscEasSwitch = resultMap.get(key);
                    oldValue=fsscEasSwitch.getFdValue();
				}
				fsscEasSwitch.setFdProperty(key);
				String[] value = entry.getValue();
				if (value.length > 0) {
					fsscEasSwitch.setFdValue(value[0]);
                    newValue=value[0];
				}
				this.getBaseDao().getHibernateSession().saveOrUpdate(fsscEasSwitch);
	              //记录有修改的值变化，记录日志
				if (UserOperHelper.allowLogOper("updateSwitch", FsscEasSwitch.class.getName())) {
					if(oldValue!=null&&!oldValue.equals(newValue)){
						UserOperContentHelper.putUpdate(fsscEasSwitch).putSimple(key, oldValue,newValue);
					}else if(oldValue!=null&&oldValue.equals(newValue)){
						UserOperContentHelper.putAdd(fsscEasSwitch).putSimple(key,newValue);
					}
				}
			}
		}
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}
}
