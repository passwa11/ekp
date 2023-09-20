package com.landray.kmss.code.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autonomy.utilities.FileUtils;
import com.landray.kmss.util.MD5Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CdpVersion {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private String patchPath = "../CORE/cdp_patch";

	private JSONObject readJson(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		String content = FileUtils.readFileToString(filePath);
		return JSONObject.fromObject(content);
	}

	private Map<String, JSONObject> moduleCdpVersions = new HashMap<String, JSONObject>();

	private JSONObject getCdpVersionFile(String module) {
		if (moduleCdpVersions.containsKey(module)) {
			return moduleCdpVersions.get(module);
		}
		String filePath = "WebContent/WEB-INF/KmssConfig/";
		if (!"core".equals(module)) {
			filePath = filePath + module + "/";
		}
		JSONObject json = readJson(filePath + "/version/cdpversion.txt");
		moduleCdpVersions.put(module, json);
		return json;
	}

	private JSONObject getCdpVersionDetail(String module, String file) {
		JSONObject json = getCdpVersionFile(module);
		if (json != null) {
			JSONArray jFiles = json.optJSONArray("files");
			for (int i = 0; i < jFiles.size(); i++) {
				JSONObject jFile = jFiles.getJSONObject(i);
				String filePath = jFile.getString("file");
				filePath = filePath.startsWith("/") ? filePath.substring(1)
						: filePath;
				if (file.equals(filePath)) {
					return jFile;
				}
			}
		}
		return new JSONObject();
	}

	public void check() throws Exception {
		JSONObject json = readJson(
				patchPath + "/cdpversion.txt");
		if (json == null) {
			logger.warn("不存在CDP补丁");
			return;
		}
		boolean modify = false;

		JSONArray jFiles = json.getJSONArray("files");
		for (int i = 0; i < jFiles.size(); i++) {
			JSONObject cdp_ver = jFiles.getJSONObject(i);
			String last_md5 = cdp_ver.optString("md5");
			String filePath = cdp_ver.getString("file");
			filePath = filePath.startsWith("/") ? filePath.substring(1)
					: filePath;
			File file = new File(filePath);
			if (!file.exists()) {
				// 产品文件不存在
				if (!"".equals(last_md5)) {
					cdp_ver.remove("md5");
					modify = true;
					logger.warn("修订：" + filePath + "，md5:" + last_md5 + " → null");
				}
				continue;
			}
			// 开发平台补丁有，标准产品里面也有的文件，需要填写cdpversion
			JSONObject prod_ver = getCdpVersionDetail(
					cdp_ver.getString("module"), filePath);

			String cdp_md5 = MD5Util
					.getMD5String(new File(patchPath + "/" + filePath));
			String prod_md5 = MD5Util.getMD5String(file);

			String cdp_update = cdp_ver.optString("update");
			String prod_update = prod_ver.optString("update");

			if (cdp_md5.equals(prod_md5)) {
				// 内容是一样的
				if (!"".equals(prod_update)
						&& !cdp_update.equals(prod_update)) {
					// 版本不一样，若产品版本号为空，表示该文件产品一般不需要修改，以cdp未准
					logger.warn("错误：" + filePath + "，内容相同，版本号不同（cdp="
							+ cdp_update + ",prod=" + prod_update + "）");
				} else if (!last_md5.equals(prod_md5)) {
					cdp_ver.put("md5", prod_md5);
					modify = true;
					logger.warn("修订：" + filePath + "，md5:" + last_md5 + " → "
							+ prod_md5);
				}
			} else {
				// 内容不一样
				if (cdp_update.equals(prod_update)) {
					logger.warn("错误：" + filePath + "，内容不同，版本号相同（"
							+ cdp_update + "）");
				} else if (!last_md5.equals(prod_md5)) {
					logger.warn("冲突：" + filePath + "，内容不同，版本号不同（cdp="
							+ cdp_update + ",prod=" + prod_update
							+ "），若要以CDP版本为准，请修订md5:" + prod_md5);
				}
			}
		}
		if (modify) {
			FileUtils.writeStringToFile(json.toString(4),
					patchPath + "/cdpversion.txt", true);
		}
		logger.info("比对完毕！");
	}

	public static void main(String[] args) throws Exception {
		new CdpVersion().check();
	}
}
