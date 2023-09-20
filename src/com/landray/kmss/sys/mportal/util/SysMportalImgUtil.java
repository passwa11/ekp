package com.landray.kmss.sys.mportal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.mportal.model.SysMportalBgInfo;
import com.landray.kmss.sys.mportal.model.SysMportalLogoInfo;
import com.landray.kmss.sys.mportal.model.SysMportalPage;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.sys.ui.plugin.SysUiTools;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysMportalImgUtil {
	/****
	 * 以下为logo信息处理
	 ***/
	private static final String[] exts = new String[] { "png", "gif", "jpg",
			"jpeg", "ico" };
	
	public static final String LOGO_FILE_NAME = "/mlogo/";
	
	public static final String BG_FILE_NAME = "/mbg/";
	
	public static void saveImg(InputStream input, String fileName, String name)
			throws Exception {
		if(StringUtil.isNull(fileName)) {
			fileName = LOGO_FILE_NAME;
		} 
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + fileName);
		if (!file.exists()) {
			file.mkdirs();
		}

		boolean isOk = false;
		String ext = FilenameUtils.getExtension(name);

		for (int j = 0; j < exts.length; j++) {
			if (exts[j].equalsIgnoreCase(ext)) {
				isOk = true;
				break;
			}
		}
		if (isOk == false) {
			throw new Exception("文件类型错误，只能是jpg,gif,png格式");
		}
		file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + fileName + IDGenerator.generateID()
				+ "." + ext);
		FileOutputStream output = null;
		try {
			file.createNewFile();
			output = new FileOutputStream(file);
			IOUtils.copy(input, output);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	public static void deleteImgs(String srcs) throws Exception {
		String[] srcArr = srcs.split("\\s*[;]\\s*");
		if(srcArr != null && srcArr.length > 0) {
			for(String src : srcArr) {
				if("/resource/images/logo.png".equals(src)) {
					continue;
				}
				String fileName = ResourceUtil.KMSS_RESOURCE_PATH + "/" + src;
				File file = new File(fileName);
				if(file.exists()) {
					file.delete();
				}
			}
		}
	}

	public static List<String> scanImgPath(String fileName) {
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + fileName );
		List<String> list = new ArrayList<String>();
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if ((SysUiTools.isImageFile(FilenameUtils
							.getExtension(files[i].getName())))) {
						list.add("/" + XmlReaderContext.UIEXT + fileName
								+ files[i].getName());
					}
				}
			}
		} else {
			file.mkdirs();
		}
		return list;
	}

	private static final String defaulLogoUrl = "/sys/mportal/resource/logo.png";

	@SuppressWarnings("unchecked")
	public static String logo() throws Exception {
		Map<String, String> config = ((ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService"))
				.findByKey(SysMportalLogoInfo.class.getName());
		String logoUrl = config.get("logoUrl");
		return StringUtil.isNull(logoUrl) ? defaulLogoUrl : logoUrl;
	}
	
	public static String logo(String pageId) throws Exception {
		// TODO 缓存
		ISysMportalPageService sysMportalPageService = (ISysMportalPageService) SpringBeanUtil
				.getBean("sysMportalPageService");
		SysMportalPage page = (SysMportalPage) sysMportalPageService.findByPrimaryKey(pageId);
		if (page != null && StringUtil.isNotNull( page.getFdLogo())) {
			return page.getFdLogo();
		}
		return logo();
	}
	
	private static final String defaulBgUrl = "/sys/mportal/mobile/img/bg.jpg";
	
	public static String bgImg() throws Exception {
		String img = appConfig("bgUrl");
		return StringUtil.isNull(img) ? defaulBgUrl : img;
	}
	
	
	public static String bgColor() throws Exception {
		String color = appConfig("fontColor");
		return StringUtil.isNull(color) ? "#ffffff" : color;
	}
	
	@SuppressWarnings("unchecked")
	private static String appConfig(String type) throws Exception { 
		Map<String, String> config = ((ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService"))
				.findByKey(SysMportalBgInfo.class.getName());
		String configType = config.get(type);
		return configType;
	}
	
}
