package com.landray.kmss.sys.portal.cloud;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;

public class VarKindParserFactory {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(VarKindParserFactory.class);

	private static final VarKindParserFactory INSTANCE = new VarKindParserFactory();

	private Map<String, IVarKindParser> parserMap = new HashMap<>(16);

	private static final String PARSER_BASE_PATH = "com.landray.kmss.sys.portal.cloud.parser";

	private static final String DOT = ".";

	private IVarKindParser newParser(String kind) {
		String className = getParserClassName(kind);
		try {
			return (IVarKindParser) com.landray.kmss.util.ClassUtils.forName(className).newInstance();
		} catch (Exception e) {
			logger.info("实例化解析器失败：" + className + "，忽略该解析器");
			return null;
		}
	}

	private String getParserClassName(String kind) {
		String[] kinds = kind.split("\\.");
		StringBuffer sb = new StringBuffer();
		for (String string : kinds) {
			sb.append(CloudPortalUtil.toUpperCaseFirstChar(string));
		}
		sb.append("Parser");
		return PARSER_BASE_PATH + DOT + sb.toString();
	}

	public static final VarKindParserFactory getInstance() {
		return INSTANCE;
	}

	public IVarKindParser getParser(String kind) {
		IVarKindParser parser = parserMap.get(kind);
		if (parser == null) {
			parser = newParser(kind);
			parserMap.put(kind, parser);
		}
		return parser;
	}

}
