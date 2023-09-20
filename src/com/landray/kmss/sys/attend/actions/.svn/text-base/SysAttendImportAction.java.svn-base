package com.landray.kmss.sys.attend.actions;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.forms.SysAttendImportForm;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * @author linxiuxian
 *
 */
public abstract class SysAttendImportAction extends ExtendAction {

	/**
	 * 获取下载的模板文件名称
	 * 
	 * @return
	 */
	public abstract String getTempletName();


	/**
	 * 构建下载模板
	 * 
	 * @return
	 */
	public abstract HSSFWorkbook buildTemplateWorkBook();

	/**
	 * 保存导入数据
	 * 
	 * @return
	 */
	public abstract KmssMessage saveImportData(ActionForm form,
			HttpServletRequest request, boolean isRollback);

	public ActionForward downloadTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = getTempletName();
			// 构建模板文件
			HSSFWorkbook workbook = buildTemplateWorkBook();

			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=\""
					+ new String(templetName.getBytes("GBK"), "ISO-8859-1")
					+ ".xls\"");
			ServletOutputStream out = response.getOutputStream();
			try {
				workbook.write(out);
			} catch (Exception e) {
				messages.addError(e);
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
			}
			return null;
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
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
	public ActionForward importExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-importExcel", true, getClass());
		KmssMessage message = null;
		boolean isRollback = true;
		String s_isRollBack = request.getParameter("isRollBack");
		if ("false".equals(s_isRollBack)) {
			isRollback = false;
		}
		SysAttendImportForm baseForm = (SysAttendImportForm) form;
		FormFile file = baseForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil
					.getString("sys-time:sysTime.import.noFile");
		} else {
			try {
				message = saveImportData(form, request, isRollback);
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
		// 保存下载模版请求路径
		request.setAttribute("downLoadUrl",
				request.getParameter("downLoadUrl"));
		// 异常后数据是否需要回滚
		request.setAttribute("isRollBack", request.getParameter("isRollBack"));
		return getActionForward("sysAttendImportExcel", mapping, form, request,
				response);
	}

}
