package com.landray.kmss.sys.portal.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.portal.forms.SysPortalHtmlForm;
import com.landray.kmss.sys.portal.forms.SysPortalLinkDetailForm;
import com.landray.kmss.sys.portal.forms.SysPortalLinkForm;
import com.landray.kmss.sys.portal.forms.SysPortalMapInletForm;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplForm;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplNavCustomForm;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplNavForm;
import com.landray.kmss.sys.portal.forms.SysPortalNavForm;
import com.landray.kmss.sys.portal.forms.SysPortalTopicForm;
import com.landray.kmss.sys.portal.forms.SysPortalTreeForm;
import com.landray.kmss.sys.portal.model.SysPortalHtml;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.sys.portal.model.SysPortalTree;
import com.landray.kmss.sys.portal.service.ISysPortalHtmlService;
import com.landray.kmss.sys.portal.service.ISysPortalLinkService;
import com.landray.kmss.sys.portal.service.ISysPortalMapTplService;
import com.landray.kmss.sys.portal.service.ISysPortalNavService;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.sys.portal.service.ISysPortalTreeService;
import com.landray.kmss.sys.portal.util.SysPortalUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 页面初始化数据导入导出
 * @author 吴进 by 20191029
 *
 */
public class SysPortalPageDataInit extends BaseServiceImp implements ISysDatainitSurroundInterceptor {
	
	// 快捷方式与常用链接
	private ISysPortalLinkService sysPortalLinkService;
	// 系统导航
	private ISysPortalNavService sysPortalNavService;
	// 多级树菜单（二级树与三级树）
	private ISysPortalTreeService sysPortalTreeService;
	// 地图模板
	private ISysPortalMapTplService sysPortalMapTplService;
	// 推荐专题
	private ISysPortalTopicService sysPortalTopicService;
	// 自定义页面
	private ISysPortalHtmlService sysPortalHtmlService;

	public void setSysPortalLinkService(ISysPortalLinkService sysPortalLinkService) {
		this.sysPortalLinkService = sysPortalLinkService;
	}

	public void setSysPortalNavService(ISysPortalNavService sysPortalNavService) {
		this.sysPortalNavService = sysPortalNavService;
	}

	public void setSysPortalTreeService(ISysPortalTreeService sysPortalTreeService) {
		this.sysPortalTreeService = sysPortalTreeService;
	}

	public void setSysPortalMapTplService(ISysPortalMapTplService sysPortalMapTplService) {
		this.sysPortalMapTplService = sysPortalMapTplService;
	}

	public void setSysPortalTopicService(ISysPortalTopicService sysPortalTopicService) {
		this.sysPortalTopicService = sysPortalTopicService;
	}

	public void setSysPortalHtmlService(ISysPortalHtmlService sysPortalHtmlService) {
		this.sysPortalHtmlService = sysPortalHtmlService;
	}
	
