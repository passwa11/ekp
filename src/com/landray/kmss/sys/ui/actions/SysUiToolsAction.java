package com.landray.kmss.sys.ui.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysUiToolsAction extends BaseAction {
	private final String defaultFileType = "html,js,css,jpg,png,gif,jpg,tmpl,eot,svg,ttf,woff";

	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		ZipOutputStream zipOut = null;
		OutputStream out = null;
		try {
			if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("sys-admin:home.nav.sysAdmin")
								+ "(" + ResourceUtil.getString("sys-ui:ui.tools.title")
								+ ")");
			}

			String fileType = request.getParameter("fdFileType");
			if (StringUtil.isNull(fileType)) {
				fileType = defaultFileType;
			}
			out = response.getOutputStream();
			zipOut = new ZipOutputStream(out);
			zipOut.setEncoding("GBK");
			response.setContentType("application/zip");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "cache, must-revalidate");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"download.zip\"");
			IOFileFilter filter = new SuffixFileFilter(toSuffixes(fileType
					.split(",")), IOCase.INSENSITIVE);
			// 本项目内文件
			String rootPath = ConfigLocationsUtil.getWebContentPath();
			Collection<File> files = FileUtils.listFiles(new File(rootPath),
					filter, TrueFileFilter.INSTANCE);
			compressFiles(files, zipOut, rootPath);
			// 皮肤文件
			rootPath = ResourceUtil.getKmssConfigString("kmss.resource.path");
			rootPath = rootPath.replace("\\", "/");
			if (!rootPath.startsWith("/")) {
                rootPath = "/" + rootPath;
            }
			File extUi = new File(rootPath + "/ui-ext");
			if (extUi.exists()) {
				files = FileUtils.listFiles(extUi, filter,
						TrueFileFilter.INSTANCE);
				compressFiles(files, zipOut, rootPath);
			}
			// 说明文件
			zipOut.putNextEntry(new ZipEntry("说明文件.txt"));
			IOUtils.copy(new ByteArrayInputStream(ResourceUtil.getString(
					"sys-ui:ui.tools.statictool.summary").replace("{0}",
					fileType).getBytes()), zipOut);
			// 压缩结束
			zipOut.finish();
		} catch (Exception e) {
			messages.addError(e);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(zipOut);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(zipOut);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return null;
		}
	}

	private void compressFiles(Collection<File> files, ZipOutputStream zipOut,
			String rootPath) throws Exception {
		InputStream in = null;
		for (Iterator iterator = files.iterator(); iterator.hasNext();) {
			File file = (File) iterator.next();
			if (file.exists()) {
				String fileName = file.getCanonicalPath().replace("\\", "/");
				if (!fileName.startsWith("/")) {
                    fileName = "/" + fileName;
                }
				fileName = fileName.replaceAll("(?i)" + rootPath, "");
				if (fileName.startsWith("/")) {
                    fileName = fileName.substring(1);
                }
				if (!fileName.startsWith("WEB-INF")) {
					in = new FileInputStream(file);
					zipOut.putNextEntry(new ZipEntry(fileName));
					IOUtils.copy(in, zipOut);
					IOUtils.closeQuietly(in);
				}
			}
		}
	}

	private String[] toSuffixes(String[] extensions) {
		String[] suffixes = new String[extensions.length];
		for (int i = 0; i < extensions.length; i++) {
			if (extensions[i].indexOf(".") > -1) {
				suffixes[i] = extensions[i];
			} else {
				suffixes[i] = "." + extensions[i];
			}
		}
		return suffixes;
	}
}
