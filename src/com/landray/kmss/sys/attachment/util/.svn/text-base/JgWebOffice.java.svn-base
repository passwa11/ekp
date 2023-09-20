package com.landray.kmss.sys.attachment.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.jg.ISysAttachmentJGAddtionFunction;
import com.landray.kmss.sys.attachment.jg.ISysAttachmentJGFunction;
import com.landray.kmss.sys.attachment.jg.JGFilePathUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.model.SysFileViewerParam;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.log.util.ua.UserAgent;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import DBstep.iMsgServer2000;

public class JgWebOffice {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JgWebOffice.class);
	
	public static final String JG_OCX_BIG_VERSION_2003 = "2003";
	
	public static final String JG_OCX_BIG_VERSION_2009 = "2009";
	
	public static final String JG_OCX_BIG_VERSION_2015 = "2015";
	
	public static final String JG_OCX_BIG_VERSION_ZZKK = "zzkk";

	public static final String PDF_OCX_BIG_VERSION = "iWebPDF";

	public static final String PDF_OCX_BIG_VERSION_2018 = "iWebPDF2018";

	// 金格webOffice下载地址
	public static final String JG_OCX_DOWNLOAD_URL = "/sys/attachment/plusin/iWebOffice2009.cab";

	// 金格webOffice控件版本
	public static final String JG_OCX_VERSION = "10,8,5,5";

	// 金格多浏览器控件下载地址
	public static final String JG_MUL_DOWNLOAD_URL = "/sys/attachment/plusin/iWebPlugin.zip";

	// 金格多浏览控件版本
	public static final String JG_MUL_VERSION = "www.landray.com.cn";

	// 金格pdf控件下载地址
	public static final String JG_PDF_DOWNLOAD_URL = "/sys/attachment/plusin/iWebPDF.cab";

	// 金格pdf控件版本
	public static final String JG_PDF_VERSION = "8,2,0,1003";
	
	// 金格网页签章地址
	public static final String JG_SIGNATUREHTML_DOWNLOAD_URL = "/sys/attachment/plusin/iSignatureHTML.cab";

	// 金格网页签章版本
	public static final String JG_SIGNATUREHTML_VERSION = "8,2,2,56";

	private static final String PDF_TYPE = "pdf";
	
	private static final String OFD_TYPE = "ofd";

	// 金格webOffice2015下载地址
	public static final String JG_OCX_DOWNLOAD_URL_2015 = "/sys/attachment/plusin/iWebOffice2015.cab";

	// 金格webOffice2015控件版本
	public static final String JG_OCX_VERSION_2015 = "12,4,0,500";
	
	// 金格webOffice2015控件授权码
	public static final String JG_OCX_COPYRIGHT_2015 = "蓝凌软件[内部开发];V5.0S0xGAAEAAAAAAAAAEAAAADwBAABAAQAALAAAANOMEteJE5UKY9xvKxwJ1iurw1TkMZ3kRSPnnIMDMR5rKzTjEz6qImddPSfYprGcq/Lu6wm959YeEYlx9ZCNGGYb5v5thZQdrIpgB5F6dbRjFD+ByP6Ox1FzGInlxgjwpTHSvCIJfi7yolunQD0CMabmh/VrluLscDQHSLBp8gREvN5nYXmsIis6wbrgNhBkHyinXYl4H09wSEwZ2HtHxLOXqsq9bVrgN7jkVkq6fIJAWHNnfvL/3EB/1g08qktLJSAv9Y0k2fEcUNnOcJ9YmOkCYxeqkRVwAsLZdopqqzX+HcvRqgD2Ga+yalRC4OgF1mY2QVcKnIBPR8Kxi/ZQ5R/rStaSmFhxD4iua9Q1OVCmnYMW8ZZAMhjh1H6QPneRdV7pEHrfQAXYwigGRDeRMZ56YMT5IQItzl6fiurRphIO55sPiwbflEQwvRllZhJQZQ==";

	// 金格webOfficeZZKK下载地址
	public static final String JG_OCX_DOWNLOAD_URL_ZZKK = "/sys/attachment/plusin/iWebOffice2015.cab";

	// 金格webOfficeZZKK控件版本
	public static final String JG_OCX_VERSION_ZZKK = "12,5,0,652";
	
	// 金格webOfficeZZKK控件授权码
	public static final String JG_OCX_COPYRIGHT_ZZKK = "金格科技自主可控测试[专用];V5.0S0xGAAEAAAAAAAAAEAAAAEABAABQAQAALAAAAHuaWYCFyoQ/c3nGbosEoEEp4+FPHenVbSvUUsalN0JzHx2Nsn0BCJNXZiwKag7DtVzgcqwLrV4qSte5ltPfOoqPjRHqm/N/IDJGgGbb2dR/mXx1DR/6SVrqw+5uAnKBQKR3O4KCru0JEh2Cq3WNaulPsXVAyUZi6QfJfLtJ1whu3N+dp2KLK14XRp41krDFBGZn5shc525fLio/V23o2Ol7sgheN3EnhMxGU7XfmnkkOzqfPs2hlyLBbfmRB43kl+sr8Sng9hdI7BK/G8yxMhFeahjtrUHXq6gQJlbyexLIlO4jbGjoYXg9rmOESljztUWEvJdk452mp1R/wTvsSi7nuPz7ePmHhlXY39hJkgSWwvHv9rZByngwNBUPOCHwYJuEo4DM3SmiQzW5X19ct/jrg5MQf76DfqQ0pblhCqRmLoGeNAF91gzxE/u7bB5CkPGn915qKNV8pAqu6kYziGM=";

	
	// 金格pdf2018控件下载地址
	public static final String JG_PDF_DOWNLOAD_URL_2018 = "/sys/attachment/plusin/iWebPDF2018.cab";

	// 金格pdf2018控件版本
	public static final String JG_PDF_VERSION_2018 = "3,1,6,2254";
	
	// 金格webOffice2003下载地址
	public static final String JG_OCX_DOWNLOAD_URL_2003 = "/sys/attachment/plusin/iWebOffice2003.ocx";
	
	// 金格webOffice2003控件版本
	public static final String JG_OCX_VERSION_2003 = "8,8,8,72";
	
	// 金格webOffice2003控件使用的办公软件类型
	public static final String JG_OCX_OFFICE_TYPE_2003 = "office";

	/**
	 * 是否启用金格
	 * 
	 * @return
	 */
	public static boolean isJGEnabled() {
		/*
		 * 金格控件默认使用，不实用蓝凌控件 String isJGEnabled = ResourceUtil
		 * .getKmssConfigString("sys.att.isJGEnabled"); if (isJGEnabled != null
		 * && "true".equals(isJGEnabled.trim())) return true;
		 */

		return true;
	}

	/**
	 * 是否启动金格PDF控件
	 * 
	 * @return
	 */
	public static boolean isJGPDFEnabled() {
		String isJGPDFEnabled = ResourceUtil
				.getKmssConfigString("sys.att.isJGPDFEnabled");
		if (isJGPDFEnabled != null && "true".equals(isJGPDFEnabled.trim())) {
            return true;
        }
		return false;
	}
	
	/**
	 * 是否启动金格PDF2018控件
	 * 
	 * @return
	 */
	public static boolean isJGPDF2018Enabled() {
		String isJGPDFEnabled = ResourceUtil
				.getKmssConfigString("sys.att.isJGPDF2018Enabled");
		if (isJGPDFEnabled != null && "true".equals(isJGPDFEnabled.trim())) {
            return true;
        }
		return false;
	}
	
	/**
	 * 是否启动金格PDF2018控件签章
	 * 
	 * @return
	 */
	public static boolean isJGPDF2018SignatureEnabled() {
		if(isJGPDF2018Enabled()){
			String isJGPDFSignatureEnabled = ResourceUtil
					.getKmssConfigString("sys.att.isJGSignaturePDFEnabled");
			if (isJGPDFSignatureEnabled != null && "true".equals(isJGPDFSignatureEnabled.trim())) {
                return true;
            }
		}
		return false;
	}

	/**
	 * 得到PDF2018控件地址
	 * 
	 * @return
	 */
	public static String getJGPdf2018URL() {
		String jgOcxUrl = ResourceUtil
				.getKmssConfigString("sys.att.jg.pdfurl2018");
		if (StringUtil.isNull(jgOcxUrl)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxUrl = (String) (tmpClass
						.getField("JG_PDF_DOWNLOAD_URL_2018").get(tmpClass));
			} catch (Exception e) {
				jgOcxUrl = JG_PDF_DOWNLOAD_URL_2018;
			}
		}
		return jgOcxUrl;
	}

	/**
	 * 得到PDF2018控件版本号
	 * 
	 * @return
	 */
	public static String getJGPdf2018Version() {
		String jgOcxVersion = ResourceUtil
				.getKmssConfigString("sys.att.jg.pdfversion2018");
		if (StringUtil.isNull(jgOcxVersion)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxVersion = (String) (tmpClass
						.getField("JG_PDF_VERSION_2018").get(tmpClass));
			} catch (Exception e) {
				jgOcxVersion = JG_PDF_VERSION_2018;
			}
		}
		return jgOcxVersion;
	}

	/**
	 * 得到金格大版本号
	 */
	public static String getJGBigVersion() {
		String bigVersion = ResourceUtil.getKmssConfigString("sys.att.jg.plugintype");
		if (StringUtil.isNull(bigVersion)){
			bigVersion = JG_OCX_BIG_VERSION_2009;
		} else if (JG_OCX_BIG_VERSION_2003.equals(bigVersion)) {
			//选择金格2003控件，也使用2009大版本
			bigVersion = JG_OCX_BIG_VERSION_2009;
		}
		return bigVersion;
	}

	/**
	 * 得到PDF大版本
	 */
	public static String getPDFBigVersion() {
		String bigVersion = ResourceUtil
				.getKmssConfigString("sys.att.pdf.plugintype");
		if (StringUtil.isNull(bigVersion)) {
			bigVersion = PDF_OCX_BIG_VERSION;
		}
		return bigVersion;
	}
	
	/**
	 * 得到国产化控件是否启用
	 */
	public static String getIsJGHandZzkkEnabled() {
		String isJGHandZzkkEnabled = "false";
		String isJGHandZzkkFlag = ResourceUtil
				.getKmssConfigString("sys.att.isJGHandZzkkEnabled");
		if (StringUtil.isNotNull(isJGHandZzkkFlag)
				&& "true".equals(isJGHandZzkkFlag)) {
			isJGHandZzkkEnabled = isJGHandZzkkFlag;
		}
		return isJGHandZzkkEnabled;
	}
	
	/**
	 * 得到当前操作系统
	 */
	public static String getOSType(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String OSType = userAgent.getOperatingSystem().getName();
		//String OSType = "windows";
		if(OSType.toLowerCase().contains("windows")){
			OSType = "windows";
		}
		return OSType;
	}

	/**
	 * 是否启动金格多浏览器控件
	 * 
	 * @return
	 */
	public static boolean isJGMULEnabled() {
		String JGMULEnabled = ResourceUtil
				.getKmssConfigString("sys.att.isJGMULEnabled");
		if (JGMULEnabled != null && "true".equals(JGMULEnabled.trim())){
			return true;
		}else if(JG_OCX_BIG_VERSION_2015.equals(getJGBigVersion())){
			return true;
		}
		return false;
	}

	/**
	 * 获取金格控件下载地址
	 * 
	 * @param key
	 *            金格控件类型 null或""或ocx 金格office控件 pdf 金格pdf控件 mul 金格多浏览器控件
	 * @return
	 */
	public static String getJGDownLoadUrl(String key) {
		key = StringUtil.isNull(key) ? "ocx" : key.toLowerCase();
		String jgOcxUrl = ResourceUtil.getKmssConfigString("sys.att.jg." + key
				+ "url");
		if (StringUtil.isNull(jgOcxUrl)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxUrl = (String) (tmpClass.getField("JG_"
						+ key.toUpperCase() + "_DOWNLOAD_URL").get(tmpClass));
			} catch (Exception e) {
				jgOcxUrl = JG_OCX_DOWNLOAD_URL;
			}
		}
		return jgOcxUrl;
	}
	
	/**
	 * 得到2015版本控件地址
	 * @return
	 */
	public static String getJGOcxURL2015() {
		String jgOcxUrl = ResourceUtil.getKmssConfigString("sys.att.jg.ocxurl.2015");
		if (StringUtil.isNull(jgOcxUrl)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxUrl = (String) (tmpClass.getField("JG_OCX_DOWNLOAD_URL_2015").get(tmpClass));
			} catch (Exception e) {
				jgOcxUrl = JG_OCX_DOWNLOAD_URL_2015;
			}
		}
		return jgOcxUrl;
	}
	
	/**
	 * 得到2015控件版本号
	 * @return
	 */
	public static String getJGOcxVersion2015() {
		String jgOcxVersion = ResourceUtil.getKmssConfigString("sys.att.jg.ocxversion.2015");
		if (StringUtil.isNull(jgOcxVersion)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxVersion = (String) (tmpClass.getField("JG_OCX_VERSION_2015").get(tmpClass));
			} catch (Exception e) {
				jgOcxVersion = JG_OCX_VERSION_2015;
			}
		}
		return jgOcxVersion;
	}
	
	/**
	 * 得到2015版本控件授权信息
	 * @return
	 */
	public static String getJGOcxCopyRight2015() {
		String jgOcxCopyright = ResourceUtil.getKmssConfigString("sys.att.jg.copyright.2015");
		if (StringUtil.isNull(jgOcxCopyright)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxCopyright = (String) (tmpClass.getField("JG_OCX_COPYRIGHT_2015").get(tmpClass));
			} catch (Exception e) {
				jgOcxCopyright = JG_OCX_COPYRIGHT_2015;
			}
		}
		return jgOcxCopyright;
	}
	
	/**
	 * 得到ZZKK版本控件地址
	 * @return
	 */
	public static String getJGOcxURLZZKK() {
		String jgOcxUrl = "";
		//String jgOcxUrl = ResourceUtil.getKmssConfigString("sys.att.jg.ocxurl.zzkk");
		if (StringUtil.isNull(jgOcxUrl)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxUrl = (String) (tmpClass.getField("JG_OCX_DOWNLOAD_URL_ZZKK").get(tmpClass));
			} catch (Exception e) {
				jgOcxUrl = JG_OCX_DOWNLOAD_URL_ZZKK;
			}
		}
		return jgOcxUrl;
	}
	
	/**
	 * 得到ZZKK控件版本号
	 * @return
	 */
	public static String getJGOcxVersionZZKK() {
		String jgOcxVersion = "";
		//String jgOcxVersion = ResourceUtil.getKmssConfigString("sys.att.jg.ocxversion.zzkk");
		if (StringUtil.isNull(jgOcxVersion)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxVersion = (String) (tmpClass.getField("JG_OCX_VERSION_ZZKK").get(tmpClass));
			} catch (Exception e) {
				jgOcxVersion = JG_OCX_VERSION_ZZKK;
			}
		}
		return jgOcxVersion;
	}
	
	/**
	 * 得到ZZKK版本控件授权信息
	 * @return
	 */
	public static String getJGOcxCopyRightZZKK() {
		String jgOcxCopyright = ResourceUtil.getKmssConfigString("sys.att.jg.copyright.zzkk");
		if (StringUtil.isNull(jgOcxCopyright)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxCopyright = (String) (tmpClass.getField("JG_OCX_COPYRIGHT_ZZKK").get(tmpClass));
			} catch (Exception e) {
				jgOcxCopyright = JG_OCX_COPYRIGHT_ZZKK;
			}
		}
		return jgOcxCopyright;
	}

	/**
	 * 得到iWebPDF控件授权信息
	 * 
	 * @return
	 */
	public static String getPDF2018CopyRight() {
		String jgOcxCopyright = ResourceUtil
				.getKmssConfigString("sys.att.pdf2018.copyright");
		return jgOcxCopyright;
	}

	/**
	 * 获取金格控件版本
	 * 
	 * @param key
	 *            金格控件类型 null或""或ocx 金格office控件 pdf 金格pdf控件 mul 金格多浏览器控件
	 * @return
	 */
	public static String getJGVersion(String key) {
		key = StringUtil.isNull(key) ? "ocx" : key.toLowerCase();
		String jgOcxVersion = ResourceUtil.getKmssConfigString("sys.att.jg."
				+ key + "version");
		if (StringUtil.isNull(jgOcxVersion)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxVersion = (String) (tmpClass.getField("JG_"
						+ key.toUpperCase() + "_VERSION").get(tmpClass));
			} catch (Exception e) {
				jgOcxVersion = JG_OCX_VERSION;
			}
		}
		return jgOcxVersion;
	}
	
	/**
	 * 得到2003版本控件地址
	 * @return
	 */
	public static String getJGOcxURL2003() {
		String jgOcxUrl = ResourceUtil.getKmssConfigString("sys.att.jg.ocxurl.2003");
		if (StringUtil.isNull(jgOcxUrl)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxUrl = (String) (tmpClass.getField("JG_OCX_DOWNLOAD_URL_2003").get(tmpClass));
			} catch (Exception e) {
				jgOcxUrl = JG_OCX_DOWNLOAD_URL_2003;
			}
		}
		return jgOcxUrl;
	}
	
	/**
	 * 得到2003控件版本号
	 * @return
	 */
	public static String getJGOcxVersion2003() {
		String jgOcxVersion = ResourceUtil.getKmssConfigString("sys.att.jg.ocxversion.2003");
		if (StringUtil.isNull(jgOcxVersion)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOcxVersion = (String) (tmpClass.getField("JG_OCX_VERSION_2003").get(tmpClass));
			} catch (Exception e) {
				jgOcxVersion = JG_OCX_VERSION_2003;
			}
		}
		return jgOcxVersion;
	}
	
	/**
	 * 得到2003控件使用的办公软件类型
	 * @return
	 */
	public static String getJGOfficeType() {
		String jgOfficeType = ResourceUtil.getKmssConfigString("sys.att.jg.office.plugintype");
		if (StringUtil.isNull(jgOfficeType)) {
			Class tmpClass = JgWebOffice.class;
			try {
				jgOfficeType = (String) (tmpClass.getField("JG_OCX_OFFICE_TYPE_2003").get(tmpClass));
			} catch (Exception e) {
				jgOfficeType = JG_OCX_OFFICE_TYPE_2003;
			}
		}
		return jgOfficeType;
	}
	
	/**
	 * 获取配置的金格控件类型
	 */
	public static String getJGPluginType() {
		String pluginType = ResourceUtil.getKmssConfigString("sys.att.jg.plugintype");
		if (StringUtil.isNull(pluginType)) {
			pluginType = JG_OCX_BIG_VERSION_2009;
		}
		return pluginType;
	}

	/**
	 * 判断是否为PDF文档
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static final boolean isPDF(String fileName) {
		fileName = fileName.toLowerCase();
		if (PDF_TYPE.equals(FilenameUtils.getExtension(fileName))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为ofd文档
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static final boolean isOFD(String fileName) {
		fileName = fileName.toLowerCase();
		if (OFD_TYPE.equals(FilenameUtils.getExtension(fileName))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isExistFile(HttpServletRequest request)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNull(fdId)) {
			return false;
		}
		File hpFile = JGFilePathUtil.getFile(fdId, null, fdId + ".htm");
		if (!hpFile.exists()) {
			if (logger.isDebugEnabled()) {
				logger.debug("文件:" + fdId + ".htm可能被删除");
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断附件文件是否已经转换
	 */
	public static boolean isExistViewPath(HttpServletRequest request)
			throws Exception {
		boolean convertResult = false;
		boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();
		boolean oldConvertSuccessUseHTMLView = SysFileStoreUtil.isOldConvertSuccessUseHTML();

		if (attConvertEnable || oldConvertSuccessUseHTMLView) {
			String fdAttMainId = (String) request.getAttribute("fdAttMainId");
			SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService")).findByPrimaryKey(fdAttMainId);
			if (sysAttMain != null) {
				SysAttViewerUtil.addQueue(sysAttMain);
				List<SysFileViewerParam> convertedParams = SysAttViewerUtil.getConvertedParams(sysAttMain);
				if (convertedParams != null && convertedParams.size() > 0) {
					convertResult = true;
				}
			}
		}
		return convertResult;
	}
	
	/**
	 * 判断选择的office中PDF是否启用
	 */
	public static boolean isOfficePDFJudge(){
		return (isJGPDF2018Enabled() || isJGPDFEnabled());
	}

	/**
	 * 判断附件文件是否已经转换成Pdf
	 */
	public static boolean isExistPdfViewPath(HttpServletRequest request) throws Exception {
		boolean result = false;
		String fdAttMainId = (String) request.getAttribute("fdAttMainId");
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		result = checkExistPdfViewPath(sysAttMain);
		return result;
	}

	public static boolean checkExistOfdViewPath(SysAttMain sysAttMain) throws Exception {
		boolean result = false;
		String convertFileName = "toOFD-Suwell_ofd";
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile sysAsttFile = sysAttUploadService.getFileById(sysAttMain.getFdFileId());
		boolean exist = false;
		ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
				.getProxyService(sysAsttFile.getFdAttLocation());
		if (sysAsttFile != null) {
			String fdPath = sysAsttFile.getFdFilePath() + "_convert" + File.separator
					+ convertFileName;
			exist = sysFileLocationProxyService.doesFileExist(fdPath);
		}
		if (exist) {
			result = true;
		}
		return result;
	}
	
	public static boolean checkExistWpsOfdViewPath(SysAttMain sysAttMain) throws Exception {
		boolean result = false;
		String convertFileName = "toOFD-WPS_ofd";
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile sysAsttFile = sysAttUploadService.getFileById(sysAttMain.getFdFileId());
		boolean exist = false;
		String basePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.lastIndexOf("/"));
		}
		String fdPath = basePath + sysAsttFile.getFdFilePath() + "_convert" + File.separator
				+ convertFileName;
		if (sysAsttFile != null) {
			if (new File(fdPath).exists()) {
				exist = true;
			}
		}
		if (exist) {
			result = true;
		}
		return result;
	}

	public static boolean checkExistWpsCenterOfdViewPath(SysAttMain sysAttMain) throws Exception {
		boolean result = false;
		String convertFileName = "toOFD-WPSCenter_ofd";
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile sysAsttFile = sysAttUploadService.getFileById(sysAttMain.getFdFileId());
		boolean exist = false;
		String basePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.lastIndexOf("/"));
		}
		String fdPath = basePath + sysAsttFile.getFdFilePath() + "_convert" + File.separator
				+ convertFileName;
		if (sysAsttFile != null) {
			if (new File(fdPath).exists()) {
				exist = true;
			}
		}
		if (exist) {
			result = true;
		}
		return result;
	}


	/**
	 * 判断附件文件是否已经转换成Ofd
	 */
	public static boolean checkExistWpsOfdViewPath(HttpServletRequest request) throws Exception {
		boolean result = false;
		String fdAttMainId = (String) request.getAttribute("fdAttMainId");
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		String convertFileName = "toOFD-WPS_ofd";
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile sysAsttFile = sysAttUploadService.getFileById(sysAttMain.getFdFileId());
		boolean exist = false;
		String basePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.lastIndexOf("/"));
		}
		String fdPath = basePath + sysAsttFile.getFdFilePath() + "_convert" + File.separator
				+ convertFileName;
		if (sysAsttFile != null) {
			if (new File(fdPath).exists()) {
				exist = true;
			}
		}
		if (exist) {
			result = true;
		}
		return result;
	}

	public static boolean checkExistPdfViewPath(SysAttMain sysAttMain) throws Exception {
		boolean result = false;
		boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();
		boolean oldConvertSuccessUseHTMLView = SysFileStoreUtil.isOldConvertSuccessUseHTML();
		if (attConvertEnable || oldConvertSuccessUseHTMLView) {
			if (sysAttMain != null) {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysFileConvertQueue.fdFileId=:fdFileId and sysFileConvertQueue.fdConverterKey=:fdConverterKey");
				hql.setParameter("fdFileId", sysAttMain.getFdFileId());
				hql.setParameter("fdConverterKey", "toHTML");
				Object obj = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService"))
						.findFirstOne(hql);

				if (obj == null) {
					HQLInfo hql2 = new HQLInfo();
					hql2.setWhereBlock(
							"sysFileConvertQueue.fdAttMainId=:fdAttMainId and sysFileConvertQueue.fdConverterKey=:fdConverterKey");
					hql2.setParameter("fdAttMainId", sysAttMain.getFdId());
					hql2.setParameter("fdConverterKey", "toHTML");
					obj = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService"))
							.findFirstOne(hql2);
				}
				// 如果转换队列存在数据，有可能更新完附件，正在转换，这时候需要判断转换状态
				if (obj!=null) {
					SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) obj;
					int convertStatus = sysFileConvertQueue.getFdConvertStatus();
					// 文件转换成功
					if (convertStatus == 4) {
						String convertFileName = "toHTML-Aspose_pdf";
						String viewerKey = SysAttViewerUtil.getFileViewerKey(sysAttMain);
						if (StringUtil.isNotNull(viewerKey)) {
							convertFileName = viewerKey + "_pdf";
							if (convertFileName.contains("/") || convertFileName.contains("\\")
									|| convertFileName.contains("..")) {
								return false;
							}
							SysAttFile sysAsttFile = ((ISysAttMainCoreInnerService) SpringBeanUtil
									.getBean("sysAttMainService"))
									.getFile(sysAttMain.getFdId());
							boolean exist = false;
							ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
									.getProxyService(sysAsttFile.getFdAttLocation());
							if (sysAsttFile != null) {
								String fdPath = sysAsttFile.getFdFilePath() + "_convert" + File.separator
										+ convertFileName;
								exist = sysFileLocationProxyService.doesFileExist(fdPath);
							}
							if (exist) {
								result = true;
							}
						} else {
							result = false;
						}
					} else {
						SysAttViewerUtil.addQueue(sysAttMain);
					}
				}
			}
		}
		return result;
	}


	public static String getConvertFileStatus(SysAttMain sysAttMain) throws Exception {
		String status = "";
		List<SysFileConvertClient> asposeClients = new ArrayList<SysFileConvertClient>();
		List<SysFileConvertClient> yozoClients = new ArrayList<SysFileConvertClient>();
		ISysFileConvertClientService sysFileConvertClientService = (ISysFileConvertClientService) SpringBeanUtil
				.getBean("sysFileConvertClientService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("avail = :avail and converterFullKey like :converterKey");
		hqlInfo.setParameter("avail", Boolean.TRUE);
		hqlInfo.setParameter("converterKey", "%toHTML%");
		List<SysFileConvertClient> availClients = sysFileConvertClientService.findList(hqlInfo);
		for (SysFileConvertClient item : availClients) {
			if (item.getConverterFullKey().toLowerCase().contains("yozo")) {
				yozoClients.add(item);
			} else if (item.getConverterFullKey().toLowerCase().contains("aspose")) {
				asposeClients.add(item);
			}
		}
		int asposeClientsSize = asposeClients.size();
		int yozoClientsSize = yozoClients.size();
		if (yozoClientsSize == 0 && asposeClientsSize == 0) {
			status = "";
		} else {
			if (sysAttMain != null) {
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock(
						"sysFileConvertQueue.fdAttMainId=:fdAttMainId and sysFileConvertQueue.fdFileId=:fdFileId");
				hql.setParameter("fdAttMainId", sysAttMain.getFdId());
				hql.setParameter("fdFileId", sysAttMain.getFdFileId());
				List l = ((ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService"))
						.findList(hql);
				if (l.size() > 0) {
					int convertStatus = 0;
					//存在多条记录的情况下，看是否存在转换成功的记录
					if (l.size() == 1) {
						SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) l.get(0);
						convertStatus = sysFileConvertQueue.getFdConvertStatus();
					} else {
						for (int m = 0; m < l.size(); m ++) {
							SysFileConvertQueue sysFileConvertQueue = (SysFileConvertQueue) l.get(m);
							convertStatus = sysFileConvertQueue.getFdConvertStatus();
							if (convertStatus == 4) {
								break;
							}
						}
					}

					if (convertStatus == 0) {
						// 转换任务未分配
						status = "0";
					} else if (convertStatus == 1) {
						// 已分配给转换服务
						status = "1";
					} else if (convertStatus == 4) {
						// 转换成功
						status = "4";
					} else {
						// 转换失败
						status = "-1";
					}
					}
				}
			}
		return status;
	}

	/**
	 * 判断文件是否转换
	 */
	public static String getConvertFileStatus(HttpServletRequest request) throws Exception {
		String fdAttMainId = (String) request.getAttribute("fdAttMainId");
		SysAttMain sysAttMain = (SysAttMain) ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
				.findByPrimaryKey(fdAttMainId);
		if (sysAttMain != null) {
			return getConvertFileStatus(sysAttMain);
		}
		return "";
	}

	/**
	 * 删除html文件
	 * 
	 * @param fdId
	 * @return
	 */
	public static boolean deleteFile(String fdId) {
		try {
			File hpHtm = JGFilePathUtil.getFile(fdId, null, fdId + ".htm");
			if (hpHtm.exists()) {
				FileUtil.deleteFile(hpHtm);
			}
			File hpFiles = JGFilePathUtil.getFile(fdId, null, fdId + ".files");
			if (hpFiles.exists()) {
				FileUtil.deleteFile(hpFiles);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static final String JG_POINT = "com.landray.kmss.sys.attachment.jg.function";

	private static final String JG_POINT_ADDITION = "com.landray.kmss.sys.attachment.jg.function.addition";

	private static final String JG_FUN_ITEM = "function";

	private static final String JG_FUN_NAME = "function";

	// 执行金格在线编辑的附加特性，用于ekp平台中，本身增加的金格后台交互逻辑处理
	public void addition(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String option = request.getParameter("method");
			IExtension[] extensions = Plugin.getExtensions(JG_POINT_ADDITION,
					"*", JG_FUN_ITEM);
			if (logger.isDebugEnabled()) {
				logger.info("金格附加操作特性名:" + option);
			}
			IExtension extension = Plugin.getExtension(extensions, "key",
					option);
			if (extension != null) {
				ISysAttachmentJGAddtionFunction tmpFun = Plugin.getParamValue(
						extension, JG_FUN_NAME);
				tmpFun.execute(new RequestContext(request), response);
			} else {
				logger.warn("金格附加操作特性‘" + option + "’不存在");
			}
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	// 执行jg函数特性，供金格后台调用
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		iMsgServer2000 MsgObj = new iMsgServer2000(); // 创建信息包对象
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			if (logger.isInfoEnabled()) {
				logger.info("非Post提交表单");
			}
			MsgObj.MsgError("请使用Post方法");
			MsgObj.MsgTextClear(); // 清除所有变量
			MsgObj.MsgFileClear();
			MsgObj.Send(response);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return;
		}
		IExtension[] extensions = Plugin.getExtensions(JG_POINT, "*",
				JG_FUN_ITEM);
		// MsgObj.MsgVariant(IOUtils.toByteArray(request.getInputStream())); //
		// 设置信息包内容
		MsgObj.Load(request);
		if ("DBSTEP".equalsIgnoreCase(MsgObj.GetMsgByName("DBSTEP"))) { // 如果是合法的信息包
			String option = MsgObj.GetMsgByName("OPTION");
			if (logger.isDebugEnabled()) {
				logger.info("OPTION:" + option + "; RECORDID:"
						+ MsgObj.GetMsgByName("RECORDID") + "; fdModelId:"
						+ MsgObj.GetMsgByName("_fdModelId"));
			}
			IExtension extension = Plugin.getExtension(extensions, "key",
					option);
			if (extension != null) {
				ISysAttachmentJGFunction tmpFun = Plugin.getParamValue(
						extension, JG_FUN_NAME);
				tmpFun.execute(new RequestContext(request), MsgObj);
			} else {
				logger.warn("金格特性‘" + option + "’未做支持！");
				MsgObj.MsgError("金格特性‘" + option + "’未做支持！");
			}
		}
		// IOUtils.write(MsgObj.MsgVariant(), response.getOutputStream());
		MsgObj.Send(response);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	/**
	 * 获取htm文件
	 */
	public static File getFile(String id, String fileName) {
		return JGFilePathUtil.getFile(id, null, fileName);
	}

	/**
	 * 批量迁移html文件到新目录中
	 */
	public static void batchUpdateHtmlFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String root = JGFilePathUtil.getHomeFilesPath();
		File dir = new File(root);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (StringUtil.isNotNull(name)
						&& name.toLowerCase().endsWith(".htm")) {
                    return true;
                }
				return false;
			}
		});
		for (File file : files) {
			String fdId = file.getName();
			fdId = fdId.substring(0, fdId.indexOf("."));
			File htmAtts = new File(root + JGFilePathUtil.SEPARATOR + fdId
					+ ".files");
			File newFileDir = new File(JGFilePathUtil.genFilePath(fdId, null));
			if (!newFileDir.exists()) {
				newFileDir.mkdirs();
			}
			FileUtil.copy(file.getAbsolutePath(), newFileDir.getAbsolutePath()
					+ JGFilePathUtil.SEPARATOR + file.getName());
			file.delete();
			if (htmAtts.exists()) {
				FileUtil.copyDir(htmAtts.getAbsolutePath(), newFileDir
						.getAbsolutePath()
						+ JGFilePathUtil.SEPARATOR + fdId + ".files");
				FileUtil.deleteDir(htmAtts);
			}
		}
	}

}
