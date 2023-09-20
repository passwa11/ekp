package com.landray.kmss.hr.organization.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.HQLUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationLogService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.hr.organization.validator.HrOrgCompileValidator;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationElementAction extends ExtendAction {
	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	public IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		if (this.hrStaffFileAuthorService == null) {
			this.hrStaffFileAuthorService = (IHrStaffFileAuthorService) this
					.getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}

	private static IHrOrgFileAuthorService hrOrgFileAuthorService;

	protected static IHrOrgFileAuthorService getHrOrgFileAuthorServiceImp() {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) SpringBeanUtil
					.getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrganizationElementAction.class);

    private IHrOrganizationElementService hrOrganizationElementService;

	@Override
	public IHrOrganizationElementService getServiceImp(HttpServletRequest request) {
        if (hrOrganizationElementService == null) {
            hrOrganizationElementService = (IHrOrganizationElementService) getBean("hrOrganizationElementService");
        }
        return hrOrganizationElementService;
    }

	private IHrOrganizationPostService hrOrganizationPostService;

	public IHrOrganizationPostService getHrOrganizationPostServiceImp() {
		if (hrOrganizationPostService == null) {
			hrOrganizationPostService = (IHrOrganizationPostService) getBean("hrOrganizationPostService");
		}
		return hrOrganizationPostService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public IHrStaffPersonInfoService getHrStaffPersonInfoServiceImp() {
		if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
        }
		return hrStaffPersonInfoService;
	}

	private IHrOrganizationLogService hrOrganizationLogService;

	public IHrOrganizationLogService getHrOrganizationLogServiceImp() {
		if (hrOrganizationLogService == null) {
			hrOrganizationLogService = (IHrOrganizationLogService) getBean("hrOrganizationLogService");
		}
		return hrOrganizationLogService;
	}

	protected ISysQuartzJobService sysQuartzJobService;

	public ISysQuartzJobService getSysQuartzJobService() {
		if (sysQuartzJobService == null) {
            sysQuartzJobService = (ISysQuartzJobService) getBean("sysQuartzJobService");
        }
		return sysQuartzJobService;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		//super.changeFindPageHQLInfo(request, hqlInfo);
		HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationElement.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request,
				com.landray.kmss.hr.organization.model.HrOrganizationElement.class);
		HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);

		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String para = request.getParameter("available");
		String fdParentId = request.getParameter("fdParentId");
		String orgType = request.getParameter("suborType");
		String queryPerson = request.getParameter("queryPerson");
		if (StringUtil.isNotNull(fdParentId)) {
			whereBlock += " and hrOrganizationElement.hbmParent.fdId=:fdParentId ";
			hqlInfo.setParameter("fdParentId", fdParentId);
		} else {
			whereBlock = "1=2 ";
		}
		if (StringUtil.isNotNull(orgType)) {
			Integer fdOrgType = Integer.parseInt(orgType);
			if (fdOrgType == 12) {
				whereBlock += " and (hrOrganizationElement.fdOrgType=:fdOrgDept or hrOrganizationElement.fdOrgType=:fdOrg)";
				hqlInfo.setParameter("fdOrgDept",
						HrOrgConstant.HR_TYPE_DEPT);
				hqlInfo.setParameter("fdOrg",
						HrOrgConstant.HR_TYPE_ORG);
			} else {
				whereBlock += " and hrOrganizationElement.fdOrgType=:fdOrgType";
				hqlInfo.setParameter("fdOrgType", fdOrgType);
			}
		}
		// 查询所有下级员工
		if (StringUtil.isNotNull(queryPerson)) {
			whereBlock += " and hrOrganizationElement.hbmParent.fdId=:fdParentId and hrOrganizationElement.fdOrgType=:orgPerson";
			hqlInfo.setParameter("fdParentId", queryPerson);
			hqlInfo.setParameter("orgPerson", HrOrgConstant.HR_TYPE_PERSON);
		}
		whereBlock += " and hrOrganizationElement.fdIsAvailable=:fdIsAvailable ";
		hqlInfo.setParameter("fdIsAvailable",
				StringUtil.isNull(para) ? true : false);
		hqlInfo.setWhereBlock(whereBlock);
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationElementForm hrOrganizationElementForm = (HrOrganizationElementForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationElementService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		hrOrganizationElementForm.setFdCreateTime(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
        return hrOrganizationElementForm;
    }

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	super.loadActionForm(mapping, form, request, response);
    	String id = request.getParameter("fdId");
    	//查询组织操作记录
		List list = getHrOrganizationLogServiceImp().findLogByOrgId(id);
		request.setAttribute("logs", list);
    }

	/**
	 * <p>手动同步EKP组织架构到人事组织架构（只新增不更新）</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward syncEKP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-syncEKP", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = null;
		try {
			JSONObject json = new JSONObject();
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			//查询定时任务
			HQLInfo hql = new HQLInfo();
			hql.setSelectBlock("fdId");
			hql.setWhereBlock("fdJobService=:fdJobService and fdJobMethod=:fdJobMethod");
			hql.setParameter("fdJobService", "synchroAllEkpToHr");
			hql.setParameter("fdJobMethod", "synchroEkpToHr");
			List<?> list = getSysQuartzJobService().findValue(hql);
			if (!ArrayUtil.isEmpty(list) && list.get(0) != null) {
				fdId = list.get(0).toString();
			}
			json.put("fdId", fdId);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	public ActionForward disableData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String available = request.getParameter("available");
			StringBuffer sbf = new StringBuffer("1=1");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			sbf.append(" and hrOrganizationElement.fdOrgType in ('1','2')");
			if (StringUtil.isNotNull(available)) {
				sbf.append(" and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(available));
			}
//			// 获取当前登录人有权限的部门id
//			String fdId = UserUtil.getUser().getFdId();
//			String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_org_file_author_detail d left join hr_org_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:fdId";
//			List<Object[]> authorDetails = this.getHrOrgFileAuthorServiceImp()
//					.getBaseDao()
//					.getHibernateSession().createSQLQuery(sql)
//					.setString("fdId",
//							UserUtil.getKMSSUser().getPerson().getFdId())
//					.list();
//			// 管辖范围内权限不为空
//			if (!authorDetails.isEmpty()
//					&& !UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {
//				sbf.append(
//						" and " + HQLUtil.buildLogicIN(
//								"hrOrganizationElement.hbmParent.fdId",
//								authorDetails));
//			}
			//过滤生态组织
			HQLInfo hql = new HQLInfo();
			hql.setSelectBlock("hrOrganizationElement.fdId");
			hql.setJoinBlock(", com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement");
			hql.setWhereBlock("hrOrganizationElement.fdOrgType in ('1','2') and sysOrgElement.fdId = hrOrganizationElement.fdId and sysOrgElement.fdIsExternal = :fdIsExternal");
			if (StringUtil.isNotNull(available)) {
				hql.setWhereBlock(hql.getWhereBlock() + " and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
				hql.setParameter("fdIsAvailable", Boolean.valueOf(available));
			}
			hql.setParameter("fdIsExternal", Boolean.TRUE);
			List<String> externalList = hrOrganizationElementService.findList(hql);
			if(externalList.size() > 0){
				sbf.append(" and " + HQLUtil.buildLogicIN("hrOrganizationElement.fdId not", externalList));
			}
			//权限过滤
			if (UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE")
					|| UserUtil
							.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE_SCOPE")
					|| UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {
				sbf.append(" and 1=1");
			} else {
				sbf.append(" and 1=2");
			}
			hqlInfo.setWhereBlock(sbf.toString());
			HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationElement.class);
			hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
			HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request,
					com.landray.kmss.hr.organization.model.HrOrganizationElement.class);
			HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);

			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("disableData", mapping, form, request, response);
		}
	}

	/**
	 * <p>启用组织</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward changeEnabled(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdId = request.getParameter("fdId");
			boolean flag = getServiceImp(request).updateValid(fdId, new RequestContext(request));
			json.put("flag", flag);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * <p>设置机构、部门编制</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward updateCompilePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			IExtendForm rtnForm = null;
			if (!StringUtil.isNull(id)) {
				HrOrganizationElement model = (HrOrganizationElement) getServiceImp(request).findByPrimaryKey(id, null, true);
				if (model != null) {
					if (StringUtil.isNull(model.getFdIsCompileOpen())) {
						model.setFdIsCompileOpen("false");
					}
					if (StringUtil.isNull(model.getFdIsLimitNum())) {
						model.setFdIsLimitNum("false");
					}
					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					Map<String, Integer> map = new HashMap<String, Integer>();
					// getHrStaffPersonInfoServiceImp().getPersonNum(id);

					JSONObject obj = getHrStaffPersonInfoServiceImp()
							.getPersonStat(id);
					map.put("onpost", (Integer) obj.get("onAllPost"));
					map.put("waitentry", (Integer) obj.get("entry"));
					map.put("waitleave", (Integer) obj.get("leave"));
					int totalNum = (Integer) obj.get("onAllPost")
							+ (Integer) obj.get("leave")
							+ (Integer) obj.get("entry");
					map.put("totalNum", totalNum);
					request.setAttribute("map", map);
					
					//获取机构、部门内岗位列表
					List<HrOrganizationPost> list = getHrOrganizationPostServiceImp().getPostsByOrgId(id);
					JSONArray postsJson = new JSONArray();
					int postsCompileNum = 0;
					List personList = null;
					List persons = null;
					for (int i = 0; i < list.size(); i++) {
						HrOrganizationPost post = list.get(i);
						JSONObject postsObj = new JSONObject();
						
						postsObj.accumulate("postId", post.getFdId());
						postsObj.accumulate("postName", post.getFdName());
						
						personList = new ArrayList();
						persons = post.getFdPersons();
						int personCount = 0;
						for (Object object : persons) {
							if(null == object) {
								continue;
							}
							HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) object;
							if(!"dismissal".equals(hrStaffPersonInfo.getFdStatus()) && !"leave".equals(hrStaffPersonInfo.getFdStatus())
									&& !"retire".equals(hrStaffPersonInfo.getFdStatus())) {
								HrOrganizationElement element = hrStaffPersonInfo.getFdParent();
								if(null != element && element.getFdId().equals(id)) {
									personCount++;
									personList.add(hrStaffPersonInfo);
								}else {
									SysOrgElement sysOrgElement = hrStaffPersonInfo.getFdOrgParent();
									if(null != sysOrgElement && sysOrgElement.getFdId().equals(id)) {
										personCount++;
										personList.add(hrStaffPersonInfo);
									}
								}
							}
						}
						list.get(i).setFdPersons(personList);
						postsObj.accumulate("postsNum", personCount);

						postsObj.accumulate("fdIsLimitNum", post.getFdIsLimitNum());
						postsObj.accumulate("fdCompileNum", post.getFdCompileNum());
						if (("true").equals(post.getFdIsLimitNum())) {
							postsCompileNum += post.getFdCompileNum();
						}
						postsObj.accumulate("parentId", id);

						postsJson.add(postsObj);
					}
					request.setAttribute("postsJson", postsJson.toString());
					request.setAttribute("postsCompileNum", postsCompileNum);
					request.setAttribute("posts", list);

				}
			}
			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);

		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("updateCompilePage", mapping, form, request, response);
		}
	}

	public ActionForward importOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			importResult.put("otherErrors", new JSONArray());
			HrOrganizationElementForm mainForm = (HrOrganizationElementForm) form;
			FormFile file = mainForm.getFile();
			importResult = getServiceImp(request)
					.saveElementImportData(file.getInputStream(),
							request.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			importResult.put("hasError", 1);
			importResult.put("importMsg", ResourceUtil
					.getString("hr-organization:hr.organization.import.fail"));
			importResult.getJSONArray("otherErrors").add(e.getMessage());
		}
		String result = HrOrgUtil.replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
				.write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	/**
	 * <p>合并组织</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward mergeOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {

			String currOrgId = request.getParameter("currOrgId");
			String newOrgId = request.getParameter("newOrgId");
			if (checkOrgLast(request, currOrgId)) {
				getServiceImp(request).updateMergeOrg(currOrgId, newOrgId);
				json.put("flag", true);
			} else {
				json.put("flag", false);
				json.put("msg", ResourceUtil.getString("hr-organization:hrOrganizationElement.mergeOrg.tips"));
			}
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
			return null;
		}
	}

	private boolean checkOrgLast(HttpServletRequest request, String orgId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId != :fdId and fdHierarchyId like :fdHierarchyId and fdIsAvailable =:fdIsAvailable and fdOrgType in ('1', '2')");
		hqlInfo.setParameter("fdId", orgId);
		hqlInfo.setParameter("fdHierarchyId", "%" + orgId + "%");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<HrOrganizationElement> list = getServiceImp(request).findList(hqlInfo);
		if (ArrayUtil.isEmpty(list)) {
			return true;
		} else {
			return false;
		}
	}

	private List getPostPersonNum(String postId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setFromBlock(
				"com.landray.kmss.hr.staff.model.HrStaffPersonInfo hrStaffPersonInfo");
		StringBuffer whereStr = new StringBuffer();
		List<String> status = new ArrayList();
	
		String[] fdStatus = { "official", "trial", "practice", "trialDelay" };
		whereStr.append("hrStaffPersonInfo.fdOrgPosts.fdId=:postId");
		whereStr.append(" and hrStaffPersonInfo.fdStatus in (:fdStatus)");
		hqlInfo.setParameter("postId", postId);
		hqlInfo.setParameter("fdStatus",
				ArrayUtil.convertArrayToList(fdStatus));
		hqlInfo.setWhereBlock(whereStr.toString());
		return getHrStaffPersonInfoServiceImp().findList(hqlInfo);
	}
	/**
	 * <p>获取组织编制</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward getCompileNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdOrgPostsIds = request.getParameter("fdOrgPostsIds");
			HrOrganizationElement element = (HrOrganizationElement) getServiceImp(request)
					.findByPrimaryKey(fdOrgPostsIds);
			json.put("fdIsLimitNum", element.getFdIsLimitNum());
			if (StringUtil.isNull(element.getFdIsCompileOpen()) || "false".equals(element.getFdIsCompileOpen())) {
				json.put("fdCompileNum", 0);
			} else {
				if (StringUtil.isNull(element.getFdIsLimitNum()) || "false".equals(element.getFdIsLimitNum())) {
					json.put("fdCompileNum", "不限人数");
				} else {
					json.put("fdCompileNum", element.getFdCompileNum());
					json.put("postsNum",
							getPostPersonNum(fdOrgPostsIds).size());
				}
			}
			JSONObject staffStat = getHrStaffPersonInfoServiceImp().getPersonStat(element.getFdId());
			json.put("personNum", staffStat.get("onAllPost"));
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error("", e);
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	/**
	 * <p>设置编制</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward setCompileNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String fdCompileNum = request.getParameter("fdCompileNum");
			HrOrganizationElement element = (HrOrganizationElement) getServiceImp(request).findByPrimaryKey(fdId);
			if (StringUtil.isNotNull(fdCompileNum)) {
				element.setFdIsCompileOpen("true");
				element.setFdIsLimitNum("true");
				element.setFdCompileNum(Integer.valueOf(fdCompileNum));
				getServiceImp(request).update(element);
				json.put("result", true);
			} else {
				json.put("result", false);
				json.put("msg", "编制人数不能为空");
			}

		} catch (Exception e) {
			logger.error("", e);
			json.put("result", false);
			messages.addError(e);
		}

		request.setAttribute("lui-source", json);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	public ActionForward downloadTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		HSSFWorkbook workbook =null;
		OutputStream out =null;
		try {
			// WorkBook wb =
			// getServiceImp(request).buildTemplateWorkbook(request);
			// ExcelOutput output = new ExcelOutputImp();
			// output.output(wb, response);
			// 模板名称
			String templetName = ResourceUtil
					.getString(
							"hr-organization:hrOrganizationElement.import.templateFile");
			// 构建模板文件
			workbook = getServiceImp(request)
					.buildTemplateWorkbook(request);

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			out = response.getOutputStream();
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
		}finally {
			IOUtils.closeQuietly(workbook);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		}
		return null;
	}

	public JSONArray searchElement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fdKey = request.getParameter("_fdKey");
		HQLInfo _hqlInfo = new HQLInfo();
		StringBuffer sbf = new StringBuffer(
				"hrOrganizationElement.fdName like :fdKey and hrOrganizationElement.fdOrgType in (1,2) and hrOrganizationElement.fdIsAvailable=:fdIsAvailable");
		sbf = HrOrgAuthorityUtil.builtWhereBlock(sbf, "hrOrganizationElement", _hqlInfo);
		//过滤生态组织
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("hrOrganizationElement.fdId");
		hql.setJoinBlock(", com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement");
		hql.setWhereBlock("hrOrganizationElement.fdName like :fdKey and hrOrganizationElement.fdIsAvailable=:fdIsAvailable and hrOrganizationElement.fdOrgType in (1,2)"
				+ " and sysOrgElement.fdId = hrOrganizationElement.fdId and sysOrgElement.fdIsExternal = :fdIsExternal");
		hql.setParameter("fdKey", "%" + fdKey + "%");
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		hql.setParameter("fdIsExternal", Boolean.TRUE);
		List<String> externalList = hrOrganizationElementService.findList(hql);
		if(externalList.size() > 0){
			sbf.append(" and " + HQLUtil.buildLogicIN("hrOrganizationElement.fdId not", externalList));
		}
		_hqlInfo.setWhereBlock(sbf.toString());
		_hqlInfo.setParameter("fdKey", "%" + fdKey + "%");
		_hqlInfo.setParameter("fdIsAvailable", true);
		List<HrOrganizationElement> info;
		JSONArray jsonArr = new JSONArray();
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		boolean loadPersonNumber =false;
		if (StringUtil.isNotNull(pageNo)) {
			_hqlInfo.setPageNo(Integer.parseInt(pageNo));
			_hqlInfo.setRowSize(Integer.parseInt(pageSize));
			Page page = hrOrganizationElementService.findPage(_hqlInfo);
			int maxPage = page.getTotalrows() / _hqlInfo.getRowSize() + (page.getTotal() % _hqlInfo.getRowSize() == 0 ? 0 : 1);
			if (maxPage < _hqlInfo.getPageNo()) {
				info = new ArrayList();
			} else {
				info = page.getList();
			}
			loadPersonNumber=true;
		} else {
			info = getServiceImp(request)
					.findValue(_hqlInfo);
		}
		for (int i = 0; i < info.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.accumulate("fdId", info.get(i).getFdId());
			obj.accumulate("value", info.get(i).getFdId());
			obj.accumulate("text", info.get(i).getFdName());
			obj.accumulate("title", info.get(i).getFdName());
			if(loadPersonNumber) {
				//如果是分页查询，则需要统计在职在变人数等
				obj.accumulate("onAllPost", getHrStaffPersonInfoServiceImp().getPersonStat(info.get(i).getFdId()).get("onAllPost"));
				// 编制人数
				Object json= getHrStaffPersonInfoServiceImp().getPersonStat(info.get(i).getFdId()).get("onCompiling");
				obj.accumulate("onCompiling", json);
				obj.put("onCompiling", json);
				//是否有设置编制权限
				obj.put("fdCompileAuth", HrOrgCompileValidator.validateCompileRole(info.get(i).getFdId()));
			}
			obj.accumulate("nodeType", info.get(i).getFdOrgType());
			obj.accumulate("fdCompileNum", info.get(i).getFdCompileNum());
			obj.accumulate("fdIsVirOrg",StringUtil.isNull(info.get(i).getFdIsVirOrg()) ? false : info.get(i).getFdIsVirOrg());
			obj.put("fdCompileNum", info.get(i).getFdCompileNum());
			obj.put("fdIsCompileOpen", info.get(i).getFdIsCompileOpen());
			obj.put("fdIsLimitNum", info.get(i).getFdIsLimitNum());			
			jsonArr.add(obj);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArr.toString());
		return null;
	}

	public ActionForward elementList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
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
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List pageList = new ArrayList();
			for (int i = 0; i < page.getList().size(); i++) {
				HrOrganizationElement elem = (HrOrganizationElement) page
						.getList().get(i);
				JSONObject stat = getHrStaffPersonInfoServiceImp()
						.getPersonStat(elem.getFdId());
				elem.setFdPersonsNumber((Integer) stat.get("onAllPost")); // 设置在职人数
				elem.setFdCompilationNum((Integer) stat.get("onCompiling")); // 设置在编人数
				pageList.add(elem);
			}
			page.setList(pageList);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	public void checkFdNoUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		boolean result = true;
		try {
			String fdNo = request.getParameter("fdNo");
			String fdId = request.getParameter("fdId");
			String fdOrgType = request.getParameter("fdOrgType");
			getServiceImp(request).checkFdNo(fdId, Integer.valueOf(fdOrgType), fdNo);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	// 校验组织名唯一
	public void checkFdNameUnique(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		boolean result = true;
		try {
			String fdName = request.getParameter("fdName");
			String fdId = request.getParameter("fdId");
			String fdOrgType = request.getParameter("fdOrgType");
			getServiceImp(request).checkFdName(fdId, Integer.valueOf(fdOrgType),
					fdName);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * <p>检查是否有设置编制权限</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author sunj
	 */
	public void checkCompileAuth(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			JSONObject json = new JSONObject();
			StringBuffer whereBlock = new StringBuffer();
			HQLInfo hqlInfo = new HQLInfo();
			boolean result = true;
			List<String> orgIds = HrOrgAuthorityUtil.getOrgIds();
			if (ArrayUtil.isEmpty(orgIds)) {
				result = false;
			}
			for (int i = 0; i < orgIds.size(); i++) {
				String orgId = orgIds.get(i);
				if ((orgIds.size() - 1) == i) {
					whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ")");
				} else {
					whereBlock.append("(hrOrganizationElement.fdHierarchyId like :orgid_" + i + ") or ");
				}
				hqlInfo.setParameter("orgid_" + i, "%" + orgId + "%");
				hqlInfo.setSelectBlock("fdId");
			}
			List list = getServiceImp(request).findList(hqlInfo);
			result = list.contains(fdId);
			json.put("result", result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
	}

	/**
	 * <p>效验是否有当前部门设置编制权限</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void validateCompileRole(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			boolean result = true;
			String fdId = request.getParameter("fdId");

			result = HrOrgCompileValidator.validateCompileRole(fdId);
			json.put("result", result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
	}
}
