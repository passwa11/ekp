package com.landray.kmss.sys.portal.actions.anonym;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.portal.model.SysPortalTree;
import com.landray.kmss.sys.portal.service.ISysPortalTreeService;
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
 * 匿名多级树菜单 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalTreeAnonymAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	protected ISysPortalTreeService sysPortalTreeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalTreeService == null) {
            sysPortalTreeService = (ISysPortalTreeService) getBean("sysPortalTreeService");
        }
		return sysPortalTreeService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");
		SysPortalTree tree = null;
		if(StringUtil.isNotNull(fdId)){
			tree = (SysPortalTree) getServiceImp(request).findByPrimaryKey(fdId);
		}else{
			if(StringUtil.isNotNull(fdType)){
				HQLInfo info = new HQLInfo();
				info.setWhereBlock(" fdAnonymous = :fdAnonymous and fdType =:fdType ");
				info.setParameter("fdAnonymous", Boolean.TRUE);
				info.setParameter("fdType", fdType);
				Object obj = getServiceImp(request).findFirstOne(info);
				if (obj != null) {
					tree = (SysPortalTree)obj;
				}
			}
		}
		JSONArray array = new JSONArray();
		if(null!=tree&&(null==tree.getFdAnonymous()?false:tree.getFdAnonymous())&&fdType.equals(tree.getFdType())){
			array = JSONArray.fromObject(tree.getFdContent());
		}

		KMSSUser kmssUser = UserUtil.getKMSSUser();
		String key = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();

		array = getLangJson(array, key);

		// 在这里统一处理系统导航的多语言转换

		request.setAttribute("lui-source", array);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	// 递归获得当前语言的系统导航text
	private JSONArray getLangJson(JSONArray array, String key) {

		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			if (obj.containsKey(key)) {
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}

			if (obj.containsKey("children")) {
				JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray, key);
			}
		}

		return array;
	}

}
