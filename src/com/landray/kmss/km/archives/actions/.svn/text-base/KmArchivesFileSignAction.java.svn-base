package com.landray.kmss.km.archives.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.km.archives.depend.IAutoFileDataServiceApi;
import com.landray.kmss.km.archives.interfaces.IAutoFileDataService;
import com.landray.kmss.km.archives.service.IKmArchivesSignService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程结束自动归档
 * @author 简建红
 *
 */
public class KmArchivesFileSignAction extends ExtendAction {

	private IKmArchivesSignService kmArchivesSignService;

	@Override
    public IKmArchivesSignService
			getServiceImp(HttpServletRequest request) {
		if (kmArchivesSignService == null) {
			kmArchivesSignService = (IKmArchivesSignService) getBean(
					"kmArchivesSignService");
        }
		return kmArchivesSignService;
    }

	
	public void attributArchives(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String serviceName = null;
		try {

			String fdModelId = request.getParameter("fdModelId");
			String mainModelName = request.getParameter("mainModelName");
			if (StringUtil.isNotNull(mainModelName)) {
				String modelClassName = mainModelName.substring(mainModelName.lastIndexOf(".") + 1);
				String firstChar = modelClassName.substring(0, 1).toLowerCase();
				if (modelClassName.indexOf("$",2) > -1) {
					mainModelName = firstChar + modelClassName.substring(1, modelClassName.indexOf("$"));
				} else {
					mainModelName = firstChar + modelClassName.substring(1);
				}
				serviceName = mainModelName + "Service";
			}
			//获取签名
			String sign = getServiceImp(request).addSignForArchives(fdModelId, mainModelName);
			//拼接 [fdid,sign]
			fdModelId = fdModelId+","+sign;

			if (null != serviceName) {
				Object bean = getBean(serviceName);
				//原逻辑
				if(bean instanceof IAutoFileDataService) {
					IAutoFileDataService service = (IAutoFileDataService) bean;
					service.addAutoFileMainDoc(fdModelId, null);
				} else {
					//解耦逻辑
					IAutoFileDataServiceApi apiProxy = ModuleCenter.getDeclareApiProxy(IAutoFileDataServiceApi.class, serviceName);
					if (apiProxy!=null){
						apiProxy.addAutoFileMainDoc(fdModelId,null);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
