package com.landray.kmss.sys.portal.cloud;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;

import net.sf.json.JSONObject;

public class PortalMappingHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(PortalMappingHelper.class);

	/**
	 * 呈现code映射：EKP.code(数据格式) -> CLOUD.code(呈现)
	 */
	private static JSONObject renderCodeMapping;
	/**
	 * 变量类型kind映射：EKP.kind -> CLOUD.type
	 */
	private static JSONObject kindMapping;

	public static final String FILE_PATH = "/sys/portal/cloud/";
	public static final String RENDER_FILE = FILE_PATH + "render.json";
	public static final String KIND_FILE = FILE_PATH + "kind.json";

	private static JSONObject initResource(String path) {
		if (logger.isInfoEnabled()) {
			logger.info("加载文件：" + path);
		}
		JSONObject json = null;
		InputStream input = null;
		try {
			input = new FileInputStream(path);
			String jsonStr = IOUtils.toString(input, "UTF-8");
			json = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			logger.error("加载" + path + "文件失败！", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return json;
	}

	private static void initRenderMapping() {
		JSONObject temp = initResource(ConfigLocationsUtil.getKmssConfigPath() + RENDER_FILE);
		renderCodeMapping = new JSONObject();
		renderCodeMapping.putAll(temp.getJSONObject("system"));
		renderCodeMapping.putAll(temp.getJSONObject("custom"));
	}

	private static void initKindMapping() {
		kindMapping = initResource(ConfigLocationsUtil.getKmssConfigPath() + KIND_FILE);
	}

	public static void main(String[] args) {
		initRenderMapping();
		JSONObject json = new JSONObject();
		Iterator it = renderCodeMapping.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			JSONObject obj = new JSONObject();
			obj.put("desktop", renderCodeMapping.getString(key));
			obj.put("mobile", renderCodeMapping.getString(key));
			json.put(key, obj.toString());
		}
		System.out.println(json.toString());
	}
	/**
	 * 根据数据格式code获得呈现code
	 * 
	 * @param formatCode
	 * @return
	 */
	public static String getRenderCodeMapping(String formatCode) {
		if (renderCodeMapping == null) {
			initRenderMapping();
		}
		return renderCodeMapping.get(formatCode) != null
				? renderCodeMapping.getString(formatCode) : null;
	}

	public static String getKindMapping(String kind) {
		if (kindMapping == null) {
			initKindMapping();
		}
		return kindMapping.get(kind) != null ? kindMapping.getString(kind)
				: kind;
	}
}