	/**
	 * 导出
	 */
	@Override
	public void beforeStoreModelData(IBaseModel model, Map<String, Object> data) throws Exception {		
		if (model instanceof SysPortalPage) {
			SysPortalPage spp = (SysPortalPage) model;
			
			// 1、遍历出所有页面明细的doc_content值
			List<String> docContentList = new ArrayList<>();
			List<SysPortalPageDetail> sysPortalPageDetailList = spp.getPageDetails();
			if (sysPortalPageDetailList != null && !sysPortalPageDetailList.isEmpty()) {
				for (SysPortalPageDetail sysPortalPageDetail : sysPortalPageDetailList) {
					String docContent = sysPortalPageDetail.getDocContent();
					if (StringUtil.isNotNull(docContent)) {
						docContentList.add(docContent);
					}
				}
			}
			
			// 2、解析出公共门户部件的ID值
			Map<String, String> docContentMap = SysPortalUtil.analysisPortalPageDocContent(docContentList);
		
			// 3、获取公共门户部件数据
			
			// 快捷方式与常用链接
			List<SysPortalLinkForm> sysPortalLinkFormList = new ArrayList<>();
			// 多级数菜单
			List<SysPortalTreeForm> sysPortalTreeFormList = new ArrayList<>();
			// 系统导航
			List<SysPortalNavForm> sysPortalNavFormList = new ArrayList<>();
			// 地图模板
			List<SysPortalMapTplForm> sysPortalMapTplFormList = new ArrayList<>();
			// 推荐专题
			List<SysPortalTopicForm> sysPortalTopicFormList = new ArrayList<>();
			// 自定义页面
			List<SysPortalHtmlForm> sysPortalHtmlFormList = new ArrayList<>();
			
			if (docContentMap != null && !docContentMap.isEmpty()) {
				Set<Map.Entry<String, String>> sets = docContentMap.entrySet();
				for (Entry<String, String> entry : sets) {
					String key = entry.getKey();
					String value = entry.getValue();
					
					if (SysPortalUtil.PortletModels.SHORTCUT.getCode().equals(value)) {
						SysPortalLinkForm sysPortalLinkForm = getSysPortalLinkForm(key);
						if (sysPortalLinkForm != null) {
							List links = sysPortalLinkForm.getFdLinks();
							if (links == null || links.isEmpty()) {
								sysPortalLinkForm.setFdLinks(null);
							}
							sysPortalLinkFormList.add(sysPortalLinkForm);
						}
					} else if (SysPortalUtil.PortletModels.LINKING.getCode().equals(value)) {
						SysPortalLinkForm sysPortalLinkForm = getSysPortalLinkForm(key);
						if (sysPortalLinkForm != null) {
							List links = sysPortalLinkForm.getFdLinks();
							if (links == null || links.isEmpty()) {
								sysPortalLinkForm.setFdLinks(null);
							}
							sysPortalLinkFormList.add(sysPortalLinkForm);
						}
					} else if (SysPortalUtil.PortletModels.TREEMENU2.getCode().equals(value)) {
						SysPortalTreeForm sysPortalTreeForm = getSysPortalTreeForm(key);
						if (sysPortalTreeForm != null) {
							sysPortalTreeFormList.add(sysPortalTreeForm);
						}
					} else if (SysPortalUtil.PortletModels.TREEMENU3.getCode().equals(value)) {
						SysPortalTreeForm sysPortalTreeForm = getSysPortalTreeForm(key);
						if (sysPortalTreeForm != null) {
							sysPortalTreeFormList.add(sysPortalTreeForm);
						}
					} else if (SysPortalUtil.PortletModels.SYSNAV.getCode().equals(value)) {
						SysPortalNavForm sysPortalNavForm = getSysPortalNavForm(key);
						if (sysPortalNavForm != null) {
							sysPortalNavFormList.add(sysPortalNavForm);
						}
					} else if (SysPortalUtil.PortletModels.MAPTPL.getCode().equals(value)) {
						SysPortalMapTplForm sysPortalMapTplForm = getSysPortalMapTplForm(key);
						if (sysPortalMapTplForm != null) {
							List listFdPortalNavForms = sysPortalMapTplForm.getFdPortalNavForms();
							List listFdMapInletForms = sysPortalMapTplForm.getFdMapInletForms();
							List listFdNavCustomForms = sysPortalMapTplForm.getFdNavCustomForms();
							if (listFdPortalNavForms == null || listFdPortalNavForms.isEmpty()) {
								sysPortalMapTplForm.setFdPortalNavForms(null);
							}
							if (listFdMapInletForms == null || listFdMapInletForms.isEmpty()) {
								sysPortalMapTplForm.setFdMapInletForms(null);
							}
							if (listFdNavCustomForms == null || listFdNavCustomForms.isEmpty()) {
								sysPortalMapTplForm.setFdNavCustomForms(null);
							}
							sysPortalMapTplFormList.add(sysPortalMapTplForm);
						}
					} else if (SysPortalUtil.PortletModels.TOPIC.getCode().equals(value)) {
						SysPortalTopicForm sysPortalTopicForm = getSysPortalTopicForm(key);
						if (sysPortalTopicForm != null) {
							sysPortalTopicFormList.add(sysPortalTopicForm);
						}
					} else if (SysPortalUtil.PortletModels.HTML.getCode().equals(value)) {
						SysPortalHtmlForm sysPortalHtmlForm = getSysPortalHtmlForm(key);
						if (sysPortalHtmlForm != null) {
							sysPortalHtmlFormList.add(sysPortalHtmlForm);
						}
					}
				}
			}
			
			// 5、页面含的公共部件对象封装
			JSONObject jsonObject = new JSONObject();
			if (sysPortalLinkFormList != null && !sysPortalLinkFormList.isEmpty()) {
				jsonObject.put("sysPortalLinkFormList", JSONArray.toJSON(sysPortalLinkFormList));
			}
			if (sysPortalTreeFormList != null && !sysPortalTreeFormList.isEmpty()) {
				jsonObject.put("sysPortalTreeFormList", JSONArray.toJSON(sysPortalTreeFormList));
			}
			if (sysPortalNavFormList != null && !sysPortalNavFormList.isEmpty()) {
				jsonObject.put("sysPortalNavFormList", JSONArray.toJSON(sysPortalNavFormList));
			}
			if (sysPortalMapTplFormList != null && !sysPortalMapTplFormList.isEmpty()) {
				JSONArray array = (JSONArray) JSONArray.toJSON(sysPortalMapTplFormList);
				for (int i = 0, n = array.size(); i < n; i++) {
					JSONObject jsonObj = (JSONObject) array.get(i);
					jsonObj.remove("attachmentForms");
				}
				jsonObject.put("sysPortalMapTplFormList", array);
			}
			if (sysPortalTopicFormList != null && !sysPortalTopicFormList.isEmpty()) {
				for (SysPortalTopicForm form : sysPortalTopicFormList) {
					form.getAttachmentForms().clear();
				}
				JSONArray array = (JSONArray) JSONArray.toJSON(sysPortalTopicFormList);
				for (int i = 0, n = array.size(); i < n; i++) {
					JSONObject jsonObj = (JSONObject) array.get(i);
					jsonObj.remove("attachmentForms");
				}
				jsonObject.put("sysPortalTopicFormList", array);
			}
			if (sysPortalHtmlFormList != null && !sysPortalHtmlFormList.isEmpty()) {
				jsonObject.put("sysPortalHtmlFormList", JSONArray.toJSON(sysPortalHtmlFormList));
			}
			
			// 6、导出扩展字段
			data.put("sysPortalPagePortlet", jsonObject.toJSONString());
		}
	}

