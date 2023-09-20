package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.mobile.compressor.config.CompressConfigPlugin;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.model.SysMportalCpageCard;
import com.landray.kmss.sys.mportal.model.SysMportalCpageRelation;
import com.landray.kmss.sys.mportal.service.ISysMportalCardService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageRelationService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageService;
import com.landray.kmss.sys.mportal.util.SysMportalConstant;
import com.landray.kmss.sys.mportal.util.SysMportalMportletUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 移动公共门户页面业务接口实现
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalCpageServiceImp extends ExtendDataServiceImp
		implements ISysMportalCpageService {

	private KmssCache cache = new KmssCache(SysMportalCpage.class);
	

	protected ISysMportalCardService sysMportalCardService;

	public void setSysMportalCardService(ISysMportalCardService sysMportalCardService) {
		this.sysMportalCardService = sysMportalCardService;
	}
	
	 private ISysMportalCpageRelationService sysMportalCpageRelationService;
	    public ISysMportalCpageRelationService getSysMportalCpageRelationService() {
	        if (sysMportalCpageRelationService == null) {
	        	sysMportalCpageRelationService = (ISysMportalCpageRelationService) SpringBeanUtil.getBean("sysMportalCpageRelationService");
	        }
	        return sysMportalCpageRelationService;
	    }

	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		return super.convertModelToForm(form, model, requestContext);
	}

	private void sortCard(List<SysMportalCpageCard> cards) throws Exception {
		Collections.sort(cards, new Comparator<SysMportalCpageCard>() {
			@Override
            public int compare(SysMportalCpageCard m1, SysMportalCpageCard m2) {
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
			SysMportalCpageRelation relation = (SysMportalCpageRelation)getSysMportalCpageRelationService().findByPrimaryKey(fdId);			
			SysMportalCpage page = relation.getSysMportalCpage();
			if (page != null) {
				List<SysMportalCpageCard> cardList = page.getCards();
				sortCard(cardList);
				for (SysMportalCpageCard pageCard : cardList) {
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
		return array;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		IBaseModel modelPage = super.convertFormToModel(form, model, requestContext);
		SysMportalCpage page = (SysMportalCpage) modelPage;
		if (SysMportalConstant.PAGE_TYPE_URL.equals(page.getFdType())) {
			page.getCards().clear();
		} else if (SysMportalConstant.PAGE_TYPE_PAGE.equals(page.getFdType())) {
			page.setFdUrl(null);
		} else if (SysMportalConstant.PAGE_TYPE_PERSON.equals(page.getFdType())) {
			page.getCards().clear();
			page.setFdUrl(null);
		}
		return modelPage;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysMportalCpage xpage = (SysMportalCpage) modelObj;
		super.update(xpage);
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		return super.add(modelObj);
	}

	public void updateInvalidated(String id, RequestContext requestContext) {
		try {
			SysMportalCpage sysMportalPage = (SysMportalCpage) this.findByPrimaryKey(id);
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
			SysMportalCpage sysMportalPage = (SysMportalCpage) this.findByPrimaryKey(id);
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
	private List<SysMportalCpage> loadPages() throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysMportalCpage.fdEnabled=:fdEnabled");
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		return this.findList(hqlInfo);
	}

	@Override
	public Map<String, List<String>> loadMporletJsAndCssList() throws Exception {

		Map<String, List<String>> result = new HashMap<>();

		List<String> jsUrls = new ArrayList<>();

		List<SysMportalCpage> pages = this.loadPages();

		JSONObject obj = new JSONObject();

		for (SysMportalCpage page : pages) {

			JSONArray arr = new JSONArray();

			List<SysMportalCpageCard> pageCards = page.getCards();

			for (SysMportalCpageCard pageCard : pageCards) {
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

}
