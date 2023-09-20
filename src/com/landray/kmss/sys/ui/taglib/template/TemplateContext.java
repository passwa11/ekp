/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * 模版运行时上下文，区块变量等都通过此上下文传递，上下文并支持链式，支持相套使用模版。
 * 
 * @author 傅游翔
 * 
 */
public class TemplateContext {

	private static final String BLOCK_REPLACE_VARIBLE_KEY = "tag_template_block_replace_varible";

	public enum Status {
		INCLUDE, PARSE
	}

	private Status status;

	public Status getStatus() {
		return this.status;
	}

	public TemplateContext asInclude() {
		this.status = Status.INCLUDE;
		return this;
	}

	public TemplateContext asParse() {
		this.status = Status.PARSE;
		return this;
	}

	public boolean isInclude() {
		return Status.INCLUDE.equals(this.status);
	}

	public boolean isParse() {
		return Status.PARSE.equals(this.status);
	}

	public TemplateContext() {
		asParse();
	}

	private static TemplateContext safeGet(PageContext context) {
		final String key = BLOCK_REPLACE_VARIBLE_KEY;
		final int scope = PageContext.REQUEST_SCOPE;
		return (TemplateContext) context.getAttribute(key, scope);
	}

	private static void safePut(PageContext context, TemplateContext tc) {
		final String key = BLOCK_REPLACE_VARIBLE_KEY;
		final int scope = PageContext.REQUEST_SCOPE;
		context.setAttribute(key, tc, scope);
	}

	public static boolean hasContext(final PageContext context) {
		return (safeGet(context) != null);
	}

	public static TemplateContext get(final PageContext context) {
		final TemplateContext tc = safeGet(context);
		if (tc == null) {
			throw new RuntimeException("TemplateContext is not exist !");
		}
		return tc;
	}

	private TemplateContext preContext = null;

	private Map<String, JspBlock> context = new HashMap<String, JspBlock>();

	public JspBlock getJspBlock(String blockName) {
		JspBlock block = context.get(blockName);
		if (block == null) {
			block = new JspBlock();
			context.put(blockName, block);
		}
		return block;
	}

	public void putReplaceBlock(String blockName, String replaceContext) {
		JspBlock block = getJspBlock(blockName);
		block.setBlockContent(replaceContext);
	}

	public JspBlock getReplaceBlock(String blockName) {
		JspBlock block = context.get(blockName);
		if (block == null) {
			throw new RuntimeException("没有名称为'" + blockName + "'的Block!");
		}
		return block;
	}

	public boolean hasReplaceBlock(String blockName) {
		return context.containsKey(blockName);
	}

	public boolean isIncludeSuper(String blockName) {
		JspBlock block = context.get(blockName);
		return block != null && block.isIncludeSuper();
	}

	public void release(final PageContext context) {
		final TemplateContext ptc = this.preContext;
		if (ptc != null) {
			safePut(context, ptc);
		}
	}

	public void bind(final PageContext context) {
		final TemplateContext ptc = safeGet(context);
		if (ptc != null) {
			this.preContext = ptc;
		}
		safePut(context, this);
	}

}
