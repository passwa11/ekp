package com.landray.kmss.sys.portal.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.dao.ISysPortalPortletDao;
import com.landray.kmss.sys.portal.model.SysPortalPortlet;
import com.landray.kmss.sys.portal.service.ISysPortalEnabledChooseCustom;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;
import com.sunbor.web.tag.Page;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 系统部件数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalPortletDaoImp extends BaseDaoImp implements
		ISysPortalPortletDao {

	/****************  根据模块业务逻辑判断组件是否可被选择 start  ***************/
	// 需要根据业务逻辑判断的组件信息
	private static List<FilterNode> component = new ArrayList<>();

	// 保存扩展点信息
	static {
		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.sys.portal.enabled.choose.custom");
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			if ("info".equals(extension.getAttribute("name"))) {
				String id = Plugin.getParamValueString(extension, "id");
				String serviceBeanName = Plugin.getParamValueString(extension, "serviceBeanName");
				if(StringUtil.isNotNull(id) && StringUtil.isNotNull(serviceBeanName)) {
                    component.add(new FilterNode(id, serviceBeanName));
                }
			}
		}
	}

	// 扩展点信息pojo
	private static class FilterNode {
		private String id;
		private String serviceBeanName; // 业务模块接口实现BeanName

		FilterNode (String id, String serviceBeanName) {
			this.id = id;
			this.serviceBeanName = serviceBeanName;
		}

		// 根据模块业务逻辑，判断是否可被选择
		public String enabledChoose(String id) {
			if(id.equals(this.id)) {
				ISysPortalEnabledChooseCustom sysPortalEnabledChooseCustom = (ISysPortalEnabledChooseCustom) SpringBeanUtil.getBean(serviceBeanName);
				if(sysPortalEnabledChooseCustom != null) {
                    if(sysPortalEnabledChooseCustom.enableChoose(null)) // 当前逻辑暂时没有用到传参
                    {
                        return "enabled"; // 业务模块判断是可被使用的
                    } else {
                        return "disEnabled"; // 业务模块判断是不可被使用的
                    }
                } else {
                    return "disEnabled"; // 配置出错，默认不能被使用
                }
			} else {
				return "ignore"; // 不是需要特殊判断的组件，就不管了
			}
		}
	}
	/****************  根据模块业务逻辑判断组件是否可被选择 end  ***************/

	private void filterList(String module, SysUiPortlet portal, String keyword,
			List<SysUiPortlet> filter) {
		if (module == null || "null".equalsIgnoreCase(module)
				|| "__all".equals(module)
				|| portal.getFdModule().equals(module)) {
			// 判断模块是否可用
			if (!TripartiteAdminUtil.isEnabled(getModuleUrlPrefix(portal.getFdModule()))) {
				// 如果模块不可用，就不加载信息
				return;
			}

			/*************************************/
			for(FilterNode node : component) {
				String result = node.enabledChoose(portal.getFdId());
				if("disEnabled".equals(result)) {
                    return;
                } else if("enabled".equals(result)) {
                    break;
                }
			}
			/*************************************/

			/***********************************************************/
			boolean bol = new File(PluginConfigLocationsUtil.getKmssConfigPath()
					+ "/kms/kmaps").exists();
			if (bol) {
				// 知识地图合并地图模板需过滤原地图模板部件
				String portletId = portal.getFdId();
				// 需要过滤的门户部件Id
				String[] arr = { "sys.portal.mapTpl" };
				if (Arrays.asList(arr).contains(portletId)) {
					return;
				}
			}
			/***********************************************************/
			if (StringUtil.isNotNull(keyword)) {
				keyword = keyword.trim();
				String name = ResourceUtil.getMessage(portal
						.getFdName());
				String mname = ResourceUtil
						.getMessage(portal.getFdModule());
				if ((StringUtil.isNotNull(name)
						&& name.toLowerCase().indexOf(
								keyword.toLowerCase()) >= 0)
						|| (StringUtil.isNotNull(mname) && mname
								.toLowerCase().indexOf(keyword
										.toLowerCase()) >= 0)) {
					filter.add(portal);
				}
			} else {
				filter.add(portal);
			}
		}
	}

	/**
	 * 根据模块名获取模块路径
	 * 
	 * @param moduleName
	 *            如：{sys-rss:home.nav.sysRssMain}
	 *            因为无法通过其它信息获取模块路径，所以只能通过这种方式，但是需要moduleName的格式如上
	 * @return
	 */
	private String getModuleUrlPrefix(String moduleName) {
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

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {

		List<SysUiPortlet> filter = new ArrayList<SysUiPortlet>();
		String _whereBlock = hqlInfo.getWhereBlock();
		String _key = hqlInfo.getKey();
		String[] whereBlock=null;
		String scene = "";
		String formate = "";
		Boolean fdAnonymous = null;
		if(StringUtil.isNotNull(_key)){
			fdAnonymous = "1".equals(_key);
		}
		if(_whereBlock.indexOf("//")>0){
			whereBlock=_whereBlock.split("//");
		if(whereBlock.length>1){
			scene=whereBlock[0];
			formate=whereBlock[1];
		}else{
			formate=whereBlock[0];
		}
		}else{
			scene=_whereBlock;
		}
		String modelName = hqlInfo.getModelName();
		String keyword = hqlInfo.getSelectBlock();
		List<SysUiSource> sources = SysUiPluginUtil.getSourceByFormat(scene,
				null);
		List<SysUiSource> _sources = new ArrayList<SysUiSource>();
		
		if(StringUtil.isNotNull(formate)){
			for (int i = 0; i < sources.size(); i++) {
				SysUiSource source = sources.get(i);
				String fdFormate=source.getFdFormat();
				if(StringUtil.isNotNull(fdFormate)){
					if(formate.toLowerCase().indexOf(fdFormate.toLowerCase())>-1){
						_sources.add(source);
					}
				}
			}
		}else{
			_sources=sources;
		}
		// 如果匿名不为空
		if(fdAnonymous != null){
			List<SysUiSource> _sourcesTemp = new ArrayList<SysUiSource>();
			for (int i = 0; i < _sources.size(); i++) {
				SysUiSource source = _sources.get(i);
				Boolean _fdAnonymous = (source.getFdAnonymous()==null||source.getFdAnonymous()==false)?false:true;
				if(fdAnonymous.equals(_fdAnonymous)){
					_sourcesTemp.add(source);
				}
			}
			//释放内存关联，垃圾回收时将进行回收
			_sources = null;
			//地址指向新的list
			_sources = _sourcesTemp;
		}

		for (int i = 0; i < _sources.size(); i++) {
			SysUiSource source = _sources.get(i);
			if (source.getFdId().endsWith(".source")) {
				String portalId = source.getFdId();
				portalId = portalId.substring(0, portalId.length() - 7);
				SysUiPortlet portal = SysUiPluginUtil.getPortletById(portalId);
				if (portal != null) {
					if (StringUtil.isNotNull(modelName)) {
						String[] modules = modelName.split(",");
						for (String module : modules) {
							filterList(module, portal, keyword, filter);
						}
					} else {
						filterList(modelName, portal, keyword, filter);
					}
				}
			}
		}

		final String orderBy = hqlInfo.getOrderBy();
		Collections.sort(filter, new Comparator<SysUiPortlet>() {
			@Override
			public int compare(SysUiPortlet a, SysUiPortlet b) {
				// 列表页面排序
				if("fdName".equals(orderBy)) {
                    return ChinesePinyinComparator.compare(ResourceUtil.getMessage(a.getFdName()), ResourceUtil.getMessage(b.getFdName()));
                }
				if("fdName desc".equals(orderBy)) {
                    return ChinesePinyinComparator.compare(ResourceUtil.getMessage(b.getFdName()), ResourceUtil.getMessage(a.getFdName()));
                }
				if("fdModule".equals(orderBy)) {
                    return ChinesePinyinComparator.compare(ResourceUtil.getMessage(a.getFdModule()), ResourceUtil.getMessage(b.getFdModule()));
                }
				if("fdModule desc".equals(orderBy)) {
                    return ChinesePinyinComparator.compare(ResourceUtil.getMessage(b.getFdModule()), ResourceUtil.getMessage(a.getFdModule()));
                }
				// 默认排序
				return ChinesePinyinComparator.compare(ResourceUtil.getMessage(a.getFdModule()) + ResourceUtil.getMessage(a.getFdName()),
						ResourceUtil.getMessage(b.getFdModule()) + ResourceUtil.getMessage(b.getFdName()));
			}
		});
		int total = filter.size();
		Page page = null;
		if (total > 0) {
			page = new Page();
			page.setRowsize(hqlInfo.getRowSize());
			page.setPageno(hqlInfo.getPageNo());
			page.setTotalrows(total);
			page.excecute();
			List list = new ArrayList();
			int loop = 0;
			Boolean allow = false;
			String fdModelName = "com.landray.kmss.sys.portal.model.SysPortalPortlet";
			if (UserOperHelper.allowLogOper("Action_FindList", fdModelName)) {
				allow = true;
				UserOperHelper.setModelNameAndModelDesc(fdModelName, ResourceUtil.getString("sys-portal:portal.system.components"));
			}
			for (int i = page.getStart(); i < total; i++) {
				if (loop < page.getRowsize()) {
					IBaseModel model = xmlToModel(filter.get(i));
					list.add(model);
					if(allow){
						SysPortalPortlet portlet = (SysPortalPortlet)model;
						UserOperContentHelper.putFind(portlet.getFdId(), portlet.getFdName(),fdModelName);
					}
				} else {
					break;
				}
				loop++;
			}
			page.setList(list);
		}
		if (page == null) {
			page = Page.getEmptyPage();
		}
		return page;
	}

	@Override
	public IBaseModel findByPrimaryKey(String id, Object modelInfo,
									   boolean noLazy) throws Exception {
		return xmlToModel(SysUiPluginUtil.getPortletById(id));
	}

	private IBaseModel xmlToModel(SysUiPortlet p) {
		SysPortalPortlet portlet = new SysPortalPortlet();
		portlet.setFdId(p.getFdId());
		portlet.setFdName(ResourceUtil.getMessage(p.getFdName()));
		portlet.setFdAnonymous(p.getFdAnonymous());
		String fdModule = p.getFdModule();
		if (fdModule.indexOf(SysUiConstant.SEPARATOR) > 0) {
			fdModule = fdModule.substring(
					fdModule.indexOf(SysUiConstant.SEPARATOR)
							+ SysUiConstant.SEPARATOR.length(),
					fdModule.length());
		}
		portlet.setFdModule(ResourceUtil.getMessage(fdModule));
		portlet.setFdFormat(SysUiPluginUtil.getSourceById(p.getFdSource())
				.getFdFormat());
		portlet.setFdSource(p.getFdSource());
		portlet.setFdDescription(ResourceUtil.getMessage(p.getFdDescription()));
		
		return portlet;
	}
}
