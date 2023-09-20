package com.landray.kmss.code.springmvc.trans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 将java和jsp的一些关键字符，替换成springmvc框架的调用
 */
public class ReplaceFileTranser extends FileTranser {
	// 替换的struts类
	private String[] REPLACECLASS = {
			"action.ActionForward",
			"action.ActionRedirect",
			"action.ActionMapping",
			"action.ActionForm",
			"upload.FormFile",
			"action.ActionMessage",
			"action.ActionMessages",
			"action.ActionError",
			"action.ActionErrors",
			"Globals",
			"taglib.html.Constants",
			"taglib.TagUtils",
			"util.RequestUtils",
			"util.MessageResources",
			"action.LOCALE" };
	// 修订的Struts继承
	private String[] ACTIONEXTENDS = {
			"org.apache.struts.actions.LookupDispatchAction",
			"org.apache.struts.actions.DispatchAction" };
	// 修订的tld声明
	private Map<String, String> replaceMap;

	public ReplaceFileTranser() {
		replaceMap = new HashMap<String, String>();
		replaceMap.put("/WEB-INF/struts-html.tld", "/WEB-INF/kmss-html.tld");
		replaceMap.put("/WEB-INF/struts-bean.tld", "/WEB-INF/kmss-bean.tld");
		replaceMap.put("/WEB-INF/struts-logic.tld", "/WEB-INF/kmss-logic.tld");
		replaceMap.put("org.apache.struts.taglib.html.BEAN",
				"com.landray.kmss.web.taglib.FormBean");
	}

	@Override
	public Result execute(TransContext context) throws Exception {
		String path = context.getPath();
		if (path.endsWith(".jsp")) {
			doReplece(context);
		} else if (path.endsWith(".java")) {
			doReplece(context);
			doJavaFile(context);
		}
		return Result.Continue;
	}

	/**
	 * Java文件继承替换
	 */
	private void doJavaFile(TransContext context) throws IOException {
		String content = context.getContent();
		for (String ext : ACTIONEXTENDS) {
			int index = content.indexOf(ext);
			if (index > -1) {
				content = content.substring(0, index)
						+ "com.landray.kmss.common.actions.BaseAction"
						+ content.substring(index + ext.length());
				String sName = ext.substring(ext.lastIndexOf('.') + 1);
				content = content.replaceFirst(
						"(\\s)(extends\\s+" + sName + ")(\\W)",
						"$1extends BaseAction$3");
				break;
			}
		}
		context.setContent(content);
	}

	/** 通用替换 */
	private void doReplece(TransContext context) throws IOException {
		String content = context.getContent();
		// 通用替换
		for (String clz : REPLACECLASS) {
			content = replace(content, "org.apache.struts." + clz,
					"com.landray.kmss.web." + clz);
		}
		for (Entry<String, String> entry : replaceMap.entrySet()) {
			content = replace(content, entry.getKey(), entry.getValue());
		}
		context.setContent(content);
	}

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
}
