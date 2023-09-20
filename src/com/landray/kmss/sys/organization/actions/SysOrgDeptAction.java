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
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgDeptForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version 1.0
 * @author
 */
public class SysOrgDeptAction extends ExtendAction implements SysOrgConstant {
	private ISysOrgDeptService sysOrgDeptService = null;

	private ISysOrgElementService sysOrgElementService = null;

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
		whereBlock += " and sysOrgDept.fdIsAvailable =:fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true
				: false);
		String fdIsExternal = request.getParameter("fdIsExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			if("false".equalsIgnoreCase(fdIsExternal)){
				whereBlock += " and ( sysOrgDept.fdIsExternal =:fdIsExternal or sysOrgDept.fdIsExternal is null ) ";
			}else{
				whereBlock += " and sysOrgDept.fdIsExternal =:fdIsExternal ";
			}
			hqlInfo.setParameter("fdIsExternal", "true".equals(fdIsExternal));
		}
		hqlInfo.setWhereBlock(whereBlock);
		changeDeptFindPageHQLInfo(request, hqlInfo);
	}

	protected void changeDeptFindPageHQLInfo(HttpServletRequest request,
											 HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();

		// 新UED查询参数
		String fdName = request.getParameter("q.fdSearchName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdSearchName");
		}

		String all = request.getParameter("all");
		String parent = request.getParameter("parent");

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if (isLangSupport) {
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if(StringUtil.isNotNull(currentLocaleCountry)&&currentLocaleCountry.equals(SysLangUtil.getOfficialLang())){
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNotNull(fdName)) { // 层级查询子部门，当fdName不为空时显示子部门及其下级部门
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			String whereBlockDept = "";
			if (StringUtil.isNotNull(parent)) {
				// 第一级查询——父层级ID过滤（parent）
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"sysOrgDept.fdHierarchyId like :parent");
				hqlInfo.setParameter("parent", "%" + parent + "%");

				String where_lang = "";
				if (StringUtil.isNotNull(currentLocaleCountry)) {
					where_lang = " or "
							+ SysLangUtil.getLangColumnName("fd_name")
							+ " like :fdName";
				}
				// 第二级查询——子部门、孙部门层级ID查询
				String sql = "select fd_id from sys_org_element where (fd_name like :fdName or fd_name_pinyin like :pinyin or fd_name_simple_pinyin like :simplePinyin"
						+ where_lang + ") "
						+ "and fd_hierarchy_id like :parent and fd_org_type = :orgType";
				List list = getServiceImp(request).getBaseDao()
						.getHibernateSession().createSQLQuery(sql)
						.setParameter("fdName", "%" + fdName + "%")
						.setParameter("pinyin", "%" + fdPinyinName + "%")
						.setParameter("simplePinyin", "%" + fdPinyinName + "%")
						.setParameter("parent", "%" + parent + "%")
						.setParameter("orgType", ORG_TYPE_DEPT).list();
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						whereBlockDept = StringUtil.linkString(whereBlockDept,
								" or ", "sysOrgDept.fdHierarchyId like :hierId"
										+ i);
						hqlInfo.setParameter("hierId" + i, "%"
								+ list.get(i).toString() + "%");
					}
				}
			}
			// 部门名称查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
					"sysOrgDept.fdName like :fdName ");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockDept = StringUtil.linkString(whereBlockDept,
						" or ",
						"sysOrgDept.fdName" + currentLocaleCountry
								+ " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 编号查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
					"sysOrgDept.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称拼音查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
					"sysOrgDept.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 名称简拼查询
			whereBlockDept = StringUtil.linkString(whereBlockDept, " or ",
					"sysOrgDept.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");

			if (StringUtil.isNotNull(whereBlockDept)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
						+ whereBlockDept + " )");
			}
		} else if(!"true".equals(all)) { // 层级查询子部门，当fdName为空时仅显示子部门
			if (parent != null) {
				if ("".equals(parent)) {
					// 查看所有生态组织时，特殊处理
					if (!UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
						AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
						// 如果有查看范围限制，就取查看范围的根组织
						if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
							whereBlock += " and (sysOrgDept.hbmParent is null or sysOrgDept.fdId in (" + SysOrgUtil.buildInBlock(orgRange.getRootDeptIds()) + "))";
						} else {
							whereBlock += " and sysOrgDept.hbmParent is null ";
						}
					} else {
						whereBlock += " and sysOrgDept.hbmParent is null ";
					}
				} else {
					whereBlock += " and sysOrgDept.hbmParent.fdId=:fdId ";
					hqlInfo.setParameter("fdId", parent);
				}
			}
		}

		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"sysOrgDept.fdOrgType = :orgType ");
		hqlInfo.setParameter("orgType", ORG_TYPE_DEPT);
		String fdFlagDeleted = request.getParameter("fdFlagDeleted");
		if (StringUtil.isNotNull(fdFlagDeleted)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgDept.fdFlagDeleted = :fdFlagDeleted ");
			hqlInfo.setParameter("fdFlagDeleted", StringUtil
					.isNull(fdFlagDeleted) ? false : true);
		}
		String fdImportInfo = request.getParameter("fdImportInfo");
		if (StringUtil.isNotNull(fdImportInfo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgDept.fdImportInfo like :fdImportInfo ");
			hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
		}
		//筛选是否与业务相关
		String fdIsBusiness = request.getParameter("q.fdIsBusiness");
		if (StringUtil.isNotNull(fdIsBusiness)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgDept.fdIsBusiness = :fdIsBusiness ");
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
			String orderby = "sysOrgDept.fdOrder,sysOrgDept.fdName";
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
		SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
		deptForm.reset(mapping, request);
		String parent = request.getParameter("parent");
		if (!StringUtil.isNull(parent)) {
			SysOrgElement parentModel = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(parent);
			deptForm.setFdParentId(parentModel.getFdId());
			deptForm.setFdParentName(parentModel.getDeptLevelNames());
			SysOrgUtil.getHideRangeTip(request, parentModel);
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();

		Integer order = sysOrgDefaultConfig.getOrgDeptDefaultOrder();
		if(order!=null) {
            deptForm.setFdOrder(String.valueOf(order));
        }
		return deptForm;
	}

	@Override
	protected ISysOrgDeptService getServiceImp(HttpServletRequest request) {
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

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		// 内部组织入口需要排除生态组织
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			SysOrgElementForm elementForm = (SysOrgElementForm) form;
			if ("true".equals(elementForm.getFdIsExternal())) {
				throw new NoRecordException();
			}
		}

		// 获取配置信息
		SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
		deptForm.setFdIsRelation(new SysOrganizationConfig().getIsRelation());

		// 部门名称显示层级
		SysOrgElementForm elementForm = (SysOrgElementForm) form;
		SysOrgElement model = (SysOrgElement) getServiceImp(request).findByPrimaryKey(elementForm.getFdId());
		if (model != null && model.getFdParent() != null) {
			elementForm.setFdParentName(model.getFdParent().getDeptLevelNames());
			SysOrgUtil.getHideRangeTip(request, model.getFdParent());
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
		String deptIds = request.getParameter("deptIds");
		String parentId = request.getParameter("parentId");
		JSONObject obj = new JSONObject();

		if (StringUtil.isNotNull(deptIds) && StringUtil.isNotNull(parentId)) {
			try {
				if (StringUtil.isNotNull(deptIds)
						&& StringUtil.isNotNull(parentId)) {
					getServiceImp(request).updateParentByDepts(deptIds.split(";"), parentId);
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
			if (StringUtil.isNull(deptIds)) {
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

}
