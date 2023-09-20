package com.landray.kmss.sys.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.ui.forms.SysUiExtendForm;
import com.landray.kmss.sys.ui.service.SysUiExtendService;
import com.landray.kmss.sys.ui.util.ResourceCacheListener;
import com.landray.kmss.sys.ui.util.ThemeUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Arrays;

public class SysUiExtendAction extends BaseAction {
	// 项目路径
	private static final String FOLDER = "resource/" + XmlReaderContext.UIEXT;
	
	private SysUiExtendService sysUiExtendService;

	public SysUiExtendService getSysUiExtendService() {
		if (sysUiExtendService == null) {
			sysUiExtendService = (SysUiExtendService) SpringBeanUtil.getBean("sysUiExtendService");
		}
		return sysUiExtendService;
	}

	/**
	 * 上传主题包
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			getSysUiExtendService().upload((SysUiExtendForm) form, new RequestContext(request));
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
			SysUiExtendForm mainForm = (SysUiExtendForm) form;
			// 检查登录模板包，并获取登录模板信息
			JSONObject jsonInfo = getSysUiExtendService().checkExtend(mainForm.getFile());
			// 主题包ID
			String extendId = jsonInfo.getString("extendId");
			// 主题包名称
			String extendName = jsonInfo.getString("extendName");
			// 主题包临时存放目录文件夹名称
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
			rtnJson.put("msg",e.getMessage());
			logger.error("获取上传主题的信息异常", e);
		}
		response.getWriter().print(rtnJson);
		return null;
	}
	
	public ActionForward delPreviewFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String directoryPath = request.getParameter("directoryPath");
			if(StringUtil.isNotNull(directoryPath)) {
				//真实文件路径
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
	 * 下载主题包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//#89893 部署在windows环境上的项目主题包下载后解压问题，应该是根据请求做目录处理
		String user_Agent = request.getHeader("User-Agent");
		Boolean flag = false;
		if(StringUtil.isNotNull(user_Agent)) {
			flag = user_Agent.indexOf("Macintosh") > -1;
		}
		String id = request.getParameter("id");
		if (StringUtil.isNotNull(id) && id.indexOf("../") == -1) {
			File extendFolder = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/" + XmlReaderContext.UIEXT + "/" + id);
			if (extendFolder.exists()) {
				ZipOutputStream zipOut = null;
				OutputStream out = null;
				try {
					response.reset();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String fileName = extendFolder.getName() + "-" + sdf.format(new Date()) + ".zip";
					fileName = encodeFileName(request, fileName, true);
					String contentType = FileMimeTypeUtil.getContentType(fileName); // application/zip
					out = response.getOutputStream();
					response.setContentType(contentType);
					response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
					response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
					zipOut = new ZipOutputStream(out);
					zipOut.setEncoding("GBK");
					downloadFileList(Arrays.asList(extendFolder.listFiles()), zipOut, null,flag);
					if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
						UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin")+"(" + ResourceUtil.getString("sys-ui:ui.extend.download")+ ")");
					}
				} catch (Exception e) {
					throw e;
				} finally {
					// 先后顺序不能乱，不然最后一个文件会没有数据
					if (zipOut != null) {
                        IOUtils.closeQuietly(zipOut);
                    }
					if (out != null) {
                        IOUtils.closeQuietly(out);
                    }
				}
			}
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
	 * 合并主题包（将每个扩展主题包与系统默认default主题包分别进行合并）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void merge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ThemeUtil.mergeAllTheme(request.getContextPath());
		// 更新集群缓存信息
		ResourceCacheListener.updateResourceCache();
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin")+"("+ ResourceUtil.getString("sys-ui:ui.extend.merge")+ ")");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("1");
	}

	
	/**
	 * 删除主题包
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteExtend(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin")+"("+ ResourceUtil.getString("sys-ui:ui.extend.delete")+ ")");
		}
		String extendId = request.getParameter("id");
		String uiType = request.getParameter("uiType");
		if (StringUtil.isNotNull(extendId)) {
			boolean bool = getSysUiExtendService().deleteExtendDirectory(extendId, uiType);
			response.getWriter().print(bool?"1":"2");
		}else{
			response.getWriter().print("0");
		}
	}
	
	/**
	 * 替换主题包（替换的逻辑是先根据主题包ID进行删除，然后保存新上传的主题包）
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
				boolean bool = getSysUiExtendService().replaceExtend(request);
				response.getWriter().print(bool ? "1" : "2");
			} catch (Exception e) {
				logger.error("替换主题包失败：", e);
				response.getWriter().print("0");
			}
		} else {
			response.getWriter().print("0");
		}
	}
	
}
