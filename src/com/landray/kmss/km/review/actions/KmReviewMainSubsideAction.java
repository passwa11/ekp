package com.landray.kmss.km.review.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory;
import com.landray.kmss.kms.multidoc.interfaces.IFileDataService;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.property.model.SysPropertyReference;
import com.landray.kmss.sys.property.model.SysPropertyTemplate;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmReviewMainSubsideAction extends ExtendAction {
	private IKmsMultidocSubsideService kmsMultidocSubsideService;

	@Override
	protected IKmsMultidocSubsideService getServiceImp(HttpServletRequest request) {
		if (kmsMultidocSubsideService == null) {
			kmsMultidocSubsideService = (IKmsMultidocSubsideService) getBean("kmsMultidocSubsideService");
		}
		return kmsMultidocSubsideService;
	}

	/**
	 * 选择框列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTypeFields(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String type = request.getParameter("type");
			String modelName = request.getParameter("modelName");
			String templateService = request.getParameter("templateService");
			String templateId = request.getParameter("templateId");
			Map<String, String> options = getServiceImp(request).getOptions(modelName, type, templateService,
					templateId);
			JSONObject obj = JSONObject.fromObject(options);
			request.setAttribute("lui-source", obj);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (!messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request, response);
		}
		return null;
	}

	/**
	 * 自动沉淀文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setCharacterEncoding("utf-8");
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNull(fdId)) {
				throw new NoRecordException();
			}
			String signKey = MD5Util.getMD5String(fdId);
			KmssCache cache = new KmssCache(KmsMultidocSubside.class);
			String signJsonStr = (String) cache.get(signKey);
			if (StringUtil.isNull(signJsonStr)) {
				throw new UnexpectedRequestException();
			}
			com.alibaba.fastjson.JSONObject sign = (com.alibaba.fastjson.JSONObject) JSON.parse(signJsonStr);
			String serviceName = sign.getString("serviceName");
			IFileDataService service = (IFileDataService) SpringBeanUtil.getBean(serviceName);
			service.addSubsideFileMainDoc(request, fdId, null);
			request.setAttribute("lui-source", null);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		return getActionForward("lui-source", mapping, form, request, response);
	}


	/**
	 * 沉淀模板页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printSubsideDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			String signKey = MD5Util.getMD5String(fdId);
			KmssCache cache = new KmssCache(KmsMultidocSubside.class);
			String signJsonStr = (String) cache.get(signKey);
			if (StringUtil.isNull(signJsonStr)) {
				throw new UnexpectedRequestException();
			}
			request.getRequestDispatcher("/km/review/km_review_main/kmReviewMain.do?method=printSubsideDoc")
					.forward(request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("filePrint", mapping, form, request, response);
        }
	}

	/**
	 * 获取扩展数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExtendData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("fdId");
			if (StringUtil.isNull(categoryId)) {
				throw new IllegalArgumentException();
			}
			KmsKnowledgeCategory category = (KmsKnowledgeCategory) getServiceImp(request).findByPrimaryKey(categoryId);
			SysPropertyTemplate sp = category.getSysPropertyTemplate();
			JSONArray jsonArray = new JSONArray();
			if (sp != null && sp.getFdReferences() != null) {
				List<SysPropertyReference> references = sp.getFdReferences();
				for (SysPropertyReference sysPropertyReference : references) {
					JSONObject obj = new JSONObject();
					obj.put("fdField", sysPropertyReference.getFdDefine().getFdStructureName());
					obj.put("fdDisplayName", sysPropertyReference.getFdDisplayName());
					obj.put("notNull", sysPropertyReference.getFdIsNotNull());
					obj.put("fdType", sysPropertyReference.getFdDefine().getFdType());
					jsonArray.add(obj);
				}
			}
			request.setAttribute("lui-source", jsonArray);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (!messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request, response);
		}
		return null;
	}
}
