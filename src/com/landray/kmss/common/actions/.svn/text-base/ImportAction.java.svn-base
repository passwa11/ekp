package com.landray.kmss.common.actions;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.FileUploadForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.WordToHtml;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.landray.sso.client.oracle.StringUtil;

public class ImportAction extends BaseAction {

	/**
	 * RTF导入word文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importWord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		String tempId = "";
		FileUploadForm fileForm = (FileUploadForm) form;
		FormFile file = null;
		InputStream is =null;
		if(fileForm!=null){
			file = fileForm.getFile();
			if(file!=null) {
                is = file.getInputStream();
            }
		}
		try {
			if (file != null && file.getInputStream() != null) {
				String fileName = file.getFileName();
				tempId = IDGenerator.generateID();
				String folderName = tempId + "/";
				String imgFolderPath = ConfigLocationsUtil.getWebContentPath()
						+ "/resource/ckeditor/images/" + folderName;
				File folder = new File(imgFolderPath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				String htmlName = "doc.html";
				WordToHtml convertor = new WordToHtml(request);
				result = convertor.convertWordToHtml(is,
						fileName);
				FileUtil.createFile(imgFolderPath + htmlName, result, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			tempId = "";
		} finally {
			if(is!=null) {
                is.close();
            }
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
				.write("<script>parent.rtf_importWord_callback('" + tempId
						+ "');</script>");
		return null;
	}

	/**
	 * 导入成功之后删除生成的HTML文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String folderName = request.getParameter("folder");
			if (StringUtil.isNotNull(folderName) && !checkId(folderName)) {
				String path = ConfigLocationsUtil.getWebContentPath()
						+ "/resource/ckeditor/images/" + folderName;
				FileUtil.deleteDir(new File(path));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	/**
	 * 初步校验字符串是否符合系统中的id策略
	 * 
	 * @param id
	 * @return
	 */
	private boolean checkId(String id) {
		String regex = "[0-9a-f]{32}";
		return Pattern.matches(regex, id);
	}
}
