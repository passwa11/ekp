package com.landray.kmss.sys.attachment.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.sys.attachment.integrate.dianju.interfaces.ISysAttachmentDianJuProvider;
import com.landray.kmss.sys.attachment.integrate.foxit.ISysAttachmentFoxitProvider;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsAddinProvider;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.interfaces.ISysAttachmentTransmissionProvider;
import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.io.RandomAccessFileInputStream;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.model.SysAttachmentWps;
import com.landray.kmss.sys.attachment.ocx.SysAttOcxUtil;
import com.landray.kmss.sys.attachment.plugin.customPage.util.SysAttCustomPageUtil;
import com.landray.kmss.sys.attachment.plugin.wpsAddons.util.SysAttachmentWpsAddonsLockUtils;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttMainFoxitService;
import com.landray.kmss.sys.attachment.service.ISysAttToggleService;
import com.landray.kmss.sys.attachment.service.ISysAttachmentWpsService;
import com.landray.kmss.sys.attachment.util.AttImageUtils;
import com.landray.kmss.sys.attachment.util.JgWebOffice;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.attachment.util.SysAttCryptUtil;
import com.landray.kmss.sys.attachment.util.SysAttUtil;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.sys.cache.redis.RedisCommands;
import com.landray.kmss.sys.cache.redis.RedisConfig;
import com.landray.kmss.sys.cache.redis.SerializeUtils;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.filestore.constant.SysAttUploadConstant;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.model.SysFileSignature;
import com.landray.kmss.sys.filestore.location.model.SysFileSignatureRequest;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.model.SysFileViewerParam;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil.FileConverter;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileTypeUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2006-九月-04
 *
 * @author 孙真
 */
public class SysAttMainAction extends ExtendAction {
	protected ISysAttMainCoreInnerService sysAttMainService;

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttMainAction.class);

	private static HashMap providerMap;

	private static final String WPS_OASISST_REDIS_KEY = "wps_oasisst_redis_";
	private static final String WPS_OASISST_LPATOKEN = "wps_oasisst_token_";
	private static final String WPS_WEB_DOCUMENT_HEART = "480";
	private static final String WPS_DOCUMENT_LIFE = "600";

	@Override
	protected ISysAttMainCoreInnerService getServiceImp(HttpServletRequest request) {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	protected ISysAttToggleService sysAttToggleService;

	protected ISysAttToggleService getSysAttToggleService() {
		if (sysAttToggleService == null) {
			sysAttToggleService = (ISysAttToggleService) getBean("sysAttToggleService");
		}
		return sysAttToggleService;
	}

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
			sysPrintLogCoreService = (ISysPrintLogCoreService) getBean("sysPrintLogCoreService");
		}
		return sysPrintLogCoreService;
	}

	protected ISysAttDownloadLogService sysAttDownloadLogService;

	public ISysAttDownloadLogService getSysAttDownloadLogService() {
		if (sysAttDownloadLogService == null) {
			sysAttDownloadLogService = (ISysAttDownloadLogService) getBean("sysAttDownloadLogService");
		}
		return sysAttDownloadLogService;
	}

	private ISysAttachmentWpsService sysAttachmentWpsService;

	public IBaseService
	getSysAttachmentWpsServiceImp(HttpServletRequest request) {
		if (sysAttachmentWpsService == null) {
			sysAttachmentWpsService = (ISysAttachmentWpsService) getBean(
					"sysAttachmentWpsService");
		}
		return sysAttachmentWpsService;
	}

	protected ISysAttMainFoxitService sysAttMainFoxitService;

	public ISysAttMainFoxitService getSysAttMainFoxitService() {
		if (sysAttMainFoxitService  == null) {
			sysAttMainFoxitService = (ISysAttMainFoxitService) getBean("sysAttMainFoxitService");
		}
		return sysAttMainFoxitService;
	}

	private ISysAttachmentDianJuProvider sysAttachmentDianJuProvider;

	public ISysAttachmentDianJuProvider getSysAttachmentDianJuProvider() {
		if (sysAttachmentDianJuProvider == null) {
			sysAttachmentDianJuProvider = (ISysAttachmentDianJuProvider) getBean("sysAttachmentDianJuProvider");
		}
		return sysAttachmentDianJuProvider;
	}
	/**
	 * 接口组件
	 */
	private ISysAttachmentFoxitProvider sysAttachmentFoxitProvider = null;
	private ISysAttachmentFoxitProvider getSysAttachmentFoxitProvider() {
		if(sysAttachmentFoxitProvider == null) {
			sysAttachmentFoxitProvider = (ISysAttachmentFoxitProvider)
					SpringBeanUtil.getBean("foxitProvider");
		}
		return sysAttachmentFoxitProvider;
	}

	private String fileLimitType = "1";

	private String disabledFileType = SysAttConstant.DISABLED_FILE_TYPE;

	private ISysAttachmentWpsAddinProvider sysAttachmentWpsAddinProvider;

	private ISysAttachmentWpsAddinProvider getSysAttachmentWpsAddinProvider() {
		if(sysAttachmentWpsAddinProvider == null) {
			sysAttachmentWpsAddinProvider = (ISysAttachmentWpsAddinProvider) getBean("sysAttachmentWpsAddinProviderImp");
		}

		return sysAttachmentWpsAddinProvider;
	}
	/**
	 * 判断附件视频是否转换完成
	 */

	public ActionForward buildHtmlComplete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception, Throwable {
		List<String> rtnList = new ArrayList<String>();
		List<String> fdFileIds = new ArrayList<String>();
		// 兼容早期附件版本
		List<String> fdAttMainIds = new ArrayList<String>();
		// 转换完成的附件的fileId
		List<String> fileIds = new ArrayList<String>();
		// 转换完成的附件的mainId(旧版附件没有fileId,只能通过mainId)
		List<String> mainIds = new ArrayList<String>();
		String modelName = request.getParameter("fdModelName");
		String modelId = request.getParameter("fdModelId");
		JSONObject jsonObject = new JSONObject();

		List<SysAttMain> attList = ((ISysAttMainCoreInnerDao) getServiceImp(request).getBaseDao())
				.findAttListByModel(modelName, modelId);
		for (SysAttMain sysAttMain : attList) {
			if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
				fdFileIds.add(sysAttMain.getFdFileId());
			} else {
				fdAttMainIds.add(sysAttMain.getFdId());
			}
		}
		if (fdFileIds.size() > 0) {
			String sql = "select DISTINCT fd_file_id from sys_file_viewer_param where fd_file_id in (:list)";
			fileIds = getServiceImp(request).getAttIds(fdFileIds, sql, null);

		}
		if (fdAttMainIds.size() > 0) {
			String sql = "select DISTINCT fd_attmain_id from sys_file_viewer_param where fd_file_id in (:list)";
			mainIds = getServiceImp(request).getAttIds(fdAttMainIds, sql, null);

		}

		if (fileIds.size() > 0) {
			String attSql = "select fd_id from sys_att_main where fd_model_name=:modelName and fd_file_id in (:fileIds)";

			rtnList = getServiceImp(request).getAttIds(fileIds, attSql, modelName);
		}
		if (mainIds.size() > 0) {
			if (rtnList.size() > 0) {
				mainIds.removeAll(rtnList);
			}
			rtnList.addAll(mainIds);
		}

		String thirdDianjuEnabled = ConfigUtil.configValue("thirdDianjuEnabled");
		String thirdFoxitEnabled = FoxitUtil.configValue("thirdFoxitEnabled");

		// 如果是wps云文档、windows在线预览、linux在线预览、文档中台

		if ("true".equals(thirdDianjuEnabled)||"true".equals(thirdFoxitEnabled)||SysAttWpsCenterUtil.isEnable()||SysAttWpsCloudUtil.isEnable()||SysAttWpsCloudUtil.checkWpsPreviewIsLinux()||SysAttWpsCloudUtil.checkWpsPreviewIsWindows()) {
			List<String> wpsList = new ArrayList<String>();
			for (SysAttMain sysAttMain : attList) {
				String previewType=SysAttViewerUtil.getPreView(sysAttMain, false);

				if ("true".equals(thirdDianjuEnabled)||"true".equals(thirdFoxitEnabled)||SysAttWpsCenterUtil.isEnable()||SysAttConstant.WPS_LINUX_VIEW.equals(previewType)||SysAttConstant.WPS_WINDOW_VIEW.equals(previewType)||SysAttConstant.WPS_CLOUD_VIEW.equals(previewType)
						|| rtnList.contains(sysAttMain.getFdId())) {
					wpsList.add(sysAttMain.getFdId());
				}
			}
			if (!ArrayUtil.isEmpty(wpsList)) {
				rtnList = new ArrayList<String>();
				rtnList.addAll(wpsList);
			}
		}

		StringBuffer buffer = new StringBuffer();
		for (String id : rtnList) {
			buffer.append(id + ",");
		}
		String ids = buffer.toString();
		jsonObject.put("fdAttHtmlIds", ids);
		response.getWriter().print(jsonObject);
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

		if(SysAttWpsCloudUtil.isEnable()||SysAttWpsCloudUtil.isEnable(isMobileRequest(request))) {
			if(!ArrayUtil.isEmpty(sysAttMains)) {
				for (SysAttMain sysAttMain : sysAttMains) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(sysAttMain.getFdFileName()))){
						Boolean isAutoSave = SysAttWpsCloudUtil
								.checkWpsCloueAutoSaveByModelName(
										sysAttMain.getFdModelName());
						if (isAutoSave) {
							SysAttWpsCloudUtil.updateAttByMainId(sysAttMain.getFdId());
						}
					}
				}
			}
		}

		return sysAttMains;
	}

	private void printAttIsNull(HttpServletRequest request, HttpServletResponse response, OutputStream out,
								KmssMessages messages) throws Exception {
		String filename = "default_pic.jpg";
		String style = "default";
		String _style = request.getParameter("s_css");
		if (_style == null || _style.trim().length() == 0) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					String cookieName = cookies[i].getName();
					if ("KMSS_Style".equals(cookieName)) {
						_style = cookies[i].getValue();
						break;
					}
				}
			}
		}
		if (_style != null && _style.trim().length() > 0) {
			style = _style;
		}
		long fileSize = 0;
		String fileContentType = "";
		messages.addError(new KmssMessage("Key not found."));
		String defaultFile = request.getParameter("default");
		if (defaultFile == null || defaultFile.trim().length() == 0) {
			defaultFile = "default_pic.jpg";
		}
		// 修正在weblogic下部署时的问题并优化。by fuyx 2010-6-2
		String filePath = ConfigLocationsUtil.getWebContentPath() + "/resource/style/" + style + "/attachment/"
				+ filename;
		File file = new File(filePath);
		fileSize = file.length();
		filename = encodeFileName(request, defaultFile);
		fileContentType = FileMimeTypeUtil.getContentType(file);
		response.reset();// add by刘声斌，在IE6打开pdf文件，要加这句才能打开
		if (AttImageUtils.cacheConsulation(request, response, fileContentType, filename)) { // 读取缓存
			return;
		}
		response.setContentLength((int) fileSize);
		response.setContentType(fileContentType);
		response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
		IOUtil.write(new FileInputStream(file), out);
		response.flushBuffer();
	}

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

	public ActionForward readDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		InputStream in = null;
		String open = null;
		boolean isDownload = false;
		boolean isImage = false; // 是否是图片
		String useBrowserOpen = request.getParameter("useBrowserOpen");
		if(StringUtil.isNull(useBrowserOpen)) {
			useBrowserOpen = "false";
		}

		//txt类型的文件，在配置为oss存储时，阅读变为下载，注释此段统一走代理即可正常阅读
