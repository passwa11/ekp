package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgOrgForm;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author
 */
public class SysOrgOrgAction extends ExtendAction implements SysOrgConstant {
	private ISysOrgOrgService sysOrgOrgService = null;

	protected ISysOrgElementService sysOrgElementService = null;

	protected ISysOrgElementService getSysOrgElementServiceImp(
			HttpServletRequest request) {
		if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
		return sysOrgElementService;
	}

	private IOrgRangeService orgRangeService;

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) getBean("orgRangeService");
		}
		return orgRangeService;
	}

	/**
	 * 更新所有架构，用于写入oms接出的SysOMSCache中
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAllElement(ActionMapping mapping,
										  ActionForm form, HttpServletRequest request,
										  HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock("sysOrgElement.fdName != :fdName and sysOrgElement.fdIsAvailable = :fdIsAvailable");
			hqlInfo.setParameter("fdName", "匿名用户");
			hqlInfo.setParameter("fdIsAvailable", true);
			List allElementList = getSysOrgElementServiceImp(request)
					.findList(hqlInfo);
			for (int i = 0; i < allElementList.size(); i++) {
				SysOrgElement sysOrgElement = (SysOrgElement) allElementList
						.get(i);
				getSysOrgElementServiceImp(request)
						.update(sysOrgElement);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
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
		whereBlock += " and sysOrgOrg.fdIsAvailable= :fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true
				: false);
		String fdIsExternal = request.getParameter("fdIsExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			if("false".equalsIgnoreCase(fdIsExternal)){
				whereBlock += " and ( sysOrgOrg.fdIsExternal =:fdIsExternal or sysOrgOrg.fdIsExternal is null ) ";
			}else{
				whereBlock += " and sysOrgOrg.fdIsExternal =:fdIsExternal ";
			}
			hqlInfo.setParameter("fdIsExternal", "true".equals(fdIsExternal));
		}
		hqlInfo.setWhereBlock(whereBlock);
		changeOrgFindPageHQLInfo(request, hqlInfo);
		// 如果是机构管理员 或 使用所有组织 ，不需要过滤权限
		if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
		}
	}

	protected void changeOrgFindPageHQLInfo(HttpServletRequest request,
											HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		// 新UED查询参数
		String fdName = request.getParameter("q.fdSearchName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdSearchName");
		}
		String all = request.getParameter("all");
		String whereBlockOrg = "";
		String whereBlockOrg2 = "";
		String parent = request.getParameter("parent");

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if (isLangSupport) {
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
		}

		if (StringUtil.isNotNull(fdName)) {
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			if (StringUtil.isNotNull(parent)) {
				whereBlockOrg2 = StringUtil.linkString(whereBlockOrg2, " or ",
						"sysOrgOrg.hbmParent.fdId like :parent");
				hqlInfo.setParameter("parent", "%" + parent + "%");
				List list = new ArrayList();
				String where_lang = "";
				if (StringUtil.isNotNull(currentLocaleCountry)) {
					where_lang = " or "
							+ SysLangUtil.getLangColumnName("fd_name")
							+ " like :fdName";
				}
				String sql = "select fd_id from sys_org_element where (fd_name like :fdName or fd_name_pinyin like :pinyin or fd_name_simple_pinyin like :simplePinyin"
						+ where_lang + ") "
						+ "and fd_parentid like :parent and fd_org_type = :orgType";
				List listTmp = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdName", "%" + fdName + "%").setParameter("pinyin", "%" + fdPinyinName + "%").setParameter("simplePinyin", "%" + fdPinyinName + "%").setParameter("parent", "%" + parent + "%").setParameter("orgType", ORG_TYPE_ORG).list();
				if (listTmp.size() > 0) {
					for (int i = 0; i < listTmp.size(); i++) {
						list.add(listTmp.get(i));
					}
					do {
						List listTmp2 = new ArrayList();
						for (int i = 0; i < listTmp.size(); i++) {
							listTmp2 = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdName", "%" + fdName + "%").setParameter("pinyin",
											"%" + fdPinyinName + "%")
									.setParameter("simplePinyin",
											"%" + fdPinyinName + "%")
									.setParameter("parent", "%" + listTmp.get(i).toString() + "%")
									.setParameter("orgType", ORG_TYPE_ORG)
									.list();
						}
						listTmp.clear();
						if (listTmp2.size() > 0) {
							for (int j = 0; j < listTmp2.size(); j++) {
								list.add(listTmp2.get(j));
								listTmp.add(listTmp2.get(j));
							}
						}
					} while (listTmp.size() > 0);
				}
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						whereBlockOrg2 = StringUtil.linkString(whereBlockOrg2,
								" or ", "sysOrgOrg.hbmParent.fdId like :orgParent"+i);
						hqlInfo.setParameter("orgParent" + i, "%"	+ list.get(i).toString() + "%");
					}
				}
			}
			// 机构名称查询
			whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
					"sysOrgOrg.fdName like :fdName");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockOrg = StringUtil.linkString(whereBlockOrg,
						" or ",
						"sysOrgOrg.fdName" + currentLocaleCountry
								+ " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 编号查询
			whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
					"sysOrgOrg.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称拼音查询
			whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
					"sysOrgOrg.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 名称简拼查询
			whereBlockOrg = StringUtil.linkString(whereBlockOrg, " or ",
					"sysOrgOrg.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");

			whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
					+ whereBlockOrg + " )");

			if (StringUtil.isNotNull(whereBlockOrg2)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
						+ whereBlockOrg2 + " )");
			}
		} else if(!"true".equals(all)) {
			if (parent != null) {
				if ("".equals(parent)) {
					// 查看所有生态组织时，特殊处理
					if (!UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
						AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
						// 如果有查看范围限制，就取查看范围的根组织
						if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
							whereBlock += " and (sysOrgOrg.hbmParent is null or sysOrgOrg.fdId in (" + SysOrgUtil.buildInBlock(orgRange.getRootDeptIds()) + "))";
						} else {
							whereBlock += " and sysOrgOrg.hbmParent is null ";
						}
					} else {
						whereBlock += " and sysOrgOrg.hbmParent is null ";
					}
				} else {
					whereBlock += " and sysOrgOrg.hbmParent.fdId=:hbmParentFdId ";
					hqlInfo.setParameter("hbmParentFdId", parent);
				}
			}
		}
		String fdFlagDeleted = request.getParameter("fdFlagDeleted");
		if (StringUtil.isNotNull(fdFlagDeleted)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgOrg.fdFlagDeleted = :fdFlagDeleted ");
			hqlInfo.setParameter("fdFlagDeleted", StringUtil
					.isNull(fdFlagDeleted) ? false : true);
		}
		String fdImportInfo = request.getParameter("fdImportInfo");
		if (StringUtil.isNotNull(fdImportInfo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgOrg.fdImportInfo like :fdImportInfo ");
			hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");

		}

		//筛选是否与业务相关
		String fdIsBusiness = request.getParameter("q.fdIsBusiness");
		if (StringUtil.isNotNull(fdIsBusiness)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgOrg.fdIsBusiness = :fdIsBusiness ");
			Boolean fdIsBusinessNext=false;
			if(Integer.parseInt(fdIsBusiness)==1) {
                fdIsBusinessNext=true;
            }

			hqlInfo.setParameter("fdIsBusiness", fdIsBusinessNext);
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
			String orderby = "sysOrgOrg.fdOrder,sysOrgOrg.fdName";
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
		SysOrgOrgForm orgForm = (SysOrgOrgForm) form;
		orgForm.reset(mapping, request);
		String parent = request.getParameter("parent");
		if (!StringUtil.isNull(parent)) {
			SysOrgElement parentModel = (SysOrgElement) sysOrgOrgService
					.findByPrimaryKey(parent);
			orgForm.setFdParentId(parentModel.getFdId());
			orgForm.setFdParentName(parentModel.getDeptLevelNames());
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();

		Integer order = sysOrgDefaultConfig.getOrgOrgDefaultOrder();
		if(order!=null) {
            orgForm.setFdOrder(String.valueOf(order));
        }
		return orgForm;
	}

	@Override
	protected ISysOrgOrgService getServiceImp(HttpServletRequest request) {
		if (sysOrgOrgService == null) {
            sysOrgOrgService = (ISysOrgOrgService) getBean("sysOrgOrgService");
        }
		return sysOrgOrgService;
	}

	public ActionForward updateRelation(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			getServiceImp(request).updateRelation();
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	/**
	 * 将部门设置为机构
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public ActionForward updateDeptToOrg(ActionMapping mapping,
										 ActionForm form, HttpServletRequest request,
										 HttpServletResponse response) throws IOException, Exception {
		String deptId = request.getParameter("deptId");
		if (!StringUtil.isNull(deptId)) {
			// 记录日志
			if (UserOperHelper.allowLogOper("updateDeptToOrg", getSysOrgElementServiceImp(request).getModelName())) {
				// 组织架构属于敏感数据，必要进可以查询数据库
				UserOperContentHelper.putUpdate(getSysOrgElementServiceImp(request).findByPrimaryKey(deptId));
			}
			if (getServiceImp(request)
					.updateDeptToOrg(deptId)) {
				return mapping.findForward("success");
			} else {
				return mapping.findForward("failure");
			}
		}
		return mapping.findForward("require");
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

	/**
	 * 打开新增页面。<br>
	 * 该操作的大部分代码有具体业务逻辑由runAddAction实现，这里仅做错误以及页面跳转的处理。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (ObjectNotFoundException e) {
			messages.addError(new KmssMessage(
					"sys-organization:sysOrg.org.create.error"));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		// 内部组织入口需要排除生态组织
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			SysOrgElementForm elementForm = (SysOrgElementForm) form;
			if ("true".equals(elementForm.getFdIsExternal())) {
				throw new NoRecordException();
			}
		}

		// 部门名称显示层级
		SysOrgElementForm elementForm = (SysOrgElementForm) form;
		SysOrgElement model = (SysOrgElement) getServiceImp(request).findByPrimaryKey(elementForm.getFdId());
		if (model != null && model.getFdParent() != null) {
            elementForm.setFdParentName(model.getFdParent().getDeptLevelNames());
        }

		if (SysOrgEcoUtil.IS_ENABLED_ECO && elementForm.getFdRange() == null) {
			SysOrgElementRangeForm rangeForm = new SysOrgElementRangeForm();
			rangeForm.setFdIsOpenLimit("false");
			elementForm.setFdRange(rangeForm);
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
	 * 批量更新上级
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
		String orgIds = request.getParameter("orgIds");
		String parentId = request.getParameter("parentId");
		JSONObject obj = new JSONObject();
		if (StringUtil.isNotNull(orgIds) && StringUtil.isNotNull(parentId)) {
			try {
				if (StringUtil.isNotNull(orgIds)
						&& StringUtil.isNotNull(parentId)) {
					getServiceImp(request).updateParentByOrgs(orgIds.split(";"), parentId);
				}
				obj.put("status", true);
			} catch (Exception e) {
				obj.put("status", false);
				obj.put("message", ResourceUtil.getString(
						"sys-organization:sysOrganization.parent.dept.same.tip"));
				// 记录日志
				if (UserOperHelper.allowLogOper("changeDept", getServiceImp(request).getModelName())) {
					UserOperHelper.setOperSuccess(false);
					UserOperHelper.logErrorMessage(StringUtil.getStackTrace(e));
				}
			}
		} else {
			obj.put("status", false);
			if (StringUtil.isNull(orgIds)) {
				obj.put("message", ResourceUtil.getString(
						"sys-organization:sysOrgDept.quickChangeDept.from.null"));
			} else if (StringUtil.isNull(parentId)) {
				obj.put("message", ResourceUtil.getString(
						"sys-organization:sysOrgDept.quickChangeDept.toDept.null"));
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(obj.toString().getBytes("UTF-8"));
	}

	/**
	 * 判断父部门是否为机构
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public ActionForward parentIsOrg(ActionMapping mapping,
									 ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws IOException, Exception {

		JSONObject obj = new JSONObject();
		String deptId = request.getParameter("deptId");
		if (StringUtil.isNotNull(deptId)) {
			obj.put("isOrg", getServiceImp(request)
					.parentIsOrg(deptId));
		}else{
			obj.put("isOrg", false);
		}

		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(obj.toString().getBytes("UTF-8"));
		return null;
	}
}