	/**
	 * 导入
	 */
	@Override
	public void beforeRestoreModelData(IBaseModel model, Map<String, Object> data, Map<String, IBaseModel> cache,
			ProcessRuntime processRuntime) throws Exception {
		if (model instanceof SysPortalPage) {
			Object object = data.get("sysPortalPagePortlet");
			if (object != null && (object instanceof String)) {
				String input = (String) object;
				Object objContent = JSONObject.parseObject(input);
				
				// 快捷方式与常用链接
				List<SysPortalLinkForm> sysPortalLinkFormList = null;
				// 多级数菜单
				List<SysPortalTreeForm> sysPortalTreeFormList = null;
				// 系统导航
				List<SysPortalNavForm> sysPortalNavFormList = null;
				// 地图模板
				List<SysPortalMapTplForm> sysPortalMapTplFormList = null;
				// 推荐专题
				List<SysPortalTopicForm> sysPortalTopicFormList = null;
				// 自定义页面
				List<SysPortalHtmlForm> sysPortalHtmlFormList = null;
				
				if (objContent instanceof Map) {
					Map map = (Map) objContent;
					Object sysPortalLinkFormListObj = map.get("sysPortalLinkFormList");
					Object sysPortalTreeFormListObj = map.get("sysPortalTreeFormList");
					Object sysPortalNavFormListObj = map.get("sysPortalNavFormList");
					Object sysPortalMapTplFormListObj = map.get("sysPortalMapTplFormList");
					Object sysPortalTopicFormListObj = map.get("sysPortalTopicFormList");
					Object sysPortalHtmlFormListObj = map.get("sysPortalHtmlFormList");
					
					if (sysPortalLinkFormListObj != null && sysPortalLinkFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalLinkFormListObj;
						sysPortalLinkFormList = array.toJavaList(SysPortalLinkForm.class);
					}
					if (sysPortalTreeFormListObj != null && sysPortalTreeFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalTreeFormListObj;
						sysPortalTreeFormList = array.toJavaList(SysPortalTreeForm.class);
					}
					if (sysPortalNavFormListObj != null && sysPortalNavFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalNavFormListObj;
						sysPortalNavFormList = array.toJavaList(SysPortalNavForm.class);
					}
					if (sysPortalMapTplFormListObj != null && sysPortalMapTplFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalMapTplFormListObj;
						sysPortalMapTplFormList = array.toJavaList(SysPortalMapTplForm.class);
					}
					if (sysPortalTopicFormListObj != null && sysPortalTopicFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalTopicFormListObj;
						sysPortalTopicFormList = array.toJavaList(SysPortalTopicForm.class);
					}
					if (sysPortalHtmlFormListObj != null && sysPortalHtmlFormListObj instanceof JSONArray) {
						JSONArray array = (JSONArray) sysPortalHtmlFormListObj;
						sysPortalHtmlFormList = array.toJavaList(SysPortalHtmlForm.class);
					}
				}
				
				// 快捷方式与常用链接
				if (sysPortalLinkFormList != null && !sysPortalLinkFormList.isEmpty()) {
					for (SysPortalLinkForm sysPortalLinkForm : sysPortalLinkFormList) {
						// 除重
						String fdId = sysPortalLinkForm.getFdId();
						SysPortalLink spl = getSysPortalLink(fdId);
						if (spl != null) {
							// 或更新
							continue;
						} else {
							List list = sysPortalLinkForm.getFdLinks();
							if (list != null && !list.isEmpty() && list instanceof JSONArray) {
								JSONArray arraySysPortalLinkDetailForm = (JSONArray) list;
								List<SysPortalLinkDetailForm> sysPortalLinkDetailFormList = arraySysPortalLinkDetailForm.toJavaList(SysPortalLinkDetailForm.class);
								sysPortalLinkForm.setFdLinks(sysPortalLinkDetailFormList);
							}
							spl = new SysPortalLink();
							sysPortalLinkService.convertFormToModel(sysPortalLinkForm, spl, new RequestContext());
							spl.setFdId(fdId);
							sysPortalLinkService.add(spl);
						}
					}
				}
				// 系统导航
				if (sysPortalNavFormList != null && !sysPortalNavFormList.isEmpty()) {
					for (SysPortalNavForm sysPortalNavForm : sysPortalNavFormList) {
						// 除重
						String fdId = sysPortalNavForm.getFdId();
						SysPortalNav spn = getSysPortalNav(fdId);
						if (spn != null) {
							continue;
						} else {
							spn = new SysPortalNav();
							sysPortalNavService.convertFormToModel(sysPortalNavForm, spn, new RequestContext());
							spn.setFdId(fdId);
							sysPortalNavService.add(spn);
						}
					}
				}
				// 多级数菜单
				if (sysPortalTreeFormList != null && !sysPortalTreeFormList.isEmpty()) {
					for (SysPortalTreeForm sysPortalTreeForm : sysPortalTreeFormList) {
						// 除重
						String fdId = sysPortalTreeForm.getFdId();
						SysPortalTree spt = getSysPortalTree(fdId);
						if (spt != null) {
							continue;
						} else {
							spt = new SysPortalTree();
							sysPortalTreeService.convertFormToModel(sysPortalTreeForm, spt, new RequestContext());
							spt.setFdId(fdId);
							sysPortalTreeService.add(spt);
						}
					}
				}
				// 地图模板
				if (sysPortalMapTplFormList != null && !sysPortalMapTplFormList.isEmpty()) {
					for (SysPortalMapTplForm sysPortalMapTplForm : sysPortalMapTplFormList) {
						// 除重
						String fdId = sysPortalMapTplForm.getFdId();
						SysPortalMapTpl spmt = getSysPortalMapTpl(fdId);
						if (spmt != null) {
							continue;
						} else {
							List listFdPortalNavForms = sysPortalMapTplForm.getFdPortalNavForms();
							List listFdMapInletForms = sysPortalMapTplForm.getFdMapInletForms();
							List listFdNavCustomForms = sysPortalMapTplForm.getFdNavCustomForms();
							
							if (listFdPortalNavForms != null && !listFdPortalNavForms.isEmpty() && listFdPortalNavForms instanceof JSONArray) {
								JSONArray arrayFdPortalNavForms = (JSONArray) listFdPortalNavForms;
								List<SysPortalMapTplNavForm> sysPortalMapTplNavFormList = arrayFdPortalNavForms.toJavaList(SysPortalMapTplNavForm.class);
								sysPortalMapTplForm.setFdPortalNavForms(sysPortalMapTplNavFormList);
							}
							if (listFdMapInletForms != null && !listFdMapInletForms.isEmpty() && listFdMapInletForms instanceof JSONArray) {
								JSONArray arrayFdMapInletForms = (JSONArray) listFdMapInletForms;
								List<SysPortalMapInletForm> sysPortalMapInletFormList = arrayFdMapInletForms.toJavaList(SysPortalMapInletForm.class);
								sysPortalMapTplForm.setFdMapInletForms(sysPortalMapInletFormList);
							}
							if (listFdNavCustomForms != null && !listFdNavCustomForms.isEmpty() && listFdNavCustomForms instanceof JSONArray) {
								JSONArray arrayFdNavCustomForms = (JSONArray) listFdNavCustomForms;
								List<SysPortalMapTplNavCustomForm> sysPortalMapTplNavCustomFormList = arrayFdNavCustomForms.toJavaList(SysPortalMapTplNavCustomForm.class);
								sysPortalMapTplForm.setFdNavCustomForms(sysPortalMapTplNavCustomFormList);
							}
							spmt = new SysPortalMapTpl();
							sysPortalMapTplService.convertFormToModel(sysPortalMapTplForm, spmt, new RequestContext());
							spmt.setFdId(fdId);
							sysPortalMapTplService.add(spmt);
						}
					}
				}
				// 推荐专题
				if (sysPortalTopicFormList != null && !sysPortalTopicFormList.isEmpty()) {
					for (SysPortalTopicForm sysPortalTopicForm : sysPortalTopicFormList) {
						// 除重
						String fdId = sysPortalTopicForm.getFdId();
						SysPortalTopic spt = getSysPortalTopic(fdId);
						if (spt != null) {
							continue;
						} else {
							spt = new SysPortalTopic();
							sysPortalTopicService.convertFormToModel(sysPortalTopicForm, spt, new RequestContext());
							spt.setFdId(fdId);
							sysPortalTopicService.add(spt);
						}
					}
				}
				// 自定义页面
				if (sysPortalHtmlFormList != null && !sysPortalHtmlFormList.isEmpty()) {
					for (SysPortalHtmlForm sysPortalHtmlForm : sysPortalHtmlFormList) {
						String fdId = sysPortalHtmlForm.getFdId();
						SysPortalHtml sph = getSysPortalHtml(fdId);
						if (sph != null) {
							continue;
						} else {
							sph = new SysPortalHtml();
							sysPortalHtmlService.convertFormToModel(sysPortalHtmlForm, sph, new RequestContext());
							sph.setFdId(fdId);
							sysPortalHtmlService.add(sph);
						}
					}
				}
			}
		}
	}
	
