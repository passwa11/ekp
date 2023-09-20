package com.landray.kmss.sys.mportal.service.spring;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.mportal.forms.SysMportalCardForm;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.service.ISysMportalCardService;
import com.landray.kmss.sys.mportal.util.SysMportalMportletUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 移动门户配置业务接口实现
 * 
 * @author
 * @version 1.0 2015-09-14
 */
public class SysMportalCardServiceImp extends BaseServiceImp implements
		ISysMportalCardService {

	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
										  RequestContext requestContext) throws Exception {
		SysMportalCardForm sysMportalCardForm = (SysMportalCardForm) super
				.convertModelToForm(form, model, requestContext);

		//兼容旧数据
		if (sysMportalCardForm.getFdType() == null || sysMportalCardForm.getFdType() == false) {
			String configstr = sysMportalCardForm.getFdPortletConfig();
			if (StringUtil.isNotNull(configstr)) {

				JSONArray configs = JSONArray.fromObject(configstr);

				if (configs.size() > 1) {
                    sysMportalCardForm.setFdType(true);
                }

			}
		}


		return sysMportalCardForm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPushPortlets(RequestContext request) throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "sysMportalCard.fdEnabled=:enabled and sysMportalCard.fdIsPushed=:fdIsPushed";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("enabled", Boolean.TRUE);
		hqlInfo.setParameter("fdIsPushed", Boolean.TRUE);
		hqlInfo.setOrderBy("sysMportalCard.fdOrder asc");

		List<SysMportalCard> list = (List<SysMportalCard>) this
				.findList(hqlInfo);

		JSONArray array = new JSONArray();
		for (SysMportalCard portlet : list) {

			JSONObject json = new JSONObject();

			json.put("uuid", portlet.getFdId());
			json.put("title", portlet.getFdName());

			SysMportalMportletUtil.loadConfigure(json, portlet);

			array.add(json);
		}
		return array;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {

		super.update(modelObj);

		String fdId = modelObj.getFdId();

		modifyPage(fdId);

	}

	@SuppressWarnings("unchecked")
	private List<String> getPageIdByCardId(String cardId) {

		NativeQuery sqlQuery = this
				.getBaseDao()
				.getHibernateSession()
				.createNativeQuery(
						"select fd_page_id from sys_mportal_page_card where fd_card_id = :fdCardId");
		sqlQuery.setParameter("fdCardId", cardId);
		// 启用二级缓存
		sqlQuery.setCacheable(true);
		// 设置缓存模式
		sqlQuery.setCacheMode(CacheMode.NORMAL);
		// 设置缓存区域
		sqlQuery.setCacheRegion("sys-mportal");
		return sqlQuery.list();

	}

	private void updatePage(List<String> pageIds) throws Exception {

		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean("sysMportalPageService");

		for (String pageId : pageIds) {

			IBaseModel page = service.findByPrimaryKey(pageId, null, true);
			service.update(page);
			this.getBaseDao().getHibernateSession().evict(page);

		}

	}
	
	@SuppressWarnings("unchecked")
	//获取复合门户页面
	private List<String> getCpageIdByCardId(String cardId) {

		NativeQuery sqlQuery = this
				.getBaseDao()
				.getHibernateSession()
				.createNativeQuery(
						"select fd_page_id from sys_mportal_cpage_card where fd_card_id = :fdCardId");
		sqlQuery.setParameter("fdCardId", cardId);

		return sqlQuery.list();

	}

	/**
	 * 更新复合 门户
	 * @param pageIds
	 * @throws Exception
	 */
	private void updateCpage(List<String> pageIds) throws Exception {

		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean("sysMportalCpageService");

		for (String pageId : pageIds) {

			IBaseModel page = service.findByPrimaryKey(pageId, null, true);
			service.update(page);
			this.getBaseDao().getHibernateSession().evict(page);

		}

	}

	/**
	 * 更新门户信息
	 * 
	 * @param cardId
	 * @throws Exception
	 */
	private void modifyPage(String cardId) throws Exception {

		List<String> pageIds = getPageIdByCardId(cardId);

		if (pageIds.size() == 0) {
            return;
        }

		updatePage(pageIds);
		//更新复合门户页面
		List<String> cpageIds = getCpageIdByCardId(cardId);
		updateCpage(cpageIds);

	}

	public void updateInvalidated(String id, RequestContext requestContext) {
		try {
			SysMportalCard sysMportalCard = (SysMportalCard) this
					.findByPrimaryKey(id);
			if (sysMportalCard != null) {
				if (sysMportalCard.getFdEnabled() == null
						|| sysMportalCard.getFdEnabled() == true) {
					sysMportalCard.setFdEnabled(new Boolean(false));
					update(sysMportalCard);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateInvalidatedAll(String[] ids, RequestContext requestContext)
			throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateInvalidated(ids[i], requestContext);
		}
	}

	public void updateValidated(String id, RequestContext requestContext) {
		try {
			SysMportalCard sysMportalCard = (SysMportalCard) this
					.findByPrimaryKey(id);
			if (sysMportalCard != null) {
				if (sysMportalCard.getFdEnabled() == null
						|| sysMportalCard.getFdEnabled() == false) {
					sysMportalCard.setFdEnabled(new Boolean(true));
					update(sysMportalCard);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateValidatedAll(String[] ids, RequestContext requestContext)
			throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateValidated(ids[i], requestContext);
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		// TODO Auto-generated method stub

		List<String> pageIds = getPageIdByCardId(modelObj.getFdId());

		super.delete(modelObj);
		this.flushHibernateSession();

		updatePage(pageIds);
		
		//更新复合门户页面
		List<String> cpageIds = getCpageIdByCardId(modelObj.getFdId());
		updateCpage(cpageIds);

	}

	@Override
	public JSONObject getCardById(RequestContext request) throws Exception {

		String cardId = request.getParameter("cardId");
		SysMportalCard card = (SysMportalCard) this.findByPrimaryKey(cardId);
		JSONObject json = new JSONObject();
		SysMportalMportletUtil.loadConfigure(json, card);
		json.put("title", card.getFdName());
		json.put("margin", true);

		return json;
	}

}
