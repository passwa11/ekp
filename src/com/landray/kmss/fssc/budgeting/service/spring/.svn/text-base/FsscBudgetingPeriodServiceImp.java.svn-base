package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingPeriod;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingPeriodService;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class FsscBudgetingPeriodServiceImp extends ExtendDataServiceImp implements IFsscBudgetingPeriodService,IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingPeriod) {
            FsscBudgetingPeriod fsscBudgetingPeriod = (FsscBudgetingPeriod) model;
            fsscBudgetingPeriod.setDocAlterTime(new Date());
            fsscBudgetingPeriod.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingPeriod fsscBudgetingPeriod = new FsscBudgetingPeriod();
        fsscBudgetingPeriod.setFdIsAvailable(Boolean.valueOf("true"));
        fsscBudgetingPeriod.setDocCreateTime(new Date());
        fsscBudgetingPeriod.setDocAlterTime(new Date());
        fsscBudgetingPeriod.setDocCreator(UserUtil.getUser());
        fsscBudgetingPeriod.setDocAlteror(UserUtil.getUser());
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingPeriod, requestContext);
        return fsscBudgetingPeriod;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingPeriod fsscBudgetingPeriod = (FsscBudgetingPeriod) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
		FsscBudgetingPeriod period=(FsscBudgetingPeriod) modelObj;
		if(period.getFdIsAvailable()){
			//若是当前提交期间是有效的，将其他的全部置为无效
			updateAvailable(period.getFdId());
		}
		return super.add(period);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		FsscBudgetingPeriod period=(FsscBudgetingPeriod) modelObj;
		if(period.getFdIsAvailable()){
			//若是当前提交期间是有效的，将其他的全部置为无效
			updateAvailable(period.getFdId());
		}
		super.update(period);
	}
	
	public void updateAvailable(String currentId) throws Exception{
		this.getBaseDao().getHibernateSession()
		.createQuery("update FsscBudgetingPeriod t set t.fdIsAvailable=:fdIsAvailable where fdId<>:currentId")
		.setParameter("fdIsAvailable", false).setParameter("currentId", currentId).executeUpdate();
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String fdStartPeriod=requestInfo.getParameter("fdStartPeriod");
		String fdEndPeriod=requestInfo.getParameter("fdEndPeriod");
		String fdId=requestInfo.getParameter("fdId");
		int start=Integer.parseInt(fdStartPeriod);
		int end=Integer.parseInt(fdStartPeriod);
		if(StringUtil.isNotNull(fdStartPeriod)&&StringUtil.isNotNull(fdEndPeriod)){
			String fdPeriod=fdStartPeriod.substring(0,fdStartPeriod.length()-2);
			HQLInfo hqlInfo=new HQLInfo();
			StringBuilder whereBlock=new StringBuilder();
			whereBlock.append("(fsscBudgetingPeriod.fdStartPeriod like :pre or fsscBudgetingPeriod.fdStartPeriod like :curr or fsscBudgetingPeriod.fdStartPeriod like :next)");
			whereBlock.append(" and fsscBudgetingPeriod.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			if(StringUtil.isNotNull(fdId)){
				whereBlock.append(" and fsscBudgetingPeriod.fdId!=:fdId");
				hqlInfo.setParameter("fdId", fdId);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("pre", (Integer.parseInt(fdPeriod)-1)+"%");
			hqlInfo.setParameter("curr", fdPeriod+"%");
			hqlInfo.setParameter("next", (Integer.parseInt(fdPeriod)+1)+"%");
			//只校验当前期间的上一年、本年、和下一年是否有交集，因为前端限制12个月期间跨度
			List<FsscBudgetingPeriod> periodList=this.findList(hqlInfo);
			String checkStatus="true";  //默认校验通过
			for (FsscBudgetingPeriod period : periodList) {
				int oldStart=Integer.parseInt(period.getFdStartPeriod().replaceAll("-", ""));
				int oldEnd=Integer.parseInt(period.getFdEndPeriod().replaceAll("-", ""));
				if((oldStart>=start&&oldStart<=end)||(oldEnd>=start&&oldEnd<=end)){
					//开始、结束期间有落在本次期间内，说明期间有交集，校验不通过
					checkStatus="false";
					break;
				}
			}
			Map<String,String> map=new HashMap<String,String>();
			map.put("checkStatus", checkStatus);
			rtnList.add(map);
		}
		return rtnList;
	}
}
