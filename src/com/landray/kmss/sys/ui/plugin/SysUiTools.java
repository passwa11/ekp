package com.landray.kmss.sys.ui.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;

public class SysUiTools {
	private static final String[] exts = new String[] { "png", "gif", "jpg",
			"jpeg" , "ico" };

	/**
	 * 是否是图片
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImageFile(String ext) {
		if (ext == null) {
			return false;
		}
		for (int j = 0; j < exts.length; j++) {
			if (exts[j].equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 扫描图标CSS类名
	 * 
	 * @param type
	 * @param isStatus
	 * @return
	 * @throws IOException
	 */
	public static List<String> scanIconCssName(String type, boolean isStatus)
			throws IOException {
		return new SysUiIconScanner().scanIconCssName(type, isStatus);
	}

	/**
	 * 重新加载icon.css
	 * 
	 * @param theme
	 *            样式名，如：default
	 * @throws Exception
	 */
	public static String reloadIcon(String theme) throws Exception {
		return new SysUiIconScanner().reloadIcon(theme);
	}

	public static void saveLogo(InputStream input, String name)
			throws Exception {
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + "/logo");
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
			throw new Exception(ResourceUtil.getString("sys.ui.logo.error.tip","sys-ui"));
		}
		file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + "/logo/" + IDGenerator.generateID()
				+ "." + ext);
		FileOutputStream output = null;
		try {
			file.createNewFile();
			output = new FileOutputStream(file);
			IOUtils.copy(input, output);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
			}
		}
	}

	public static List<String> scanLogoPath() {
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT + "/logo");
		List<String> list = new ArrayList<String>();
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (isImageFile(FilenameUtils.getExtension(files[i]
							.getName()))) {
						list.add("/" + XmlReaderContext.UIEXT + "/logo/"
								+ files[i].getName());
					}
				}
			}
		} else {
			file.mkdirs();
		}
		return list;
	}

	public static void deleteLogo(String fileName) {
		String filePath = ResourceUtil.KMSS_RESOURCE_PATH + fileName;
		if (fileName.indexOf(XmlReaderContext.UIEXT) == -1) {
			return;
		}
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			if (isImageFile(FilenameUtils.getExtension(file.getName()))) {
				file.delete();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		reloadIcon("/sys/ui/extend/theme/default");
		// handleIcon(new File("d:/icon"));
		// System.out.println(scanCssName("l", false));
		System.out.println("OK!");
	}
}
