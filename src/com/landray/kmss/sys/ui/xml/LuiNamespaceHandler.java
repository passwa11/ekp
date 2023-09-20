package com.landray.kmss.sys.ui.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.config.design.SysCfgPortlet;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.xml.NamespaceHandlerSupport;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.IniUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiCode;
import com.landray.kmss.sys.ui.xml.model.SysUiExtend;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.sys.ui.xml.parser.AssemblyElementParser;
import com.landray.kmss.sys.ui.xml.parser.CombinElementParser;
import com.landray.kmss.sys.ui.xml.parser.FormatElementParser;
import com.landray.kmss.sys.ui.xml.parser.LayoutElementParser;
import com.landray.kmss.sys.ui.xml.parser.PortletElementParser;
import com.landray.kmss.sys.ui.xml.parser.RenderElementParser;
import com.landray.kmss.sys.ui.xml.parser.SourceElementParser;
import com.landray.kmss.sys.ui.xml.parser.TemplateElementParser;
import com.landray.kmss.sys.ui.xml.parser.ThemeElementParser;
import com.landray.kmss.sys.ui.xml.parser.VarKindElementParser;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.filter.ResourceCacheFilter;

public class LuiNamespaceHandler extends NamespaceHandlerSupport {

	@Override
    public void init() {
		registerBeanDefinitionParser("assembly", new AssemblyElementParser());
		registerBeanDefinitionParser("combin", new CombinElementParser());
		registerBeanDefinitionParser("format", new FormatElementParser());
		registerBeanDefinitionParser("layout", new LayoutElementParser());
		registerBeanDefinitionParser("portlet", new PortletElementParser());
		registerBeanDefinitionParser("render", new RenderElementParser());
		registerBeanDefinitionParser("source", new SourceElementParser());
		registerBeanDefinitionParser("template", new TemplateElementParser());
		registerBeanDefinitionParser("theme", new ThemeElementParser());
		registerBeanDefinitionParser("var-kind", new VarKindElementParser());
	}

	@Override
    public void beforeLoad() {
		SysUiPluginUtil.getAssemblies().clear();
		SysUiPluginUtil.getThemes().clear();
		SysUiPluginUtil.getSources().clear();
		SysUiPluginUtil.getRenders().clear();
		SysUiPluginUtil.getFormats().clear();
		SysUiPluginUtil.getTemplates().clear();
		SysUiPluginUtil.getLayouts().clear();
		SysUiPluginUtil.getVarKinds().clear();
		SysUiPluginUtil.getPortlets().clear();
		SysUiPluginUtil.getCombins().clear();
		SysUiPluginUtil.getExtends().clear();
	}

