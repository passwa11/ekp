package com.landray.kmss.util.face;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * face 表情处理类
 * @author lifangmin
 *
 */
public final class SysFaceConfig {
	private static Pattern imgPattern = Pattern.compile("<img[^>]*/?>");
	private static final Map<String, FaceTypeConfig> FACE_MAP = new HashMap<String, FaceTypeConfig>(){{
		put("face", new FaceTypeConfig(
				"face",//
				"/sys/evaluation/import/resource/images/bq/",//
				".gif",//
				0,//
				79,//
				24,//
				24//
		));
		put("lingling", new FaceTypeConfig(
				"lingling",//
				"/sys/ui/resource/images/phiz/lingling/",//
				".png",//
				1,//
				33,//
				60,//
				60//
		));
		put("dnyling", new FaceTypeConfig(
				"dnyling",//
				"/sys/ui/resource/images/phiz/dnyling/",//
				".gif",//
				1,//
				32,//
				60,//
				60//
		));
	}};

	public static String getUrl(HttpServletRequest request, String faceContent) {
		if(StringUtils.isBlank(faceContent)) {
			return StringUtils.EMPTY;
		}
		faceContent = StringUtils.strip(faceContent);
		Map<String, String> map = new HashMap<>();
		String prefix = "@Face@img";
		StringBuilder sb = new StringBuilder();
		getImage(faceContent,map, prefix, 0,sb);
		faceContent = StringEscapeUtils.escapeHtml4(sb.toString());
		for (Map.Entry<String, String> entry : map.entrySet()){
			faceContent = faceContent.replace(entry.getKey(), entry.getValue());
		}
		faceContent = SysFaceConfig.getContent(request, faceContent);
		return faceContent;
	}

	public static String getContent(HttpServletRequest request, String content){
		if(StringUtils.isEmpty(content)) {
			return StringUtils.EMPTY;
		}
		Collection<FaceTypeConfig> configs = FACE_MAP.values();
		for(FaceTypeConfig config : configs) {
			content = config.replace(request.getContextPath(), content);
		}
		return content;
	}

	private static void getImage(String faceContent, Map<String, String> map, String prefix, int index, StringBuilder sb){
		Matcher matcher = imgPattern.matcher(faceContent);
		int subIndex = 0;
		String[] filters = {"lingling", "dnyling","data:image","face","wangwang","fckeditor" };
		while(matcher.find()){
			String key = prefix+"_"+index+"_$";
			String val = matcher.group();
			boolean flag = false;
			for (String filter : filters){
				if(val.contains(filter)){
					flag = true;
					break;
				}
			}
			if(!flag) {
                continue;
            }
			map.put(key, val);
			sb.append(faceContent.substring(subIndex, matcher.start())).append(key);
			subIndex = matcher.end();
			index++;
		}
		sb.append(faceContent.substring(subIndex, faceContent.length()));
	}

	/**
	 * 表情转换成url
	 * @param request
	 * @param faceContent
	 * @return
	 */
	public static String replaceExpressionUrl(HttpServletRequest request, String faceContent) {
		if(StringUtils.isBlank(faceContent)) {
			return StringUtils.EMPTY;
		}
		Collection<FaceTypeConfig> configs = FACE_MAP.values();
		for(FaceTypeConfig config : configs) {
			faceContent = config.replace(request.getContextPath(), faceContent);
		}
		return faceContent;
	}

	public static String replaceEmptyString(HttpServletRequest request, String faceContent) {
		if(StringUtils.isBlank(faceContent)) {
			return StringUtils.EMPTY;
		}
		Collection<FaceTypeConfig> configs = FACE_MAP.values();
		for(FaceTypeConfig config : configs) {
			faceContent = config.replaceEmptyString(faceContent);
		}
		return faceContent;
	}

	private static class FaceTypeConfig {
		private String type;
		private String dir;
		private String suffix;
		private int start;
		private int max;
		private int width;
		private int height;
		private Map<String, String> __replace__ = new HashMap<String, String>();
		public FaceTypeConfig(String type, String dir, String suffix, int start, int max, int width, int height) {
			this.type = type;
			this.dir = dir;
			this.suffix = suffix;
			this.start = start;
			this.max = max;
			this.width = width;
			this.height = height;
			this.init();
		}
		private void init() {
			for(int i= this.start; i<= max; i++) {
				__replace__.put("\\["+this.type+i+"]", this.dir+i+this.suffix);
			}
		}

		public String replace(String ctxPath, String str) {
			if(StringUtils.isBlank(str)) {
				return StringUtils.EMPTY;
			}
			for(Map.Entry<String, String> entry : this.__replace__.entrySet()) {
				str = str.replaceAll(entry.getKey(), this.replaceString(ctxPath, entry.getValue()));
			}
			return str;
		}

		private String replaceString(String ctxPath, String value) {
			StringBuilder sb = new StringBuilder("<img");
			sb.append(" width='").append(this.width).append("'")
					.append(" src='").append(ctxPath+value).append("'")
					.append(" type='").append(this.type).append("'")
					.append(" />");
			return sb.toString();
		}
		public String getDir() {
			return dir;
		}
		public String getSuffix() {
			return suffix;
		}

		public String replaceEmptyString(String str) {
			if(StringUtils.isBlank(str)) {
				return StringUtils.EMPTY;
			}
			Set<String> faces = this.__replace__.keySet();
			for(String face : faces) {
				str = str.replaceAll(face, StringUtils.EMPTY);
			}
			return str;
		}
	}
}

