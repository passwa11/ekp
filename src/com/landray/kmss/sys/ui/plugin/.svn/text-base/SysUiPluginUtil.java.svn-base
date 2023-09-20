package com.landray.kmss.sys.ui.plugin;

import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.portal.xml.model.SysPortalHeader;
import com.landray.kmss.sys.ui.util.ThemeUtil;
import com.landray.kmss.sys.ui.xml.model.*;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SysUiPluginUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysUiPluginUtil.class);
	// 主题，模板，面板,数据格式，数据抓取，数据呈现
	private static volatile Map<String, SysUiTheme> themes = new HashMap<String, SysUiTheme>();
	private static volatile Map<String, SysUiTemplate> templates = new HashMap<String, SysUiTemplate>();
	private static volatile Map<String, SysUiLayout> layouts = new HashMap<String, SysUiLayout>();
	private static volatile Map<String, SysUiFormat> formats = new HashMap<String, SysUiFormat>();
	private static volatile Map<String, SysUiSource> sources = new HashMap<String, SysUiSource>();
	private static volatile Map<String, SysUiRender> renders = new HashMap<String, SysUiRender>();
	private static volatile Map<String, SysUiVarKind> varkinds = new HashMap<String, SysUiVarKind>();
	private static volatile Map<String, SysUiPortlet> portlets = new HashMap<String, SysUiPortlet>();
	private static volatile Map<String, SysUiAssembly> assemblies = new HashMap<String, SysUiAssembly>();
	private static volatile Map<String, SysUiMode> modes = new HashMap<String, SysUiMode>();
	private static volatile Map<String, SysUiExtend> inis = new HashMap<String, SysUiExtend>();
	private static volatile Map<String, SysUiComponent> components = new HashMap<String, SysUiComponent>();
	// 部件
	private static volatile Map<String, SysUiCombin> combins = new HashMap<String, SysUiCombin>(); 
	
	public static SysUiLayout getLayoutById(String id) {
		return layouts.get(id);
	}

	public static SysUiFormat getFormatById(String id) {
		return formats.get(id);
	}

	public static SysUiTheme getThemeById(String id) {
		SysUiTheme theme = themes.get(id);
		if (theme == null) {
			theme = themes.get("default");
			if (theme == null) {
				return SysUiTheme.getDefault();
			}
		}
		return theme;
	}

	public static SysUiTemplate getTemplateById(String id) {
		return templates.get(id);
	}

	public static SysUiRender getRenderById(String id) {
		return renders.get(id);
	}

	public static SysUiRender getPorgletSourceDefaultRender(String id) {
		SysUiRender render = getRenderById(getFormatById(
				getSourceById(getPortletById(id).getFdSource()).getFdFormat())
				.getFdDefaultRender());
		return render;
	}

	public static SysUiSource getSourceById(String id) {
		return sources.get(id);
	}

	public static SysUiVarKind getVarKindById(String id) {
		return varkinds.get(id);
	}

	public static SysUiPortlet getPortletById(String id) {
		return portlets.get(id);
	}

	public static SysUiExtend getExtendById(String id) {
		return inis.get(id);
	}

	public static SysUiAssembly getAssemblyById(String id) {
		return assemblies.get(id);
	}

	public static SysUiMode getModeById(String id) {
		return modes.get(id);
	}

	public static Map<String, SysUiTheme> getThemes() {
		return themes;
	}

	public static Map<String, SysUiTemplate> getTemplates() {
		return templates;
	}

	public static Map<String, SysUiLayout> getLayouts() {
		return layouts;
	}

	public static Map<String, SysUiFormat> getFormats() {
		return formats;
	}

	public static Map<String, SysUiRender> getRenders() {
		return renders;
	}

	public static Map<String, SysUiSource> getSources() {
		return sources;
	}

	public static Map<String, SysUiVarKind> getVarKinds() {
		return varkinds;
	}

	public static Map<String, SysUiPortlet> getPortlets() {
		return portlets;
	}

	public static Map<String, SysUiAssembly> getAssemblies() {
		return assemblies;
	}

	public static Map<String, SysUiExtend> getExtends() {
		return inis;
	}

	public static Map<String, SysUiComponent> getCompoenets() {
		return components;
	}

	public static Map<String, SysUiMode> getModes() {
		return modes;
	}

	public static Map<String, SysUiCombin> getCombins() {
		return combins;
	}

	public static List<SysUiSource> getSourceByFormat(String scene,
			String format) {
		String[] formats = null;
		if (StringUtil.isNotNull(format)
				&& !"null".equalsIgnoreCase(format.trim())) {
			formats = format.trim().split(";");
		}
		String[] scenes = null;
		if (StringUtil.isNotNull(scene)
				&& !"null".equalsIgnoreCase(scene.trim())) {
			scenes = scene.trim().split(";");
		}
		List<SysUiSource> list = new ArrayList<SysUiSource>();
		Collection<SysUiSource> all = sources.values();
		Iterator<SysUiSource> iter = all.iterator();
		while (iter.hasNext()) {
			SysUiSource source = iter.next();
			boolean isOk = false;
			if (formats == null && scenes == null) {
				isOk = true;
			} else if (formats == null && scenes != null) {
				/**
				 * 作废
				 * 这个是老代码，source中并没有定义for属性
				 * @author 吴进 by 20191115
				 */
//				// 只需要验证场景
//				if (StringUtil.isNull(source.getFdFor())) {
//					isOk = true;
//				} else {
//					String temp = ";" + source.getFdFor() + ";";
//					for (int j = 0; j < scenes.length; j++) {
//						if (temp.indexOf(";" + scenes[j] + ";") >= 0) {
//							isOk = true;
//							break;
//						}
//					}
//				}
				
				/**
				 * source.getFdAnonymous()优先取父级portlet.getFdAnonymous()的值
				 * @author 吴进 by 20191115
				 */
				if (!"anonymous".equals(scene) && (source.getFdAnonymous() == null || !source.getFdAnonymous())) {
					// 普通portlet过滤
					isOk = Boolean.TRUE;
				} else if ("anonymous".equals(scene) && source.getFdAnonymous()) {
					// 匿名portlet
					isOk = Boolean.TRUE;
				} else {
					continue;
				}
			} else if (formats != null && scenes == null) {
				// 只需要验证数据格式
				if (StringUtil.isNotNull(source.getFdFormat())) {
					String temp = ";" + source.getFdFormat() + ";";
					for (int j = 0; j < formats.length; j++) {
						if (temp.indexOf(";" + formats[j] + ";") >= 0) {
							isOk = true;
							break;
						}
					}
				}
			} else {
				// 都需要验证
				for (int j = 0; j < scenes.length; j++) {
					if (StringUtil.isNull(source.getFdFor())
							|| (";" + source.getFdFor() + ";").indexOf(";"
									+ scenes[j] + ";") >= 0) {
						for (int m = 0; m < formats.length; m++) {
							if (StringUtil.isNotNull(source.getFdFormat())) {
								if ((";" + source.getFdFormat() + ";")
										.indexOf(";" + formats[m] + ";") >= 0) {
									isOk = true;
									break;
								}
							}
						}
					}
				}
			}
			if (isOk) {
				list.add(source);
			}
		}
		return list;
	}

	public static List<SysUiRender> getRenderByFormat(String scene,
			String format) {
		String[] formats = null;
		if (StringUtil.isNotNull(format)
				&& !"null".equalsIgnoreCase(format.trim())) {
			formats = format.trim().split(";");
		}
		String[] scenes = null;
		if (StringUtil.isNotNull(scene)
				&& !"null".equalsIgnoreCase(scene.trim())) {
			scenes = scene.trim().split(";");
		}
		List<SysUiRender> list = new ArrayList<SysUiRender>();
		Collection<SysUiRender> all = renders.values();
		Iterator<SysUiRender> iter = all.iterator();
		while (iter.hasNext()) {
			SysUiRender render = iter.next();
			boolean isOk = false;
			if (formats == null && scenes == null) {
				isOk = true;
			} else if (formats == null && scenes != null) {
				// 只需要验证场景
				if (StringUtil.isNull(render.getFdFor())) {
					isOk = true;
				} else {
					String temp = ";" + render.getFdFor() + ";";
					for (int j = 0; j < scenes.length; j++) {
						if (temp.indexOf(";" + scenes[j] + ";") >= 0) {
							isOk = true;
							break;
						}
					}
				}
			} else if (formats != null && scenes == null) {
				// 只需要验证数据格式
				if (StringUtil.isNotNull(render.getFdFormat())) {
					String temp = ";" + render.getFdFormat() + ";";
					for (int j = 0; j < formats.length; j++) {
						if (temp.indexOf(";" + formats[j] + ";") >= 0) {
							isOk = true;
							break;
						}
					}
				}
			} else {
				// 都需要验证
				for (int j = 0; j < scenes.length; j++) {
					if (StringUtil.isNull(render.getFdFor())
							|| (";" + render.getFdFor() + ";").indexOf(";"
									+ scenes[j] + ";") >= 0) {
						for (int m = 0; m < formats.length; m++) {
							if (StringUtil.isNotNull(render.getFdFormat())) {
								if ((";" + render.getFdFormat() + ";")
										.indexOf(";" + formats[m] + ";") >= 0) {
									isOk = true;
									break;
								}
							}
						}
					}
				}
			}
			if (isOk) {
				list.add(render);
			}
		}
		return list;
	}
	public static String getThemes(HttpServletRequest request) {
		return ThemeUtil.getThemes(request);
	}
	public static String getThemesFileByName(HttpServletRequest request,String fileName) {
		return ThemeUtil.getThemesFileByName(request,fileName);
	}
	
	public static String getThemePath(HttpServletRequest request) {
		String theme = ThemeUtil.getThemeId(request);
		String def = "default";
		
		SysUiTheme defaultTheme = SysUiPluginUtil.getThemeById(def);
		SysUiTheme currentTheme = SysUiPluginUtil.getThemeById(theme);
		
		String path = null;
		if (theme.equals(def) || currentTheme == null) {
			path = defaultTheme.getFdPath();
		} else {
			path = currentTheme.getFdPath();
		}
		
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		
		return path;
	}

	/**
	 * 对外提供删除方法
	 * @param fdId
	 * @param uiType
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/7/28 10:20 上午
	 */
	public static synchronized void deleteUiExtend(String fdId, String uiType){
		logger.info("删除了部件,id:{},类型:{}", fdId, uiType);
		switch (uiType) {
			case "render":
				if(renders.get(fdId) != null){
					renders.remove(fdId);
				}
				break;
			case "layout":
				if(layouts.get(fdId) != null){
					layouts.remove(fdId);
				}
				break;
			case "header":
				if(PortalUtil.getPortalHeaders().get(fdId) != null){
					PortalUtil.getPortalHeaders().remove(fdId);
				}
				break;
			case "footer":
				if(PortalUtil.getPortalFooters().get(fdId) != null){
					PortalUtil.getPortalFooters().remove(fdId);
				}
				break;
			case "template":
				if(templates.get(fdId) != null){
					templates.remove(fdId);
				}
				break;
			case "theme":
				if(themes.get(fdId) != null){
					themes.remove(fdId);
				}
				if(inis.get(fdId) != null){
					inis.remove(fdId);
				}
				break;
			default:
				break;
		}


	}
}
