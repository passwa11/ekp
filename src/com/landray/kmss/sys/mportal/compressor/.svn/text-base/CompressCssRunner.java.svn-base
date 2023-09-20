package com.landray.kmss.sys.mportal.compressor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.mobile.compressor.CompressUtils;
import com.landray.kmss.sys.mobile.compressor.CssCompressTask;

public class CompressCssRunner implements ICompressRunner {

	@Override
	public void run(List<String> cssUrls) throws Exception {

		StringBuilder content = new StringBuilder(500);

		for (String cssUrl : cssUrls) {

			File file = new File(ConfigLocationsUtil.getWebContentPath() + cssUrl);

			CssCompressTask task = new CssCompressTask(null);
			List<String> fileContents = new ArrayList<String>();
			List<File> importFiles = new ArrayList<File>();
			importFiles.add(file);

			// 递归加载样式文件
			task.loadAllFiles(file, importFiles, fileContents);

			// 剔除import信息
			for (int i = 0; i < fileContents.size(); i++) {

				String c = fileContents.get(i);
				File f = importFiles.get(i);

				String nc = Pattern
						.compile(
								"\\@(import|IMPORT)\\s+(url\\()?\\s*([^);]+)\\s*(\\))?([\\w, ]*)(;)?")
						.matcher(c).replaceAll("");
				// 圖片轉64
				nc = task.url2Base64(f, nc);

				content.append(nc);
			}

		}

		File miniFile = new File(ConfigLocationsUtil.getWebContentPath()
				+ CompressUtils.getMiniFileName("/" + TARGET_PATH, "css"));
		try {

			CompressUtils.compressOneCSS(new StringReader(content.toString()), new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(miniFile), "UTF-8")));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
