package com.landray.kmss.sys.zone.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.zone.service.ISysZonePrivateChangeService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysZonePrivateConfigAction extends SysAppConfigAction {
    /** 用于清除数据库的索引配置记录 */
    private ISysZonePrivateChangeService sysZonePrivateChangeService ;
    
    public ISysZonePrivateChangeService getSysZonePrivateChangeService() 
    {
        if (sysZonePrivateChangeService == null) 
        {
            sysZonePrivateChangeService = (ISysZonePrivateChangeService) getBean("sysZonePrivateChangeService");
        }
        return sysZonePrivateChangeService;
    }
    
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String modelName = request.getParameter("modelName");
			if (!"com.landray.kmss.sys.zone.model.SysZonePrivateConfig".equals(modelName) ) {
                throw new NoRecordException();
            }
			SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
			Map configMap = appConfigForm.getMap();
			if(configMap.get("isContactPrivate") == null){
				configMap.put("isContactPrivate", "0");
			}
			if(configMap.get("isDepInfoPrivate") == null){
				configMap.put("isDepInfoPrivate", "0");
			}
			if(configMap.get("isRelationshipPrivate") == null){
				configMap.put("isRelationshipPrivate", "0");
			}
			if(configMap.get("isWorkmatePrivate") == null){
				configMap.put("isWorkmatePrivate", "0");
			} 
			if (configMap.get("isQrCodePrivate") == null) {
				configMap.put("isQrCodePrivate", "0");
			}
			String autoclose = request.getParameter("autoclose");
			if (StringUtil.isNotNull(autoclose) && "false".equals(autoclose)) {
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			}
			BaseAppConfig appConfig = (BaseAppConfig) ClassUtils.forName(modelName)
					.newInstance();
			appConfig.getDataMap().putAll(appConfigForm.getMap());
			appConfig.save(); 
			getSysZonePrivateChangeService().deleteSearchConfig("com.landray.kmss.sys.organization.model.SysOrgPerson,com.landray.kmss.sys.zone.model.SysZonePersonInfo,");
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}
