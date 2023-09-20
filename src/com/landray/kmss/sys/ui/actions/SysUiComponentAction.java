package com.landray.kmss.sys.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.portal.xml.model.SysPortalHeader;
import com.landray.kmss.sys.profile.util.LoginTemplateUtil;
import com.landray.kmss.sys.ui.model.Pager;
import com.landray.kmss.sys.ui.util.ComponentUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.ui.forms.SysUiComponentForm;
import com.landray.kmss.sys.ui.service.SysUiComponentService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.slf4j.Logger;

public class SysUiComponentAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	// 项目路径
	private static final String FOLDER = "resource/ui-component";
	public static final String SYSPORTALUI = "/sys/portal/template/ui_component/";

	private SysUiComponentService sysUiComponentService;

	public SysUiComponentService getSysUiComponentService() {
		if (sysUiComponentService == null) {
			sysUiComponentService = (SysUiComponentService) SpringBeanUtil
					.getBean("sysUiComponentService");
		}
		return sysUiComponentService;
	}

	/**
	 * 上传部件包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			getSysUiComponentService().upload((SysUiComponentForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return mapping.findForward("upload");
	}
	
	public ActionForward getThemeInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		JSONObject rtnJson = new JSONObject();
		try {
			SysUiComponentForm mainForm = (SysUiComponentForm) form;
			// 检查部件包信息
			JSONObject jsonInfo = getSysUiComponentService()
					.checkExtend(mainForm.getFile());
			// 部件ID
			String extendId = jsonInfo.getString("extendId");
			// 部件名称
			String extendName = jsonInfo.getString("extendName");
			// 部件临时存放目录文件夹名称
			String folderName = jsonInfo.getString("folderName");
			//缩略图
			String thumbnail = jsonInfo.getString("thumbnail");
			String folderPath = System.getProperty("java.io.tmpdir");
			if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
				folderPath += "/";
			}
			folderPath += folderName;
			// 复制到应用路径
			File appThemeFolder = new File(getAppFolder(extendId));
			FileUtils.copyDirectory(new File(folderPath), appThemeFolder);

			if (StringUtil.isNotNull(thumbnail)) {
				if (!thumbnail.startsWith("/")) {
					thumbnail = "/" + thumbnail;
				}
				rtnJson.put("fdThumbnail", FOLDER + "/" + extendId + thumbnail);
			}
			rtnJson.put("directoryPath", extendId);
			rtnJson.put("status", "1");
		} catch (Exception e) {
			rtnJson.put("status", "0");
			logger.error("获取上传部件的信息异常", e);
		}
		response.getWriter().print(rtnJson);
		return null;
	}
	
	public ActionForward delPreviewFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String directoryPath = request.getParameter("directoryPath");
			//真实文件路径
			if(StringUtil.isNotNull(directoryPath)) {
				directoryPath = getAppFolder(directoryPath);
				File appThemeFolder = new File(directoryPath);
				if (appThemeFolder.exists() && appThemeFolder.isDirectory()) {
					FileUtil.deleteDir(appThemeFolder);
				}
			}
		} catch (Exception e) {
			logger.error("删除预览留下的文件异常", e);
		}
		return null;
	}
	
	private String getAppFolder(String extendId) {
		return ConfigLocationsUtil.getWebContentPath() + "/" + FOLDER + "/" + extendId;
	}

	/**
	 * 下载部件包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// #89893 部署在windows环境上的项目部件包下载后解压问题，应该是根据请求做目录处理
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("downloadId");
			//对id做处理
			id=id.replaceAll("\\.","-");
			if (StringUtil.isNotNull(id) && id.indexOf("../") == -1) {
				File extendFolder = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
						+ "ui_component/" + id);
				if (extendFolder.exists()) {
					//打包下载
					LoginTemplateUtil.toZip(java.util.Arrays.asList(extendFolder.listFiles()), request, response, id+ ".zip");
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}

	private static void downloadFileList(List<File> files, ZipOutputStream zipOut, String dir,Boolean flag) throws Exception {
		if (StringUtil.isNull(dir)) {
            dir = "";
        } else {
			if(flag && "\\".equals(File.separator)) {
                dir += "/";
            } else {
                dir += File.separator;
            }
		}
		InputStream in = null;
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			if (file.isDirectory()) {
				List<File> fs = Arrays.asList(file.listFiles());
				downloadFileList(fs, zipOut, dir + file.getName(), flag);
			} else {
				in = new FileInputStream(file);
				if (in != null) {
					zipOut.putNextEntry(new ZipEntry(dir + file.getName()));
					// zipout在批量下载时，不能马上关闭
					IOUtils.copy(in, zipOut);
					in.close();
				}
			}
			in = null;
		}
	}
	
	/**
	 * 文件名编码
	 * 
	 * @param request
	 * @param oldFileName
	 * @param isEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeFileName(HttpServletRequest request, String oldFileName, boolean isEncode) throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1 || userAgent.indexOf("EDGE") > -1) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			oldFileName = oldFileName.replace("+", "%20");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"),"ISO8859-1");
		}
		if (isEncode) { // 如果是在线查看时，文件名会追加到URL上，此时需要转码。如果是下载文件，则不需要转码
			oldFileName = oldFileName.replace("+", "%20");
		}
		return oldFileName;
	}
	
	/**
	 * 删除部件包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteComponent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin")+"("+ ResourceUtil.getString("sys-ui:ui.extend.delete")+ ")");
		}
		String extendId = null;
		String fdId = request.getParameter("fdId");
		String uiType = request.getParameter("uiType");
//		if (path.startsWith(SYSPORTALUI)) {
//			String tempPath = path.substring(SYSPORTALUI.length());
//			String[] names = tempPath.split("/");
//			if (names != null && names.length > 0) {
//				extendId = names[0];
//			}
//		}
		if (StringUtil.isNotNull(fdId)) {
			fdId=fdId.replaceAll("\\.","-");
			boolean bool = getSysUiComponentService().deleteExtendDirectory(fdId, uiType);
			response.getWriter().print(bool ? "1" : "2");
		} else {
			response.getWriter().print("0");
		}
	}
	
	/**
	 * 替换部件包（替换的逻辑是先根据部件包ID进行删除，然后保存新上传的部件包）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void replaceExtend(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin")+"("+ ResourceUtil.getString("sys-ui:ui.extend.replace")+ ")");
		}
		String extendId = request.getParameter("extendId");
		String folderName = request.getParameter("folderName");
		if (StringUtil.isNotNull(extendId) && StringUtil.isNotNull(folderName)) {
			try {
				boolean bool = getSysUiComponentService()
						.replaceExtend(request);
				response.getWriter().print(bool ? "1" : "2");
			} catch (Exception e) {
				logger.error("替换部件包失败：", e);
				response.getWriter().print("0");
			}
		} else {
			response.getWriter().print("0");
		}
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}
	public ActionForward listChildren(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String type = request.getParameter("type");

			int pageno = 1;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			List allDatas = new ArrayList();
			List datas = new ArrayList();
			switch (type) {
				case "render":
					datas.add(new SysUiRender());
					allDatas.addAll(ComponentUtil.getAllRender(request));
					break;
				case "layout":
					datas.add(new SysUiLayout());
					allDatas.addAll(ComponentUtil.getAllLayout(request));
					break;
				case "header":
					datas.add(new SysPortalHeader());
					allDatas.addAll(ComponentUtil.getAllHeader(request));
					break;
				case "footer":
					datas.add(new SysPortalFooter());
					allDatas.addAll(ComponentUtil.getAllFooter(request));
					break;
				case "template":
					datas.add(new SysUiTemplate());
					allDatas.addAll(ComponentUtil.getAllTemplate(request));
					break;
				default:
					break;
			}
			Pager pager = new Pager(allDatas, rowsize);
			datas.addAll(pager.getPageList(pageno));

			// 返回页
			Page page = Page.getEmptyPage();
			page.setTotalrows(allDatas.size());
			page.setPageno(pageno);
			page.setRowsize(rowsize);
			page.setList(datas);

			request.setAttribute("queryPage", page);

		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.toString());
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("listChildren");
		}
	}
}
