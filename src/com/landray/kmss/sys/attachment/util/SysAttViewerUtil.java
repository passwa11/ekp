package com.landray.kmss.sys.attachment.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.attachment.forms.SysAttWaterMarkForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.authentication.token.Token;
import com.landray.kmss.sys.authentication.token.TokenGenerator;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileViewerParam;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil.FileConverter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserUpdateDetailOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

@SuppressWarnings("unused")
public class SysAttViewerUtil {
	
	// 默认图片
	public static final String unConvertedUrl =
			"/sys/attachment/viewer/resource/common/images/unConverted.png";

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttViewerUtil.class);
	private static ISysFileConvertDataService convertDataService = (ISysFileConvertDataService) SpringBeanUtil
			.getBean("sysFileConvertDataService");

	private static Map<String, FileViewer> extensionViewers;

	private static Map<String, FileViewer> getAllViewers() {
		if (extensionViewers == null) {
			extensionViewers = new HashMap<String, FileViewer>();
			IExtension[] allViewers = Plugin.getExtensions("com.landray.kmss.sys.filestore", "*", "viewer");
			if (allViewers != null && allViewers.length > 0) {
				FileViewer fileViewer = null;
				for (IExtension viewer : allViewers) {
					fileViewer = new FileViewer();
					fileViewer.setViewerKey(Plugin.getParamValue(viewer, "viewerKey").toString());
					fileViewer.setViewerPath(Plugin.getParamValue(viewer, "viewerPath").toString());
					String[] extName = getSupportedExtNames(viewer);
					fileViewer.setExtName(extName);
					fileViewer.setOrder(Integer.valueOf(Plugin.getParamValue(viewer, "order").toString()));
					extensionViewers.put(fileViewer.getViewerKey(), fileViewer);
				}
			}
		}
		return extensionViewers;
	}

	private static String[] getSupportedExtNames(IExtension viewer) {
		Object[] values = Plugin.getParamValues(viewer, "extName").toArray();
		String[] result = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i].toString();
		}
		return result;
	}

	public static String getExtName(String fdFileName) {
		return StringUtil.isNotNull(fdFileName) ? fdFileName.substring(fdFileName.lastIndexOf(".") + 1) : "";
	}

	public static String getFileViewerKey(SysAttMain sysAttMain) throws Exception {
		String  viewerKey = "";
		boolean oldConvertSuccessUseHTMLView = SysFileStoreUtil.isOldConvertSuccessUseHTML();
		List<FileConverter> fileConverters = SysFileStoreUtil.getFileConverters(
				SysAttViewerUtil.getExtName(sysAttMain.getFdFileName()), sysAttMain.getFdModelName(),
				oldConvertSuccessUseHTMLView);
		List<SysFileViewerParam> convertedParamsList = getConvertedParams(sysAttMain);
		Map<String, FileViewer> allViewers = getAllViewers();
		FileViewer  fv = getViewer(convertedParamsList, "", allViewers, fileConverters);
		if (fv != null && StringUtil.isNotNull(fv.getConverterKey())) {
			viewerKey = fv.getConverterKey();
		}
		return viewerKey;
	}

	/**
	 * 获取阅读器路径以及阅读器参数
	 * 
	 * @param attMain
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getViewerPath(SysAttMain attMain, List<SysFileViewerParam> convertedParamsList,
			List<FileConverter> fileConverters, HttpServletRequest request) throws Exception {
		String viewerPath = "";
		String viewerKey = request.getParameter("viewer");
		Map<String, FileViewer> allViewers = getAllViewers();
		logger.debug("viewer:" + viewerKey);
		FileViewer fv = getViewer(convertedParamsList, viewerKey, allViewers, fileConverters);
		if (fv != null) {
			viewerPath = fv.getViewerPath();
			String viewerParam = fv.getViewerParam();
			request.setAttribute("viewerParam", viewerParam);
			logger.debug("viewerParam:" + viewerParam);
			request.setAttribute("converterKey", fv.getConverterKey());
			logger.debug("converterKey:" + fv.getConverterKey());
			String fileFullName = attMain.getFdFileName();
			request.setAttribute("fileFullName", fileFullName);
			String fileName = "";
			if (fileFullName.length() > 12) {
				fileName = fileFullName.substring(0, 12) + "...";
			} else {
				fileName = fileFullName;
			}
			request.setAttribute("fileName", fileName);
			request.setAttribute("fileSize", convertFileSize(attMain.getFdSize()));
			if ("toHTML".equals(fv.getConverterKey().split("-")[0])) {
				String viewerStyle = "";
				String fileExtName = "";
				int htmlPageCount = 0;
				int picPageCount = 0;
				try {
					JSONObject viewerParamJson = JSONObject.fromObject(viewerParam);
					viewerStyle = viewerParamJson.getString("viewerStyle");
					fileExtName = viewerParamJson.getString("fileExtName");
					if (viewerParamJson.containsKey("htmlPageCount")) {
						htmlPageCount = viewerParamJson.getInt("htmlPageCount");
					}
					if (viewerParamJson.containsKey("picPageCount")) {
						picPageCount = viewerParamJson.getInt("picPageCount");
					}
					if (htmlPageCount == 0 && viewerParamJson.containsKey("totalPageCount")) {
						htmlPageCount = viewerParamJson.getInt("totalPageCount");
					}
					if (picPageCount == 0 && viewerParamJson.containsKey("totalPageCount")) {
						picPageCount = viewerParamJson.getInt("totalPageCount");
					}
				} catch (Exception e) {
					String[] paramArray = viewerParam.split(",");
					for (int i = 0; i < paramArray.length; i++) {
						String[] param = paramArray[i].split(":");
						if ("viewerStyle".equals(param[0])) {
							viewerStyle = param[1];
						}
						if ("totalPageCount".equals(param[0])) {
							htmlPageCount = Integer.valueOf(param[1]).intValue();
							picPageCount = Integer.valueOf(param[1]).intValue();
						}
					}
				}
				if("et".equals(fileExtName)) {
					viewerStyle += fileExtName;
				}

				logger.debug("viewerStyle:" + viewerStyle);
				logger.debug("htmlPageCount:" + htmlPageCount);
				logger.debug("picPageCount:" + picPageCount);
				request.setAttribute("viewerStyle", viewerStyle);
				request.setAttribute("htmlPageCount", htmlPageCount);
				request.setAttribute("picPageCount", picPageCount);
				request.setAttribute("highFidelity", fv.getFdHighFidelity());
				JSONObject waterMarkInDB = getWaterMarkConfigInDB(true);
				waterMarkInDB.accumulate("otherInfos", getWaterMarkInfos(waterMarkInDB, request));
				request.setAttribute("waterMarkConfig", waterMarkInDB.toString());
			}
		}
		return viewerPath;
	}

	public static String getViewerPath(SysAttMain attMain, HttpServletRequest request) throws Exception {
		List<SysFileViewerParam> convertedParamsList = getConvertedParams(attMain);
		List<FileConverter> fileConverters = SysFileStoreUtil.getFileConverters(getExtName(attMain.getFdFileName()),
				attMain.getFdModelName(), SysFileStoreUtil.isOldConvertSuccessUseHTML());
		return getViewerPath(attMain, convertedParamsList, fileConverters, request);
	}

	public static String convertFileSize(Double size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String filesize = "";
		if (size < 1024) {
			filesize = df.format(size) + "B";
		} else if (size < 1048576) {
			filesize = df.format(size / 1024) + "KB";
		} else if (size < 1073741824) {
			filesize = df.format(size / 1048576) + "MB";
		} else {
			filesize = df.format(size / 1073741824) + "GB";
		}
		return filesize;
	}

	private static FileViewer getViewer(List<SysFileViewerParam> convertedParamsList, String viewerKey,
			Map<String, FileViewer> allViewers, List<FileConverter> fileConverters) {
		FileViewer resultViewer = null;
		FileConverter[] convertersArray = new FileConverter[fileConverters.size()];
		fileConverters.toArray(convertersArray);
		if (convertedParamsList != null && convertedParamsList.size() > 0) {
			if (StringUtil.isNotNull(viewerKey)) {
				if (allViewers.containsKey(viewerKey)) {
					resultViewer = allViewers.get(viewerKey);
					for (SysFileViewerParam item : convertedParamsList) {
						String itemViewerKey = new String(item.getFdViewerKey());
						if (viewerKey.toLowerCase().equals(itemViewerKey.toLowerCase())) {
							resultViewer.setConverterKey(item.getFdConverterKey());
							if(StringUtil.isNotNull(item.getFdParameterLong())) {
								resultViewer.setViewerParam(item.getFdParameterLong());
							} else {
								resultViewer.setViewerParam(item.getFdParameter());
							}
							resultViewer.setFdHighFidelity(getHighFidelity(item, convertersArray));
							break;
						}
					}
				}
			} else {
				if (fileConverters.size() > 0) {
					Collections.sort(fileConverters, new Comparator<FileConverter>() {
						@Override
						public int compare(FileConverter o1, FileConverter o2) {
							int o1_len = o1.getModelName().length();
							int o2_len = o2.getModelName().length();
							return (o1_len > o2_len) ? -1 : (o1_len < o2_len) ? 1 : 0;
						}

					});
					for (int i = 0; i < fileConverters.size(); i++) {
						FileConverter fileConverter = fileConverters.get(i);
						for (SysFileViewerParam itemParam : convertedParamsList) {
							if (itemParam.getFdViewerKey().toLowerCase().contains("mobile")) {
								continue;
							}
							String itemViewerKey = new String(itemParam.getFdViewerKey());
							String converterKey = new String(itemParam.getFdConverterKey());
							String filConvertKey = fileConverter.getConverterKey();
							if("toJPG".equals(filConvertKey))
							{
								filConvertKey = "toHTML";
							}

							// #157070 dwg文件ConvertKey改为为cadToImg
							String extendName = fileConverter.getExtName();
							String convertType = fileConverter.getConverterType();
							if("dwg".equalsIgnoreCase(extendName) && "aspose".equalsIgnoreCase(convertType)) {
								filConvertKey = "cadToImg";
							}
							if (converterKey.split("-")[0].equals(filConvertKey) || isVideoToMp4(converterKey.split("-")[0],filConvertKey)) {
								if (allViewers.containsKey(itemViewerKey)) {
									resultViewer = allViewers.get(itemViewerKey);
									resultViewer.setConverterKey(itemParam.getFdConverterKey());
									if(StringUtil.isNotNull(itemParam.getFdParameterLong())) {
										resultViewer.setViewerParam(itemParam.getFdParameterLong());
									} else {
										resultViewer.setViewerParam(itemParam.getFdParameter());
									}
									resultViewer.setFdHighFidelity(getHighFidelity(itemParam, fileConverter));
									break;
								}
							}
						}
					}
				}
			}
		}
		return resultViewer;
	}

	private static boolean isVideoToMp4(String string, String converterKey) {
		if("videoToMp4".equals(string) && "videoToFlv".equals(converterKey)) {
			return true;
		}
		return false;
	}

	private static String getHighFidelity(SysFileViewerParam item, FileConverter... fileConverters) {
		String resultHighFidelity = "";
		if ("toHTML".equals(item.getFdConverterKey().split("-")[0])) {
			JSONObject jsonParam = null;
			if(StringUtil.isNotNull(item.getFdParameterLong())) {
				jsonParam = JSONObject.fromObject(item.getFdParameterLong());
			} else {
				jsonParam = JSONObject.fromObject(item.getFdParameter());
			}
			
			String extName = null;
			if (jsonParam.containsKey("fileExtName")) {
				extName = jsonParam.getString("fileExtName");
			}
			String highFidelity_html = "0";
			if (jsonParam.containsKey("highFidelity_html")) {
				highFidelity_html = "1";
			}
			String highFidelity_pic = "0";
			if (jsonParam.containsKey("highFidelity_pic")) {
				highFidelity_pic = "1";
			}
			if ("1".equals(highFidelity_html)) {
				resultHighFidelity += "html";
			}
			if ("1".equals(highFidelity_pic)) {
				resultHighFidelity += "pic";
			}
		}
		return resultHighFidelity;
	}

	@SuppressWarnings("unchecked")
	public static List<SysFileViewerParam> getConvertedParams(SysAttMain attMain) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "";
		if (StringUtil.isNotNull(attMain.getFdFileId())) {
			whereBlock += " fdFileId=:fdFileId ";
			hqlInfo.setParameter("fdFileId", attMain.getFdFileId());
		} else {
			whereBlock += " fdAttMainId=:fdAttMainId ";
			hqlInfo.setParameter("fdAttMainId", attMain.getFdId());
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setGetCount(false);
		return convertDataService.getViewerParamService().findList(hqlInfo);
	}
	
	/**
	 * 获取转换后的文件路径
	 * @param attMain
	 * @param convertFileName
	 * @return
	 * @throws Exception
	 */
	public static String getConvertFilePath(SysAttMain attMain,
			String convertFileName) throws Exception {

		if (convertFileName.contains("/") || convertFileName.contains("\\")
				|| convertFileName.contains("..")) {
			return null;
		}
		
		String attFilePath = getAttFilePath(attMain);

		String convertFile = "";
		convertFile = attFilePath + "_convert" + "/" + convertFileName;
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile file = sysAttUploadService.getFileById(attMain.getFdFileId());
		ISysFileLocationProxyService proxyService =
				SysFileLocationUtil.getProxyService(file.getFdAttLocation());
		String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
		if (proxyService.doesFileExist(convertFile, pathPrefix)) {
			return convertFile;
		}

		return attFilePath;

	}

	/**
	 * 获取转换后的文件流<br>
	 * 如果没转换则直接拿源文件
	 * 
	 * @param attMain
	 * @param convertFileName
	 * @return
	 * @throws Exception
	 */
	public static InputStream getConvertFileInputStream(SysAttMain attMain,
			String convertFileName) throws Exception {
		
		String convertFile =
				getConvertFilePath(attMain, convertFileName);
		
		if (StringUtil.isNull(convertFile)) {
			return null;
		}
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(attMain.getFdFileId());
		ISysFileLocationProxyService sysFileLocationService =
				SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return sysFileLocationService.readFile(convertFile,pathPrefix);
	}
	
	/**
	 * 获取文件封面路径
	 * 
	 * @param attMain
	 * @param convertedParamsList
	 * @param fileConverters
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getFileThumbPath(SysAttMain attMain,
			List<SysFileViewerParam> convertedParamsList,
			List<FileConverter> fileConverters, HttpServletRequest request)
			throws Exception {

		String thumbNailFile = "";
		String attFilePath = getAttFilePath(attMain);
		String viewerPath = getViewerPath(attMain, convertedParamsList,
				fileConverters, request);

		if (StringUtil.isNull(viewerPath)) {
			return null;
		} else {
			thumbNailFile = attFilePath + "_convert/"
					+ request.getAttribute("converterKey") + "_thumbnail";
		}
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile file = sysAttUploadService.getFileById(attMain.getFdFileId());
		String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
		if (SysFileLocationUtil.getProxyService(file.getFdAttLocation())
				.doesFileExist(thumbNailFile,pathPrefix)) {
			return thumbNailFile;
		} else if (SysAttUtil.isOfficeType(
				FilenameUtils.getExtension(attMain.getFdFileName()))) {
			return null;
		}

		return attFilePath;

	}

	public static InputStream getFileThumbInputStream(SysAttMain attMain,
			List<SysFileViewerParam> convertedParamsList,
			List<FileConverter> fileConverters, HttpServletRequest request)
			throws Exception {

		String path = getFileThumbPath(attMain, convertedParamsList,
				fileConverters, request);
		
		if (StringUtil.isNull(path)) {
			return new FileInputStream(
					PluginConfigLocationsUtil.getWebContentPath()
							+ unConvertedUrl);
		}
		
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(attMain.getFdFileId());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFile(path,pathPrefix);
	}
	
	public static String getAttFilePath(SysAttBase att) throws Exception {
		return convertDataService.getFilePath(att.getFdFileId(), att.getFdId());
	}

	public static String getAttFileFullPath(SysAttBase att) throws Exception {
		return convertDataService.getFilePath(att.getFdFileId(), att.getFdId(), true);
	}

	private static class FileViewer {
		private String fileId;
		private String converterKey;
		private String viewerKey;
		private String viewerPath;
		private String[] extName;
		private Integer order;
		private String viewerParam;
		private String fdHighFidelity;

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

		public String getConverterKey() {
			return converterKey;
		}

		public void setConverterKey(String converterKey) {
			this.converterKey = converterKey;
		}

		public String getViewerKey() {
			return viewerKey;
		}

		public void setViewerKey(String viewerKey) {
			this.viewerKey = viewerKey;
		}

		public String getViewerPath() {
			return viewerPath;
		}

		public void setViewerPath(String viewerPath) {
			this.viewerPath = viewerPath;
		}

		public String[] getExtName() {
			return extName;
		}

		public void setExtName(String[] extName) {
			this.extName = extName;
		}

		public Integer getOrder() {
			return order;
		}

		public void setOrder(Integer order) {
			this.order = order;
		}

		public String getViewerParam() {
			return viewerParam;
		}

		public void setViewerParam(String viewerParam) {
			this.viewerParam = viewerParam;
		}

		public String getFdHighFidelity() {
			return fdHighFidelity;
		}

		public void setFdHighFidelity(String fdHighFidelity) {
			this.fdHighFidelity = fdHighFidelity;
		}

	}

	/**
	 * 获取图片缩略图链接
	 * @param picThumb
	 * @return
	 * @throws Exception
	 */
	public static String getPicThumbPath(SysAttBase sysAttBase, String picThumb)
			throws Exception {
		String fileFullPath = "";
		String attFileFullPath = getAttFilePath(sysAttBase);
		String thumbNailFile = null;
		fileFullPath = attFileFullPath;
		if ("big".equals(picThumb)) {
			fileFullPath = fileFullPath + "_convert/image2thumbnail_"
					+ SysFileStoreUtil.getBigImageWidth();
		}
		if ("small".equals(picThumb)) {
			fileFullPath = fileFullPath + "_convert/image2thumbnail_"
					+ SysFileStoreUtil.getSmallImageWidth();
		}
		ISysAttUploadService sysAttUploadService =
				(ISysAttUploadService) SpringBeanUtil
						.getBean("sysAttUploadService");
		SysAttFile file =
				sysAttUploadService.getFileById(sysAttBase.getFdFileId());
		ISysFileLocationProxyService sysFileLocationService =
				SysFileLocationUtil.getProxyService(file.getFdAttLocation());
		String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
		if (StringUtil.isNull(picThumb) || "original".equals(picThumb)
				|| (!sysFileLocationService.doesFileExist(fileFullPath,pathPrefix))) {

			return attFileFullPath;
		}

		return fileFullPath;
	}

	public static InputStream getPicThumbStream(SysAttMain attMain,
			HttpServletRequest request) throws Exception {
		String picThumb = request.getParameter("picthumb");
		String path = getPicThumbPath(attMain, picThumb);
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(attMain.getFdFileId());
		ISysFileLocationProxyService sysFileLocationService =
				SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return sysFileLocationService.readFile(path,pathPrefix);
	}
	
	public static InputStream getFileInputStream(SysAttMain attMain) throws Exception {
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(attMain.getFdFileId());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return SysFileLocationUtil.getProxyService(attFile.getFdAttLocation())
				.readFile(getAttFilePath(attMain),pathPrefix);
	}

	public static boolean isLowerThanIE8(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if (userAgent.indexOf("msie") > 0) {
			return getIEVersion(userAgent) < 5;
		}
		return false;
	}

	private static int getIEVersion(String userAgent) {
		String trident = userAgent.substring(userAgent.indexOf("trident") + 8, userAgent.indexOf("trident") + 9);
		try {
			return Integer.valueOf(StringUtil.isNotNull(trident) ? trident : "0").intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean isConverted(SysAttMain attMain) throws Exception {
		List<SysFileViewerParam> convertedParams = getConvertedParams(attMain);
		List<FileConverter> fileConverters = SysFileStoreUtil.getFileConverters(getExtName(attMain.getFdFileName()),
				attMain.getFdModelName(), SysFileStoreUtil.isOldConvertSuccessUseHTML());
		return (convertedParams != null && convertedParams.size() > 0)
				&& (fileConverters != null && fileConverters.size() > 0);
	}

	public static void addQueue(SysAttMain sysAttMain) throws Exception {
		ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) SpringBeanUtil
				.getBean("sysFileConvertQueueService");
		queueService.addQueue(sysAttMain.getFdFileId(), sysAttMain.getFdFileName(), sysAttMain.getFdModelName(),
				sysAttMain.getFdModelId(), null, sysAttMain.getFdId());
	}

	@SuppressWarnings("unchecked")
	private static String findAppConfigValue(String whereBlock, String hintValue) throws Exception {
		List<String> valueList = convertDataService.getAppConfigService().findValue("fdValue", whereBlock, "");
		if (valueList == null || valueList.size() == 0) {
			return hintValue;
		}
		return StringUtil.isNull(valueList.get(0)) ? hintValue : valueList.get(0);
	}

	private static String getWaterMarkGlobalValue(String fdKey, String defaultValue) {
		try {
			return findAppConfigValue("fdKey='" + fdKey + "'", defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	private static void setWaterMarkGlobalValue(String fdKey, String fdValue) throws Exception {
		SysAppConfig appConfig = new SysAppConfig();
		appConfig.setFdKey(fdKey);
		appConfig.setFdValue(fdValue);
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("fdId");
		info.setWhereBlock("fdKey=:fdKey");
		info.setParameter("fdKey", fdKey);
		List<String> fdIdList = convertDataService.getAppConfigService().findValue(info);
		if (fdIdList == null || fdIdList.size() == 0) {
			convertDataService.getAppConfigService().add(appConfig);
		} else {
			appConfig.setFdId(fdIdList.get(0));
			convertDataService.getAppConfigService().update(appConfig);
		}
	}

	public static InputStream getWaterMarkPNG(JSONObject waterMarkConfig) throws Exception {
		InputStream resultMarkPNG = null;
		if (waterMarkConfig.has("showWaterMark")) {
			String showWaterMark = waterMarkConfig.getString("showWaterMark");
			if ("true".equals(showWaterMark)) {
				String waterMarkType = waterMarkConfig.getString("markType");
				if ("word".equals(waterMarkType)) {
					//
				}
				if ("pic".equals(waterMarkType)) {
					String markPicFileName = waterMarkConfig.getString("markPicFileName");
					resultMarkPNG = getPicFileInputStream(markPicFileName);
				}
			}
		} else {
			return getWaterMarkPNG(getWaterMarkConfigInDB(true));
		}
		return resultMarkPNG;
	}

	private static InputStream getPicFileInputStream(String markPicFileName) throws Exception {
		String picFile = getWaterMarkPicFileDir() + "/" + markPicFileName;
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileByPath(picFile);
		ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil
				.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		if (sysFileLocationService.doesFileExist(picFile,pathPrefix)) {
			return sysFileLocationService.readFile(picFile,pathPrefix);
		} else {
			return null;
		}
	}

	private static InputStream getWordMarkPNG(String markWord, String fontFamily, String fontColor, String fontSize,
			String rotateType, String rotateAngel) {
		int angel = 0;
		if ("declining".equals(rotateType)) {
			angel = Integer.parseInt(rotateAngel);
		}
		Font font = new Font(fontFamily, Font.PLAIN, Integer.parseInt(fontSize));
		FontMetrics fm = FontDesignMetrics.getMetrics(font);
		int strWidth = fm.stringWidth(markWord);
		int strHeight = fm.getHeight();
		int imgWidth = strWidth;
		int imgHeight = strHeight;
		if ("declining".equals(rotateType)) {
			Rectangle oldTangle = new Rectangle(new Dimension(strWidth, strHeight));
			Rectangle newTangle = calcRotatedSize(oldTangle, angel);
			imgWidth = newTangle.width;
			imgHeight = newTangle.height;
		}
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int y = (imgHeight - (ascent + descent)) / 2 + ascent;// 保证文字居中
		int x = (imgWidth - strWidth) / 2;
		BufferedImage srcImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = srcImg.createGraphics();
		srcImg = graphics.getDeviceConfiguration().createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
		graphics.dispose();
		graphics = srcImg.createGraphics();
		graphics.setStroke(new BasicStroke(1));
		graphics.setColor(stringToColor(fontColor));
		graphics.setFont(font);
		if ("declining".equals(rotateType)) {
			graphics.translate((imgWidth - strWidth) / 2, (imgHeight - strHeight) / 2);
			graphics.rotate(Math.toRadians(angel), strWidth / 2, strHeight / 2);
			graphics.drawString(markWord, 0, y / 2);// 保证文字居中
		} else {
			graphics.drawString(markWord, x, y);// 保证文字居中
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		graphics.dispose();
		try {
			ImageIO.write(srcImg, "png", bos);
		} catch (Throwable e) {
			logger.info("error", e);
		}
		return new ByteArrayInputStream(bos.toByteArray());
	}

	private static Rectangle calcRotatedSize(Rectangle src, int angel) {
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}
		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);
		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

	public static JSONObject getWaterMarkConfigInDB(Boolean check) {
		JSONObject resultConfig = new JSONObject();
		String enable = getWaterMarkGlobalValue("watermark.enable", "false");
		boolean isAvailable = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases().contains("ROLE_SYSATTACHMENT_REMOVE_WATERMARK");

		if(check && isAvailable){
			enable = "false";
		}
		resultConfig.accumulate("showWaterMark", enable);
		resultConfig.accumulate("markOpacity", getWaterMarkGlobalValue("watermark.opacity", "0.5"));
		resultConfig.accumulate("markType", getWaterMarkGlobalValue("watermark.type", "word"));
		resultConfig.accumulate("markWordVar", getWaterMarkGlobalValue("watermark.word.var", ""));
		resultConfig.accumulate("markWordFontFamily", getWaterMarkGlobalValue("watermark.word.fontfamily", ""));
		resultConfig.accumulate("markWordFontColor", getWaterMarkGlobalValue("watermark.word.fontcolor", "#cccccc"));
		resultConfig.accumulate("markWordFontSize", getWaterMarkGlobalValue("watermark.word.fontsize", "36"));
		resultConfig.accumulate("markRowSpace", getWaterMarkGlobalValue("watermark.rowspace", "50"));
		resultConfig.accumulate("markColSpace", getWaterMarkGlobalValue("watermark.colspace", "30"));
		resultConfig.accumulate("markRotateType", getWaterMarkGlobalValue("watermark.rotatetype", "declining"));
		resultConfig.accumulate("markRotateAngel", getWaterMarkGlobalValue("watermark.rotateangel", "330"));
		resultConfig.accumulate("markPicFileName", getWaterMarkGlobalValue("watermark.pic.filename", ""));
		return resultConfig;
	}

	public static JSONObject getWaterMarkConfigInRequest(HttpServletRequest request, String requestType) {
		JSONObject resultConfig = new JSONObject();
		String waterMarkEnable = request.getParameter("showWaterMark");
		if (StringUtil.isNotNull(waterMarkEnable) && "true".equals(waterMarkEnable)) {
			resultConfig.accumulate("showWaterMark", waterMarkEnable);
			String markType = request.getParameter("markType");
			resultConfig.accumulate("markType", markType);
			resultConfig.accumulate("markWordVar", request.getParameter("markWordVar"));
			resultConfig.accumulate("markWordFontFamily", request.getParameter("markWordFontFamily"));
			resultConfig.accumulate("markWordFontColor", request.getParameter("markWordFontColor"));
			resultConfig.accumulate("markWordFontSize", request.getParameter("markWordFontSize"));
			resultConfig.accumulate("markPicFileName", request.getParameter("markPicFileName"));
			resultConfig.accumulate("markOpacity", request.getParameter("markOpacity"));
			resultConfig.accumulate("markRowSpace", request.getParameter("markRowSpace"));
			resultConfig.accumulate("markColSpace", request.getParameter("markColSpace"));
			String rotateType = request.getParameter("markRotateType");
			resultConfig.accumulate("markRotateType", rotateType);
			resultConfig.accumulate("markRotateAngel", request.getParameter("markRotateAngel"));
		} else if (StringUtil.isNotNull(waterMarkEnable)) {
			resultConfig.accumulate("showWaterMark", "false");
		}
		return resultConfig;
	}

	private static Color stringToColor(String str) {
		int i = Integer.parseInt(str.substring(1), 16);
		return new Color(i);
	}

	public static void saveWaterMarkConfig(SysAttWaterMarkForm waterMarkForm) throws Exception {
		String showWaterMark = waterMarkForm.getShowWaterMark();
		if (StringUtil.isNotNull(showWaterMark) && ("true".equals(showWaterMark) || "on".equals(showWaterMark))) {
			showWaterMark = "true";
		} else {
			showWaterMark = "false";
		}
		// 日志记录
		IUserUpdateDetailOper detailOper = null;
		if (UserOperHelper.allowLogOper("saveWaterMark", null)) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("sys-attachment:attachment.watermark.config"));
			detailOper = UserOperContentHelper.putUpdate(SysAppConfig.class.getName(), null, null)
					.createOper4Detail("dataMap");
			detailOper.putUpdate("WaterMarkConfig", "").putSimple("watermark.enable", "", showWaterMark)
					.putSimple("watermark.opacity", "", waterMarkForm.getMarkOpacity())
					.putSimple("watermark.type", "", waterMarkForm.getMarkType())
					.putSimple("watermark.rotatetype", "", waterMarkForm.getMarkRotateType())
					.putSimple("watermark.rowspace", "", waterMarkForm.getMarkRowSpace())
					.putSimple("watermark.colspace", "", waterMarkForm.getMarkColSpace())
					.putSimple("watermark.rotateangel", "", waterMarkForm.getMarkRotateAngel())
					.putSimple("watermark.word.var", "", waterMarkForm.getMarkWordVar())
					.putSimple("watermark.word.fontfamily", "", waterMarkForm.getMarkWordFontFamily())
					.putSimple("watermark.word.fontcolor", "", waterMarkForm.getMarkWordFontColor())
					.putSimple("watermark.word.fontsize", "", waterMarkForm.getMarkWordFontSize())
					.putSimple("watermark.pic.filename", "", waterMarkForm.getMarkPicFileName());
		}

		setWaterMarkGlobalValue("watermark.enable", showWaterMark);
		if ("true".equals(showWaterMark)) {
			setWaterMarkGlobalValue("watermark.opacity", waterMarkForm.getMarkOpacity());
			String markType = waterMarkForm.getMarkType();
			setWaterMarkGlobalValue("watermark.type", markType);
			String rotateType = waterMarkForm.getMarkRotateType();
			setWaterMarkGlobalValue("watermark.rotatetype", rotateType);
			setWaterMarkGlobalValue("watermark.rowspace", waterMarkForm.getMarkRowSpace());
			setWaterMarkGlobalValue("watermark.colspace", waterMarkForm.getMarkColSpace());
			if ("declining".equals(rotateType)) {
				setWaterMarkGlobalValue("watermark.rotateangel", waterMarkForm.getMarkRotateAngel());
			}
			if ("word".equals(markType)) {
				setWaterMarkGlobalValue("watermark.word.var", waterMarkForm.getMarkWordVar());
				setWaterMarkGlobalValue("watermark.word.fontfamily", waterMarkForm.getMarkWordFontFamily());
				setWaterMarkGlobalValue("watermark.word.fontcolor", waterMarkForm.getMarkWordFontColor());
				setWaterMarkGlobalValue("watermark.word.fontsize", waterMarkForm.getMarkWordFontSize());
			}
			if ("pic".equals(markType)) {
				FormFile markPicFile = waterMarkForm.getMarkPicFile();
				setWaterMarkGlobalValue("watermark.pic.filename", waterMarkForm.getMarkPicFileName());
				generateMarkPicFile(markPicFile);
			}
		}
	}

	public static boolean isPicAtt(SysAttMain sysAttMain) {
		return sysAttMain.getFdContentType().toLowerCase().contains("image");
	}

	private static void generateMarkPicFile(FormFile markPicFile) throws Exception {
		if (markPicFile == null || StringUtil.isNull(markPicFile.getFileName())) {
			return;
		}
		String diskMarkPicFile = getWaterMarkPicFileDir() + "/" + markPicFile.getFileName();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SysFileLocationUtil.getProxyService().writeFile(markPicFile.getInputStream(), diskMarkPicFile);
	}

	private static String getWaterMarkPicFileDir() {
		return "/watermark";
	}

	/**
	 * 获取视频文件路径
	 * @param attMain
	 * @return
	 * @throws Exception
	 */
	public static String getMediaFilePath(SysAttMain attMain) throws Exception {

		List<SysFileViewerParam> params = getConvertedParams(attMain);
		String fileFullPath = getAttFilePath(attMain);
		ISysAttUploadService sysAttUploadService =
				(ISysAttUploadService) SpringBeanUtil
						.getBean("sysAttUploadService");
		if (params.size() > 0) {
			String ck = params.get(0).getFdConverterKey();
			if (StringUtil.isNotNull(ck)) {
				String attFileFullPath =
						fileFullPath + "_convert/" + ck + "_flv";
				SysAttFile file = sysAttUploadService.getFileById(attMain.getFdFileId());
				String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
				boolean exists = SysFileLocationUtil
						.getProxyService(file.getFdAttLocation())
						.doesFileExist(attFileFullPath,pathPrefix);
				if (exists) {
					fileFullPath = attFileFullPath;
				}
			}
		}

		return fileFullPath;

	}

	/**
	 * 媒体文件流读取，优先使用转换后的文件
	 * 
	 * @param attMain
	 * @return
	 * @throws Exception
	 */
	public static InputStream getMediaFileInputStream(SysAttMain attMain)
			throws Exception {

		String fileFullPath = getMediaFilePath(attMain);

		ISysAttUploadService sysAttUploadService =
				(ISysAttUploadService) SpringBeanUtil
						.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(attMain.getFdFileId());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return SysFileLocationUtil.getProxyService(attFile.getFdAttLocation())
				.readFile(fileFullPath,pathPrefix);
	}

	public static JSONObject getWaterMarkInfos(JSONObject waterMarkConfig, HttpServletRequest request) {
		JSONObject markInfos = new JSONObject();
		String markType = waterMarkConfig.getString("markType");
		int markWidth = 0;
		int markHeight = 0;
		if ("word".equals(markType)) {
			String fontFamily = waterMarkConfig.getString("markWordFontFamily");
			Integer fontSize = Integer.parseInt(waterMarkConfig.getString("markWordFontSize"));
			String markWord = "";
			markWord = getVarMarkWord(waterMarkConfig.getString("markWordVar"),
					request);
			if (StringUtil.isNull(markWord)) {
				markWord = UserUtil.getUser().getFdName();
			}
			Font font = new Font(fontFamily, Font.CENTER_BASELINE, fontSize);
			FontMetrics fm = FontDesignMetrics.getMetrics(font);
			markWidth = fm.stringWidth(markWord);
			markHeight = fm.getHeight();
			markInfos.accumulate("markWord", markWord);
		} else if ("pic".equals(markType)) {
			String markPicFileName = waterMarkConfig.getString("markPicFileName");
			ImageInputStream imageStream = null;
			try {
				imageStream = ImageIO.createImageInputStream(getPicFileInputStream(markPicFileName));
				Iterator<ImageReader> iter = ImageIO.getImageReaders(imageStream);
				if (iter.hasNext()) {
					ImageReader imageReader = iter.next();
					imageReader.setInput(imageStream, true);
					markWidth = imageReader.getWidth(0);
					markHeight = imageReader.getHeight(0);
				}
				markInfos.accumulate("picUrl", request.getContextPath()
						+ "/sys/attachment/sys_att_watermark/sysAttWaterMark.do?method=getWaterMarkPNG");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		markInfos.accumulate("markWidth", markWidth);
		markInfos.accumulate("markHeight", markHeight);
		return markInfos;
	}

	public static String getVarMarkWord(String varMarkWord,
			HttpServletRequest request) {
          return getVarMarkWord(varMarkWord, request, null);
	}

	public static String getVarMarkWord(String varMarkWord,
										HttpServletRequest request, com.alibaba.fastjson.JSONObject json) {
		StringBuffer resultSB = new StringBuffer();
		SysOrgPerson curUser = null;

		// 与第三方系统集成情况下，水印的用户信息通过回调所给的userId查询
		if(json != null) {
			String userId = json.getString("userId");
			if(StringUtil.isNotNull(userId)) {
				curUser = UserUtil.getUser(userId);
			}
		} else {
			curUser = UserUtil.getUser();
		}


		String curUserDept = curUser.getFdParent() == null ? "" : curUser.getFdParent().getFdName();
		String curUserName = curUser.getFdName();
		String curUserLoginName = curUser.getFdLoginName();
		String curUserPhoneNo = curUser.getFdMobileNo();
		String curDate = DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE);
		String curIp = request.getRemoteAddr();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String curTime = df.format(new Date());
		if (StringUtil.isNotNull(varMarkWord)) {
			String[] vars = varMarkWord.split("%");
			for (String item : vars) {
				if (item.contains("(")
						&& item.contains(",") && item.contains(")")) {
					int oneIndex = item.indexOf("(");
					int twoIndex = item.indexOf(",");
					int threeIndex = item.indexOf(")");
					String func = item.substring(0, oneIndex);
					String field = item.substring(oneIndex + 1, twoIndex);
					String length = item.substring(twoIndex + 1, threeIndex);
					int subLength = 0;
					try {
						subLength = Integer.parseInt(length);
					} catch (Exception e) {
						subLength = 0;
					}
					if ("dept".equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(
									subRight(curUserDept, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(
									subLeft(curUserDept, subLength));
						}
					} else if ("name".equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(
									subRight(curUserName, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(
									subLeft(curUserName, subLength));
						}
					} else if ("loginname"
							.equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(
									subRight(curUserLoginName, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(
									subLeft(curUserLoginName, subLength));
						}
					} else if ("phoneno"
							.equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(
									subRight(curUserPhoneNo, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(
									subLeft(curUserPhoneNo, subLength));
						}
					} else if ("date".equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(subRight(curDate, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(subLeft(curDate, subLength));
						}
					} else if ("ip".equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(subRight(curIp, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(subLeft(curIp, subLength));
						}
					}else if ("time".equals(field.toLowerCase().trim())) {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(subRight(curTime, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(subLeft(curTime, subLength));
						}
					} else {
						if ("right".equals(func.toLowerCase().trim())) {
							resultSB.append(subRight(field, subLength));
						} else if ("left"
								.equals(func.toLowerCase().trim())) {
							resultSB.append(subLeft(field, subLength));
						}
					}
				} else {
					if ("dept".equals(item.toLowerCase().trim())) {
						resultSB.append(StringUtil.isNull(curUserDept) ? ""
								: curUserDept);
					} else if ("name".equals(item.toLowerCase().trim())) {
						resultSB.append(StringUtil.isNull(curUserName) ? ""
								: curUserName);
					} else if ("loginname".equals(item.toLowerCase().trim())) {
						resultSB.append(StringUtil.isNull(curUserLoginName) ? ""
								: curUserLoginName);
					} else if ("phoneno".equals(item.toLowerCase().trim())) {
						resultSB.append(StringUtil.isNull(curUserPhoneNo) ? ""
								: curUserPhoneNo);
					} else if ("date".equals(item.toLowerCase().trim())) {
						resultSB.append(curDate);
					} else if ("ip".equals(item.toLowerCase().trim())) {
						resultSB.append(curIp);
					} else if ("time".equals(item.toLowerCase().trim())) {
						resultSB.append(curTime);
					}else {
						resultSB.append(item);
					}
				}
			}
		}
		return resultSB.toString();
	}
	/**
	 * 格式化路径<br>
	 * 内部补全项目名，外部链接则保留
	 * 
	 * @param request
	 * @param url
	 *            业务链接
	 * @return
	 */
	public static String formatUrl(HttpServletRequest request, String url) {

		if (url.startsWith("/")) {
			return request.getContextPath() + url;
		}

		return url;

	}
	
	/**
	 * 从右侧开始截取部分字符串,长度为length
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	private static String subRight(String value, int length) {
		if (StringUtil.isNull(value)) {
			return "";
		}
		if (length > value.length() || length <= 0) {
			return value;
		}
		int index = value.length() - length;
		return value.substring(index);

	}

	/**
	 * 从左侧开始截取部分字符串,长度为length
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	private static String subLeft(String value, int length) {
		if (StringUtil.isNull(value)) {
			return "";
		}
		if (length > value.length() || length <= 0) {
			return value;
		}
		return value.substring(0, length);
	}
	
	public static Map<String, Boolean> getConvertedResult(List<SysAttMain> sysAttMains) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		if (sysAttMains != null && sysAttMains.size() > 0) {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "";
			for (SysAttMain attMain : sysAttMains) {
				if (StringUtil.isNotNull(attMain.getFdFileId())) {
					whereBlock += "or fdFileId='" + attMain.getFdFileId() + "' ";
					result.put(attMain.getFdFileId(), Boolean.FALSE);
				} else {
					whereBlock += "or fdAttMainId='" + attMain.getFdId() + "' ";
					result.put(attMain.getFdId(), Boolean.FALSE);
				}
			}
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock = whereBlock.substring(3);
			}
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setGetCount(false);
			try {
				List<SysFileViewerParam> params = convertDataService.getViewerParamService().findList(hqlInfo);
				for (SysFileViewerParam param : params) {
					if (StringUtil.isNotNull(param.getFdFileId())) {
						result.put(param.getFdFileId(), Boolean.TRUE);
					} else {
						result.put(param.getFdAttMainId(), Boolean.TRUE);
					}
				}
			} catch (Exception e) {
				logger.error("getConvertedResult", e);
			}
		}
		return result;
	}
	
	public static String getPreView(SysAttMain attMain, boolean isMobile) throws Exception {
		
		String pc=SysAttConfigUtil.getOnlineToolType();//PC端预览方式
		
		String result="";
		
		boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();//是否开启html转换
		Boolean isWindows=SysAttWpsCloudUtil.checkWpsPreviewIsWindows();//是否windows在线预览
		Boolean isLinux=SysAttWpsCloudUtil.checkWpsPreviewIsLinux();//是否linux在线预览
	
		String fileName=attMain.getFdFileName();
		
		switch(pc) {
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG:
			{
				if(SysAttConstant.MOBILE_CLOUD.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.JG_VIEW;
						if(isMobile) {
							result=SysAttConstant.WPS_CLOUD_VIEW;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.CONVERTING;
						if(isMobile) {
							result=SysAttConstant.WPS_CLOUD_VIEW;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.WPS_CLOUD_VIEW;
							}
							if(isWindows&&isMobile) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isMobile) {
								result=SysAttConstant.WPS_CLOUD_VIEW;
							}
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.WPS_CLOUD_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isWindows&&isMobile&&JgWebOffice.isPDF(fileName)) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isMobile) {
								result=SysAttConstant.WPS_CLOUD_VIEW;
							}
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
					
				}
				
				if(SysAttConstant.MOBILE_WPS.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.JG_VIEW;
						if(isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.CONVERTING;
						if(isMobile) {
							result=SysAttConstant.CONVERTING;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isWindows&&isMobile) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isWindows&&isMobile) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
				}
				
				if(SysAttConstant.MOBILE_NONE.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.JG_VIEW;
						if(isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.CONVERTING;
						if(isMobile) {
							result=SysAttConstant.CONVERTING;
						}
						if(isWindows&&isMobile) {
							result=SysAttConstant.CONVERTING;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.CONVERTING;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isWindows&&isMobile) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						if(JgWebOffice.isOfficePDFJudge()) {
							result=SysAttConstant.I_WEB_VIEW;
							if(isMobile) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isWindows&&isMobile) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(isWindows&&isMobile&&JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux&&isMobile) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}else {
							result=SysAttConstant.DOWNLOAD;
							if(isWindows) {
								result=SysAttConstant.WPS_WINDOW_VIEW;
							}
							if(JgWebOffice.isOFD(fileName)) {
								result=SysAttConstant.DOWNLOAD;
							}
							if(isLinux) {
								result=SysAttConstant.WPS_LINUX_VIEW;
							}
						}
						
					}
				}
				break;
			}
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD: {
			    // PC端为云文档
				if (!isMobile) {
					if (isWindows) {
						result = SysAttConstant.WPS_WINDOW_VIEW;
					}
					if (isLinux) {
						result = SysAttConstant.WPS_LINUX_VIEW;
					}
					break;
				}
			    // 移动端为云文档
				if(SysAttConstant.MOBILE_CLOUD.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
				}
				
				if(SysAttConstant.MOBILE_WPS.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux&&isMobile) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
				}
				break;
			}
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST:
			{
				if(SysAttConstant.MOBILE_CLOUD.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_OAASSIST_VIEW;
						if(isMobile) {
							result=SysAttConstant.WPS_CLOUD_VIEW;
						}
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_OAASSIST_VIEW;
						if(isMobile) {
							result=SysAttConstant.WPS_CLOUD_VIEW;
						}
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_CLOUD_VIEW;
						if(isWindows&&isMobile) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
				}
				
				if(SysAttConstant.MOBILE_WPS.equals(SysAttConfigUtil.getMobileOnlineToolType())) {
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&!attConvertEnable) {
						result=SysAttConstant.WPS_OAASSIST_VIEW;
						if(isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					if(SysAttUtil.isOfficeTypeByWps(FilenameUtils.getExtension(fileName))&&attConvertEnable) {
						result=SysAttConstant.WPS_OAASSIST_VIEW;
						if(isMobile) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&!attConvertEnable) {
						result=SysAttConstant.DOWNLOAD;
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
						
					}
					
					if ((JgWebOffice.isPDF(fileName)||JgWebOffice.isOFD(fileName))&&attConvertEnable) {
						result=SysAttConstant.DOWNLOAD;
						if(isWindows) {
							result=SysAttConstant.WPS_WINDOW_VIEW;
						}
						if(JgWebOffice.isOFD(fileName)) {
							result=SysAttConstant.DOWNLOAD;
						}
						if(isLinux) {
							result=SysAttConstant.WPS_LINUX_VIEW;
						}
					}
					
				}
				break;
			}
			default:
		}
		/**
		 * 优先在线预览 144883
		 */
		if (!JgWebOffice.isJGPDF2018Enabled() && (JgWebOffice.isPDF(fileName) || JgWebOffice.isOFD(fileName))) {
			result=SysAttConstant.WPS_LINUX_VIEW;
		}
		if (SysAttConstant.CONVERTING.equals(result)) {
			List<SysFileViewerParam> convertedParams = SysAttViewerUtil.getConvertedParams(attMain);
			if (convertedParams == null || convertedParams.size() <= 0) {
				result=SysAttConstant.WPS_LINUX_VIEW;
			}
		}
		//#156744预览编辑使用金格，未启用金格iWebPDF，PDF打开方式（如果附件已转换，则优先以快速方式打开）选择"严控文件下载"功能不生效
		if ("0".equals(SysAttConfigUtil.getOnlineToolType()) && !JgWebOffice.isJGPDFEnabled() && !JgWebOffice.isJGPDF2018Enabled()
				 && (JgWebOffice.isPDF(fileName) || JgWebOffice.isOFD(fileName)) && "1".equals(SysAttConfigUtil.isReadPdf())) {
			result=SysAttConstant.DOWNLOAD;
		}

		//OFD文件并且开启了福昕阅读
		if( JgWebOffice.isOFD(fileName) && "6".equals(SysAttConfigUtil.getReadOLConfig())) {
			result = "";
		}

		logger.info("预览方式："+result);
		//System.out.println("预览方式："+result);
		return result;
	}

	public static String getDownloadUrl(SysAttMain sysAttMain) throws Exception{
		String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		if(urlPrefix.endsWith("/")) {
			urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
		}

		if(logger.isDebugEnabled()) {
			logger.debug("系统地址:" + urlPrefix);
		}
		long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
		// 不支持直连时（代理）走统一的系统附件下载url，因为对外时可能未登录，要添加签名验证
		String sign = getRestSign(sysAttMain.getFdId(), expires);
		String downLoadUrl = urlPrefix + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + sysAttMain.getFdId() + "&Expires="
				+ expires + "&Signature=" + sign + "&reqType=rest&filename=" + URLEncoder.encode(sysAttMain.getFdFileName(), "utf-8");

		return downLoadUrl;
	}

	public static String getUploadUrl(SysAttMain sysAttMain) throws Exception {

		if (!TokenGenerator.isInitialized()) {

			String zdWebContentPath = ConfigLocationsUtil
					.getKmssConfigPath();
			if (StringUtil.isNotNull(zdWebContentPath)) {
				zdWebContentPath = zdWebContentPath + "/LRToken";
			}

			TokenGenerator.loadFromKeyFile(zdWebContentPath);
		}

		Token token = TokenGenerator.getInstance()
				.generateTokenByUserName(
						UserUtil.getUser().getFdLoginName());

		String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		if(urlPrefix.endsWith("/")) {
			urlPrefix = urlPrefix.substring(0, urlPrefix.lastIndexOf("/"));
		}
		String uploadUri = urlPrefix +  "/sys/attachment/uploadFile.do?"
				+ "userId=" + UserUtil.getUser().getFdId()
				+"&fdId=" + sysAttMain.getFdId()
				+"&wpsOasisstToken=" + token.toString();

		return uploadUri;
	}

	/**
	 * 为下载链接签名
	 *
	 * @param expires
	 * @param attMainId
	 * @return
	 * @throws Exception
	 */
	private static String getRestSign(String attMainId, long expires) throws Exception {
		String signStr = expires + ":" + attMainId;
		ISysRestserviceServerMainService sysRestMainService = (ISysRestserviceServerMainService) SpringBeanUtil
				.getBean("sysRestserviceServerMainService");
		SysRestserviceServerMain sysRestserviceServerMain = sysRestMainService
				.findByServiceBean("sysAttachmentRestService");
		List<SysRestserviceServerPolicy> webPolicys = sysRestserviceServerMain.getFdPolicy();
		if (ArrayUtil.isEmpty(webPolicys)) {
			return "";
		}
		SysRestserviceServerPolicy webPolicy = webPolicys.get(0);
		String sign = SignUtil.getHMAC(signStr + ":" + webPolicy.getFdLoginId(),
				StringUtil.isNotNull(webPolicy.getFdPassword()) ? webPolicy.getFdPassword() : webPolicy.getFdId());
		return sign;
	}
}
