package com.landray.kmss.fssc.eas.service.spring;

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
import com.landray.kmss.fssc.eas.model.FsscEasParam;
import com.landray.kmss.fssc.eas.service.IFsscEasParamService;
import com.landray.kmss.fssc.eas.util.FsscEasUtil;
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

public class FsscEasParamServiceImp extends ExtendDataServiceImp implements IFsscEasParamService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof FsscEasParam) {
			FsscEasParam fsscEasParam = (FsscEasParam) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		FsscEasParam fsscEasParam = new FsscEasParam();
		FsscEasUtil.initModelFromRequest(fsscEasParam, requestContext);
		return fsscEasParam;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		FsscEasParam fsscEasParam = (FsscEasParam) model;
	}
	
	@Override
    public void updateParam(IExtendForm form, RequestContext requestContext)
			throws Exception {
		List<FsscEasParam> paramList=this.findList(new HQLInfo());
		Map<String,FsscEasParam> resultMap=new ConcurrentHashMap();
		for (FsscEasParam eopBasedataParam : paramList) {
			resultMap.put(eopBasedataParam.getFdProperty(), eopBasedataParam);
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
				FsscEasParam fsscEasParam = new FsscEasParam();
				if (resultMap.containsKey(key)) {
					fsscEasParam = resultMap.get(key);
                    oldValue=fsscEasParam.getFdValue();
				}
				fsscEasParam.setFdProperty(key);
				String[] value = entry.getValue();
				if (value.length > 0) {
					fsscEasParam.setFdValue(value[0]);
                    newValue=value[0];
				}
				this.getBaseDao().getHibernateSession().saveOrUpdate(fsscEasParam);
	              //记录有修改的值变化，记录日志
				if (UserOperHelper.allowLogOper("updateParam", FsscEasParam.class.getName())) {
					if(oldValue!=null&&!oldValue.equals(newValue)){
						UserOperContentHelper.putUpdate(fsscEasParam).putSimple(key, oldValue,newValue);
					}else if(oldValue!=null&&oldValue.equals(newValue)){
						UserOperContentHelper.putAdd(fsscEasParam).putSimple(key,newValue);
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
			hqlInfo.setWhereBlock(" fsscEasParam.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<FsscEasParam> result = this.findList(hqlInfo);
			if (!ArrayUtil.isEmpty(result)) {
				for (FsscEasParam eas : result) {
					if(eas.getFdProperty().endsWith("fdName")){
						obj.put("fdName", eas.getFdValue());
					}
				}
			}
			obj.put("fdCostItem", ResourceUtil.getString("enums.cost_item."+i, "eop-basedata"));
			dataArr.add(obj);
		}
		return dataArr;
	}

}
