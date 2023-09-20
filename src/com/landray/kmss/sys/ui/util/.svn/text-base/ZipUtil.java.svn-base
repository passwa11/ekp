package com.landray.kmss.sys.ui.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ZipUtil.class);

	public static void unZip(File zipFile, String destination)
			throws IOException {
		ZipFile zip = new ZipFile(zipFile, "GBK");
		Enumeration<?> en = zip.getEntries();
		ZipEntry entry = null;
		byte[] buffer = new byte[8192];
		int length = -1;
		InputStream input = null;
		BufferedOutputStream bos = null;
		File file = null;
		try {
			while (en.hasMoreElements()) {
				entry = (ZipEntry) en.nextElement();
				if (entry.getName().contains("..\\")
						|| entry.getName().contains("../")) {
					logger.error("该文件名存在任意路径访问写法，解压失败：" + entry.getName());
					continue;
				}
				if (entry.isDirectory()) {
					continue;
				}
				input = zip.getInputStream(entry);
				file = new File(destination, entry.getName().replace('\\', '/'));
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				bos = new BufferedOutputStream(new FileOutputStream(file));
				try {
					while (true) {
						length = input.read(buffer);
						if (length == -1) {
                            break;
                        }
						bos.write(buffer, 0, length);
					}
				} finally {
					bos.close();
					input.close();
				}
			}
		} finally {
			zip.close();
		}

	}

	public static void main(String[] args) throws Exception {
		ZipUtil.unZip(new File("d:\\temp\\demo-6.30-20190809.zip"), "d:\\temp\\demo");
	}
}
