package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgGroupForm;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @version 1.0
 * @author
 */
public class SysOrgGroupAction extends ExtendAction implements SysOrgConstant {
	private ISysOrgGroupService sysOrgGroupService = null;

	private ISysOrgGroupCateService sysOrgGroupCateService = null;

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String para = request.getParameter("available");
		String parentCate = request.getParameter("parentCate");
		whereBlock += " and sysOrgGroup.fdIsAvailable= :fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true
				: false);

		if (StringUtil.isNotNull(parentCate)) {
			whereBlock += " and sysOrgGroup.hbmGroupCate.fdId =:fdId ";
			hqlInfo.setParameter("fdId", parentCate);
		}
		hqlInfo.setWhereBlock(whereBlock);
		changeGroupFindPageHQLInfo(request, hqlInfo);
	}

	protected void changeGroupFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String whereBlockGroup = "";
		// 新UED查询参数
		String fdName = request.getParameter("q.fdName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdName");
		}

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if (isLangSupport) {
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNotNull(fdName)) {
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			request.setAttribute("fdName", fdName);
			// 群组名称查询
			whereBlockGroup = StringUtil.linkString(whereBlockGroup, " or ",
					"sysOrgGroup.fdName like :fdName ");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockGroup = StringUtil.linkString(whereBlockGroup,
						" or ",
						"sysOrgGroup.fdName" + currentLocaleCountry
								+ " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称拼音查询
			whereBlockGroup = StringUtil.linkString(whereBlockGroup, " or ",
					"sysOrgGroup.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 名称简拼查询
			whereBlockGroup = StringUtil.linkString(whereBlockGroup, " or ",
					"sysOrgGroup.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");
			// 编号查询
			whereBlockGroup = StringUtil.linkString(whereBlockGroup, " or ",
					"sysOrgGroup.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");

			whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
					+ whereBlockGroup + " )");
		}
		String fdFlagDeleted = request.getParameter("fdFlagDeleted");
		if (StringUtil.isNotNull(fdFlagDeleted)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgGroup.fdFlagDeleted = :fdFlagDeleted ");
			hqlInfo.setParameter("fdFlagDeleted", StringUtil
					.isNull(fdFlagDeleted) ? false : true);
		}
		String fdImportInfo = request.getParameter("fdImportInfo");
		if (StringUtil.isNotNull(fdImportInfo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgGroup.fdImportInfo like :fdImportInfo ");
			hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
		}
		hqlInfo.setWhereBlock(whereBlock);
		
		//String sys_page = request.getParameter("sys_page");
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
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
			String orderby = "sysOrgGroup.fdOrder,sysOrgGroup.fdName";
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
		SysOrgGroupForm groupForm = (SysOrgGroupForm) form;
		groupForm.reset(mapping, request);
		String parent = request.getParameter("parentCate");
		if (!StringUtil.isNull(parent)) {
			SysOrgGroupCate parentModel = (SysOrgGroupCate) getSysOrgGroupCateService()
					.findByPrimaryKey(parent);
			groupForm.setFdGroupCateId(parentModel.getFdId());
			groupForm.setFdGroupCateName(parentModel.getFdName());
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
		
		Integer order = sysOrgDefaultConfig.getOrgGroupDefaultOrder();
		if(order!=null) {
            groupForm.setFdOrder(String.valueOf(order));
        }
		return groupForm;
	}

	@Override
	protected ISysOrgGroupService getServiceImp(HttpServletRequest request) {
		if (sysOrgGroupService == null) {
            sysOrgGroupService = (ISysOrgGroupService) getBean("sysOrgGroupService");
        }
		return sysOrgGroupService;
	}

	public ISysOrgGroupCateService getSysOrgGroupCateService() {
		if (sysOrgGroupCateService == null) {
            sysOrgGroupCateService = (ISysOrgGroupCateService) getBean("sysOrgGroupCateService");
        }
		return sysOrgGroupCateService;
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
                getServiceImp(request).updateInvalidated(id,new RequestContext(request));
            }
		} catch (ExistChildrenException existChildren) {
			messages
					.addError(
							new KmssMessage(
									"global.message",
									"<a href='"
											+ request.getContextPath()
											+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
											+ id
											+ "' target='_blank'>"
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren",
															"sys-organization")
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren.msg",
															"sys-organization")
											+ "</a>"), existChildren);
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
                getServiceImp(request).updateInvalidated(ids,new RequestContext(request));
            }
		} catch (ExistChildrenException existChildren) {
			messages
					.addError(
							new KmssMessage(
									"global.message",
									"<a href='"
											+ request.getContextPath()
											+ "/sys/organization/sys_org_element/sysOrgElement.do?method=findChildrens&fdIds="
											+ StringUtil.escape(ArrayUtil
													.concat(ids, ','))
											+ "' target='_blank'>"
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren",
															"sys-organization")
											+ ResourceUtil
													.getString(
															"sysOrgDept.error.existChildren.msg",
															"sys-organization")
											+ "</a>"), existChildren);
		} catch (Exception e) {
			messages.addError(e);
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
}
