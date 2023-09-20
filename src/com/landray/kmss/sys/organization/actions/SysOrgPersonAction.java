package com.landray.kmss.sys.organization.actions;

import com.google.common.base.Joiner;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.LoginConfig;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.extend.actions.PluginExtendAction;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgPersonAddressTypeForm;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPersonAddressType;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonAddressTypeService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.AjaxUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version 1.0
 * @author
 */
public class SysOrgPersonAction extends PluginExtendAction implements
		SysOrgConstant {
	protected ISysOrgPersonService sysOrgPersonService = null;

	protected ISysOrgElementService sysOrgElementService = null;

	protected ISysOrgPersonAddressTypeService sysOrgPersonAddressTypeService;

	protected ISysOrgPersonAddressTypeService getSysOrgPersonAddressTypeServiceImp(
			HttpServletRequest request) {
		if (sysOrgPersonAddressTypeService == null) {
			sysOrgPersonAddressTypeService = (ISysOrgPersonAddressTypeService) getBean("sysOrgPersonAddressTypeService");
		}
		return sysOrgPersonAddressTypeService;
	}

	protected ISysZonePersonInfoService sysZonePersonInfoService;

	protected ISysZonePersonInfoService
	getSysZonePersonInfoServiceImp(HttpServletRequest request) {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) getBean(
					"sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}

	private IOrgRangeService orgRangeService;

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) getBean("orgRangeService");
		}
		return orgRangeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);

		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String para = request.getParameter("available");
		String isAbandon = request.getParameter("abandon");
		whereBlock += " and sysOrgPerson.fdIsAvailable= :fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true
				: false);
		String fdIsExternal = request.getParameter("fdIsExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			if("false".equalsIgnoreCase(fdIsExternal)){
				whereBlock += " and ( sysOrgPerson.fdIsExternal =:fdIsExternal or sysOrgPerson.fdIsExternal is null ) ";
			}else{
				whereBlock += " and sysOrgPerson.fdIsExternal =:fdIsExternal ";
			}
			hqlInfo.setParameter("fdIsExternal", "true".equals(fdIsExternal));
		}
		if (StringUtil.isNotNull(isAbandon)) {
			whereBlock += " and sysOrgPerson.fdIsAbandon= :isAbandon ";
			hqlInfo.setParameter("isAbandon", isAbandon);
		} else {
			whereBlock += " and (sysOrgPerson.fdIsAbandon = false or sysOrgPerson.fdIsAbandon is null) ";
		}
		hqlInfo.setWhereBlock(whereBlock);
		changePersonFindPageHQLInfo(request, hqlInfo);
	}

	protected void changePersonFindPageHQLInfo(HttpServletRequest request,
											   HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String whereBlockPerson = "";
		// 新UED查询参数
		String fdName = request.getParameter("q.fdSearchName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdSearchName");
		}
		String all = request.getParameter("all");
		// 层级查询子部门员工
		String parent = request.getParameter("parent");

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if(isLangSupport){
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNotNull(fdName)) {
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			if (StringUtil.isNotNull(parent)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"sysOrgPerson.fdHierarchyId like :parent");
				hqlInfo.setParameter("parent", "%" + parent + "%");

				// #52116 搜索结果超过2000时报错
				// 下面这段代码不知道用意何在，但是会有一个问题，如果查询出来的数据超过2000，那么下面的逻辑会拼接2000个“or sysOrgPerson.fdHierarchyId like :hierId”
				// 性能先不说，这些查询条件的数量就超过了某些数据库的最大限制数量，会报“java.sql.SQLException:Prepared or callable statement has more than 2000 parameter markers.”的异常
//				String where_lang = "";
//				if(StringUtil.isNotNull(currentLocaleCountry)){
//					where_lang = " or "
//							+ SysLangUtil.getLangColumnName("fd_name")
//							+ " like :fdName";
//				}
//				String sql = "select fd_id from sys_org_element where (fd_name like :fdName or fd_name_pinyin like :pinyin or fd_name_simple_pinyin like :simplePinyin"+where_lang+") "
//					+ "and fd_hierarchy_id like :parent and fd_org_type = :orgType";
//				List list = getServiceImp(request).getBaseDao()
//				.getHibernateSession().createSQLQuery(sql)
//				.setParameter("fdName", "%" + fdName + "%")
//				.setParameter("pinyin", "%" + fdPinyinName + "%")
//						.setParameter("simplePinyin", "%" + fdPinyinName + "%")
//				.setParameter("parent", "%" + parent + "%")
//				.setParameter("orgType", ORG_TYPE_PERSON).list();
//				if (list.size() > 0) {
//					for (int i = 0; i < list.size(); i++) {
//						whereBlockPerson = StringUtil.linkString(
//								whereBlockPerson, " or ",
//								"sysOrgPerson.fdHierarchyId like :hierId"
//										+ i);
//						hqlInfo.setParameter("hierId" + i, "%" + list.get(i).toString() + "%");
//					}
//				}
			}
			// 姓名查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdName like :fdName ");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockPerson = StringUtil.linkString(whereBlockPerson,
						" or ",
						"sysOrgPerson.fdName" + currentLocaleCountry
								+ " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 姓名拼音查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 名称简拼查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");
			// 登录名查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdLoginName like :loginName ");
			hqlInfo.setParameter("loginName", "%" + fdName + "%");
			// 邮件地址查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdEmail like :fdEmail ");
			hqlInfo.setParameter("fdEmail", "%" + fdName + "%");
			// 手机号码查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdMobileNo like :fdMobileNo ");
			hqlInfo.setParameter("fdMobileNo", "%" + fdName + "%");
			// 编号查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ",
					"sysOrgPerson.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		} else if(!"true".equals(all)) {
			// 层级查询子部门人员，不需要此条件
			if (parent != null) {
				if ("".equals(parent)) {
					// 查看所有生态组织时，特殊处理
					if (!UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
						AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
						// 如果有查看范围限制，就取查看范围的根组织
						if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootPersonIds())) {
							whereBlock += " and (sysOrgPerson.hbmParent is null or sysOrgPerson.fdId in (" + SysOrgUtil.buildInBlock(orgRange.getRootPersonIds()) + "))";
						} else {
							whereBlock += " and sysOrgPerson.hbmParent is null ";
						}
					} else {
						whereBlock += " and sysOrgPerson.hbmParent is null ";
					}
				} else {
					whereBlock += " and sysOrgPerson.hbmParent.fdId =:hbmParentFdId ";
					hqlInfo.setParameter("hbmParentFdId", parent);
				}
			}
		}
		if (StringUtil.isNotNull(whereBlockPerson)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
					+ whereBlockPerson + " )");
		}
		String fdFlagDeleted = request.getParameter("fdFlagDeleted");
		if (StringUtil.isNotNull(fdFlagDeleted)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPerson.fdFlagDeleted = :fdFlagDeleted ");
			hqlInfo.setParameter("fdFlagDeleted", StringUtil
					.isNull(fdFlagDeleted) ? false : true);
		}
		String fdImportInfo = request.getParameter("fdImportInfo");
		if (StringUtil.isNotNull(fdImportInfo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPerson.fdImportInfo like :fdImportInfo ");
			hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
		}

		// 查询锁定的员工
		String locked = request.getParameter("locked");
		if ("true".equals(locked)) {
			// 获取配置锁定时间，判断是否已经自动解锁
			whereBlock = StringUtil.linkString(whereBlock, " and ", "sysOrgPerson.fdLockTime > :lockTime");
			hqlInfo.setParameter("lockTime", new Date());
		}
		//筛选是否与业务相关
		String fdIsBusiness = request.getParameter("q.fdIsBusiness");
		if (StringUtil.isNotNull(fdIsBusiness)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPerson.fdIsBusiness = :fdIsBusiness ");
			Boolean fdIsBusinessNext=false;
			if(Integer.parseInt(fdIsBusiness)==1) {
				fdIsBusinessNext=true;
			}

			hqlInfo.setParameter("fdIsBusiness", fdIsBusinessNext);
		}
		// 开启三员后，如果不是查询待激活人中，应该过滤未激活
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			String available = request.getParameter("available");
			if (StringUtil.isNull(available)) {
				whereBlock += " and sysOrgPerson.fdIsActivated =:fdIsActivated ";
				hqlInfo.setParameter("fdIsActivated", true);
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		// 如果是机构管理员 或 使用所有组织 ，不需要过滤权限
		if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
		}
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		String currentLocaleCountry = null;
		if (SysLangUtil.isLangEnabled()) {
			currentLocaleCountry = SysLangUtil
					.getCurrentLocaleCountry();
			if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNull(curOrderBy)) {
			String orderby = "sysOrgPerson.fdOrder,sysOrgPerson.fdName";
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				orderby += currentLocaleCountry;
			}
			return orderby;
		} else {
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				if (curOrderBy.contains(" ")) {
					String orderby = curOrderBy.substring(0,
							curOrderBy.indexOf(" "));
					if ("fdName".equals(orderby)) {
						return "fdName" + currentLocaleCountry
								+ curOrderBy.substring(curOrderBy.indexOf(" "));
					}
				} else {
					if ("fdName".equals(curOrderBy)) {
						return "fdName" + currentLocaleCountry;
					}
				}
			}

		}
		return curOrderBy;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysOrgPersonForm personForm = (SysOrgPersonForm) form;
		personForm.reset(mapping, request);

		String parent = request.getParameter("parent");
		if (!StringUtil.isNull(parent)) {
			SysOrgElement parentModel = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(parent);
			personForm.setFdParentId(parentModel.getFdId());
			personForm.setFdParentName(parentModel.getDeptLevelNames());
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();

		Integer order = sysOrgDefaultConfig.getOrgPersonDefaultOrder();
		String psw = sysOrgDefaultConfig.getOrgDefaultPassword();
		if(order!=null) {
			personForm.setFdOrder(String.valueOf(order));
		}
		if(StringUtil.isNotNull(psw)) {
			personForm.setFdNewPassword(psw);
		}

		return personForm;
	}

	public ActionForward addressModify(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			IExtendForm rtnForm = null;
			String id = UserUtil.getUser(request).getFdId();
			Map<String,List<String>> addressTypeMap=new HashMap<>();
			if (!StringUtil.isNull(id)) {
				IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
						SysOrgPerson.class, true);
				if (model != null) {
					List list = getSysOrgPersonAddressTypeServiceImp(request)
							.findList(
									"docCreator.fdId='"
											+ UserUtil.getUser().getFdId()
											+ "'", "fdOrder");
					SysOrgPerson person = (SysOrgPerson) model;
					person.setAddressTypeList(list);
					rtnForm = getServiceImp(request).convertModelToForm(
							(IExtendForm) form, model,
							new RequestContext(request));

					try {
						if(CollectionUtils.isNotEmpty(list)) {
							for(Object addressObj:list) {
								SysOrgPersonAddressType address=(SysOrgPersonAddressType)addressObj;
								List<String> fdTypeMemberNameList=new ArrayList();
								List addressTypeList=address.getSysOrgPersonTypeList();
								if(CollectionUtils.isNotEmpty(addressTypeList)) {
									for(Object addressTypeObj:addressTypeList) {
										SysOrgElement org=(SysOrgElement)addressTypeObj;
										fdTypeMemberNameList.add(org.getDeptLevelNames());
									}
								}
								addressTypeMap.put(address.getFdId(), fdTypeMemberNameList);
							}
						}

					}catch(Exception e) {

					}
				}
			}
			if (rtnForm == null) {
				throw new NoRecordException();
			}
			try {
				if(rtnForm instanceof SysOrgPersonForm) {
					SysOrgPersonForm rtnFormTemp=(SysOrgPersonForm) rtnForm;
					List list =rtnFormTemp.getAddressTypeList();
					if(CollectionUtils.isNotEmpty(list)) {
						for(Object addressObj:list) {
							SysOrgPersonAddressTypeForm address=(SysOrgPersonAddressTypeForm)addressObj;
							if(addressTypeMap.get(address.getFdId()) !=null) {
								PropertyUtils.setProperty(address, "fdTypeMemberNames", Joiner.on(";").join(addressTypeMap.get(address.getFdId()) ));
							}
						}
					}
				}

			}catch(Exception e) {

			}
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("address", mapping, form, request, response);
		}
	}

	public ActionForward updateAddress(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysOrgPersonForm personForm = (SysOrgPersonForm) form;
			getSysOrgPersonAddressTypeServiceImp(request).updateAddress(
					personForm.getAddressTypeList(),
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			if (AjaxUtil.requiredJson(request)) {
				AjaxUtil.saveMessagesToJson(request, KmssReturnPage
						.getInstance(request));
				return getActionForward("lui-failure", mapping, form, request,
						response);
			}
			return getActionForward("address", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			if (AjaxUtil.requiredJson(request)) {
				request.setAttribute("lui-source", new JSONObject().element(
						"success", true));
				return getActionForward("lui-source", mapping, form, request,
						response);
			}
			return getActionForward("addressUi", mapping, form, request,
					response);
		}
	}

	@Override
	protected ISysOrgPersonService getServiceImp(HttpServletRequest request) {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public ActionForward getUserInfoXML(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysOrgPerson person = UserUtil.getUser();
		if (person == null) {
			return null;
		}
		Document document = DocumentHelper.createDocument();
		Element rootElement = DocumentHelper.createElement("userInfo");
		// 设置编码
		document.setXMLEncoding("UTF-8");
		document.add(rootElement);
		addElement("fdId", person.getFdId(), rootElement);
		addElement("fdName", person.getFdName(), rootElement);
		addElement("fdEmail", person.getFdEmail(), rootElement);
		addElement("fdMobileNo", person.getFdMobileNo(), rootElement);
		if (person.getHbmParentOrg() != null) {
			addElement("fdCompanyName", person.getHbmParentOrg().getFdName(),
					rootElement);
		}

		String xmlString = document.asXML();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(xmlString);
		return null;
	}

	private void addElement(String nodeName, String nodeValue,
							Element rootElement) {
		Element element = DocumentHelper.createElement(nodeName);
		if (nodeValue != null) {
			element.setText(nodeValue);
		}
		rootElement.add(element);
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
	public ActionForward invalidatedAll(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			if (ids != null) {
				getServiceImp(request).updateInvalidated(ids,
						new RequestContext(request));
			}
		} catch (ExistChildrenException existChildren) {
			messages.addError(new KmssMessage("global.message", "<a href='" + request.getContextPath()
							+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
							+ StringUtil.escape(ArrayUtil.concat(ids, ',')) + "' target='_blank'>"
							+ ResourceUtil.getString("sysOrgDept.error.existChildren", "sys-organization")
							+ ResourceUtil.getString("sysOrgDept.error.existChildren.msg", "sys-organization") + "</a>"),
					existChildren);
		} catch (Exception e) {
			messages.addError(new KmssMessage("global.message", e.getMessage()));
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 判断此用户是否已锁定
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			SysOrgPerson person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(fdId);
			if (person != null) {
				if(person.getFdIsAvailable()){
					//在职
					List postList = person.getFdPosts();
					request.setAttribute("postList", postList);
					if (person.getFdParent() != null) {
						if (person.getFdParent().getFdOrgType() == ORG_TYPE_DEPT) {
							String orgType = "dept";
							request.setAttribute("orgType", orgType);
						} else if (person.getFdParent()
								.getFdOrgType() == ORG_TYPE_ORG) {
							String orgType = "org";
							request.setAttribute("orgType", orgType);
						}
					}
				}else{
					//离职需要展示原所在部门与岗位
					String preDeptId = person.getFdPreDeptId();
					SysOrgElement preDept = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(preDeptId);
					if (preDept != null) {
						if (preDept.getFdOrgType() == ORG_TYPE_DEPT) {
							String orgType = "dept";
							request.setAttribute("orgType", orgType);
						} else if (preDept.getFdOrgType() == ORG_TYPE_ORG) {
							String orgType = "org";
							request.setAttribute("orgType", orgType);
						}
						request.setAttribute("fdPreDeptId",person.getFdPreDeptId());
					}
					String[] postIds = person.getFdPrePostIdsArr();
					if(postIds != null && postIds.length != 0){
						List<SysOrgElement> postList = new ArrayList<>();
						for(String postId : postIds){
							postList.add((SysOrgElement) getSysOrgElementService().findByPrimaryKey(postId));
						}
						request.setAttribute("postList",postList);
					}
				}
			}
			if (person != null && person.getFdLockTime() != null) {
				int time = LoginConfig.lockTime(person);
				if(time > 0) {
					request.setAttribute("unlock", "true");
					request.setAttribute("autoUnlockTime", time);
				}
			}
		}
		return super.view(mapping, form, request, response);
	}

	/**
	 * 解锁用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePersonUnLock(ActionMapping mapping, ActionForm form,
										  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-unLock", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				SysOrgPerson person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(id);
				if (UserOperHelper.allowLogOper("savePersonUnLock", SysOrgPerson.class.getName())) {
					UserOperContentHelper.putUpdate(person);
				}
				getServiceImp(request).savePersonUnLock(person, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-unLock", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}


	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward af = super.edit(mapping, form, request, response);
		// 不允许编辑“匿名”和“EveryOne”用户
		if (SysOrgUtil.isAnonymousOrEveryOne(form)) {
			throw new KmssException(new KmssMessage("sys-organization:sysOrgPerson.error.anonymousOrEveryOne.edit"));
		}
		return af;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 不允许编辑“匿名”和“EveryOne”用户
		if (SysOrgUtil.isAnonymousOrEveryOne(form)) {
			throw new KmssException(new KmssMessage("sys-organization:sysOrgPerson.error.anonymousOrEveryOne.edit"));
		}
		if(StringUtil.isNotNull(request.getParameter("fdId"))){
			String fdId = request.getParameter("fdId");
			SysOrgPerson sysOrgPerson = new SysOrgPerson();
			sysOrgPerson.setFdId(fdId);
			getSysZonePersonInfoServiceImp(request)
					.updatePersonLastModifyTime(sysOrgPerson);
		}
		return super.update(mapping, form, request, response);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		SysOrgElementForm elementForm = (SysOrgElementForm) form;

		String outToIn = request.getParameter("outToIn");
		// 内部组织入口需要排除生态组织
		if (SysOrgEcoUtil.IS_ENABLED_ECO && !"true".equals(outToIn)) {
			if ("true".equals(elementForm.getFdIsExternal())) {
				throw new NoRecordException();
			}
		}
		// 外转内，需要清除上级组织
		if ("true".equals(outToIn)) {
			elementForm.setFdParentId(null);
			elementForm.setFdParentName(null);
		} else {
			// 部门名称显示层级
			SysOrgElement model = (SysOrgElement) getServiceImp(request).findByPrimaryKey(elementForm.getFdId());
			if (model != null && model.getFdParent() != null) {
				elementForm.setFdParentName(model.getFdParent().getDeptLevelNames());
			}
		}
	}

	/**
	 * 返回json数组，重新渲染地址本
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void changeDeptEdit(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fdIds = request.getParameter("fdIds");
		JSONArray valueArr = new JSONArray();
		if(StringUtil.isNotNull(fdIds)){
			String[] ids = fdIds.split(";");
			if(ids.length>0){
				JSONObject jsonObj = new JSONObject();
				List<SysOrgElement> orgList = getServiceImp(request).findByPrimaryKeys(ids);
				for(SysOrgElement org : orgList){
					jsonObj.put("id",org.getFdId());
					jsonObj.put("name",org.getFdName());
					valueArr.add(jsonObj);
				}

			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(valueArr.toString().getBytes("UTF-8"));
	}

	/**
	 * 批量更新人员部门（快捷调换部门）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void changeDept(ActionMapping mapping, ActionForm form,
						   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personIds = request.getParameter("personIds");
		String deptId = request.getParameter("deptId");
		JSONObject obj = new JSONObject();

		if (StringUtil.isNotNull(personIds) && StringUtil.isNotNull(deptId)) {
			try {
				if (StringUtil.isNotNull(personIds) && StringUtil.isNotNull(deptId)) {
					getServiceImp(request).updateDeptByPersons(personIds.split(";"), deptId,new RequestContext(request));
				}
				obj.put("status", true);
			} catch (Exception e) {
				obj.put("status", false);
				obj.put("message", e.getMessage());
			}
		} else {
			obj.put("status", false);
			if (StringUtil.isNull(personIds)) {
				obj.put("message", ResourceUtil.getString("sys-organization:sysOrgPerson.quickChangeDept.fromPerson.null"));
			} else if (StringUtil.isNull(deptId)) {
				obj.put("message", ResourceUtil.getString("sys-organization:sysOrgPerson.toDept.fromPerson.null"));
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(obj.toString().getBytes("UTF-8"));
	}

	/**
	 * 恢复启用人员
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resume(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 不允许编辑“匿名”和“EveryOne”用户
		if (SysOrgUtil.isAnonymousOrEveryOne(form)) {
			throw new KmssException(new KmssMessage("sys-organization:sysOrgPerson.error.anonymousOrEveryOne.edit"));
		}
		TimeCounter.logCurrentTime("Action-resume", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				SysOrgPerson person = getServiceImp(request).resumePerson(id);
				if (person != null) {
					if (UserOperHelper.allowLogOper("resume", SysOrgPerson.class.getName())) {
						UserOperContentHelper.putUpdate(person);
					}
					getServiceImp(request).saveResumePerson(person, new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-resume", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 查询激活列表(三员管理使用)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward actiList(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-actiList", true, getClass());
		KmssMessages messages = new KmssMessages();

		// 没有开启三员管理，此功能不能使用
		if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			return getActionForward("e404", mapping, form, request, response);
		}
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			// 查询类型：0待激活，1已激活
			String available = request.getParameter("available");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changePersonFindPageHQLInfo(request, hqlInfo);

			int _available = StringUtil.getIntFromString(available, 0);

			if (UserOperHelper.allowLogOper("Action_FindAll", SysOrgPerson.class.getName())) {
				UserOperHelper.setEventType(_available == 0
						? ResourceUtil.getString("sys-organization:org.personnel.activation.no.list")
						: ResourceUtil.getString("sys-organization:org.personnel.activation.yes.list"));
			}

			String where = "sysOrgPerson.fdIsAvailable = :available";
			hqlInfo.setParameter("available", Boolean.TRUE);
			if (_available == 0) {
				// 未激活
				where += " and (sysOrgPerson.fdIsActivated = :activated or sysOrgPerson.fdIsActivated is null)";
				hqlInfo.setParameter("activated", Boolean.FALSE);
			} else {
				// 已激活
				where += " and sysOrgPerson.fdIsActivated = :activated";
				hqlInfo.setParameter("activated", Boolean.TRUE);
			}

			// 根据激活状态查询
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));

			Page page = getServiceImp(request).findPage(hqlInfo);
			if (UserOperHelper.allowLogOper("Action_FindAll", SysOrgPerson.class.getName())) {
				UserOperContentHelper.putFinds(page.getList());
			}
			request.setAttribute("queryPage", page);
			request.setAttribute("actiList", "true");
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-actiList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	/**
	 * 激活人员(三员管理使用)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activation(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-activation", true, getClass());
		KmssMessages messages = new KmssMessages();
		// 此操作只有安全保密管理员可以操作
		if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN
				|| !TripartiteAdminUtil.isSecurity()) {
			return getActionForward("e404", mapping, form, request, response);
		}

		try {
			String[] ids = request.getParameterValues("List_Selected");
			for (String id : ids) {
				// 激活操作属于比较敏感的操作，需要记录激活的ID和名称
				SysOrgPerson person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(id);
				if (UserOperHelper.allowLogOper("Service_Update", SysOrgPerson.class.getName())) {
					UserOperContentHelper.putUpdate(person).putSimple("fdIsActivated", 0, 1);
				}
				getServiceImp(request).updatePersonActivation(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-activation", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			if (TripartiteAdminUtil.isSysadmin()) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			} else {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			}
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	/**
	 * 保存特权用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePrivilege(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			int count = 0;
			if(StringUtil.isNotNull(ids)) {
				count = getServiceImp(request).savePrivilege(ids.split("[,;]"));
			}
			result.put("count", count);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
		return null;
	}

	/**
	 * 删除特权用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePrivilege(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String personId = request.getParameter("personId");
			if(StringUtil.isNotNull(personId)) {
				getServiceImp(request).deletePrivilege(personId);
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
		return null;
	}

	/**
	 * 查询特权用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPrivileges(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			com.alibaba.fastjson.JSONArray array = getServiceImp(request).getPrivileges(request);
			result.put("data", array);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
		return null;
	}

	/**
	 * 获取特权用户数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPrivilegeCounts(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			com.alibaba.fastjson.JSONObject obj = getServiceImp(request).getPrivilegeCounts();
			result.put("data", obj);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(result);
		return null;
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = super.list(mapping, form, request, response);
		if("0".equalsIgnoreCase(request.getParameter("available"))){
			//无效架构需要展示原所在部门与原所在岗位
			Page page = (Page) request.getAttribute("queryPage");
			List<SysOrgPerson> list = page.getList();
			for(SysOrgPerson person : list){
				String preDeptId = person.getFdPreDeptId();
				SysOrgElement preDept = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(preDeptId);
				if(preDept != null){
					person.setFdParent(preDept);
				}
				String[] prePostIds = person.getFdPrePostIdsArr();
				if(prePostIds != null && prePostIds.length != 0){
					List<SysOrgElement> postList = new ArrayList<>();
					for(String postId : prePostIds){
						postList.add((SysOrgElement) getSysOrgElementService().findByPrimaryKey(postId));
					}
					person.setFdPosts(postList);
				}
			}
		}
		return actionForward;
	}
}
