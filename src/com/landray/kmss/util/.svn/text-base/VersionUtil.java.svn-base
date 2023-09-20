package com.landray.kmss.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.version.Description;
import com.landray.kmss.util.version.Difference;
import com.landray.kmss.util.version.Modify;
import com.landray.kmss.util.version.VersionCheck;
import com.landray.kmss.util.version.VersionXMLUtil;

/**
 * 模块版本检测工具
 * <p>
 * 原代码逻辑在VersionUrlRedirectFilter中，因为要与三员管理结合处理，所以将原有的逻辑移到这里
 * 
 * @author 潘永辉 2018年8月31日
 *
 */
public class VersionUtil {

	public static void setAllDescription(HttpServletRequest httpRequest) {
		List<Description> descriptionList = new ArrayList<Description>();
		File[] descriptions = FileUtil.searchFiles(ConfigLocationsUtil
				.getKmssConfigPath(), "description.xml");
		if (descriptions != null && descriptions.length > 0) {
			for (File description : descriptions) {
				String desFilePath = description.getPath(); // description文件路径
				Description des = VersionXMLUtil.getInstance(desFilePath)
						.getDescriprion();
				if (des == null) {
					continue;
				}
				descriptionList.add(des);
			}
		}
		httpRequest.setAttribute("descriptionList", descriptionList);
	}

	/**
	 * 设置版本信息
	 * 
	 * @param httpRequest
	 * @param path
	 */
	public static void setVersion(HttpServletRequest httpRequest, String path) {
		String descriptionXml = ConfigLocationsUtil.getKmssConfigPath() + path
				+ "/version/description.xml";
		String versionXml = ConfigLocationsUtil.getKmssConfigPath() + path
				+ "/version/version.xml";
		File descFile = null;
		File versionFile = null;
		try {
			descFile = FileUtil.getFile(descriptionXml);
			versionFile = FileUtil.getFile(versionXml);
		} catch (Exception e) {
		}
		if ((descFile != null && versionFile != null && descFile.exists()
				&& versionFile
						.exists())) {
			// description.xml和version.xml两个文件都存在
			Description description = VersionXMLUtil
					.getInstance(descriptionXml).getDescriprion();
			List<Modify> modifyList = VersionXMLUtil.getInstance(versionXml)
					.getVersionModify();
			httpRequest.setAttribute("description", description);
			httpRequest.setAttribute("modifyList", modifyList);
		}
	}

	/**
	 * 版本检测
	 * 
	 * @param httpRequest
	 * @param path
	 */
	public static void versionCheck(HttpServletRequest httpRequest, String path,
			boolean isCore) {
		String descriptionXml = ConfigLocationsUtil.getKmssConfigPath() + path
				+ "/version/description.xml";
		String md5Txt = ConfigLocationsUtil.getKmssConfigPath() + path
				+ "/version/md5.txt";
		Description description = VersionXMLUtil.getInstance(descriptionXml)
				.getDescriprion();
		File md5File = null;
		try {
			md5File = FileUtil.getFile(md5Txt);
			if (!"是".equals(description.getModule().getIsCustom())) {
				if (md5File != null && md5File.exists()) {
					List<Difference> differences = VersionCheck.compareVersion(
							path, isCore);
					if (differences != null && differences.size() > 0) {
						httpRequest.setAttribute("differences", differences);
						httpRequest.setAttribute("prodSetupPath",
								(ConfigLocationsUtil.getWebContentPath())
										.substring(1));
					} else {
						httpRequest.setAttribute("error", "检测结果没有发现不同项");
					}
				} else {
					httpRequest.setAttribute("error", "该组件MD5丢失，无法进行检测");
				}
			} else {
				httpRequest.setAttribute("error", "该组件已经定制，无法进行检测，请到项目SVN中查看");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
