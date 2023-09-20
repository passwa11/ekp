package com.landray.kmss.sys.attachment.actions;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.AttImageUtils;
import com.landray.kmss.sys.attachment.util.SysAttPicUtils;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class SysAttPicAction extends BaseAction {

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getServiceImp(HttpServletRequest request) {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	/**
	 * 查看附件图片
	 * 
	 * 为了避免图片权限无限制放开,每次请求均需要带上时间戳和加密参数.只有时间戳在数据库时间的10分钟内且加密参数正确才可以查看图片
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		try {
			String fdId = request.getParameter("fdId");
			String timestamp = request.getParameter("t");
			String key = request.getParameter("k");
			// 请求必须包含k和t参数
			if (StringUtil.isNull(timestamp) || StringUtil.isNull(key)) {
				messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				return viewAttError(mapping, request, response, messages);
			}
			// 请求时间戳超过限定时间,无权限查看
			if (DbUtils.getDbTimeMillis() - Long.parseLong(timestamp) > 10L * 60 * 1000) {
				messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				return viewAttError(mapping, request, response, messages);
			}
			// 加密参数不正确,无权限查看
			if (!SysAttPicUtils.verify(timestamp + fdId, key)) {
				messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				return viewAttError(mapping, request, response, messages);
			}
			List<SysAttMain> sysAttMains = getAttMains(request);
			if (sysAttMains == null || sysAttMains.isEmpty()) {
				messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				return viewAttError(mapping, request, response, messages);
			}
			SysAttMain sysAttMain = sysAttMains.get(0);
			// 附件类型为图片才可以通过此方法查看,其他无权限这么做
			if (!"pic".equals(sysAttMain.getFdAttType())
					&& !SysAttPicUtils.isImageType(FilenameUtils.getExtension(sysAttMain.getFdFileName()))) {
				messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				return viewAttError(mapping, request, response, messages);
			}
			int fileSize = sysAttMain.getFdSize().intValue();
			String filename = new String(sysAttMain.getFdFileName().getBytes("UTF-8"), "ISO8859-1");
			String fileContentType = FileMimeTypeUtil.getContentType(filename);
			if (StringUtil.isNull(fileContentType)) {
				fileContentType = sysAttMain.getFdContentType();
			}
			// 读取缓存
			response.reset();
			if (AttImageUtils.cacheConsulation(request, response, fileContentType, filename)) {
				return null;
			}
			response.setContentLength(fileSize);
			response.setContentType(fileContentType);
			response.setHeader("Pragma", "public");
			response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
			out = response.getOutputStream();
			(getServiceImp(request)).findData(sysAttMain.getFdId(), out);
			return null;
		} catch (Exception e) {
			messages.addError(e);
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
		if (messages.hasError()) {
			return viewAttError(mapping, request, response, messages);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<SysAttMain> getAttMains(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		List<SysAttMain> sysAttMains = null;
		if (fdId != null && fdId.trim().length() > 0) {
			String[] fdIds = fdId.split(";");
			sysAttMains = (getServiceImp(request)).findModelsByIds(fdIds);
		}
		return sysAttMains;
	}

	private ActionForward viewAttError(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
			KmssMessages messages) {
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("failure");
	}

}
