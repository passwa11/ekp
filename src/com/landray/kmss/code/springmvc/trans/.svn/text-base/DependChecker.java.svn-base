package com.landray.kmss.code.springmvc.trans;

import java.io.IOException;

/**
 * 检查遗留struts的依赖
 */
public class DependChecker extends FileTranser {
	private String[] FILESUFFIX = { ".xml", ".jsp", ".java" };

	@Override
	public Result execute(TransContext context) throws Exception {
		for (String s : FILESUFFIX) {
			if (context.getPath().endsWith(s)) {
				doCheck(context, "org.apache.struts.");
				doCheck(context, "org.springframework.web.struts.");
				return Result.Continue;
			}
		}
		return Result.Continue;
	}

	private void doCheck(TransContext context, String pack) throws IOException {
		String content = context.getContent();
		String path = context.getPath();
		int begin = 0;
		while (true) {
			int index = content.indexOf(pack, begin);
			if (index == -1) {
				break;
			}
			if (path.endsWith(".java")) {
				// java文件，判断是否注释
				int lineBegin = content.lastIndexOf("\r", index);
				if (lineBegin == -1) {
					lineBegin = content.lastIndexOf("\n", index);
				}
				String line = content.substring(lineBegin + 1, index).trim();
				if (line.startsWith("//") || line.startsWith("*")
						|| line.startsWith("/*")) {
					// 注释
					begin = index + 10;
					continue;
				}
			}
			StringBuffer sb = new StringBuffer();
			for (int i = index; i < content.length(); i++) {
				char c = content.charAt(i);
				if (isJavaChar(c)) {
					sb.append(c);
				} else {
					begin = i;
					break;
				}
			}
			context.addError(sb.toString(), path);
		}
	}

	private boolean isJavaChar(char c) {
		return c == '.' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z'
				|| c >= 'A' && c <= 'Z';
	}
}
