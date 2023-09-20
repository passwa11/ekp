package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataSwitch;
import com.landray.kmss.eop.basedata.service.IEopBasedataSwitchService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class EopBasedataSwitchServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataSwitchService {
	
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataSwitch) {
            EopBasedataSwitch eopBasedataSwitch = (EopBasedataSwitch) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataSwitch eopBasedataSwitch = new EopBasedataSwitch();
        EopBasedataUtil.initModelFromRequest(eopBasedataSwitch, requestContext);
        return eopBasedataSwitch;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataSwitch eopBasedataSwitch = (EopBasedataSwitch) model;
    }
    
    @Override
    public void updateSwitch(IExtendForm form, RequestContext requestContext)
    		throws Exception {
    	List<EopBasedataSwitch> switchList=this.findList(new HQLInfo());
    	Map<String,EopBasedataSwitch> resultMap=new HashMap();
    	for (EopBasedataSwitch eopBasedataSwitch : switchList) {
    		resultMap.put(eopBasedataSwitch.getFdProperty(), eopBasedataSwitch);
    		this.delete(eopBasedataSwitch);
		}
		Map<String, String[]> parameterMap = requestContext.getParameterMap();
		Iterator<Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Object oldValue="";  //修改前的值
			Object newValue="";  //修改后的值
			Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			if (StringUtil.isNotNull(key) && (key.startsWith("fd"))) {
				EopBasedataSwitch eopBasedataSwitch = new EopBasedataSwitch();
				if (resultMap.containsKey(key)) {
					eopBasedataSwitch = resultMap.get(key);
					oldValue=eopBasedataSwitch.getFdValue();
				}
				eopBasedataSwitch.setFdProperty(key);
				String[] value = entry.getValue();
				if (value.length > 0) {
					for(int i=0,len=value.length;i<len;i++){
						newValue+=value[i]+";";
					}
					if(newValue!=null&&StringUtil.isNotNull(String.valueOf(newValue))){
						newValue=newValue.toString().substring(0, newValue.toString().length()-1);
						if(key.endsWith("Date")){ //时间属性
							String pattern = ResourceUtil.getString("date.format.date", ResourceUtil.getLocaleByUser());
							String tempStr=newValue.toString();
							Date tempDate=DateUtil.convertStringToDate(tempStr, pattern);
							tempStr=DateUtil.convertDateToString(tempDate, "yyyy-MM-dd");
							eopBasedataSwitch.setFdValue(tempStr);  //日期处理后保存
						}else{
							eopBasedataSwitch.setFdValue(newValue.toString());  //正常保存
						}
					}
				}
				this.getBaseDao().getHibernateSession().saveOrUpdate(
						eopBasedataSwitch);
				//记录有修改的值变化，记录日志
				if (UserOperHelper.allowLogOper("updateSwitch", EopBasedataSwitch.class.getName())) {
					if(oldValue!=null&&!oldValue.equals(newValue)){
						UserOperContentHelper.putUpdate(eopBasedataSwitch).putSimple(key, oldValue,newValue);
					}else if(oldValue!=null&&oldValue.equals(newValue)){
						UserOperContentHelper.putAdd(eopBasedataSwitch).putSimple(key,newValue);
					}
				}
			}
		}
    }

	@Override
	public List<Map<String, String>> viewOpenOrClose() throws Exception {
		List<Map<String, String>> formMapList = new ArrayList<Map<String, String>>();
		String preName = "";
		for (int i = 0;; i++) {
			preName = "fdDetail." + i + ".";
			Map<String, String> valMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" eopBasedataSwitch.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<EopBasedataSwitch> result = this.findList(hqlInfo);
			if (ArrayUtil.isEmpty(result)) {
				break;
			}
			for (EopBasedataSwitch openOrClose : result) {
				if(openOrClose.getFdProperty().endsWith("Date")){ //时间，不是时间请不要以Date结尾
					String pattern = ResourceUtil.getString("date.format.date", ResourceUtil.getLocaleByUser());
					String tempStr=openOrClose.getFdValue();
					if(StringUtil.isNull(tempStr)){
						continue;
					}
					Date tempDate=DateUtil.convertStringToDate(tempStr, "yyyy-MM-dd");
					tempStr=DateUtil.convertDateToString(tempDate,pattern);
					valMap.put(openOrClose.getFdProperty().replace(preName, ""), tempStr);
				}else{
					valMap.put(openOrClose.getFdProperty().replace(preName, ""), openOrClose.getFdValue());
				}
			}
			formMapList.add(valMap);
		}
		return formMapList;
	}
	
	@Override
	public List<Map<String, String>> budgetRuleDetail() throws Exception {
		List<Map<String, String>> formMapList = new ArrayList<Map<String, String>>();
		String preName = "";
		for (int i = 0;; i++) {
			preName = "fdBudgetRuleDetail." + i + ".";
			Map<String, String> valMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" eopBasedataSwitch.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<EopBasedataSwitch> result = this.findList(hqlInfo);
			if (ArrayUtil.isEmpty(result)) {
				break;
			}
			for (EopBasedataSwitch openOrClose : result) {
				valMap.put(openOrClose.getFdProperty().replace(preName, ""), openOrClose.getFdValue());
			}
			formMapList.add(valMap);
		}
		return formMapList;
	}
	
	@Override
	public List<Map<String, String>> deduBdgetDetail() throws Exception {
		List<Map<String, String>> formMapList = new ArrayList<Map<String, String>>();
		String preName = "";
		for (int i = 0;; i++) {
			preName = "fdDeduBudgetRuleDetail." + i + ".";
			Map<String, String> valMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" eopBasedataSwitch.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<EopBasedataSwitch> result = this.findList(hqlInfo);
			if (ArrayUtil.isEmpty(result)) {
				break;
			}
			for (EopBasedataSwitch openOrClose : result) {
				valMap.put(openOrClose.getFdProperty().replace(preName, ""), openOrClose.getFdValue());
			}
			formMapList.add(valMap);
		}
		return formMapList;
	}
	
	@Override
	public List<Map<String, String>> deduProvisionDetail() throws Exception {
		List<Map<String, String>> formMapList = new ArrayList<Map<String, String>>();
		String preName = "";
		for (int i = 0;; i++) {
			preName = "fdDeduProvisionRuleDetail." + i + ".";
			Map<String, String> valMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" eopBasedataSwitch.fdProperty like :fdProperty");
			hqlInfo.setParameter("fdProperty", preName + "%");
			List<EopBasedataSwitch> result = this.findList(hqlInfo);
			if (ArrayUtil.isEmpty(result)) {
				break;
			}
			for (EopBasedataSwitch openOrClose : result) {
				valMap.put(openOrClose.getFdProperty().replace(preName, ""), openOrClose.getFdValue());
			}
			formMapList.add(valMap);
		}
		return formMapList;
	}

	@Override
	public JSONObject bankOpenOrClose() throws Exception {
		JSONObject obj=new JSONObject();
		String sql="select eopBasedataSwitch.fdValue from com.landray.kmss.eop.basedata.model.EopBasedataSwitch eopBasedataSwitch where eopBasedataSwitch.fdProperty=:fdProperty ";
		Query query = getBaseDao().getHibernateSession().createQuery(sql);
		query.setParameter("fdProperty", "fdUseBank");
		List list = query.list();
		if(list.isEmpty()||(!ArrayUtil.isEmpty(list)&&list.get(0)==null)){
			obj.put("result", "");
		}else{
			String fdValue =    list.get(0)+"";
			obj.put("result", fdValue);
		}
		return obj;
		
	}
}