//		if (downloadDirect(request, response)) {
//			return null;
//		}

		List<SysAttMain> attMains = getAttMains(request);


		if (attMains == null || attMains.isEmpty()) {
			out = response.getOutputStream();
			printAttIsNull(request, response, out, messages);
		} else {
			SysAttMain sysAttMain = attMains.get(0);
			if (StringUtil.isNotNull(sysAttMain.getFdContentType())
					&& sysAttMain.getFdContentType().contains("image")) {
				isImage = true;
			}
			boolean isMoblie = isMobileRequest(request);

			String previewType=SysAttViewerUtil.getPreView(sysAttMain, isMoblie);
			if (isImage) {
				previewType = "";
			}

			//跳转自定义附件页面拓展点实现
			String actionForwardUrl = SysAttCustomPageUtil.getCustomPage(request);
			if(StringUtil.isNotNull(actionForwardUrl) && !isMoblie && !isImage && !SysAttConstant.DOWNLOAD.equals(previewType)) {
				return new ActionForward(actionForwardUrl);
			}

			// #161059 PDF附件可以支持使用谷歌、火狐、Edge直接打开查看（先只开放给EIS的中小客户使用）
			if(SysAttUtil.viewByBrowser(sysAttMain) && "true".equals(useBrowserOpen)) {
				SysAttUtil.outputFile(response, sysAttMain);
				return null;
			}
			//优先判断使用福昕
			if(SysAttConfigUtil.pdfReadByFoxitPC() && JgWebOffice.isPDF(sysAttMain.getFdFileName())){
				getSysAttMainFoxitService().createRequestMateData(request, sysAttMain, false);
				return mapping.findForward("viewonline_foxit");
			}
			commonLogFind(request,sysAttMain,"readDownload");
			//#156744 预览编辑使用金格，未启用金格iWebPDF，PDF打开方式（如果附件已转换，则优先以快速方式打开）选择"严控文件下载"功能不生效，打开还是用iwebpdf打开
			if ("0".equals(SysAttConfigUtil.getOnlineToolType()) && !JgWebOffice.isJGPDFEnabled() && !JgWebOffice.isJGPDF2018Enabled()
					&& (JgWebOffice.isPDF(sysAttMain.getFdFileName()) || JgWebOffice.isOFD(sysAttMain.getFdFileName())) && "1".equals(SysAttConfigUtil.getReadOLConfig())) {
				String convertPath = SysAttViewerUtil.getViewerPath(sysAttMain, SysAttViewerUtil.getConvertedParams(sysAttMain), SysFileStoreUtil.getFileConverters(
						SysAttViewerUtil.getExtName(sysAttMain.getFdFileName()), sysAttMain.getFdModelName(), SysFileStoreUtil.isOldConvertSuccessUseHTML()), request);
				if (StringUtils.isEmpty(convertPath)) {
					if ("0".equals(SysAttConfigUtil.isReadPdf())) {
						return mapping.findForward("viewonline_nopdf");
					} else if ("1".equals(SysAttConfigUtil.isReadPdf())) {
						isDownload = true;
						previewType = SysAttConstant.DOWNLOAD;
					}
				}
			}

			ActionForward routeView = routeView(mapping, request, response, sysAttMain, out, in);
			if (!isDownload && !isImage && ("true".equals(request.getAttribute("printStream")) || routeView != null)) {
				return routeView;
			}
			if(SysAttConstant.DOWNLOAD.equals(previewType)) {
				isDownload = true;
			}
			if (!isDownload && (JgWebOffice.isPDF(sysAttMain.getFdFileName()) ||
					JgWebOffice.isOFD(sysAttMain.getFdFileName()))) {
				// view(mapping, form, request, response);
				boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();
				boolean oldConvertSuccessUseHTMLView = SysFileStoreUtil.isOldConvertSuccessUseHTML();
				String viewerPath = "";
				String fdAttMainId = request.getParameter("fdId");
				request.setAttribute("fdId", fdAttMainId);
				String viewType = StringUtil.isNull(request.getParameter("viewType")) ? "html"
						: request.getParameter("viewType");
				if (attConvertEnable) {
					SysAttViewerUtil.addQueue(sysAttMain);
				}

				if (JgWebOffice.isOFD(sysAttMain.getFdFileName())) {
					viewType = "jg";
				}

				if ("jg".equals(viewType)) {
					return viewByJG(mapping, form, request, response, sysAttMain);
				}
				List<SysFileViewerParam> convertedParamsList = SysAttViewerUtil.getConvertedParams(sysAttMain);
				List<FileConverter> fileConverters = null;
				fileConverters = SysFileStoreUtil.getFileConverters(
						SysAttViewerUtil.getExtName(sysAttMain.getFdFileName()), sysAttMain.getFdModelName(),
						oldConvertSuccessUseHTMLView);
				if (attConvertEnable && fileConverters.size() == 0) {
					return new ActionForward("/sys/attachment/viewer/attconvertconfig_not_ok.jsp");
				}
				viewerPath = SysAttViewerUtil.getViewerPath(sysAttMain, convertedParamsList, fileConverters, request);
				if (StringUtil.isNotNull(viewerPath)) {
					request.setAttribute("fdModelName", sysAttMain.getFdModelName());
					IBaseModel model = getServiceImp(request).findByPrimaryKey(sysAttMain.getFdModelId(),
							sysAttMain.getFdModelName(), true);
					if (model != null && PropertyUtils.isReadable(model, "docStatus")) {
						String docStatus = (String) PropertyUtils.getProperty(model, "docStatus");
						if (StringUtil.isNotNull(docStatus)) {
							request.setAttribute("docStatus", docStatus);
						}
					}
					boolean canCopy = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canCopy", canCopy);
					boolean canPrint = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canPrint", canPrint);
					return new ActionForward(viewerPath);
				} else if (JgWebOffice.isJGPDFEnabled()
						|| JgWebOffice.isJGPDF2018Enabled()||SysAttWpsWebOfficeUtil.isEnable()||SysAttWpsCloudUtil.isEnable()||SysAttWpsCenterUtil.isEnable()) {
					return viewByJG(mapping, form, request, response, sysAttMain);
				} else if ("0".equals(SysAttConfigUtil.isReadPdf())) {
					return mapping.findForward("viewonline_nopdf");
				}
			}

			if (SysAttConstant.WPS_LINUX_VIEW.equals(previewType)) {
				request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(sysAttMain.getFdId()));
				return mapping.findForward("viewonline_wps_preview");

			}
			if(SysAttConstant.WPS_WINDOW_VIEW.equals(previewType)) {
				request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsWindowPreviewUrl(sysAttMain.getFdId()));
				return mapping.findForward("viewonline_wps_preview");
			}
			if(SysAttConstant.WPS_CLOUD_VIEW.equals(previewType)) {
				if (!SysAttWpsCloudUtil
						.isAttHadSyncByAttMainId(
								sysAttMain.getFdId())) {
					SysAttWpsCloudUtil
							.syncAttToAddByMainId(sysAttMain.getFdId());
				}
				return mapping.findForward("viewonline_wps_cloud");
			}
			if(SysAttConstant.CONVERTING.equals(previewType)) {
				return mapping.findForward("viewonline_converting");
			}

			if ("0".equals(SysAttConfigUtil.isReadPdf())&&!isDownload && (JgWebOffice.isPDF(sysAttMain.getFdFileName()) ||
					JgWebOffice.isOFD(sysAttMain.getFdFileName()))) {

				return mapping.findForward("viewonline_nopdf");
			}

			try {
				out = response.getOutputStream();
				in = sysAttMain.getInputStream();
				int fileSize = sysAttMain.getFdSize().intValue();
				int tmpSize = in.available();
				if (tmpSize != fileSize && tmpSize > 0) {
					fileSize = tmpSize;
				}
				String filename = encodeFileName(request, sysAttMain.getFdFileName());
				String fileContentType = sysAttMain.getFdContentType();
				response.reset(); // add by 刘声斌，在IE6打开pdf文件，要加这句才能打开
				if (fileSize > 0) {
					response.setContentLength(fileSize);
				}
				response.setContentType(fileContentType);
				String extName = FilenameUtils.getExtension(filename);
				// 解决包含中文的txt文件在浏览器直接打开乱码问题
				if ("txt".equals(extName)||"html".equals(extName)) {
					String charCode = SysAttUtil.getCharset(in);
					if (StringUtil.isNotNull(charCode)) {
						response.setCharacterEncoding(charCode);
					}
				}
				response.setHeader("Pragma", "public"); // 解决ie6下载附件问题,ie8在https下的下载附件问题
				open = request.getParameter("open");
				if (StringUtil.isNotNull(open) && fileContentType.indexOf("pdf") > -1) {
					SysAttMainForm sysAttMainForm = new SysAttMainForm();
					BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
					request.setAttribute("sysAttMainForm", sysAttMainForm);
					boolean isJGPDFEnabled = JgWebOffice.isJGPDFEnabled();
					String fdFileName = sysAttMain.getFdFileName();
					if (JgWebOffice.isPDF(fdFileName)) {// 文件后缀名是否有pdf
						if (isJGPDFEnabled) {// 开启pdf控件跳转pdf在线阅读页面
							return mapping.findForward("viewonline_pdf");
						} else {// 未启用控件时转到下载
							out = response.getOutputStream();
							fileContentType = FileMimeTypeUtil.getContentType(filename);
							if (StringUtil.isNull(fileContentType)) {
								fileContentType = sysAttMain.getFdContentType();
							}
							// 读取缓存
							if (AttImageUtils.cacheConsulation(request, response, fileContentType, filename)) {
								return null;
							}
							response.setContentType(fileContentType);
							response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题

							if (filename.indexOf(".swf") == -1) {
								response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
							}
							IOUtil.write(in, out);
							return null;
						}
					}
				}
				if (StringUtil.isNotNull(open) || fileContentType.indexOf("image") > -1) { // 图片直接打开，不下载
					// #60553 临时解决ofd格式文件在文件名少于30个汉字时点击阅读变为zip文件下载的问题
//					if ("ofd".equals(extName)) {
//						filename = encodeFileName(request,
//								"（OFD格式的文件需要使用专门的阅读器查看，请您安装福昕阅读器进行阅读。）")
//								+ filename;
//					}
					response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
				} else {
					response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");

				}
				(getServiceImp(request)).findData(sysAttMain.getFdId(), out);
				// 记录附件下载日志
				if (fileContentType.indexOf("image") == -1 && fileContentType.indexOf("html") == -1) {
					// 不是图片类型才记录
					// #124273 html也会走这里，需要排除
					RequestContext context = new RequestContext(request);
					context.setParameter("downloadType", ISysAttDownloadLogService.ATT_DOWNLOAD_TYPE_MANUAL);
					getSysAttDownloadLogService().addDownloadLogByAtt(sysAttMain, context);
				}
				return null;
			} catch (Exception e) {
				streamClose(null, out);
				messages.addError(e);
				logger.error("readDownload错误", e);
			} finally {
				streamClose(null, out);
			}
		}
		if (messages.hasError()) {
			if (StringUtil.isNotNull(open)) {
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}
		return null;
	}



	/**
	 * 对外下载链接，并且带有Expires和Signature参数，签名验证通过可以免登录下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String sign = request.getParameter("Signature");
		String expires = request.getParameter("Expires");
		String reqType = request.getParameter("reqType");
		String fdId = request.getParameter("fdId");
		if (SysAttConstant.REQTYPE_REST.equals(reqType)) {
			if (!getServiceImp(request).validateDownloadSignatureRest(expires, fdId, sign)) {
				messages.addError(new Exception("Signature is invailid."));
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}else{
			if(!getServiceImp(request).validateDownloadSignature(expires,fdId,sign)){
				messages.addError(new Exception("Signature is invailid."));
				KmssReturnPage.getInstance(request).addMessages(messages).addButton
						(KmssReturnPage.BUTTON_CLOSE).save(request);
				return mapping.findForward("failure");
			}
		}
		return this.download(mapping, form, request, response);
	}

	/**
	 * PDF下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadPdf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String convertType = request.getParameter("convertType");
		String converterKey = "toHTML";
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdId);
		if (sysAttMain != null && JgWebOffice.isPDF(sysAttMain.getFdFileName())) {
			downloadConvertFile(request,response, sysAttMain, ".pdf");
			return null;
		}
		InputStream in = null;
		String open = null;
		OutputStream out = response.getOutputStream();
		try {
			HQLInfo hql = new HQLInfo();
			String strWhere = "sysFileConvertQueue.fdFileId=:fdFileId and sysFileConvertQueue.fdConverterKey=:fdConverterKey";
			if(StringUtil.isNotNull(convertType)) {
				strWhere += " and sysFileConvertQueue.fdConverterType=:convertType ";
				hql.setParameter("convertType", convertType);
				converterKey = "toPDF";
			}
			hql.setWhereBlock(strWhere);
			hql.setParameter("fdFileId", sysAttMain.getFdFileId());
			hql.setParameter("fdConverterKey", converterKey);
			Object convertQueue = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService")).findFirstOne(hql);
			if (convertQueue == null) {
				hql = new HQLInfo();
				String where = "sysFileConvertQueue.fdAttMainId=:fdAttMainId and sysFileConvertQueue.fdConverterKey=:fdConverterKey";

				if(StringUtil.isNotNull(convertType)) {
					where += " and sysFileConvertQueue.fdConverterType=:convertType ";
					hql.setParameter("convertType", convertType);
					converterKey = "toPDF";
				}
				hql.setWhereBlock(where);
				hql.setParameter("fdAttMainId", sysAttMain.getFdId());
				hql.setParameter("fdConverterKey", converterKey);
				convertQueue = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService")).findList(hql);
			}
			// 如果转换队列存在数据，有可能更新完附件，正在转换，这时候需要判断转换状态
			if (convertQueue !=null ) {
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) convertQueue;
				int convertStatus = sysFileConvertQueue.getFdConvertStatus();
				// 文件转换成功
				if (convertStatus == 4) {
					String viewerKey = SysAttViewerUtil.getFileViewerKey(sysAttMain);
					String convertFileName = convertPDFFileName(convertType, viewerKey, "toPDF", "pdf");
					SysAttFile sysAsttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
							.getFile(sysAttMain.getFdId());
					boolean exist = false;
					String fdPath = "";
					String pathPrefix = "";
					ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
							.getProxyService(sysAsttFile.getFdAttLocation());
					if (sysAsttFile != null) {
						pathPrefix = sysAsttFile.getFdCata() == null ? null : sysAsttFile.getFdCata().getFdPath();
						fdPath = sysAsttFile.getFdFilePath() + "_convert" + File.separator + convertFileName;
						exist = sysFileLocationProxyService.doesFileExist(fdPath,pathPrefix);
					}
					if (exist) {
						String filename = "";
						String fdFileName = request.getParameter("fdFileName");
						if (StringUtil.isNotNull(fdFileName)) {
							filename = fdFileName + ".pdf";
						} else {
							filename = sysAttMain.getFdFileName().substring(0,
									sysAttMain.getFdFileName().lastIndexOf(".")) + ".pdf";
						}
						if (StringUtil.isNotNull(request.getHeader("User-Agent"))) {
							filename = encodeFileName(request, filename, false);
						}
						String fileContentType = FileMimeTypeUtil.getContentType(filename);
						if (StringUtil.isNull(fileContentType)) {
							fileContentType = "application/x-download";
						}
						response.reset();
						response.setContentType(fileContentType);
						response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
						open = request.getParameter("open");
						if (StringUtil.isNotNull(open)) {
							response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
						} else {
							response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
						}
						in = sysFileLocationProxyService.readFile(fdPath,pathPrefix);
						response.setContentLength(in.available());
						in = new DecryptionInputStream(in);
						IOUtil.write(in, out);
						return null;
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error("download错误", e);
		} finally {
		}
		if (messages.hasError()) {
			if (StringUtil.isNotNull(open)) {
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}
		return null;
	}

	/**
	 * OFD下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadOfd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String convertType = request.getParameter("convertType");
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdId);
		if (sysAttMain != null && JgWebOffice.isOFD(sysAttMain.getFdFileName())) {
			downloadConvertFile(request,response, sysAttMain, ".ofd");
			return null;
		}
		InputStream in = null;
		String open = null;
		OutputStream out = response.getOutputStream();
		try {
			HQLInfo hql = new HQLInfo();
			String strWhere = "sysFileConvertQueue.fdFileId=:fdFileId and sysFileConvertQueue.fdConverterKey=:fdConverterKey";

			if(StringUtil.isNotNull(convertType)) {
				strWhere += " and sysFileConvertQueue.fdConverterType=:convertType ";
				hql.setParameter("convertType", convertType);
			}
			hql.setParameter("fdFileId", sysAttMain.getFdFileId());
			hql.setParameter("fdConverterKey", "toOFD"); //查找OFD
			hql.setWhereBlock(strWhere);

			Object convertQueue = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService")).findFirstOne(hql);
			if (convertQueue == null) {
				hql = new HQLInfo();
				String where = "sysFileConvertQueue.fdAttMainId=:fdAttMainId and sysFileConvertQueue.fdConverterKey=:fdConverterKey";
				if(StringUtil.isNotNull(convertType)) {
					where += " and sysFileConvertQueue.fdConverterType=:convertType ";
					hql.setParameter("convertType", convertType);
				}
				hql.setWhereBlock(where);
				hql.setParameter("fdAttMainId", sysAttMain.getFdId());
				hql.setParameter("fdConverterKey", "toOFD");
				convertQueue = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService")).findFirstOne(hql);
			}
			// 如果转换队列存在数据，有可能更新完附件，正在转换，这时候需要判断转换状态
			if (convertQueue != null) {
				SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) convertQueue;
				int convertStatus = sysFileConvertQueue.getFdConvertStatus();
				// 文件转换成功
				if (convertStatus == 4) {
					String convertFileName = convertOFDFileName(convertType,"toOFD", "ofd");

					//	String viewerKey = SysAttViewerUtil.getFileViewerKey(sysAttMain);
					//	if (StringUtil.isNotNull(viewerKey)) {
					//		convertFileName = viewerKey + "_ofd";
					//	}

					SysAttFile sysAsttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
							.getFile(sysAttMain.getFdId());
					boolean exist = false;
					String fdPath = "";
					String pathPrefix = "";
					ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
							.getProxyService(sysAsttFile.getFdAttLocation());
					if (sysAsttFile != null) {
						pathPrefix = sysAsttFile.getFdCata() == null ? null : sysAsttFile.getFdCata().getFdPath();
						fdPath = sysAsttFile.getFdFilePath() + "_convert" + File.separator + convertFileName;
						exist = sysFileLocationProxyService.doesFileExist(fdPath);
					}
					if (exist) {
						String filename = "";
						String fdFileName = request.getParameter("fdFileName");
						if (StringUtil.isNotNull(fdFileName)) {
							filename = fdFileName + ".ofd";
						} else {
							filename = sysAttMain.getFdFileName().substring(0,
									sysAttMain.getFdFileName().lastIndexOf(".")) + ".ofd";
						}
						if (StringUtil.isNotNull(request.getHeader("User-Agent"))) {
							filename = encodeFileName(request, filename, false);
						}
						String fileContentType = FileMimeTypeUtil.getContentType(filename);
						if (StringUtil.isNull(fileContentType)) {
							fileContentType = "application/x-download";
						}
						response.reset();
						response.setContentType(fileContentType);
						response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
						open = request.getParameter("open");
						if (StringUtil.isNotNull(open)) {
							response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
						} else {
							response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
						}
						in = sysFileLocationProxyService.readFile(fdPath,pathPrefix);
						response.setContentLength(in.available());
						in = new DecryptionInputStream(in);
						IOUtil.write(in, out);
						return null;
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error("download错误", e);
		} finally {
		}
		if (messages.hasError()) {
			if (StringUtil.isNotNull(open)) {
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}
		return null;
	}

	/**
	 * 直接下载OFD或PDF文件
	 *
	 * @param request
	 * @param response
	 * @param sysAttMain
	 * @param suffix  文件后缀名
	 */
	public void downloadConvertFile( HttpServletRequest request,
								 HttpServletResponse response, SysAttMain sysAttMain, String suffix) {
		InputStream in = null;
		String open = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			SysAttFile sysAsttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
					.getFile(sysAttMain.getFdId());
			ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
					.getProxyService(sysAsttFile.getFdAttLocation());
			String fdPath = "";
			String pathPrefix ="";
			Boolean exist = false;
			if (sysAsttFile != null) {
				pathPrefix = sysAsttFile.getFdCata() == null ? null : sysAsttFile.getFdCata().getFdPath();
				fdPath = sysAsttFile.getFdFilePath();
				exist = sysFileLocationProxyService.doesFileExist(fdPath,pathPrefix);
			}

			if (exist) {
				String filename = "";
				String fdFileName = request.getParameter("fdFileName");
				if (StringUtil.isNotNull(fdFileName)) {
					filename = fdFileName + suffix;
				} else {
					filename = sysAttMain.getFdFileName().substring(0,
							sysAttMain.getFdFileName().lastIndexOf(".")) + suffix;
				}
				if (StringUtil.isNotNull(request.getHeader("User-Agent"))) {
					filename = encodeFileName(request, filename, false);
				}
				String fileContentType = FileMimeTypeUtil.getContentType(filename);
				if (StringUtil.isNull(fileContentType)) {
					fileContentType = "application/x-download";
				}
				response.reset();
				response.setContentType(fileContentType);
				response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
				open = request.getParameter("open");
				if (StringUtil.isNotNull(open)) {
					response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
				} else {
					response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
				}
				in = sysFileLocationProxyService.readFile(fdPath,pathPrefix);
				response.setContentLength(in.available());
				in = new DecryptionInputStream(in);
				IOUtil.write(in, out);
			}


		} catch (Exception e) {
			logger.error("下载"+ suffix +"文件失败：",e);
		} finally {
			try {
				if(out != null) {
					out.close();
				}
				if(in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("下载" + suffix + "文件关闭流失败：",e);
			}

		}
	}


	/**
	 * OFD文件类型
	 * @param convertType
	 * @param pre
	 * @param end
	 * @return
	 */
	public String convertOFDFileName(String convertType, String pre, String end) {
		String fileName = "";
		if(StringUtil.isNotNull(convertType)) {
			if ("wpsCenter".equals(convertType)) {
				fileName = pre + "-WPSCenter_" + end;
			} else if ("wps".equals(convertType)) {
				fileName = pre + "-WPS_" + end;
			} else if ("skofd".equals(convertType)) {
				fileName = pre + "-Suwell_" + end;
			} else if ("dianju".equals(convertType)) {
				fileName = pre + "-Dianju_" + end;
			} else if("foxit".equals(convertType)) {
				fileName = pre + "-Foxit_" + end;
			}
		}

		return fileName;
	}

	/**
	 * PDF文件类型
	 * @param convertType
	 * @param pre
	 * @param end
	 * @return
	 */
	public String convertPDFFileName(String convertType, String viewerKey, String pre, String end) {
		String fileName = "toHTML-Aspose_pdf";

		if (StringUtil.isNotNull(viewerKey)) {
			fileName = viewerKey + "_pdf";
		}
		if(StringUtil.isNotNull(convertType)) {
			if ("wpsCenter".equals(convertType)) {
				fileName = pre + "-WPSCenter_" + end;
			} else if ("wps".equals(convertType)) {
				fileName = pre + "-WPS_" + end;
			} else if ("dianju".equals(convertType)) {
				fileName = pre + "-Dianju_" + end;
			} else if("foxit".equals(convertType)) {
				fileName = pre + "-Foxit_" + end;
			}
		}

		return fileName;
	}
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {

		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		InputStream in = null;
		String open = null;

		String wpsOaassistfilename = "";
		String ocxType = "";
		String from = request.getParameter("from");
		//点聚不走直连
		if (!SysAttConfigUtil.isDianJuOLEnabled()) {
			if (downloadDirect(request, response)) {
				return null;
			}
		}

		try {

			String fdDefaultName = request.getParameter("fdDefaultName");
			String wpsExtAppModel = request.getParameter("wpsExtAppModel");

			List<SysAttMain> sysAttMains = getAttMains(request);
			out = response.getOutputStream();
			if (logger.isDebugEnabled())
			{
				logger.debug("SysAttMainAction:download:sysAttMains:" + sysAttMains);
			}
			if (sysAttMains == null || sysAttMains.isEmpty()) {
				printAttIsNull(request, response, out, messages);
			} else {
				out = response.getOutputStream();
				String filename = "";
				String fileContentType = null;
				long fileSize = 0;
				if (logger.isDebugEnabled())
				{
					logger.debug("SysAttMainAction:download:sysAttMains.size:" + sysAttMains.size());
				}
				if (sysAttMains.size() == 1) {
					SysAttMain sysAttMain = sysAttMains.get(0);
					commonLogFind(request,sysAttMain,"download");
					if (StringUtil.isNotNull(request.getHeader("User-Agent"))) {
						filename = encodeFileName(request, sysAttMain.getFdFileName(), false);
					}
					wpsOaassistfilename = sysAttMain.getFdFileName();
					ocxType = SysAttOcxUtil.getOcxType(sysAttMain.getFdKey(), sysAttMain.getFdModelName());

					fileContentType = FileMimeTypeUtil.getContentType(filename);
					if (StringUtil.isNull(fileContentType)) {
						fileContentType = sysAttMain.getFdContentType();
					}

					if (logger.isDebugEnabled())
					{
						logger.debug("SysAttMainAction:download:sysAttMain.getInputStream:" + sysAttMain.getInputStream());
					}
					in = sysAttMain.getInputStream();
					fileSize = sysAttMain.getFdSize().intValue();
					/*
					 * 注意，in为DecryptionInputStream，作用为处理解密文件，
					 * 由于附件查找读取采用的是API：findModelsByIds，
					 * 故DecryptionInputStream处理的对象是FileInputStream
					 * ,所以这里可以放心使用available方法获取文件大小
					 */
					if (logger.isDebugEnabled())
					{
						logger.debug("SysAttMainAction:download:sysAttMain.in:" + in);
					}

					int tmpSize = 0;
					if(in != null)
					{
						tmpSize = in.available();
					}

					if (tmpSize != fileSize && tmpSize > 0) {
						fileSize = tmpSize;
					}
				} else {
					// 打包下载
					filename = StringUtil.isNotNull(fdDefaultName) ? encodeFileName(request, fdDefaultName + ".zip")
							: encodeFileName(request, fetchMainModelSubject(request, sysAttMains.get(0)) + ".zip");
					if (".zip".equals(filename)) {// 当getDisplayProperty()为fdId时取第一个附件名称
						String fileName = sysAttMains.get(0).getFdFileName();
						fileName = fileName.substring(0,
								fileName.lastIndexOf(".")) + ".zip";
						filename = encodeFileName(request, fileName);
					}
					fileContentType = FileMimeTypeUtil.getContentType(filename);
					for(SysAttMain sysAttMain:sysAttMains){
						commonLogFind(request,sysAttMain,"download");
					}
				}

				response.reset();
				if (fileSize > 0) {
					response.setContentLength((int) fileSize);
				}
				// 读取缓存
				if (AttImageUtils.cacheConsulation(request, response, fileContentType, filename)) {
					// 记录附件下载日志
					getSysAttDownloadLogService().addDownloadLogByAttList(sysAttMains, new RequestContext(request));
					return null;
				}
				if(SysAttWpsCloudUtil.isEnableMobile(isMobileRequest(request))) //移动端WPS
				{
					logger.info("政务微信下载文件。");
					response.setContentType("application/octet-stream");
				}
				else
				{
					response.setContentType(fileContentType);
				}
				//文件名包含了“:”符号，需要处理掉，不然后无法获取文件后缀
				String getExtensionFileName = filename;
				if(getExtensionFileName.contains(":")) {
					getExtensionFileName = getExtensionFileName.replaceAll(":", "");
				}
				String extName = FilenameUtils.getExtension(getExtensionFileName);
				// 解决包含中文的txt文件在浏览器直接打开乱码问题
				if ("txt".equals(extName)) {
					String charCode = SysAttUtil.getCharset(in);
					if (StringUtil.isNotNull(charCode)) {
						response.setCharacterEncoding(charCode);
					}
				}
				response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
				open = request.getParameter("open");
				if (StringUtil.isNotNull(open)) {
					response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
				} else {
					if (MobileUtil.DING_ANDRIOD == MobileUtil
							.getClientType(request)) {
						// 钉钉ISO编码问题
						response.setHeader("Content-Disposition",
								"attachment;filename*=utf-8''"
										+ filename);
					} else {

						if (SysAttWpsoaassistUtil.isEnable() ||
								SysAttOcxUtil.OCX_WPS_OA_ASSIST_VALUE
										.equals(ocxType)|| "kmImissive".equals(wpsExtAppModel)) {
							//移动端WPS
							if(SysAttWpsCloudUtil.isEnableMobile(isMobileRequest(request)))
							{
								response.setHeader("Content-Disposition",
										"attachment; filename="
												+ java.net.URLEncoder.encode(
												wpsOaassistfilename,
												"UTF-8")+"; md5="+MD5Util.getMD5String(in));
							}else
							{
								if ("kmImissive".equals(wpsExtAppModel)) {
									response.setHeader("Content-Disposition",
											"attachment; filename="
													+ java.net.URLEncoder.encode(wpsOaassistfilename, "UTF-8"));
								} else {
									if ("wpsOAasisst".equals(from)) {
										response.setHeader("Content-Disposition", "attachment; filename="
												+ java.net.URLEncoder.encode(wpsOaassistfilename, "UTF-8"));
									} else {
										response.setHeader("Content-Disposition",
												"attachment;filename=\"" + filename + "\"");
									}
								}
							}

						} else if(SysAttConfigUtil.isWritingByWps() && "kmsCowritting".equals(from)) {

							//移动端WPS
							if(SysAttWpsCloudUtil.isEnableMobile(isMobileRequest(request)))
							{
								response.setHeader("Content-Disposition",
										"attachment; filename="
												+ java.net.URLEncoder.encode(
												wpsOaassistfilename,
												"UTF-8")+"; md5="+MD5Util.getMD5String(in));
							}else
							{
								response.setHeader("Content-Disposition",
										"attachment; filename="
												+ java.net.URLEncoder.encode(
												wpsOaassistfilename,
												"UTF-8"));
							}

						} else {

							//移动端WPS
							if(SysAttWpsCloudUtil.isEnableMobile(isMobileRequest(request)))
							{
								response.setHeader("Content-Disposition",
										"attachment; filename="
												+ java.net.URLEncoder.encode(
												wpsOaassistfilename,
												"UTF-8")+"; md5="+MD5Util.getMD5String(in));
							}else
							{
								response.setHeader("Content-Disposition",
										"attachment;filename=\"" + filename + "\"");
							}

						}
					}

				}

				if(SysAttConfigUtil.pdfReadByFoxitPC() || SysAttConfigUtil.pdfReadByFoxitMobile()) {
					//福昕阅读PDF需要加入此代码
					response.setHeader("Access-Control-Allow-Origin", "*");
					response.setHeader("Access-Control-Allow-Credentials", "true");
					response.setHeader("Access-Control-Allow-Methods", "*");
					response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
					response.setHeader("Access-Control-Expose-Headers", "*");
				}

				if (sysAttMains.size() == 1) {
					// 使用浏览器或移动端插件直接打开PDF文件
					if(SysAttUtil.viewByBrowser(sysAttMains.get(0))) {
						response.setHeader("Access-Control-Allow-Origin", "*");
					}
					(getServiceImp(request)).findData(sysAttMains.get(0).getFdId(), out);
				} else {
					ZipOutputStream zipOut = null;
					try {
						zipOut = new ZipOutputStream(out);
						zipOut.setEncoding("GBK");
						for (Iterator iterator = sysAttMains.iterator(); iterator.hasNext();) {
							SysAttMain tmpSysAttMain = (SysAttMain) iterator.next();
							in = tmpSysAttMain.getInputStream();
							if (in != null) {
								zipOut.putNextEntry(new ZipEntry(tmpSysAttMain.getFdFileName()));
								// zipout在批量下载时，不能马上关闭
								IOUtils.copy(in, zipOut);
								in.close();
								in = null;
							}
						}
					} catch (Exception e) {
						if (zipOut != null) {
							IOUtils.closeQuietly(zipOut);
						}
					} finally {
						if (zipOut != null) {
							IOUtils.closeQuietly(zipOut);
						}
					}
				}
				// 记录附件下载日志
				if(!"kmImissive".equals(wpsExtAppModel)){
					getSysAttDownloadLogService().addDownloadLogByAttList(sysAttMains, new RequestContext(request));
				}
				return null;
			}
		} catch (Exception e) {
			streamClose(in, out);
			messages.addError(e);
			logger.error("download错误", e);
		} finally {
			streamClose(in, out);
		}
		if (messages.hasError()) {
			if (StringUtil.isNotNull(open)) {
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}
		return null;
	}

	public ActionForward downloadToOther(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		if (!"POST".equals(request.getMethod())) {
			throw new UnexpectedRequestException();
		}

		String key = request.getParameter("key");

		String fdId = request.getParameter("fdId");

		String t = request.getParameter("t");

		Long time = null;

		if(StringUtil.isNotNull(t)) {
			time = Long.valueOf(t);
		}

		if (StringUtil.isNotNull(key) && StringUtil.isNotNull(fdId) && getProviderMap().containsKey(key)) {

			ISysAttachmentTransmissionProvider provider = (ISysAttachmentTransmissionProvider) getProviderMap()
					.get(key);

			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(provider.downloadToOther(fdId, time));
			return null;
		}else {
			return null;
		}
	}

	public ActionForward isSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");

		String fdId = request.getParameter("fdId");

		String t = request.getParameter("t");

		String type = request.getParameter("type");

		Long time = null;

		if(StringUtil.isNotNull(t)) {
			time = Long.valueOf(t);
		}

		if (StringUtil.isNotNull(key) && StringUtil.isNotNull(fdId) && getProviderMap().containsKey(key)) {

			ISysAttachmentTransmissionProvider provider = (ISysAttachmentTransmissionProvider) getProviderMap()
					.get(key);

			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(provider.isSuccess(fdId, type, time));
			return null;
		}else {
			return null;
		}
	}

	public ActionForward isShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");

		if (StringUtil.isNotNull(key) && getProviderMap().containsKey(key)) {

			ISysAttachmentTransmissionProvider provider = (ISysAttachmentTransmissionProvider) getProviderMap()
					.get(key);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(provider.isShow());
			return null;
		}else {
			return null;
		}
	}

	public HashMap getProviderMap() {
		if (providerMap == null || providerMap.isEmpty()) {
			providerMap = new HashMap();
			IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.attachment.transmission",
					"*", "transmission");
			if (extensions == null || extensions.length == 0) {
				return providerMap;
			}
			for (int i = 0; i < extensions.length; i++) {
				String key = (String) Plugin.getParamValue(extensions[i],
						"key");

				providerMap.put(key, Plugin.getParamValue(extensions[i],
						"provider"));
			}
		}
		return providerMap;
	}

	private String fetchMainModelSubject(HttpServletRequest request, SysAttMain sysAttMain) {
		String filename = "download";
		if (sysAttMain != null) {
			Object mainModel = null;
			try {
				mainModel = (getServiceImp(request)).getMainModel(sysAttMain);
			} catch (Exception e1) {
				logger.error("压缩过程获取主文档出错,错误信息:", e1);
			}
			return SysAttUtil.getSubjectByModel(mainModel, filename);
		}
		return filename;
	}

	/**
	 * 文件名编码
	 *
	 * @param request
	 * @param oldFileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeFileName(HttpServletRequest request, String oldFileName) throws UnsupportedEncodingException {
		return encodeFileName(request, oldFileName, true);
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
	private String encodeFileName(HttpServletRequest request, String oldFileName, boolean isEncode)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
				|| userAgent.indexOf("EDGE") > -1
				|| (MobileUtil.DING_ANDRIOD == MobileUtil
				.getClientType(request))) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			oldFileName = oldFileName.replace("+", "%20");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"), "ISO8859-1");
		}
		if (isEncode) { // 如果是在线查看时，文件名会追加到URL上，此时需要转码。如果是下载文件，则不需要转码
			oldFileName = oldFileName.replace("+", "%20");
		}
		//过滤文件名中的换行和回车
		oldFileName = oldFileName.replaceAll("\r|\n", "");
		return oldFileName;
	}

	/****
	 * 下载pic文件专用action，无权限拦截
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadPic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		String open = null;
		try {
			List<SysAttMain> sysAttMains = getAttMains(request);
			out = response.getOutputStream();
			if (sysAttMains == null || sysAttMains.isEmpty()) {
				printAttIsNull(request, response, out, messages);
			} else {
				SysAttMain sysAttMain = sysAttMains.get(0);
				commonLogFind(request,sysAttMain,"downloadPic");
				if (!"pic".equals(sysAttMain.getFdAttType())) {
					messages.addError(new KmssMessage("sys-attachment:sysAttMain.error.downloadPic"));
				} else {
					int fileSize = sysAttMain.getFdSize().intValue();
					String filename = encodeFileName(request, sysAttMain.getFdFileName(), false);
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
					response.setHeader("Pragma", "public");// 解决ie6下载附件问题,ie8在https下的下载附件问题
					open = request.getParameter("open");
					if (StringUtil.isNotNull(open)) {
						response.setHeader("Content-Disposition", "filename=\"" + filename + "\"");
					} else {
						response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
					}
					out = response.getOutputStream();
					(getServiceImp(request)).findData(sysAttMain.getFdId(), out);
					return null;
				}
			}
		} catch (Exception e) {
			streamClose(null, out);
			messages.addError(e);
			logger.error("downloadPic错误", e);
		} finally {
			streamClose(null, out);
		}
		if (messages.hasError()) {
			if (StringUtil.isNotNull(open)) {
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);
				return mapping.findForward("failure");
			}
		}
		return null;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String format = request.getParameter("format");
		String forward = "upload_result";
		try {
			if (fdId == null || fdId.trim().length() == 0) {
				messages.addError(new KmssMessage("Key not found."));
			} else {
				(getServiceImp(request)).deleteAtt(fdId);
			}
			if (StringUtil.isNotNull(format)) {
				Map rtnMap = new HashMap();
				rtnMap.put("status", "1");
				request.setAttribute("resultMap", rtnMap);
				request.setAttribute("format", format);
				forward = "upload_result";
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward(forward);
		}
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("添加文档附件。。。。。" + request.getMethod());
			}
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				return update(mapping, form, request, response);
			}

			List sysAttMains = new ArrayList();
			SysAttMainForm attMainForm = (SysAttMainForm) form;
			if (logger.isDebugEnabled()) {
				logger.debug("files:" + attMainForm.getFormFiles());
			}
			for (Iterator it = attMainForm.getFormFiles().iterator(); it.hasNext();) {
				Object ofile = it.next();
				if (ofile != null) {
					SysAttMain sysAttMain = new SysAttMain();
					String id = sysAttMain.getFdId();
					BeanUtils.copyProperties(sysAttMain, attMainForm);
					sysAttMain.setFdId(id);
					FormFile formFile = (FormFile) ofile;
					if (formFile.getFileName() == null || formFile.getFileName().trim().length() == 0) {
						continue;
					}
					// 保存文件
					String fileName = formFile.getFileName();
					if (logger.isDebugEnabled()) {
						logger.debug("save files:" + fileName);
					}

					sysAttMain.setFdFileName(fileName);
					sysAttMain.setDocCreateTime(new Date());
					sysAttMain.setFdSize(new Double(formFile.getFileSize()));

					String contentType = FileMimeTypeUtil.getContentType(fileName);
					if (StringUtil.isNull(contentType)) {
						contentType = formFile.getContentType();
					}

					sysAttMain.setFdContentType(contentType);
					sysAttMain.setInputStream(formFile.getInputStream());
					boolean allowLogOper = UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_ADD, SysAttMain.class.getName());
					if(allowLogOper){
						UserOperContentHelper.putAdd(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName())
								.putSimple("fdContentType", contentType);
					}
					sysAttMains.add(sysAttMain);
					if (logger.isDebugEnabled()) {
						logger.debug("save files:" + fileName + " complete!");
					}

				}
			}
			sysAttMains = (getServiceImp(request)).add(sysAttMains);
			String sysAttMainIds = "";
			for (Iterator it = sysAttMains.iterator(); it.hasNext();) {
				SysAttMain sysAttMain = (SysAttMain) it.next();
				sysAttMainIds += sysAttMain.getFdId();
				if (it.hasNext()) {
					sysAttMainIds += ";";
				}
				if (logger.isDebugEnabled()) {
					logger.debug("sysAttMainIds：" + sysAttMainIds);
				}
			}
			request.setAttribute("sysAttMains", sysAttMains);
			request.setAttribute("sysAttMainIds", sysAttMainIds);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			if (logger.isDebugEnabled()) {
				logger.debug("save fail!");
			}
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			logger.debug("save success!");
			return mapping.findForward("upload_success");
		}
	}

	/**
	 * 将浏览器提交的表单数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			String fdId = request.getParameter("fdId");
			if (StringUtil.isNull(fdId)) {
				return save(mapping, form, request, response);
			}

			SysAttMain sysAttMain = null;
			if (fdId != null && fdId.trim().length() > 0) {
				String l_fdId = fdId;
				sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(l_fdId);
			}

			if (sysAttMain != null) {
				List sysAttMains = new ArrayList();
				SysAttMainForm attMainForm = (SysAttMainForm) form;
				/*
				 * TODO 在线编辑保存只可能出现一个上传文件，所以这个循环可能只执行一次
				 */
				for (Iterator it = attMainForm.getFormFiles().iterator(); it.hasNext();) {
					Object ofile = it.next();
					if (ofile != null) {
						FormFile formFile = (FormFile) ofile;
						if (formFile.getFileName() == null || formFile.getFileName().trim().length() == 0) {
							continue;
						}
						String fileName = formFile.getFileName();
						String fdAttType = request.getParameter("fdAttType");
						String extName = FilenameUtils.getExtension(fileName);
						if ("office".equalsIgnoreCase(fdAttType)) {
							if (StringUtil.isNotNull(fileName)) {
								if ("docx".equalsIgnoreCase(extName)) {
									fileName = fileName.substring(0, fileName.length() - 1);
								} else if ("xlsx".equalsIgnoreCase(extName)) {
									fileName = fileName.substring(0, fileName.length() - 1);
								} else if ("pptx".equalsIgnoreCase(extName)) {
									fileName = fileName.substring(0, fileName.length() - 1);
								}
							}
						}
						boolean allowLogOper = UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE, SysAttMain.class.getName());
						if(allowLogOper){
							UserOperContentHelper.putUpdate(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName())
									.putSimple("fdFileName", sysAttMain.getFdFileName(), fileName);
						}
						sysAttMain.setFdFileName(fileName);
						sysAttMain.setFdSize(new Double(formFile.getFileSize()));
						String contentType = FileMimeTypeUtil.getContentType(fileName);

						if (StringUtil.isNull(contentType)) {
							contentType = formFile.getContentType();
						}
						if(allowLogOper){
							UserOperContentHelper.putUpdate(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName())
									.putSimple("fdContentType", sysAttMain.getFdContentType(), contentType);
						}
						sysAttMain.setFdContentType(contentType);
						sysAttMain.setInputStream(formFile.getInputStream());
						sysAttMains.add(sysAttMain);
					}
				}
				sysAttMains = (getServiceImp(request)).update(sysAttMains);
				request.setAttribute("sysAttMains", sysAttMains);
				request.setAttribute("sysAttMainIds", fdId);
			} else {
				messages.addError(new KmssMessage("Key not found."));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("upload_success");
		}
	}

	/**
	 * 在线编辑文档。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		SysAttMain sysAttMain = null;
		String fdForceUseJG = request.getParameter("fdForceUseJG");
		String isJGSignaturePDF = request.getParameter("isJGSignaturePDF");
		boolean isMobile = isMobileRequest(request);
		try {
			String fdId = request.getParameter("fdId");
			if (fdId != null && fdId.trim().length() > 0) {
				String l_fdId = fdId;
				sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(l_fdId);
			}
			if (sysAttMain != null) {

				//先根据业务模块配置跳转
				String forward = SysAttOcxUtil.getOcxPageForward(sysAttMain.getFdKey(),
						sysAttMain.getFdModelName(), SysAttOcxUtil.EDIT);
				if (StringUtil.isNotNull(forward) && !isMobile) {
					if ("editonline_wps_cloud".equals(forward)) {
						if (!SysAttWpsCloudUtil.isAttHadSyncByAttMainId(sysAttMain.getFdId())) {
							SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
						}
					}
					SysAttMainForm sysAttMainForm = new SysAttMainForm();
					BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
					request.setAttribute("sysAttMainForm", sysAttMainForm);
					// 保存fdModelName
					request.setAttribute("fdModelName", sysAttMain.getFdModelName());
					return mapping.findForward(forward);
				}

				if(SysAttConfigUtil.isEnableAttachmentSignature() &&
						"2".equals(SysAttConfigUtil.getAttachmentSignatureType()) &&
						(JgWebOffice.isOFD(sysAttMain.getFdFileName())
						|| JgWebOffice.isPDF(sysAttMain.getFdFileName()))) {

					dianjuFormat(request, sysAttMain);
					return mapping.findForward("viewonline_dianju_format");
				}
				if(SysAttBase.HISTORY_NAME.equals(sysAttMain.getFdKey())) {//历史版本附件不允许编辑
					messages.addError(new KmssMessage(ResourceUtil.getString("sysAttMain.view.history.deny", "sys-attachment")));
					KmssReturnPage.getInstance(request).addMessages(messages).save(request);
					return mapping.findForward("failure");
				}

				SysAttMainForm sysAttMainForm = new SysAttMainForm();
				BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
				if(UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND, SysAttMain.class.getName())){
					UserOperContentHelper.putAdd(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName());
				}
				request.setAttribute("sysAttMainForm", sysAttMainForm);
				request.setAttribute("canPrint", "1");
				// 保存fdModelName
				request.setAttribute("fdModelName", sysAttMain.getFdModelName());
			} else {
				messages.addError(new KmssMessage("Key not found."));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return mapping.findForward("failure");
		} else {
			boolean isWpsoaassist=SysAttWpsoaassistUtil.isEnable();
			boolean isWpsoaassistEmbed=SysAttWpsoaassistUtil.isWPSOAassistEmbed();
			if (logger.isDebugEnabled()) {
				logger.debug("isWpsoaassist={},isWpsoaassistEmbed={},fdForceUseJG={},isJGSignaturePDF={},wpscenter={},wpscloud={},WpsWebOffice={}",
						isWpsoaassist,isWpsoaassistEmbed,fdForceUseJG,isJGSignaturePDF,SysAttWpsCenterUtil.isEnable(),SysAttWpsCloudUtil.isEnable(),SysAttWpsWebOfficeUtil.isEnable());
			}
			if (!isMobile&&isWpsoaassist&&isWpsoaassistEmbed&&(isWord(sysAttMain.getFdFileName())||isExcel(sysAttMain.getFdFileName())||isPpt(sysAttMain.getFdFileName()))) {
				String userAgent = request.getHeader("user-agent");
				boolean winFlag = userAgent.toLowerCase().contains("win");
				if(!winFlag) {
					return mapping.findForward("editonline_wps_oaassist_linux");
				}
			}

			if ("true".equals(fdForceUseJG)) {
				return mapping.findForward("editonline_jg");
			} else {
				if("true".equals(isJGSignaturePDF)) {
					String pdfJgSignKey = request.getParameter("pdfJgSignKey");
					if("true".equals(pdfJgSignKey)){
						request.setAttribute("pdfSaveGw","true");
					}

					return mapping.findForward("editonline_jgPdfSignature");
				}

				if (SysAttWpsWebOfficeUtil.isEnable()) {
					return mapping.findForward("editonline_wps");
				}else if(SysAttWpsCenterUtil.isEnable()||(isMobile &&SysAttWpsCenterUtil.isWPSCenterEnableMobile(isMobile))){
					//文档中台
					return mapping.findForward("editonline_wps_center");
				} else if ((!isMobile && SysAttWpsCloudUtil.isEnable())
						|| (isMobile && SysAttWpsCloudUtil.isEnable(isMobile))) {
					// wps私有云查看
					if (sysAttMain != null && !SysAttWpsCloudUtil
							.isAttHadSyncByAttMainId(sysAttMain.getFdId())) {
						SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
						return mapping.findForward("editonline_wps_cloud");
					} /*
					 * else if (sysAttMain != null) {
					 * SysAttWpsCloudUtil.syncAttToUpdateByMainId(sysAttMain
					 * .getFdId()); }
					 */
					return mapping.findForward("editonline_wps_cloud");
				}else {
					boolean isJGEnabled = JgWebOffice.isJGEnabled();
					if (isJGEnabled) {
						return mapping.findForward("editonline_jg");
					} else {
						return mapping.findForward("editonline");
					}
				}
			}
		}
	}

	/**
	 * 在线编辑下载文档 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward editDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String head = request.getHeader("User-Agent");
			if ("LKS Software".equals(head)) {
				return download(mapping, form, request, response);
			} else {
				messages.addMsg(new KmssMessage("Illegal access error!"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return mapping.findForward("failure");
		} else {
			boolean isJGEnabled = JgWebOffice.isJGEnabled();
			if (isJGEnabled) {
				return mapping.findForward("editonline_jg");
			} else {
				return mapping.findForward("editonline");
			}
		}

	}

	private ActionForward routeView(ActionMapping mapping,HttpServletRequest request,HttpServletResponse response,SysAttMain sysAttMain,OutputStream out, InputStream in) throws Exception {
		/**
		 * 切换阅读模式
		 */
		SysAttMainForm sysAttMainForm = new SysAttMainForm();
		BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
		request.setAttribute("sysAttMainForm", sysAttMainForm);
		String viewType = request.getParameter("viewType");
		Boolean isWindows = SysAttWpsCloudUtil.checkWpsPreviewIsWindows();
		Boolean isLinux = SysAttWpsCloudUtil.checkWpsPreviewIsLinux();
		String directPreview = request.getParameter("directPreview");

		String extName = SysAttViewerUtil.getExtName(sysAttMain.getFdFileName());

		// WPS中台痕迹稿中是过滤器显示痕迹
		if(StringUtil.isNotNull(directPreview)) {
			request.setAttribute("directPreview", directPreview);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("文件阅读模式路由开始, viewType = {}, onlineToolType = {}, readOLConfig = {}, isWindows = {}, isLinux = {}, attMainId = {}, fileName = {}",
					viewType, SysAttConfigUtil.getOnlineToolType(), SysAttConfigUtil.getReadOLConfig(), isWindows, isLinux, sysAttMain.getFdId(), sysAttMain.getFdFileName());
		}
		if (StringUtils.isNotEmpty(viewType)) {
			if ("0".equals(SysAttConfigUtil.getOnlineToolType()) || "jg".equals(viewType)) {//金格
				String fdFileName = sysAttMain.getFdFileName();
				if (JgWebOffice.isPDF(fdFileName) || JgWebOffice.isOFD(fdFileName)) {
					if (JgWebOffice.isOfficePDFJudge()) {
						if ((JgWebOffice.isOFD(fdFileName) && JgWebOffice.isJGPDF2018Enabled()) || JgWebOffice.isPDF(fdFileName)) {
							return mapping.findForward("viewonline_pdf");
						}
					}
				} else {
					return mapping.findForward("viewonline_jg");
				}
			}
		}
		String pdfJgSignKey = request.getParameter("pdfJgSignKey");
		if(StringUtil.isNotNull(pdfJgSignKey)){
			return viewByAspose(mapping, request, response, out, in, sysAttMain);
		}
		/**
		 * #149006
		 * 所有预览使用在线预览配置，先保留原逻辑，放在此处将影响降到最小
		 */
		switch (SysAttConfigUtil.getReadOLConfig()) {
			case "1":
				return viewByAspose(mapping, request, response, out, in, sysAttMain);
			case "2":
				//wps在线预览
				if (isLinux) {
					request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(sysAttMain.getFdId()));
					return mapping.findForward("viewonline_wps_preview");
				}
				if(isWindows) {
					request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsWindowPreviewUrl(sysAttMain.getFdId()));
					return mapping.findForward("viewonline_wps_preview");
				}
				break;
			case "3":
				//wps云文档预览
				if (!SysAttWpsCloudUtil.isAttHadSyncByAttMainId(sysAttMain.getFdId())) {
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
				}
				return mapping.findForward("viewonline_wps_cloud");
			case "4":
				//点聚轻阅读
				if(!SysAttViewerUtil.isPicAtt(sysAttMain)){
					List typeLs = Arrays.asList(SysAttConstant.DIANJU_VIEW_TYPE);
					if(!typeLs.contains(extName)) {
						return null;
					} else {
						request.setAttribute("attMain", sysAttMain);
						request.setAttribute("previewUrl", getSysAttachmentDianJuProvider().getPreviewUrl(sysAttMain, UserUtil.getKMSSUser().getUserName()));
						if ("true".equals(request.getParameter("dj_ifr"))) {//iframe
							return mapping.findForward("viewonline_dianju");
						} else {//include
							return mapping.findForward("viewonline_dianju_preview");
						}
					}
				}
				break;
			case "5":
				//文档中台查看
				if(!SysAttViewerUtil.isPicAtt(sysAttMain)){
					List typeLs = Arrays.asList(SysAttConstant.CENTER_VIEW_TYPE);
					if(!typeLs.contains(extName)){
						return null;
					}else {
						return mapping.findForward("viewonline_wps_center");
					}
				}
				break;
			case "6":

				// 福昕轻阅读
				if(!SysAttViewerUtil.isPicAtt(sysAttMain)){
					List typeLs = Arrays.asList(SysAttConstant.FOXIT_VIEW_TYPE);
					if(!typeLs.contains(extName)) {
						return null;
					} else {
						boolean isMobile = isMobileRequest(request);
						request.setAttribute("isMobile", isMobile);
						request.setAttribute("attMain", sysAttMain);
						return mapping.findForward("viewonline_view_foxit");
					}
				}
				break;
			default:
				;
		}
		return null;
	}

	private ActionForward viewByAspose(ActionMapping mapping,HttpServletRequest request,HttpServletResponse response, OutputStream out, InputStream in,SysAttMain sysAttMain) throws Exception {
		List<FileConverter> fileConverters = SysFileStoreUtil.getFileConverters(
				SysAttViewerUtil.getExtName(sysAttMain.getFdFileName()), sysAttMain.getFdModelName(),
				SysFileStoreUtil.isOldConvertSuccessUseHTML());
		request.setAttribute("fdId", sysAttMain.getFdId());
		List<SysFileViewerParam> convertedParamsList = SysAttViewerUtil.getConvertedParams(sysAttMain);
		String fdAttMainId = sysAttMain.getFdId();
		String contentType = sysAttMain.getFdContentType();
		// 跳转播放器
		if (needPrintStream(request)) {
			request.setAttribute("printStream", "true");
			return printStream(request, response, out, in, sysAttMain, convertedParamsList, fileConverters);
		}
		if (SysFileStoreUtil.isAttConvertEnable()) {
			SysAttViewerUtil.addQueue(sysAttMain);
		}
		if ((!SysAttViewerUtil.isPicAtt(sysAttMain) && (convertedParamsList == null || convertedParamsList.size() == 0) && !contentType.contains("video")) ||
				(fileConverters.size() == 0 && !contentType.contains("video"))) {
			ActionForward forward = handleAspose(mapping, request, sysAttMain);
			if (forward != null) {
				return forward;
			}
		}
		String viewerPath = SysAttViewerUtil.getViewerPath(sysAttMain, convertedParamsList, fileConverters, request);
		IBaseModel model = getServiceImp(request).findByPrimaryKey(sysAttMain.getFdModelId(),
				sysAttMain.getFdModelName(), true);
		if (model != null && PropertyUtils.isReadable(model, "docStatus")) {
			String docStatus = (String) PropertyUtils.getProperty(model, "docStatus");
			if (StringUtil.isNotNull(docStatus)) {
				request.setAttribute("docStatus", docStatus);
			}
		}
		String forwardModule = request.getParameter("forwardModule");
		if(StringUtil.isNotNull(forwardModule)&&StringUtil.isNotNull(viewerPath)){  //模块下重写的附件展示页面
			String[] moduleStrArr = forwardModule.split("\\.");
			if(moduleStrArr.length>4){
				String key1 = moduleStrArr[3];
				String key2 = moduleStrArr[4];
				viewerPath = "/"+key1+"/"+key2+"/" +viewerPath.replaceAll("^(?:[^/]*/){3}(.*)$","$1");
			}
			if(forwardModule.indexOf("kms.train")>-1){ //kms下的train模块特用
				String type = request.getParameter("type");
				if(StringUtil.isNotNull(type)){
					viewerPath = StringUtil.linkString(viewerPath, "?type=", type);
				}
				String editAuth = request.getParameter("editAuth");
				if(StringUtil.isNotNull(editAuth)){
					if(viewerPath.indexOf("?")>-1){
						viewerPath = StringUtil.linkString(viewerPath, "&editAuth=", editAuth);
					}else{
						viewerPath = StringUtil.linkString(viewerPath, "?editAuth=", editAuth);
					}
				}
				String isConfirmAttend = request.getParameter("isConfirmAttend");
				if(StringUtil.isNotNull(isConfirmAttend)){
					if(viewerPath.indexOf("?")>-1){
						viewerPath = StringUtil.linkString(viewerPath, "&isConfirmAttend=", isConfirmAttend);
					}else{
						viewerPath = StringUtil.linkString(viewerPath, "?isConfirmAttend=", isConfirmAttend);
					}
				}
				String fdModelId = request.getParameter("fdModelId");
				if(StringUtil.isNotNull(fdModelId)){
					if(viewerPath.indexOf("?")>-1){
						viewerPath = StringUtil.linkString(viewerPath, "&fdModelId=", fdModelId);
					}else{
						viewerPath = StringUtil.linkString(viewerPath, "?fdModelId=", fdModelId);
					}
				}
			}
		}
		if (StringUtil.isNotNull(viewerPath) &&
				(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(sysAttMain.getFdFileName()))
						|| contentType.contains("video")
						|| SysAttUtil.isCADType(FilenameUtils.getExtension(sysAttMain.getFdFileName())))) {
			if ("com.landray.kmss.km.agreement.model.KmAgreementApply".equals(sysAttMain.getFdModelName())
					|| "com.landray.kmss.km.agreement.model.KmAgreementModel".equals(sysAttMain.getFdModelName())) {
				//判断是否以html形式展现,合同正文以html形式展示
				String viewer = request.getParameter("viewer");
				if ("htmlviewer".equals(viewer)) {
					boolean canCopy = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canCopy", canCopy);
					boolean canPrint = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canPrint", canPrint);
					request.setAttribute("fdKey", sysAttMain.getFdKey().toLowerCase());
					request.setAttribute("fdModelName", sysAttMain.getFdModelName());
					return new ActionForward(viewerPath);
				}
			}
			boolean canCopy = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=" + fdAttMainId, "GET");
			request.setAttribute("canCopy", canCopy);
			boolean canPrint = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + fdAttMainId, "GET");
			request.setAttribute("canPrint", canPrint);
			request.setAttribute("fdKey", sysAttMain.getFdKey().toLowerCase());
			request.setAttribute("fdModelName", sysAttMain.getFdModelName());
			request.setAttribute("fdAttMainId", sysAttMain.getFdId());
			return new ActionForward(viewerPath);
		} else {
			if (SysAttUtil.isOfficeType(FilenameUtils.getExtension(sysAttMain.getFdFileName()))) {
				if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())) {
					return mapping.findForward("viewonline_jg");
				} else if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())) {
					return mapping.findForward("viewonline_wps_jiazaix");
				} else {
					return null;
				}
			} else if (contentType.contains("video")) {
				return mapping.findForward("viewonline_video_converting");
			} else {
				return null;
			}
		}
	}

	private ActionForward handleAspose(ActionMapping mapping,HttpServletRequest request, SysAttMain attMain) throws Exception {
		boolean isMoblie = isMobileRequest(request);
		String fdFileName = attMain.getFdFileName();
		String previewType = SysAttViewerUtil.getPreView(attMain, isMoblie);
		if (JgWebOffice.isPDF(fdFileName) || JgWebOffice.isOFD(fdFileName)) {
			if (JgWebOffice.isOfficePDFJudge()) {
				if ((JgWebOffice.isOFD(fdFileName) && JgWebOffice.isJGPDF2018Enabled()) || JgWebOffice.isPDF(fdFileName)) {
					// 开启pdf控件跳转pdf在线阅读页面
					return mapping.findForward("viewonline_pdf");
				}
			} else {
				return mapping.findForward("viewonline_nopdf");
			}
		}
		if (SysAttConstant.CONVERTING.equals(previewType)) {
			return mapping.findForward("viewonline_converting");
		}
		if (SysAttUtil.isOfficeType(FilenameUtils.getExtension(attMain.getFdFileName()))) {
			if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())) {
				return mapping.findForward("viewonline_jg");
			} else if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())) {
				if (SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)) {
					SysAttMainForm sysAttMainForm = new SysAttMainForm();
					BeanUtils.copyProperties(sysAttMainForm, attMain);
					request.setAttribute("sysAttMainForm", sysAttMainForm);
					return mapping.findForward("viewonline_wps_oaassist_linux");
				} else {
					return mapping.findForward("viewonline_wps_jiazaix");
				}
			}
		}
		return null;
	}

	private ActionForward printStream(HttpServletRequest request, HttpServletResponse response, OutputStream out, InputStream in, SysAttMain sysAttMain, List<SysFileViewerParam> convertedParamsList, List<FileConverter> fileConverters) throws Exception {
		String fileThumb = request.getParameter("filethumb");
		String picThumb = request.getParameter("picthumb");
		String fileKey = request.getParameter("filekey");
		String converterKey = request.getAttribute("converterKey") != null
				? request.getAttribute("converterKey").toString() : "";
		String referer = request.getHeader("referer");
		if (StringUtil.isNull(referer)) {
			StringBuffer newUrl = new StringBuffer(request.getRequestURL());
			newUrl.append("?method=view&fdId=").append(request.getParameter("fdId"));
			response.sendRedirect(newUrl.toString());
		} else {
			if (StringUtil.isNotNull(picThumb)) {
				String mime = FileMimeTypeUtil.getContentType(sysAttMain.getFdFileName());
				response.setContentType(mime);
			} else if (StringUtil.isNotNull(fileKey)
					&& fileKey.toLowerCase().contains("cad")) {
				response.setContentType("image/png;charset=UTF-8");
			} else if (!converterKey.toLowerCase().contains("swf")) {
				response.setHeader("Content-type",
						"text/html;charset=UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
			}
			SysAttFile attFile = null;
			String fdType = request.getParameter("fdType");
			boolean isRtf = false;
			if(fdType != null && "rtf".equals(fdType)) {
				isRtf = true;
			}
			if (isRtf) {
				attFile = getAttUploadService().getFileById(sysAttMain.getFdFileId());
			} else {
				attFile = getServiceImp(request).getFile(sysAttMain.getFdId());
			}
			ISysFileLocationDirectService directService =
					SysFileLocationUtil.getDirectService(attFile.getFdAttLocation());
			if (StringUtil.isNotNull(picThumb)) {
				// 图片缩略图
				if (directService.isSupportDirect(request.getHeader("User-Agent"))) {
					String fdPath = SysAttViewerUtil
							.getPicThumbPath(sysAttMain, picThumb);
					String fileName = sysAttMain.getFdFileName() + ".jpeg";//缩略图与主文件类型不同，须加上扩展名
					viewDirect(request, response, fdPath, fileName, sysAttMain.getFdId());
					return null;
				}
				in = SysAttViewerUtil.getPicThumbStream(sysAttMain, request);
			} else {
				if (StringUtil.isNotNull(fileThumb) && "yes".equals(fileThumb)) {
					// 文件缩略图，包括OFFICE首页，VIDEO首帧
					if (directService.isSupportDirect("")) {//IE9以下的浏览器不能直连上传，但可以直连下载
						String fdPath =
								SysAttViewerUtil.getFileThumbPath(
										sysAttMain, convertedParamsList,
										fileConverters, request);
						String fileName = sysAttMain.getFdFileName() + ".jpeg";//缩略图与主文件类型不同，须加上扩展名
						viewDirect(request, response, fdPath, fileName, sysAttMain.getFdId());
						return null;
					}
					in = SysAttViewerUtil.getFileThumbInputStream(sysAttMain, convertedParamsList,
							fileConverters, request);
				} else {
					if (StringUtil.isNotNull(fileKey)) {
						/**
						 * 转换后的问题<br>
						 * 直连模式下前端涉及到跨域问题，故暂时修改为代理模式
						 */
						// if (directService.isSupportDirect(request)) {
						// String fdPath =
						// SysAttViewerUtil.getConvertFilePath(
						// sysAttMain, fileKey);
						// if (urlDirect(request, response, fdPath)) {
						// return null;
						// }
						//
						// }
						in = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, fileKey);
					}
				}
			}
			out = response.getOutputStream();
			if (in != null) {
				IOUtil.write(in, out);
			}
		}
		return null;
	}

	/**
	 * 在线阅读文档。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();
		boolean oldConvertSuccessUseHTMLView = SysFileStoreUtil.isOldConvertSuccessUseHTML();
		boolean isConverthtml=true;
		InputStream in = null;
		OutputStream out = null;
		boolean isMobile = isMobileRequest(request);

		try {
			String viewerPath = "";
			String fdAttMainId = request.getParameter("fdId");
			String fdType = request.getParameter("fdType");
			request.setAttribute("fdId", fdAttMainId);
			String showBar = request.getParameter("showBar");

			if ("false".equals(showBar)) {
				request.setAttribute("showBar", "false");
			}

			String isCowriting = request.getParameter("isCowriting");
			if ("false".equals(isCowriting)) {
				request.setAttribute("isCowriting", "false");
			}
			String requestViewType = request.getParameter("viewType");
			String viewType = StringUtil.isNull(requestViewType) ? "html" : requestViewType;
			String fKey = request.getParameter("filekey");
			Boolean viewImg = false;
			boolean isRtf = false;

			//判断是否是富文本框内文件
			if(fdType != null && "rtf".equals(fdType)) {
				isRtf = true;
			}
			SysAttMain sysAttMain = null;
			if(isRtf) {
				SysAttRtfData sysAttRtfData = getServiceImp(request).findRtfDataByPrimaryKey(fdAttMainId);
				sysAttMain = new SysAttMain();
				sysAttMain.setFdId(fdAttMainId);
				sysAttMain.setFdSize(sysAttRtfData.getFdSize());
				sysAttMain.setFdModelId(sysAttRtfData.getFdModelId());
				sysAttMain.setFdModelName(sysAttRtfData.getFdModelName());
				sysAttMain.setFdFileName(sysAttRtfData.getFdFileName());
				sysAttMain.setFdFileId(sysAttRtfData.getFdFileId());
				sysAttMain.setFdContentType(sysAttRtfData.getFdContentType());
			}else {
				sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdAttMainId);
			}
			if((StringUtil.isNotNull(fKey) && "image2thumbnail_s1".equals(fKey))||SysAttViewerUtil.isPicAtt(sysAttMain)||"yes".equals(request.getParameter("filethumb"))) {
				viewImg = true;
			}
			if(logger.isDebugEnabled()) {
				logger.debug("view the parameter of viewImg:" + viewImg);
			}
			if(UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND, SysAttMain.class.getName())){
				UserOperContentHelper.putAdd(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName());
			}

			//跳转自定义附件页面拓展点实现
			String actionForwardUrl = SysAttCustomPageUtil.getCustomPage(request);
			if(StringUtil.isNotNull(actionForwardUrl) && !isMobile && !viewImg) {
				return new ActionForward(actionForwardUrl);
			}

			//先根据业务模块配置跳转
			String forward = SysAttOcxUtil.getOcxPageForward(sysAttMain.getFdKey(),
					sysAttMain.getFdModelName(), SysAttOcxUtil.VIEW);
			if (StringUtil.isNotNull(forward) && !isMobile) {
				if ("viewonline_wps_cloud".equals(forward)) {
					// wps私有云查看 文档未同步则进行文档同步
					if (!SysAttWpsCloudUtil
							.isAttHadSyncByAttMainId(sysAttMain.getFdId())) {
						SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
					}
				}

				String viewer = request.getParameter("viewer");
				if ("htmlviewer".equals(viewer)||StringUtil.isNull(viewer)) {
					//html在线预览在后续处理
				} else {
					return mapping.findForward(forward);
				}
			}

			String contentType = sysAttMain.getFdContentType();

			//#160654 视频类型文件都走aspose的逻辑，因为其他模式都不支持视频播放。
			if(contentType.contains("video")){
				return viewByAspose(mapping, request, response, out, in, sysAttMain);
			}

           // #161059 PDF附件可以支持使用谷歌、火狐、Edge直接打开查看（先只开放给EIS的中小客户使用）
			if(isMobile &&  SysAttUtil.viewByBrowser(sysAttMain)) {
				request.setAttribute("downUrl", SysAttViewerUtil.getDownloadUrl(sysAttMain));
				request.setAttribute("fileName", sysAttMain.getFdFileName());
				return mapping.findForward("view_online_pdfh5");
			}
			//优先使用福昕阅读
			if(isMobile && SysAttConfigUtil.pdfReadByFoxitMobile() && JgWebOffice.isPDF(sysAttMain.getFdFileName())){
				getSysAttMainFoxitService().createRequestMateData(request, sysAttMain, false);
				return mapping.findForward("viewonline_foxit");
			}

			// 图片缓存
			if (!viewImg && AttImageUtils.cacheConsulation(request, response, contentType, sysAttMain.getFdFileName())) {
				return null;
			}
			// 保存fdModelName
			request.setAttribute("fdModelName", sysAttMain.getFdModelName());
			if ("audio_mp3".equals(viewType)) {
				return viewByaudiomp3(mapping, form, request, response);
			}

			ActionForward routeView = routeView(mapping, request,response, sysAttMain,out,in);
			if (!viewImg && ("true".equals(request.getAttribute("printStream")) || routeView != null)) {
				return routeView;
			}

			if ("jg".equals(viewType) || (SysAttWpsCloudUtil.getUseWpsLinuxView() && !viewImg)) {
				return viewByJG(mapping, form, request, response, sysAttMain);
			} else if ("audio_mp3".equals(viewType)) {
				return viewByaudiomp3(mapping, form, request, response);
			}

			List<FileConverter> fileConverters = SysFileStoreUtil.getFileConverters(
					SysAttViewerUtil.getExtName(sysAttMain.getFdFileName()), sysAttMain.getFdModelName(),
					oldConvertSuccessUseHTMLView);
			List<SysFileViewerParam> convertedParamsList = SysAttViewerUtil.getConvertedParams(sysAttMain);
			if(convertedParamsList == null || convertedParamsList.size() == 0) {
				isConverthtml=false;
			}
			// 跳转播放器
			if (!needPrintStream(request)) {
				if (attConvertEnable) {
					SysAttViewerUtil.addQueue(sysAttMain);
				}
				if (!SysAttViewerUtil.isPicAtt(sysAttMain)
						&& (convertedParamsList == null || convertedParamsList.size() == 0)
						&& !contentType.contains("video")) {
					return viewByJG(mapping, form, request, response, sysAttMain);
				}
				if (fileConverters.size() == 0 && !contentType.contains("video")) {
					return viewByJG(mapping, form, request, response, sysAttMain);
				}
				viewerPath = SysAttViewerUtil.getViewerPath(sysAttMain, convertedParamsList, fileConverters, request);
				IBaseModel model = getServiceImp(request).findByPrimaryKey(sysAttMain.getFdModelId(),
						sysAttMain.getFdModelName(), true);
				if (model != null && PropertyUtils.isReadable(model, "docStatus")) {
					String docStatus = (String) PropertyUtils.getProperty(model, "docStatus");
					if (StringUtil.isNotNull(docStatus)) {
						request.setAttribute("docStatus", docStatus);
					}
				}

				String forwardModule = request.getParameter("forwardModule");

				if(StringUtil.isNotNull(forwardModule)&&StringUtil.isNotNull(viewerPath)){  //模块下重写的附件展示页面
					String[] moduleStrArr = forwardModule.split("\\.");
					if(moduleStrArr.length>4){
						String key1 = moduleStrArr[3];
						String key2 = moduleStrArr[4];

						viewerPath = "/"+key1+"/"+key2+"/" +viewerPath.replaceAll("^(?:[^/]*/){3}(.*)$","$1");

					}

					if(forwardModule.indexOf("kms.train")>-1){ //kms下的train模块特用
						String type = request.getParameter("type");
						if(StringUtil.isNotNull(type)){
							viewerPath = StringUtil.linkString(viewerPath, "?type=", type);
						}

						String editAuth = request.getParameter("editAuth");
						if(StringUtil.isNotNull(editAuth)){
							if(viewerPath.indexOf("?")>-1){
								viewerPath = StringUtil.linkString(viewerPath, "&editAuth=", editAuth);
							}else{
								viewerPath = StringUtil.linkString(viewerPath, "?editAuth=", editAuth);
							}

						}

						String isConfirmAttend = request.getParameter("isConfirmAttend");
						if(StringUtil.isNotNull(isConfirmAttend)){
							if(viewerPath.indexOf("?")>-1){
								viewerPath = StringUtil.linkString(viewerPath, "&isConfirmAttend=", isConfirmAttend);
							}else{
								viewerPath = StringUtil.linkString(viewerPath, "?isConfirmAttend=", isConfirmAttend);
							}

						}

						String fdModelId = request.getParameter("fdModelId");
						if(StringUtil.isNotNull(fdModelId)){

							if(viewerPath.indexOf("?")>-1){
								viewerPath = StringUtil.linkString(viewerPath, "&fdModelId=", fdModelId);
							}else{
								viewerPath = StringUtil.linkString(viewerPath, "?fdModelId=", fdModelId);
							}
						}

					}

				}
				if (StringUtil.isNotNull(viewerPath)) {
					if ("com.landray.kmss.km.agreement.model.KmAgreementApply"
							.equals(sysAttMain.getFdModelName())
							|| "com.landray.kmss.km.agreement.model.KmAgreementModel"
							.equals(sysAttMain.getFdModelName())) {
						//判断是否以html形式展现,合同正文以html形式展示
						String viewer = request.getParameter("viewer");
						if ("htmlviewer".equals(viewer)) {
							boolean canCopy = UserUtil.checkAuthentication(
									"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=" + fdAttMainId, "GET");
							request.setAttribute("canCopy", canCopy);
							boolean canPrint = UserUtil.checkAuthentication(
									"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + fdAttMainId, "GET");
							request.setAttribute("canPrint", canPrint);
							request.setAttribute("fdKey", sysAttMain.getFdKey().toLowerCase());
							request.setAttribute("fdModelName", sysAttMain.getFdModelName());
							return new ActionForward(viewerPath);
						}
					}



					boolean canCopy = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canCopy", canCopy);
					boolean canPrint = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + fdAttMainId, "GET");
					request.setAttribute("canPrint", canPrint);
					request.setAttribute("fdKey", sysAttMain.getFdKey().toLowerCase());
					request.setAttribute("fdModelName", sysAttMain.getFdModelName());
					request.setAttribute("fdAttMainId", sysAttMain.getFdId());
					return new ActionForward(viewerPath);
				} else {

					// 文档类型
					if (SysAttUtil.isOfficeType(FilenameUtils
							.getExtension(sysAttMain.getFdFileName()))) {
						return viewByJG(mapping, form, request, response,
								sysAttMain);
						// 视频类型
					} else if (contentType.contains("video")) {
						return mapping
								.findForward("viewonline_video_converting");
						// 其他类型
					} else {
						return null;
					}
				}
			} else {

				// 读取文件流
				String fileThumb = request.getParameter("filethumb");
				String picThumb = request.getParameter("picthumb");
				String fileKey = request.getParameter("filekey");
				String converterKey = request.getAttribute("converterKey") != null
						? request.getAttribute("converterKey").toString() : "";
				out = response.getOutputStream();
				String referer = request.getHeader("referer");
				if (StringUtil.isNull(referer)) {
					StringBuffer newUrl = new StringBuffer(request.getRequestURL());
					newUrl.append("?method=view&fdId=").append(request.getParameter("fdId"));
					response.sendRedirect(newUrl.toString());
				} else {
					if(StringUtil.isNotNull(picThumb)){
						String mime = FileMimeTypeUtil.getContentType(sysAttMain.getFdFileName());
						response.setContentType(mime);
					} else if (StringUtil.isNotNull(fileKey)
							&& fileKey.toLowerCase().contains("cad")) {
						response.setContentType("image/png;charset=UTF-8");
					} else if (!converterKey.toLowerCase().contains("swf")) {
						response.setHeader("Content-type",
								"text/html;charset=UTF-8");
						response.setContentType("text/html;charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
					}
					SysAttFile attFile = null;
					if(isRtf) {
						attFile = getAttUploadService().getFileById(sysAttMain.getFdFileId());
					}else {
						attFile = getServiceImp(request).getFile(fdAttMainId);
					}


					ISysFileLocationDirectService directService =
							SysFileLocationUtil.getDirectService(attFile.getFdAttLocation());
					if (StringUtil.isNotNull(picThumb)) {

						// 图片缩略图

						if (directService.isSupportDirect(request.getHeader("User-Agent"))) {
							String fdPath = SysAttViewerUtil
									.getPicThumbPath(sysAttMain, picThumb);
							String fileName = sysAttMain.getFdFileName() + ".jpeg";//缩略图与主文件类型不同，须加上扩展名
							viewDirect(request, response, fdPath, fileName, sysAttMain.getFdId());
							return null;
						}

						in = SysAttViewerUtil.getPicThumbStream(sysAttMain, request);
					} else {

						if (StringUtil.isNotNull(fileThumb) && "yes".equals(fileThumb)) {

							// 文件缩略图，包括OFFICE首页，VIDEO首帧
							if (directService.isSupportDirect("")) {//IE9以下的浏览器不能直连上传，但可以直连下载
								String fdPath =
										SysAttViewerUtil.getFileThumbPath(
												sysAttMain, convertedParamsList,
												fileConverters, request);
								String fileName = sysAttMain.getFdFileName() + ".jpeg";//缩略图与主文件类型不同，须加上扩展名
								viewDirect(request, response, fdPath,fileName,sysAttMain.getFdId());
								return null;
							}

							in = SysAttViewerUtil.getFileThumbInputStream(sysAttMain, convertedParamsList,
									fileConverters, request);
						} else {
							if (StringUtil.isNotNull(fileKey)) {

								/**
								 * 转换后的问题<br>
								 * 直连模式下前端涉及到跨域问题，故暂时修改为代理模式
								 */
								// if (directService.isSupportDirect(request)) {
								// String fdPath =
								// SysAttViewerUtil.getConvertFilePath(
								// sysAttMain, fileKey);
								// if (urlDirect(request, response, fdPath)) {
								// return null;
								// }
								//
								// }

								in = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, fileKey);
							}
						}
					}

					out = response.getOutputStream();

					if (in != null) {
						IOUtil.write(in, out);
					}
				}
				return null;
			}
		} catch (Exception e) {
			logger.error("error", e);
			streamClose(in, out);
			return null;
		} finally {
			streamClose(in, out);
		}
	}

	/**
	 * 文件下载直连跳转相关
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Boolean downloadDirect(HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		if("false".equals(request.getParameter("isSupportDirect"))){//强制走代理，不走直连
			return false;
		}

		if (StringUtil.isNull(fdId) || fdId.indexOf(";") >= 0) {
			return false;
		}

		SysAttMain sysAttMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId);
		if (sysAttMain == null || SysAttBase.ATTACHMENT_LOCATION_DB.equals(sysAttMain.getFdAttLocation())
				|| SysAttMain.ATTACHMENT_LOCATION_FILE.equalsIgnoreCase(sysAttMain.getFdAttLocation())) {
			return false;
		}
		SysAttFile attFile = getServiceImp(request).getFile(fdId);
		ISysFileLocationDirectService directService = SysFileLocationUtil
				.getDirectService(attFile.getFdAttLocation());

		// 如果是其他扩展存储类型（支持直连模式）且是下载单个文件，重新跳转到第三方下载链接
		if (directService.isSupportDirect("")) {//IE9以下的浏览器不能直连上传，但可以直连下载
			String path = getServiceImp(request).getFilePath(fdId);
			String url = directService.getDownloadUrl(path,sysAttMain.getFdFileName());
			if (StringUtil.isNotNull(url)) {
				response.sendRedirect(SysAttViewerUtil
						.formatUrl(request, url));
				//记录附件下载日志
				String[] fids = fdId.split(";");
				List<SysAttMain> sysAttMains = getServiceImp(request).getSysAttMainDao().findByPrimaryKeys(fids);
				getSysAttDownloadLogService().addDownloadLogByAttList(sysAttMains, new RequestContext(request));
				return true;
			}
		}

		return false;

	}

	/**
	 * 文件阅读直连链接跳转相关
	 * @param request
	 * @param response
	 * @param fdPath
	 * @return
	 * @throws Exception
	 */
	private void viewDirect(HttpServletRequest request,HttpServletResponse response,
							String fdPath,String fileName,String fdMainId) throws Exception {

		if (StringUtil.isNull(fdPath)) {
			response.sendRedirect(
					request.getContextPath() + SysAttViewerUtil.unConvertedUrl);
			return;
		}
		SysAttFile attFile = getServiceImp(request).getFile(fdMainId);
		ISysFileLocationDirectService directService = SysFileLocationUtil
				.getDirectService(attFile.getFdAttLocation());
		String url = directService.getDownloadUrl(fdPath,fileName);
		if (StringUtil.isNotNull(url)) {
			response.sendRedirect(SysAttViewerUtil.formatUrl(request, url));
		}
	}

	/**
	 * 文件阅读直连链接跳转相关
	 * @param request
	 * @param response
	 * @param fdPath
	 * @return
	 * @throws Exception
	 */
	private void viewDirectByRtf(HttpServletRequest request,HttpServletResponse response,
								 String fdPath,String fileName,String fdFileId) throws Exception {

		if (StringUtil.isNull(fdPath)) {
			response.sendRedirect(
					request.getContextPath() + SysAttViewerUtil.unConvertedUrl);
			return;
		}
		SysAttFile attFile = getAttUploadService().getFileById(fdFileId);
		ISysFileLocationDirectService directService = SysFileLocationUtil
				.getDirectService(attFile.getFdAttLocation());
		String url = directService.getDownloadUrl(fdPath,fileName);
		if (StringUtil.isNotNull(url)) {
			response.sendRedirect(SysAttViewerUtil.formatUrl(request, url));
		}
	}

	private ActionForward viewByJG(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								   HttpServletResponse response, SysAttMain attMain) throws Exception {

		SysAttMainForm sysAttMainForm = new SysAttMainForm();
		BeanUtils.copyProperties(sysAttMainForm, attMain);
		request.setAttribute("sysAttMainForm", sysAttMainForm);
		String fdFileName = attMain.getFdFileName();
		String inner = request.getParameter("inner");
		String fdForceUseJG = request.getParameter("fdForceUseJG");
		String attMainId = attMain.getFdId();
		boolean isMoblie = isMobileRequest(request);
		if ("yes".equals(inner)) {
			request.setAttribute("inner", "true");
		}

		//文档中台查看
		if(SysAttWpsCenterUtil.isEnable()){
			request.setAttribute("directPreview", request.getParameter("directPreview"));
			//文档中台
			return mapping.findForward("viewonline_wps_center");
		}

		//加载项模式下，嵌入式wps打开
		boolean isWpsoaassist=SysAttWpsoaassistUtil.isEnable();
		boolean isWpsoaassistEmbed=SysAttWpsoaassistUtil.isWPSOAassistEmbed();
		if (!"yes".equals(inner)&&!isMoblie&&isWpsoaassist&&isWpsoaassistEmbed&&(isWord(attMain.getFdFileName())||isExcel(attMain.getFdFileName())||isPpt(attMain.getFdFileName()))) {
			String userAgent = request.getHeader("user-agent");
			boolean winFlag = userAgent.toLowerCase().contains("win");
			if(!winFlag) {
				return mapping.findForward("viewonline_wps_oaassist_linux");
			}
			//return mapping.findForward("editonline_wps_linux");
		}

		String previewType=SysAttViewerUtil.getPreView(attMain, isMoblie);

		// 如果开启了iwebpdf控件，查看ofd有优先iwebpdf, 再是在线预览服务
		if (SysAttWpsCloudUtil.getUseWpsLinuxView() && !JgWebOffice.isJGPDF2018Enabled()) {
			request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(attMainId));
			return mapping.findForward("viewonline_wps_preview");
		}

		//福昕阅读
		if (JgWebOffice.isPDF(fdFileName) && SysAttConfigUtil.pdfReadByFoxitPC()) {
			String downLoadUrl = SysAttViewerUtil.getDownloadUrl(attMain);
			if(logger.isDebugEnabled()) {
				logger.info("下载地址:" + downLoadUrl);
			}
			request.setAttribute("downLoadUrl", downLoadUrl);

			return mapping.findForward("viewonline_foxit");
		}
		if (JgWebOffice.isPDF(fdFileName) || JgWebOffice.isOFD(fdFileName)) {// 文件后缀名是否有pdf
			if (SysAttWpsWebOfficeUtil.isEnable()) {
				// wps查看
				return mapping.findForward("viewonline_wps");
			} else if (SysAttConstant.WPS_LINUX_VIEW.equals(previewType)) {// 使用linux在线预览查看OFD和pdf文件

				request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(attMainId));
				return mapping.findForward("viewonline_wps_preview");

			}else if(SysAttConstant.WPS_WINDOW_VIEW.equals(previewType)) {//
				request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsWindowPreviewUrl(attMainId));
				return mapping.findForward("viewonline_wps_preview");

			} else if (SysAttConstant.WPS_CLOUD_VIEW.equals(previewType)) {
				// wps私有云查看 文档未同步则进行文档同步
				if (!SysAttWpsCloudUtil.isAttHadSyncByAttMainId(sysAttMainForm.getFdId())) {
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMainForm.getFdId());
					return mapping.findForward("viewonline_wps_cloud");
				}

			}else {
				if (JgWebOffice.isOfficePDFJudge()) {
					if ((JgWebOffice.isOFD(fdFileName)
							&& JgWebOffice.isJGPDF2018Enabled())
							|| JgWebOffice.isPDF(fdFileName)) {
						// 开启pdf控件跳转pdf在线阅读页面
						return mapping.findForward("viewonline_pdf");
					}
				} else {
					return mapping.findForward("viewonline_nopdf");
				}
			}
		}

		// 如果使用linux在线预览
		if (SysAttConstant.WPS_LINUX_VIEW.equals(previewType)) {
			request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(attMainId));
			return mapping.findForward("viewonline_wps_preview");
		}
		//如果使用windows在线预览
		if (SysAttConstant.WPS_WINDOW_VIEW.equals(previewType)) {
			request.setAttribute("requestUrl", SysAttWpsCloudUtil.getWpsWindowPreviewUrl(attMainId));
			return mapping.findForward("viewonline_wps_preview");
		}

		if(SysAttConstant.WPS_CLOUD_VIEW.equals(previewType)) {
			if (!SysAttWpsCloudUtil.isAttHadSyncByAttMainId(attMainId)) {
				SysAttWpsCloudUtil.syncAttToAddByMainId(attMainId);
			}
			return mapping.findForward("viewonline_wps_cloud");
		}
		if(SysAttConstant.CONVERTING.equals(previewType)) {
			return mapping.findForward("viewonline_converting");
		}

		// add 附件上传后支持预览
		if (StringUtil.isNotNull(attMain.getFdModelId()) && StringUtil.isNotNull(attMain.getFdModelName())) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(attMain.getFdModelId(), attMain.getFdModelName(),
					true);
			// 在新建（复制流程）页面上传的附件，由于还没有生成主文档，所以取出来的主文档可能为空
			if (model != null && PropertyUtils.isReadable(model, "docStatus")) {
				String docStatus = (String) PropertyUtils.getProperty(model, "docStatus");
				if (StringUtil.isNotNull(docStatus)) {
					request.setAttribute("docStatus", docStatus);
				}
			}
		}
		if ("true".equals(fdForceUseJG)) {
			return mapping.findForward("editonline_jg");
		} else {
			if (SysAttWpsWebOfficeUtil.isEnable()) {
				// wps查看
				return mapping.findForward("viewonline_wps");
			} else if (SysAttConstant.WPS_CLOUD_VIEW.equals(previewType)) {
				// wps私有云查看 文档未同步则进行文档同步
				if (!SysAttWpsCloudUtil
						.isAttHadSyncByAttMainId(sysAttMainForm.getFdId())) {
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMainForm.getFdId());
					return mapping.findForward("viewonline_wps_cloud");
				}

				//PC端是云文档  移动端是WPS加载项  #133032
				if(SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
						&& "1".equals(SysAttConfigUtil.isReadWPSCloudForMobile()))
				{
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMainForm.getFdId());
					return mapping.findForward("viewonline_wps_cloud");
				}

				return mapping.findForward("viewonline_wps_cloud");
			} else if(isMoblie && SysAttWpsCloudUtil.isEnable(isMoblie))
			{
				// wps私有云查看 文档未同步则进行文档同步
				if (!SysAttWpsCloudUtil
						.isAttHadSyncByAttMainId(sysAttMainForm.getFdId())) {
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMainForm.getFdId());
					return mapping.findForward("viewonline_wps_cloud");
				}
				return mapping.findForward("viewonline_wps_cloud");
			}
			else {
				return mapping.findForward("viewonline_jg");
			}
		}
	}

	/**
	 * 音频的阅读权限和下载权限分开，但是阅读是下载之后播放的， 所以实现是一样的，入口不同
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward viewByaudiomp3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		return download(mapping, form, request, response);
	}

	/**
	 * 判断是否进行文件流输出
	 *
	 * @param request
	 * @return
	 */
	private boolean needPrintStream(HttpServletRequest request) {
		return request.getParameterMap().containsKey("filekey") || request.getParameterMap().containsKey("picthumb")
				|| request.getParameterMap().containsKey("filethumb");
	}

	/**
	 * 附件请求密钥，以及产生附件文档方法
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward handleAttUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		Map<String, Object> rtnMap = null;
		String format = request.getParameter("format");
		if (StringUtil.isNull(format)) {
			format = "xml";
		}
		String reqType = request.getParameter("gettype");
		if ("getuserkey".equalsIgnoreCase(reqType)) {
			if (logger.isDebugEnabled()) {
				logger.debug("与客户端服务器握手中。。");
			}
			rtnMap = getUserKey(request, form);
		} else if ("submit".equalsIgnoreCase(reqType)) {
			rtnMap = saveAttInfo(request, form);
		} else if ("checkMd5".equals(reqType)){
			checkMd5(request,response);
			return null;
		}
		request.setAttribute("resultMap", rtnMap);
		request.setAttribute("format", format);
		return mapping.findForward("upload_result");
	}

	/**
	 * 附件上传第一步，握手获取密钥
	 *
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getUserKey(HttpServletRequest request,
										   ActionForm form) {
		Map<String, Object> rtnMap = new HashMap<>();
		try {
			SysFileSignature sign = null;
			SysFileSignatureRequest sysFileSign = new SysFileSignatureRequest(request);

			/**
			 * 强制代理模式<br>
			 * 此参数为false意味着为模块定制的上传模式，走原来的逻辑，即使支持直连模式也使用代理，防止token不对应
			 */
			String isSupportDirect = request.getParameter("isSupportDirect");
			ISysFileLocationDirectService directService;
			if ("false".equals(isSupportDirect)) {
				directService = SysFileLocationUtil.getDirectService(
						SysAttBase.ATTACHMENT_LOCATION_SERVER);
			} else {
				directService = SysFileLocationUtil.getDirectService(null);
			}

			sign = directService.getSignature(sysFileSign);

			rtnMap.put("token", sign.toMap());
			rtnMap.put("status", "1");

			// rtnMap.put("userkey", fileInfo);
			// if (logger.isDebugEnabled())
			// logger.debug("生成密钥成功，密钥：" + fileInfo);
		} catch (Exception e) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "文件生成密钥过程出错：" + e);
			logger.error("文件生成密钥过程出错", e);
		}
		return rtnMap;
	}

	/**
	 * 检查是否有相同md5值的附件存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void checkMd5(HttpServletRequest request,
						  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkMd5", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObj = new JSONObject();
		String md5 = request.getParameter("md5");
		String filesize = request.getParameter("filesize");

		SysAttFile file = getServiceImp(request).getFileByMd5(md5,Long.valueOf(filesize));
		if(file == null){
			jsonObj.put("md5Exists", false);
		}else{
			jsonObj.put("md5Exists", true);
			jsonObj.put("fileId", file.getFdId());
			jsonObj.put("path", file.getFdFilePath());
		}
		KmssReturnPage.getInstance(request).addMessages(messages);
		TimeCounter.logCurrentTime("Action-checkMd5", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonObj.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	private String getFileLimitType() {
		String fileLimit = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");
		if (StringUtil.isNotNull(fileLimit)) {
			fileLimitType = fileLimit;
		}
		return fileLimitType;
	}

	private String getDisabledFileType() {
		String limitType = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");
		String disabledfile = ResourceUtil.getKmssConfigString("sys.att.disabledFileType");
		if (StringUtil.isNotNull(limitType) && disabledfile!=null) {
			disabledFileType = disabledfile.toLowerCase();
		}
		return disabledFileType;
	}

	/**
	 * 附件上传最后一步，根据上传处理结果，产生sysAttMain对象。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/**
	 * 附件上传最后一步，根据上传处理结果，产生sysAttMain对象。
	 *
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> saveAttInfo(HttpServletRequest request, ActionForm form) {
		Map<String, Object> rtnMap = new HashMap<>();
		String fileId = request.getParameter("filekey");
		if (StringUtil.isNotNull(fileId)) {
			try {
				SysAttMainForm attMainForm = (SysAttMainForm) form;
				SysAttMain sysAttMain = new SysAttMain();
				String id = sysAttMain.getFdId();
				BeanUtils.copyProperties(sysAttMain, attMainForm);
				sysAttMain.setFdId(id);
				String fileName = request.getParameter("filename");
				if (StringUtil.isNotNull(fileName)) {
					String _fileType = null;
					if (fileName.indexOf(".") > -1) {
						_fileType = fileName.substring(fileName.lastIndexOf("."));
					}
					if (StringUtil.isNotNull(_fileType)) {
						_fileType = _fileType.toLowerCase();
						String[] files = getDisabledFileType().split("[;；]");
						if("1".equals(getFileLimitType())){
							Boolean isPass = true;
							for(String f : files){
								if(_fileType.equals(f)){
									isPass = false;
									break;
								}
							}
							if(!isPass){
								rtnMap.put("status", "-1");
								rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
								return rtnMap;
							}

						}else if("2".equals(getFileLimitType())){
							Boolean isPass = false;
							for(String f : files){
								if(_fileType.equals(f)){
									isPass = true;
									break;
								}
							}
							if(!isPass){
								rtnMap.put("status", "-1");
								rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
								return rtnMap;
							}
						}
					}else{
						//无后缀文件不允许上传。
						rtnMap.put("status", "-1");
						rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
						return rtnMap;
					}

					String fileNameSign = request.getParameter("fdSign");
					String namesign = new String(Base64.encodeBase64(fileName.getBytes("utf-8")), "UTF-8");

					namesign = namesign.replaceAll("\\+", "");
					namesign = namesign.replaceAll("\\/", "");
					namesign = namesign.replaceAll("\\=", "");

					if (StringUtil.isNull(fileNameSign)|| !fileNameSign.equals(namesign)) {
						rtnMap.put("status", "-1");
						rtnMap.put("msg", "附件签名不符，基于安全考虑，不允许上传该附件！");
						return rtnMap;
					}

					// #56945 从流中判断文件类型
					if (SysAttBase.ATTACHMENT_LOCATION_SERVER
							.equals(SysFileLocationUtil.getKey(null))) {
						InputStream in = (getServiceImp(request))
								.getInputStreamByFile(fileId);
						if (in != null) {
							_fileType = FileTypeUtil.getFileType(in);
							IOUtils.closeQuietly(in);
						}
					}

					if(StringUtil.isNotNull(_fileType)&&!_fileType.startsWith(".")) {
						_fileType = "."+_fileType;
					}

					if (StringUtil.isNotNull(_fileType)) {
						String[] files = getDisabledFileType().split("[;；]");
						if("1".equals(getFileLimitType())){
							Boolean isPass = true;
							for(String f : files){
								if(_fileType.equals(f)){
									isPass = false;
									break;
								}
							}
							if(!isPass){
								rtnMap.put("status", "-1");
								rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
								return rtnMap;
							}
						}else if("2".equals(getFileLimitType())){
							Boolean isPass = false;

							String[] office = new String[]{".doc",".xls",".xlsx",".docx",".vsd",".wps"};
							for(String f : files){
								//#110109
								//如果是wps类型，则office和.vsd后缀允许通过
								if(".wps".equals(_fileType)){
									if(ArrayUtil.asList(office).contains(f)){
										isPass = true;
										break;
									}
								}else if(_fileType.equals(f)){
									isPass = true;
									break;
								}
							}
							if(!isPass){
								rtnMap.put("status", "-1");
								rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
								return rtnMap;
							}
						}
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("生成SysAttMain文档，文件名为:" + fileName);
				}
				sysAttMain.setFdFileName(fileName);
				sysAttMain.setDocCreateTime(new Date());

				String contentType = FileMimeTypeUtil.getContentType(fileName);
				sysAttMain.setFdContentType(contentType);
//				sysAttMain.setFdAttLocation(SysFileLocationUtil.getLocation().getKey());
				sysAttMain.setFdFileId(fileId);
				(getServiceImp(request)).add(sysAttMain);

				rtnMap.put("status", "1");
				rtnMap.put("msg", "上传成功");
				rtnMap.put("attid", id);
				rtnMap.put("attname", fileName);
				rtnMap.put("attsize", String.valueOf(sysAttMain.getFdSize()));
				rtnMap.put("atttype", "byte");
				if (logger.isDebugEnabled()) {
					logger.debug("生成SysAttMain文档结束!");
				}
			} catch (Exception e) {
				rtnMap.put("status", "-1");
				rtnMap.put("msg", "生成SysAttMain文档出错：" + e);
				logger.error("生成SysAttMain文档出错", e);
			}
		} else {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "文件ID信息为空！");
			logger.error("文件ID信息为空！");
		}
		return rtnMap;
	}

	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							   HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String forward = "viewonline";
		boolean isJGEnabled = JgWebOffice.isJGEnabled();
		boolean isJGPDFEnabled = JgWebOffice.isOfficePDFJudge();
		SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);

		commonLogFind(request,sysAttMain,"print");
		// 打印附件日志
		getSysPrintLogCoreService().addPrintLog(sysAttMain, new RequestContext(request));
		SysAttMainForm sysAttMainForm = new SysAttMainForm();
		BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
		request.setAttribute("sysAttMainForm", sysAttMainForm);
		request.setAttribute("isPrint", true);

		// #161059 PDF附件可以支持使用谷歌、火狐、Edge直接打开查看（先只开放给EIS的中小客户使用）
		if(SysAttUtil.viewByBrowser(sysAttMain)) {
			SysAttUtil.outputFile(response, sysAttMain);
			return null;
		}

		if(SysAttConfigUtil.pdfReadByFoxitPC() && JgWebOffice.isPDF(sysAttMain.getFdFileName())){
			getSysAttMainFoxitService().createRequestMateData(request, sysAttMain, false);
			return mapping.findForward("viewonline_foxit");
		}

		switch (SysAttConfigUtil.getReadOLConfig()) {
			case "3":
				//wps云文档预览
				if (!SysAttWpsCloudUtil.isAttHadSyncByAttMainId(sysAttMain.getFdId())) {
					SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
				}
				return mapping.findForward("viewonline_wps_cloud");
			case "4":
				//点聚轻阅读
				request.setAttribute("attMain", sysAttMain);
				request.setAttribute("previewUrl",getSysAttachmentDianJuProvider().getPreviewUrl(sysAttMain,UserUtil.getKMSSUser().getUserName()));
				return mapping.findForward("viewonline_dianju_preview");
			case "5":
				//文档中台查看
				return mapping.findForward("viewonline_wps_center");
			case "6":
				// 福昕轻阅读
				if(!SysAttViewerUtil.isPicAtt(sysAttMain)){
					boolean isMobile = isMobileRequest(request);
					request.setAttribute("isMobile", isMobile);
					request.setAttribute("attMain", sysAttMain);
					return mapping.findForward("viewonline_view_foxit");
				}
		}

		//wps中台
		if (SysAttWpsCenterUtil.isEnable()) {
			return mapping.findForward(
					"viewonline_wps_center");
		}

		// 如果是wps私有化，优先wps查看页面打印
		if (SysAttWpsCloudUtil.isEnable()
				&& (SysAttUtil.isOfficeTypeByWps(FilenameUtils
				.getExtension(
						sysAttMain.getFdFileName()))
				|| JgWebOffice.isPDF(sysAttMain.getFdFileName()))) {
			// wps私有云查看 文档未同步则进行文档同步
			if (!SysAttWpsCloudUtil
					.isAttHadSyncByAttMainId(
							sysAttMain.getFdId())) {
				SysAttWpsCloudUtil
						.syncAttToAddByMainId(sysAttMain.getFdId());
				return mapping.findForward(
						"viewonline_wps_cloud");
			}
			return mapping.findForward("viewonline_wps_cloud");
		}

		//加载项模式下，嵌入式wps打开
		boolean isWpsoaassist=SysAttWpsoaassistUtil.isEnable();
		boolean isWpsoaassistEmbed=SysAttWpsoaassistUtil.isWPSOAassistEmbed();
		if (isWpsoaassist&&isWpsoaassistEmbed&&(isWord(sysAttMain.getFdFileName())||isExcel(sysAttMain.getFdFileName())||isPpt(sysAttMain.getFdFileName()))) {
			String userAgent = request.getHeader("user-agent");
			boolean winFlag = userAgent.toLowerCase().contains("win");
			if(!winFlag) {
				return mapping.findForward("viewonline_wps_oaassist_linux");
			}
			//return mapping.findForward("editonline_wps_linux");
		}

		if (isJGPDFEnabled && JgWebOffice.isPDF(sysAttMain.getFdFileName())) {
			forward = "viewonline_pdf";
		} else if (isJGEnabled) {
			forward = "viewonline_jg";
		}

		return mapping.findForward(forward);

	}

	/**
	 * 在线阅读下载文档 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward viewDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String head = request.getHeader("User-Agent");
			if ("LKS Software".equals(head)) {
				return download(mapping, form, request, response);
			} else {
				boolean isFlash = StringUtil.isNotNull(request.getHeader("x-flash-version"));
				String flash = request.getHeader("Referer");
				if (isFlash && flash != null && flash.indexOf("vcastr3.swf") > -1) {
					return download(mapping, form, request, response);
				} else {
					messages.addMsg(new KmssMessage("Illegal access error!"));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return mapping.findForward("failure");
		} else {
			boolean isJGEnabled = JgWebOffice.isJGEnabled();
			if (isJGEnabled) {
				return mapping.findForward("viewonline_jg");
			} else {
				return mapping.findForward("viewonline");
			}
		}

	}

	/**
	 * 将原来保存在数据库中的文件迁移到磁盘系统
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transferToDisk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			HQLInfo hqlInfo = new HQLInfo();
			List attList = getServiceImp(request).findList(hqlInfo);
			if(log.isDebugEnabled()){
				log.debug("附件个数为：" + attList.size());
			}
			String filePath = ResourceUtil.getKmssConfigString("kmss.resource.path");

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
			String datePath = format.format(date);
			filePath += "/" + datePath.substring(0, 6) + "/" + datePath.substring(6);
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = RandomStringUtils.random(20, "abcdefghijklmnopqrstuvwxyz1234567890");
			boolean allowLog = UserOperHelper.allowLogOper("transferToDisk",
					getServiceImp(request).getModelName());
			for (int i = 0; i < 4; i++) {
				SysAttMain sysAttMain = (SysAttMain) attList.get(i);
				java.sql.Blob attdate = sysAttMain.getFdData();
				if (attdate == null) {
					continue;
				}
				InputStream in = attdate.getBinaryStream();
				if(log.isDebugEnabled()){
					log.debug("附件内容:====" + attdate);
				}
				OutputStream stream = new FileOutputStream(filePath + "/" + sysAttMain.getFdFileName());
				IOUtil.write(in, stream);
				sysAttMain.setFdData(null);
				String fdfilePath = filePath + "/" + fileName;
				sysAttMain.setFdFilePath(fdfilePath);
				sysAttMain.setFdAttLocation(null);
				if(allowLog){
					UserOperContentHelper.putUpdate(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName())
							.putSimple("fdFilePath", sysAttMain.getFdFilePath(), fdfilePath)
							.putSimple("fdAttLocation", sysAttMain.getFdAttLocation(), null);
				}
				getServiceImp(request).update(sysAttMain);
			}

		} catch (Exception e) {
			messages.addError(new KmssMessage("common.io.exception", e.toString()), e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

	// 显示缩略图
	public ActionForward showThumbs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		try {
			String fdId = request.getParameter("fdId");
			String size = request.getParameter("size");
			if (StringUtil.isNotNull(fdId)) {
				String l_fdId = fdId;
				SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(l_fdId);
				if (sysAttMain != null) {
					commonLogFind(request,sysAttMain,"showThumbs");
					String loaction = sysAttMain.getFdAttLocation();
					response.reset();
					out = response.getOutputStream();
					if (SysAttMain.ATTACHMENT_LOCATION_FILE.equals(loaction)) {
						String fileName = sysAttMain.getFdFileName();
						String filepath = null;
						if (StringUtil.isNotNull(sysAttMain.getFdFilePath()) && StringUtil.isNotNull(size)) {
							File f = new File(sysAttMain.getFdFilePath() + "_" + size + "_" + fileName);
							if (f.exists()) {
								filepath = sysAttMain.getFdFilePath() + "_" + size + "_" + fileName;
							}
						} else { // 原图
							if (StringUtil.isNotNull(sysAttMain.getFdFilePath())) {
								File f = new File(sysAttMain.getFdFilePath());
								if (f.exists()) {
									filepath = sysAttMain.getFdFilePath();
								}
							}
						}
						if (StringUtil.isNotNull(filepath)) {
							IOUtil.write(new FileInputStream(filepath), out);
						}
					} else if (SysAttMain.ATTACHMENT_LOCATION_SERVER.equals(loaction)) {
						if (sysAttMain.getFdContentType().indexOf("image") > -1
								|| (sysAttMain.getFdFileName().endsWith(".png")
								&& "application/octet-stream".equals(sysAttMain.getFdContentType()))) {
							(getServiceImp(request)).findData(sysAttMain.getFdId(), size, out);
						} else {
							(getServiceImp(request)).findData(sysAttMain.getFdId(), "s1.jpg", out, request);
						}
					}
					return null;
				}
			}
		} catch (Exception e) {
			streamClose(null, out);
			messages.addError(e);
		} finally {
			streamClose(null, out);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return null;
		}
	}

	/**
	 * token中解析出附件id
	 * @param request
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private String parserToken2Id(HttpServletRequest request, String token)
			throws Exception {

		if (StringUtil.isNull(token)) {
			return null;
		}

		String desc;

		try {
			desc = SysAttCryptUtil.decryptByLicenseId(token);
		} catch (Exception e) {
			logger.error("附件token解析失败：", e);
			return null;
		}

		String auth = StringUtil.getParameter(desc, "auth");

		// 没有文档阅读权限，没有权限
		if (!"true".equals(auth)) {

			if (logger.isDebugEnabled()) {
				logger.debug("没有权限访问此附件");
			}

			return null;
		}

		String timestamp = StringUtil.getParameter(desc, "time");
		long curTimestamp = getServiceImp(request).getCurTimestamp().getTime();

		// token失效，没有权限
		if (Long.valueOf(timestamp) < curTimestamp - 60L * 60L * 1000L) {

			if (logger.isDebugEnabled()) {
				logger.debug("附件token过期了");
			}

			return null;
		}

		String fdAttMainId = StringUtil.getParameter(desc, "fdId");

		if (StringUtil.isNull(fdAttMainId)) {

			if (logger.isDebugEnabled()) {
				logger.debug("token中没有id信息");
			}

			return null;
		}

		return fdAttMainId;

	}

	/**
	 * 加载多媒体流，移动端视频音频播放使用
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();

		String token = request.getParameter("token");
		String fdType = request.getParameter("fdType");

		// 没有令牌，没有权限
		if (StringUtil.isNull(token)) {
			return null;
		}

		String fdAttMainId = parserToken2Id(request, token);

		if (StringUtil.isNull(fdAttMainId)) {
			return null;
		}

		SysAttMain sysAttMain = null;

		SysAttRtfData sysAttRtfData = null;

		boolean isRtf = false;

		//判断是否是富文本框内文件
		if(fdType != null && "rtf".equals(fdType)) {
			isRtf = true;
		}

		if(isRtf) {
			sysAttRtfData = getServiceImp(request).findRtfDataByPrimaryKey(fdAttMainId);
			sysAttMain = new SysAttMain();
			sysAttMain.setFdId(fdAttMainId);
			sysAttMain.setFdSize(sysAttRtfData.getFdSize());
			sysAttMain.setFdModelId(sysAttRtfData.getFdModelId());
			sysAttMain.setFdModelName(sysAttRtfData.getFdModelName());
			sysAttMain.setFdFileName(sysAttRtfData.getFdFileName());
			sysAttMain.setFdFileId(sysAttRtfData.getFdFileId());
			sysAttMain.setFdContentType(sysAttRtfData.getFdContentType());
		}else {
			sysAttMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdAttMainId);
		}

		SysAttFile attFile = getAttUploadService().getFileById(sysAttMain.getFdFileId());
		ISysFileLocationDirectService directService = SysFileLocationUtil
				.getDirectService(attFile.getFdAttLocation());

		// 视频直连
		if (directService.isSupportDirect("")) {//IE9以下的浏览器不能直连上传，但可以直连下载

			String fdPath = SysAttViewerUtil.getMediaFilePath(sysAttMain);
			if(isRtf) {
				viewDirectByRtf(request, response, fdPath,sysAttMain.getFdFileName(),sysAttMain.getFdId());
			}else {
				viewDirect(request, response, fdPath,sysAttMain.getFdFileName(),sysAttMain.getFdId());
			}
			return null;

		}

		String range = request.getHeader("Range");

		// 部分手机，如小米跟魅族请求头部并没有range信息
		if (range == null) {
			String forwardUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=playByNoRange&token=" + token;
			if(isRtf) {
				forwardUrl += "&fdType=" + fdType;
			}
			return new ActionForward(forwardUrl);
		}

		commonLogFind(request,sysAttMain,"play");

		String fileName = sysAttMain.getFdFileName();

		ISysFileLocationProxyService proxyService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());

		/**
		 * 因为需要随机读流，必须返回File对象
		 */
		String filePath = attFile.getFdFilePath();
		String fileViewerKey = SysAttViewerUtil.getFileViewerKey(sysAttMain);
		File file = null;
		String convertFilePath = "";
		if("videoToFlv-Aspose".equals(fileViewerKey)) {
			convertFilePath = filePath + "_convert/videoToFlv-Aspose_flv";
		}else {
			convertFilePath = filePath + "_convert/videoToMp4-Aspose_flv";
		}
		String relaFilePath = "";
		SysAttFile file1 = sysAttMainService.getFile(sysAttMain.getFdId());
		String pathPrefix = null;
		if (file1.getFdCata() != null) {
			pathPrefix = file1.getFdCata().getFdPath();
		}
		if (proxyService.doesFileExist(convertFilePath,pathPrefix)) {
			relaFilePath = convertFilePath;
		}else {// 未转换则走源文件
			relaFilePath = filePath;
		}
		String tempFilePath = proxyService.getTempFilePath(relaFilePath,attFile.getFdMd5());
		file = new File(tempFilePath);
		if(!file.exists()) {//如果临时文件存在，不重复下载，以免解密或从oss上down文件耗时过长，引起拖动进度条视频卡顿
			file = proxyService.readFileToTemp(relaFilePath, pathPrefix, attFile.getFdMd5());
		}
		if(file == null || !file.exists()){
			messages.addError(new Exception("file not exists:" + relaFilePath));
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
			return mapping.findForward("failure");
		}
		//String fileName = sysAttMain.getFdFileName();
		long length = file.length();
		List<Range> ranges = new ArrayList<Range>();
		if (range != null) {
			for (String part : range.substring(6).split(",")) {
				long start = sublong(part, 0, part.indexOf("-"));
				long end = sublong(part, part.indexOf("-") + 1, part.length());

				if (start == -1L) {
					start = length - end;
					end = length - 1L;
				} else if ((end == -1L) || (end > length - 1L)) {
					end = length - 1L;
				}
				ranges.add(new Range(start, end, length));
			}
		}
		fileName = encodeFileName(request,fileName);
		response.reset();
		response.setBufferSize(10240);
		response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
		response.setHeader("Accept-Ranges", "bytes");
		RandomAccessFile access = null;
		InputStream input = null;
		OutputStream output = null;
		try {
			access = new RandomAccessFile(file, "r");
			output = response.getOutputStream();
			if (ranges.size() == 1) {
				SysAttMainAction.Range r = ranges.get(0);
				response.setContentType(sysAttMain.getFdContentType());
				response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
				response.setStatus(206);
				input = new RandomAccessFileInputStream(access, r.start, r.length);
				IOUtil.write(input, output);
			}
		} catch (Exception localException1) {
			messages.addError(localException1);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
			if (access != null) {
				access.close();
			}
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
			return mapping.findForward("failure");
		}
		return null;
	}

	/**
	 * 加载头部没有range信息多媒体流
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward playByNoRange(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		OutputStream out = null;
		InputStream in = null;

		String fdAttMainId = parserToken2Id(request, request.getParameter("token"));
		String fdType = request.getParameter("fdType");

		if (StringUtil.isNull(fdAttMainId)) {
			return null;
		}

		SysAttMain att = null;

		SysAttRtfData sysAttRtfData = null;

		boolean isRtf = false;
		//判断是否为富文本框内视频文件
		if(fdType != null && "rtf".equals(fdType)) {
			isRtf = true;
		}


		try {
			if(isRtf) {
				sysAttRtfData = getServiceImp(request).findRtfDataByPrimaryKey(fdAttMainId);
				att = new SysAttMain();
				att.setFdId(fdAttMainId);
				att.setFdSize(sysAttRtfData.getFdSize());
				att.setFdModelId(sysAttRtfData.getFdModelId());
				att.setFdModelName(sysAttRtfData.getFdModelName());
				att.setFdFileName(sysAttRtfData.getFdFileName());
				att.setFdFileId(sysAttRtfData.getFdFileId());
				att.setFdContentType(sysAttRtfData.getFdContentType());
			}else {
				att = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdAttMainId);
			}
			commonLogFind(request,att,"playByNoRange");
			String fileContentType = null;

			String filename = encodeFileName(request, att.getFdFileName());
			fileContentType = FileMimeTypeUtil.getContentType(filename);
			if (StringUtil.isNull(fileContentType)) {
				fileContentType = att.getFdContentType();
			}

			// 非视频类型直接跳过
			if (StringUtil.isNotNull(fileContentType)
					&& fileContentType.indexOf("video") < 0) {
				return null;
			}

			out = response.getOutputStream();

			long fileSize = 0;
			in = SysAttViewerUtil.getMediaFileInputStream(att);
			fileSize = att.getFdSize().intValue();
			int tmpSize = in.available();
			if (tmpSize != fileSize && tmpSize > 0) {
				fileSize = tmpSize;
			}
			response.reset();
			if (fileSize > 0) {
				response.setContentLength((int) fileSize);
			}


			if(!"video/mp4".equals(fileContentType)){
				fileContentType = "video/x-flv";
			}
			response.setContentType(fileContentType);
			response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
			IOUtil.write(in, out);
			return null;
		} catch (Exception e) {
			streamClose(in, out);
			messages.addError(e);
		} finally {
			streamClose(in, out);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	/**
	 * 获取视频播放token
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward handleAttToken(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		try {
			if (StringUtil.isNotNull(fdId)) {
				long expire = (getServiceImp(request)).getCurTimestamp().getTime();
				String fileInfo = "";
				if(fdType != null && "rtf".equals(fdType)) {
					fileInfo = "fdId=" + (StringUtil.isNotNull(fdId) ? fdId : "") + "&time=" + expire + "&auth="
							+ playAuthByRtf(request);
				}else {
					fileInfo = "fdId=" + (StringUtil.isNotNull(fdId) ? fdId : "") + "&time=" + expire + "&auth="
							+ playAuth(request);
				}
				fileInfo = SysAttCryptUtil.encryptByLicenseId(fileInfo);

				jsonObject.put("token", fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonObject.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	private Boolean playAuth(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		// 没有id，没有权限
		if (StringUtil.isNull(fdId)) {
			return false;
		}
		String url = null;
		IBaseModel mainModel = null;
		SysAttMain attMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId, SysAttMain.class, true);
		if (attMain != null) {
			if (StringUtil.isNotNull(attMain.getFdModelId()) && StringUtil.isNotNull(attMain.getFdModelName())) {
				mainModel = sysAttMainService.findByPrimaryKey(attMain.getFdModelId(), attMain.getFdModelName(), true);
			}
		}
		if (mainModel != null) {
			url = ModelUtil.getModelUrl(mainModel);
		}
		return UserUtil.checkAuthentication(url, "GET");

	}

	private Boolean playAuthByRtf(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		// 没有id，没有权限
		if (StringUtil.isNull(fdId)) {
			return false;
		}
		SysAttRtfData attRtfData = getServiceImp(request).findRtfDataByPrimaryKey(fdId);
		/**
		 *  富文本框视频暂不进行权限校验
		 */
		if (attRtfData != null) {
			return true;
		}
		return false;
	}

	private static long sublong(String value, int beginIndex, int endIndex) {
		String substring = value.substring(beginIndex, endIndex);
		return substring.length() > 0 ? Long.parseLong(substring) : -1L;
	}

	protected class Range {
		long start;
		long end;
		long length;
		long total;

		public Range(long start, long end, long total) {
			this.start = start;
			this.end = end;
			this.length = end - start + 1;
			this.total = total;
		}
	}

	/**
	 * 迁移附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toggleAttchment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-toggleAttchment", true, getClass());
		String operate = request.getParameter("operate");
		String source = request.getParameter("source");
		String target = request.getParameter("target");
		String startDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String toggleStartTime = request.getParameter("toggleBeginTime");
		String toggleEndTime = request.getParameter("toggleEndTime");
		JSONObject json = new JSONObject();
		try {
			json = getSysAttToggleService().saveToggleAttchment(operate, source, target, startDate
					, endDate, Integer.valueOf(toggleStartTime), Integer.valueOf(toggleEndTime));
			json.put("status", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", "0");
			json.put("errorMsg", e.getMessage());
		}
		TimeCounter.logCurrentTime("Action-toggleAttchment", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward findFilePath(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-findFilePath", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdDocIds = request.getParameter("fdDocIds");
		JSONArray jsonArr = new JSONArray();
		try {
			if (StringUtil.isNotNull(fdDocIds)) {
				jsonArr = getServiceImp(request).findFilePath(fdDocIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-findFilePath", false, getClass());
		// if (StringUtil.isNotNull(docNum)) {

		// json.put("docNum", docNum);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonArr.toString());
		response.getWriter().flush();
		response.getWriter().close();
		// }
		return null;
	}

	public ActionForward restorefile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-findFilePath", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] fdDocIds = request.getParameterValues("List_Selected");
		JSONObject jsonObj = new JSONObject();
		try {
			if (fdDocIds.length > 0) {
				jsonObj = getServiceImp(request).restorefile(fdDocIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-findFilePath", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonObj.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 在附件View页面添加查看主文档按钮，返回主文档信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 返回主文档页面，否则将将错误信息返回附件view页面
	 * @throws Exception
	 */
	public ActionForward findMainDocInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-findMainDocInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObj = new JSONObject();

		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				jsonObj = getServiceImp(request).findMainDocInfo(fdId);
			}
			if (jsonObj != null) {
				request.setAttribute("lui-source", jsonObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-findMainDocInfo", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 保存SysAttFile，用于OSS上传回调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFile(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();

		try {

			String fdMd5 = request.getParameter("md5");
			String fdPath = request.getParameter("path");
			String fdId = request.getParameter("fileId");
			String fdFileSize = request.getParameter("fileSize");
			getServiceImp(request).addFile(fdMd5, fdFileSize, fdId, fdPath);
			net.sf.json.JSONObject json = new net.sf.json.JSONObject();
			request.setAttribute("lui-source", json);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveFile", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward updateFileName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			String fdId = request.getParameter("id");
			String fdName = request.getParameter("name");

			SysAttMain sysAttMain = null;
			JSONObject result = new JSONObject();
			Boolean auth = true;

			if (fdId != null && fdId.trim().length() > 0) {
				sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);

				if(sysAttMain!=null){
					auth = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId="
									+ fdId,
							"get");
					if(auth){
						sysAttMain.setFdFileName(fdName);
						if(UserOperHelper.allowLogOper("updateFileName", getServiceImp(request).getModelName())){
							UserOperContentHelper.putUpdate(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName())
									.putSimple("fdFileName", sysAttMain.getFdFileName(), fdName);
						}
						getServiceImp(request).update(sysAttMain);
					}
				}
			}

			if(auth) {
				result.put("isUpdate", true);
			} else{
				result.put("isUpdate", false);
				result.put("errorInfo", "Permission denied!");
			}

			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}

		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward checkEditName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			String fdId = request.getParameter("id");

			SysAttMain sysAttMain = null;
			JSONObject result = new JSONObject();
			Boolean auth = true;

			if (fdId != null && fdId.trim().length() > 0) {
				sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);

				if(sysAttMain!=null){
					auth = UserUtil.checkAuthentication(
							"/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId="
									+ fdId,
							"get");
				}
			}

			result.put("auth", auth);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}

		return null;
	}

	public ActionForward editName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();

		String fdFileName = request.getParameter("fdFileName");

		if(StringUtil.isNotNull(fdFileName)){
		//	fdFileName =  URLDecoder.decode(fdFileName,"UTF-8");
			request.setAttribute("fdFileName", URLEncoder.encode(fdFileName,"UTF-8"));
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editName", mapping, form, request, response);
		}
	}

	private ISysAttUploadService sysAttUploadService = null;
	public ISysAttUploadService getAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}

	public ActionForward zipView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								 HttpServletResponse response) throws Exception, Throwable {
		String fdId = request.getParameter("fdId");
		JSONArray ja = new JSONArray();
		SysAttMain att = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId, SysAttMain.class, true);
		if(att!=null){
			SysAttFile attFile = getAttUploadService().getFileById(att.getFdFileId());
			String filePath = ResourceUtil.getKmssConfigString("kmss.resource.path")+attFile.getFdFilePath();
			if(filePath.contains("\\")){
				filePath = filePath.replaceAll("\\\\", "/");
			}
			File file = new File(filePath);
			File zipfile = new File(filePath+"_uncompress");
			if(file.exists()&&zipfile.exists()){
				if(zipfile.isDirectory()){
					Map<String,String> map = new HashMap<String,String>();
					getFile(zipfile, map);
					if(map.size()>0){
						getQueueService().updateAtt(map, att);
					}
					Map<String, Boolean> hasview = getViewer(request, zipfile);
					getFile(zipfile, ja, request,hasview);
				}
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(ja);
		return null;
	}

	private ISysFileConvertQueueService queueService = null;

	public ISysFileConvertQueueService getQueueService() {
		if(queueService==null){
			queueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
		}
		return queueService;
	}

	private void getFile(File file,Map<String,String> map) throws Exception{
		File[] files = file.listFiles();
		if(files!=null&&files.length>0){
			for(File fl:files){
				if (fl.isFile()&&fl.getName().length()!=32) {
					map.put(fl.getName(), fl.getCanonicalPath());
				}else if(fl.isDirectory()&&!fl.getName().endsWith("_convert")){
					getFile(fl, map);
				}
			}
		}
	}

	private void getAttFile(File file,Map<String,String> map) throws Exception{
		File[] files = file.listFiles();
		if(files!=null&&files.length>0){
			for(File fl:files){
				if (fl.isFile()&&fl.getName().length()==32) {
					map.put(fl.getName(), fl.getCanonicalPath());
				}else if(fl.isDirectory()&&!fl.getName().endsWith("_convert")){
					getFile(fl, map);
				}
			}
		}
	}

	private JSONArray getFile(File file,JSONArray ja,HttpServletRequest request,Map<String, Boolean> hasview) throws Exception{
		File[] files = file.listFiles();
		if(files!=null&&files.length>0){
			File dtemp = null;
			//List<SysAttMain> atts = null;
			net.sf.json.JSONObject json = null;
			for(File fl:files){
				if(fl.isFile()){
					json = new net.sf.json.JSONObject();
					json.put("view", "1");
					json.put("id", fl.getName());
					HQLInfo hql = new HQLInfo();
					hql.setWhereBlock("fdFileId=:filename");
					hql.setParameter("filename", fl.getName());
					Object obj = getServiceImp(request).findFirstOne(hql);
					if(obj!=null){
						SysAttMain att = (SysAttMain)obj;
						json.put("name", att.getFdFileName());
						json.put("attId", att.getFdId());
						json.put("size", att.getFdSize());
						if(hasview.containsKey(att.getFdId())){
							json.put("hasViewer", hasview.get(att.getFdId()));
						}else{
							json.put("hasViewer", false);
						}
					}else{
						json.put("hasViewer", false);
						json.put("view", "0");
						json.put("name", fl.getName());
					}
					json.put("type", "file");
					json.put("path", fl.getCanonicalPath());
					ja.add(json);
				}else if(fl.isDirectory()&&!fl.getName().endsWith("_convert")){
					json = new net.sf.json.JSONObject();
					json.put("view", "0");
					json.put("type", "dir");
					json.put("name", fl.getName());
					json.put("path", fl.getCanonicalPath());
					json.put("child", getFile(fl, new JSONArray(),request,hasview));
					ja.add(json);
				}
			}
		}
		return ja;
	}

	private Map<String,Boolean> getViewer(HttpServletRequest request,File file) throws Exception{
		Map<String,Boolean> attmap = new HashMap<String,Boolean>();
		Map<String,String> map = new HashMap<String,String>();
		getAttFile(file, map);
		//List atts = null;
		List<SysAttMain> mains = new ArrayList<SysAttMain>();
		for(String key:map.keySet()){
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock("fdFileId=:fdFileId");
			hql.setParameter("fdFileId", key);
			Object obj = getServiceImp(request).findFirstOne(hql);
			if(obj!=null) {
				mains.add((SysAttMain) obj);
			}
		}
		Map<String, Boolean> cr = SysAttViewerUtil.getConvertedResult(mains);

		for(SysAttMain main:mains){
			String crKey = StringUtil.isNotNull(main.getFdFileId()) ? main.getFdFileId() : main.getFdId();
			Boolean attCr = cr.get(crKey);
			if (attCr != null && attCr.equals(Boolean.TRUE)
					&& main.getFdContentType().indexOf("video") < 0
					|| main.getFdContentType().indexOf("mp4") >= 0
					|| main.getFdContentType().indexOf("m4v") >= 0
					|| main.getFdContentType().indexOf("audio") >= 0) {
				attmap.put(main.getFdId(), true);
			}else{
				attmap.put(main.getFdId(), false);
			}
		}
		return attmap;
	}

	/**
	 * 记录附件读取类操作的操作日志
	 * @param request
	 * @param sysAttMain
	 * @param logPoint
	 */
	private void commonLogFind(HttpServletRequest request,SysAttMain sysAttMain,String logPoint){
		boolean allowLog =
				UserOperHelper.allowLogOper(logPoint,getServiceImp(request).getModelName());
		if(allowLog){
			UserOperContentHelper.putFind(sysAttMain.getFdId(),sysAttMain.getFdFileName(),SysAttMain.class.getName());
		}
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {

		hqlInfo.setWhereBlock("sysAttMain.fdModelId is not null ");

		String driverClass = ResourceUtil
				.getKmssConfigString("hibernate.connection.driverClass");
		if (!"oracle.jdbc.driver.OracleDriver".equals(driverClass)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "sysAttMain.fdModelId <> ''"));
		}


		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttMain.class);

		// 我的附件筛选
		String myAtt = request.getParameter("q.myAtt");

		if (StringUtil.isNotNull(myAtt)) {

			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);

			// 我上传的
			if ("upload".equals(myAtt)) {
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
								"sysAttMain.fdCreatorId = :fdCreatorId"));
				hqlInfo.setParameter("fdCreatorId",
						UserUtil.getUser().getFdId());
			}

			// 我下载的
			if ("download".equals(myAtt)) {
				hqlInfo.setJoinBlock(
						",com.landray.kmss.sys.attachment.model.SysAttDownloadLog sysAttDownloadLog");
				hqlInfo.setWhereBlock(StringUtil.linkString(
						hqlInfo.getWhereBlock(), " and ",
						"sysAttMain.fdId = sysAttDownloadLog.fdAttId and sysAttDownloadLog.docCreatorId = :fdCreatorId"));
				hqlInfo.setParameter("fdCreatorId",
						UserUtil.getUser().getFdId());
			}

			// 我借阅的
			if ("borrow".equals(myAtt)) {
				hqlInfo.setJoinBlock(
						",com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow sysAttBorrow");
				hqlInfo.setWhereBlock(StringUtil.linkString(
						hqlInfo.getWhereBlock(), " and ",
						"sysAttMain.fdId = sysAttBorrow.fdAttId and sysAttBorrow.fdBorrowers.fdId = :fdCreatorId"));
				hqlInfo.setParameter("fdCreatorId",
						UserUtil.getUser().getFdId());
			}
		}

		// 文件类型筛选
		String[] fileType = cv.polls("fileType");

		if (ArrayUtils.isEmpty(fileType)) {
			return;
		}

		List<String> valueList = Arrays.asList(fileType);
		buildFileTypeHql(valueList, hqlInfo);

	}

	/**
	 * 文件格式筛选组合
	 * @param valueList
	 * @param hqlInfo
	 */
	@SuppressWarnings("unchecked")
	private void buildFileTypeHql(List<String> valueList, HQLInfo hqlInfo) {

		List<String> others = new ArrayList<>();
		List<String> fileTypeList = new ArrayList<>();

		List<String> doc = ArrayUtil.convertArrayToList(new String[] {
				"application/msword",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document" });
		List<String> ppt = ArrayUtil.convertArrayToList(new String[] {
				"application/vnd.ms-powerpoint",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation" });
		List<String> pdf = ArrayUtil
				.convertArrayToList(new String[] { "application/pdf" });
		List<String> excel = ArrayUtil.convertArrayToList(new String[] {
				"application/vnd.ms-excel",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
		List<String> pic = ArrayUtil
				.convertArrayToList(new String[] { "image/bmp", "image/jpeg",
						"image/gif", "image/cis-cod", "image/ief", "image/png",
						"image/pipeg", "image/x-icon", "image/x-xwindowdump",
						"image/x-portable-anymap", "image/tiff" });
		List<String> video = ArrayUtil.convertArrayToList(new String[] {
				"audio/x-pn-realaudio", "audio/wrf", "audio/f4v", "video/mp4",
				"video/3gpp", "video/wmv9", "video/x-ms-wmv", "video/x-flv",
				"video/x-ms-asf", "video/x-msvideo", "video/x-sgi-movie",
				"video/quicktime", "video/mpeg", "video/x-la-asf" });
		List<String> audio = ArrayUtil.convertArrayToList(
				new String[] { "audio/mpeg", "audio/x-wav", "audio/ogg" });

		if (valueList.contains("doc")) {
			fileTypeList.addAll(doc);
		}

		if (valueList.contains("ppt")) {
			fileTypeList.addAll(ppt);
		}

		if (valueList.contains("pdf")) {
			fileTypeList.addAll(pdf);
		}

		if (valueList.contains("excel")) {
			fileTypeList.addAll(excel);
		}

		if (valueList.contains("pic")) {
			fileTypeList.addAll(pic);
		}

		if (valueList.contains("audio")) {
			fileTypeList.addAll(audio);
		}

		if (valueList.contains("video")) {
			fileTypeList.addAll(video);
		}

		if (valueList.contains("others")) {

			others.addAll(doc);
			others.addAll(ppt);
			others.addAll(pdf);
			others.addAll(excel);
			others.addAll(pic);
			others.addAll(audio);
			others.addAll(video);

			others.removeAll(fileTypeList);

			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "sysAttMain.fdContentType not in (:others)"));
			hqlInfo.setParameter("others", others);
		} else {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "sysAttMain.fdContentType in (:others)"));
			hqlInfo.setParameter("others", fileTypeList);
		}
	}

	/**
	 * 统计阅读页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistics(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-statistics", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			loadActionForm(mapping, form, request, response);
			SysAttMainForm attForm = (SysAttMainForm) form;
			getServiceImp(request).statistics(request, attForm);

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-statistics", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("statistics", mapping, form, request, response);
		}
	}

	/**
	 * 获取用户sessionId
	 *
	 * @throws Exception
	 */
	public ActionForward getSessionId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-count", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONObject json = new JSONObject();
			json.put("JSESSIONID", request.getSession().getId());

			String cookieHeader = request.getHeader("Cookie");
			if (StringUtil.isNotNull(cookieHeader)) {
				String[] cookies = cookieHeader.split(";");
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						String cookie = cookies[i].trim();
						json.put(cookie.substring(0, cookie.indexOf("=")), cookie.substring(cookie.indexOf("=") + 1, cookie.length()));
					}
				}
			}

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-count", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * 附件数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward count(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-count", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONObject json = new JSONObject();

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setGettingCount(true);

			String type = request.getParameter("type");

			hqlInfo.setWhereBlock("sysAttMain.fdModelId is not null "
					+ "and sysAttMain.fdModelId <> ''");

			// 我上传的
			if ("create".equals(type)) {
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
								"sysAttMain.fdCreatorId =:fdCreatorId"));
				hqlInfo.setParameter("fdCreatorId",
						UserUtil.getUser().getFdId());
			}

			hqlInfo.setSelectBlock("count(distinct sysAttMain.fdId)");

			List<Long> count = this.getServiceImp(request).findValue(hqlInfo);
			json.put("count", count.get(0));

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-count", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}


	/**
	 * 获取模块信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward modules(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-modules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONArray array = new JSONArray();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" sysAttMain.fdModelName");
			changeFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" ", "group by sysAttMain.fdModelName"));
			List<String> modules = getServiceImp(request).findValue(hqlInfo);
			for (int i = 0; i < modules.size(); i++) {

				net.sf.json.JSONObject json = new net.sf.json.JSONObject();
				String modelName = modules.get(i);

				String text =
						getServiceImp(request).getMainModuleName(modelName);

				if (StringUtil.isNull(text)) {
					continue;
				}
				json.element("text", text);
				json.element("value", modelName);
				array.add(json);
			}

			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-modules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward addOnlineFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		String mobileEditAttId = "";
		JSONObject json = new JSONObject();
		try {

			SysAttMain originalAtt = null;
			originalAtt = getServiceImp(request).addOnlineFile(request);
			if (originalAtt != null) {
				mobileEditAttId = originalAtt.getFdId();
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		json.put("editOnlineAttId", mobileEditAttId);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward addCloudOnlineFile(ActionMapping mapping,
											ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-addCloudOnlineFile", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String mobileEditAttId = "";
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {

			SysAttMain originalAtt = null;
			originalAtt = getServiceImp(request).addCloudOnlineFile(request);
			if (originalAtt != null) {
				mobileEditAttId = originalAtt.getFdId();
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			json.put("error", e);
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-addCloudOnlineFile", false,
				getClass());
		json.put("editOnlineAttId", mobileEditAttId);
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward addWpsOaassistOnlineFile(ActionMapping mapping,
												  ActionForm form, HttpServletRequest request,
												  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-addWpsOaassistOnlineFile", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String mobileEditAttId = "";
		JSONObject json = new JSONObject();
		try {

			SysAttMain originalAtt = null;
			originalAtt = getServiceImp(request)
					.addWpsOaassistOnlineFile(request);
			if (originalAtt != null) {
				mobileEditAttId = originalAtt.getFdId();
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			json.put("error", e);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-addWpsOaassistOnlineFile", false,
				getClass());
		json.put("editOnlineAttId", mobileEditAttId);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward updateRelation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean result = false;
		JSONObject json = new JSONObject();
		try {
			result = getServiceImp(request).updateRelation(request);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		json.put("status", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward updateCloudRelation(ActionMapping mapping,
											 ActionForm form, HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean result = false;
		JSONObject json = new JSONObject();
		try {
			result = getServiceImp(request).updateCloudRelation(request);

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		json.put("status", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward getWpsUrlAndToken(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			json = getServiceImp(request).getWpsUrlAndToken(request);

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward getWpsCloudViewUrl(ActionMapping mapping,
											ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			boolean canEdit = false;
			String fdMode = request.getParameter("fdMode");
			String history = request.getParameter("history");
			if (StringUtils.isNotBlank(fdMode) && "write".equals(fdMode)) {
				canEdit = true;
			}
			if (StringUtils.isNotBlank(history)) {
				history = "1";
			} else {
				history = "0";
			}

			// 文件以本地文件为准，故去掉版本查看功能
			history = "0";

			String fdAttMainId = request.getParameter("fdAttMainId");
			if (StringUtil.isNotNull(fdAttMainId)) {
				String contentFlag = request.getParameter("contentFlag");
				if (StringUtil.isNotNull(contentFlag)) {
					net.sf.json.JSONObject result = SysAttWpsCloudUtil.getWpsCloudViewUrl(fdAttMainId, canEdit, history,Boolean.parseBoolean(contentFlag));
					json.putAll(result);
				}else{
					net.sf.json.JSONObject result = SysAttWpsCloudUtil.getWpsCloudViewUrl(fdAttMainId, canEdit, history);
					json.putAll(result);
				}
			}

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
		// response.sendRedirect(result);
		// request.getRequestDispatcher(result).forward(request, response);
	}

	public ActionForward getWpsOAasisstUrl(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fileId = request.getParameter("fileId");
			String modelName = request.getParameter("wpsExtAppModel");
			String url = getServiceImp(request).getRestDownloadUrl(fileId,modelName);
			String contentFlag = request.getParameter("contentFlag");

			json.put("downUrl", url+"&from=wpsOAasisst");
			json.put("userId", UserUtil.getKMSSUser().getUserId());
			json.put("userName", UserUtil.getKMSSUser().getUserName());

			SysAttMain sysAttMain = (SysAttMain) getServiceImp(request)
					.findByPrimaryKey(fileId);
			json.put("attMainId", sysAttMain.getFdId());
			json.put("attFileName", sysAttMain.getFdFileName());
			json.put("modelId", sysAttMain.getFdModelId());
			json.put("modelName", sysAttMain.getFdModelName());
			json.put("fdKey", sysAttMain.getFdKey());
			boolean canCopy = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId="
							+ fileId,
					"GET");
			json.put("canCopy", canCopy);
			boolean canPrint = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId="
							+ fileId,
					"GET");


			boolean download = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
							+ fileId,
					"GET");
			if(StringUtil.isNotNull(contentFlag) && "true".equals(contentFlag)){
				boolean canPrintContent =  UserUtil.checkAuthentication(
						"/sys/attachment/sys_att_main/sysAttMain.do?method=printContent&fdModelName="+sysAttMain.getFdModelName()+"&fdId="+ sysAttMain.getFdId(),"GET");
				if (canPrint && canPrintContent) {
					canPrint=true;
				}else{
					canPrint=false;
				}
			}
			json.put("canPrint", canPrint);
			json.put("download", download);

			// System.out.println(SysAttViewerUtil.getWaterMarkConfigInDB());

			net.sf.json.JSONObject watermarkCfg = SysAttViewerUtil
					.getWaterMarkConfigInDB(true);
			if (watermarkCfg.get("markWordVar") != null
					&& "true".equals(watermarkCfg.get("showWaterMark"))) {
				String waterText = UserUtil.getKMSSUser().getUserName();
				if (watermarkCfg.get("markWordVar") != null
						&& "true".equals(watermarkCfg.get("showWaterMark"))
						&& "word".equals(watermarkCfg.get("markType"))) {
					waterText = SysAttViewerUtil.getVarMarkWord(
							watermarkCfg.getString("markWordVar"), request);
				}
				json.put("waterText", waterText);
			}
			json.put("watermarkCfg", watermarkCfg);

			//网页端文档状态心跳
			String wpsWebDocumentHeart = SysAttConfigUtil.getConfigInfo("wpsWebDocumentHeart");
			if(StringUtil.isNull(wpsWebDocumentHeart))
			{
				wpsWebDocumentHeart = WPS_WEB_DOCUMENT_HEART;
			}
			json.put("wpsWebDocumentHeart", Long.valueOf(wpsWebDocumentHeart));

//			if (!TokenGenerator.isInitialized()) {
//
//				String zdWebContentPath = ConfigLocationsUtil
//						.getKmssConfigPath();
//				if (StringUtil.isNotNull(zdWebContentPath)) {
//					zdWebContentPath = zdWebContentPath + "/LRToken";
//				}
//				TokenGenerator.loadFromKeyFile(zdWebContentPath);
//			}
//			Token token = TokenGenerator.getInstance()
//					.generateTokenByUserName(
//							UserUtil.getUser().getFdLoginName());

//			json.put("wpsoaassistToken", token.getTokenString());
       // 获取token信息
			String token = getSysAttachmentWpsAddinProvider().getToken();
			json.put("wpsoaassistToken", token);


		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	// 延长打开文件时间
	public ActionForward extendWpsOaassitEditMark(ActionMapping mapping,
												  ActionForm form, HttpServletRequest request,
												  HttpServletResponse response) throws Exception {
		logger.info("extendWpsOaassitEditMark");
		String fdId = request.getParameter("fdId");
		String useId = request.getParameter("useId");
		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {

			// 判断是否启用redis
			boolean redis = RedisConfig.ENABLED;
			String wpsDocumentLife = SysAttConfigUtil.getConfigInfo("wpsDocumentLife");
			if(StringUtil.isNull(wpsDocumentLife))
			{
				wpsDocumentLife = WPS_DOCUMENT_LIFE;
			}

			//Long documentLife = Long.valueOf(wpsDocumentLife);
			int documentLife = Integer.valueOf(wpsDocumentLife);
			if (redis) {
				long expires = DbUtils.getDbTimeMillis() + (documentLife * 1000);// 10秒有效;
				RedisCommands.getInstance()
						.set(WPS_OASISST_REDIS_KEY + useId + "_" + fdId,
								SerializeUtils.str2Byte(String.valueOf(expires)),
								documentLife);
			} else {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysAttachmentWps.sysAttMainId=:sysAttMainId and sysAttachmentWps.userId=:userId");
				hql.setParameter("sysAttMainId", fdId);
				hql.setParameter("userId", useId);
				Object obj = getSysAttachmentWpsServiceImp(
						request).findFirstOne(hql);
				if (obj == null) {
					SysAttachmentWps sysAttachmentWps = new SysAttachmentWps();
					sysAttachmentWps.setUserId(useId);
					sysAttachmentWps.setSysAttMainId(fdId);
					long expires = DbUtils.getDbTimeMillis() + (documentLife * 1000);// 5秒有效;
					sysAttachmentWps.setExpirationTime(new Date(expires));
					getSysAttachmentWpsServiceImp(request)
							.add(sysAttachmentWps);
				} else {
					SysAttachmentWps sysAttachmentWps = (SysAttachmentWps) obj;
					long expires = DbUtils.getDbTimeMillis() + (documentLife * 1000);// 5秒有效;
					sysAttachmentWps.setExpirationTime(new Date(expires));
					sysAttachmentWps.setDocAlterTime(new Date());
					getSysAttachmentWpsServiceImp(request)
							.update(sysAttachmentWps);
				}
			}
			// wpsaasisstCache.put(WPS_OASISST_KEY + useId + "_" + fdId,
			// expires);
		}

		return null;

	}

	// wps客户端请求是否关闭服务器
	public ActionForward checkWpsOaassitEditMark(ActionMapping mapping,
												 ActionForm form, HttpServletRequest request,
												 HttpServletResponse response) throws Exception {
		logger.info("checkWpsOaassitEditMark");
		// System.out.println("判断文档是否关闭");
		String fdId = request.getParameter("fdId");
		String useId = request.getParameter("useId");
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
			// 判断是否启用redis
			boolean redis = RedisConfig.ENABLED;
			if (redis) {
				boolean ishas = RedisCommands.getInstance()
						.exists(WPS_OASISST_REDIS_KEY + useId + "_" + fdId);
				if (ishas) {
					json.put("status", "open");
				} else {
					json.put("status", "close");
				}
			} else {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysAttachmentWps.sysAttMainId=:sysAttMainId and sysAttachmentWps.userId=:userId");
				hql.setParameter("sysAttMainId", fdId);
				hql.setParameter("userId", useId);
				Object obj = getSysAttachmentWpsServiceImp(
						request).findFirstOne(hql);
				if (obj==null) {
					json.put("status", "close");
				} else {
					SysAttachmentWps sysAttachmentWps = (SysAttachmentWps) obj;
					long expires = DbUtils.getDbTimeMillis();
					if (expires > sysAttachmentWps.getExpirationTime()
							.getTime()) {
						json.put("status", "close");
					} else {
						json.put("status", "open");
					}
				}
			}

			// System.out.println(ishas);

		} else {
			json.put("status", "close");
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();

		return null;

	}

	public ActionForward expiredWpsOaassitEditMark(ActionMapping mapping,
												   ActionForm form, HttpServletRequest request,
												   HttpServletResponse response) throws Exception {
		logger.info("expiredWpsOaassitEditMark");
		// System.out.println("网页关闭文件");
		JSONObject json = new JSONObject();
		String fdId = request.getParameter("fdId");
		String type = request.getParameter("type");
		// System.out.println(type);
		String useId = request.getParameter("userId");
		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
			// wpsaasisstCache.remove(WPS_OASISST_KEY + useId + "_" + fdId);
			// System.out.println(WPS_OASISST_REDIS_KEY + useId + "_" + fdId);

			boolean redis = RedisConfig.ENABLED;

			if (redis) {
				RedisCommands.getInstance()
						.del(WPS_OASISST_REDIS_KEY + useId + "_" + fdId);
			} else {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysAttachmentWps.sysAttMainId=:sysAttMainId and sysAttachmentWps.userId=:userId");
				hql.setParameter("sysAttMainId", fdId);
				hql.setParameter("userId", useId);
				Object obj = getSysAttachmentWpsServiceImp(
						request).findFirstOne(hql);
				if (obj!=null) {
					getSysAttachmentWpsServiceImp(request).delete((SysAttachmentWps) obj);
				}
			}
			json.put("status", true);

		} else {
			json.put("status", false);
		}
		SysAttMain sysAttMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId);
		Map<String, String> params = new HashMap<>();
		params.put("fdId", fdId);
		params.put("modelId", sysAttMain.getFdModelId());
		params.put("modelName", sysAttMain.getFdModelName());
		params.put("fdKey", sysAttMain.getFdKey());
		params.put("userId", useId);
		params.put("type", "unlock");
		SysAttachmentWpsAddonsLockUtils.handle(params);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;

	}

	public ActionForward handleEditWpsOaassist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String modelId = request.getParameter("modelId");
			String modelName = request.getParameter("modelName");
			String fdKey = request.getParameter("fdKey");
			String userId = request.getParameter("userId");
			String type = request.getParameter("type");
			Map<String, String> params = new HashMap<>();
			params.put("fdId", fdId);
			params.put("modelId", modelId);
			params.put("modelName", modelName);
			params.put("fdKey", fdKey);
			params.put("userId", userId);
			params.put("type", type);
			if ("unlock".equals(type)) {
				String lockQueenStr = request.getParameter("lockQueen");
				params.put("lockQueen", lockQueenStr);
			}
			boolean lock = SysAttachmentWpsAddonsLockUtils.handle(params);
			result.put("success", lock);
			if ("lock".equals(type) && !lock) {
				SysAttMain sysAttMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId,null,true);
				String operatorName = UserUtil.getUser(sysAttMain.getFdPersonId()).getFdName();
				result.put("operatorName", operatorName);
			}
		} catch (Exception e) {
			logger.error("WPS加载项客户端编辑控制异常：", e);
		} finally {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}
		return null;
	}

	public ActionForward uploadWpsOaassist(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String useId = request.getParameter("useId");
		boolean ishas = false;

		TimeCounter.logCurrentTime("uploadWpsOaassist", true, getClass());

		// 判断是否启用redis
		boolean redis = RedisConfig.ENABLED;
		if (redis) {
			ishas = RedisCommands.getInstance()
					.exists(WPS_OASISST_REDIS_KEY + useId + "_" + fdId);
		} else {
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock(
					"sysAttachmentWps.sysAttMainId=:sysAttMainId and sysAttachmentWps.userId=:userId");
			hql.setParameter("sysAttMainId", fdId);
			hql.setParameter("userId", useId);
			Object obj = getSysAttachmentWpsServiceImp(request).findFirstOne(hql);
			if (obj!=null) {
				SysAttachmentWps sysAttachmentWps = (SysAttachmentWps)obj;
				long expires = DbUtils.getDbTimeMillis();
				if (expires <= sysAttachmentWps.getExpirationTime()
						.getTime()) {
					ishas = true;
				}
			}
		}

		JSONObject json = new JSONObject();
		logger.info("fdId:" + fdId + "\n  useId:" + useId + "");
		logger.info("redis has value:" + ishas);
		if (!ishas) {
			SysAttMain sysAttMain = (SysAttMain) getServiceImp(request).findByPrimaryKey(fdId);
			Map<String, String> params = new HashMap<>();
			params.put("fdId", fdId);
			params.put("modelId", sysAttMain.getFdModelId());
			params.put("modelName", sysAttMain.getFdModelName());
			params.put("fdKey", sysAttMain.getFdKey());
			params.put("userId", useId);
			params.put("type", "unlock");
			SysAttachmentWpsAddonsLockUtils.handle(params);
			json.put("flag", "error");
			json.put("message", "ekp会话已过期");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
			logger.info("startSave");
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("file");
			InputStream is = null;
			try {
				InputStream in = multipartFile.getInputStream();

				SysAttMain sysAttMain = (SysAttMain) getServiceImp(request)
						.findByPrimaryKey(fdId);

				logger.info("附件FdfileId:"+sysAttMain.getFdFileId());

				sysAttMain.setInputStream(in);
				//getServiceImp(request).update(sysAttMain);
				getServiceImp(request).updateByUser(sysAttMain,useId);
				json.put("flag", "ok");
				logger.info("修改后的附件FdfileId:"+sysAttMain.getFdFileId());
				if(SysAttWpsCloudUtil.isEnable()){
					logger.info("进入云文档:"+sysAttMain.getFdFileId());
					SysAttWpsCloudUtil.syncAttToAddByMainId(fdId);
				}
				logger.info("确认修改后的附件FdfileId:"+sysAttMain.getFdFileId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("flag", "error");
				json.put("message", "保存失败");
			} finally {
				// TODO: handle finally clause
				if (is != null) {
					is.close();
				}
			}
		} else {
			json.put("flag", "error");
			json.put("message", "参数错误");
		}

		TimeCounter.logCurrentTime("uploadWpsOaassist", true, getClass());

		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward uploadWps(ActionMapping mapping,
								   ActionForm form, HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");

		TimeCounter.logCurrentTime("uploadWps", true, getClass());
		JSONObject json = new JSONObject();
		logger.info("fdId:" + fdId);

		if (StringUtil.isNotNull(fdId)) {
			logger.info("startSaveWps");
			SysAttMain sysAttMain = (SysAttMain) getServiceImp(request)
					.findByPrimaryKey(fdId);
			InputStream is = null;
			MultipartFile multipartFile = null;

			if(isPpt(sysAttMain.getFdFileName())){
				is=request.getInputStream();
			}else{
				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

				if(isWord(sysAttMain.getFdFileName())) {
					multipartFile=multipartHttpServletRequest.getFile("file");
				}

				if(isExcel(sysAttMain.getFdFileName())) {
					multipartFile=multipartHttpServletRequest.getFile("filedata");
				}
			}

			try {
				if(isPpt(sysAttMain.getFdFileName())){
					sysAttMain.setInputStream(is);
				}else{
					is = multipartFile.getInputStream();

					sysAttMain.setInputStream(is);
				}

				getServiceImp(request).updateByUser(sysAttMain,UserUtil.getUser().getFdId());
				json.put("flag", "ok");
				if(SysAttWpsCloudUtil.isEnable()){
					SysAttWpsCloudUtil.syncAttToAddByMainId(fdId);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("flag", "error");
				json.put("message", "保存失败");
			} finally {
				// TODO: handle finally clause
				if (is != null) {
					is.close();
				}
			}
		} else {
			json.put("flag", "error");
			json.put("message", "参数错误");
		}

		TimeCounter.logCurrentTime("uploadWps", true, getClass());

		if("error".equals(json.get("flag"))){
			logger.error("uploadWps方法保存失败！");
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward uploadWpsTmp(ActionMapping mapping,
								   ActionForm form, HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String uuId = request.getParameter("uuId");

		TimeCounter.logCurrentTime("uploadWpsTmp", true, getClass());
		JSONObject json = new JSONObject();

		logger.info("fdId:" + fdId);
		logger.info("uuId:" + uuId);

		if (StringUtil.isNotNull(fdId)) {
			logger.info("startSaveWpsTmp");
			SysAttMain sysAttMain = (SysAttMain) getServiceImp(request)
					.findByPrimaryKey(fdId);
			InputStream is = null;
			MultipartFile multipartFile = null;

			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

			if(isWord(sysAttMain.getFdFileName())) {
				multipartFile=multipartHttpServletRequest.getFile("file");
			}
			try {
				is = multipartFile.getInputStream();
				SysAttMain attMain = (SysAttMain) getServiceImp(request)
						.findByPrimaryKey(uuId);
				if(attMain==null){
					SysAttMain tmpAtt = new SysAttMain();
					tmpAtt.setFdId(uuId);
					tmpAtt.setInputStream(is);
					tmpAtt.setFdModelId("");
					tmpAtt.setFdModelName("");
					tmpAtt.setFdKey("tmpWpsOaassist");
					double fileSize = is.available();
					tmpAtt.setFdSize(fileSize);
					tmpAtt.setFdAttType("office");

					tmpAtt.setFdFileName(sysAttMain.getFdFileName());
					tmpAtt.setFdContentType(sysAttMain.getFdContentType());
					tmpAtt.setFdUploadTime(new Date());

					getServiceImp(request).add(tmpAtt);
				}else{
					attMain.setInputStream(is);
					getServiceImp(request).updateByUser(attMain,UserUtil.getUser().getFdId());
				}


				json.put("flag", "ok");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("flag", "error");
				json.put("message", "保存失败");
			} finally {
				// TODO: handle finally clause
				if (is != null) {
					is.close();
				}
			}
		} else {
			json.put("flag", "error");
			json.put("message", "参数错误");
		}

		TimeCounter.logCurrentTime("uploadWpsTmp", true, getClass());

		if("error".equals(json.get("flag"))){
			logger.error("uploadWpsTmp方法保存失败！");
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward uploadWpsByTmp(ActionMapping mapping,
									  ActionForm form, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		String editonlineFdId = request.getParameter("editonlineFdId");
		//String editonlinePrintFdId = request.getParameter("editonlinePrintFdId");
		String editonlineTmpFdId = request.getParameter("editonlineTmpFdId");
		//String editonlinePrintTmpFdId = request.getParameter("editonlinePrintTmpFdId");

		TimeCounter.logCurrentTime("uploadWpsByTmp", true, getClass());
		JSONObject json = new JSONObject();

		logger.info("editonlineFdId:" + editonlineFdId);
		logger.info("editonlineTmpFdId:" + editonlineTmpFdId);
		json.put("flag", "error");
		try {

			getServiceImp(request).updateByTmpAttmainId(editonlineFdId,editonlineTmpFdId);

			json.put("flag", "ok");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("flag", "error");
			json.put("message", "保存失败");
		}

		TimeCounter.logCurrentTime("uploadWpsByTmp", true, getClass());

		if("error".equals(json.get("flag"))){
			logger.error("uploadWpsByTmp方法保存失败！");
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward checkWpsOaassist(ActionMapping mapping,
										  ActionForm form, HttpServletRequest request,
										  HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String useId = request.getParameter("useId");
		JSONObject json = new JSONObject();

		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
			// 判断是否启用redis
			boolean redis = RedisConfig.ENABLED;
			if (redis) {
				boolean ishas = RedisCommands.getInstance().exists(WPS_OASISST_REDIS_KEY + useId + "_" + fdId);
				if (ishas) {
					json.put("flag", "ok");
				} else {
					json.put("flag", "error");
					json.put("message", "ekp会话已过期");
				}
			} else {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock("sysAttachmentWps.sysAttMainId=:sysAttMainId and sysAttachmentWps.userId=:userId");
				hql.setParameter("sysAttMainId", fdId);
				hql.setParameter("userId", useId);
				Object obj = getSysAttachmentWpsServiceImp(request).findFirstOne(hql);
				if (obj==null) {
					json.put("flag", "error");
					json.put("message", "ekp会话已过期");
				} else {
					SysAttachmentWps sysAttachmentWps = (SysAttachmentWps)obj;
					long expires = DbUtils.getDbTimeMillis();
					if (expires > sysAttachmentWps.getExpirationTime().getTime()) {
						json.put("flag", "error");
						json.put("message", "ekp会话已过期");
					} else {
						json.put("flag", "ok");
					}
				}
			}
		} else {
			json.put("flag", "error");
			json.put("message", "参数错误");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward listHistory(ActionMapping mapping, ActionForm form,
									 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdOriginId = request.getParameter("fdOriginId");
			if(StringUtil.isNotNull(fdOriginId)) {

				String s_pageno = request.getParameter("pageno");
				//String s_rowsize = request.getParameter("rowsize");
				String orderby = request.getParameter("orderby");
				String ordertype = request.getParameter("ordertype");
				boolean isReserve = false;
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					isReserve = true;
				}
				int pageno = 0;
				int rowsize = 8;
				if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
					pageno = Integer.parseInt(s_pageno);
				}
//				if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
//					rowsize = Integer.parseInt(s_rowsize);
//				}
				// 按多语言字段排序
				if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
					Class<?> modelClass = ((IExtendForm) form).getModelClass();
					if (modelClass != null) {
						String langFieldName = SysLangUtil
								.getLangFieldName(modelClass.getName(), orderby);
						if (StringUtil.isNotNull(langFieldName)) {
							orderby = langFieldName;
						}
					}
				}
				if (isReserve) {
					orderby += " desc";
				}
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setOrderBy(orderby);
				hqlInfo.setPageNo(pageno);
				hqlInfo.setRowSize(rowsize);
				hqlInfo.setWhereBlock("(sysAttMain.fdOriginId = :fdOriginId or sysAttMain.fdId = :fdOriginId) and sysAttMain.fdKey != :fdKey");
				hqlInfo.setParameter("fdOriginId",fdOriginId);
				hqlInfo.setParameter("fdKey",SysAttBase.WPS_CENTER_TEMP_NAME);
				hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
				Page page = getServiceImp(request).findPage(hqlInfo);
				UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
				request.setAttribute("queryPage", page);
			} else {
				return getActionForward("failure", mapping, form, request, response);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listHistory", mapping, form, request, response);
		}
	}

	public ActionForward downloadPluginXml(ActionMapping mapping,
										   ActionForm form,
										   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("downloadPluginXml", true, getClass());
		String xml = getServiceImp(request).downloadPluginXml(
				request.getServletContext().getRealPath("/"));
		response.setContentType("text/xml");
		response.getOutputStream().write(xml.getBytes(StandardCharsets.UTF_8));
		TimeCounter.logCurrentTime("downloadPluginXml", false, getClass());
		return null;
	}

	/**
	 * 判断终端请求类型
	 *
	 * @param request
	 * @return 移动：true PC:false
	 */
	public boolean isMobileRequest(HttpServletRequest request)
	{
		String userAgent = request.getHeader("user-agent");
		if (userAgent == null || StringUtil.isNull(userAgent)) {
			return false;
		}
		if (userAgent.indexOf("Android") != -1) {
			return true;
		} else if (userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 请求WPS时需要的参数
	 * open_mode : ReadOnly    //只读模式
	 *				Normal     //正常模式
	 *				ReadMode  //打开直接进入阅读模式
	 *				EditMode   //打开直接进入编辑模式
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWpsOfficeViewParam(ActionMapping mapping,
											   ActionForm form, HttpServletRequest request,
											   HttpServletResponse response) throws Exception {
		logger.info("政务微信请WPS需要的参数");
		KmssMessages messages = new KmssMessages();
		String fdAttMainId = request.getParameter("fdAttMainId");
		String fdMode = "ReadOnly";
		JSONObject json = new JSONObject();

		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		String downLoadUrl = "";



		if(sysAttMain != null) {
			downLoadUrl = SysAttViewerUtil.getDownloadUrl(sysAttMain);
			if(logger.isDebugEnabled()) {
				logger.info("下载地址:" + downLoadUrl);
			}

		} else {
			return getActionForward("failure", mapping, form, request,
					response);
		}

//		if (!TokenGenerator.isInitialized()) {
//
//			String zdWebContentPath = ConfigLocationsUtil
//					.getKmssConfigPath();
//			if (StringUtil.isNotNull(zdWebContentPath)) {
//				zdWebContentPath = zdWebContentPath + "/LRToken";
//			}
//
//			TokenGenerator.loadFromKeyFile(zdWebContentPath);
//		}
//
//		Token token = TokenGenerator.getInstance()
//				.generateTokenByUserName(
//						UserUtil.getUser().getFdLoginName());
//
//		json.put("wpsoaassistToken", token.getTokenString());

		// 获取token信息
		String token = getSysAttachmentWpsAddinProvider().getToken();
		json.put("wpsoaassistToken", token);

		String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		if(urlPrefix.endsWith("/")) {
			urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
		}
		String uploadUrl = urlPrefix +  "/sys/attachment/uploadByWpsCallback.do?"
				+ "useId=" + UserUtil.getUser().getFdId()
				+"&fdId=" + sysAttMain.getFdId()
				+"&wpsOasisstToken=" + token.toString();

		logger.info("下载文件地址:" + downLoadUrl);
		logger.info("上传文件地址:" + uploadUrl);

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("download_url", downLoadUrl);
			map.put("upload_url", uploadUrl);
			map.put("file_id", sysAttMain.getFdFileId());
			map.put("open_mode", "Normal");
			map.put("other", "");
			map.put("settings", "");
			Map<String, String> resultMap = new HashMap<String, String>();
			json.putAll(map);
			byte[] param = Base64.encodeBase64(json.toString().getBytes());
			resultMap.put("param", new String(param));
			json.clear();
			json.putAll(resultMap);


		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 此方法只支持POST的请求方式，如果使用PUT上传文件，请使用sys/attachment/uploadByWpsCallback.do
	 *
	 * 注：方法暂时不用，但是保留。如果使用请在SysAttachmentValidator的validate权限过滤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadByWpsCallback(ActionMapping mapping,
											 ActionForm form, HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String useId = request.getParameter("useId");
		JSONObject json = new JSONObject();
		logger.info("fdId:" + fdId + "\n  useId:" + useId + "");
		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
			logger.info("startSave");
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("file");
			InputStream is = null;
			try {
				InputStream in = multipartFile.getInputStream();

				SysAttMain sysAttMain = (SysAttMain) getServiceImp(request)
						.findByPrimaryKey(fdId);
				sysAttMain.setInputStream(in);
				getServiceImp(request).update(sysAttMain);
				json.put("flag", "ok");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("flag", "error");
				json.put("message", "保存失败");
			} finally {
				// TODO: handle finally clause
				if (is != null) {
					is.close();
				}
			}
		} else {
			json.put("flag", "error");
			json.put("message", "参数错误");
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward getWpsCloudViewParam(ActionMapping mapping,
											  ActionForm form, HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {

			String fdAttMainId = request.getParameter("fdAttMainId");
			String fdMode = request.getParameter("mode");
			net.sf.json.JSONObject result = SysAttWpsCloudUtil
					.getWpsCloudViewParam(fdAttMainId,fdMode);
			json.putAll(result);


		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}



	/**
	 * 获取附件信息
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ActionForward findAttMains(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysAttMain> sysAttMains = null;
		String fdKey = request.getParameter("fdKey");
		String fdModelName = request.getParameter("fdModelName");
		String modelId = request.getParameter("fdModelId");
		sysAttMains = (getServiceImp(request)).findByModelKey(fdModelName, modelId, fdKey);
		if(sysAttMains != null && sysAttMains.size() > 0)
		{
			SysAttMain sysAttMain = sysAttMains.get(0);
			JSONObject json = new JSONObject();
			json.put("attMainId", sysAttMain.getFdId());
			json.put("fdFileName", sysAttMain.getFdFileName());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
		return null;
	}

	/**
	 * 获取在线预览的工具
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWpsoaassistConfig(ActionMapping mapping,
											  ActionForm form, HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String onlineToolType = SysAttConfigUtil.getOnlineToolType();
			json.put("onlineToolType", onlineToolType);

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	private ActionForward viewWpsPreviewLinux(ActionMapping mapping,
											  ActionForm form, HttpServletRequest request,
											  HttpServletResponse response, SysAttMain attMain) throws Exception {
		String url = "/sys/attachment/sys_att_main/downloadFile.jsp?fdId="
				+ attMain.getFdId() + "&reqType=rest&filename="
				+ URLEncoder.encode(attMain.getFdFileName(), "utf-8");
		String convertId = WpsUtil.upFileToRemote(url,
				attMain.getFdId(), true);
		String previewId = WpsUtil.convertFile(convertId, "");
		String wpsUrl = WpsUtil.configInfo("thirdWpsSetRedUrl");
		if (wpsUrl.endsWith("/")) {
			wpsUrl = wpsUrl.substring(0, wpsUrl.lastIndexOf("/"));
		}

		String attMainId = attMain.getFdId();
		String inner = request.getParameter("inner");
		// 是否可复制
		boolean canCopy = UserUtil.checkAuthentication(
				"/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId="
						+ attMainId,
				"GET");
		// 是否可打印
		boolean canPrint = UserUtil.checkAuthentication(
				"/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId="
						+ attMainId,
				"GET");

		// 是否可下载
		boolean download = UserUtil.checkAuthentication(
				"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
						+ attMainId,
				"GET");

		if ("yes".equals(inner)) {
			download = false;
			canPrint = false;
		}
		String requestUrl = wpsUrl + "/web/reader?file="
				+ URLEncoder.encode(previewId) +
				"&isPrint=" + canPrint + "&isDownload=" + download
				+ "&isCopy=" + canCopy;
		request.setAttribute("wpsofd", "true");
		request.setAttribute("requestUrl", requestUrl);
		return mapping.findForward("viewonline_wps_cloud");
	}

	/**
	 * 检查是否使用在线阅读配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkUseReadOLConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		try {
			JSONObject result = new JSONObject();
			Boolean flag = true;
			String readOLConfig = SysAttConfigUtil.getReadOLConfig();
			if (StringUtils.isEmpty(readOLConfig) || "-1".equals(readOLConfig)) {
				flag = false;
			} else {
				//wps加载项(客户端)+aspose，文件没有转换完成时，使用加载项
				if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
						&& !SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)
						&& "1".equals(SysAttConfigUtil.getReadOLConfig())) {
					String fdAttMainId = request.getParameter("fdId");
					SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdAttMainId);
					if (!SysAttViewerUtil.isPicAtt(sysAttMain)) {
						request.setAttribute("fdAttMainId", fdAttMainId);
						boolean existViewPath = JgWebOffice.isExistViewPath(request);
						if (!existViewPath) {
							flag = false;
						}
					}
				}
			}
			result.put("flag", flag);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}
		return null;
	}

	/**
	 * 内嵌加载项阅读
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewByWpsoAassistEmbed(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		String fdAttMainId = request.getParameter("fdId");
		SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdAttMainId);
		SysAttMainForm sysAttMainForm = new SysAttMainForm();
		BeanUtils.copyProperties(sysAttMainForm, sysAttMain);
		request.setAttribute("sysAttMainForm", sysAttMainForm);
		return mapping.findForward("viewonline_wps_oaassist_linux");
	}

	public ActionForward checkOAassistView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}


			String fdId = request.getParameter("id");

			SysAttMain sysAttMain = null;
			JSONObject result = new JSONObject();
			Boolean flag = true;

			if(SysAttWpsoaassistUtil.isEnable()) {//判断PC端是否是加载项
				flag=true;
			}else {
				if (fdId != null && fdId.trim().length() > 0) {
					sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);

					if(sysAttMain!=null){
						List<SysFileViewerParam> convertedParamsList = SysAttViewerUtil.getConvertedParams(sysAttMain);
						if(!ArrayUtil.isEmpty(convertedParamsList)) {
							flag=false;
						}
					}
				}

				if(SysAttWpsCloudUtil.checkWpsPreviewIsLinux()||SysAttWpsCloudUtil.checkWpsPreviewIsWindows()) {
					flag=false;
				}
			}

			result.put("flag", flag);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}

		return null;
	}
	public ActionForward getPreViewType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		try {
			String fdId = request.getParameter("id");

			JSONObject result = new JSONObject();
			Boolean flag = false;

			if (fdId != null && fdId.trim().length() > 0) {
				SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);
				if(sysAttMain!=null){
					flag=true;
					boolean isMoblie = isMobileRequest(request);
					result.put("viewType", SysAttViewerUtil.getPreView(sysAttMain, isMoblie));

				}
			}
			result.put("flag", flag);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}

		return null;
	}

	//获取在线预览链接
	public ActionForward getWpsPreView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		try {
			String fdId = request.getParameter("fdId");

			JSONObject result = new JSONObject();
			Boolean flag = false;
			String requestUrl="";
			if (fdId != null && fdId.trim().length() > 0) {
				SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdId);
				if(sysAttMain!=null){
					String viewType = null;
					String readOlCOnfig = SysAttConfigUtil.getReadOLConfig();
					if("2".equals(readOlCOnfig)){
						Boolean isWindows=SysAttWpsCloudUtil.checkWpsPreviewIsWindows();//是否windows在线预览
						Boolean isLinux=SysAttWpsCloudUtil.checkWpsPreviewIsLinux();//是否linux在线预览
						if (isLinux) {
							flag=true;
							requestUrl= SysAttWpsCloudUtil.getWpsLinuxPreviewUrl(fdId);
							viewType = SysAttConstant.WPS_LINUX_VIEW;
						}else if(isWindows) {
							flag=true;
							requestUrl= SysAttWpsCloudUtil.getWpsWindowPreviewUrl(fdId);
							viewType = SysAttConstant.WPS_WINDOW_VIEW;
						}
					}else if("4".equals(readOlCOnfig)){
						flag=true;
						requestUrl = getSysAttachmentDianJuProvider().getPreviewUrl(sysAttMain, UserUtil.getKMSSUser().getUserName());
						viewType ="dianju";
					}

					result.put("viewType", viewType);
				}
			}
			result.put("flag", flag);
			result.put("requestUrl", requestUrl);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){

		}

		return null;
	}

	public ActionForward addCenterOnlineFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-addCenterOnlineFile", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String mobileEditAttId = "";
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {

			SysAttMain originalAtt = null;
			originalAtt = getServiceImp(request).addWpsCenterOnlineFile(request);
			if (originalAtt != null) {
				mobileEditAttId = originalAtt.getFdId();
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			json.put("error", e);
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-addCenterOnlineFile", false,
				getClass());
		json.put("editOnlineAttId", mobileEditAttId);
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 *  获取文档中台的预览或者编辑地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWpsCenterViewAndEditUrl(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getWpsCenterViewAndEditUrl", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdAttMainId = request.getParameter("fdAttMainId");
			String fdMode = request.getParameter("fdMode");
			if ("write".equals(fdMode)) { //在线编辑
				net.sf.json.JSONObject result = SysAttWpsCenterUtil.getWpsCenterEditUrl(request);
				result.put("wpsCenterToken",SysAttWpsCenterUtil.getWpsToken());
				json.putAll(result);
			} else if ("read".equals(fdMode)) { //在线预览
				SysAttMain sysAttMain = (SysAttMain) (getServiceImp(request)).findByPrimaryKey(fdAttMainId);
				net.sf.json.JSONObject result;
				if(sysAttMain.getFdModelName()!=null && sysAttMain.getFdModelName().startsWith("com.landray.kmss.kms.cooperate.model")){//文档协同模块预览
					result= SysAttWpsCenterUtil.getWpsCenterViewAndEditUrl(fdAttMainId, fdMode);
				}else{
					result = SysAttWpsCenterUtil.getWpsCenterPreviewUrl(fdAttMainId, fdMode);
				}
				result.put("wpsCenterToken",SysAttWpsCenterUtil.getWpsToken());
				json.putAll(result);
			}

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getWpsCenterViewAndEditUrl", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward getLongToken4WpsCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String token = SysAttWpsCenterUtil.getLongCallBackToken();
			//防止wps刷新token和ekp生成token冲突，让wps缩小刷新间隔
			Long timeout = SysAttWpsCenterUtil.getLongTokenTimeOut() / 3;
			json.put("token", token);
			json.put("timeout", timeout);
			json.put("success", true);
		} catch (Exception e) {
			logger.error("刷新WPS中台LongToken失败", e);
			json.put("success", false);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 此方法是测试清稿套红使用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward testAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getWpsCenterPreviewUrl", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {

			String downloadUrl = "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179ad85bc077a9f0c9ec5e84a0e94f89&Expires=1623585122325&Signature=PCFMGmJ1MZ1DMfCfh7F7oCF11e4=&reqType=rest&filename=template.docx";
			//String fdAttainId = "179a14f1645f0259dac065e4e23966ad";
			ISysAttachmentWpsCenterOfficeProvider provider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
					.getBean("wpsCenterProvider");
			//清稿测试
			//	String taskId = provider.wpsCenterOperateClean(downloadUrl, fdAttainId);

			//套红测试
			List<Map<String, Object>> fileInfos = new ArrayList<Map<String, Object>>();
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			//	fileInfo.put("templateId", "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179ad85bc077a9f0c9ec5e84a0e94f89&Expires=1623585122325&Signature=PCFMGmJ1MZ1DMfCfh7F7oCF11e4=&reqType=rest&filename=template.docx");
			fileInfo.put("templateId", "179ad85bc077a9f0c9ec5e84a0e94f89");
			fileInfo.put("templateName", "template.docx");
			fileInfos.add(fileInfo);

			List<Map<String, Object>> fillDatas = new ArrayList<Map<String, Object>>();
			Map<String, Object> fillData = new HashMap<String, Object>();
			fillData.put("type", "1"); //0-文本  1-文档
			fillData.put("bookmark", "redhead");
			//fillData.put("content", "5555555555566666666666");
			//	fillData.put("sampleId", "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179ad9699164b90f488e36d4d61bae8b&Expires=1623586227537&Signature=lRjSb4rGymkyE5tvgjvOf6K8nSk=&reqType=rest&filename=content.docx");
			fillData.put("sampleId", "179ad9699164b90f488e36d4d61bae8b");
			fillData.put("sampleName", "content.docx");
			fillDatas.add(fillData);
			Map<String, Object> fillData1 = new HashMap<String, Object>();
			fillData1.put("type", "0"); //0-文本  1-文档
			fillData1.put("bookmark", "redhead1");
			fillData1.put("content", "999999999999999955566666666666");
			//fillData1.put("sampleId", "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179a17ecd7c2fb85a24035f4be0b5e5c&Expires=1623383341449&Signature=/uxuM0WbY6HBIUc/4fBD6DmRl/o=&reqType=rest&filename=mock.docx");
			//fillData1.put("sampleName", "mock.docx");
			fillDatas.add(fillData1);

			Map<String, Object> fillData2 = new HashMap<String, Object>();
			fillData2.put("type", "0"); //0-文本  1-文档
			fillData2.put("bookmark", "redhead2");
			fillData2.put("content", "中方20rrrr");
			//fillData1.put("sampleId", "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179a17ecd7c2fb85a24035f4be0b5e5c&Expires=1623383341449&Signature=/uxuM0WbY6HBIUc/4fBD6DmRl/o=&reqType=rest&filename=mock.docx");
			//fillData1.put("sampleName", "mock.docx");
			fillDatas.add(fillData2);


			Map<String, Object> fillData3 = new HashMap<String, Object>();
			fillData3.put("type", "0"); //0-文本  1-文档
			fillData3.put("bookmark", "bookflag");
			fillData3.put("content", "中文件20ppppppp");
			//fillData1.put("sampleId", "http://10.10.3.144:8080/ekp" + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=179a17ecd7c2fb85a24035f4be0b5e5c&Expires=1623383341449&Signature=/uxuM0WbY6HBIUc/4fBD6DmRl/o=&reqType=rest&filename=mock.docx");
			//fillData1.put("sampleName", "mock.docx");
			fillDatas.add(fillData3);
			String taskId = provider.wpsCenterWrapHeader(fileInfos, fillDatas, null, null, null, null, null);

			Thread.sleep(1000);
			String downloadId = provider.queryTask(taskId);
			provider.download(downloadId, "D:\\temp\\0525\\wrap0528-1.docx");
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getWpsCenterPreviewUrl", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward uploadToEkp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!"POST".equals(request.getMethod())) {
			throw new UnexpectedRequestException();
		}
		String fileName = request.getParameter("fileName");
		String fileNameSign = request.getParameter("fdSign");
		if (!isTypeValid(fileName)) {
			response.getWriter().print("基于安全考虑，不允许上传该附件！");
			return null;
		}
		if (!isSignValid(fileName, fileNameSign)) {
			response.getWriter().print("附件签名不符，基于安全考虑，不允许上传该附件！");
			return null;
		}
		if (!checkToken(request.getParameter("token"))) {
			response.getWriter().print("附件上传操作超时，基于安全考虑，不允许上传该附件！");
			return null;
		}
		String key = request.getParameter("key");
		String fileType = request.getParameter("fileType");
		//String fdId = request.getParameter("fdId");

		String fdKey = request.getParameter("fdKey");
		String fdModelId = request.getParameter("fdModelId");
		String fdModelName = request.getParameter("fdModelName");
		String fdFileName = request.getParameter("fileName");
		String fdFileSize = request.getParameter("fileSize");
		String fdFileType = request.getParameter("fileType");
		String fdAttType = request.getParameter("fdAttType");

		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
		jsonObject.put("key", key);
		jsonObject.put("code", request.getParameter("code"));
		jsonObject.put("spaceId", request.getParameter("spaceId"));
		jsonObject.put("fileId", request.getParameter("fileId"));
		jsonObject.put("fileType", fdFileType);
		//jsonObject.put("fdId", request.getParameter("fdId"));

		if (StringUtil.isNotNull(key) && getProviderMap().containsKey(key)) {
			ISysAttachmentTransmissionProvider provider = (ISysAttachmentTransmissionProvider) getProviderMap()
					.get(key);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			String contentType = FileMimeTypeUtil.getContentType(fdFileName);

			//getServiceImp(request).add(sysAttMain);

			jsonObject.put("fdAttId", IDGenerator.generateID());
			jsonObject.put("fileName", fdFileName);
			jsonObject.put("fileSize", fdFileSize);
			jsonObject.put("fdKey", fdKey);
			jsonObject.put("fdAttType", fdAttType);
			jsonObject.put("fdModelId", fdModelId);
			jsonObject.put("fdModelName", fdModelName);
			jsonObject.put("contentType", contentType);
			jsonObject.put("userId", UserUtil.getKMSSUser().getUserId());

			provider.uploadToEkp(jsonObject);
			response.getWriter().write(jsonObject.toString());
			return null;
		} else {
			return null;
		}
	}

	private boolean isTypeValid(String fileName) {
		String _fileType = null;
		if (fileName.indexOf(".") > -1) {
			_fileType = fileName.substring(fileName.lastIndexOf("."));
		}
		if (StringUtil.isNotNull(_fileType)) {
			_fileType = _fileType.toLowerCase();
			String[] files = getDisabledFileType().split("[;；]");
			if("1".equals(getFileLimitType())){
				Boolean isPass = true;
				for(String f : files){
					if(_fileType.equals(f)){
						isPass = false;
						break;
					}
				}
				if(!isPass){
					return false;
				}
			}else if("2".equals(getFileLimitType())){
				Boolean isPass = false;
				for(String f : files){
					if(_fileType.equals(f)){
						isPass = true;
						break;
					}
				}
				if(!isPass){
					return false;
				}
			}
		}
		return true;
	}

	private boolean isSignValid(String fileName, String fileNameSign) throws UnsupportedEncodingException {
		String namesign = new String(Base64.encodeBase64(fileName.getBytes("utf-8")), "UTF-8");
		namesign = namesign.replaceAll("\\+", "");
		namesign = namesign.replaceAll("\\/", "");
		namesign = namesign.replaceAll("\\=", "");
		if (StringUtil.isNull(fileNameSign) || !fileNameSign.equals(namesign)) {
			return false;
		}
		return true;
	}

	private boolean checkToken(String token) throws Exception {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		long curTimestamp = getAttUploadService().getCurTimestamp().getTime();
		String fileInfo = SysAttCryptUtil.decrypt(token);
		String fileMd5 = StringUtil.getParameter(fileInfo, "md5");
		String fileSize = StringUtil.getParameter(fileInfo, "filesize");
		String timestamp = StringUtil.getParameter(fileInfo, "time");
		if (Long.valueOf(timestamp) < curTimestamp - SysAttUploadConstant.SYS_ATT_CONFIG_EXPIRETIME) {
			return false;
		}
		return true;
	}

	public ActionForward delFileRelation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		Boolean flag = false;
		try {
			String fdId = request.getParameter("fdId");
			flag = getServiceImp(request).updateDelRelation(fdId);
			result.put("flag", flag);

		}catch(Exception e){
			result.put("flag", flag);
		}

		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toString());

		return null;
	}

	/**
	 * 查询是否勾选在线预览强制使用 金格 or 加载项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUseWpsLinuxView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			JSONObject result = new JSONObject();
			Boolean flag = false;
			if(SysAttWpsCloudUtil.getUseWpsLinuxView()) {
				flag=true;
			}

			result.put("flag", flag);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getUseWpsLinuxView错误信息", e);
		}
		return null;
	}

	public void dianjuFormat(HttpServletRequest request, SysAttMain sysAttMain) {
          try{
			  String downloadUrl = SysAttViewerUtil.getDownloadUrl(sysAttMain);
			  if(logger.isDebugEnabled()) {
				  logger.info("下载地址:" + downloadUrl);
			  }

			  request.setAttribute("downloadUrl",downloadUrl);
			  request.setAttribute("attMainId",sysAttMain.getFdId());
			  request.setAttribute("attMainFileName",sysAttMain.getFdFileName());
		  } catch (Exception e) {
          	 logger.error("点聚版式阅读异常：{}", e);
		  }

		//	return mapping.findForward("viewonline_dianju_format");
	}



	/**
	 * 获取附件查看地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAttMainViewUrl(ActionMapping mapping,
											   ActionForm form, HttpServletRequest request,
											   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdAttMainId = request.getParameter("fdAttMainId");
	//	fdAttMainId = "17b9bb135219528274875be4803b880e";
		JSONObject json = new JSONObject();

		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		String downLoadUrl = "";

		if(sysAttMain != null) {
			String downloadUrl = SysAttViewerUtil.getDownloadUrl(sysAttMain);
			if(logger.isDebugEnabled()) {
				logger.info("下载地址:" + downloadUrl);
			}
		} else {
			return getActionForward("failure", mapping, form, request,
					response);
		}

		try {
			json.put("downloadUrl", downLoadUrl);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());

		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	private  Boolean isWord(String filename){
		Boolean mark=false;
		String ext=FilenameUtils.getExtension(filename);
		if("doc".equals(ext.toLowerCase())||"docx".equals(ext.toLowerCase())||"wps".equals(ext.toLowerCase())){
			mark=true;
		}
		return mark;
	}

	private  Boolean isExcel(String filename){
		Boolean mark=false;
		String ext=FilenameUtils.getExtension(filename);
		if("xls".equals(ext.toLowerCase())||"xlsx".equals(ext.toLowerCase())||"et".equals(ext.toLowerCase())){
			mark=true;
		}
		return mark;
	}

	private  Boolean isPpt(String filename){
		Boolean mark=false;
		String ext=FilenameUtils.getExtension(filename);
		if("ppt".equals(ext.toLowerCase())||"pptx".equals(ext.toLowerCase())||"dps".equals(ext.toLowerCase())){
			mark=true;
		}
		return mark;
	}

	/**
	 * 钉钉唤起WPS
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getParamForDing(ActionMapping mapping,
											   ActionForm form, HttpServletRequest request,
											   HttpServletResponse response) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("钉钉唤起WPS");
		}
		KmssMessages messages = new KmssMessages();
		String fdAttMainId = request.getParameter("fdAttMainId");
		JSONObject json = new JSONObject();
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);

		if(sysAttMain == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("钉钉唤起WSP失败：附件为空！");
			}
			return getActionForward("failure", mapping, form, request,
					response);
		}

		String downloadUrl = SysAttViewerUtil.getDownloadUrl(sysAttMain);

		String uploadUri = SysAttViewerUtil.getUploadUrl(sysAttMain);
		String wpsSerNum = WpsUtil.configInfo("wpsSerNum");

		if(logger.isDebugEnabled()) {
			logger.debug("钉钉唤起WPS信息：下载地址:{},上传文件地址:{},fileId:{},fileName:{}, wpsSerNum:{}",
					downloadUrl, uploadUri, sysAttMain.getFdId(), sysAttMain.getFdFileName(),wpsSerNum);
		}

		try {
			json.put("downloadUri", downloadUrl);
			json.put("fileId", sysAttMain.getFdId());
			json.put("fileName", sysAttMain.getFdFileName());
			json.put("wpsSerNum", wpsSerNum);
			json.put("uploadUri", uploadUri);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		if(logger.isDebugEnabled()) {
			logger.debug("钉钉唤起WPS信息JSON：{}", json.toString());
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 获取签名下载地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getSignDownload(ActionMapping mapping,
								  ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		String fdAttMainId = request.getParameter("fdAttMainId");
		JSONObject json = new JSONObject();

		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		String downLoadUrl = "";
		String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");

		if(urlPrefix.endsWith("/"))
		{
			urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
		}

		logger.info("系统地址:" + urlPrefix);

		if(sysAttMain != null)
		{
			long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效

			String sign = SysAttUtil.getRestSign(sysAttMain.getFdId(), expires);

			if(StringUtil.isNotNull(sign)) {
				downLoadUrl = urlPrefix + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + sysAttMain.getFdId() + "&Expires="
						+ expires + "&Signature=" + sign + "&reqType=rest&filename=" +URLEncoder.encode(sysAttMain.getFdFileName(), "utf-8");
				json.put("hasAtt", true);
				json.put("hasRest", true);
				json.put("downloadUrl", downLoadUrl);
			}else {
				json.put("hasAtt", true);
				json.put("hasRest", false);
			}

		}else {
			json.put("hasAtt", false);
		}

		TimeCounter.logCurrentTime("Action-getSignDownload", false, getClass());
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();

		return null;
	}

}