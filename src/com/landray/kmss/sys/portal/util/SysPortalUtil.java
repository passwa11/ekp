package com.landray.kmss.sys.portal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.portal.model.export.FdId;
import com.landray.kmss.sys.portal.model.export.Fdids;
import com.landray.kmss.sys.portal.model.export.JPortal;
import com.landray.kmss.sys.portal.model.export.Portlet;
import com.landray.kmss.sys.portal.model.export.SourceOpt;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

public class SysPortalUtil {
	public static class ModuleInfo {
		private String code;
		private String name;
		private List<ModuleInfo> children;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<ModuleInfo> getChildren() {
			if (this.children == null) {
				this.children = new ArrayList<ModuleInfo>();
			}
			return children;
		}

		public void setChildren(List<ModuleInfo> children) {
			this.children = children;
		}
	}
 
	private static ModuleInfo getModuleInfo(List<ModuleInfo> modules,
			String server) {
		for (int i = 0; i < modules.size(); i++) {
			if (modules.get(i).getCode().equals(server)) {
				return modules.get(i);
			}
		}
		return null;
	}

	public static List<ModuleInfo> getPortalModules() {
		List<ModuleInfo> modules = new ArrayList<ModuleInfo>();
		Collection<SysUiPortlet> portlets = SysUiPluginUtil.getPortlets()
				.values();
		String[] sinfo = SysPortalConfig.getCurrentGroupInfo();
		if (sinfo == null) {
			sinfo = new String[3];
			sinfo[0] = "current";
			sinfo[1] = "current";
		}
		ModuleInfo current = new ModuleInfo();
		current.setCode(sinfo[0]);
		current.setName(sinfo[1]+"(当前服务器)");
		modules.add(current);
		Map<String, String> serverNames = SysPortalConfig.getServerName();
		Iterator<SysUiPortlet> it = portlets.iterator();
		while (it.hasNext()) {
			SysUiPortlet x = it.next();
			String key = x.getFdModule();
			String name = key;
			// 判断模块是否可用
			if (!TripartiteAdminUtil.isEnabled(getModuleUrlPrefix(name))) {
				// 如果模块不可用，就不加载信息
				continue;
			}
			if (name.indexOf(SysUiConstant.SEPARATOR) > 0) {
				name = name.substring(
						name.indexOf(SysUiConstant.SEPARATOR)
								+ SysUiConstant.SEPARATOR.length(),
								name.length());
			}
			String value = ResourceUtil.getMessage(name);
			if (x.getFdId().indexOf(SysUiConstant.SEPARATOR) > 0) {
				String server = x.getFdId().substring(0,
						x.getFdId().indexOf(SysUiConstant.SEPARATOR));
				ModuleInfo info = getModuleInfo(modules, server);
				if (info == null) {
					info = new ModuleInfo();
					info.setCode(server);
					info.setName(serverNames.get(server));
					modules.add(info);
				}
				if (getModuleInfo(info.getChildren(), key) == null) {
					ModuleInfo module = new ModuleInfo();
					module.setCode(key);
					module.setName(value);
					info.getChildren().add(module);
				}
			} else {
				List<ModuleInfo> ms = getModuleInfo(modules, sinfo[0])
						.getChildren();
				if (getModuleInfo(ms, key) == null) {
					ModuleInfo module = new ModuleInfo();
					module.setCode(key);
					module.setName(value);
					ms.add(module);
				}
			}
		}

		Collections.sort(modules, new Comparator<ModuleInfo>() {
			@Override
            public int compare(ModuleInfo a, ModuleInfo b) {
				return ChinesePinyinComparator.compare(a.getName(),
						b.getName());
			}
		});
		for (int i = 0; i < modules.size(); i++) {
			List<ModuleInfo> infos = modules.get(i).getChildren();
			Collections.sort(infos, new Comparator<ModuleInfo>() {
				@Override
                public int compare(ModuleInfo a, ModuleInfo b) {
					return ChinesePinyinComparator.compare(a.getName(),
							b.getName());
				}
			});
		}
		return modules;
	}

