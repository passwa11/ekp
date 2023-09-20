package com.landray.kmss.sys.portal.cloud.parser;

/**
 * { "help":"{messageKey，配置说明}" }
 * 
 * @author chao
 *
 */
public class StringParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "string";
	}
}
