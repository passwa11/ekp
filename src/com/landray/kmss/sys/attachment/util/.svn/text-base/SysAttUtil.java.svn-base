package com.landray.kmss.sys.attachment.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttThirdService;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.ArrayUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.landray.kmss.util.SpringBeanUtil.getBean;

public class SysAttUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttUtil.class);

	private static final List<String> OFFICE_TYPES = new ArrayList<String>();

	private static final List<String> WPSOFFICE_TYPES = new ArrayList<String>();

	private static final List<String> CAD_TYPES = new ArrayList<String>();

	private static CacheConfig config = CacheConfig.get(SysAttUtil.class);

	public static KmssCache tokenCache = new KmssCache(SysAttUtil.class,config.setCacheType(3));

	/**
	 * 扩展点变量
	 * @param extension
	 * @return
	 */
	private static IExtension[] extensions;

	static {
		OFFICE_TYPES.add("doc");
		OFFICE_TYPES.add("docx");
		OFFICE_TYPES.add("xls");
		OFFICE_TYPES.add("xlsx");
		OFFICE_TYPES.add("ppt");
		OFFICE_TYPES.add("pptx");
		OFFICE_TYPES.add("pdf");

		WPSOFFICE_TYPES.add("doc");
		WPSOFFICE_TYPES.add("docx");
		WPSOFFICE_TYPES.add("wps");
		WPSOFFICE_TYPES.add("xls");
		WPSOFFICE_TYPES.add("xlsx");
		WPSOFFICE_TYPES.add("et");
		WPSOFFICE_TYPES.add("ppt");
		WPSOFFICE_TYPES.add("pptx");
		WPSOFFICE_TYPES.add("dps");
		WPSOFFICE_TYPES.add("pdf");

		CAD_TYPES.add("dwg");
		CAD_TYPES.add("dwx");
		CAD_TYPES.add("dxf");

		extensions = Plugin.getExtensions(
				"com.landray.kmss.sys.attachment.third.service",
				"*","service");
	}

	/**
	 *
	 * 根据扩展点convert参数值获取对应扩展点<br/>
	 * 仅限point-id=com.landray.kmss.sys.attachment.third.service的扩展点
	 * @param extensionConvert
	 * @return
	 */
	private static IExtension getExtensionByConvert(String extensionConvert) {
		if(extensions != null){
			for (IExtension extension : extensions) {
				String convert = Plugin.getParamValueString(
						extension, "convert");
				if (extensionConvert.equals(convert)) {
					return extension;
				}
			}
		}
		logger.warn("没有获取到【point_id={},convert={}】的扩展点",
				"com.landray.kmss.sys.attachment.third.service", extensionConvert);
		return null;
	}

	/**
	 * 判断是否为Office文档
	 * 
	 * @param type
	 *            文件扩展名
	 * @return
	 */
	public static final boolean isOfficeType(String type) {
		type = type.toLowerCase();
		for (String imgType : OFFICE_TYPES) {
			if (imgType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为Office文档,并且使用wps查看
	 * 
	 * @param type
	 *            文件扩展名
	 * @return
	 */
	public static final boolean isOfficeTypeByWps(String type) {
		type = type.toLowerCase();
		for (String imgType : WPSOFFICE_TYPES) {
			if (imgType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isCADType(String type) {
		type = type.toLowerCase();
		for (String imgType : CAD_TYPES) {
			if (imgType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	// 是否支持大附件
	public static boolean isSupportAttLarge() {
		String isSupportAttLarge = ResourceUtil
				.getKmssConfigString("sys.att.useBigAtt");
		if (isSupportAttLarge != null
				&& "true".equals(isSupportAttLarge.trim())) {
            return false;
        }
		return false;
	}

	public static String getSubjectByModel(Object mainModel, String defaultName) {
		String filename = defaultName;
		if (mainModel != null) {
			String modelInfo = ModelUtil.getModelClassName(mainModel);
			if (StringUtil.isNotNull(modelInfo)) {
				modelInfo = SysDataDict.getInstance().getModel(modelInfo)
						.getDisplayProperty();
				if (StringUtil.isNotNull(modelInfo)) {
					if ("fdId".equals(modelInfo)) {
						return "";
					}
					try {
						filename = String.valueOf(PropertyUtils.getProperty(
								mainModel, modelInfo));
					} catch (Exception e) {
						try {
							logger.warn("获取主文档标题出错,后续尝试默认属性docSubject:", e);
							filename = String.valueOf(PropertyUtils
									.getProperty(mainModel, "docSubject"));
						} catch (Exception ex) {
							try {
								logger.warn("获取主文档标题出错,后续尝试属性fdName:", ex);
								filename = String.valueOf(PropertyUtils
										.getProperty(mainModel, "fdName"));
							} catch (Exception et) {

							}
						}
					}
				}
			}
		}
		return filename;
	}

	/*
	 * 获取文件的编码格式
	 */
	public static String getCharset(InputStream in) throws IOException {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(in);
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0) {
                        break;
                    }
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                    {
                        break;
                    }
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else {
                                break;
                            }
						} else {
                            break;
                        }
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	/**
	 * 图片转base64字符串
	 * @param imgFile  图片路径
	 * @return
	 */
	public static String imageToBase64Str(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			int count = 0;
			while((count = inputStream.read(data)) > 0) {
				// 无逻辑
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					logger.error("error:", e);
				}

			}
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}


	/**
	 * 图片转base64字符串
	 * @return
	 */
	public static String imageToBase64(HttpServletRequest request) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = SysAttViewerUtil.getWaterMarkPNG(SysAttViewerUtil.getWaterMarkConfigInRequest(request, "get"));
			data = new byte[inputStream.available()];
			int count = 0;
			while ((count = inputStream.read(data)) > 0) {
                   // 无逻辑
			}
		} catch (Exception e) {
          logger.error("error:", e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					logger.error("error:", e);
				}

			}
		}

		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}


	private static ISysAttMainCoreInnerService sysAttMainService;
	private static ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
		return sysAttMainService;
	}

	/**
	 * 生成url，根据admin.do隐藏配置判断是否生成直链。
	 * 若存储服务不支持则仍为ekp下载链接。
	 * @param sysAttMain 附件信息
	 * @param convert 具体业务服务名称（如wps中台、foxit、dianju.etc）
	 * @param additionInfo 附加信息，用于弥补业务服务入参差距
	 * @return
	 * @throws Exception
	 */
	public static String generateDownloadUrl(SysAttMain sysAttMain, String convert, Map<String, String> additionInfo) {
		String downloadUrl = "";
		if(sysAttMain == null){
			logger.info("附件信息为空无法生成链接");
			return "";
		}
		//文件信息
		SysAttFile file;
		try {
			file = getSysAttMainService().getFile(sysAttMain.getFdId());
		} catch (Exception e) {
			logger.error("获取文件信息SysAttFile失败[sysAttMainId={}]", sysAttMain.getFdId(), e);
			return downloadUrl;
		}
		//获取直连服务
		ISysFileLocationDirectService directService = SysFileLocationUtil.getDirectService(file.getFdAttLocation());
		IExtension extension = getExtensionByConvert(convert);
		//存储服务（阿里云oss）是否支持提供服务器直链
		boolean isSupportServiceDirect = directService.isSupportServiceDirect();
		if(extension != null){
			//具体集成服务业务是否能使用直链。已知福昕的交互逻辑不能使用直链
			Boolean isSupportDirect = Plugin.getParamValue(extension, "isSupportDirect");
			isSupportServiceDirect = isSupportServiceDirect && isSupportDirect;
		}
		if (isSupportServiceDirect) {// 支持直连时直接返回
			try {
				downloadUrl = directService.getDownloadUrl(file.getFdFilePath(), sysAttMain.getFdFileName());
			} catch (Exception e) {
				logger.error("获取直连链接失败[sysAttMainId={}]", sysAttMain.getFdId(), e);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("获取到的直连信息[sysAttMainId = {}, convert = {}, directUrl = {}]", sysAttMain.getFdId(), convert, downloadUrl);
			}
			return downloadUrl;
		}
		//不支持直连，交由服务处理
		if(extension == null){
			return null;
		}
		ISysAttThirdService thirdService = Plugin.getParamValue(extension, "bean");
		downloadUrl = thirdService.generateDownloadUrl(sysAttMain, additionInfo);

		if(logger.isDebugEnabled()) {
			logger.debug("获取到的代理链接信息[sysAttMainId = {}, convert = {}, proxyUrl = {}]", sysAttMain.getFdId(), convert, downloadUrl);
		}
		return downloadUrl;
	}


	/**
	 * 文件写出
	 * @param response
	 * @throws Exception
	 */
	public static void outputFile(HttpServletResponse response,SysAttMain sysAttMain) throws Exception{
		ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
				SpringBeanUtil.getBean("sysAttUploadService");
		SysAttFile attFile = sysAttUploadService.getFileById(sysAttMain.getFdFileId());
		InputStream in = null;
		OutputStream out = null;
		try {
			response.setContentType(sysAttMain.getFdContentType());
			response.setHeader("Content-Disposition",
					"inline;filename=\"" +
							new String(sysAttMain.getFdFileName().getBytes("utf-8"),"ISO8859-1") + "\"");
			if(logger.isDebugEnabled()) {
				logger.debug("请求下载文件，EKP文件所处位置:{}", attFile.getFdFilePath());
			}

			in = SysFileLocationUtil.getProxyService().readFile(attFile.getFdFilePath());

			if(in == null) {
				logger.error("文件流不存在，请求查检文件路径:{}", attFile.getFdFilePath());
			}

			out = response.getOutputStream();
			IOUtil.write(in, out);

		} catch (Exception e) {
			if(out != null) {
				IOUtils.closeQuietly(out);
			}
			if(in != null) {
				IOUtils.closeQuietly(in);
			}
			logger.error("Foxit下载EKP的文件出现异常:" + e.getStackTrace());
		} finally {
			if(out != null) {
				IOUtils.closeQuietly(out);
			}
			if(in != null) {
				IOUtils.closeQuietly(in);
			}
		}
	}

	/**
	 * #161059 PDF附件可以支持使用谷歌、火狐、Edge直接打开查看（先只开放给EIS的中小客户使用）
	 * @param sysAttMain
	 * @return
	 * @throws Exception
	 */
	public static  Boolean viewByBrowser(SysAttMain sysAttMain) throws Exception{
		return SysAttConfigUtil.pdfViewByBrowser()
				&& JgWebOffice.isPDF(sysAttMain.getFdFileName())
				&& "true".equals(LicenseUtil.get("license-eis-project"));
	}


	/**
	 * 为下载链接签名
	 *
	 * @param expires
	 * @param attMainId
	 * @return
	 * @throws Exception
	 */
	public static String getRestSign(String attMainId, long expires) throws Exception {
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


		Long time = Long.valueOf(expires) - System.currentTimeMillis();
		if(time>1000) { //大于1秒才设置redis
			tokenCache.put(sign, "false", time.intValue());
		}
		return sign;
	}
}