	private SysPortalLink getSysPortalLink(String fdId) throws Exception {
		SysPortalLink sysPortalLink = (SysPortalLink) sysPortalLinkService.findByPrimaryKey(fdId, SysPortalLink.class, Boolean.TRUE);
		return sysPortalLink;
	}
	
	private SysPortalLinkForm getSysPortalLinkForm(String fdId) throws Exception {
		SysPortalLink sysPortalLink = getSysPortalLink(fdId);
		if (sysPortalLink != null) {
			SysPortalLinkForm form = new SysPortalLinkForm();
			sysPortalLinkService.convertModelToForm(form, sysPortalLink, new RequestContext());
			return form;
		}
		return null;
	}
	
	private SysPortalTree getSysPortalTree(String fdId) throws Exception {
		SysPortalTree sysPortalTree = (SysPortalTree) sysPortalTreeService.findByPrimaryKey(fdId, SysPortalTree.class, Boolean.TRUE);
		return sysPortalTree;
	}
	
	private SysPortalTreeForm getSysPortalTreeForm(String fdId) throws Exception {
		SysPortalTree sysPortalTree = getSysPortalTree(fdId);
		if (sysPortalTree != null) {
			SysPortalTreeForm form = new SysPortalTreeForm();
			sysPortalTreeService.convertModelToForm(form, sysPortalTree, new RequestContext());
			return form;
		}
		return null;
	}
	
