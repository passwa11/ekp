package com.landray.kmss.sys.mportal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.mobile.compressor.config.CompressConfigPlugin;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.model.SysMportalLogoInfo;
import com.landray.kmss.sys.mportal.model.SysMportalPage;
import com.landray.kmss.sys.mportal.model.SysMportalPageCard;
import com.landray.kmss.sys.mportal.service.ISysMportalCardService;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.sys.mportal.util.SysMportalConstant;
import com.landray.kmss.sys.mportal.util.SysMportalMportletUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.oracle.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 移动公共门户页面业务接口实现
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalPageServiceImp extends ExtendDataServiceImp
		implements ISysMportalPageService {

	private KmssCache cache = new KmssCache(SysMportalPage.class);
	

	private ISysAppConfigService sysAppConfigService;

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	protected ISysMportalCardService sysMportalCardService;

	public void setSysMportalCardService(ISysMportalCardService sysMportalCardService) {
		this.sysMportalCardService = sysMportalCardService;
	}

	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		return super.convertModelToForm(form, model, requestContext);
	}

	private void sortCard(List<SysMportalPageCard> cards) throws Exception {
		Collections.sort(cards, new Comparator<SysMportalPageCard>() {
			@Override
			public int compare(SysMportalPageCard m1, SysMportalPageCard m2) {
				if (m1.getFdOrder() < m2.getFdOrder()) {
					return -1;
				} else {
					return 1;
				}
			}
		});
	}

	@Override
	public JSONArray getPushPortlets(RequestContext request) throws Exception {
		String fdId = request.getParameter("fdId");
		JSONArray array = new JSONArray();
		if (StringUtil.isNotNull(fdId)) {
			// if (cache.get(fdId) != null) {
			// return (JSONArray) cache.get(fdId);
			// }
			//166829 移动门户-简单门户，删除门户后，移动端有缓存，刷新时会url会指定请求，findByPrimaryKey会报错找不到数据，导致页面一直刷新
			if (getBaseDao().isExist(getModelName(), fdId)) {
				SysMportalPage page = (SysMportalPage) this.findByPrimaryKey(fdId);
				if (page != null) {
					List<SysMportalPageCard> cardList = page.getCards();
					sortCard(cardList);
					for (SysMportalPageCard pageCard : cardList) {
						SysMportalCard card = pageCard.getSysMportalCard();
						JSONObject json = new JSONObject();
						SysMportalMportletUtil.loadConfigure(json, card);

						json.put("title", pageCard.getFdName());
						json.put("margin", pageCard.getFdMargin());
						array.add(json);
					}
					cache.put(fdId, array);
				}
			}
		}
		return array;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		IBaseModel modelPage = super.convertFormToModel(form, model, requestContext);
		SysMportalPage page = (SysMportalPage) modelPage;
		if (SysMportalConstant.PAGE_TYPE_URL.equals(page.getFdType())) {
			page.getCards().clear();
		} else if (SysMportalConstant.PAGE_TYPE_PAGE.equals(page.getFdType())) {
			page.setFdUrl(null);
		} else if (SysMportalConstant.PAGE_TYPE_PERSON.equals(page.getFdType())) {
			page.getCards().clear();
			page.setAuthEditors(null);
			page.setAuthReaders(null);
			page.setFdUrl(null);
		}
		return modelPage;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysMportalPage xpage = (SysMportalPage) modelObj;
		super.update(xpage);
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		return super.add(modelObj);
	}

	public void updateInvalidated(String id, RequestContext requestContext) {
		try {
			SysMportalPage sysMportalPage = (SysMportalPage) this.findByPrimaryKey(id);
			if (sysMportalPage != null) {
				if (sysMportalPage.getFdEnabled() == null
						|| sysMportalPage.getFdEnabled() == true) {
					sysMportalPage.setFdEnabled(new Boolean(false));
					update(sysMportalPage);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateInvalidatedAll(String[] ids, RequestContext requestContext) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateInvalidated(ids[i], requestContext);
		}
	}

	public void updateValidated(String id, RequestContext requestContext) {
		try {
			SysMportalPage sysMportalPage = (SysMportalPage) this.findByPrimaryKey(id);
			if (sysMportalPage != null) {
				if (sysMportalPage.getFdEnabled() == null
						|| sysMportalPage.getFdEnabled() == false) {
					sysMportalPage.setFdEnabled(new Boolean(true));
					update(sysMportalPage);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateValidatedAll(String[] ids, RequestContext requestContext) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateValidated(ids[i], requestContext);
		}
	}

	@SuppressWarnings("unchecked")
	private List<SysMportalPage> loadPages() throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysMportalPage.fdEnabled=:fdEnabled");
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		return this.findList(hqlInfo);
	}

	@Override
	public Map<String, List<String>> loadMporletJsAndCssList() throws Exception {

		Map<String, List<String>> result = new HashMap<>();

		List<String> jsUrls = new ArrayList<>();

		List<SysMportalPage> pages = this.loadPages();

		JSONObject obj = new JSONObject();

		for (SysMportalPage page : pages) {

			JSONArray arr = new JSONArray();

			List<SysMportalPageCard> pageCards = page.getCards();

			for (SysMportalPageCard pageCard : pageCards) {
				SysMportalCard card = pageCard.getSysMportalCard();

				JSONObject cardJson = new JSONObject();
				SysMportalMportletUtil.loadConfigure(cardJson, card);

				if (cardJson.get("configs") == null) {
					continue;
				}

				// 所有配置信息封装
				cardJson.element("title", pageCard.getFdName());
				cardJson.element("uuid", card.getFdId());
				cardJson.element("margin", pageCard.getFdMargin());
				arr.add(cardJson);

				JSONArray configs = cardJson.getJSONArray("configs");

				for (int j = 0; j < configs.size(); j++) {

					JSONObject config = (JSONObject) configs.get(j);
					// mportlet.xml脚本封装
					Object jsUrlObj = config.get("jsUrl");
					if (jsUrlObj != null) {
						String jsUrl = jsUrlObj.toString();
						if (jsUrl.indexOf("?") >= 0) {
							jsUrl = jsUrl.substring(0, jsUrl.indexOf("?"));
						}
						
						if (!jsUrls.contains(jsUrl)) {
							jsUrls.add(jsUrl);
						}
					}

				}

			}

			obj.element(page.getFdId(), arr);
		}

		// 最后加上所有门户的配置数据包
		jsUrls.add("var memory = " + obj.toString());

		result.put(CompressConfigPlugin.MPORTLET_ID + ".js", jsUrls);

		return result;

	}

	@Override
	public void headerSetting(HttpServletRequest request) throws Exception {
		String type = this.getHeaderType();
		request.setAttribute("headerType", type);
	}

	@Override
	public String getHeaderType() throws Exception {
		SysAppConfig sysAppConfig = this.getDBConfig();

		if (sysAppConfig == null) {
			return "1";
		} else {
			return sysAppConfig.getFdValue();
		}
	}

	@Override
	public void saveOrUpdateHeaderType(HttpServletRequest request) throws Exception {

		SysAppConfig sysAppConfig = this.getDBConfig();
		String type = request.getParameter("headerTpye");
		if (StringUtil.isNull(type)) {
            type = "1";
        }
		if(sysAppConfig == null){
			sysAppConfig = new SysAppConfig();
			sysAppConfig.setFdKey("com.landray.kmss.sys.mportal.model.SysMportalPage");
			sysAppConfig.setFdField("headerType");
			sysAppConfig.setFdValue(type);
			sysAppConfigService.add(sysAppConfig);
		} else {
			sysAppConfig.setFdValue(type);
			sysAppConfigService.update(sysAppConfig);
		}

	}

	@SuppressWarnings("unchecked")
	private SysAppConfig getDBConfig() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "fdKey =:fdKey and fdField =:fdField";
		hqlInfo.setParameter("fdKey", "com.landray.kmss.sys.mportal.model.SysMportalPage");
		hqlInfo.setParameter("fdField", "headerType");
		hqlInfo.setWhereBlock(whereBlock);
		Object obj = sysAppConfigService.findFirstOne(hqlInfo);
		return (SysAppConfig)obj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initPageMessage(HttpServletRequest request, JSONObject jsonObject) throws Exception {
		List<SysMportalPage> rtnList = new ArrayList<SysMportalPage>();
		//开启多语言情况下，当前用户的默认语言不为空时处理 #103354
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if(StringUtil.isNotNull(langStr)) {
			if(StringUtil.isNotNull(UserUtil.getUser().getFdDefaultLang())) {
				rtnList = findLangPortal(UserUtil.getUser().getFdDefaultLang());
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String whereBlock = "sysMportalPage.fdEnabled = :fdEnabled";
		hqlInfo.setParameter("fdEnabled", true);
		hqlInfo.setOrderBy("sysMportalPage.fdOrder asc, sysMportalPage.docCreateTime desc");
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 移动门户内外隔离
			hqlInfo.setJoinBlock(" left join sysMportalPage.authAllReaders readers");
			if (SysOrgEcoUtil.isExternal()) {
				whereBlock += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				whereBlock += " and (readers.fdId is null or readers.fdId in (:orgIds))";
			}
			hqlInfo.setParameter("orgIds", orgIds);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<SysMportalPage> list = this.findList(hqlInfo);
		
		//去重
		list.removeAll(rtnList);
		rtnList.addAll(list);
		
		if (!rtnList.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			List<String> ids = new ArrayList<String>();
			for (SysMportalPage sysMportalPage : rtnList) {
				if (ids.contains(sysMportalPage.getFdId())) {
					// 重复
					continue;
				} else {
					ids.add(sysMportalPage.getFdId());
				}
				JSONObject jsonObj = new JSONObject();
				// 页面信息
				jsonObj.put("pageId", sysMportalPage.getFdId());
				jsonObj.put("pageName", sysMportalPage.getFdName());
				jsonObj.put("pageType", sysMportalPage.getFdType());
				jsonObj.put("pageIcon", sysMportalPage.getFdIcon());
				if (SysMportalConstant.PAGE_TYPE_URL.equals(sysMportalPage.getFdType())) {
                    jsonObj.put("pageUrl", sysMportalPage.getFdType());
                }
				if (StringUtil.isNotNull(sysMportalPage.getFdLogo())) {
					jsonObj.put("pageLogo", sysMportalPage.getFdLogo());
				} else {
					Map<String, String> config = ((ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService")).findByKey(SysMportalLogoInfo.class.getName());
					String logoUrl = config.get("logoUrl");
					if (StringUtil.isNotNull(logoUrl)) {
						jsonObj.put("pageLogo", logoUrl);
					} else {
						jsonObj.put("pageLogo", "/sys/mportal/resource/logo.png");
					}
				}
				jsonArray.add(jsonObj);
			}
			jsonObject.put("portalList", jsonArray);
		}

	}
	/**
	 * 根据用户语言查找门户
	 * @param fdDefaultLang
	 * @return
	 * @throws Exception 
	 */
	private List<SysMportalPage> findLangPortal(String fdDefaultLang) throws Exception {
		
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String whereBlock = "sysMportalPage.fdEnabled = :fdEnabled and sysMportalPage.fdLang = :fdLang";
		hqlInfo.setParameter("fdEnabled", true);
		hqlInfo.setParameter("fdLang", fdDefaultLang);
		hqlInfo.setOrderBy("sysMportalPage.fdOrder asc, sysMportalPage.docCreateTime desc");
		// 移动门户内外隔离
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setJoinBlock(" left join sysMportalPage.authAllReaders readers");
			if (SysOrgEcoUtil.isExternal()) {
				whereBlock += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				whereBlock += " and (readers.fdId is null or readers.fdId in (:orgIds))";
			}
			hqlInfo.setParameter("orgIds", orgIds);
		}
		
		hqlInfo.setWhereBlock(whereBlock);

		return this.findPage(hqlInfo).getList();
	}

	@Override
	public com.alibaba.fastjson.JSONArray getMportalInfoByLogo(List<String> logoList) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String selectBlock = " sysMportalPage.fdLogo,sysMportalPage.fdId,sysMportalPage.fdName ";
		String whereBlock = " sysMportalPage.fdLogo IN (:logoList) ORDER BY fdLogo";
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("logoList", logoList);
		List<Object[]> list = this.findList(hqlInfo);
		com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
		for (Object[] obj : list) {
			JSONObject json = new JSONObject();
			json.put("fdLogo", obj[0]);
			json.put("fdId", obj[1]);
			json.put("fdName", obj[2]);
			array.add(json);
		}
		return array;
	}
}
