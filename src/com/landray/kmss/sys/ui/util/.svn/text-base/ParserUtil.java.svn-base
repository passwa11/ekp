package com.landray.kmss.sys.ui.util;

import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.util.StringUtil;

public class ParserUtil {
	/**
	 * 拼接部件的缩略图路径
	 * 
	 * @param preFix
	 * @param srcPath
	 * @return
	 */
	public static String concatThumb(String preFix, String srcPath) {
		String destPath = srcPath;
		if (StringUtil.isNotNull(preFix) && StringUtil.isNotNull(srcPath)) {
			if (srcPath.startsWith("/")) {
				destPath = XmlReaderContext.SYSPORTALUI + preFix + srcPath;
			} else {
				destPath = XmlReaderContext.SYSPORTALUI + preFix + "/"
						+ srcPath;
			}
		}
		return destPath;
	}

	/**
	 * 拼接部件的file、design的路径
	 * 
	 * @param preFix
	 * @param srcPath
	 * @return
	 */
	public static String concatPath(String preFix, String srcPath) {
		String destPath = srcPath;
		if (StringUtil.isNotNull(preFix) && StringUtil.isNotNull(srcPath)) {
			if (srcPath.startsWith("/")) {
				destPath = XmlReaderContext.SYSPORTALUI + preFix + "/jsp"
						+ srcPath;
			} else {
				destPath = XmlReaderContext.SYSPORTALUI + preFix + "/"
						+ srcPath;
			}
		}
		return destPath;
	}

}
