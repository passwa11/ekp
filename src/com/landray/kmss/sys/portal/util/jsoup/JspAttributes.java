package com.landray.kmss.sys.portal.util.jsoup;

import org.jsoup.nodes.Attributes;

/**
 * jsoup从1.6升级到1.14后，不会对key进行转小写处理，因此不再需要重写{@link #html()}方法
 * <br/>
 * 如果有用到业务，可以直接替换成 {@link Attributes}
 * @Author 陈进科
 * @date 2022-04
 */
@Deprecated
public class JspAttributes extends Attributes {
	/*
	@Override
	public Attributes put(String key, String value) {
		JspAttribute attr = new JspAttribute(key, value);
		put(attr);//1.14.3以后这个方法将导致StackOverflow
		return this;
	}
	*/
}
