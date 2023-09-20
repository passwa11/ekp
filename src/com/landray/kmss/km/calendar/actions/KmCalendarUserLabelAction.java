package com.landray.kmss.km.calendar.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.util.*;
import org.json.simple.JSONArray;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.forms.KmCalendarLabelForm;
import com.landray.kmss.km.calendar.forms.KmCalendarUserLabelForm;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 创建日期 2008-十月-16
 * 
 * @author 陈亮
 */
public class KmCalendarUserLabelAction extends ExtendAction

{
	protected IKmCalendarLabelService kmCalendarLabelService;

	public IKmCalendarLabelService getKmCalendarLabelService() {
		if (kmCalendarLabelService == null) {
			kmCalendarLabelService = (IKmCalendarLabelService) SpringBeanUtil
					.getBean("kmCalendarLabelService");
		}
		return kmCalendarLabelService;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarUserLabelForm kmCalenadrUserLabelForm = (KmCalendarUserLabelForm) form;
		String idStr = kmCalenadrUserLabelForm.getDeleteIds();
		if (StringUtil.isNotNull(idStr)) {
			String[] ids = idStr.split(",");
			try {
				getKmCalendarLabelService().delete(ids);
			} catch (Exception e1) {
				e1.printStackTrace();
				messages.addError(e1);
			}
		}
		List<KmCalendarLabelForm> list = (List<KmCalendarLabelForm>) kmCalenadrUserLabelForm
				.getKmCalendarLabelFormList();
		try {
			for (KmCalendarLabelForm kmCalendarLabelForm : list) {
				if (StringUtil.isNull(kmCalendarLabelForm.getFdName())) {
					continue;
				}
				getKmCalendarLabelService().update(kmCalendarLabelForm,
						new RequestContext(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward updateJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarUserLabelForm kmCalenadrUserLabelForm = (KmCalendarUserLabelForm) form;
		String idStr = kmCalenadrUserLabelForm.getDeleteIds();
		if (StringUtil.isNotNull(idStr)) {
			String[] ids = idStr.split(",");
			try {
				getKmCalendarLabelService().delete(ids);
				UserOperHelper.setOperSuccess(true);
			} catch (Exception e1) {
				UserOperHelper.setOperSuccess(false);
				e1.printStackTrace();
				messages.addError(e1);
			}
		}
		List<KmCalendarLabelForm> list = (List<KmCalendarLabelForm>) kmCalenadrUserLabelForm
				.getKmCalendarLabelFormList();

		try {

			for (KmCalendarLabelForm kmCalendarLabelForm : list) {
				if (StringUtil.isNull(kmCalendarLabelForm.getFdName())) {
					continue;
				}
				getKmCalendarLabelService().update(kmCalendarLabelForm,
						new RequestContext(request));
			}

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "1");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		} else {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "0");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<KmCalendarLabel> userLabels = getKmCalendarLabelService()
				.getLabelsByPerson(UserUtil.getUser().getFdId());
		// if (userLabels.size() == 0) {
		// KmCalendarLabel label = new KmCalendarLabel();
		// label.setFdId(IDGenerator.generateID());
		// label.setFdCreator(UserUtil.getUser());
		// label.setFdName("重要");
		// label.setFdOrder(1);
		// label.setFdColor("#dc2127");
		// getKmCalendarLabelService().add(label);
		// label = new KmCalendarLabel();
		// label.setFdId(IDGenerator.generateID());
		// label.setFdCreator(UserUtil.getUser());
		// label.setFdName("个人");
		// label.setFdOrder(2);
		// label.setFdColor("#51b749");
		// getKmCalendarLabelService().add(label);
		// userLabels = getKmCalendarLabelService().getLabelsByPerson(
		// UserUtil.getUser().getFdId());
		// }

		List<KmCalendarLabelForm> userLabelForms = new ArrayList<KmCalendarLabelForm>();

		for (KmCalendarLabel label : userLabels) {
			String fdModelName = label.getFdModelName();
			// 只可以管理编辑自定义标签
			if (StringUtil.isNull(fdModelName)) {
				// 过滤系统标签
				if(label.getFdCommonFlag() != null && '1' == (label.getFdCommonFlag().charAt(0))){
                      continue;
				}
				KmCalendarLabelForm kmCalendarLabelForm = new KmCalendarLabelForm();
				getKmCalendarLabelService().convertModelToForm(
						(IExtendForm) kmCalendarLabelForm, label,
						new RequestContext(request));
				String labelName = kmCalendarLabelForm.getFdName();
				kmCalendarLabelForm.setFdName(labelName);
				userLabelForms.add(kmCalendarLabelForm);
			}
		}
		KmCalendarUserLabelForm kmCalendarUserLabelForm = new KmCalendarUserLabelForm();
		kmCalendarUserLabelForm.setKmCalendarLabelFormList(userLabelForms);

		if (kmCalendarUserLabelForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(kmCalendarUserLabelForm, request),
				kmCalendarUserLabelForm);
	}

	// public ActionForward edit(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// TimeCounter.logCurrentTime("Action-edit", true, getClass());
	// KmssMessages messages = new KmssMessages();
	// try {
	// loadActionForm(mapping, form, request, response);
	// KmCalendarUserLabelForm kmCalendarUserLabelForm =
	// (KmCalendarUserLabelForm) form;
	// List<KmCalendarLabelForm> list = kmCalendarUserLabelForm
	// .getKmCalendarLabelFormList();
	// if (list.size() == 0) {
	// KmCalendarLabelForm kmCalendarLabelForm = new KmCalendarLabelForm();
	// kmCalendarLabelForm.setFdColor("");
	// kmCalendarLabelForm
	// .setFdCreatorId(UserUtil.getUser().getFdId());
	// kmCalendarLabelForm.setFdName("重要");
	// kmCalendarLabelForm.setFdOrder("1");
	//
	// }
	// } catch (Exception e) {
	// messages.addError(e);
	// }
	//
	// TimeCounter.logCurrentTime("Action-edit", false, getClass());
	// if (messages.hasError()) {
	// KmssReturnPage.getInstance(request).addMessages(messages)
	// .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
	// return getActionForward("failure", mapping, form, request, response);
	// } else {
	// return getActionForward("edit", mapping, form, request, response);
	// }
	// }

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		return null;
	}

}
