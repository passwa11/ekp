package com.landray.kmss.sys.time.actions;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.time.forms.SysTimeImportForm;
import com.landray.kmss.sys.time.service.ISysTimeImportService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 导入
 *
 * @author cuiwj
 * @version 1.0 2018-12-29
 */
public abstract class SysTimeImportAction extends ExtendAction {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
			HSSFWorkbook workbook = ((ISysTimeImportService) getServiceImp(
					request)).buildTempletWorkBook();

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
		SysTimeImportForm baseForm = (SysTimeImportForm) form;
		FormFile file = baseForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil
					.getString("sys-time:sysTime.import.noFile");
		} else {
			try {
				message = ((ISysTimeImportService) getServiceImp(request))
						.saveImportData(baseForm, isRollBack);
				if (message.getMessageType() == KmssMessage.MESSAGE_COMMON) {
					state = true;
				}
			} catch (Exception e) {
				message = new KmssMessage(e.getMessage());
				logger.error("saveImportData:" + e.getMessage(), e);
			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		
		// 保存form请求路径
		String uploadActionUrl = request.getParameter("uploadActionUrl");
		if (StringUtil.isNotNull(uploadActionUrl)) {
			uploadActionUrl = StringEscapeUtils.escapeJavaScript(uploadActionUrl);
		}
		
		request.setAttribute("uploadActionUrl", uploadActionUrl);
		
		// 保存下载模版请求路径
		String downLoadUrl = request.getParameter("downLoadUrl");
		if (StringUtil.isNotNull(downLoadUrl)) {
			downLoadUrl = StringEscapeUtils.escapeJavaScript(downLoadUrl);
		}
		request.setAttribute("downLoadUrl",downLoadUrl);
		
		// 异常后数据是否需要回滚
		if (StringUtil.isNotNull(s_isRollBack)) {
			s_isRollBack = StringEscapeUtils.escapeJavaScript(s_isRollBack);
		}
		request.setAttribute("isRollBack", s_isRollBack);
		
		return getActionForward("sysTimeFileUpload", mapping, form, request,
				response);
	}

}
