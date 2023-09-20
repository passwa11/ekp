package com.landray.kmss.sys.mportal.compressor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.mobile.compressor.CompressI18nUtil;
import com.landray.kmss.sys.mobile.compressor.CompressUtils;
import com.landray.kmss.sys.mobile.compressor.JSContext;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UnicodeReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 脚本压缩
 * 
 * @author
 *
 */
public class CompressJsRunner implements ICompressRunner {

	private final String PATTERN_DEPENDENCES = "dependences:[^\\[]*(\\[[^\\]]*\\])";
	private final String PATTERN_CSSURLS = "cssUrls:[^\\[]*(\\[[^\\]]*\\])";

	@Override
	public void run(List<String> jsUrls) throws Exception {

		// 封装合并模块名与文件路径映射
		Map<String, String> jsMaps = new HashMap<>();

		Iterator<String> jsIter = jsUrls.iterator();

		String configJs = "";

		while (jsIter.hasNext()) {

			String url = jsIter.next();
			// 最后一个为门户配置数据包
			if (!jsIter.hasNext()) {
				configJs = url;
				break;
			}

			String key = getModuleName(url);
			jsMaps.put(key, url);
		}

		StringBuilder allJss = new StringBuilder().append("require({cache:{");

		// 合并主脚本
		StringBuilder mainJss = merge(jsMaps);
		// 合并依赖脚本
		StringBuilder dependences = dependences(mainJss);
		// 合并样式文件
		allJss.append(cssUrls(dependences));

		// 其他配置文件脚本
		otherConfigs(allJss);

		allJss.append("\r\n}});");

		allJss.append(configJs + ";\n");

		// 压缩国际化文件
		CompressI18nUtil.run(TARGET_PATH, allJss);

		// 替换国际化请求为代理请求
		String jss = CompressI18nUtil.proxyI18n(allJss.toString(), TARGET_PATH);

		// 压缩脚本
		File miniFile = new File(ConfigLocationsUtil.getWebContentPath()
				+ CompressUtils.getMiniFileName("/" + TARGET_PATH, "js"));
		JSContext ctx = new JSContext(new StringReader(jss), new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(miniFile), "UTF-8")));
		ctx.setVerbose(true);
		CompressUtils.compressOneJS(ctx);

	}

	/**
	 * 其他配置信息，例如机器人之类的
	 * 
	 * @param allJss
	 * @return
	 * @throws Exception
	 */
	private void otherConfigs(StringBuilder allJss) throws Exception {
		module(allJss, StringUtil.formatUrl("/sys/mportal/mobile/config.jsp", false),
				"define([], function() { return " + getConfig().toString() + "});");
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getConfig() throws Exception {

		String itEnabled = "''";
		String itUrl = "''";
		String searchHost = "''";

		String intellModelName = "com.landray.kmss.third.intell.model.IntellConfig";
		if (com.landray.kmss.util.ModelUtil.isExisted(intellModelName)) {
			Class clz = ClassUtils.forName(intellModelName);
			Object instance = clz.newInstance();
			itEnabled = (String) clz.getMethod("getItEnabled", null).invoke(instance, null);
			if ("true".equals(itEnabled) || "1".equals(itEnabled)) {
				itUrl = (String) clz.getMethod("getItUrl", null).invoke(instance, null);
				String searchUrl = "/labc-search/#/?q=";
				try {
					String tmpUrl = (String) clz.getMethod("getSearchUrl", null).invoke(instance,
							null);
					if (StringUtil.isNotNull(tmpUrl)) {
						searchUrl = tmpUrl;
					}
				} catch (Exception e) {

				}
				if (StringUtil.isNotNull(itUrl)) {
					itUrl = StringUtil.formatUrl(itUrl);
					String searchEnabled = (String) clz.getMethod("getSearchEnabled", null)
							.invoke(instance, null);
					if ("true".equals(searchEnabled) || "1".equals(searchEnabled)) {
						URL url = new URL(itUrl);
						String host = ("https".equalsIgnoreCase(url.getProtocol()) ? "https://"
								: "http://") + url.getHost()
								+ (url.getPort() == -1 || url.getPort() == 80
										|| url.getPort() == 443 ? "" : ":" + url.getPort());
						if (StringUtil.isNotNull(host)) {
							host = host + searchUrl;
						}
						searchHost = host;
					}
				}
			}
		}

		// 以上为"移动端判断是否有小k,aip模块，和搜索图标点击跳转位置"

		// 移动端首页页眉类型

		ISysMportalPageService sysMportalPageService = (ISysMportalPageService) SpringBeanUtil
				.getBean("sysMportalPageService");
		String headerType = sysMportalPageService.getHeaderType();

		JSONObject object = new JSONObject();
		object.element("itEnabled", itEnabled);
		object.element("itUrl", itUrl);
		object.element("searchHost", searchHost);
		object.element("headerType", headerType);

		return object;
	}

	/**
	 * 根据url获取模块名
	 * 
	 * @param url
	 * @return
	 */
	private String getModuleName(String url) {

		String key = StringUtils.removeStart(url, "/");
		key = StringUtils.removeEnd(key, ".js");
		return key;
	}

	/**
	 * 样式解析压缩
	 * 
	 * @param mainJss
	 * @return
	 * @throws Exception
	 */
	private String cssUrls(StringBuilder mainJss) throws Exception {

		Pattern p = Pattern.compile(PATTERN_CSSURLS, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(mainJss);

		List<String> cssUrls = new ArrayList<>();

		while (matcher.find()) {

			String cssUrl = matcher.group(1);

			if (StringUtil.isNull(cssUrl)) {
				continue;
			}

			JSONArray array = JSONArray.fromObject(cssUrl);

			for (int i = 0; i < array.size(); i++) {

				String url = array.get(i).toString();

				if (cssUrls.contains(url)) {
					continue;
				}

				cssUrls.add(url);

			}
		}

		if (!cssUrls.isEmpty()) {
			new CompressCssRunner().run(cssUrls);
		}

		return mainJss.toString().replaceAll(",(\\s*|\n)" + PATTERN_CSSURLS, "");

	}

	/**
	 * 合并依赖
	 * 
	 * @param mergeJss
	 * @return
	 */
	private StringBuilder dependences(StringBuilder mainJss) {

		Pattern p = Pattern.compile(PATTERN_DEPENDENCES, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(mainJss);

		Map<String, String> map = new HashMap<>();

		while (matcher.find()) {

			String dependences = matcher.group(1);

			if (StringUtil.isNull(dependences)) {
				continue;
			}

			JSONArray array = JSONArray.fromObject(dependences);

			for (int i = 0; i < array.size(); i++) {

				String url = array.get(i).toString();

				// 生成模块信息
				String key = getModuleName(url);

				if (!map.containsKey(key)) {
					map.put(key, url);
				}
			}
		}

		String mainJs = mainJss.toString().replaceAll(PATTERN_DEPENDENCES, "");
		return merge(map).append(mainJs);
	}

	private StringBuilder module(StringBuilder sb, String key, String jss) {

		sb.append("\r\n'").append(key).append("':function(){\r\n");
		sb.append(jss);
		sb.append("\r\n}");
		sb.append(",");
		return sb;

	}

	/**
	 * 合并文件
	 * 
	 * @param jss
	 * @return
	 */
	private StringBuilder merge(Map<String, String> jss) {
		StringBuilder sb = new StringBuilder(1000);
		Set<Map.Entry<String, String>> jsList = jss.entrySet();

		for (Iterator<Map.Entry<String, String>> it = jsList.iterator(); it.hasNext();) {
			Map.Entry<String, String> module = it.next();
			String mapKey = module.getKey().replaceAll("\\\\", "\\/");
			if (mapKey.startsWith("/")) {
				mapKey = mapKey.substring(1);
			}

			try {
				module(sb, mapKey,
						IOUtils.toString(new UnicodeReader(new FileInputStream(
								ConfigLocationsUtil.getWebContentPath() + module.getValue()),
								"utf-8")));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return sb;
	}

}
