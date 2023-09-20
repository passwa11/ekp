package com.landray.kmss.sys.attachment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.service.ISysAttImageCropService;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysAttImageCropAction extends ExtendAction {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysAttImageCropService sysAttImageCropService;
	
	@Override
	protected ISysAttImageCropService getServiceImp(HttpServletRequest request) {
		if (null == sysAttImageCropService) {
			sysAttImageCropService = (ISysAttImageCropService) getBean("sysAttImageCropService");
		}
		return sysAttImageCropService;
	}

	public ActionForward addCrop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addCrop", true, getClass());
		try {
			response.setCharacterEncoding("utf-8");
			String imgInfo =getServiceImp(request).addCrop((IExtendForm) form).toString();
			if(StringUtil.isNotNull(imgInfo)) {
				response.getWriter().append(imgInfo);
				UserOperHelper.setOperSuccess(true);
			}
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			logger.error("att image crop error!", e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-addCrop", false, getClass());
		return null;
	}
	
	public ActionForward cancelCrop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-cancelCrop", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String attId = request.getParameter("attId");
			if (StringUtil.isNotNull(attId)) {
				getServiceImp(request).updateCancel(attId);
				request.setAttribute("lui-source", JSONObject.fromObject("{'scuccess' : 'true'}"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-cancelCrop", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	public ActionForward listCrop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listCrop", true, getClass());
		try {
			String modelId = request.getParameter("fdModelId");
			String modelName = request.getParameter("fdModelName");
			JSONArray array = new JSONArray();
			if (!StringUtil.isNull(modelId)) {
				String[] keys =  abtainCropKeys(request);
				array = getServiceImp(request).findByKeys(modelName, modelId, keys);
				if(array.isEmpty()) {
					//兼容旧的key值
					array = getServiceImp(request)
							.findByKeys(modelName, modelId, new String[]{"zonePersonInfo"});
					if(!array.isEmpty()) {
						JSONObject obj  = (JSONObject)array.remove(0);
						String id = obj.get("value").toString();
						for(String key : keys) {
							JSONObject json = new JSONObject();
							json.accumulate("key", StringEscapeUtils.escapeHtml(key));
							json.accumulate("value", id);
							array.add(json);
						}
					}
				}
			}
			response.setHeader("content-type", "application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().append(array.toString());
		} catch (Exception e) {
			logger.error("find image crop error!", e);
		}
		TimeCounter.logCurrentTime("Action-listCrop", false, getClass());
		return null;
	}

	private String[] abtainCropKeys(HttpServletRequest request) {
		String fdKey = request.getParameter("fdKey");
		String fdCropKeys = request.getParameter("fdCropKeys");
		String[] tempKeys = null;
		if (StringUtil.isNull(fdCropKeys)) {
			tempKeys = ImageCropUtil.CROP_KEYS;
		} else {
			tempKeys = fdCropKeys.split(";");
		}
		for (int i = 0; i < tempKeys.length; i++) {
			tempKeys[i] = fdKey + tempKeys[i];
		}
		return tempKeys;
	}
	
	public ActionForward imageInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-imageInfo", true, getClass());
		try {
			String attId = request.getParameter("attId");
			response.setCharacterEncoding("utf-8");
			response.getWriter().append(getServiceImp(request).obtainImageInfo(attId).toString());
		} catch (Exception e) {
			logger.error("get width and height of image  error!", e);
		}
		TimeCounter.logCurrentTime("Action-imageInfo", false, getClass());
		return null;
	}
}