	private SysPortalNav getSysPortalNav(String fdId) throws Exception {
		SysPortalNav sysPortalNav = (SysPortalNav) sysPortalNavService.findByPrimaryKey(fdId, SysPortalNav.class, Boolean.TRUE);
		return sysPortalNav;
	}
	
	private SysPortalNavForm getSysPortalNavForm(String fdId) throws Exception {
		SysPortalNav sysPortalNav = getSysPortalNav(fdId);
		if (sysPortalNav != null) {
			SysPortalNavForm form = new SysPortalNavForm();
			sysPortalNavService.convertModelToForm(form, sysPortalNav, new RequestContext());
			return form;
		}
		return null;
	}
	
	private SysPortalMapTpl getSysPortalMapTpl(String fdId) throws Exception {
		SysPortalMapTpl sysPortalMapTpl = (SysPortalMapTpl) sysPortalMapTplService.findByPrimaryKey(fdId, SysPortalMapTpl.class, Boolean.TRUE);
		return sysPortalMapTpl;
	}
	
	private SysPortalMapTplForm getSysPortalMapTplForm(String fdId) throws Exception {
		SysPortalMapTpl sysPortalMapTpl = getSysPortalMapTpl(fdId);
		if (sysPortalMapTpl != null) {
			SysPortalMapTplForm form = new SysPortalMapTplForm();
			sysPortalMapTplService.convertModelToForm(form, sysPortalMapTpl, new RequestContext());
			form.setFdId(sysPortalMapTpl.getFdId());
			return form;
		}
		return null;
	}
	
