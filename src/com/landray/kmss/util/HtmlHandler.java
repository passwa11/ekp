package com.landray.kmss.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlHandler {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(HtmlHandler.class);

	/**
	 * 再次对html内容进行处理
	 * @param html
	 * @return
	 */
	public String handlingHTMLAgain(String html) {
		if (html == null || "".equals(html)) {
			logger.warn("html内容为空");
			return html;
		}
		//XForm编辑器
		html = handlingCKEditor(html);
		//px整体缩小
		html = handlingZoom(html, 0.7f);
		return html;
	}

	private String handlingZoom(String html, final float percent) {
		List<Replace> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder(html);

		//px 匹配浮点数+px、以及(空格、单引号、双引号、等于号、冒号)+正整数+px
		Pattern p = Pattern.compile("([1-9]\\d*\\.\\d*px|0\\.\\d*[1-9]\\d0*px| [1-9]\\d*px|'[1-9]\\d*px|\"[1-9]\\d*px|=[1-9]\\d*px|:[1-9]\\d*px)");
		Matcher m = p.matcher(html);
		while (m.find()) {
			String group = m.group();
			String prefix = "";
			String num = group.replace("px", "");
			int prelength = num.length();
			
			//XXX
			num = num.replace(" ", "");
			if (prelength != num.length()) {
				prefix = " ";
				prelength = num.length();
			}
			
			num = num.replace("'", "");
			if (prelength != num.length()) {
				prefix = "'";
				prelength = num.length();
			}
			
			num = num.replace("\"", "");
			if (prelength != num.length()) {
				prefix = "\"";
				prelength = num.length();
			}
			
			num = num.replace("=", "");
			if (prelength != num.length()) {
				prefix = "=";
				prelength = num.length();
			}

			num = num.replace(":", "");
			if (prelength != num.length()) {
				prefix = ":";
				prelength = num.length();
			}
			
			if (!"".equals(num)) {
				float fnum = Float.valueOf(num);
				if (fnum > 1) {
					float newnum = percent * fnum;
					list.add(new Replace(m.start(), m.end(), prefix + newnum + "px"));
				}
			}
		}

		//未设置单位的内容
		//height
		p = Pattern.compile("height=\"[0-9]+\"");
		m = p.matcher(html);
		while (m.find()) {
			String group = m.group();
			String num = group.replace("height=\"", "").replace("\"", "");
			if (!"".equals(num)) {
				float fnum = Float.valueOf(num);
				if (fnum > 1) {
					float newnum = percent * fnum;
					list.add(new Replace(m.start(), m.end(), "height=\"" + newnum + "\""));
				}
			}
		}

		//width
		p = Pattern.compile("width=\"[0-9]+\"");
		m = p.matcher(html);
		while (m.find()) {
			String group = m.group();
			String num = group.replace("width=\"", "").replace("\"", "");
			if (!"".equals(num)) {
				float fnum = Float.valueOf(num);
				if (fnum > 1) {
					float newnum = percent * fnum;
					list.add(new Replace(m.start(), m.end(), "width=\"" + newnum + "\""));
				}
			}
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			sb = sb.replace(r.start, r.end, r.newChar);
		}

		html = sb.toString();
		return html;
	}

	private String handlingCKEditor(String html) {
		StringBuilder sb = new StringBuilder(html);

		Pattern p = Pattern.compile("<div id='_____rtf_____[\\s\\S]*?' style='display:none'>");
		Matcher m = p.matcher(html);
		if (m.find()) { //只替换第一次匹配到的位置
			String group = m.group();
			String newStr = group.replace("display:none", "");
			sb = sb.replace(m.start(), m.end(), newStr);
			html = sb.toString();
			return handlingCKEditor(html);
		} else {
			return html;
		}
	}

	private static void replace(StringBuilder jsp, String oldStr, String newStr) {
		if (jsp == null || jsp.length() == 0 || oldStr == null || newStr == null) {
            return;
        }
		int index = jsp.indexOf(oldStr);
		int loopindex;
		while (index != -1) {
			jsp.replace(index, index + oldStr.length(), newStr);
			loopindex = jsp.substring(index + newStr.length()).indexOf(oldStr);
			if (loopindex != -1) {
				index = index + newStr.length() + loopindex;
			} else {
				index = -1;
				break;
			}
		}
	}
	
	private class Replace implements Comparable<Replace> {
		int start;
		int end;
		String newChar;

		public Replace(int start, int end, String newChar) {
			this.start = start;
			this.end = end;
			this.newChar = newChar;
		}

		@Override
		public int compareTo(Replace o) {
			return (this.end < o.end) ? -1 : ((this.end == o.end) ? 0 : 1);
		}
	}
}
