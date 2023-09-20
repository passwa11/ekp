package com.landray.kmss.sys.portal.actions.anonym;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalNavService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 匿名系统导航 Action
 */
public class SysPortalNavAnonymAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ISysPortalNavService sysPortalNavService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalNavService == null) {
            sysPortalNavService = (ISysPortalNavService) getBean("sysPortalNavService");
        }
		return sysPortalNavService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		JSONArray array = new JSONArray();
		SysPortalNav nav =null;
		if (StringUtil.isNotNull(fdId)) {
			nav = (SysPortalNav) getServiceImp(request).findByPrimaryKey(fdId);
		}else{
			HQLInfo info = new HQLInfo();
			info.setWhereBlock(" fdAnonymous = :fdAnonymous ");
			info.setParameter("fdAnonymous", Boolean.TRUE);
			Object obj = getServiceImp(request).findFirstOne(info);
			if (obj != null) {
				nav = (SysPortalNav) obj;
			}
		}
		
		if(null!=nav&&(null==nav.getFdAnonymous()?false:nav.getFdAnonymous())){
			array = JSONArray.fromObject(nav.getFdContent());
		}
		KMSSUser kmssUser = UserUtil.getKMSSUser();
		String key = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();
		
		array = getLangJson(array ,key);
		
		//在这里统一处理系统导航的多语言转换
		request.setAttribute("lui-source", array);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	//递归获得当前语言的系统导航text
	private JSONArray getLangJson(JSONArray array , String key){
		
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			if(obj.containsKey(key)){
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}
			
			if(obj.containsKey("children")){
				JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray,key);
			}
		}
		
		return array;
	}
	
	public ActionForward getPortletNavByPortalPageId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String portalPageId = request.getParameter("portalPageId");
		JSONObject resultObj = new JSONObject();
		if (StringUtil.isNotNull(portalPageId)) {
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil
					.getBean("sysPortalMainService");
			SysPortalMainPage portalMainPage = service
					.getAnonymousPortalPageByPageId(portalPageId);

			HQLInfo hql = new HQLInfo();
			StringBuffer whereBlockSb = new StringBuffer();
			whereBlockSb.append("sysPortalNav.fdPageId=:fdPageId ");

			hql.setWhereBlock(whereBlockSb.toString());
			hql.setParameter("fdPageId",
					portalMainPage.getSysPortalPage().getFdId());

			Object obj = getServiceImp(request).findFirstOne(hql);
			SysPortalNav navModel = null;
			if (obj!=null) {
				navModel = (SysPortalNav)obj;
				resultObj.put("fdId", navModel.getFdId());
				resultObj.put("fdName", navModel.getFdName());
				resultObj.put("fdContent", navModel.getFdContent());
			}
		}
		request.setAttribute("lui-source", resultObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	
	
	

}