	private SysPortalTopic getSysPortalTopic(String fdId) throws Exception {
		SysPortalTopic sysPortalTopic = (SysPortalTopic) sysPortalTopicService.findByPrimaryKey(fdId, SysPortalTopic.class, Boolean.TRUE);
		return sysPortalTopic;
	}
	
	private SysPortalTopicForm getSysPortalTopicForm(String fdId) throws Exception {
		SysPortalTopic sysPortalTopic = getSysPortalTopic(fdId);
		if (sysPortalTopic != null) {
			SysPortalTopicForm form = new SysPortalTopicForm();
			sysPortalTopicService.convertModelToForm(form, sysPortalTopic, new RequestContext());
			return form;
		}
		return null;
	}
	
	private SysPortalHtml getSysPortalHtml(String fdId) throws Exception {
		SysPortalHtml sysPortalHtml = (SysPortalHtml) sysPortalHtmlService.findByPrimaryKey(fdId, SysPortalHtml.class, Boolean.TRUE);
		return sysPortalHtml;
	}
	
	private SysPortalHtmlForm getSysPortalHtmlForm(String fdId) throws Exception {
		SysPortalHtml sysPortalHtml = getSysPortalHtml(fdId);
		if (sysPortalHtml != null) {
			SysPortalHtmlForm form = new SysPortalHtmlForm();
			sysPortalHtmlService.convertModelToForm(form, sysPortalHtml, new RequestContext());
			return form;
		}
		return null;
	}
}
