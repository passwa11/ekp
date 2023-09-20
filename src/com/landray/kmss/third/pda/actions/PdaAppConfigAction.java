package com.landray.kmss.third.pda.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.*;
import com.landray.kmss.third.pda.service.IPdaHomePageConfigService;
import com.landray.kmss.third.pda.service.IPdaModuleCateService;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.service.IPdaTabViewConfigMainService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.third.pda.util.PdaModuleConfigUtil;
import com.landray.kmss.third.pda.util.PdaPlugin;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PdaAppConfigAction extends BaseAction {

	protected IPdaModuleConfigMainService pdaModuleConfigMainService;

	/**
	 * 模块配置service
	 * 
	 * @param request
	 * @return
	 */
	protected IPdaModuleConfigMainService getPdaModuleConfigMainServiceImp() {
		if (pdaModuleConfigMainService == null) {
            pdaModuleConfigMainService = (IPdaModuleConfigMainService) SpringBeanUtil
                    .getBean("pdaModuleConfigMainService");
        }
		return pdaModuleConfigMainService;
	}

	protected IPdaHomePageConfigService pdaHomePageConfigService;

	/**
	 * ipad主页配置service
	 * 
	 * @param request
	 * @return
	 */
	protected IPdaHomePageConfigService getPdaHomePageConfigService() {
		if (pdaHomePageConfigService == null) {
            pdaHomePageConfigService = (IPdaHomePageConfigService) SpringBeanUtil
                    .getBean("pdaHomePageConfigService");
        }
		return pdaHomePageConfigService;
	}

	protected IPdaTabViewConfigMainService pdaTabViewConfigMainService;

	/**
	 * 功能区配置service
	 * 
	 * @return
	 */
	protected IPdaTabViewConfigMainService getPdaTabViewConfigMainService() {
		if (pdaTabViewConfigMainService == null) {
            pdaTabViewConfigMainService = (IPdaTabViewConfigMainService) SpringBeanUtil
                    .getBean("pdaTabViewConfigMainService");
        }
		return pdaTabViewConfigMainService;
	}
	
	protected IPdaModuleCateService pdaModuleCateService;


	protected IPdaModuleCateService getPdaModuleCateServiceImp() {
		if (pdaModuleCateService == null) {
            pdaModuleCateService = (IPdaModuleCateService) SpringBeanUtil
                    .getBean("pdaModuleCateService");
        }
		return pdaModuleCateService;
	}
	/**
	 * 图标,模块,主页版本信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward configVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/* 版本信息 */
		JSONObject configVersion = new JSONObject();
		configVersion.accumulate("menuVersion", this.getMenuVersion()); // 版本号
		configVersion.accumulate("iconVersion", this.getIconVersion()); // 图片版本
		configVersion.accumulate("homeVersion", this.getHomePageVersion()); // ipad主页配置,ipad用
		// 记录日志
		if (UserOperHelper.allowLogOper("configVersion", null)) {
			UserOperHelper.logMessage(configVersion.toString());
		}
		request.setAttribute("jsonDetail", configVersion.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}
	
	
	/**
	 * 根据路径跳转到模块
	 * /third/pda/third_pda_config/thirdPdaConfig.do?method=openByModulePath&modelpath=km/doc;sys/news
	 * */
	public ActionForward openByModulePath(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-open", true, getClass());
		KmssMessages messages = new KmssMessages();
		Boolean found = false;
		String modelpath = request.getParameter("modelpath");
		
	    if ((StringUtil.isNotNull(modelpath)) && (modelpath.length() == 32) && 
	    	      (!(modelpath.contains("/")))) {
	    	PdaModuleConfigMain model = (PdaModuleConfigMain)getPdaModuleConfigMainServiceImp().findByPrimaryKey(modelpath, null, true);
	    	if(model!=null){
	    	      found = Boolean.valueOf(true);
	    	      String url = "/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId=" + 
	    	        modelpath;
	    	      request.setAttribute("redirectto", url);
	    	      return new ActionForward("/resource/jsp/redirect.jsp");
	    	}
	    }				
		
		String[] modelpaths = modelpath.split(";");
        for(int i=0;i<modelpaths.length;i++){
        	HQLInfo info = new HQLInfo();
    		info.setSelectBlock("pdaModuleConfigMain.fdId");
    		info.setWhereBlock("pdaModuleConfigMain.fdStatus=:status and pdaModuleConfigMain.fdSubMenuType <>:type " +
    				            "and pdaModuleConfigMain.fdUrlPrefix =:modelpath");
    		info.setOrderBy("pdaModuleConfigMain.fdOrder asc");
    		info.setParameter("status", "1");
    		info.setParameter("type", PdaModuleConfigConstant.PDA_MENUS_APP);
    		info.setParameter("modelpath", modelpaths[i]);
    		// 使用权限过滤
    		//info.setCheckParam(SysAuthConstant.CheckType.AuthCheck,SysAuthConstant.AuthCheck.SYS_READER);
    		String fdId = (String) getPdaModuleConfigMainServiceImp().findFirstOne(info);
    		if(StringUtils.isNoneBlank(fdId)){
    			String url = "/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId="+fdId;
    			request.setAttribute("redirectto", url);
    			return new ActionForward("/resource/jsp/redirect.jsp");
    		}else{
    			continue;
    		}
		}
        return  mapping.findForward("notfound.4pda");
	}

	/**
	 * 模块详细配置信息 /third/pda/third_pda_config/thirdPdaConfig.do?method=menuDetail
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward menuDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject menuDetail = new JSONObject();
		// 版本号
		menuDetail.accumulate("menuVersion", this.getMenuVersion());
		// 菜单列表
		List<PdaModuleConfigMain> modules = getModules(request,"");
		JSONArray menus = new JSONArray();
		JSONArray cates = new JSONArray();
		for (PdaModuleConfigMain moduleConfigMain : modules) {
			JSONObject menu = new JSONObject();
			// 类型，标识这是一个模块，第一级的type必须为module
			menu.accumulate("type", PdaModuleConfigConstant.PDA_MENUS_MODULE);
			// 名称
			menu.accumulate("name", moduleConfigMain.getFdName());
			// 缓存在本地的图片名
			menu.accumulate("icon", PdaModuleConfigUtil
					.getFileName(moduleConfigMain.getFdIconUrl()));
			// 模块唯一标识,ipad用
			menu.accumulate("id", moduleConfigMain.getFdId());
			// 模块描述,ipad用
			if (StringUtil.isNotNull(moduleConfigMain.getFdDescription())) {
                menu.accumulate("description", moduleConfigMain
                        .getFdDescription());
            }
			// 该参数可选，获取文档个数（目前只有待办模块才有）
			if (StringUtil.isNotNull(moduleConfigMain.getFdCountUrl())) {
                menu.accumulate("countUrl", PdaFlagUtil.formatUrl(request,
                        moduleConfigMain.getFdCountUrl()));
            }

			// 子菜单类型
			String subMenuType = moduleConfigMain.getFdSubMenuType();
			// 判断该模块是否配置了功能区，是则启用tabview模式
			PdaTabViewConfigMain pdaTabViewConfigMain = getTabView(moduleConfigMain
					.getFdId());
			if (pdaTabViewConfigMain != null&&PdaFlagUtil.getPdaClientType(request)!=3) {
				// 功能区类型
				menu.accumulate("subMenuType",
						PdaModuleConfigConstant.PDA_MENUS_TABVIEW);
				menu.element("url", PdaFlagUtil.formatUrl(request,
						PdaModuleConfigConstant.PDA_TABVIEW_CONFIG_URL)
						+ "&fdId=" + pdaTabViewConfigMain.getFdId());
			} else {
				if (StringUtil.isNull(subMenuType)) {
                    subMenuType = PdaModuleConfigConstant.PDA_MENUS_LISTTAB;
                }
				if (PdaModuleConfigConstant.PDA_MENUS_MODULE
						.equalsIgnoreCase(subMenuType)) {
					// 子菜单是模块
					menu.accumulate("subMenuType",
							PdaModuleConfigConstant.PDA_MENUS_MODULE);
				} else if (PdaModuleConfigConstant.PDA_MENUS_DOC
						.equalsIgnoreCase(subMenuType)) {
					// 子菜单是链接
					menu.accumulate("subMenuType",
							PdaModuleConfigConstant.PDA_MENUS_DOC);
					menu.element("submenus", getModuleSubMenu(request,
							moduleConfigMain));
				} else if (PdaModuleConfigConstant.PDA_MENUS_APP
						.equalsIgnoreCase(subMenuType)) {
					// 子菜单是app
					JSONArray submenus = getModuleSubMenu(request,
							moduleConfigMain);
					if (!submenus.isEmpty()) {
						menu.accumulate("subMenuType",
								PdaModuleConfigConstant.PDA_MENUS_APP);
						menu.element("submenus", submenus);
					} else {
						menu = new JSONObject();
					}
				} else if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
						.equalsIgnoreCase(subMenuType)) {
					if ("1".equals(moduleConfigMain.getFdLinkerType())) {// 外部链接
						// 子菜单是EKP集成模块
						menu.accumulate("subMenuType",
								PdaModuleConfigConstant.PDA_MENUS_EKP);
						menu.element("url", PdaFlagUtil.formatUrl(request,
								moduleConfigMain.getFdEkpModuleUrl()));
					} else {
						// 子菜单是列表
						JSONArray submenus = getModuleSubMenu(request,
								moduleConfigMain);
						if (!submenus.isEmpty()) {
							// 子菜单类型
							if (submenus.size() > 1) {
								menu
										.accumulate(
												"subMenuType",
												PdaModuleConfigConstant.PDA_MENUS_LISTTAB);
							} else {
								menu.accumulate("subMenuType",
										PdaModuleConfigConstant.PDA_MENUS_LIST);
							}
							menu.element("submenus", submenus);
						}
					}
				}
			}

			// 这里处理的逻辑主要是 当菜单类型为第三方应用（APP）时候，只能在设置允许的客户端上显示;菜单类型为非APP类型的时候，全部显示
			if (!PdaModuleConfigConstant.PDA_MENUS_APP
					.equalsIgnoreCase(subMenuType)
					|| (PdaModuleConfigConstant.PDA_MENUS_APP
							.equalsIgnoreCase(subMenuType) && checkIsDisplayAppPro(
							request, moduleConfigMain))) {
				menus.element(menu);
			}
		}
		List<PdaModuleCate> modulecates = getModuleCates();
		for (PdaModuleCate moduleCate : modulecates) {
			JSONObject cate = new JSONObject();
			cate.accumulate("name", moduleCate.getFdName());
			JSONArray moduleIds = new JSONArray();
			// 菜单列表
			List<PdaModuleConfigMain> modulesByCate = getModules(request,moduleCate.getFdId());
			
			if(modulesByCate.size()>0){
				for (PdaModuleConfigMain moduleConfigMain : modulesByCate) {
					JSONObject moduleId = new JSONObject();
					moduleId.accumulate("id", moduleConfigMain.getFdId());
					moduleId.accumulate("name", moduleConfigMain.getFdName());
					moduleIds.element(moduleId);
				}
				cate.element("modules", moduleIds);
				cates.element(cate);
			}
		}
		menuDetail.element("cates", cates);
		menuDetail.element("menus", menus);
		request.setAttribute("jsonDetail", menuDetail.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}
	
	
	public ActionForward rightDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject rightDetail = new JSONObject();
		JSONArray rights = new JSONArray();
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("pdaModuleConfigMain.fdId");
		info.setWhereBlock("pdaModuleConfigMain.fdStatus=:status and pdaModuleConfigMain.fdSubMenuType!=:type");
		info.setOrderBy("pdaModuleConfigMain.fdOrder asc");
		info.setParameter("status", "1");
		info.setParameter("type", PdaModuleConfigConstant.PDA_MENUS_APP);
		// 使用权限过滤
		info.setCheckParam(SysAuthConstant.CheckType.AuthCheck,SysAuthConstant.AuthCheck.SYS_READER);
		List rightList = getPdaModuleConfigMainServiceImp().findList(info);
		for (Iterator iterator = rightList.iterator(); iterator.hasNext();) {
			String fdId = (String) iterator.next();
			JSONObject module = new JSONObject();
			module.accumulate("id", fdId);
			rights.element(module);
		}
		
		rightDetail.element("rights", rights);
		request.setAttribute("jsonDetail", rightDetail.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
		
	}
	
	
	/**
	 * 获取模块分类
	 */
	private List<PdaModuleCate> getModuleCates()
	throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(" pdaModuleCate.fdOrder asc");
		return getPdaModuleCateServiceImp().findList(hqlInfo);
	}
	
	

	/**
	 * 模块详细配置信息 /third/pda/third_pda_config/thirdPdaConfig.do?method=listModules
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject menuDetail = new JSONObject();
		// 版本号
		menuDetail.accumulate("updateTime", this.getMenuVersion());
		// 菜单列表
		List<PdaModuleConfigMain> modules = listtabAnddocModules(request,"");
		JSONArray menus = new JSONArray();
		JSONArray cates = new JSONArray();
		for (PdaModuleConfigMain moduleConfigMain : modules) {
			JSONObject module = new JSONObject();
			// 模块唯一标识,ipad用
			module.accumulate("id", moduleConfigMain.getFdId());
			// 名称
			module.accumulate("name", moduleConfigMain.getFdName());
			//英文名称
			module.accumulate("name-US", StringUtil.isNotNull(moduleConfigMain.getDynamicMap().get("fdNameUS"))
					? moduleConfigMain.getDynamicMap().get("fdNameUS") : "");
			//繁体名称
			module.accumulate("name-TW", StringUtil.isNotNull(moduleConfigMain.getDynamicMap().get("fdNameHK"))
					? moduleConfigMain.getDynamicMap().get("fdNameHK") : "");
			// 缓存在本地的图片名
			module.accumulate("icon", PdaModuleConfigUtil
					.getFileName(moduleConfigMain.getFdIconUrl()));
			// 模块描述,ipad用
			if (StringUtil.isNotNull(moduleConfigMain.getFdDescription())) {
                module.accumulate("description", moduleConfigMain
                        .getFdDescription());
            }
			// 该参数可选，获取文档个数（目前只有待办模块才有）
			if (StringUtil.isNotNull(moduleConfigMain.getFdCountUrl())) {
                module.accumulate("countUrl", PdaFlagUtil.formatUrl(request,
                        moduleConfigMain.getFdCountUrl()));
            }

			// 判断该模块是否配置了功能区，是则启用tabview模式
			PdaTabViewConfigMain pdaTabViewConfigMain = getTabView(moduleConfigMain
					.getFdId());
			if (pdaTabViewConfigMain != null) {
				// 功能区类型
				module.accumulate("type",
						PdaModuleConfigConstant.PDA_MENUS_TABVIEW);
				JSONArray submenus = new JSONArray();
				___buildTabViewDetail(pdaTabViewConfigMain.getFdId(), request,
						submenus);
				module.element("menus", submenus);
			} else {
				module.accumulate("type", moduleConfigMain.getFdSubMenuType());
				if (PdaModuleConfigConstant.PDA_MENUS_DOC
						.equalsIgnoreCase(moduleConfigMain.getFdSubMenuType())) {
					// 子菜单是链接
					module.accumulate("contentUrl", PdaFlagUtil.formatUrl(
							request, moduleConfigMain.getFdSubDocLink()));
				}
				if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
						.equalsIgnoreCase(moduleConfigMain.getFdSubMenuType())) {
					JSONArray submenus = getModuleSubMenu(request,
							moduleConfigMain);
					module.element("menus", submenus);
				}
			}
			// 类型
			menus.element(module);
		}
		List<PdaModuleCate> modulecates = getModuleCates();
		for (PdaModuleCate moduleCate : modulecates) {
			JSONObject cate = new JSONObject();
			cate.accumulate("name", moduleCate.getFdName());
			cate.accumulate("name-US", StringUtil.isNotNull(moduleCate.getDynamicMap().get("fdNameUS"))
					? moduleCate.getDynamicMap().get("fdNameUS") : "");
			JSONArray moduleIds = new JSONArray();
			// 菜单列表
			List<PdaModuleConfigMain> modulesByCate = listtabAnddocModules(request,moduleCate.getFdId());
			
			for (PdaModuleConfigMain moduleConfigMain : modulesByCate) {
				JSONObject moduleId = new JSONObject();
				moduleId.accumulate("id", moduleConfigMain.getFdId());
				moduleId.accumulate("name", moduleConfigMain.getFdName());
				moduleId.accumulate("name-US", StringUtil.isNotNull(moduleConfigMain.getDynamicMap().get("fdNameUS"))
						? moduleConfigMain.getDynamicMap().get("fdNameUS") : "");
				moduleIds.element(moduleId);
			}
			cate.element("modules", moduleIds);
			cates.element(cate);
		}
		menuDetail.element("cates", cates);
		menuDetail.element("module", menus);
		request.setAttribute("jsonDetail", menuDetail.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	/**
	 * 更新时间返回 /third/pda/third_pda_config/thirdPdaConfig.do?method=moduleVersion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward moduleVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject version = new JSONObject();
		version.accumulate("updateTime", this.getMenuVersion());
		request.setAttribute("jsonDetail", version.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	private void ___buildTabViewDetail(String fdId, HttpServletRequest request,
			JSONArray tabViewDetail) throws Exception {
		PdaTabViewConfigMain pdaTabViewConfigMain = (PdaTabViewConfigMain) getPdaTabViewConfigMainService()
				.findByPrimaryKey(fdId);
		List<PdaTabViewLabelList> pdaTabViewLabelList = pdaTabViewConfigMain
				.getFdLabelList();
		for (PdaTabViewLabelList pdaTabViewLabel : pdaTabViewLabelList) {
			if (!PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE
					.equalsIgnoreCase(pdaTabViewLabel.getFdStatus())) {
				continue;
			}
			JSONObject tag = new JSONObject();
			tag.accumulate("type", PdaModuleConfigConstant.PDA_MENUS_TABVIEW);
			tag.accumulate("name", pdaTabViewLabel.getFdTabName());
			String tagType = pdaTabViewLabel.getFdTabType();
			JSONArray submenus = new JSONArray();
			if ("module".equalsIgnoreCase(tagType)
					|| "home".equalsIgnoreCase(tagType)) {// 配置
				// 主页或模块module,home
				PdaModuleConfigMain moduleConfigMain = pdaTabViewLabel
						.getFdTabModule();
				if (moduleConfigMain != null) {
					JSONArray moduleTmp = getModuleSubMenu(request,
							moduleConfigMain);
					if (!moduleTmp.isEmpty()) {
						String tmpType = moduleConfigMain.getFdSubMenuType();
						if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
								.equalsIgnoreCase(tmpType)) {
							if (moduleTmp.size() == 1) {
								tmpType = PdaModuleConfigConstant.PDA_MENUS_LIST;
							}
						}
						tag.element("subMenuType", tmpType);
						submenus = moduleTmp;
					}
				}
			} else if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
					.equalsIgnoreCase(tagType)) {// 配置 列表标签页listTab
				tag.element("subMenuType", tagType);
				JSONObject listTab = new JSONObject();
				if (pdaTabViewLabel.getFdTabBean() != null) {
					IXMLDataBean bean = (IXMLDataBean) SpringBeanUtil
							.getBean(pdaTabViewLabel.getFdTabBean());
					List<JSONObject> dataList = bean
							.getDataList(new RequestContext(request));
					for (JSONObject lable : dataList) {
						submenus.element(lable);
					}
				}
				submenus.element(listTab);
			} else if (PdaModuleConfigConstant.PDA_MENUS_LIST
					.equalsIgnoreCase(tagType)
					|| PdaModuleConfigConstant.PDA_MENUS_LISTCATEGORY
							.equals(tagType)) {// 配置 列表页list,分类listcategory
				if (PdaModuleConfigConstant.PDA_MENUS_LISTCATEGORY
						.equals(tagType)) {
					tagType = PdaModuleConfigConstant.PDA_MENUS_LIST;
					tag
							.accumulate(
									"propertyFilterUrl",
									PdaFlagUtil
											.formatUrl(
													request,
													PdaModuleConfigConstant.PROPERTYFILTER_URL));
					tag.accumulate("icon", "tab_ico_2.png");
				}
				tag.element("subMenuType", tagType);
				JSONObject list = new JSONObject();
				list.element("type", tagType);
				list.element("name", pdaTabViewLabel.getFdTabName());
				list.element("contentUrl", PdaFlagUtil.formatUrl(request,
						pdaTabViewLabel.getFdTabUrl())
						+ "&isAppflag=1");
				submenus.element(list);
			} else if (PdaModuleConfigConstant.PDA_MENUS_SEARCH
					.equalsIgnoreCase(tagType)) {// 配置 搜索search
				tag.element("subMenuType", tagType);
				tag.element("ftSearchUrl", StringUtil.linkString(PdaFlagUtil
						.formatUrl(request,
								PdaModuleConfigConstant.FTSEARCH_URL), "&",
						"modelName=" + pdaTabViewLabel.getFdTabBean()));
				tag.accumulate("modelNameField", "modelName");
				tag.accumulate("keywordField", "keyword");
				tag.accumulate("icon", "tab_ico_3.png");
			} else if (PdaModuleConfigConstant.PDA_MENUS_DOC
					.equalsIgnoreCase(tagType)) {// 配置 文档页doc
				tag.element("subMenuType", tagType);
				JSONObject doc = new JSONObject();
				doc.element("type", tagType);
				doc.element("contentUrl", PdaFlagUtil.formatUrl(request,
						pdaTabViewLabel.getFdTabUrl())
						+ "&isAppflag=1");
				submenus.element(doc);
			}
			tag.element("submenus", submenus);
			if (tag.get("icon") == null) {
                tag.accumulate("icon", "tab_ico_1.png");
            }
			tabViewDetail.element(tag);
		}
	}

	/**
	 * 返回标签页类型菜单信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward tabViewDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			JSONArray tabViewDetail = new JSONArray();
			PdaTabViewConfigMain pdaTabViewConfigMain = (PdaTabViewConfigMain) getPdaTabViewConfigMainService()
					.findByPrimaryKey(fdId);
			List<PdaTabViewLabelList> pdaTabViewLabelList = pdaTabViewConfigMain
					.getFdLabelList();
			for (PdaTabViewLabelList pdaTabViewLabel : pdaTabViewLabelList) {
				if (!PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE
						.equalsIgnoreCase(pdaTabViewLabel.getFdStatus())) {
					continue;
				}
				JSONObject tag = new JSONObject();
				tag.accumulate("type",
						PdaModuleConfigConstant.PDA_MENUS_TABVIEW);
				tag.accumulate("name", pdaTabViewLabel.getFdTabName());
				String tagType = pdaTabViewLabel.getFdTabType();
				JSONArray submenus = new JSONArray();
				if ("module".equalsIgnoreCase(tagType)
						|| "home".equalsIgnoreCase(tagType)) {// 配置
					// 主页或模块module,home
					PdaModuleConfigMain moduleConfigMain = pdaTabViewLabel
							.getFdTabModule();
					if (moduleConfigMain != null) {
						JSONArray moduleTmp = getModuleSubMenu(request,
								moduleConfigMain);
						if (!moduleTmp.isEmpty()) {
							String tmpType = moduleConfigMain
									.getFdSubMenuType();
							if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
									.equalsIgnoreCase(tmpType)) {
								if (moduleTmp.size() == 1) {
									tmpType = PdaModuleConfigConstant.PDA_MENUS_LIST;
								}
							}
							tag.element("subMenuType", tmpType);
							submenus = moduleTmp;
						}
					}
				} else if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
						.equalsIgnoreCase(tagType)) {// 配置 列表标签页listTab
					tag.element("subMenuType", tagType);
					JSONObject listTab = new JSONObject();
					if (pdaTabViewLabel.getFdTabBean() != null) {
						IXMLDataBean bean = (IXMLDataBean) SpringBeanUtil
								.getBean(pdaTabViewLabel.getFdTabBean());
						List<JSONObject> dataList = bean
								.getDataList(new RequestContext(request));
						for (JSONObject lable : dataList) {
							submenus.element(lable);
						}
					}
					submenus.element(listTab);
				} else if (PdaModuleConfigConstant.PDA_MENUS_LIST
						.equalsIgnoreCase(tagType)
						|| PdaModuleConfigConstant.PDA_MENUS_LISTCATEGORY
								.equals(tagType)) {// 配置 列表页list,分类listcategory
					if (PdaModuleConfigConstant.PDA_MENUS_LISTCATEGORY
							.equals(tagType)) {
						tagType = PdaModuleConfigConstant.PDA_MENUS_LIST;
					}
					tag.element("subMenuType", tagType);
					JSONObject list = new JSONObject();
					list.element("type", tagType);
					list.element("name", pdaTabViewLabel.getFdTabName());
					list.element("contentUrl", PdaFlagUtil.formatUrl(request,
							pdaTabViewLabel.getFdTabUrl())
							+ "&isAppflag=1");
					submenus.element(list);
				} else if (PdaModuleConfigConstant.PDA_MENUS_SEARCH
						.equalsIgnoreCase(tagType)) {// 配置 搜索search
					tag.element("subMenuType", tagType);
					JSONObject search = new JSONObject();
					search.element("name", pdaTabViewLabel.getFdTabName());
					search.element("type", tagType);
					search.element("icon", StringUtil.isNotNull(pdaTabViewLabel
							.getFdTabIcon()) ? pdaTabViewLabel.getFdTabIcon()
							: "");
					search.element("modelName", pdaTabViewLabel.getFdTabBean());
					submenus.element(search);
				} else if (PdaModuleConfigConstant.PDA_MENUS_DOC
						.equalsIgnoreCase(tagType)) {// 配置 文档页doc
					tag.element("subMenuType", tagType);
					JSONObject doc = new JSONObject();
					doc.element("type", tagType);
					doc.element("contentUrl", PdaFlagUtil.formatUrl(request,
							pdaTabViewLabel.getFdTabUrl())
							+ "&isAppflag=1");
					submenus.element(doc);
				}
				tag.element("submenus", submenus);
				tag.accumulate("icon", StringUtil.isNotNull(pdaTabViewLabel
						.getFdTabIcon()) ? pdaTabViewLabel.getFdTabIcon() : "");
				tabViewDetail.element(tag);
			}
			request.setAttribute("jsonDetail", tabViewDetail);
		}
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	/**
	 * 获取模块子菜单详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward moduleDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		PdaModuleConfigMain moduleMain = null;
		if (StringUtil.isNotNull(fdId)) {
			moduleMain = (PdaModuleConfigMain) getPdaModuleConfigMainServiceImp()
					.findByPrimaryKey(fdId);
			JSONArray tabViewDetail = getModuleSubMenu(request, moduleMain);
			request.setAttribute("jsonDetail", tabViewDetail);
		}
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	/**
	 * 图标配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward iconDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject iconDetail = new JSONObject();
		/* 图片版本 */
		iconDetail.accumulate("iconVersion", this.getIconVersion());

		if (PdaFlagUtil.getPdaClientType(request) == PdaFlagUtil.PDA_FLAG_IPADAPP) {
			/* logo图片地址 */
			iconDetail.accumulate("logoUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_IPAD_LOGO_URL));
			/* 预加载图片地址 */
			iconDetail.accumulate("loadingUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_IPAD_LOADING_URL));
			/* 背景图片地址 */
			iconDetail.accumulate("backgroudUrl", PdaFlagUtil.formatUrl(
					request, PdaModuleConfigConstant.PDA_IPAD_BACK_URL));
		} else {
			/* logo图片地址 */
			iconDetail.accumulate("logoUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_LOGO_URL));

			/* 预加载图片地址 */
			iconDetail.accumulate("loadingUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_LOADING_URL));

			/* 背景图片地址 */
			iconDetail.accumulate("backgroudUrl", PdaFlagUtil.formatUrl(
					request, PdaModuleConfigConstant.PDA_BACK_URL));

			/* banner图 */
			iconDetail.accumulate("bannerUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_BANNER_URL));

			/* banner背景图 */
			iconDetail.accumulate("bannerBgUrl", PdaFlagUtil.formatUrl(request,
					PdaModuleConfigConstant.PDA_BANNER_BG_URL));
		}

		/* 对应的图片下载地址 */
		List<PdaModuleConfigMain> modules = getModules(request,"");
		JSONArray icons = new JSONArray();
		for (PdaModuleConfigMain moduleCfg : modules) {
			JSONObject icon = new JSONObject();
			icon.accumulate("url", PdaFlagUtil.formatUrl(request, moduleCfg
					.getFdIconUrl()));
			icons.element(icon);
		}
		Collection<File> files = PdaModuleConfigUtil.getListFiles(
				ConfigLocationsUtil.getWebContentPath()
						+ PdaModuleConfigUtil.ICON_DIR,
				PdaModuleConfigUtil.extensions);
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			String fileName = file.getName().toLowerCase();
			if (!(fileName.startsWith("module_") || fileName.startsWith("ico_"))) {
				JSONObject icon = new JSONObject();
				icon.accumulate("url", PdaFlagUtil.formatUrl(request,
						PdaModuleConfigUtil.ICON_DIR)
						+ "/" + file.getName());
				icons.element(icon);
			}
		}
		iconDetail.element("icons", icons);
		request.setAttribute("jsonDetail", iconDetail.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	/**
	 * ipad主页配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward homeDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<PdaHomePageConfig> list = getHomePages(request);
		JSONObject homeDetail = new JSONObject();
		homeDetail.accumulate("homeVersion", getHomePageVersion());
		if (list != null && list.size() > 0) {
			JSONArray homePages = new JSONArray();
			for (PdaHomePageConfig homePageConfig : list) {
				JSONObject homePage = new JSONObject();
				homePage.accumulate("name", homePageConfig.getFdName()); // 主页名
				homePage.accumulate("id", homePageConfig.getFdId()); // 主页id
				homePage.accumulate("rowsize",
						homePageConfig.getFdRowsize() == 0 ? 6 : homePageConfig
								.getFdRowsize()); // porlet条目数设置

				JSONArray portlets = new JSONArray();
				for (PdaHomePagePortlet homePagePortlet : homePageConfig
						.getFdPortlets()) { // porlet配置
					JSONObject portlet = new JSONObject();
					portlet.accumulate("type", homePagePortlet.getFdType()); // portlet类型
					portlet.accumulate("name", homePagePortlet.getFdName()); // portlet名称
					if (StringUtil.isNull(homePagePortlet.getFdLabelId())) {
                        portlet.accumulate("id", ""); // 所在页签唯一标识
                    } else {
                        portlet
                                .accumulate("id", homePagePortlet
                                        .getFdLabelId());
                    }
					if (StringUtil.isNotNull(homePagePortlet.getFdIconUrl())) {
                        portlet.accumulate("icon", homePagePortlet
                                .getFdIconUrl()); // portlet(暂为空)
                    }
					portlet.accumulate("fullRowFlag",
							"1".equals(homePagePortlet.getFdFullRowFlag()) ? "1" : "0");// 是否整行展示
					portlet.accumulate("moduleId", homePagePortlet
							.getFdModuleId()); // 所在模块id
					portlet.accumulate("contentUrl", PdaFlagUtil.formatUrl(
							request, homePagePortlet.getFdDataUrl())); // 数据URL
					portlets.element(portlet);
				}
				homePage.accumulate("portlets", portlets);
				homePages.element(homePage);
			}
			homeDetail.element("homePages", homePages);
		}
		request.setAttribute("jsonDetail", homeDetail.toString());
		setClearCache(response);
		return mapping.findForward("jsonDetail");
	}

	/**
	 * 返回版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getMenuVersion() throws Exception {
		PdaVersionConfig versionCfg = new PdaVersionConfig();
		if (versionCfg.getMenuVersion() == null) {
            return "";
        }
		return versionCfg.getMenuVersion();
	}

	/**
	 * 返回图片版本
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getIconVersion() throws Exception {
		PdaVersionConfig versionCfg = new PdaVersionConfig();
		if (versionCfg.getIconVersion() == null) {
            return "";
        }
		return versionCfg.getIconVersion();
	}

	/**
	 * 返回主页配置版本,ipad用
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getHomePageVersion() throws Exception {
		PdaVersionConfig versionCfg = new PdaVersionConfig();
		if (versionCfg.getHomePageVersion() == null) {
            return "";
        }
		return versionCfg.getHomePageVersion();
	}

	/**
	 * 返回模块配置信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List<PdaModuleConfigMain> getModules(HttpServletRequest request,String cateId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "pdaModuleConfigMain.fdStatus=:fdStatus and pdaModuleConfigMain.fdSubMenuType!=:fdSubType";
		hqlInfo.setParameter("fdStatus",
				PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdSubType",
				PdaModuleConfigConstant.PDA_MENUS_MODULE);
		if(StringUtil.isNotNull(cateId)){
			whereBlock += " and pdaModuleConfigMain.fdModuleCate.fdId = :fdCateId";
			hqlInfo.setParameter("fdCateId",cateId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(" pdaModuleConfigMain.fdOrder asc");
		return getPdaModuleConfigMainServiceImp().findValue(hqlInfo);
	}
	


	/**
	 * 返回模块类型为listTab（列表）和doc（文档）配置信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List<PdaModuleConfigMain> listtabAnddocModules(HttpServletRequest request,String cateId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "pdaModuleConfigMain.fdStatus=:fdStatus and (pdaModuleConfigMain.fdSubMenuType =:fdSubTypeListTab or pdaModuleConfigMain.fdSubMenuType =:fdSubTypeDoc)";
		hqlInfo.setParameter("fdStatus",
				PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdSubTypeListTab",
				PdaModuleConfigConstant.PDA_MENUS_LISTTAB);
		hqlInfo.setParameter("fdSubTypeDoc",
				PdaModuleConfigConstant.PDA_MENUS_DOC);
		if(StringUtil.isNotNull(cateId)){
			whereBlock += " and pdaModuleConfigMain.fdModuleCate.fdId = :fdCateId";
			hqlInfo.setParameter("fdCateId",cateId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(" pdaModuleConfigMain.fdOrder asc");
		return getPdaModuleConfigMainServiceImp().findValue(hqlInfo);
	}

	/**
	 * 返回ipad主页配置信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List<PdaHomePageConfig> getHomePages(HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setOrderBy("pdaHomePageConfig.fdOrder asc,pdaHomePageConfig.fdCreateTime desc");
		hqlInfo.setWhereBlock("pdaHomePageConfig.fdIsDefault='1'");
		List list = getPdaHomePageConfigService().findList(hqlInfo);
		return getPdaHomePageConfigService().findValue(hqlInfo);
	}

	/***
	 * 获取module详细子菜单配置
	 * 
	 * @param request
	 * @param moduleCfg
	 * @return
	 * @throws Exception
	 */
	private JSONArray getModuleSubMenu(HttpServletRequest request,
			PdaModuleConfigMain moduleCfg) throws Exception {
		JSONArray rtnArray = new JSONArray();
		String subMenuType = moduleCfg.getFdSubMenuType();
		if (PdaModuleConfigConstant.PDA_MENUS_MODULE
				.equalsIgnoreCase(subMenuType)) {
			// 模块子菜单是模块，暂不支持
		} else if (PdaModuleConfigConstant.PDA_MENUS_DOC
				.equalsIgnoreCase(subMenuType)) {
			// 模块子菜单是文档
			JSONObject doc = new JSONObject();
			doc.accumulate("type", PdaModuleConfigConstant.PDA_MENUS_DOC);
			doc.accumulate("contentUrl", PdaFlagUtil.formatUrl(request,
					moduleCfg.getFdSubDocLink()));
			rtnArray.element(doc);
		} else if (PdaModuleConfigConstant.PDA_MENUS_LISTTAB
				.equalsIgnoreCase(subMenuType)) {
			// 子菜单是列表
			List<PdaModuleLabelList> labelList = moduleCfg.getFdLabelList();
			String tmpType = PdaModuleConfigConstant.PDA_MENUS_LISTTAB;
			if (!labelList.isEmpty()) {
				if (labelList.size() == 1) {
					tmpType = PdaModuleConfigConstant.PDA_MENUS_LIST;
				}
				for (PdaModuleLabelList lable : labelList) {
					JSONObject listTab = new JSONObject();
					if ("1".equals(lable.getFdIsLink())) {
						// 类型，标识这是一个列表标签页
						listTab.accumulate("type",
								PdaModuleConfigConstant.PDA_MENUS_DOC);
					} else {
						// 类型，标识这是一个列表标签页
						listTab.accumulate("type", tmpType);
					}
					// 名称
					listTab.accumulate("name", lable.getFdName());
					// 唯一标识,ipad使用
					listTab.accumulate("id", lable.getFdId());
					// listTab.accumulate("icon", "");//缓存在本地的图片名
					// 可选，获取文档个数（目前只有待办模块才有）
					if (StringUtil.isNotNull(lable.getFdCountUrl())) {
						listTab.accumulate("countUrl", PdaFlagUtil.formatUrl(
								request, lable.getFdCountUrl()));
					}
					// 获取列表内容的URL
					listTab.accumulate("contentUrl", PdaFlagUtil.formatUrl(
							request, lable.getFdDataUrl()));
					// 获取列表对应创建文档URL
					String createUrl = PdaPlugin.getPdaExtendInfo(request,
							lable.getFdDataUrl(),
							PdaPlugin.PARAM_PDA_EXTEND_CREATEURL).get(
							PdaPlugin.PARAM_PDA_EXTEND_CREATEURL);
					if (StringUtil.isNotNull(createUrl)) {
						listTab.accumulate("createUrl", PdaFlagUtil.formatUrl(
								request,
								PdaModuleConfigConstant.PDA_CREATE_TAINSIT_URL)
								+ lable.getFdId() + "&isAppflag=1");
					}
					rtnArray.element(listTab);
				}
			}
		} else if (PdaModuleConfigConstant.PDA_MENUS_APP
				.equalsIgnoreCase(subMenuType)) {
			// 当前登录客户端是否需要显示某一设置应用
			boolean isDisplay = checkIsDisplayAppPro(request, moduleCfg);
			if (isDisplay == true) {
				JSONObject subApp = new JSONObject();
				subApp
						.accumulate("type",
								PdaModuleConfigConstant.PDA_MENUS_APP);
				// 应用类型
				subApp.accumulate("appType", moduleCfg.getFdAppType());
				// 应用对应的连接
				subApp.accumulate("contentUrl", PdaFlagUtil.formatUrl(request,
						moduleCfg.getFdUrlScheme()));
				// 应用下载地址
				subApp.accumulate("downLoadUrl", PdaFlagUtil.formatUrl(request,
						moduleCfg.getFdUrlDownLoad()));
				rtnArray.element(subApp);
			}
		}
		return rtnArray;
	}

	/**
	 * 当前登录客户端是否需要显示某一设置应用
	 * 
	 * @param HttpServletRequest
	 *            request 当前request请求对象
	 * @param PdaModuleConfigMain
	 *            moduleCfg 当前模块配置对象
	 * @return boolean 是否允许在当前登录的客户端上，显示某一应用 true :允许 false：不允许 缺省值为false（不允许）
	 */
	private boolean checkIsDisplayAppPro(HttpServletRequest request,
			PdaModuleConfigMain moduleCfg) {
		// 获取当前手机访问类型, 调用前提是必须确保访问客户端含UA信息
		// 0:普通手机web访问标识(缺省值为0)
		// 1:iPhone或iPad中浏览器访问标识
		// 2:iPhone应用端标识
		// 3:iPad应用端标识
		// 4:android应用端标识
		// 5:android宽频应用端标识
		int pdaFlag = PdaFlagUtil.getPdaClientType(request);

		// 0. 苹果应用 1.ipda应用 2.安卓应用
		String appType = moduleCfg.getFdAppType();

		boolean isDisplay = checkIsDisplayAppPro(pdaFlag, appType);
		return isDisplay;
	}

	/**
	 * 当前登录客户端是否需要显示某一设置应用
	 * 
	 * @param int pdaFlag 当前登录的客户端类型
	 * @param String
	 *            appType 当前设置的允许显示应用的客户端类型
	 * @return boolean 是否允许在当前登录的客户端上，显示某一应用 true :允许 false：不允许 缺省值为false（不允许）
	 */
	private boolean checkIsDisplayAppPro(int pdaFlag, String appType) {
		boolean isDisplay = false;

		// 当前只在iPhone应用端显示应用
		if (pdaFlag == PdaFlagUtil.PDA_FLAG_IPHONEAPP && "0".equals(appType)) {
			isDisplay = true;
		}

		// 当前只在iPad应用端显示应用
		if (pdaFlag == PdaFlagUtil.PDA_FLAG_IPADAPP && "1".equals(appType)) {
			isDisplay = true;
		}

		// 当前只在android应用端或者android宽频应用端显示应用
		if ((pdaFlag == PdaFlagUtil.PDA_FLAG_ANDROIDAPP || pdaFlag == PdaFlagUtil.PDA_FLAG_ANDROIDPADAPP)
				&& "2".equals(appType)) {
			isDisplay = true;
		}
		return isDisplay;
	}

	/**
	 * 设置不需要缓存
	 * 
	 * @param response
	 */
	private void setClearCache(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
	}

	/**
	 * 根据模块配置id获取功能区信息
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	private PdaTabViewConfigMain getTabView(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaTabViewConfigMain.fdStatus=:fdStatus "
				+ "and pdaTabViewConfigMain.fdModule.fdId =:fdId");
		hqlInfo.setParameter("fdStatus",
				PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setOrderBy(" pdaTabViewConfigMain.fdOrder asc");
		return (PdaTabViewConfigMain) getPdaTabViewConfigMainService()
				.findFirstOne(hqlInfo);
	}
}
