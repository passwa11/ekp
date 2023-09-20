package com.landray.kmss.sys.ui.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiExtend;
import com.landray.kmss.sys.ui.xml.model.SysUiTheme;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThemeUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThemeUtil.class);
	private static ThemeGetter themeGetter;

	public static boolean isNotMerge = true;
	static {
		String _merge = ResourceUtil
				.getKmssConfigString("kmss.ui.theme.notmerge");
		if (StringUtil.isNotNull(_merge)) {
			isNotMerge = Boolean.valueOf(_merge);
		}
	}

	public static void registerThemeGetter(ThemeGetter _themeGetter) {
		ThemeUtil.themeGetter = _themeGetter;
	}

	public static String getThemeId(HttpServletRequest request) {
		String theme = (String) request.getAttribute("sys.ui.theme");
		if (theme != null) {
            return theme;
        }
		if (themeGetter == null) {
            return "default";
        }
		theme = themeGetter.getTheme(request);
		if (StringUtil.isNull(theme)) {
            return "default";
        }
		return theme;
	}

	public static void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String _theme = request.getParameter("theme");
		String _key = request.getParameter("key");
		StringBuilder sb = mergeTheme(request.getContextPath(), _theme, _key);
		response.setContentType("text/css");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(sb.toString());
	}

	public static String getThemes(HttpServletRequest request) {
		String theme = ThemeUtil.getThemeId(request);
		String def = "default";
		JSONObject json = new JSONObject();
		SysUiTheme defaultTheme = SysUiPluginUtil.getThemeById(def);
		SysUiTheme currentTheme = SysUiPluginUtil.getThemeById(theme);
		String dtemp = defaultTheme.getFdPath();
		if (dtemp.startsWith("/")) {
			dtemp = dtemp.substring(1);
		}
		if (theme.equals(def) || currentTheme == null) {
			Iterator<Map.Entry<String, String>> fs = defaultTheme.getFiles()
					.entrySet().iterator();
			while (fs.hasNext()) {
				Map.Entry<String, String> fname = fs.next();
				JSONArray css = new JSONArray();
				css.add(dtemp + fname.getValue());
				json.put(fname.getKey(), css);
			}
		} else {

			Iterator<String> ts = defaultTheme.getFiles().keySet().iterator();
			while (ts.hasNext()) {
				String key = ts.next();
				JSONArray fs = new JSONArray();
				String temp = currentTheme.getFdPath();
				if (temp.startsWith("/")) {
					temp = temp.substring(1);
				}
				if (currentTheme.getFiles().containsKey(key)) {
					if (!ThemeUtil.isNotMerge) {
						fs.add(temp
								+ currentTheme.getFiles().get(key).replace(
										".css", "_.css"));
					} else {
						fs.add(temp
								+ currentTheme.getFiles().get(key).replace(
										".css", ".theme.css") + "?theme="
								+ theme + "&key=" + key);
					}
				} else {
					if (!ThemeUtil.isNotMerge) {
						fs.add(dtemp + defaultTheme.getFiles().get(key));
					} else {
						fs.add("ui-ext/"
								+ currentTheme.getFdId()
								+ "/"
								+ defaultTheme.getFiles().get(key).replace(
										".css", ".theme.css") + "?theme="
								+ theme + "&key=" + key);
					}
				}
				json.put(key, fs);
			}
		}
		/**
		 * prompt映射到listview，修改原因<br>
		 * 为了减少请求和无数据样式抖动，将prompt.css合并到listview.css，由于很多零散页面通过
		 * (JSONObject.fromObject(SysUiPluginUtil.getThemes(request))).getJSONArray("prompt")
		 * 获取皮肤样式，所有在此做映射
		 */
		json.put("prompt", json.get("listview"));
		return json.toString(4);
	}
	
	
	public static String getThemesFileByName(HttpServletRequest request,String fileName) {
		String theme = ThemeUtil.getThemeId(request);
		String def = "default";
		SysUiTheme defaultTheme = SysUiPluginUtil.getThemeById(def);
		SysUiTheme currentTheme = SysUiPluginUtil.getThemeById(theme);
		String dtemp = defaultTheme.getFdPath();
		if (dtemp.startsWith("/")) {
			dtemp = dtemp.substring(1);
		}
		if (theme.equals(def) || currentTheme == null) {
			if(defaultTheme.getFiles().containsKey(fileName)){
				return dtemp + defaultTheme.getFiles().get(fileName);
			}
		} else {
			if(currentTheme.getFiles().containsKey(fileName)){
				String temp = currentTheme.getFdPath();
				if (temp.startsWith("/")) {
					temp = temp.substring(1);
				}
				if (!ThemeUtil.isNotMerge) {
					return temp+ currentTheme.getFiles().get(fileName).replace(".css", "_.css");
				} else {
					return temp+ currentTheme.getFiles().get(fileName).replace(".css", ".theme.css") + "?theme="+ theme + "&key=" + fileName;
				}
			}else {
				return dtemp + defaultTheme.getFiles().get(fileName);
			}
		}
		return null;
	}

	public static void mergeAllTheme(String contextPath) {
		Iterator<String> iter = SysUiPluginUtil.getThemes().keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			try {
				ThemeUtil.mergeTheme(contextPath, key);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
	}

	public static void mergeTheme(String contextPath, String theme)
			throws Exception {
		SysUiTheme defaultTheme = SysUiPluginUtil.getThemeById("default");
		SysUiTheme currentTheme = SysUiPluginUtil.getThemeById(theme);
		if ("default".equals(theme) || currentTheme == null) {
		} else {
			Iterator<String> ts = defaultTheme.getFiles().keySet().iterator();
			String temp = currentTheme.getFdPath();
			while (ts.hasNext()) {
				String key = ts.next();
				if (currentTheme.getFiles().containsKey(key)) {
					String fileName = currentTheme.getFiles().get(key).replace(
							".css", "_.css");
					String body = ThemeUtil.mergeTheme(contextPath, theme, key)
							.toString();
					if (temp.startsWith("/ui-ext/")) {
						fileName = ResourceUtil.KMSS_RESOURCE_PATH + temp
								+ fileName;
					} else {
						fileName = ConfigLocationsUtil.getWebContentPath()
								+ temp + fileName;
					}
					FileUtil.createFile(fileName, body, "UTF-8");
				}
			}
		}
	}

	private static String getFileString(String fileName) throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fileName), "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} finally {
			// 关闭流
			IOUtils.closeQuietly(br);
		}
		return sb.toString();
	}

	public static StringBuilder mergeTheme(String contextPath, String theme,
			String key) {
		SysUiTheme defaultTheme = SysUiPluginUtil.getThemeById("default");
		SysUiTheme currentTheme = SysUiPluginUtil.getThemeById(theme);
		String filePath = defaultTheme.getFiles().get(key);
		filePath = defaultTheme.getFdPath() + filePath;
		StringBuilder sb = new StringBuilder();
		try {
			String temp = getFileString(ConfigLocationsUtil.getWebContentPath()
					+ filePath);
			sb.append(replace(temp, contextPath));
			if (currentTheme.getFiles().containsKey(key)) {
				sb.append("\r\n");
				sb.append("/*************当前主题***************/");
				sb.append("\r\n");
				String path = currentTheme.getFdPath();
				String ctemp = "";
				if (path.startsWith("/ui-ext/")) {
					ctemp = getFileString(ResourceUtil.KMSS_RESOURCE_PATH
							+ path + currentTheme.getFiles().get(key));
				} else {
					ctemp = getFileString(ConfigLocationsUtil
							.getWebContentPath()
							+ path + currentTheme.getFiles().get(key));
				}

				String bom = ((char) 65279) + "";
				if (ctemp.startsWith(bom)) {
					ctemp = ctemp.substring(bom.length());
				}
				sb.append(ctemp);
			}
		} catch (Exception e) {
			logger.error("CSS文件合并错误", e);
		}
		return sb;
	}

	private static String replace(String value, String contextPath) {
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("url\\(([^\\)]+)\\)");
		Matcher matcher = pattern.matcher(value);
		while (matcher.find()) {
			String str = matcher.group(1);
			
			if ("/".equals(contextPath))// WAS下路径问题
            {
                contextPath = "";
            }
			if (str.startsWith("../")) {
				str = "url(" + contextPath + "/sys/ui/extend/theme/default/"
						+ str.substring(3) + ")";
			} else { // 其他情况保留
				str = "url(" + str + ")";
			}

			// ///////////请保留下面内容
			str = str.replaceAll("\\\\", "\\\\\\\\");
			if (str.indexOf("$") > -1) {
				str = str.replaceAll("\\$", "\\\\\\$");
			}
			
			matcher.appendReplacement(sb, str);
			
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 获取默认主题
	 * 
	 * @return
	 */
	public static List<SysUiTheme> getDefTheme() {
		List<SysUiTheme> defThemes = new ArrayList<SysUiTheme>();
		// 所有主题
		Collection<SysUiTheme> themes = SysUiPluginUtil.getThemes().values();
		for (SysUiTheme theme : themes) {
			// 排除自定义主题
			if (SysUiPluginUtil.getExtendById(theme.getFdId()) == null) {
				defThemes.add(theme);
			}
		}
		Collections.sort(defThemes, new Comparator<SysUiTheme>() {
			@Override
			public int compare(SysUiTheme o1, SysUiTheme o2) {
				return o1.getFdId().compareTo(o2.getFdId());
			}
		});
		return defThemes;
	}

	/**
	 * 获取自定义主题
	 * 
	 * @return
	 */
	public static List<SysUiExtend> getCustomTheme() {
		List<SysUiExtend> customTheme = new ArrayList<SysUiExtend>();
		Collection<SysUiExtend> list = SysUiPluginUtil.getExtends().values();
		for (SysUiExtend l : list) {
			l.setUiType("theme");
			customTheme.add(l);
		}
		Collections.sort(customTheme, new Comparator<SysUiExtend>() {
			@Override
			public int compare(SysUiExtend o1, SysUiExtend o2) {
				return o1.getFdId().compareTo(o2.getFdId());
			}
		});
		return customTheme;
	}

}
