package com.landray.kmss.km.review.rest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.rest.util.MechanismHelper;
import com.landray.kmss.km.review.actions.KmReviewMainAction;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionForm;

/**
 * @ClassName: KmReviewMainController
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-10 13:23
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/km-review/kmReviewMain")
public class KmReviewMainController extends BaseController {

    private KmReviewMainAction action = new KmReviewMainAction();

    @ResponseBody
    @RequestMapping(value = "add")
    public RestResponse<?> add(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        KmReviewMainForm form = new KmReviewMainForm();
        action.add(emptyMapping, form, reqWrapper, response);
        KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) reqWrapper.getAttribute(getFormName(form));
        if (kmReviewMainForm != null) {
            form = kmReviewMainForm;
        }
		return result(reqWrapper, MechanismHelper.formToJson(form));
    }

    @ResponseBody
    @RequestMapping(value = "view")
    public RestResponse<?> view(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        KmReviewMainForm form = new KmReviewMainForm();
        action.view(emptyMapping, form, reqWrapper, response);
        KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) reqWrapper.getAttribute(getFormName(form));
        if (kmReviewMainForm != null) {
            form = kmReviewMainForm;
        }
		return result(reqWrapper, MechanismHelper.formToJson(form));
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public RestResponse<?> delete(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        reqWrapper.setMethod("GET");
        action.delete(emptyMapping, null, reqWrapper, response);
        return result(reqWrapper);
    }

    @ResponseBody
    @RequestMapping(value = "edit")
    public RestResponse<?> edit(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
		KmReviewMainForm form = new KmReviewMainForm();
		action.edit(emptyMapping, form, reqWrapper, response);
		KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) reqWrapper
				.getAttribute(getFormName(form));
		if (kmReviewMainForm != null) {
			form = kmReviewMainForm;
		}
		return result(reqWrapper, MechanismHelper.formToJson(form));
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public RestResponse<?> update(@RequestBody Map requestBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm extendForm = MechanismHelper.jsonToForm(requestBody,
				KmReviewMainForm.class);
		action.update(emptyMapping, extendForm, request, response);
		return result(request);
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public RestResponse<?> save(@RequestBody Map requestBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm extendForm = MechanismHelper.jsonToForm(requestBody,
				KmReviewMainForm.class);
		action.save(emptyMapping, extendForm, request, response);
		return result(request);
	}

	@ResponseBody
	@RequestMapping(value = "saveDraft")
	public RestResponse<?> saveDraft(@RequestBody Map requestBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm extendForm = MechanismHelper.jsonToForm(requestBody,
				KmReviewMainForm.class);
		action.saveDraft(emptyMapping, extendForm, request, response);
		return result(request);
	}

	@ResponseBody
	@RequestMapping(value = "updateDraft")
	public RestResponse<?> updateDraft(@RequestBody Map requestBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm extendForm = MechanismHelper.jsonToForm(requestBody,
				KmReviewMainForm.class);
		action.updateDraft(emptyMapping, extendForm, request, response);
		return result(request);
	}

	@ResponseBody
	@RequestMapping(value = "publishDraft")
	public RestResponse<?> publishDraft(@RequestBody Map requestBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm extendForm = MechanismHelper.jsonToForm(requestBody,
				KmReviewMainForm.class);
		action.publishDraft(emptyMapping, extendForm, request, response);
		return result(request);
	}

	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("deleteall")
	public RestResponse<String> deleteall(@RequestBody Map<String, Object> vo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, vo);
		action.deleteall(getDefMapping(), null, wrapper, response);
		return result(wrapper);
    }


    @ResponseBody
    @RequestMapping(value = "checkAuth")
    public RestResponse<?> checkAuth(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        HttpServletResponseWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
        action.checkAuth(emptyMapping, null, reqWrapper, response);
        return result(reqWrapper,ControllerHelper.standardizeResult(((HttpResponseWriterWrapper) respWrapper).getWriteContent()));
    }

}