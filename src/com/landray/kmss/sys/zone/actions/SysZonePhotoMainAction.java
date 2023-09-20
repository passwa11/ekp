package com.landray.kmss.sys.zone.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.zone.model.SysZonePhotoMain;
import com.landray.kmss.sys.zone.service.ISysZonePhotoMainService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 照片墙 Action
 * 
 * @author
 * @version 1.0 2014-09-11
 */
public class SysZonePhotoMainAction extends ExtendAction {
	protected ISysZonePhotoMainService sysZonePhotoMainService;

	@Override
	protected ISysZonePhotoMainService getServiceImp(HttpServletRequest request) {
		if (sysZonePhotoMainService == null) {
			sysZonePhotoMainService = (ISysZonePhotoMainService) getBean("sysZonePhotoMainService");
		}
		return sysZonePhotoMainService;
	}

	public ActionForward loadMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-loadMap", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdKey = request.getParameter("fdKey");
		String defaultPath = "";
		if ("spic".equals(fdKey)) {
			// 默认照片路径
			defaultPath = "/sys/zone/sys_zone_photo_template/resource/Ypage_default_bg.gif";
		} else if ("html".equals(fdKey)) {
			defaultPath = null;
		}
		SysZonePhotoMain model = null;
		ActionForward actionForward = new ActionForward();
		actionForward.setPath(defaultPath);
		actionForward.setRedirect(true);
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {// 特定照片墙
				model = (SysZonePhotoMain) this.getServiceImp(request)
						.findByPrimaryKey(fdId);
			} else {
				String sourceId = request.getParameter("sourceId");
				String templateId = request.getParameter("templateId");
				if (StringUtil.isNotNull(sourceId)) {
					HQLInfo hqlInfo = new HQLInfo();
					String whereBlock = "sysZonePhotoMain.fdSourceId =:fdSourceId";
					hqlInfo.setParameter("fdSourceId", sourceId);
					if (StringUtil.isNotNull(templateId)) {
						whereBlock = StringUtil
								.linkString(whereBlock, " and ",
										" sysZonePhotoMain.fdTemplateId =:fdTemplateId");
						hqlInfo.setParameter("fdTemplateId", templateId);
					} else {
						whereBlock = StringUtil.linkString(whereBlock, " and ",
								" sysZonePhotoMain.fdIsDefault =:fdIsDefault");
						hqlInfo.setParameter("fdIsDefault", true);
					}
					hqlInfo.setWhereBlock(whereBlock);
					Object obj = getServiceImp(request).findFirstOne(hqlInfo);
					if (obj!=null) {
						model = (SysZonePhotoMain) obj;
					}
				}
			}
			if (model != null) {
				actionForward.setPath(getServiceImp(request).getMapPath(model,
						fdKey));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-loadMap", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if(StringUtil.isNotNull(actionForward.getPath())) {
				return actionForward;
			} else {
                return null;
            }
		}
	}
}
