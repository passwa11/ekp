package com.landray.kmss.sys.attachment.service.spring;

import java.awt.GraphicsEnvironment;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

public class SysWaterMarkFontDataSource implements ICustomizeDataSource {

	public SysWaterMarkFontDataSource() {
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = e.getAvailableFontFamilyNames();
		Map<String, String> tmpMap = new TreeMap<String, String>();
		Locale locale = Locale.getDefault();
		for (String fontName : fontNames) {
			if (locale.equals(Locale.CHINA) || locale.equals(Locale.CHINESE)
					|| locale.equals(Locale.SIMPLIFIED_CHINESE)) {
				if (isChinese(fontName)) {
					if ("".equals(fdDefaultFontName)) {
						fdDefaultFontName = fontName;
					}
				}
			} else {
				if (fontName.startsWith("a") || fontName.startsWith("A")) {
					if ("".equals(fdDefaultFontName)) {
						fdDefaultFontName = fontName;
					}
				}
			}
			tmpMap.put(fontName, fontName);
		}
		fontDataSource = sortMapByKey(tmpMap);

	}

	private String fdDefaultFontName = "";

	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	private boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private Map<String, String> sortMapByKey(Map<String, String> tmpMap) {
		if (tmpMap == null || tmpMap.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(tmpMap);
		return sortMap;
	}

	private Map<String, String> fontDataSource = new TreeMap<String, String>();

	@Override
	public Map<String, String> getOptions() {
		return fontDataSource;
	}

	@Override
	public String getDefaultValue() {
		return fdDefaultFontName;
	}

	class MapKeyComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}
}
