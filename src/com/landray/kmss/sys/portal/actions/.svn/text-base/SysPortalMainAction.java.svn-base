package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalMainForm;
import com.landray.kmss.sys.portal.forms.SysPortalMainPageForm;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalPersonDefault;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPersonDefaultService;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 自定义页面 Action
 *
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalMainAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private static final ExtendForm main = null;
	protected ISysPortalMainService sysPortalMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalMainService == null) {
			sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
		}
		return sysPortalMainService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalMainForm xform = (SysPortalMainForm) super.createNewForm(
				mapping, form, request, response);
		xform.setFdTarget("_top");
		xform.setFdEnabled("true");
		xform.setFdIcon("lui_icon_l_icon_1");
		xform.setFdTheme("fresh_elegant");
		xform.setFdLogo("/resource/images/logo.png");
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		if (StringUtil.isNotNull(request.getParameter("parentportal"))) {
			String parentId = request.getParameter("parentportal").toString()
					.trim();
			try {
				SysPortalMain parent = (SysPortalMain) getServiceImp()
						.findByPrimaryKey(parentId);
				xform.setFdParentId(parent.getFdId());
				xform.setFdParentName(parent.getFdName());
			} catch (Exception e) {
			}
		}
		return xform;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.update(mapping, form, request, response);
		if (af.getName().endsWith("success")) {
			KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
			List<List<String>> buttons = returnPage.getButtons();
			for (int i = 0; i < buttons.size(); i++) {
				List<String> ele = buttons.get(i);
				if (ele.contains("button.back")) {
					buttons.remove(i);
					break;
				}
			}

			/**
			 * 更新保存返回按钮能正确回退路径
			 *
			 * @author 吴进 by 20191113
			 */
			String fdAnonymous = request.getParameter("fdAnonymous");
			if ("0".equals(fdAnonymous)) {
				returnPage.addButton("button.back",
						"sysPortalMain.do?method=edit&fdId="
								+ ((SysPortalMainForm) form).getFdId()
								+ "&fdAnonymous=0",
						false);
			} else if ("1".equals(fdAnonymous)) {
				returnPage.addButton("button.back",
						"sysPortalMain.do?method=editAnonymous&fdId="
								+ ((SysPortalMainForm) form).getFdId()
								+ "&fdAnonymous=1",
						false);
			}
		}
		return af;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			String path = URLEncoder.encode(request.getParameter("s_path"),
					"UTF-8");
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"/sys/portal/sys_portal_main/sysPortalMain.do?method=list&s_path="
									+ path,
							false)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}


	public ActionForward setEnable(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else{
				SysPortalMain sysPortalMain = (SysPortalMain)getServiceImp(request).findByPrimaryKey(id);
				if(UserOperHelper.allowLogOper("setEnable", getServiceImp(request).getModelName())){
					UserOperContentHelper.putUpdate(sysPortalMain.getFdId(), sysPortalMain.getFdName(),getServiceImp(request).getModelName()).putSimple("fdEnabled", sysPortalMain.getFdEnabled(), Boolean.TRUE);
				}
				sysPortalMain.setFdEnabled(Boolean.TRUE);
				getServiceImp(request).update(sysPortalMain);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			String path = URLEncoder.encode(request.getParameter("s_path"),
					"UTF-8");
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"/sys/portal/sys_portal_main/sysPortalMain.do?method=list&s_path="
									+ path,
							false)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward setDisable(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else{
				SysPortalMain sysPortalMain = (SysPortalMain)getServiceImp(request).findByPrimaryKey(id);
				if(UserOperHelper.allowLogOper("setEnable", getServiceImp(request).getModelName())){
					UserOperContentHelper.putUpdate(sysPortalMain.getFdId(), sysPortalMain.getFdName(), getServiceImp(request).getModelName()).putSimple("fdEnabled", sysPortalMain.getFdEnabled(), Boolean.FALSE);
				}
				sysPortalMain.setFdEnabled(Boolean.FALSE);
				getServiceImp(request).update(sysPortalMain);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			String path = URLEncoder.encode(request.getParameter("s_path"),
					"UTF-8");
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"/sys/portal/sys_portal_main/sysPortalMain.do?method=list&s_path="
									+ path,
							false)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	protected ISysPortalMainService getServiceImp() {
		if (sysPortalMainService == null) {
			sysPortalMainService = (ISysPortalMainService) getBean("sysPortalMainService");
		}
		return sysPortalMainService;
	}

	public ActionForward getPageDefReader(ActionMapping mapping,
										  ActionForm form, HttpServletRequest request,
										  HttpServletResponse response) throws Exception {
		String portalId = request.getParameter("portalId");

		Map<String, JSONObject> json = new HashMap<String, JSONObject>();
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(portalId)) {
			throw new Exception("error");
		}
		hqlInfo
				.setWhereBlock(" sysPortalMain.fdParent.fdId = :fdId and sysPortalMain.fdEnabled = :fdEnabled ");
		hqlInfo.setParameter("fdId", portalId);
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		List list = getServiceImp().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain pm = (SysPortalMain) list.get(i);
			List defr = pm.getDefReaders();
			for (int j = 0; j < defr.size(); j++) {
				SysOrgElement ele = (SysOrgElement) defr.get(j);
				if (!json.containsKey(ele.getFdId())) {
					JSONObject x = new JSONObject();
					x.put("fdId", ele.getFdId());
					x.put("fdName", ele.getFdName());
					json.put(ele.getFdId(), x);
				}
			}
		}
		// 页面
		hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setWhereBlock(" sysPortalMainPage.sysPortalMain.fdId = :fdId ");
		hqlInfo.setParameter("fdId", portalId);
		hqlInfo.setIsAutoFetch(false);
		list = getServiceImp().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMainPage page = (SysPortalMainPage) list.get(i);
			List defr = page.getSysPortalPage().getDefReaders();
			for (int j = 0; j < defr.size(); j++) {
				SysOrgElement ele = (SysOrgElement) defr.get(j);
				if (!json.containsKey(ele.getFdId())) {
					JSONObject x = new JSONObject();
					x.put("fdId", ele.getFdId());
					x.put("fdName", ele.getFdName());
					json.put(ele.getFdId(), x);
				}
			}
		}
		request.setAttribute("lui-source", JSONArray.fromObject(json.values()));
		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 获取有编辑权限的门户ID集合
	 * @return
	 * @throws Exception
	 */
	private List<String> getCanEditorPortalIds() throws Exception {
		List<String> ids = new ArrayList<String>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_EDITOR");
		// hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setSelectBlock(" distinct sysPortalMain.fdId ");
		hqlInfo.setGetCount(false);
		List list = getServiceImp().findList(hqlInfo);

		// 有权限编辑门户ID列表
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ids.add(list.get(i).toString());
			}
		}
		return ids;
	}


	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getActionForward("list", mapping, form, request, response);
	}


	public ActionForward getPortalList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		String fdName = request.getParameter("fdName"); //门户名称
		String fdEnabled = request.getParameter("fdEnabled"); //门户状态：启用，禁用
		String fdAnonymous = request.getParameter("fdAnonymous"); //是否匿名
		String createStartTime = request.getParameter("createStartTime"); //创建时间-起始时间
		String createEndTime = request.getParameter("createEndTime"); //创建时间-结束时间
		List<Map<String, Object>> portalList = new ArrayList<Map<String, Object>>();
		// 获取有编辑权限的门户ID集合
		List<String> canEditorPortalIds = getCanEditorPortalIds();
		// 查询门户数据列表
		HQLInfo hqlInfo1 = new HQLInfo();
		hqlInfo1.setOrderBy("fdOrder,fdId");
		hqlInfo1.setGetCount(false);
		if (hqlInfo1.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,SysAuthConstant.AllCheck.DEFAULT);
			hqlInfo1.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
		String whereBlock = "1=1";
		if(StringUtil.isNotNull(fdName)){ //门户名称
			whereBlock += " and (sysPortalMain.fdName like :fdName) ";
			hqlInfo1.setParameter("fdName", "%" + fdName + "%");
		}
		if(StringUtil.isNotNull(fdEnabled)){
			whereBlock += " and (sysPortalMain.fdEnabled =:fdEnabled) ";
			if("1".equals(fdEnabled)){ //启用
				hqlInfo1.setParameter("fdEnabled",Boolean.TRUE);
			}else if("0".equals(fdEnabled)){ //禁用
				hqlInfo1.setParameter("fdEnabled",Boolean.FALSE);
			}
		}
		if(StringUtil.isNotNull(fdAnonymous)){
			whereBlock += " and (sysPortalMain.fdAnonymous =:fdAnonymous) ";
			if("1".equals(fdAnonymous)){  //匿名
				hqlInfo1.setParameter("fdAnonymous",Boolean.TRUE);
			}else if("0".equals(fdAnonymous)){ //普通
				hqlInfo1.setParameter("fdAnonymous",Boolean.FALSE);
			}
		}
		//门户创建时间-起始时间
		if(StringUtil.isNotNull(createStartTime)){
			//处理中英文多语言切换时日期格式化报错
			Date satrtTime = DateUtil.convertStringToDate(createStartTime,ResourceUtil.getString("date.format.date"));
			whereBlock += " and (sysPortalMain.docCreateTime >:createStartTime) ";
			hqlInfo1.setParameter("createStartTime", satrtTime);
		}
		if(StringUtil.isNotNull(createEndTime)){
			//处理中英文多语言切换时日期格式化报错
			Date endTime = DateUtil.convertStringToDate(createEndTime,ResourceUtil.getString("date.format.date"));
			whereBlock += " and (sysPortalMain.docCreateTime <:createEndTime) ";
			hqlInfo1.setParameter("createEndTime",endTime);
		}
		hqlInfo1.setWhereBlock(whereBlock);
		List<SysPortalMain> portalMainList = getServiceImp().findValue(hqlInfo1);
		if(portalMainList!=null && portalMainList.size()>0){
			// 查询门户页面中间表数据对象列表
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setModelName(SysPortalMainPage.class.getName());
			hqlInfo2.setOrderBy("fdOrder,fdId");
			hqlInfo2.setGetCount(false);
			List<SysPortalMainPage> portalMainPageList = getServiceImp().getBaseDao().findList(hqlInfo2);
			if (portalMainPageList == null ) {
				portalMainPageList = new ArrayList<SysPortalMainPage>();
			}

			// 定义子门户数据列表
			List<SysPortalMain> childrenPortalMainList = new ArrayList<SysPortalMain>();
			// 定义子门户数据索引Map，用于通过fdId获取子门户数据Map （ key: fdId , value:子门户数据Map ）
			Map<String, Map<String, Object>> childrenPortalMainKeyMap = new HashMap<String, Map<String, Object>>();

			for(SysPortalMain portalMain : portalMainList){
				Map<String, Object> portalMainMap = this.convertPortalMainToMap(portalMain,canEditorPortalIds);
				for(SysPortalMainPage portalMainPage : portalMainPageList){
					if(portalMainPage.getSysPortalMain().getFdId().equals(portalMain.getFdId())){
						Map<String, Object> portalMainPageMap = this.convertPortalMainPageToMap(portalMainPage);
						List<Map<String, Object>> childrenList = (List<Map<String, Object>>)portalMainMap.get("children");
						childrenList.add(portalMainPageMap);
					}
				}
				if(portalMain.getFdParent()==null){
					portalList.add(portalMainMap);
				}else{
					childrenPortalMainList.add(portalMain);
					childrenPortalMainKeyMap.put(portalMain.getFdId(), portalMainMap);
				}

			}
			this.buildPortalList(portalList, childrenPortalMainList, childrenPortalMainKeyMap);

		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		request.setAttribute("lui-source", JSONArray.fromObject(portalList));// 将返回前端的数据转换为JSON格式
		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 构建门户列表信息对象（递归向 portalList添加 children属性的子元素）
	 * @param portalList  门户列表信息对象
	 * @param portalMainList 子门户Model数组
	 * @param portalMainKeyMap 子门户数据索引Map，用于通过fdId获取子门户数据Map
	 */
	private void buildPortalList(List<Map<String, Object>> portalList, List<SysPortalMain> portalMainList, Map<String, Map<String, Object>> portalMainKeyMap){
		for(Map<String, Object> portalMainMap : portalList){
			if("portal".equals(portalMainMap.get("fdType").toString())){
				List<Map<String, Object>> childrenList = (List<Map<String, Object>>)portalMainMap.get("children");
				boolean hasChildrenPortalMain = false; // 是否存在子门户
				for(SysPortalMain portalMain : portalMainList){
					if(portalMain.getFdParent().getFdId().equals(portalMainMap.get("fdId").toString())){
						childrenList.add(portalMainKeyMap.get(portalMain.getFdId()));
						hasChildrenPortalMain = true;
					}
				}
				// 存在子门户的情况下递归子门户列表
				if(hasChildrenPortalMain){
					this.buildPortalList(childrenList, portalMainList, portalMainKeyMap);
				}
			}
		}
	}


	private Map<String, Object> convertPortalMainToMap( SysPortalMain portalMain, List<String> canEditorPortalIds ){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("fdId", portalMain.getFdId());
		m.put("fdType", "portal");
		m.put("fdName", StringUtil.XMLEscape(portalMain.getFdName()));
		m.put("fdEnabled", portalMain.getFdEnabled() == null ? true : portalMain.getFdEnabled());
		if ((portalMain.getFdOrder() != null)) {
			m.put("fdOrder", portalMain.getFdOrder().toString());
		}
		m.put("fdIcon", portalMain.getFdIcon());
		m.put("fdImg", portalMain.getFdImg());
		m.put("fdTarget", portalMain.getFdTarget());
		m.put("children", new ArrayList<Map<String, Object>>());
		boolean isCanEdit = canEditorPortalIds.contains(portalMain.getFdId())?true:false;
		m.put("isCanEdit", isCanEdit); // 是否有编辑权限
		m.put("isCanDel", isCanEdit);  // 是否有删除权限
		m.put("isCanEnableOrDisable", isCanEdit); // 是否要显示“启用”或“禁用”按钮

		// 0普通 1匿名 @author 吴进 by 20191113
		m.put("fdAnonymous", (null!=portalMain.getFdAnonymous()) ? portalMain.getFdAnonymous() : Boolean.FALSE);
		return m;
	}


	private Map<String, Object> convertPortalMainPageToMap(SysPortalMainPage page){
		Map<String, Object> m = new HashMap<String, Object>();
		String portalPageId = page.getSysPortalPage().getFdId();
		m.put("fdId", portalPageId);
		m.put("fdPageId", page.getFdId());
		if ("1".equals(page.getSysPortalPage().getFdType())) {
			m.put("fdType", "page");
		} else {
			m.put("fdType", "url");
		}
		m.put("fdEnabled", true);
		m.put("fdName", StringUtil.XMLEscape(page.getFdName()));
		m.put("fdPageName", StringUtil.XMLEscape(page.getSysPortalPage().getFdName()));
		if ((page.getFdOrder() != null)) {
			m.put("fdOrder", page.getFdOrder().toString());
		}
		m.put("fdIcon", page.getFdIcon());
		m.put("fdTarget", page.getFdTarget());
		boolean isCanEdit = UserUtil.checkAuthentication("/sys/portal/sys_portal_page/sysPortalPage.do?method=edit&fdId="+portalPageId,null);
		m.put("isCanEdit", isCanEdit); // 是否有编辑权限
		m.put("isCanDel", false);  // 是否有删除权限
		m.put("isCanEnableOrDisable", false); // 是否要显示“启用”或“禁用”按钮

		// 0普通 1匿名 @author 吴进 by 20191113
		m.put("fdAnonymous", (null!=page.getSysPortalPage().getFdAnonymous()) ? page.getSysPortalPage().getFdAnonymous() : Boolean.FALSE);
		return m;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		SysPortalMainForm xform = (SysPortalMainForm) form;
		for (int i = 0; i < xform.getPages().size(); i++) {
			SysPortalMainPageForm fform = (SysPortalMainPageForm) xform
					.getPages().get(i);
			if ("1".equals(fform.getFdType())) {
				fform.setFdType("page");
			}
			if ("2".equals(fform.getFdType())) {
				fform.setFdType("url");
			}
		}
		SysPortalMain model = (SysPortalMain) getServiceImp(request)
				.findByPrimaryKey(xform.getFdId());
		List<?> list = model.getFdChildren();
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain xmodel = (SysPortalMain) list.get(i);
			SysPortalMainPageForm fform = new SysPortalMainPageForm();
			fform.setFdId(xmodel.getFdId());
			fform.setFdType("portal");
			fform.setFdName(xmodel.getFdName());
			if (xmodel.getFdOrder() != null) {
				fform.setFdOrder(xmodel.getFdOrder().toString());
			}
			fform.setSysPortalMainId(xmodel.getFdId());
			fform.setFdIcon(xmodel.getFdIcon());
			fform.setFdTarget(xmodel.getFdTarget());
			fform.setFdEnabled(xmodel.getFdEnabled().toString());
			xform.getPages().add(fform);
		}
		Collections.sort(xform.getPages(), new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				SysPortalMainPageForm a1 = (SysPortalMainPageForm) a;
				SysPortalMainPageForm b1 = (SysPortalMainPageForm) b;
				Integer i1 = a1.getFdOrder() == null ? Integer.MAX_VALUE
						: Integer.parseInt(a1.getFdOrder());
				Integer i2 = b1.getFdOrder() == null ? Integer.MAX_VALUE
						: Integer.parseInt(b1.getFdOrder());
				boolean m = (i1 > i2);
				if (m) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}

	private JSONArray getChildren(String portalId, List<String[]> ids)
			throws Exception {
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(portalId)) {
			where += (" sysPortalMain.fdParent = null and sysPortalMain.fdEnabled = :fdEnabled ");
		} else {
			where += (" sysPortalMain.fdParent.fdId = :portalId and sysPortalMain.fdEnabled = :fdEnabled ");
			hqlInfo.setParameter("portalId", portalId);
		}
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		// hqlInfo.setParameter(key, value);
		List list = getServiceImp().findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain main = (SysPortalMain) list.get(i);
			boolean isShow = false;
			for (int j = 0; j < ids.size(); j++) {
				if (ids.get(j)[1].equals(main.getFdId())) {
					isShow = true;
					break;
				}
			}
			if (isShow) {
				JSONObject m = new JSONObject();
				m.put("fdId", main.getFdId());
				m.put("text", main.getFdName());
				m.put("icon", main.getFdIcon());
				m.put("img", main.getFdImg()); //素材库图片路径
				m.put("target", main.getFdTarget());
				m.put("fdOrder", main.getFdOrder());
				m
						.put("href", "/sys/portal/page.jsp?portalId="
								+ main.getFdId());
				ret.add(m);
			}
		}
		if (StringUtil.isNotNull(portalId)) {
			List pages = getServiceImp().getPortalPageList(portalId);
			for (int i = 0; i < pages.size(); i++) {
				SysPortalMainPage page = (SysPortalMainPage) pages.get(i);
				JSONObject m = new JSONObject();
				m.put("fdId", page.getFdId());
				m.put("text", page.getFdName());
				m.put("icon", page.getSysPortalPage().getFdIcon());
				m.put("img", page.getSysPortalPage().getFdImg());
				m.put("target", page.getFdTarget());
				m.put("fdOrder", page.getFdOrder());
				m.put("href", "/sys/portal/page.jsp?pageId=" + page.getFdId());
				m.put("pageType", page.getSysPortalPage() != null
						? page.getSysPortalPage().getFdType() : "1");
				ret.add(m);
			}
		}
		Collections.sort(ret, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject a, JSONObject b) {
				if (!a.containsKey("fdOrder") || !b.containsKey("fdOrder")) {
					return 1;
				}
				boolean m = (Integer.parseInt(a.getString("fdOrder")) > Integer
						.parseInt(b.getString("fdOrder")));
				if (m) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return ret;
	}

	public ActionForward pages(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String portalId = request.getParameter("portalId");
		String pageId = request.getParameter("pageId");

		List<String[]> ids = getHierarchyIds();
		if (StringUtil.isNotNull(portalId) && !"null".equals(portalId)) {
			JSONArray json = JSONArray.fromObject(getChildren(portalId, ids));
			if (!json.isEmpty()) {
				for (int i = 0; i < json.size(); i++) {
					JSONObject obj = json.getJSONObject(i);
					if (obj.getString("fdId").equals(pageId)) {
						obj.put("selected", true);
					} else {
						obj.put("selected", false);
					}
				}
			}
			request.setAttribute("lui-source", json);
		} else {
			JSONArray json = new JSONArray();
			request.setAttribute("lui-source", json);
		}
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public JSONArray getPortalTree(String portalId, List<String[]> ids,
								   String cpid) throws Exception {
		JSONArray ret = new JSONArray();
		String where = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(portalId)) {
			where += (" fdParent = null and fdEnabled = :fdEnabled ");
		} else {
			where += (" fdParent.fdId = '" + portalId + "' and fdEnabled = :fdEnabled ");
		}
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		boolean isAreaEnabled = ISysAuthConstant.IS_AREA_ENABLED;  // 是否启用了集团分级
		if(isAreaEnabled){
			where += (" and authArea is not null and authArea.fdIsAvailable = :fdIsAvailable ");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setOrderBy("fdOrder,fdId");
		hqlInfo.setGetCount(false);
		List<?> list = getServiceImp().findList(hqlInfo);
		String fdPersonDefaultPortalId = "";
		ISysPortalPersonDefaultService sysPortalPersonDefaultService = (ISysPortalPersonDefaultService) SpringBeanUtil
				.getBean("sysPortalPersonDefaultService");
		SysPortalPersonDefault fdPersonDefaultPortal = sysPortalPersonDefaultService.getPersonDefaultPortal();
		if (fdPersonDefaultPortal != null) {
			fdPersonDefaultPortalId = fdPersonDefaultPortal.getFdPortalId();
		}
		for (int i = 0; i < list.size(); i++) {
			SysPortalMain main = (SysPortalMain) list.get(i);
			if(isAreaEnabled && SysUiConfigUtil.getIsLoginDefaultAreaPortal() && ISysAuthConstant.IS_ISOLATION_ENABLED && !SysAuthAreaUtils.isAvailableModel(main, null)) {
				continue;
			}
			JSONObject m = new JSONObject();
			m.put("fdId", main.getFdId());
			m.put("text", main.getFdName());
			m.put("icon", main.getFdIcon());
			m.put("img", main.getFdImg());
			m.put("target", main.getFdTarget());
			m.put("fdOrder", main.getFdOrder());
			//普通门户
			if (main.getFdAnonymous()!=null && main.getFdAnonymous()) {
				m.put("isAnonymous", false);
			} else {
				m.put("isAnonymous", true);
			}
			if (StringUtil.isNotNull(fdPersonDefaultPortalId) && main.getFdId().equals(fdPersonDefaultPortalId)) {
				m.put("default", true);
			} else {
				m.put("default", false);
			}
			if (main.getFdId().equals(cpid)) {
				m.put("selected", true);
			} else {
				m.put("selected", false);
			}
			boolean isShow = false;
			for (int j = 0; j < ids.size(); j++) {
				if (ids.get(j)[0].indexOf(BaseTreeConstant.HIERARCHY_ID_SPLIT
						+ main.getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT) >= 0) {
					isShow = true;
					break;
				}
			}
			if (isShow) {
				JSONArray children = getPortalTree(main.getFdId(), ids, cpid);
				if (children != null && !children.isEmpty()
						&& children.size() > 0) {
					m.put("children", children);
				}
				boolean isLink = false;
				for (int j = 0; j < ids.size(); j++) {
					if (ids.get(j)[1].equals(main.getFdId())) {
						isLink = true;
						break;
					}
				}
				if (isLink) {
					m.put("href", "/sys/portal/page.jsp?portalId="+ main.getFdId());
				} else {
					m.put("href", "");
				}
				ret.add(m);
			}
		}
		return ret;
	}

	private List<String[]> getHierarchyIds() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysPortalMainPage.class.getName());
		hqlInfo.setCheckParam(CheckType.AuthCheck, "SYS_READER");
		// hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setSelectBlock(" distinct sysPortalMainPage.sysPortalMain.fdHierarchyId,sysPortalMainPage.sysPortalMain.fdId ");
		hqlInfo.setWhereBlock(" sysPortalMainPage.sysPortalMain.fdEnabled = :fdEnabled ");
		hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
		List list = getServiceImp().findList(hqlInfo);

		// 有权限范围的门户ID列表
		List<String[]> ids = new ArrayList<String[]>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				String[] xxxx = new String[2];
				xxxx[0] = objs[0].toString();
				xxxx[1] = objs[1].toString();
				ids.add(xxxx);
			}
		}
		return ids;
	}

	public ActionForward portal(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<String[]> ids = getHierarchyIds();
		String cpid = PortalUtil.getPortalInfo(request).getPortalId();
		System.out.println("cpid:" + cpid);
		JSONArray json = getPortalTree(null, ids, cpid);
		request.setAttribute("lui-source", json);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	/*************************
	 * 匿名门户 Start
	 ****************************************/

	/**
	 * 新建匿名门户
	 *
	 * @author 吴进 by 20191113
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAnonymous(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request,
					response);
			if (newForm != form) {
				request.setAttribute(getFormName(newForm, request), newForm);
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editAnonymous", mapping, form, request,
					response);
		}
	}

	/**
	 * 编辑匿名门户
	 *
	 * @author 吴进 by 20191113
	 *
	 *         打开编辑页面。<br>
	 *         URL中必须包含fdId参数，该参数为记录id。<br>
	 *         该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward editAnonymous(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editAnonymous", mapping, form, request,
					response);
		}
	}

	/*************************
	 * 匿名门户 End
	 ****************************************/
}
