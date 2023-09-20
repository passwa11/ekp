/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.DynamicAttributes;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.web.filter.GZipFilter;

/**
 * 模版使用标签，使用模版时需要调用此标签
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class IncludeTag extends IncludeTempate implements DynamicAttributes {

	/**
	 * admin.do是否开启压缩
	 */
	private static boolean enableGZip = false;

	/**
	 * 最小压缩
	 */
	private static long gzipMinSize;

	static {
		gzipMinSize = GZipFilter.getMinSize();
		enableGZip = GZipFilter.isEnable();
	}

	protected ParamManager params = null;

	/**
	 * 是否走压缩
	 */
	protected String gzip = "false";

	public void setGzip(String gzip) {
		this.gzip = gzip;
	}

	protected String ref = null;

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	protected void clearResource() {
		this.ref = null;
		this.file = null;
		this.params = null;
		this.gzip = null;
		super.clearResource();
	}

	@Override
	protected String getFile() throws JspTagException {
		if (file == null && ref == null) {
			throw new JspTagException("file 同 ref 属性不可以同时为空!");
		}
		if (file == null) {
			// build file from ref
			file = SysUiPluginUtil.getTemplateById(ref).getFdFile();
		}
		if (this.params != null) {
			String ce = charEncoding;
			if (ce == null) {
				ce = DEFAULT_ENCODING;
			}
			file = params.aggregateParams(file, ce);
		}
		return file;
	}

	@Override
    public int doStartTag() throws JspException {
		if (!isByInclude()) {
			(new TemplateContext()).bind(pageContext);

		} else {
			getTemplateContext().asParse();
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
    public int doEndTag() throws JspException {

		try {
			getTemplateContext().asInclude();
			String value = acquireString();
			printString(value);
		} finally {
			getTemplateContext().release(pageContext);
		}

		return super.doEndTag();
	}

	private void gzip(byte[] content) throws IOException {
		
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		response.setHeader(GZipFilter.CONTENT_ENCODING, "gzip");
		response.addHeader("Vary", GZipFilter.ACCEPT_ENCODING);
		ServletOutputStream out = response.getOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
		pageContext.getOut().clear();
		gzipOutputStream.write(content);
		gzipOutputStream.close();
	}

	@Override
	protected void printString(String str) throws JspException {
		try {

			Boolean isGzip = false;
			// 内容压缩
			if ("true".equals(gzip) && enableGZip) {
				byte[] content = str.getBytes();
				if (isCache(content.length)) {
					isGzip = true;
					gzip(content);
				}
			}
			// 不做压缩
			if (!isGzip) {
				super.printString(str);
			}

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("压缩JSP内容出错", e);
			}
		}
	}

	@Override
    public void setDynamicAttribute(String uri, String key, Object value) throws JspException {
		if (this.params == null) {
			this.params = new ParamManager();
		}
		if (value != null) {
			value = value.toString();
		}
		this.params.addParameter(key, (String) value);
	}

	private boolean isCache(long size) {

		if (size > gzipMinSize) {
			return true;
		}
		return false;
	}

}
