package com.landray.kmss.third.weixin.attachment.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class WeixinAttachmentAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeixinAttachmentAction.class);

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getServiceImp(
			HttpServletRequest request) {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
		return sysAttMainService;
	}
	
	// public ActionForward download(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// System.out.println("==============微信下载。。。");
	// KmssMessages messages = new KmssMessages();
	// OutputStream out = null;
	// InputStream in = null;
	// try {
	// String filename = "default_pic.jpg";
	// String style = "default";
	// String _style = request.getParameter("s_css");
	// if (_style == null || _style.trim().length() == 0) {
	// Cookie[] cookies = request.getCookies();
	// if (cookies != null) {
	// for (int i = 0; i < cookies.length; i++) {
	// String cookieName = cookies[i].getName();
	// if ("KMSS_Style".equals(cookieName)) {
	// _style = cookies[i].getValue();
	// break;
	// }
	// }
	// }
	// }
	// if (_style != null && _style.trim().length() > 0) {
	// style = _style;
	// }
	// // 修正在weblogic下部署时的问题并优化。by fuyx 2010-6-2
	// // String filePath = getServletContext().getRealPath(
	// // "/resource/style/" + style + "/attachment/" + filename);
	// // File file = new File(filePath);
	// long fileSize = 0;
	// String fileContentType = "";
	// String fdId = request.getParameter("fdId");
	//
	// List<SysAttMain> sysAttMains = null;
	// if (fdId != null && fdId.trim().length() > 0) {
	// String[] l_fdId = fdId.split(";");
	// sysAttMains = ((ISysAttMainCoreInnerService) getServiceImp(request))
	// .findModelsByIds(l_fdId);
	// }
	// if (sysAttMains == null || sysAttMains.isEmpty()) {
	// messages.addError(new KmssMessage("Key not found."));
	// String defaultFile = request.getParameter("default");
	// if (defaultFile == null || defaultFile.trim().length() == 0) {
	// defaultFile = "default_pic.jpg";
	// }
	// // 修正在weblogic下部署时的问题并优化。by fuyx 2010-6-2
	// String filePath = ConfigLocationsUtil.getWebContentPath()
	// + "/resource/style/" + style + "/attachment/"
	// + filename;
	// File file = new File(filePath);
	// fileSize = file.length();
	// filename = encodeFileName(request, defaultFile);
	// fileContentType = FileMimeTypeUtil.getContentType(file);
	// // 读取缓存
	// if (AttImageUtils.cacheConsulation(request, response,
	// fileContentType, filename)) {
	// return null;
	// }
	// response.setContentLength((int) fileSize);
	// response.setContentType(fileContentType);
	// response.setHeader("Content-Disposition",
	// "attachment;filename=\"" + filename + "\"");
	//
	// out = response.getOutputStream();
	// FileInputStream fr = new FileInputStream(file);
	// // 按块1024K从输入流中读取数据到缓冲区中，然后从缓冲区逐次写入输出流中 menglei begin
	// IOUtil.write(fr, out);
	// // 按块1024K从输入流中读取数据到缓冲区中，然后从缓冲区逐次写入输出流中 menglei end
	//
	// response.flushBuffer();
	// return null;
	// } else {
	// out = response.getOutputStream();
	// // 记录日志信息
	// if (UserOperHelper.allowLogOper("download", "*")) {
	// UserOperContentHelper.putFinds(sysAttMains);
	// }
	// if (sysAttMains.size() == 1) {
	// SysAttMain sysAttMain = sysAttMains.get(0);
	// filename = encodeFileName(request, sysAttMain
	// .getFdFileName());
	// fileContentType = FileMimeTypeUtil.getContentType(filename);
	// if (StringUtil.isNull(fileContentType)) {
	// fileContentType = sysAttMain.getFdContentType();
	// }
	// in = sysAttMain.getInputStream();
	// fileSize = sysAttMain.getFdSize().intValue();
	// /*
	// * 注意，in为DecryptionInputStream，作用为处理解密文件，
	// * 由于附件查找读取采用的是API：findModelsByIds，
	// * 故DecryptionInputStream处理的对象是FileInputStream
	// * ,所以这里可以放心使用available方法获取文件大小
	// */
	// int tmpSize = in.available();
	// if (tmpSize != fileSize && tmpSize > 0) {
	// fileSize = tmpSize;
	// }
	// } else {
	// // 打包下载
	// filename = encodeFileName(request, fetchMainModelSubject(
	// request, sysAttMains.get(0))
	// + ".zip");
	// fileContentType = FileMimeTypeUtil.getContentType(filename);
	// }
	// if (fileSize > 0)
	// response.setContentLength((int) fileSize);
	// // 读取缓存
	// if (AttImageUtils.cacheConsulation(request, response,
	// fileContentType, filename)) {
	// return null;
	// }
	// response.setContentType(fileContentType);
	// response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
	// response.setHeader("Cache-Control", "cache, must-revalidate");
	// if (filename.indexOf(".swf") == -1) {
	// String open = request.getParameter("open");
	// if (StringUtil.isNotNull(open)) {
	// response.setHeader("Content-Disposition", "filename=\""
	// + filename + "\"");
	// } else {
	// response.setHeader("Content-Disposition",
	// "attachment;filename=\"" + filename + "\"");
	// }
	// }
	// if (sysAttMains.size() == 1) {
	// IOUtil.write(in, out);
	// } else {
	// ZipOutputStream zipOut = null;
	// try {
	// zipOut = new ZipOutputStream(out);
	// zipOut.setEncoding("GBK");
	// for (Iterator iterator = sysAttMains.iterator(); iterator
	// .hasNext();) {
	// SysAttMain tmpSysAttMain = (SysAttMain) iterator
	// .next();
	// in = tmpSysAttMain.getInputStream();
	// if (in != null) {
	// zipOut.putNextEntry(new ZipEntry(tmpSysAttMain
	// .getFdFileName()));
	// // zipout在批量下载时，不能马上关闭
	// IOUtils.copy(in, zipOut);
	// in.close();
	// in = null;
	// }
	// }
	// } catch (Exception e) {
	// if (zipOut != null)
	// IOUtils.closeQuietly(zipOut);
	// } finally {
	// if (zipOut != null)
	// IOUtils.closeQuietly(zipOut);
	// }
	// }
	// return null;
	// }
	// } catch (Exception e) {
	// if (in != null) {
	// in.close();
	// in = null;
	// }
	// if (out != null) {
	// out.close();
	// out = null;
	// }
	// messages.addError(e);
	// } finally {
	// if (in != null) {
	// in.close();
	// in = null;
	// }
	// if (out != null) {
	// out.close();
	// out = null;
	// }
	// }
	//
	// if (messages.hasError()) {
	// KmssReturnPage.getInstance(request).addMessages(messages)
	// .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
	// return mapping.findForward("failure");
	// } else {
	// return mapping.findForward("view");
	// }
	// }
	
	// private String fetchMainModelSubject(HttpServletRequest request,
	// SysAttMain sysAttMain) {
	// String filename = "download";
	// if (sysAttMain != null) {
	// Object mainModel = null;
	// try {
	// mainModel = ((ISysAttMainCoreInnerService) getServiceImp(request))
	// .getMainModel(sysAttMain);
	// } catch (Exception e1) {
	// logger.error("压缩过程获取主文档出错,错误信息:", e1);
	// }
	// if (mainModel != null) {
	// String modelInfo = ModelUtil.getModelClassName(mainModel);
	// if (StringUtil.isNotNull(modelInfo)) {
	// modelInfo = SysDataDict.getInstance().getModel(modelInfo)
	// .getDisplayProperty();
	// if (StringUtil.isNotNull(modelInfo)) {
	// try {
	// filename = String.valueOf(PropertyUtils
	// .getProperty(mainModel, modelInfo));
	// } catch (Exception e) {
	// try {
	// logger.warn("获取主文档标题出错,后续尝试默认属性docSubject:", e);
	// filename = String.valueOf(PropertyUtils
	// .getProperty(mainModel, "docSubject"));
	// } catch (Exception ex) {
	// try {
	// logger.warn("获取主文档标题出错,后续尝试属性fdName:", ex);
	// filename = String.valueOf(PropertyUtils
	// .getProperty(mainModel, "fdName"));
	// } catch (Exception et) {
	// logger
	// .warn("无法获取主文档标题，使用默认名称download:",
	// et);
	// filename = "download";
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// return filename;
	// }
	
	/**
	 * 文件名编码
	 * 
	 * @param request
	 * @param oldFileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeFileName(HttpServletRequest request, String oldFileName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"), "ISO8859-1");
		}
		oldFileName = oldFileName.replace("+", "%20");
		return oldFileName;
	}
	
	protected ISysAttDownloadLogService sysAttDownloadLogService;
	public ISysAttDownloadLogService getSysAttDownloadLogService() {
		if (sysAttDownloadLogService == null) {
            sysAttDownloadLogService = (ISysAttDownloadLogService) getBean(
                    "sysAttDownloadLogService");
        }
		return sysAttDownloadLogService;
	}
	public ActionForward downloadWxPic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream out = null;
		InputStream in = null;
		try {
			List<SysAttMain> sysAttMains = getAttMains(request);
			out = response.getOutputStream();
			if (!(sysAttMains == null || sysAttMains.isEmpty())) {
				// 记录日志信息
				if (UserOperHelper.allowLogOper("downloadWxPic", "*")) {
					UserOperContentHelper.putFinds(sysAttMains);
				}
				out = response.getOutputStream();
				String filename = "";
				long fileSize = 0;
				SysAttMain sysAttMain = sysAttMains.get(0);
				if (sysAttMains.size() == 1) {
					if (!("image/jpeg".equals(sysAttMain.getFdContentType())
							|| "image/png"
									.equals(sysAttMain.getFdContentType()))) {
						logger.warn("附件非jpg或png文件，不允许访问！");
						return null;
					}
					if (StringUtil.isNotNull(request.getHeader("User-Agent"))) {
						filename = encodeFileName(request, sysAttMain.getFdFileName());
					}
					in = sysAttMain.getInputStream();
					fileSize = sysAttMain.getFdSize().intValue();
					int tmpSize = in.available();
					if (tmpSize != fileSize && tmpSize > 0) {
						fileSize = tmpSize;
					}
				} else {
					logger.warn("图片附件打开异常！");
					return null;
				}
				response.reset();
				if (fileSize > 0) {
                    response.setContentLength((int) fileSize);
                }
				response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
				response.setHeader("Content-Type","image/jpeg");
				response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
				if (sysAttMains.size() == 1) {
					(getServiceImp(request)).findData(sysAttMain.getFdId(), out);
				}
				return null;
			}
		} catch (Exception e) {
			streamClose(in, out);
			logger.error("download错误", e);
		} finally {
			streamClose(in, out);
		}
		return null;
	}
	/**
	 * 获取附件信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List<SysAttMain> getAttMains(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		List<SysAttMain> sysAttMains = null;
		if (fdId != null && fdId.trim().length() > 0) {
			String[] l_fdId = fdId.split(";");
			sysAttMains = (getServiceImp(request)).findModelsByIds(l_fdId);
		}
		return sysAttMains;
	}

	// private void printAttIsNull(HttpServletRequest request,
	// HttpServletResponse response, OutputStream out,
	// KmssMessages messages) throws Exception {
	// String filename = "default_pic.jpg";
	// String style = "default";
	// String _style = request.getParameter("s_css");
	// if (_style == null || _style.trim().length() == 0) {
	// Cookie[] cookies = request.getCookies();
	// if (cookies != null) {
	// for (int i = 0; i < cookies.length; i++) {
	// String cookieName = cookies[i].getName();
	// if ("KMSS_Style".equals(cookieName)) {
	// _style = cookies[i].getValue();
	// break;
	// }
	// }
	// }
	// }
	// if (_style != null && _style.trim().length() > 0) {
	// style = _style;
	// }
	// long fileSize = 0;
	// String fileContentType = "";
	// messages.addError(new KmssMessage("Key not found."));
	// String defaultFile = request.getParameter("default");
	// if (defaultFile == null || defaultFile.trim().length() == 0) {
	// defaultFile = "default_pic.jpg";
	// }
	// // 修正在weblogic下部署时的问题并优化。by fuyx 2010-6-2
	// String filePath = ConfigLocationsUtil.getWebContentPath() +
	// "/resource/style/" + style + "/attachment/"
	// + filename;
	// File file = new File(filePath);
	// fileSize = file.length();
	// filename = encodeFileName(request, defaultFile);
	// fileContentType = FileMimeTypeUtil.getContentType(file);
	// response.reset();// add by刘声斌，在IE6打开pdf文件，要加这句才能打开
	// if (AttImageUtils.cacheConsulation(request, response, fileContentType,
	// filename)) { // 读取缓存
	// return;
	// }
	// response.setContentLength((int) fileSize);
	// response.setContentType(fileContentType);
	// response.setHeader("Content-Disposition", "filename=\"" + filename +
	// "\"");
	// IOUtil.write(new FileInputStream(file), out);
	// response.flushBuffer();
	// }
	private void streamClose(InputStream in, OutputStream out) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
		} catch (Exception e) {
			logger.debug("流关闭错误，错误信息", e);
		}
	}
}
