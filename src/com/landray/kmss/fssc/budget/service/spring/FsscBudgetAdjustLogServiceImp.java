package com.landray.kmss.fssc.budget.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustLog;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustLogService;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class FsscBudgetAdjustLogServiceImp extends ExtendDataServiceImp implements IFsscBudgetAdjustLogService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetAdjustLog) {
            FsscBudgetAdjustLog fsscBudgetAdjustLog = (FsscBudgetAdjustLog) model;
        }
        return model;
    }
    
    protected ISysOrgPersonService sysOrgPersonService;
    
    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
    	if(sysOrgPersonService==null){
    		sysOrgPersonService=(ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
    	}
		this.sysOrgPersonService = sysOrgPersonService;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetAdjustLog fsscBudgetAdjustLog = new FsscBudgetAdjustLog();
        fsscBudgetAdjustLog.setDocCreateTime(new Date());
        fsscBudgetAdjustLog.setDocCreator(UserUtil.getUser());
        FsscBudgetUtil.initModelFromRequest(fsscBudgetAdjustLog, requestContext);
        return fsscBudgetAdjustLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetAdjustLog fsscBudgetAdjustLog = (FsscBudgetAdjustLog) model;
    }

    @Override
    public List<FsscBudgetAdjustLog> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetAdjustLog.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public void addAdjust(JSONObject logJson) throws Exception {
		FsscBudgetAdjustLog adjustLog=new FsscBudgetAdjustLog();
		adjustLog.setDocCreateTime(new Date());
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder("sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		String where="";
		if(logJson.containsKey("fdPersonCode")){//异构系统人员编号
			where=StringUtil.linkString(where, " or ", "sysOrgPerson.fdNo=:fdPersonCode");
			hqlInfo.setParameter("fdPersonCode", logJson.get("fdPersonCode"));
		}
		if(logJson.containsKey("fdPersonId")){//异构系统人员ID
			where=StringUtil.linkString(where, " or ", "sysOrgPerson.fdId=:fdPersonId");
			hqlInfo.setParameter("fdPersonId", logJson.get("fdPersonId"));
		}
		if(StringUtil.isNotNull(where)){
			whereBlock.append(" and (").append(where).append(")");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<SysOrgPerson> orgList=sysOrgPersonService.findList(hqlInfo);
		adjustLog.setDocCreator(ArrayUtil.isEmpty(orgList)?(UserUtil.getUser()):orgList.get(0));
		adjustLog.setFdAmount(logJson.containsKey("fdMoney")?logJson.getDouble("fdMoney"):null);
		adjustLog.setFdBudgetId(logJson.containsKey("fdBudgetId")?logJson.getString("fdBudgetId"):null);
		adjustLog.setFdDesc(logJson.containsKey("fdDesc")?logJson.getString("fdDesc"):null);
		adjustLog.setFdModelId(logJson.containsKey("fdModelId")?logJson.getString("fdModelId"):null);
		adjustLog.setFdModelName(logJson.containsKey("fdModelName")?logJson.getString("fdModelName"):null);
		this.getBaseDao().add(adjustLog);
	}
}
