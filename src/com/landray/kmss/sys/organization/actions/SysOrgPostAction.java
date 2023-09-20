package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgPostForm;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version 1.0
 * @author
 */
public class SysOrgPostAction extends ExtendAction implements SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgPostAction.class);

	protected ISysOrgPostService sysOrgPostService = null;

	private ISysOrgElementService sysOrgElementService = null;

	protected ISysZonePersonInfoService sysZonePersonInfoService;

	private IOrgRangeService orgRangeService;

	public IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) getBean("orgRangeService");
		}
		return orgRangeService;
	}

	protected ISysZonePersonInfoService
	getSysZonePersonInfoServiceImp(HttpServletRequest request) {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) getBean(
					"sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
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
		whereBlock += " and sysOrgPost.fdIsAvailable= :fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true
				: false);
		String fdIsExternal = request.getParameter("fdIsExternal");
		if (StringUtil.isNotNull(fdIsExternal)) {
			whereBlock += " and sysOrgPost.fdIsExternal =:fdIsExternal ";
			hqlInfo.setParameter("fdIsExternal", "true".equals(fdIsExternal));
		}
		hqlInfo.setWhereBlock(whereBlock);
		changePostFindPageHQLInfo(request, hqlInfo);
	}

	protected void changePostFindPageHQLInfo(HttpServletRequest request,
											 HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String whereBlockPost = "";

		// 新UED查询参数
		String fdName = request.getParameter("q.fdSearchName");
		if (StringUtil.isNull(fdName)) {
			// 兼容旧UI查询方式
			fdName = request.getParameter("fdSearchName");
		}
		String all = request.getParameter("all");

		// 层级查询岗位
		String parent = request.getParameter("parent");
		// 查询条件fdName为空则只显示层级

		boolean isLangSupport = SysLangUtil.isLangEnabled();
		String currentLocaleCountry = null;
		if (isLangSupport) {
			currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if (StringUtil.isNotNull(currentLocaleCountry)
					&& currentLocaleCountry.equals(SysLangUtil
					.getOfficialLang())) {
				currentLocaleCountry = null;
			}
		}

		if (StringUtil.isNotNull(fdName)) {
			// 为兼容数据库对大小写敏感，在搜索拼音时，按小写搜索
			String fdPinyinName = fdName.toLowerCase();
			if (StringUtil.isNotNull(parent)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"sysOrgPost.fdHierarchyId like :parent");
				hqlInfo.setParameter("parent", "%" + parent + "%");
				String where_lang = "";
				if (StringUtil.isNotNull(currentLocaleCountry)) {
					where_lang = " or "
							+ SysLangUtil.getLangColumnName("fd_name")
							+ " like :fdName";
				}
				String sql = "select fd_id from sys_org_element where (fd_name like :fdName or fd_name_pinyin like :pinyin or fd_name_simple_pinyin like :simplePinyin"
						+ where_lang
						+ ") "
						+ "and fd_hierarchy_id like :parent and fd_org_type = :orgType";
				List list = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdName", "%" + fdName + "%").setParameter("pinyin", "%" + fdPinyinName + "%").setParameter("simplePinyin", "%" + fdPinyinName + "%").setParameter("parent", "%" + parent + "%").setParameter("orgType", ORG_TYPE_POST).list();
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						whereBlockPost = StringUtil.linkString(whereBlockPost,
								" or ", "sysOrgPost.fdHierarchyId like :hierId"
										+ i);
						hqlInfo.setParameter("hierId" + i, "%"
								+ list.get(i).toString() + "%");
					}
				}
			}
			// 岗位名称查询
			whereBlockPost = StringUtil.linkString(whereBlockPost, " or ",
					"sysOrgPost.fdName like :fdName ");
			if (StringUtil.isNotNull(currentLocaleCountry)) {
				whereBlockPost = StringUtil.linkString(whereBlockPost, " or ",
						"sysOrgPost.fdName" + currentLocaleCountry
								+ " like :fdName ");
			}
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称拼音查询
			whereBlockPost = StringUtil.linkString(whereBlockPost, " or ",
					"sysOrgPost.fdNamePinYin like :pinyin ");
			hqlInfo.setParameter("pinyin", "%" + fdPinyinName + "%");
			// 编号查询
			whereBlockPost = StringUtil.linkString(whereBlockPost, " or ",
					"sysOrgPost.fdNo like :fdName ");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
			// 名称简拼查询
			whereBlockPost = StringUtil.linkString(whereBlockPost, " or ",
					"sysOrgPost.fdNameSimplePinyin like :simplePinyin ");
			hqlInfo.setParameter("simplePinyin", "%" + fdPinyinName + "%");
		} else if (!"true".equals(all)) {
			if (parent != null) {
				if ("".equals(parent)) {
					// 查看所有生态组织时，特殊处理
					if (!UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
						AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
						// 如果有查看范围限制，就取查看范围的根组织
						if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootPersonIds())) {
							whereBlock += " and (sysOrgPost.hbmParent is null or sysOrgPost.fdId in (" + SysOrgUtil.buildInBlock(orgRange.getRootPersonIds()) + "))";
						} else {
							whereBlock += " and sysOrgPost.hbmParent is null ";
						}
					} else {
						whereBlock += " and sysOrgPost.hbmParent is null ";
					}
				} else {
					whereBlock += " and sysOrgPost.hbmParent.fdId=:hbmParenFdId ";
					hqlInfo.setParameter("hbmParenFdId", parent);
				}
			}
		}
		if (StringUtil.isNotNull(whereBlockPost)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "( "
					+ whereBlockPost + " )");
		}
		String fdFlagDeleted = request.getParameter("fdFlagDeleted");
		if (StringUtil.isNotNull(fdFlagDeleted)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPost.fdFlagDeleted = :fdFlagDeleted ");
			hqlInfo.setParameter("fdFlagDeleted", StringUtil
					.isNull(fdFlagDeleted) ? false : true);
		}
		String fdImportInfo = request.getParameter("fdImportInfo");
		if (StringUtil.isNotNull(fdImportInfo)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPost.fdImportInfo like :fdImportInfo ");
			hqlInfo.setParameter("fdImportInfo", fdImportInfo + "%");
		}
		// 筛选是否与业务相关
		String fdIsBusiness = request.getParameter("q.fdIsBusiness");
		if (StringUtil.isNotNull(fdIsBusiness)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysOrgPost.fdIsBusiness = :fdIsBusiness ");
			Boolean fdIsBusinessNext = false;
			if (Integer.parseInt(fdIsBusiness) == 1) {
                fdIsBusinessNext = true;
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
		if (StringUtil.isNull(curOrderBy)) {
            return "sysOrgPost.fdOrder,sysOrgPost.fdName";
        }
		return curOrderBy;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysOrgPostForm postForm = (SysOrgPostForm) form;
		postForm.reset(mapping, request);

		// 判断是否需要关联部门名称
		String isRelation = new SysOrganizationConfig().getIsRelation();
		request.setAttribute("isRelation", isRelation);

		String parent = request.getParameter("parent");
		if (!StringUtil.isNull(parent)) {
			SysOrgElement parentModel = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(parent);
			postForm.setFdParentId(parentModel.getFdId());
			postForm.setFdParentName(parentModel.getDeptLevelNames());

			if ("true".equals(isRelation)) {
				postForm.setFdName(parentModel.getFdName() + "_");
			}
		}

		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();

		Integer order = sysOrgDefaultConfig.getOrgPostDefaultOrder();
		if(order!=null) {
            postForm.setFdOrder(String.valueOf(order));
        }

		return postForm;
	}

	@Override
	protected ISysOrgPostService getServiceImp(HttpServletRequest request) {
		if (sysOrgPostService == null) {
            sysOrgPostService = (ISysOrgPostService) getBean("sysOrgPostService");
        }
		return sysOrgPostService;
	}

	private ISysOrgElementService getSysOrgElementService() {
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
				updatePersonsTime(new String[] { id });
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
				updatePersonsTime(ids);
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
		SysOrgElement model = (SysOrgElement) getServiceImp(request)
				.findByPrimaryKey(elementForm.getFdId());
		if (model != null && model.getFdParent() != null) {
            elementForm
                    .setFdParentName(model.getFdParent().getDeptLevelNames());
        }
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).addNewPost((IExtendForm) form,
					new RequestContext(request));
			//this.updatePersonLastModifyTime(request);
			this.updatePersonsTime(request);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else {
            return add(mapping, form, request, response);
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
			getServiceImp(request).addNewPost((IExtendForm) form,
					new RequestContext(request));
			//this.updatePersonLastModifyTime(request);
			this.updatePersonsTime(request);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
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
	 * 批量更新岗位部门（快捷调换部门）
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
		String postIds = request.getParameter("postIds");
		String deptId = request.getParameter("deptId");
		JSONObject obj = new JSONObject();

		if (StringUtil.isNotNull(postIds) && StringUtil.isNotNull(deptId)) {
			try {
				if (StringUtil.isNotNull(postIds)
						&& StringUtil.isNotNull(deptId)) {
					getServiceImp(request).updateDeptByPosts(
							postIds.split(";"), deptId,new RequestContext(request));
				}
				obj.put("status", true);
			} catch (Exception e) {
				obj.put("status", false);
				obj.put("message", e.getMessage());
			}
		} else {
			obj.put("status", false);
			if (StringUtil.isNull(postIds)) {
				obj
						.put(
								"message",
								ResourceUtil
										.getString("sys-organization:sysOrgPerson.quickChangeDept.fromPerson.null"));
			} else if (StringUtil.isNull(deptId)) {
				obj
						.put(
								"message",
								ResourceUtil
										.getString("sys-organization:sysOrgPerson.toDept.fromPerson.null"));
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(obj.toString().getBytes("UTF-8"));
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//this.updatePersonLastModifyTime(request);
		this.updatePersonsTime(request);
		return super.update(mapping, form, request, response);
	}

	protected void updatePersonLastModifyTime(HttpServletRequest request) {
		// 岗位人员批量更新，员工黄页也需要更新
		if(StringUtil.isNotNull(request.getParameter("fdPersonIds"))){
			String[] fdPersonIds = request.getParameter("fdPersonIds")
					.split(";");
			//更新的时候从岗位移出人员,需要更新移出人员的信息修改时间
			if(StringUtil.isNotNull(request.getParameter("fdOldPersonIds"))){
				String[] fdOldPersonIds = request.getParameter("fdOldPersonIds")
						.split(";");
				if(fdOldPersonIds.length>fdPersonIds.length){
					fdPersonIds= fdOldPersonIds;
				}
			}
			for (String fdPersonId : fdPersonIds) {
				SysOrgPerson sysOrgPerson = new SysOrgPerson();
				sysOrgPerson.setFdId(fdPersonId);
				getSysZonePersonInfoServiceImp(request)
						.updatePersonLastModifyTime(sysOrgPerson);
			}
		}
	}

	private void updatePersonsTime(String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		Date time = new Date();
		try {
			for (String id : ids) {
				SysOrgPost post = (SysOrgPost) getServiceImp(null)
						.findByPrimaryKey(id);
				List<SysOrgPerson> persons = post.getFdPersons();
				if (persons == null || persons.isEmpty()) {
					continue;
				}
				for (SysOrgPerson person : persons) {
					getServiceImp(null).updatePersonsByPost(person.getFdId(),
							time);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void updatePersonsTime(HttpServletRequest request) {
		String fdParentIdStr = request.getParameter("fdParentId");
		String fdOldParentIdStr = request.getParameter("fdOldParentId");
		String fdPersonIdsStr = request.getParameter("fdPersonIds");
		String fdOldPersonIdsStr = request.getParameter("fdOldPersonIds");
		String fdIsAvailable = request.getParameter("fdIsAvailable");
		if ("false".equals(fdIsAvailable)) {
			fdPersonIdsStr = "";
			fdParentIdStr = "";
		}
		Date time = new Date();
		if (fdParentIdStr != null && fdOldParentIdStr != null
				&& !fdParentIdStr.equals(fdOldParentIdStr)) {
			// 岗位所属部门变了，那么岗位新老成员的更新时间都要变更。
			String[] fdPersonIds = fdPersonIdsStr
					.split(";");
			String[] fdOldPersonIds = fdOldPersonIdsStr
					.split(";");
			Set<String> personids = new HashSet<String>();
			personids.addAll(Arrays.asList(fdPersonIds));
			personids.addAll(Arrays.asList(fdOldPersonIds));
			for (String id : personids) {
				try {
					getServiceImp(request).updatePersonsByPost(id, time);
				} catch (Exception e) {
					logger.error("更新岗位成员的更新时间失败: " + id);
				}
			}
		}
		// 岗位人员批量更新
		else {
			if(fdPersonIdsStr==null){
				fdPersonIdsStr = "";
			}
			String[] fdPersonIds = fdPersonIdsStr
					.split(";");
			if(StringUtil.isNotNull(fdOldPersonIdsStr)){
				String[] fdOldPersonIds = fdOldPersonIdsStr
						.split(";");
				//获取差集
				String[] personids = getTotal(fdPersonIds,fdOldPersonIds);

				for(String id : personids) {
					try {
						getServiceImp(request).updatePersonsByPost(id, time);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("更新岗位成员的更新时间失败: "+id);
					}
				}
			}else {
				for(String id : fdPersonIds) {
					try {
						getServiceImp(request).updatePersonsByPost(id, time);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("更新岗位成员的更新时间失败: "+id);
					}
				}
			}

		}
	}

	//取两个数组的差集
	private static String[] getTotal(String[] m, String[] n){
		// 将较长的数组转换为set
		Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));

		// 遍历较短的数组，实现最少循环
		for (String i : m.length > n.length ? n : m)
		{
			// 如果集合里有相同的就删掉，如果没有就将值添加到集合
			if (set.contains(i)){
				set.remove(i);
			} else{
				set.add(i);
			}
		}

		String[] arr = {};
		return set.toArray(arr);
	}

}
