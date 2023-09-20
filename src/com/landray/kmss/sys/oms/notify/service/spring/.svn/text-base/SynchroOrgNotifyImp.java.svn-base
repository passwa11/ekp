package com.landray.kmss.sys.oms.notify.service.spring;

import java.util.HashMap;
import java.util.Set;

import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.oms.notify.model.OrgSynchroNotifyTemplateEmpty;
import com.landray.kmss.sys.oms.notify.service.ISynchroOrgNotify;

/**
 * 创建日期 2006-12-25
 * 
 * @author 吴兵 组织机构同步通知
 */
public class SynchroOrgNotifyImp implements ISynchroOrgNotify{

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}
	
	/**
	 *同步通知 
	 */
	@Override
	public void send(ISysNotifyModel mainModel, NotifyContext notifyContext, HashMap replaceMap)throws Exception{
		NotifyReplace notifyReplace = new NotifyReplace();
		if (replaceMap != null) {
			for (String key : (Set<String>) replaceMap.keySet()) {
				notifyReplace.addReplaceText(key,
						replaceMap.get(key).toString());
			}
		}
		sysNotifyMainCoreService.sendNotify(mainModel, notifyContext,
				notifyReplace);
	}
	
	@Override
	public NotifyContext getSyncExceptionNotifyContext()throws Exception{
		NotifyContext notifyContext = sysNotifyMainCoreService.getContext(
				new OrgSynchroNotifyTemplateEmpty(), "orgSynchroMessageSetting");
		return notifyContext;
	}

	@Override
	public NotifyContext getCreateAccountNotifyContext()throws Exception{
		NotifyContext notifyContext = sysNotifyMainCoreService.getContext(
				new OrgSynchroNotifyTemplateEmpty(), "createAccountMessageSetting");
		return notifyContext;
	}

	@Override
	public NotifyContext getDeleteAccountNotifyContext()throws Exception{
		NotifyContext notifyContext = sysNotifyMainCoreService.getContext(
				new OrgSynchroNotifyTemplateEmpty(), "deleteAccountMessageSetting");
		return notifyContext;
	}
	
	@Override
	public HashMap getReplaceMap(String notifyPersonName) {
		if(notifyPersonName==null) {
            return null;
        }
		HashMap replaceMap = new HashMap();
		replaceMap.put("sys-oms-notify:orgSynchroNotifyTemplateEmpty.fdNotifyPersonName",
				notifyPersonName);
		return replaceMap;
	}	
}
