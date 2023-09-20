package com.landray.kmss.code.springmvc.trans;

import java.io.File;;

/**
 * 清理struts.xml
 */
public class DeleteXmlFile {
	private void scan(File dir, String path) throws Exception {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.startsWith(".")) {
				continue;
			}
			String filePath = path + "/" + fileName;
			if (file.isDirectory()) {
				scan(file, filePath);
			} else {
				if ("struts.xml".equals(fileName)
						|| "struts.4pda.xml".equals(fileName)) {
					context.deleteFile(filePath);
				}
			}
		}
	}

	private TransContext context;

	public DeleteXmlFile(TransContext context) {
		super();
		this.context = context;
	}

	public void start() throws Exception {
		scan(new File("WebContent/WEB-INF/KmssConfig"),
				"WebContent/WEB-INF/KmssConfig");
	}
}
