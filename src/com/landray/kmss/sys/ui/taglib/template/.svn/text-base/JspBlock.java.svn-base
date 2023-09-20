/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import org.apache.commons.lang.StringUtils;

/**
 * 区块变量类，用以在运行时标识可替换区域内容
 * 
 * @author 傅游翔
 * 
 */
public class JspBlock {

	private static final String JSP_BLOCK_FLAG = "_"
			+ JspBlock.class.getSimpleName() + "@" + System.currentTimeMillis()
			+ "flag_";

	private String blockContent;

	public String getBlockContent() {
		if (blockContent == null) {
			return "";
		}
		return blockContent;
	}

	public void setBlockContent(String blockContent) {
		this.blockContent = blockContent;
	}

	public boolean isIncludeSuper() {
		if (blockContent == null) {
			return false;
		}
		return blockContent.indexOf(JSP_BLOCK_FLAG) > -1;
	}

	public JspBlock() {
		super();
	}

	public JspBlock(String blockContent) {
		super();
		this.blockContent = blockContent;
	}

	public void setSuperContent(String superContent) {
		if (superContent == null) {
			superContent = "";
		}
		if (blockContent == null) {
			return;
		}
		blockContent = StringUtils.replace(blockContent, JSP_BLOCK_FLAG,
				superContent);
		// blockContent = blockContent.replaceAll(JSP_BLOCK_FLAG, superContent);
	}

	public static final String getBlockFlag() {
		return JSP_BLOCK_FLAG;
	}

}
