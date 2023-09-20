package com.landray.kmss.sys.portal.actions.anonym;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.sys.portal.model.SysPortalLinkDetail;
import com.landray.kmss.sys.portal.service.ISysPortalLinkService;
import com.landray.kmss.sys.portal.util.SysPortalConfig;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 匿名快捷方式 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalLinkAnonymAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ISysPortalLinkService sysPortalLinkService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalLinkService == null) {
            sysPortalLinkService = (ISysPortalLinkService) getBean("sysPortalLinkService");
        }
		return sysPortalLinkService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			JSONArray array = new JSONArray();
			String fdId = request.getParameter("fdId");
			String fdType = request.getParameter("fdType");
			SysPortalLink link = null;
			if(StringUtil.isNotNull(fdId)){
				link = (SysPortalLink) getServiceImp(request).findByPrimaryKey(fdId);
			}else{
				if(StringUtil.isNotNull(fdType)){
					HQLInfo info = new HQLInfo();
					info.setWhereBlock(" fdAnonymous = :fdAnonymous and fdType =:fdType ");
					info.setParameter("fdType", fdType);
					info.setParameter("fdAnonymous", Boolean.TRUE);
					Object obj = getServiceImp(request).findFirstOne(info);
					if (obj != null) {
						link = (SysPortalLink) obj;
					}
				}
			}
			
			if (null != link&&(null==link.getFdAnonymous()?false:link.getFdAnonymous())) {
				List<SysPortalLinkDetail> links = link.getFdLinks();
				if (links != null && !links.isEmpty()) {
					if ("1".equals(fdType)) {
						// 常用链接
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if(StringUtil.isNotNull(links.get(i).getFdSysLink())){
								String xid = links.get(i).getFdSysLink();
								if(xid.indexOf(SysUiConstant.SEPARATOR)>0){
									String server = xid.substring(0,xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server)+links.get(i).getFdUrl());
								}else{
									json.put("href", links.get(i).getFdUrl());
								}
							}else{
								json.put("href", links.get(i).getFdUrl());
							}
							array.add(json);
						}
					} else if ("2".equals(fdType)) {
						// 快捷方式
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if(StringUtil.isNotNull(links.get(i).getFdSysLink())){
								String xid = links.get(i).getFdSysLink();
								if(xid.indexOf(SysUiConstant.SEPARATOR)>0){
									String server = xid.substring(0,xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server)+links.get(i).getFdUrl());
								}else{
									json.put("href", links.get(i).getFdUrl());
								}
							}else{
								json.put("href", links.get(i).getFdUrl());
							}
							//json.put("icon", links.get(i).getFdIcon());
							//如果icon为空则是自定义的图片
							if(StringUtil.isNull(links.get(i).getFdIcon())){
								json.put("img", links.get(i).getFdImg());
							}else {
								json.put("icon", links.get(i).getFdIcon());
							}
							array.add(json);
						}
					}
				}
			}
			request.setAttribute("lui-source", array);
			return getActionForward("lui-source", mapping, form, request, response);
		} catch (Exception e) {
			KmssMessages messages = new KmssMessages();
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		}
	}
}
