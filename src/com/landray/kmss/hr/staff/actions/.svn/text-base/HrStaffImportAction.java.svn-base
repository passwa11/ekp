package com.landray.kmss.hr.staff.actions;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffImportForm;
import com.landray.kmss.hr.staff.service.IHrStaffImportService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 导入
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public abstract class HrStaffImportAction extends ExtendAction {

	/**
	 * 获取下载的模板文件名称
	 * 
	 * @return
	 */
	public abstract String getTempletName();

	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = getTempletName();
			// 构建模板文件
			HSSFWorkbook workbook = ((IHrStaffImportService) getServiceImp(request))
					.buildTempletWorkBook();

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			// 添加日志信息
			if (UserOperHelper.allowLogOper("downloadTemplet",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setEventType(ResourceUtil
						.getString("hr-staff:hrStaff.import.button.download"));
				UserOperContentHelper.putFind("", templetName,
						getServiceImp(request).getModelName());
			}
			return null;
		} catch (IOException e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-fileUpload", true, getClass());
		KmssMessage message = null;
		boolean isRollBack = true;
		String s_isRollBack = request.getParameter("isRollBack");
		if ("false".equals(s_isRollBack)) {
			isRollBack = false;
		}
		HrStaffImportForm baseForm = (HrStaffImportForm) form;
		FormFile file = baseForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString("hrStaff.import.noFile",
					"hr-staff");
		} else {
			try {
				message = ((IHrStaffImportService) getServiceImp(request))
						.saveImportData(baseForm, isRollBack);
				if (message.getMessageType() == KmssMessage.MESSAGE_COMMON) { 
					state = true;
				}
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		// 保存form请求路径
		request.setAttribute("uploadActionUrl", request
				.getParameter("uploadActionUrl"));
		//保存下载模版请求路径
		request.setAttribute("downLoadUrl", request.getParameter("downLoadUrl"));
		// 异常后数据是否需要回滚
		request.setAttribute("isRollBack", request.getParameter("isRollBack"));
		// 添加日志信息
		if (UserOperHelper.allowLogOper("fileUpload",
				getServiceImp(request).getModelName())) {
			UserOperHelper.setEventType(ResourceUtil
					.getString("hr-staff:hrStaff.import.button.submit"));
		}
		return getActionForward("hrStaffFileUpload", mapping, form, request,
				response);
	}
}