	@Override
    @SuppressWarnings("unchecked")
	public void afterLoad() {
		loadUiExtend();
		convertPortlet();
		ResourceCacheFilter.cache = String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 
	 * 加载扩展资源定义信息
	 * 
	 * */
	private void loadUiExtend() {
		List<String> inis = new ArrayList<String>();
		File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/"
				+ XmlReaderContext.UIEXT);
		if (file.exists()) {
			File[] uiext = file.listFiles();
			for (int i = 0; i < uiext.length; i++) {
				File uixml = new File(uiext[i].getAbsolutePath() + "/"
						+ "ui.ini");
				if (uixml.exists()) {
					inis.add(uixml.getAbsolutePath());
				}
			}
		}
		for (int i = 0; i < inis.size(); i++) {
			try {
				Map<String, String> item;
				item = IniUtil.loadIniFile(new File(inis.get(i)));
				String id = item.get("id");
				String name = item.get("name");
				String thumb = item.get("thumb");
				String help = item.get("help");

				SysUiPluginUtil.getExtends().put(id,
						new SysUiExtend(id, name, thumb, help));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 兼容旧的Design.xml文件里面的portlet配置信息
	 */
	private void convertPortlet() {
		Map<String, SysUiPortlet> uiportlets = SysUiPluginUtil.getPortlets();
		Map<String, SysUiSource> uisources = SysUiPluginUtil.getSources();
		Collection modules = SysConfigs.getInstance().getPortlets().values();
		for (Object module : modules) {
			List<SysCfgPortlet> portlets = (List<SysCfgPortlet>) module;
			for (SysCfgPortlet portlet : portlets) {
				SysUiSource source = tranSource(portlet);
				if (source == null) {
					continue;
				}
				uisources.put(source.getFdId(), source);
				SysUiPortlet uiportlet = tranPortlet(portlet, source);
				uiportlets.put(uiportlet.getFdId(), uiportlet);
			}
		}
	}

	/**
	 * 把旧portlet转换成source
	 * 
	 * @param portlet
	 * @return
	 */
	private SysUiSource tranSource(SysCfgPortlet portlet) {
		String id = "_" + portlet.getMessageKey() + ".source";
		String name = "{" + portlet.getMessageKey() + "}";
		String format = null;
		String type = null;
		String url = null;
		SysUiCode code = new SysUiCode();
		if (StringUtil.isNotNull(portlet.getContentBean())) {
			format = "sys.ui.classic";
			type = "lui/data/source!AjaxXml";
			url = portlet.getContentBean().trim();
			code.setBody("{\"url\":\"/sys/common/dataxml.jsp?s_bean=" + url
					+ "\"}");
		} else if (StringUtil.isNotNull(portlet.getContentURL())) {
			format = "sys.ui.iframe";
			type = "lui/data/source!Static";
			url = portlet.getContentURL().trim();
			code.setBody("{\"src\":\"" + url + "\"}");
		} else {
			return null;
		}
		String[] formats = format.split(";");
		SysUiSource source = new SysUiSource(id, name, formats[0], code, type,
				null, null, null, formats, null);

		List<SysUiVar> vars = new ArrayList<SysUiVar>();
		if (url.indexOf("!{rowsize}") > -1) {
			SysUiVar var = new SysUiVar("rowsize", "{portlet.rowsize}", "rowsize", null,
					null, null);
			vars.add(var);
		}
		if (StringUtil.isNotNull(portlet.getCateBean())) {
			SysUiVar var = new SysUiVar("cateid", "{portlet.cate}", "sys.tree",
					"{\"bean\":\"" + portlet.getCateBean().trim()
							+ "\",\"name\":\"{sys-ui:portlet.select}\"}",
					null, null);
			vars.add(var);
		} else if (StringUtil.isNotNull(portlet.getTemplateClass())) {
			String cateType = null;
			if (SysConfigs.getInstance().getCategoryMngs()
					.get(portlet.getTemplateClass()) != null) {
				cateType = "sys.category";
			} else {
				cateType = "sys.simplecategory";
			}
			SysUiVar var = new SysUiVar("cateid", "{portlet.cate}", cateType,
					"{\"model\":\"" + portlet.getTemplateClass().trim()
							+ "\",\"name\":\"{sys-ui:portlet.selectCate}\"}",
					null, null);
			vars.add(var);
		}
		source.setFdVars(vars);
		return source;
	}

	/**
	 * 把旧portlet转换成新的portlet
	 * 
	 * @param portlet
	 * @param source
	 * @return
	 */
	private SysUiPortlet tranPortlet(SysCfgPortlet portlet, SysUiSource source) {
		SysUiPortlet uiportlet = new SysUiPortlet(
				"_" + portlet.getMessageKey(), source.getFdNameKey(), "{"
						+ portlet.getModuleKey() + "}");
		uiportlet.setFdVars(source.getFdVars());
		List<SysUiOperation> operations = new ArrayList<SysUiOperation>();
		if (StringUtil.isNotNull(portlet.getCreateURL())) {
			SysUiOperation operation = new SysUiOperation("{operation.create}", portlet
					.getCreateURL().trim(), "_blank", null, "create", "right");
			operations.add(operation);
		}
		if (StringUtil.isNotNull(portlet.getMoreURL())) {
			SysUiOperation operation = new SysUiOperation("{operation.more}", portlet
					.getMoreURL().trim(), "_blank", null, "more", "right");
			operations.add(operation);
		}
		uiportlet.setFdOperations(operations);
		return uiportlet;
	}
}
