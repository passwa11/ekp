package com.landray.kmss.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyRunner {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private String path;

	private List<String> fromModules = new ArrayList<String>();

	private List<String> toModules = new ArrayList<String>();

	private List<String> fromStrings = new ArrayList<String>();

	private List<String> toStrings = new ArrayList<String>();

	private String replace(String srcText, String fromStr, String toStr) {
		if (srcText == null) {
            return null;
        }
		StringBuffer rtnVal = new StringBuffer();
		String rightText = srcText;
		for (int i = rightText.indexOf(fromStr); i > -1; i = rightText
				.indexOf(fromStr)) {
			rtnVal.append(rightText.substring(0, i));
			rtnVal.append(toStr);
			rightText = rightText.substring(i + fromStr.length());
		}
		rtnVal.append(rightText);
		return rtnVal.toString();
	}

	private String replaceStrings(String srcText) {
		String rtnVal = srcText;
		for (int i = 0; i < fromStrings.size(); i++) {
			rtnVal = replace(rtnVal, fromStrings.get(i), toStrings.get(i));
		}
		return rtnVal;
	}

	public CopyRunner(String project) {
		path = replace(new File("").getAbsolutePath(), "\\", "/");
		int i = path.lastIndexOf('/');
		path = path.substring(0, i) + "/" + project;
	}

	public CopyRunner(String project, String fromModule, String toModule) {
		this(project);
		addModule(fromModule, toModule);
	}

	public CopyRunner addModule(String fromModule, String toModule) {
		fromModules.add(fromModule);
		toModules.add(toModule);

		// km/review
		fromStrings.add(fromModule);
		toStrings.add(toModule);

		// kmss.km.review
		fromStrings.add("kmss." + fromModule.replace('/', '.'));
		toStrings.add("kmss." + toModule.replace('/', '.'));

		// km_review_
		fromStrings.add(fromModule.replace('/', '_') + "_");
		toStrings.add(toModule.replace('/', '_') + "_");

		// KmReview
		String fromUpper = upperFirst(fromModule);
		String toUpper = upperFirst(toModule);
		fromStrings.add(fromUpper);
		toStrings.add(toUpper);

		// kmReview
		fromStrings.add(Character.toLowerCase(fromUpper.charAt(0))
				+ fromUpper.substring(1));
		toStrings.add(Character.toLowerCase(toUpper.charAt(0))
				+ toUpper.substring(1));

		// KMREVIEW
		fromStrings.add(replace(fromModule.toUpperCase(), "/", ""));
		toStrings.add(replace(toModule.toUpperCase(), "/", ""));

		// km-review
		fromStrings.add(fromModule.replace('/', '-'));
		toStrings.add(toModule.replace('/', '-'));
		return this;
	}

	private String upperFirst(String path) {
		StringBuffer result = new StringBuffer();
		String[] infos = path.split("/");
		for (String info : infos) {
			result.append(Character.toUpperCase(info.charAt(0))
					+ info.substring(1));
		}
		return result.toString();
	}

	public void Run() throws Exception {
		for (int i = 0; i < fromModules.size(); i++) {
			CopyPath(path + "/src/com/landray/kmss/" + fromModules.get(i), path
					+ "/src/com/landray/kmss/" + toModules.get(i));
			CopyPath(path + "/WebContent/" + fromModules.get(i), path
					+ "/WebContent/" + toModules.get(i));
			CopyPath(path + "/WebContent/WEB-INF/KmssConfig/"
					+ fromModules.get(i), path
					+ "/WebContent/WEB-INF/KmssConfig/" + toModules.get(i));
		}
		logger.info("拷贝完成");
	}

	private void CopyPath(String fromPath, String toPath) throws Exception {
		File fromFile = new File(fromPath);
		if (!fromFile.exists()) {
			return;
		}
		File toFile = new File(toPath);
		logger.info("正在拷贝：" + fromPath + " -> " + toPath);
		if (fromFile.isDirectory()) {
			toFile.mkdir();
			String[] files = fromFile.list();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].startsWith(".")) {
                        continue;
                    }
					CopyPath(fromPath + "/" + files[i], toPath + "/"
							+ replaceStrings(files[i]));
				}
			}
		} else {
			StringBuffer sb = new StringBuffer();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fromFile), "UTF-8"));
			for (String s = input.readLine(); s != null; s = input.readLine()) {
                sb.append(s + "\r\n");
            }
			input.close();

			toFile.createNewFile();
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(toFile), "UTF-8"));
			output.write(replaceStrings(sb.toString()));
			output.close();
		}
	}

	public static void main(String[] args) throws Exception {
		new CopyRunner("ekp", "km/resource", "km/meetingres").Run();
	}
}
