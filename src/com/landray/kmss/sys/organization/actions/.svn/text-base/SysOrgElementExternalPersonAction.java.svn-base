package com.landray.kmss.sys.organization.actions;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.authentication.user.LoginConfig;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.webservice.eco.ISysSynchroEcoWebService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 外部人员扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalPersonAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysOrgElementExternalService sysOrgElementExternalService;
	private ISysOrgElementExternalPersonService sysOrgElementExternalPersonService;
	private ISysOrgDeptService sysOrgDeptService;
	private ISysOrgElementService sysOrgElementService;
	private ISysSynchroEcoWebService sysSynchroEcoWebService;
	private IOrgRangeService orgRangeService;

	@Override
	protected ISysOrgElementExternalPersonService getServiceImp(HttpServletRequest request) {
		if (sysOrgElementExternalPersonService == null) {
			sysOrgElementExternalPersonService = (ISysOrgElementExternalPersonService) getBean("sysOrgElementExternalPersonService");
		}
		return sysOrgElementExternalPersonService;
	}

	protected ISysOrgElementExternalService getSysOrgElementExternalService() {
		if (sysOrgElementExternalService == null) {
			sysOrgElementExternalService = (ISysOrgElementExternalService) getBean("sysOrgElementExternalService");
		}
		return sysOrgElementExternalService;
	}

	public ISysOrgDeptService getSysOrgDeptService() {
		if (sysOrgDeptService == null) {
			sysOrgDeptService = (ISysOrgDeptService) getBean("sysOrgDeptService");
		}
		return sysOrgDeptService;
	}

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public ISysSynchroEcoWebService getSysSynchroEcoWebService() {
		if (sysSynchroEcoWebService == null) {
			sysSynchroEcoWebService = (ISysSynchroEcoWebService) getBean("sysSynchroEcoWebService");
		}
		return sysSynchroEcoWebService;
	}

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) getBean("orgRangeService");
		}
		return orgRangeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 设置为外部组织，用来过滤器跳过组织可见性校验(AuthOrgVisibleFilter)
		hqlInfo.setExternal(true);
		// 外部组织特有过滤器类型，区别内组织权限
		hqlInfo.setAuthCheckType("EXTERNAL_READER");
		StringBuilder whereBlock = new StringBuilder();
		// 只查询外部组织
		whereBlock.append("fdIsExternal = true");
		// 根据上级查询
		String parent = request.getParameter("parent");
		if (StringUtil.isNotNull(parent)) {
			if(getParentOrgTypeIsOrg(parent)){
				whereBlock.append(" and fdHierarchyId like :fdHierarchyId");
				hqlInfo.setParameter("fdHierarchyId","%"+parent+"%");
			}else{
				whereBlock.append(" and hbmParent.fdId = :parent");
				hqlInfo.setParameter("parent", parent);
			}
		}
		// 移动端不显示禁用的组织
		if (MobileUtil.getClientType(request) == MobileUtil.PC) {
			String[] isAvailables = request.getParameterValues("q.isAvailable");
			if (isAvailables != null && isAvailables.length > 0) {
				for (int i = 0; i < isAvailables.length; i++) {
					String isAvailable = isAvailables[i];
					if (i == 0) {
						whereBlock.append(" and (fdIsAvailable = :isAvailable" + i);
					} else {
						whereBlock.append(" or fdIsAvailable = :isAvailable" + i);
					}
					hqlInfo.setParameter("isAvailable" + i, Boolean.valueOf(isAvailable));
				}
				whereBlock.append(") ");
			}
		} else {
			whereBlock.append(" and fdIsAvailable = true ");
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", whereBlock.toString()));
		changeDeptFindPageHQLInfo(request, hqlInfo);
		// 生态组织权限过滤
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "EXTERNAL_READER");
	}

	protected void changeDeptFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();

		// 新UED查询参数
		String fdName = request.getParameter("q.fdSearchName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdSearchName");
		}

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if (isLangSupport) {
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if (StringUtil.isNotNull(currentLocaleCountry)
					&& currentLocaleCountry.equals(SysLangUtil.getOfficialLang())) {
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNotNull(fdName)) { // 层级查询子部门，当fdName不为空时显示子部门及其下级部门
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			String whereBlockDept = "";

			// 部门名称查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdName like :fdName ");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
						"sysOrgPerson.fdName" + currentLocaleCountry + " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 编号查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称拼音查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 名称简拼查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
					"sysOrgPerson.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");
			// 登录名查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdLoginName like :loginName ");
			hqlInfo.setParameter("loginName", "%" + fdName + "%");
			// 邮件地址查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdEmail like :fdEmail ");
			hqlInfo.setParameter("fdEmail", "%" + fdName + "%");
			// 手机号码查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ", "sysOrgPerson.fdMobileNo like :fdMobileNo ");
			hqlInfo.setParameter("fdMobileNo", "%" + fdName + "%");

			if (StringUtil.isNotNull(whereBlockDept)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ", "( " + whereBlockDept + " )");
			}
		}

		whereBlock = StringUtil.linkString(whereBlock, " and ", "sysOrgPerson.fdOrgType = :orgType ");
		hqlInfo.setParameter("orgType", SysOrgConstant.ORG_TYPE_PERSON);
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 父节点的组织类型
	 * @description:
	 * @param parentId
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2021/10/11 11:42 上午
	 */
	private boolean getParentOrgTypeIsOrg(String parentId) throws Exception{
		SysOrgElement sysOrgElement = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(parentId, SysOrgElement.class, true);
		return SysOrgConstant.ORG_TYPE_ORG == sysOrgElement.getFdOrgType();
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		SysOrgPersonForm personForm = (SysOrgPersonForm) super.createNewForm(mapping, form, request, response);
		String parent = request.getParameter("parent");
		if (StringUtil.isNotNull(parent)) {
			SysOrgDept dept = (SysOrgDept) getSysOrgDeptService().findByPrimaryKey(parent);
			personForm.setFdParentId(parent);
			personForm.setFdParentName(dept.getFdName());
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
		Integer order = sysOrgDefaultConfig.getOrgPersonDefaultOrder();
		String psw = sysOrgDefaultConfig.getOrgDefaultPassword();
		if (order != null) {
			personForm.setFdOrder(String.valueOf(order));
		}
		if (StringUtil.isNotNull(psw)) {
			personForm.setFdNewPassword(psw);
		}
		personForm.setFdCanLogin(true);
		return personForm;
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		initProp(request);
		return super.add(mapping, form, request, response);
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		// initProp(request);
		return super.edit(mapping, form, request, response);
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		ActionForward af = super.list(mapping, form, request, response);
		// 根据上级查询
		String parent = request.getParameter("parent");
		if (StringUtil.isNotNull(parent)) {
			SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parent);
			if (element != null) {
				String cateId = element.getFdId();
				SysOrgElement org = element.getFdParentOrg();
				if (org != null) {
					cateId = org.getFdId();
				}
				SysOrgElementExternal external = (SysOrgElementExternal) getSysOrgElementExternalService().findByPrimaryKey(cateId);
				request.setAttribute("props", external.getFdPersonProps());
				Page page = (Page) request.getAttribute("queryPage");
				List<SysOrgPerson> list = page.getList();
				if (CollectionUtils.isNotEmpty(list)) {
					for (SysOrgPerson person : list) {
						// 查询自定义属性
						Map<String, String> map = getServiceImp(request).getExtProp(external, person.getFdId(), true);
						person.setDynamicMap(map);
					}
				}
			}
		}
		return af;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			// 发现异常，需要重新加载扩展属性
			initProp(request);
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			// 发现异常，需要重新加载扩展属性
			initProp(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			if (MobileUtil.getClientType(request) != MobileUtil.PC) {
				if (e instanceof RuntimeException) {
					response(response, e.getMessage());
					return null;
				}
			}
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			// 发现异常，需要重新加载扩展属性
			initProp(request);
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			// 返回按钮
			KmssReturnPage.getInstance(request).addMessages(messages).addButton("button.back",
					"/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=view&fdId="
							+ ((IExtendForm) form).getFdId(),
					false).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	private void response(HttpServletResponse response, String message)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		json.put("message", message);
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
	}

	/**
	 * 初始化
	 *
	 * @param request
	 */
	private void initProp(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdId");
		String parent = request.getParameter("parent");
		String cateId = null;
		if (StringUtil.isNull(fdId) && StringUtil.isNull(parent)) {
			throw new RuntimeException("人员ID或组织ID不能为空！");
		}

		if (StringUtil.isNotNull(parent)) {
			SysOrgDept dept = (SysOrgDept) getSysOrgDeptService().findByPrimaryKey(parent, null, true);
			if (dept == null) {
				throw new RuntimeException("没有找到上级组织！");
			}
			cateId = dept.getFdParentOrg().getFdId();
		} else {
			SysOrgPerson person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(fdId, null, true);
			cateId = person.getFdParentOrg().getFdId();
		}
		if (StringUtil.isNull(cateId)) {
			throw new RuntimeException("没有找到外部组织类型！");
		}
		SysOrgElementExternal external = (SysOrgElementExternal) getSysOrgElementExternalService().findByPrimaryKey(cateId, null, true);

		// 排序
		List<SysOrgElementExtProp> props = new ArrayList<SysOrgElementExtProp>(external.getFdPersonProps());
		Iterator<SysOrgElementExtProp> iterator = props.iterator();
		while (iterator.hasNext()) {
			SysOrgElementExtProp prop = iterator.next();
			if (!BooleanUtils.isTrue(prop.getFdStatus())) {
				// 移除禁用的属性
				iterator.remove();
			}
		}
		Collections.sort(props, new Comparator<SysOrgElementExtProp>() {
			@Override
			public int compare(SysOrgElementExtProp o1, SysOrgElementExtProp o2) {
				Integer num1 = o1.getFdOrder();
				Integer num2 = o2.getFdOrder();
				if (num2 == null) {
					return 0;
				}
				if (num1 == null) {
					return -1;
				}
				if (num2 < num1) {
					return 0;
				} else if (num1.equals(num2)) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		request.setAttribute("props", props);
		request.setAttribute("cateId", cateId);
	}

	public ActionForward initInvitePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId);
			request.setAttribute("sysOrgElementName", sysOrgElement.getFdName());

			SysOrgElementRange range = sysOrgElement.getFdRange();
			String inviteUrl = "";
			if(range != null && StringUtil.isNotNull((inviteUrl = range.getFdInviteUrl()))) {
				request.setAttribute("dingdingUrl", inviteUrl);
			} else {
				request.setAttribute("qrCodeerror", ResourceUtil.getString("sysOrgElement.4m.qrcode.error", "sys-organization"));
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		return getActionForward("initInvitePage", mapping, form, request, response);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		// 外部组织入口只能查询生态组织
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			SysOrgElementForm elementForm = (SysOrgElementForm) form;
			if (!"true".equals(elementForm.getFdIsExternal())) {
				throw new NoRecordException();
			}
		}

		SysOrgPersonForm personForm = (SysOrgPersonForm) form;
		SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(personForm.getFdId());
		SysOrgElement parent = null;
		// 外转外时，使用新组织
		if (BooleanUtils.isTrue((Boolean) request.getAttribute("outToOut"))) {
			String pid = request.getParameter("parent");
			if (StringUtil.isNotNull(pid)) {
				parent = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(pid);
			}
		} else {
			parent = sysOrgElement.getFdParent();
		}
		if (parent != null) {
			personForm.setFdParentId(parent.getFdId());
			personForm.setFdParentName(parent.getFdName());
		}
		List<SysOrgElement> list = sysOrgElement.getAllParent(true);
		if (!list.isEmpty()) {
			for (SysOrgElement org : list) {
				if (org.getFdOrgType() != null && 1 == org.getFdOrgType()) {
					request.setAttribute("sysOrgEcoName", org.getFdName());
					break;
				}
			}
		}
		initProp(request);
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				SysOrgPerson person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(fdId);
				if (person != null) {
					request.setAttribute("postList", person.getFdPosts());
					if (person.getFdParent() != null) {
						if (person.getFdParent().getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT) {
							String orgType = "dept";
							request.setAttribute("orgType", orgType);
						} else if (person.getFdParent().getFdOrgType() == SysOrgConstant.ORG_TYPE_ORG) {
							String orgType = "org";
							request.setAttribute("orgType", orgType);
						}
					}
				}
				if (person != null && person.getFdLockTime() != null) {
					int time = LoginConfig.lockTime(person);
					if (time > 0) {
						request.setAttribute("unlock", "true");
						request.setAttribute("autoUnlockTime", time);
					}
				}
			}

			loadActionForm(mapping, form, request, response);
			getDDData(request);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	/**
	 * 获取钉钉api接口所需要数据
	 *
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getDDData(HttpServletRequest request) throws Exception {
		ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		if(sysAppConfigService != null) {
			List<SysAppConfig> ddConfig = sysAppConfigService.findList(
					"fdKey='com.landray.kmss.third.ding.model.DingConfig' and fdField='dingEnabled' and fdValue='true'",
					null);
			if (!ddConfig.isEmpty()) {
				// 获取corpId
				List<SysAppConfig> corpIdList = sysAppConfigService.findList(
						"fdKey='com.landray.kmss.third.ding.model.DingConfig' and fdField='dingCorpid'",
						null);
				if (!corpIdList.isEmpty()) {
					request.setAttribute("corpId", corpIdList.get(0).getFdValue());

					// 获取人员对应的钉钉id
					String userId = request.getParameter("fdId");
					if (StringUtil.isNotNull(userId)) {
						List list = this.getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery("select fd_app_pk_id from oms_relation_model where fd_ekp_id =:userId and fd_type = '8'").setParameter("userId", userId).list();
						if (!list.isEmpty()) {
							String userDDId = (String) list.get(0);
							request.setAttribute("userDDId", userDDId);
							request.setAttribute("ekpId", userId);
						}
					}
				}
			}
		}
	}

	/**
	 * 置为无效
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidated(ActionMapping mapping, ActionForm form,
									 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).updateInvalidated(id,
						new RequestContext(request));
			}
		} catch (ExistChildrenException existChildren) {
			messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
							+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds=" + id
							+ "' target='_blank'>"
							+ ResourceUtil.getString("sysOrgDept.error.existChildren", "sys-organization")
							+ ResourceUtil.getString("sysOrgDept.error.existChildren.msg", "sys-organization") + "</a>"),
					existChildren);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 批量置为无效
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidatedAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			if (ids != null) {
				getServiceImp(request).updateInvalidated(ids, new RequestContext(request));
			}
		} catch (ExistChildrenException existChildren) {
			messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
							+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
							+ StringUtil.escape(ArrayUtil.concat(ids, ',')) + "' target='_blank'>"
							+ ResourceUtil.getString("sysOrgDept.error.existChildren", "sys-organization")
							+ ResourceUtil.getString("sysOrgDept.error.existChildren.msg", "sys-organization") + "</a>"),
					existChildren);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 外转外页面跳转
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward outToOut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String parent = request.getParameter("parent");
		if (StringUtil.isNull(fdId) || StringUtil.isNull(parent)) {
			throw new UnexpectedRequestException();
		}

		request.setAttribute("outToOut", true);
		SysOrgPersonForm personForm = (SysOrgPersonForm) form;
		personForm.setFdParentId(parent);
		// 设置编辑模式
		personForm.setMethod_GET("edit");
		return edit(mapping, form, request, response);
	}

	/**
	 * 外转外
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOutToOut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateOutToOut", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String parent = request.getParameter("parent");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateOutToOut", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 外转内
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOutToIn(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateOutToIn", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String parent = request.getParameter("parent");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateOutToIn", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 人员转外部人员
	 * @description:
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return: com.landray.kmss.web.action.ActionForward
	 * @author: wangjf
	 * @time: 2021/9/24 6:31 下午
	 */
	public ActionForward transformOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-transformOut", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String fdId = request.getParameter("fdId");
			String parentId = request.getParameter("parentId");
			if (StringUtil.isNotNull(fdId)) {
				SysOrgElement parentSysElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parentId);
				SysOrgElement sysElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId);
				List<SysOrgElement> sysElementList = new ArrayList<>();
				sysElementList.add(sysElement);
				getServiceImp(request).updateTransformOut(parentSysElement, sysElementList);
				getSysOrgElementService().updateRelation();
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-transformOut", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

}
