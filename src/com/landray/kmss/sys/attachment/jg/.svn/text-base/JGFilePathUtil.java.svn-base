package com.landray.kmss.sys.attachment.jg;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.attachment.util.ConsistentHash;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class JGFilePathUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JGFilePathUtil.class);

	public static final String SEPARATOR = "/";

	private static final String[] jg_extendPath = { "extends1", "extends2",
			"extends3", "extends4", "extends5", "extends6" };

	private static final ConsistentHash<String> filePathHash = new ConsistentHash<String>(
			(Collection<String>) ArrayUtil.convertArrayToList(jg_extendPath));

	/**
	 * 获取金格控件文件根目录
	 */
	public static String getHomeFilesPath() {
		String dir = ResourceUtil.KMSS_RESOURCE_PATH + SEPARATOR + "JG_HTML";
		try {
			FileUtil.createDir(dir, true);
		} catch (Exception ex) {
		}
		return dir;
	}

	/**
	 * 根据fdId及文件名，获取对应的文件或目录。 文件先在JG_HTML目录或JG_HTML/JG_Document目录下查找，
	 * 如果没有就直接根据id计算出日期的年月信息组成的目录查找 如果id反向解析时间出错，则按照hash散列查找。
	 * 
	 * @param id
	 *            文档参考ID pathFix 子目录 fileName 文件或目录名
	 */
	public static File getFile(String id, String pathFix, String fileName) {
		String root = getHomeFilesPath();
		Date cteated = IDGenerator.getIDCreateTime(id);
		Calendar cal = Calendar.getInstance();
		cal.setTime(cteated);
		File file = new File(root + SEPARATOR + cal.get(Calendar.YEAR)
				+ SEPARATOR + (cal.get(Calendar.MONTH) + 1) + SEPARATOR
				+ cal.get(Calendar.DATE) + SEPARATOR + fileName);
		if (!file.exists()) {
			try {
				if (StringUtil.isNotNull(pathFix)) {
					root = root + SEPARATOR + pathFix;
				}
				file = new File(root + SEPARATOR + fileName);

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug("解析文档ID出错，将采用散列式存储，解析出错id为：" + id + ",错误信息："
							+ e);
				}
				file = new File(root + SEPARATOR + filePathHash.get(id)
						+ fileName);
				return file;
			}
		}
		return file;
	}

	/**
	 * 根据fdId，计算生成文件目录路径
	 */
	public static String genFilePath(String id, String pathFix) {
		String root = getHomeFilesPath() + SEPARATOR;
		if (StringUtil.isNotNull(pathFix)) {
			root = root + pathFix + SEPARATOR;
		}
		try {
			Date cteated = IDGenerator.getIDCreateTime(id);
			Calendar cal = Calendar.getInstance();
			cal.setTime(cteated);
			return root + cal.get(Calendar.YEAR) + SEPARATOR
					+ (cal.get(Calendar.MONTH) + 1) + SEPARATOR
					+ cal.get(Calendar.DATE) + SEPARATOR;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("解析文档ID出错，将采用散列式存储路径，解析出错id为：" + id + ",错误信息："
								+ e);
			}
			return root + filePathHash.get(id) + SEPARATOR;
		}
	}
}
