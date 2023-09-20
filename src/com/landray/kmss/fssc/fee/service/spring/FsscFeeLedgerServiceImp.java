package com.landray.kmss.fssc.fee.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.service.IFsscFeeLedgerService;
import com.landray.kmss.fssc.fee.util.FsscFeeUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class FsscFeeLedgerServiceImp extends ExtendDataServiceImp implements IFsscFeeLedgerService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscFeeLedger) {
            FsscFeeLedger fsscFeeLedger = (FsscFeeLedger) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscFeeLedger fsscFeeLedger = new FsscFeeLedger();
        FsscFeeUtil.initModelFromRequest(fsscFeeLedger, requestContext);
        return fsscFeeLedger;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscFeeLedger fsscFeeLedger = (FsscFeeLedger) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    @Override
    public JSONObject getLedgerMoney(String fdLedgerId, String fdModelId){
    	JSONObject rtn = new JSONObject();
    	String hql = "select sum(ledger.fdBudgetMoney),ledger.fdType from "+FsscFeeLedger.class.getName()+" ledger where ledger.fdLedgerId=:fdLedgerId and ledger.fdModelId<>:fdModelId group by ledger.fdType";
    	Query query = getBaseDao().getHibernateSession().createQuery(hql);
    	query.setParameter("fdLedgerId", fdLedgerId);
    	query.setParameter("fdModelId", fdModelId);
    	List<Object[]> list = query.list();
    	Double fdTotalMoney = 0d,fdUsingMoney=0d,fdUsedMoney=0d;
    	for(Object[] data:list){
    		if(FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_INIT.equals(data[1])){
    			fdTotalMoney = ((Number)data[0]).doubleValue();
    		}else if(FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USING.equals(data[1])){
    			fdUsingMoney= ((Number)data[0]).doubleValue();
    		}else if(FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USED.equals(data[1])){
    			fdUsedMoney= ((Number)data[0]).doubleValue();
    		}
    	}
    	rtn.put("fdTotalMoney", fdTotalMoney);
    	rtn.put("fdUsingMoney", fdUsingMoney);
    	rtn.put("fdUsedMoney", fdUsedMoney);
    	rtn.put("fdUsableMoney", FsscNumberUtil.getSubtraction(fdTotalMoney, FsscNumberUtil.getAddition(fdUsingMoney, fdUsedMoney)));
    	return rtn;
    }
    
    @Override
    public Map<String, Map<String,String>> processListData(List<FsscFeeLedger> dataList)
			throws Exception {
		Map<String, Map<String,String>> rtnMap=new HashMap<String, Map<String,String>>();
		StringBuilder hql=new StringBuilder();
		Map<String,String> valMap=new HashMap<String,String>();
		for (FsscFeeLedger execute : dataList) {
			valMap=new HashMap<String,String>();
			String fdModelName=execute.getFdModelName();
			hql=new StringBuilder();
			hql.append(" select t.docNumber,t.docSubject,t.fdId from "+fdModelName);
			hql.append(" t where t.fdId=:fdModelId");
			List<Object[]> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
					.setParameter("fdModelId", execute.getFdModelId()).list();
			for(int i=0,len=result.size();i<len;i++){
				Object[] obj=result.get(i);
				valMap.put("docNumber", String.valueOf(obj[0]));
				valMap.put("docSubject", String.valueOf(obj[1]));
				if(StringUtil.isNotNull(fdModelName)){
					SysDictModel model=SysDataDict.getInstance().getModel(fdModelName);
					String messageKey=model.getMessageKey();
					if(StringUtil.isNotNull(messageKey)){
						valMap.put("fdModelName", ResourceUtil.getString(messageKey.split(":")[1], messageKey.split(":")[0]));
					}
					valMap.put("fdUrl", model.getUrl().replace("${fdId}", String.valueOf(obj[2])));
				}
			}
			valMap.put("fdType", ResourceUtil.getString("fsscFeeLedger.fdType."+execute.getFdType(),"fssc-fee"));
			rtnMap.put(execute.getFdId(), valMap);
		}
		return rtnMap;
	}
}
