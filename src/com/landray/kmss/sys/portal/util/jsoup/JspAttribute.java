package com.landray.kmss.sys.portal.util.jsoup;

import java.nio.charset.CharsetEncoder;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;

/**
 * jsoup从1.6升级到1.14后，不会对key进行转小写处理，因此不再需要重写{@link #html()}方法
 * <br/>
 * 如果有用到业务，可以直接替换成 {@link Attribute}
 * @Author 陈进科
 * @date 2022-04
 */
@Deprecated
public class JspAttribute extends Attribute {
	private String _key;

	public JspAttribute(String key, String value) {
		this(key,value,null);
	}

	public JspAttribute(String key, String value, Attributes parent) {
		super(key,value,parent);
		this._key = key.trim();
	}

	@Override
	public void setKey(String key) {
		super.setKey(key);
		this._key = key.trim();
	}

	@Override
	public String html() {
		String s = super.html();
		int x = s.indexOf("=");
		return this._key + s.substring(x, s.length());
	}

	protected void html(StringBuilder accum, Document.OutputSettings out) {
		accum.append(this._key)
				.append("=\"")
				.append(escape(this.getValue(), out.charset().newEncoder(),
						out.escapeMode())).append("\"");
	}

	private String escape(String string, CharsetEncoder encoder,
						  EscapeMode escapeMode) {
		// 和StringEscapeUtils.escapeHtml(string)的效果差不多，而且前端还有很多类似的方法，先注释掉 #92373
		// zqx 20191226
		/*
		 * StringBuilder accum = new StringBuilder(string.length() * 2); Map map
		 * = escapeMode.getMap();
		 *
		 * for (int pos = 0; pos < string.length(); ++pos) { Character c =
		 * Character.valueOf(string.charAt(pos)); if (map.containsKey(c))
		 * accum.append('&').append((String) map.get(c)).append(';'); else if
		 * (encoder.canEncode(c.charValue())) accum.append(c.charValue()); else
		 * { accum.append("&#").append(c.charValue()).append(';'); } }
		 */
		return string;
	}
}