	/**
	 * 为防万一，重载了这个函数 页面选择部件，模块下拉列表，过滤匿名与普通portlet部件
	 * 
	 * @author 吴进 by 20191114
	 * @param scene
	 * @return
	 */
	public static List<ModuleInfo> getPortalModules(String scene) {
		List<ModuleInfo> modules = new ArrayList<ModuleInfo>();
		Collection<SysUiPortlet> portlets = SysUiPluginUtil.getPortlets()
				.values();
		String[] sinfo = SysPortalConfig.getCurrentGroupInfo();
		if (sinfo == null) {
			sinfo = new String[3];
			sinfo[0] = "current";
			sinfo[1] = "current";
		}
		ModuleInfo current = new ModuleInfo();
		current.setCode(sinfo[0]);
		current.setName(sinfo[1] + "(当前服务器)");
		modules.add(current);
		Map<String, String> serverNames = SysPortalConfig.getServerName();

		if ("anonymous".equals(scene)) {
			Iterator<SysUiPortlet> it = portlets.iterator();
			while (it.hasNext()) {
				SysUiPortlet x = it.next();
				Boolean isAnonymous = x.getFdAnonymous();
				if (isAnonymous) {
					String key = x.getFdModule();
					String name = key;
					// 判断模块是否可用
					if (!TripartiteAdminUtil
							.isEnabled(getModuleUrlPrefix(name))) {
						// 如果模块不可用，就不加载信息
						continue;
					}
					if (name.indexOf(SysUiConstant.SEPARATOR) > 0) {
						name = name.substring(
								name.indexOf(SysUiConstant.SEPARATOR)
										+ SysUiConstant.SEPARATOR.length(),
								name.length());
					}
					String value = ResourceUtil.getMessage(name);
					if (x.getFdId().indexOf(SysUiConstant.SEPARATOR) > 0) {
						String server = x.getFdId().substring(0,
								x.getFdId().indexOf(SysUiConstant.SEPARATOR));
						ModuleInfo info = getModuleInfo(modules, server);
						if (info == null) {
							info = new ModuleInfo();
							info.setCode(server);
							info.setName(serverNames.get(server));
							modules.add(info);
						}
						if (getModuleInfo(info.getChildren(), key) == null) {
							ModuleInfo module = new ModuleInfo();
							module.setCode(key);
							module.setName(value);
							info.getChildren().add(module);
						}
					} else {
						List<ModuleInfo> ms = getModuleInfo(modules, sinfo[0])
								.getChildren();
						if (getModuleInfo(ms, key) == null) {
							ModuleInfo module = new ModuleInfo();
							module.setCode(key);
							module.setName(value);
							ms.add(module);
						}
					}
				}
			}
		} else {
			Iterator<SysUiPortlet> it = portlets.iterator();
			while (it.hasNext()) {
				SysUiPortlet x = it.next();
				Boolean isAnonymous = x.getFdAnonymous();
				if (isAnonymous == null || !isAnonymous) {
					String key = x.getFdModule();
					String name = key;
					// 判断模块是否可用
					if (!TripartiteAdminUtil
							.isEnabled(getModuleUrlPrefix(name))) {
						// 如果模块不可用，就不加载信息
						continue;
					}
					if (name.indexOf(SysUiConstant.SEPARATOR) > 0) {
						name = name.substring(
								name.indexOf(SysUiConstant.SEPARATOR)
										+ SysUiConstant.SEPARATOR.length(),
								name.length());
					}
					String value = ResourceUtil.getMessage(name);
					if (x.getFdId().indexOf(SysUiConstant.SEPARATOR) > 0) {
						String server = x.getFdId().substring(0,
								x.getFdId().indexOf(SysUiConstant.SEPARATOR));
						ModuleInfo info = getModuleInfo(modules, server);
						if (info == null) {
							info = new ModuleInfo();
							info.setCode(server);
							info.setName(serverNames.get(server));
							modules.add(info);
						}
						if (getModuleInfo(info.getChildren(), key) == null) {
							ModuleInfo module = new ModuleInfo();
							module.setCode(key);
							module.setName(value);
							info.getChildren().add(module);
						}
					} else {
						List<ModuleInfo> ms = getModuleInfo(modules, sinfo[0])
								.getChildren();
						if (getModuleInfo(ms, key) == null) {
							ModuleInfo module = new ModuleInfo();
							module.setCode(key);
							module.setName(value);
							ms.add(module);
						}
					}
				}
			}
		}

		Collections.sort(modules, new Comparator<ModuleInfo>() {
			@Override
            public int compare(ModuleInfo a, ModuleInfo b) {
				return ChinesePinyinComparator.compare(a.getName(),
						b.getName());
			}
		});
		for (int i = 0; i < modules.size(); i++) {
			List<ModuleInfo> infos = modules.get(i).getChildren();
			Collections.sort(infos, new Comparator<ModuleInfo>() {
				@Override
                public int compare(ModuleInfo a, ModuleInfo b) {
					return ChinesePinyinComparator.compare(a.getName(),
							b.getName());
				}
			});
		}
		return modules;
	}

