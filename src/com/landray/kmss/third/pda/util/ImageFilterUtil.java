package com.landray.kmss.third.pda.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.landray.kmss.util.StringUtil;

public class ImageFilterUtil {

	// 图片正在加载中地址
	private static final String PDA_IMGLOADING_URL = "/third/pda/resource/images/imgloading.gif";

	// 获取所有的image元素
	private static void fetchImgTag(String htmlSource, List<String> imageArray) {
		String htmlStr = htmlSource.toLowerCase();
		int indexInt = htmlStr.indexOf("<img");
		if (indexInt > -1) {
			htmlStr = htmlStr.substring(indexInt);
			int endInt = -1;
			int lengthVar = 0;
			int endInt1 = htmlStr.indexOf(">");
			int endInt2 = htmlStr.indexOf("/>");
			if (endInt1 > -1 && endInt2 > -1) {
				if (endInt1 < endInt2) {
					endInt = endInt1;
					lengthVar = 1;
				} else {
					endInt = endInt2;
					lengthVar = 2;
				}
			} else {
				if (endInt1 <= -1) {
					if (endInt2 > -1) {
						endInt = endInt2;
						lengthVar = 2;
					}
				}
				if (endInt2 <= -1) {
					if (endInt1 > -1) {
						endInt = endInt1;
						lengthVar = 1;
					}
				}
			}
			if (endInt > -1) {
				imageArray.add(htmlSource.substring(indexInt, indexInt + endInt
						+ lengthVar));
				fetchImgTag(
						htmlSource.substring(indexInt + endInt + lengthVar),
						imageArray);
			}
		}
	}

	// 获取image元素的src属性值
	private static String getImgSrc(String imgHtml) {
		String imgHtmlLower = imgHtml.toLowerCase();
		int srcIndex = imgHtmlLower.indexOf("src=");
		if (srcIndex > -1) {
			imgHtmlLower = imgHtmlLower.substring(srcIndex);
			int endint1 = imgHtmlLower.indexOf(" ");
			int endint2 = imgHtmlLower
					.indexOf(imgHtmlLower.endsWith("/>") ? "/>" : ">");
			return imgHtml.substring(srcIndex + 4, srcIndex
					+ (endint1 > -1 ? endint1 : endint2));
		}
		return "";
	}

	public static String replaceImgHtml(String source, String basePath) {
		List<String> replace = new ArrayList<String>();
		fetchImgTag(source, replace);
		if (replace != null && replace.size() > 0) {
            for (Iterator iterator = replace.iterator(); iterator.hasNext();) {
                String replaceStr = (String) iterator.next();
                String src = getImgSrc(replaceStr);
                if (StringUtil.isNotNull(src)) {
                    source = source.replace(replaceStr, "<img src=\""
                            + (StringUtil.isNotNull(basePath) ? basePath : "")
                            + PDA_IMGLOADING_URL + "\" oldsrc="
                            + src + (replaceStr.endsWith("/>") ? "/>" : ">"));
                }
            }
        }
		return source;
	}
}
