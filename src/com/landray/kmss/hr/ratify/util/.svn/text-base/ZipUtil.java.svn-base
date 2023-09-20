package com.landray.kmss.hr.ratify.util;

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

import com.landray.kmss.util.StringUtil;

public class ZipUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ZipUtil.class);

	public static void unZip(String sourcePath) throws Exception {

		unZip(sourcePath, null);

	}

	public static void unZip(String sourcePath, String targetDir)
			throws IOException {
		File file = new File(sourcePath);

		if (!file.exists()) {
            return;
        }

		if (StringUtil.isNull(targetDir)) {
			unZip(file, file.getParentFile().getAbsolutePath());
			return;
		}
		unZip(file, targetDir);
	}

	/**
	 * 将sourceFile解压到targetDir
	 * 
	 * @param sourceFile
	 * @param targetDir
	 * @throws RuntimeException
	 */
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
				if (entry.getName().contains("..\\") || entry.getName().contains("../")) {
					logger.error("该文件名存在任意路径访问写法，解压失败：" + entry.getName());
					continue;
				}
				if (entry.isDirectory()) {
					continue;
				}
				input = zip.getInputStream(entry);
				file = new File(destination,
						entry.getName().replace('\\', '/'));
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

	public static void deleteFile(File file) {
		// 判断是否为文件
		if (file.isFile()) { // 为文件时调用删除文件方法
			if (file.exists()) {
				file.delete();
			}
		} else if (file.isDirectory()) { // 为目录时调用删除目录方法
			deleteDir(file);
		}
	}

	private static void deleteDir(File file) {
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	        	files[i].delete(); 
	        } //删除子目录  
	        else if(file.isDirectory()){  
				deleteDir(files[i]);
	        }  
	    }
		
	}
}