	/**
	 * 根据模块名获取模块路径
	 * 
	 * @param moduleName
	 *            如：{sys-rss:home.nav.sysRssMain}
	 *            因为无法通过其它信息获取模块路径，所以只能通过这种方式，但是需要moduleName的格式如上
	 * @return
	 */
	private static String getModuleUrlPrefix(String moduleName) {
		if (StringUtil.isNotNull(moduleName)) {
			moduleName = moduleName.replaceAll("\\{", "").replaceAll("\\}", "");
			int index = moduleName.indexOf(":");
			if (index > 0) {
				moduleName = moduleName.substring(0, index);
			}
			return "/" + (moduleName.replaceAll("-", "/")) + "/";
		}
		return null;
	}

	/****************** 门户的导入导出 Start *************************************/
	/****************** @author 吴进 by 20191028 ********************************/
	/**
	 * 解析出公共门户部件的ID值
	 * 
	 * @param doccontentList
	 * @return
	 */
	public static Map<String, String>
			analysisPortalPageDocContent(List<String> doccontentList) {
		if (doccontentList != null && !doccontentList.isEmpty()) {
			// 1、获取页面中所有<script type="text/config"></script>的内容
			List<String> script = new ArrayList<String>();
			for (String s : doccontentList) {
				Document document = Jsoup.parse(s);
				Elements elements = document.select("script");
				for (Element element : elements) {
					if ("text/config".equals(element.attr("type"))) {
						String text = element.data().toString();
						script.add(text);
					}
				}
			}

			// 2、内容转换成对象
			List<JPortal> jPortalList = new ArrayList<JPortal>();
			if (script != null && !script.isEmpty()) {
				for (String s : script) {
					JPortal portal = JSON.parseObject(s, JPortal.class);
					jPortalList.add(portal);
				}
			}

			// 3、找出属公共门户部件的ID值，对应所属模块
			Map<String, String> portletMap = new HashMap<String, String>();
			if (jPortalList != null && !jPortalList.isEmpty()) {
				for (JPortal jPortal : jPortalList) {
					List<Portlet> portletList = jPortal.getPortlet();
					if (portletList != null && !portletList.isEmpty()) {
						for (Portlet portlet : portletList) {
							String sourceId = portlet.getSourceId();
							SourceOpt sourceOpt = portlet.getSourceOpt();
							if (sourceOpt != null) {
								FdId fdId = sourceOpt.getFdId();
								if (fdId != null) {
									String fdid = fdId.getFdId();
									portletMap.put(fdid, sourceId);
								}
								Fdids fdids = sourceOpt.getFdids();
								if (fdids != null) {
									String fdid = fdids.getFdids();
									portletMap.put(fdid, sourceId);
								}
							}
						}
					}
				}
			}
			return portletMap;
		}
		return null;
	}

	public enum PortletModels {
		SHORTCUT("sys.portal.shortcut.source", "快捷方式"), LINKING(
				"sys.portal.linking.source",
				"常用链接"), TREEMENU2("sys.portal.treeMenu2.source",
						"二级树菜单"), TREEMENU3("sys.portal.treeMenu3.source",
								"三级树菜单"), SYSNAV("sys.portal.sysnav.source",
										"系统导航"), MAPTPL(
												"sys.portal.mapTpl.source",
												"地图模板"), TOPIC(
														"sys.portal.topic.source",
														"推荐专题"), HTML(
																"sys.portal.html.source",
																"自定义页面");

		private String code;
		private String desc;

		private PortletModels(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public static PortletModels typeOf(String code) {
			for (PortletModels c : PortletModels.values()) {
				if (c.getCode().equals(code)) {
					return c;
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}
	/****************** 门户的导入导出 End *************************************/

}
