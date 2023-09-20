package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingOrg;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingOrgService;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class FsscBudgetingOrgServiceImp extends ExtendDataServiceImp implements IFsscBudgetingOrgService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingOrg) {
            FsscBudgetingOrg fsscBudgetingOrg = (FsscBudgetingOrg) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingOrg fsscBudgetingOrg = new FsscBudgetingOrg();
        fsscBudgetingOrg.setDocCreateTime(new Date());
        fsscBudgetingOrg.setDocCreator(UserUtil.getUser());
        List<FsscBudgetingOrg> mainList=this.findList(null, "fsscBudgetingOrg.docCreateTime  desc");
        if(!ArrayUtil.isEmpty(mainList)){
        	FsscBudgetingOrg main=mainList.get(0);
        	fsscBudgetingOrg.setFdName(main.getFdName());
        	List<SysOrgPerson> orgList=new ArrayList<SysOrgPerson>();
        	orgList.addAll(main.getFdOrgs());
        	fsscBudgetingOrg.setFdOrgs(orgList);
        	fsscBudgetingOrg.setFdNotifyType(main.getFdNotifyType());
        }
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingOrg, requestContext);
        // 根据admin.do配置显示对应的信息
        requestContext.setAttribute("todo", ResourceUtil.getKmssConfigString("kmss.notify.type.todo.enabled"));// 待办
        requestContext.setAttribute("email", ResourceUtil.getKmssConfigString("kmss.notify.type.email.enabled"));// 邮件
        requestContext.setAttribute("mobile", ResourceUtil.getKmssConfigString("kmss.notify.type.mobile.enabled"));// 短消息
        return fsscBudgetingOrg;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingOrg fsscBudgetingOrg = (FsscBudgetingOrg) model;
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
    	FsscBudgetingOrg fsscBudgetingOrg=(FsscBudgetingOrg) modelObj;
    	cancelTodo(null);
    	sendTodo(fsscBudgetingOrg);
		return super.add(fsscBudgetingOrg);
	}

    
    public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
    	 if (sysNotifyMainCoreService == null) {
             sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
         }
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

    /**
	 * 发送消息
	 * 
	 * @throws Exception
	 */

	private void sendTodo(FsscBudgetingOrg fsscBudgetingOrg) throws Exception {
		String fdTypes=fsscBudgetingOrg.getFdNotifyType();
		if(StringUtil.isNotNull(fdTypes)){
			String[] types=fdTypes.split(";");
			for(int i=0,len=types.length;i<len;i++){
				NotifyContext notifyContext = null;
				notifyContext = sysNotifyMainCoreService.getContext("fssc-budgeting:fsscBudgetingOrg.notify.message"+ fsscBudgetingOrg.getFdName());
				notifyContext.setNotifyType(types[i]);
				notifyContext.setKey("startBudgeting");
				// 设置发布通知人
				List<SysOrgPerson> targets=fsscBudgetingOrg.getFdOrgs();
				notifyContext.setNotifyTarget(targets);
				String context = fsscBudgetingOrg.getFdName();
				notifyContext.setSubject(ResourceUtil.getString("fsscBudgetingOrg.notify.message", "fssc-budgeting") + context);
				// 设计通知显示的类型
				notifyContext
						.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
				notifyContext.setLink("/fssc/budgeting/index.jsp");
				sysNotifyMainCoreService.send(fsscBudgetingOrg, notifyContext,
						getReplaceMap(fsscBudgetingOrg));
			}
		}
	}

	/**
	 * 功能描述:替换资源文件的方法
	 * 
	 * @param fsscBudgetingOrg 主文档model
	 * @return HashMap<String,String>
	 */
	private HashMap<String, String> getReplaceMap(FsscBudgetingOrg fsscBudgetingOrg) {
		HashMap<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("title", fsscBudgetingOrg.getFdName());
		return replaceMap;
	}
	
	/**
	 * 功能描述:取消待办
	 * 
	 */
	@Override
    public void cancelTodo(List persons) throws Exception {
		//查找上一个发待办的model
		List<FsscBudgetingOrg> budgetingOrgList=this.findList(null, "fsscBudgetingOrg.docCreateTime desc");
		if(!ArrayUtil.isEmpty(budgetingOrgList)){
			if(!ArrayUtil.isEmpty(persons)){
				sysNotifyMainCoreService.getTodoProvider().removePersons(budgetingOrgList.get(0),
						"startBudgeting", persons);
			}else{
				sysNotifyMainCoreService.getTodoProvider().remove(budgetingOrgList.get(0),
						"startBudgeting");
			}
		}
	}
}
