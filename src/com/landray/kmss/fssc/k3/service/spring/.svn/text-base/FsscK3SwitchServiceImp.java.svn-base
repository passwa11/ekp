package com.landray.kmss.fssc.k3.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.k3.model.FsscK3Switch;
import com.landray.kmss.fssc.k3.service.IFsscK3SwitchService;
import com.landray.kmss.fssc.k3.util.FsscK3Util;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscK3SwitchServiceImp extends ExtendDataServiceImp implements IFsscK3SwitchService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof FsscK3Switch) {
			FsscK3Switch fsscK3Switch = (FsscK3Switch) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		FsscK3Switch fsscK3Switch = new FsscK3Switch();
		FsscK3Util.initModelFromRequest(fsscK3Switch, requestContext);
		return fsscK3Switch;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		FsscK3Switch fsscK3Switch = (FsscK3Switch) model;
	}
	@Override
    public void updateSwitch(IExtendForm form, RequestContext requestContext)
			throws Exception {
		List<FsscK3Switch> switchList=this.findList(new HQLInfo());
		Map<String,FsscK3Switch> resultMap=new ConcurrentHashMap();
		for (FsscK3Switch eopBasedataSwitch : switchList) {
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
				FsscK3Switch fsscK3Switch = new FsscK3Switch();
				if (resultMap.containsKey(key)) {
					fsscK3Switch = resultMap.get(key);
                    oldValue=fsscK3Switch.getFdValue();
				}
				fsscK3Switch.setFdProperty(key);
				String[] value = entry.getValue();
				if (value.length > 0) {
					fsscK3Switch.setFdValue(value[0]);
                    newValue=value[0];
				}
				this.getBaseDao().getHibernateSession().saveOrUpdate(fsscK3Switch);
	              //记录有修改的值变化，记录日志
				if (UserOperHelper.allowLogOper("updateSwitch", FsscK3Switch.class.getName())) {
					if(oldValue!=null&&!oldValue.equals(newValue)){
						UserOperContentHelper.putUpdate(fsscK3Switch).putSimple(key, oldValue,newValue);
					}else if(oldValue!=null&&oldValue.equals(newValue)){
						UserOperContentHelper.putAdd(fsscK3Switch).putSimple(key,newValue);
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

	@Override
	public JSONArray getBaseCostItem() throws Exception {
		JSONArray dataArr=new JSONArray();
		List accountList = EnumerationTypeUtil.getColumnEnumsByType("eop_basedata_cost_item");
		String preName = "";
		for(int i=1;i<=accountList.size();i++){
			JSONObject obj=new JSONObject();
			preName = "fdDetail." + (i-1) + ".";
			Map<String, String> valMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fsscK3Switch.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<FsscK3Switch> result = this.findList(hqlInfo);
			if (!ArrayUtil.isEmpty(result)) {
				for (FsscK3Switch k3 : result) {
					if(k3.getFdProperty().endsWith("fdCode")){
						obj.put("fdCode", k3.getFdValue());
					}else{
						obj.put("fdName", k3.getFdValue());
					}
				}
			}
			obj.put("fdCostItem", ResourceUtil.getString("enums.cost_item."+i, "eop-basedata"));
			dataArr.add(obj);
		}
		return dataArr;
	}

}
