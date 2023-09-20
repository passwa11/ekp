package com.landray.kmss.sys.portal.cloud.parser;

/**
 * { "max":100, "min":0, "help":"{messageKey，配置说明}" }
 * 
 * @author chao
 *
 */
public class IntParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "int";
	}
}
