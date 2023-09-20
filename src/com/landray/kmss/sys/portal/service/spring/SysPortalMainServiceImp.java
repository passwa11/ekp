package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.portal.forms.SysPortalMainForm;
import com.landray.kmss.sys.portal.forms.SysPortalMainPageForm;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalPersonDefault;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import java.util.List;

/**
 * 自定义页面业务接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalMainServiceImp extends BaseServiceImp implements ISysPortalMainService {

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysPortalMainForm xform = (SysPortalMainForm) form;
		for (int i = xform.getPages().size() - 1; i >= 0; i--) {
			SysPortalMainPageForm fform = (SysPortalMainPageForm) xform
					.getPages().get(i);
			if ("portal".equals(fform.getFdType())) {
				SysPortalMain model = (SysPortalMain) findByPrimaryKey(fform
						.getSysPortalMainId());
				model.setFdOrder(Integer.parseInt(fform.getFdOrder()));
				model.setFdEnabled(Boolean.valueOf(fform.getFdEnabled()));
				model.setFdIcon(fform.getFdIcon());
				model.setFdTarget(fform.getFdTarget());
				getBaseDao().getHibernateTemplate().update(model);
				xform.getPages().remove(fform);
			}
		}
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		update(model);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		PortalUtil.clearPageCache();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		if(null != modelObj) {
			deleteSysPortalPersonDefault(modelObj.getFdId());
		}
		super.delete(modelObj);
		PortalUtil.clearPageCache();
	}
	
	private void deleteSysPortalPersonDefault(String fdId) {
		if(StringUtil.isNull(fdId)) {
			return ;
		}
		//删除默认门户
		getBaseDao().getHibernateSession().createQuery(
				"delete " +SysPortalPersonDefault.class.getName() +" where fdPortalId = :fdPortalId" 
				)
		.setParameter("fdPortalId", fdId)
		.executeUpdate();
		
		//删除门户关联的页面信息
		getBaseDao().getHibernateSession().createQuery(
				"delete " +SysPortalMainPage.class.getName() +" where sysPortalMain.fdId = :fdPortalId" 
				)
		.setParameter("fdPortalId", fdId)
		.executeUpdate();
	}
	
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysPortalMain xpage = (SysPortalMain) modelObj;
		return super.add(xpage);
	}

	/**
	 * 根据门户和页面中间表的fdId获取中间表的Model对象，权限过滤使用页面的可阅读者
	 */
	@Override
	public SysPortalMainPage getPortalPageById(String pageId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setWhereBlock(" sysPortalMainPage.fdId = :fdId ");
		hqlInfo.setParameter("fdId", pageId);
		hqlInfo.setOrderBy(" sysPortalMain.fdOrder,sysPortalMainPage.fdOrder ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		Page page = getBaseDao().findPage(hqlInfo);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			return (SysPortalMainPage) page.getList().get(0);
		} else {
			throw new KmssException(new KmssMessage(
					"error.noportal.error3"));
		}
	}


	/**
	 * 获取默认门户页面model,权限过滤使用门户的默认可访问者
	 * @param lang  语言
	 * @param authAreaId  场所ID
 	 * @param isAnonymous 是否匿名用户
	 * @return
	 * @throws Exception
	 */	
	private SysPortalMainPage getDefaultPortalPage(String lang, String authAreaId,  boolean isAnonymous) throws Exception {
		// 带多语言获取用户默认访问的门户
		Page page = getDefaultReadersPortalPageWithLang(lang,authAreaId, isAnonymous);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			// 获取当前门户下用户默认访问的页面
			String portalId = (page.getList().get(0).toString());
			return getPortalInfoById(portalId);
		} else {
			// 如果未获取到页面，则获取所有用户默认访问的页面
			page = getDefaultReadersPortalPageWithLang(null,authAreaId, isAnonymous);
			if (page != null && page.getList() != null && page.getList().size() > 0) {
				// 获取当前门户下用户默认访问的页面
				String portalId = (page.getList().get(0).toString());
				return getPortalInfoById(portalId);
			} else {
				//如果没有默认门户，则看是否有默认访问页面
				page = getDefaultPageWithLang(lang,authAreaId, isAnonymous);
				if (page != null && page.getList() != null && page.getList().size() > 0) {
					return (SysPortalMainPage) page.getList().get(0);
				}
				page = getDefaultPageWithLang(null,authAreaId, isAnonymous);
				if (page != null && page.getList() != null && page.getList().size() > 0) {
					return (SysPortalMainPage) page.getList().get(0);
				}
				/**
				 * 如果没有默认页面，则看是否有可访问的页面，根据语言选择合适的门户
				 */  
				page = getDefaultPortalPageWithLang(lang,authAreaId, isAnonymous);
				if (page != null && page.getList() != null && page.getList().size() > 0) {
					return (SysPortalMainPage) page.getList().get(0);
				} else {
					// 为兼容历史数据，如果根据语言没有找到适合的门户，则查询所有门户
					page = getDefaultPortalPageWithLang(null,authAreaId, isAnonymous);
					if (page != null && page.getList() != null && page.getList().size() > 0) {
						return (SysPortalMainPage) page.getList().get(0);
					} else {
						if(StringUtil.isNotNull(authAreaId)){
							return null;
						}else{
							throw new KmssException(
									new KmssMessage("error.noportal.error1"));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 针对门户页面中间表的查询增加场所过滤条件（限制用户访问门户时查询当前场所下的门户页面）
	 * @param hqlInfo
	 * @param authAreaId  场所ID
	 * @return
	 */
	private HQLInfo buildPortalPageAuthAreaWhereBlock(HQLInfo hqlInfo, String authAreaId) throws Exception {
		boolean isAreaEnabled = ISysAuthConstant.IS_AREA_ENABLED;  // 是否启用了集团分级 
		boolean isRoamSwitchPortal = isAreaEnabled && SysUiConfigUtil.getIsRoamSwitchPortal(); // 是否开启 用户漫游到其它场所的同时切换到该场所下的门户 (门户引擎》门户维护》门户参数)
		boolean isLoginDefaultAreaPortal = isAreaEnabled && SysUiConfigUtil.getIsLoginDefaultAreaPortal(); // 是否登录到用户设置的默认场所下的门户 (门户引擎》门户维护》门户参数)

		if( ( isRoamSwitchPortal||isLoginDefaultAreaPortal ) && StringUtil.isNotNull(authAreaId) ){
			String whereBlock = "";
			if(StringUtil.isNotNull(hqlInfo.getWhereBlock())){
				whereBlock +=" and";
			}
			hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join sysPortalMain.authArea authArea"));
			whereBlock += " authArea.fdId = :authAreaId ";
			hqlInfo.setParameter("authAreaId", authAreaId);
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()+whereBlock);
		}
		
		return hqlInfo;
	}
	
	
	/**
	 * 根据多语言查询默认访问者门户
	 * @param lang  语言
	 * @param authAreaId  场所ID
	 * @param isAnonymous 是否匿名用户
	 * @return
	 * @throws Exception
	 */
	private Page getDefaultReadersPortalPageWithLang(String lang, String authAreaId, boolean isAnonymous) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage.sysPortalMain.fdId ");
		hqlInfo.setFromBlock(" SysPortalMainPage sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain left join sysPortalMain.defReaders defReaders");
		hqlInfo.setOrderBy(" length(sysPortalMain.fdHierarchyId),sysPortalMain.fdOrder,sysPortalMainPage.fdOrder,sysPortalMain.fdId,sysPortalMainPage.fdId ");
		hqlInfo.setIsAutoFetch(false);
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		// 默认访问者
		HQLWrapper wa = HQLUtil.buildPreparedLogicIN(" defReaders.fdId ","sysPortalMainPage"+ "0_", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		hqlInfo.setWhereBlock(" sysPortalMain.fdEnabled = :fdEnabled and " + wa.getHql());
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		addAnonymousCondition(isAnonymous, hqlInfo);
		hqlInfo.setParameter(wa.getParameterList()); 
        // 添加场所过滤条件
		buildPortalPageAuthAreaWhereBlock(hqlInfo,authAreaId);
		return getPageWithLang(hqlInfo, lang);
	}
	
	/**
	 * 根据多语言查询门户
	 * @param lang 语言
	 * @param authAreaId 场所ID
	 * @param isAnonymous 是否匿名用户
	 * @return
	 * @throws Exception
	 */
	private Page getDefaultPortalPageWithLang(String lang, String authAreaId, boolean isAnonymous) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setWhereBlock("  sysPortalMainPage.sysPortalMain.fdEnabled = true ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		addAnonymousCondition(isAnonymous, hqlInfo);
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setOrderBy(" length(sysPortalMain.fdHierarchyId),sysPortalMain.fdOrder,sysPortalMainPage.fdOrder,sysPortalMain.fdId,sysPortalMainPage.fdId ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");

        // 添加场所过滤条件
		buildPortalPageAuthAreaWhereBlock(hqlInfo,authAreaId);
		return getPageWithLang(hqlInfo, lang);
	}

	/**
	 * 根据门户的fdId获取中间表的Model对象，权限过滤使用页面的可阅读者
	 */
	@Override
	public SysPortalMainPage getPortalInfoById(String portalId)
			throws Exception {		
		//查询当前门户下默认可访问者的页面，如果查找不到就返回当前门户下第一个页面。
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalPage sysPortalPage left join sysPortalMainPage.sysPortalMain sysPortalMain left join sysPortalPage.defReaders defReaders ");
		HQLWrapper wax = HQLUtil.buildPreparedLogicIN(" defReaders.fdId ", "defReaders" + "0_", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		hqlInfo.setWhereBlock(" sysPortalMain.fdId = :fdId and " + wax.getHql());
		hqlInfo.setParameter("fdId", portalId);
		hqlInfo.setParameter(wax.getParameterList()); 
		hqlInfo.setOrderBy(" sysPortalMainPage.fdOrder ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		Page page = getBaseDao().findPage(hqlInfo);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			//门户下有默认访问者的页面
			return (SysPortalMainPage) page.getList().get(0);
		} else {
			hqlInfo = new HQLInfo();
			hqlInfo.setModelName(SysPortalMainPage.class.getName());
			hqlInfo.setSelectBlock(" sysPortalMainPage ");
			hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
			hqlInfo.setWhereBlock(" sysPortalMain.fdId = :fdId ");
			hqlInfo.setParameter("fdId", portalId); 
			hqlInfo.setOrderBy(" sysPortalMainPage.fdOrder ");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(1);
			hqlInfo.setGetCount(false);
			hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
			page = getBaseDao().findPage(hqlInfo);
			if (page != null && page.getList() != null && page.getList().size() > 0) {
				return (SysPortalMainPage) page.getList().get(0);
			}else{
				throw new KmssException(new KmssMessage(
					"error.noportal.error2"));
			}
		}
	}
	
	/**
	 * 根据多语言查询默认访问者页面
	 * @param lang  语言
	 * @param authAreaId  场所ID
	 * @param isAnonymous 是否匿名用户
	 * @return
	 * @throws Exception
	 */
	public Page getDefaultPageWithLang(String lang, String authAreaId, boolean isAnonymous) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain left join sysPortalMainPage.sysPortalPage sysPortalPage left join sysPortalPage.defReaders defReaders");
		hqlInfo.setOrderBy(" length(sysPortalMain.fdHierarchyId),sysPortalMain.fdOrder,sysPortalMainPage.fdOrder,sysPortalMain.fdId,sysPortalMainPage.fdId ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		HQLWrapper wax = HQLUtil.buildPreparedLogicIN(" defReaders.fdId ","sysPortalMainPage"+ "0_", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		hqlInfo.setWhereBlock(" sysPortalMain.fdEnabled = true and " + wax.getHql());
		addAnonymousCondition(isAnonymous, hqlInfo);
		hqlInfo.setParameter(wax.getParameterList()); 

        // 添加场所过滤条件
		buildPortalPageAuthAreaWhereBlock(hqlInfo,authAreaId);
		return getPageWithLang(hqlInfo, lang);
	}

	@Override
    public List getPortalPageList(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setWhereBlock(" sysPortalMain.fdId = :fdId ");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setOrderBy(" sysPortalMainPage.fdOrder ");  
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		return getBaseDao().findList(hqlInfo);
	}

	private Page getPageWithLang(HQLInfo hqlInfo, String lang)
			throws Exception {
		Page _page = null;
		if (StringUtil.isNotNull(lang)) {
			HQLInfo _hqlInfo = (HQLInfo) hqlInfo.clone();
			// 如果带语言查询，先按指定的语言进行查询
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and sysPortalMain.fdLang like :fdLang");
			// 因为在某些环境，得到的语言数据会是这种结果：zh-CN-#Hans，所以这里只做前面数据的匹配
			hqlInfo.setParameter("fdLang", lang + "%");
			_page = getBaseDao().findPage(hqlInfo);
			// 如果按指定语言没有查询到结果，则需要查询“不限语言”的门户
			if (_page == null || _page.getList() == null || _page.getList().isEmpty()) {
				_hqlInfo.setWhereBlock(_hqlInfo.getWhereBlock() + " and (sysPortalMain.fdLang is null or sysPortalMain.fdLang = '')");
				_page = getBaseDao().findPage(_hqlInfo);
			}
		} else {
			_page = getBaseDao().findPage(hqlInfo);
		}
		return _page;
	}

	
	/************ 匿名门户 Start ****************************************************************/
	/**
	 * 获取默认的匿名门户页面
	 * @author 吴进 by 20191120
	 * @param lang  语言
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysPortalMainPage getAnonymousPortalPage(String lang) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setWhereBlock(" sysPortalMain.fdEnabled = :fdEnabled AND sysPortalMain.fdAnonymous = :fdAnonymous ");
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setParameter("fdAnonymous", Boolean.TRUE);
		hqlInfo.setOrderBy(" length(sysPortalMain.fdHierarchyId),sysPortalMain.fdOrder,sysPortalMainPage.fdOrder,sysPortalMain.fdId,sysPortalMainPage.fdId ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AllCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		Page page = getPageWithLang(hqlInfo, lang);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			return (SysPortalMainPage) page.getList().get(0);
		}
		return null;
	}

	/**
	 * 获取指定的匿名门户页面
	 * @author 吴进 by 20191120
	 */
	@Override
	public SysPortalMainPage getAnonymousPortalPageById(String portalId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setWhereBlock(" sysPortalMain.fdId = :fdId ");
		hqlInfo.setParameter("fdId", portalId);
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AllCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		Page page = getBaseDao().findPage(hqlInfo);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			return (SysPortalMainPage) page.getList().get(0);
		}
		return null;
	}
	
	/**
	 * 根据门户和页面中间表的fdId获取中间表的Model对象
	 * @author 吴进 by 20191120
	 */
	@Override
	public SysPortalMainPage getAnonymousPortalPageByPageId(String pageId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setWhereBlock(" sysPortalMainPage.fdId = :fdId ");
		hqlInfo.setParameter("fdId", pageId);
		hqlInfo.setOrderBy(" sysPortalMain.fdOrder,sysPortalMainPage.fdOrder ");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		hqlInfo.setCheckParam(CheckType.AllCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		Page page = getBaseDao().findPage(hqlInfo);
		if (page != null && page.getList() != null && page.getList().size() > 0) {
			return (SysPortalMainPage) page.getList().get(0);
		} else {
			throw new KmssException(new KmssMessage(
					"error.noportal.error3"));
		}
	}
	
	/**
	 * 获取页面，无过滤权限
	 * @author 吴进 by 20191120
	 */
	@Override
	public List getAnonymousPortalPageList(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setSelectBlock(" sysPortalMainPage ");
		hqlInfo.setJoinBlock(" left join sysPortalMainPage.sysPortalMain sysPortalMain");
		hqlInfo.setWhereBlock(" sysPortalMain.fdId = :fdId ");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setOrderBy(" sysPortalMainPage.fdOrder ");  
		hqlInfo.setCheckParam(CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		return getBaseDao().findList(hqlInfo);
	}

	@Override
	public SysPortalMainPage getDefaultPortalPage(String lang, String authAreaId) throws Exception {
		return this.getDefaultPortalPage(lang, authAreaId, false);
	}

	@Override
	public SysPortalMainPage getDefaultAnonymousPortalPage(String lang, String authAreaId) throws Exception {
		return this.getDefaultPortalPage(lang, authAreaId, true);
	}
	
	private void addAnonymousCondition(boolean isAnonymous, HQLInfo hqlInfo) {
		hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", " left join sysPortalMainPage.sysPortalPage sysPortalPage"));
		StringBuilder whereBlock = new StringBuilder(" and ");
		whereBlock.append(" (sysPortalMain.fdAnonymous = :fdAnonymous or sysPortalMain.fdAnonymous is null)");
		whereBlock.append(" and ");
		whereBlock.append(" (sysPortalPage.fdAnonymous = :fdAnonymous or sysPortalPage.fdAnonymous is null)");
		hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + whereBlock.toString());
		
		hqlInfo.setParameter("fdAnonymous", isAnonymous);
	}
	/************ 匿名门户 End ****************************************************************/

}
