package com.landray.kmss.sys.ui.taglib.chart;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

public class ChartPropertyTag extends BaseTag {
	public static class JSONScript extends JSONFunction {
		private static final long serialVersionUID = 1129095988585982311L;

		public JSONScript(String text) {
			super(text);
		}

		@Override
		public String toString() {
			return getText();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 194894522788723915L;
	private String name;
	private String merge;
	private String isScript;

	public String getMerge() {
		return merge;
	}

	public void setMerge(String merge) {
		this.merge = merge;
	}

	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsScript() {
		return isScript;
	}

	public void setIsScript(String isScript) {
		this.isScript = isScript;
	}

	public Object getContent() {
		if (value != null) {
			return value;
		} else {
			String content = bodyContent.getString();
			if (content == null) {
				return null;
			} else {
				return formatValue(content);
			}
		}
	}

	private Object formatValue(String value) {
		String text = value.trim();
		if ("true".equals(isScript)) {
			return new JSONScript(text);
		}
		if (text.startsWith("[")) {
			return JSONArray.fromObject(text);
		}
		if (text.startsWith("{")) {
			return JSONObject.fromObject(text);
		}
		return text;
	}

	private void setProperty(JSONObject target, Object value) {
		if (value == null) {
			return;
		}
		JSONObject tJson = target;
		String[] names = this.name.split("\\.");
		String key;
		int i = 0;
		for (; i < names.length; i++) {
			boolean last = i == names.length - 1;
			int index = names[i].indexOf('[');
			if (index > -1) {
				// 数组，但不支持多维
				key = names[i].substring(0, index);
				String s_index = names[i].substring(index + 1, names[i]
						.indexOf(']'));
				index = Integer.parseInt(s_index, 10);
				JSONArray array = (JSONArray) tJson.get(key);
				if (array == null) {
					tJson.put(key, new JSONArray());
					array = (JSONArray) tJson.get(key);
				}

				if (last) {
					// 最后一个，填充数据
					// 若下标不足，则补齐
					for (int j = 0; j <= index && j >= array.size(); j++) {
						array.add(null);
					}
					if ("false".equals(this.merge) || array.get(index) == null) {
						array.set(index, value);
					} else {
						if (value instanceof JSONObject) {
							merge((JSONObject) value, (JSONObject) array
									.get(index));
						} else {
							array.set(index, value);
						}
					}
				} else {
					// 否则用json填充
					// 若下标不足，则补齐
					for (int j = 0; j <= index && j >= array.size(); j++) {
						array.add(new JSONObject());
					}
					tJson = (JSONObject) array.get(index);
				}
			} else {
				key = names[i];
				if (last) {
					// 最后一个，填充数据
					if ("false".equals(this.merge) || tJson.get(key) == null) {
						tJson.element(key, value);
					} else {
						if (value instanceof JSONObject) {
							merge((JSONObject) value, tJson.getJSONObject(key));
						} else {
							tJson.element(key, value);
						}
					}
				} else {
					// 否则用json填充
					JSONObject json = (JSONObject) tJson.get(key);
					if (json == null) {
						tJson.put(key, new JSONObject());
						tJson = (JSONObject) tJson.get(key);
					} else {
						tJson = json;
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void merge(JSONObject from, JSONObject to) {
		for (Iterator it = from.keys(); it.hasNext();) {
			String key = (String) it.next();
			Object value = from.get(key);
			if (value == null) {
				to.element(key, value);
				continue;
			}
			if (value instanceof JSONObject) {
				JSONObject json = (JSONObject) to.get(key);
				if (json == null) {
					to.element(key, value);
				} else {
					merge((JSONObject) value, json);
				}
			} else {
				to.element(key, value);
			}
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			Tag parent = findAncestorWithClass(this, SeriesTag.class);
			if (parent != null) {
				setProperty(((SeriesTag) parent).seriesdata, getContent());
			} else {
				parent = findAncestorWithClass(this, ChartDataTag.class);
				if (parent != null) {
					setProperty(((ChartDataTag) parent).option, getContent());
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		this.name = null;
		this.merge = null;
		this.value = null;
		super.release();
	}

	public static void main(String[] args) {
		ChartPropertyTag tag = new ChartPropertyTag();
		JSONObject json1 = JSONObject.fromObject("{a:{b:{c:1}}}");
		JSONObject json2 = JSONObject.fromObject("{a:{d:{c:1}}}");
		tag.merge(json1, json2);
		System.out.println(json2.toString());
	}

}
