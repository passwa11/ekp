package com.landray.kmss.sys.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class IniUtil {

	public static Map<String, String> loadIniFile(File file) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStreamReader streamReader = null;
		BufferedReader reader = null;
		FileInputStream in = null; 
		try {
			// "EF BB BF"
			String bom = ((char) 65279) + "";
			in = new FileInputStream(file);
			streamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(streamReader);
			String line;
			int loop = 0;
			while ((line = reader.readLine()) != null) {
				if (loop == 0 && line.startsWith(bom)) {
					line = line.substring(bom.length());
					loop++;
				}
				if (line.matches(".*=.*")) {
					int i = line.indexOf('=');
					String name = line.substring(0, i).trim();
					String value = line.substring(i + 1).trim();
					map.put(name, value);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(streamReader);
			IOUtils.closeQuietly(reader);
		}
		return map;
	}
}
