package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.mportal.model.SysMportalMenu;
import com.landray.kmss.sys.mportal.model.SysMportalMenuItem;
import com.landray.kmss.sys.mportal.service.ISysMportalMenuService;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 快捷方式业务接口实现
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuServiceImp extends BaseServiceImp implements
		ISysMportalMenuService {

	private static final String css = "/sys/mobile/css/themes/default/font-mui.css";

	private static final String iconList = "/sys/mportal/sys_mportal_menu/css/iconList.css";

	@Override
	public List<String> toClass() throws Exception {
		List<String> clazs = new ArrayList<String>();
		String str = FileUtil.getFileString(PluginConfigLocationsUtil
				.getWebContentPath() + css);
		Pattern p = Pattern.compile("\\.(.*?):before");
		Matcher m = p.matcher(str);
		while (m.find()) {
			clazs.add(m.group(1));
		}
		return clazs;
	}

	@Override
	public List<String> imgClass() throws Exception {
		List<String> clazs = new ArrayList<String>();
		String str = FileUtil.getFileString(PluginConfigLocationsUtil.getWebContentPath() + iconList);
		Pattern p = Pattern.compile("\\.(.*?)-icon");
		Matcher m = p.matcher(str);
		while (m.find()) {
			clazs.add(m.group().replace(".", ""));
		}
		return clazs;
	}
	@Override
	public List<String> iconClass() throws Exception {
		List<String> clazs = new ArrayList<String>();
		String str = FileUtil.getFileString(PluginConfigLocationsUtil.getWebContentPath() + iconList);
		Pattern p = Pattern.compile("\\.(.*?)-new");
		Matcher m = p.matcher(str);
		while (m.find()) {
			clazs.add(m.group().replace(".", ""));
		}
		return clazs;
	}
	@Override
	public JSONArray toItemData(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		SysMportalMenu menu = null;
		JSONArray datas = new JSONArray();
		if (StringUtil.isNotNull(fdId)) {
			menu = (SysMportalMenu) this.findByPrimaryKey(fdId);
		} else {
			return datas;
		}
		List<SysMportalMenuItem> items = menu
				.getFdSysMportalMenuItems();
		for (SysMportalMenuItem item : items) {
			JSONObject data = new JSONObject();
			data.element("icon", item.getFdIcon());
			data.element("url", item.getFdUrl());
			data.element("text", item.getFdName());
			data.element("iconType", item.getFdIconType());
			datas.add(data);
		}
		return datas;
	}

	

}
