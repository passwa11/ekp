/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.template;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * 抽象模版标签类，主要完成模版内容的加载请求
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractTemplateTag extends BaseTag {

	public static final String DEFAULT_ENCODING = "UTF-8";

	protected String file;

	public void setFile(String file) {
		this.file = file;
	}

	protected String charEncoding;

	public void setCharEncoding(String charEncoding) {
		this.charEncoding = charEncoding;
	}

	@Override
	protected void clearResource() {
		this.file = null;
		this.charEncoding = null;
		super.clearResource();
	}

	protected String postTargetUrl(String targetUrl) {
		return targetUrl;
	}

	protected String getFile() throws JspTagException {
		return file;
	}

	protected String acquireString() throws JspException {
		String targetUrl = getFile();
		targetUrl = postTargetUrl(TargetUrlContentAcquirer.coverUrl(targetUrl,
				pageContext));
		return new TargetUrlContentAcquirer(targetUrl, pageContext,
				charEncoding).acquireString();
	}

	@SuppressWarnings("all")
	public static class ParamManager {

		private List names = new LinkedList();
		private List values = new LinkedList();
		private boolean done = false;

		public void addParameter(String name, String value) {
			if (done)
				throw new IllegalStateException();
			if (name != null) {
				names.add(name);
				if (value != null)
					values.add(value);
				else
					values.add("");
			}
		}

		public String aggregateParams(String url, String charEncoding)
				throws JspTagException {
			if (done)
				throw new IllegalStateException();
			done = true;

			StringBuffer newParams = new StringBuffer();
			for (int i = 0; i < names.size(); i++) {

				try {
					newParams.append(names.get(i)
							+ "="
							+ URLEncoder.encode(values.get(i).toString(),
									charEncoding));
				} catch (UnsupportedEncodingException e) {
					throw new JspTagException(e);
				}
				if (i < (names.size() - 1))
					newParams.append("&");
			}

			if (newParams.length() > 0) {
				int questionMark = url.indexOf('?');
				if (questionMark == -1) {
					return (url + "?" + newParams);
				} else {
					StringBuffer workingUrl = new StringBuffer(url);
					workingUrl.insert(questionMark + 1, (newParams + "&"));
					return workingUrl.toString();
				}
			} else {
				return url;
			}
		}
	}

}
