package com.landray.kmss.hr.organization.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.forms.HrOrganizationPostForm;
import com.landray.kmss.hr.organization.model.HrOrganizationCompilingSum;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPersoninfoSetting;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationPostAction extends ExtendAction {
	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	public IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		if (this.hrStaffFileAuthorService == null) {
			this.hrStaffFileAuthorService = (IHrStaffFileAuthorService) this
					.getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrganizationPostAction.class);

    private IHrOrganizationPostService hrOrganizationPostService;

	@Override
	public IHrOrganizationPostService getServiceImp(HttpServletRequest request) {
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

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrOrganizationPost.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.organization.model.HrOrganizationPost.class);
        com.landray.kmss.hr.organization.util.HrOrganizationUtil.buildHqlInfoModel(hqlInfo, request);
		CriteriaValue cv = new CriteriaValue(request);

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}

		String fdParentId = cv.poll("fdParent");
		if (StringUtil.isNotNull(fdParentId)) {
			whereBlock.append(" and hrOrganizationPost.hbmParent.fdId = :fdParentId");
			hqlInfo.setParameter("fdParentId", fdParentId);
		}

		String fdIsCompileOpen = cv.poll("fdIsCompileOpen");
		if (StringUtil.isNotNull(fdIsCompileOpen)) {
			whereBlock.append(" and hrOrganizationPost.fdIsCompileOpen =:fdIsCompileOpen");
			hqlInfo.setParameter("fdIsCompileOpen", fdIsCompileOpen);
		}

		String para = request.getParameter("available");
		whereBlock.append(" and hrOrganizationPost.fdIsAvailable= :fdIsAvailable ");
		hqlInfo.setParameter("fdIsAvailable", StringUtil.isNull(para) ? true : false);
		Boolean isAdmin = UserUtil.getKMSSUser().isAdmin();
		// 拥有查看或者编辑岗位管理的权限就过滤
		if (UserUtil.checkRole("ROLE_HRORGANIZATION_POST_COMPILE_SCOPE")
				|| UserUtil.checkRole("ROLE_HRORGANIZATION_POST_COMPILE")
				|| isAdmin
				|| UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {
			// 数据权限过滤
			whereBlock = HrOrgAuthorityUtil.builtWhereBlock(whereBlock,
					"hrOrganizationPost", hqlInfo);
		} else {
			whereBlock.append(" and 1 = 2");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrOrganizationPostForm hrOrganizationPostForm = (HrOrganizationPostForm) super.createNewForm(mapping, form, request, response);
        ((IHrOrganizationPostService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		hrOrganizationPostForm.setFdIsKey("false");
		hrOrganizationPostForm.setFdIsSecret("false");
		hrOrganizationPostForm.setFdIsCompileOpen("false");
		hrOrganizationPostForm.setFdIsLimitNum("false");
		// 判断是否需要关联部门名称
		String ispostRelationdept = new HrOrganizationPersoninfoSetting()
				.getIspostRelationdept();
		request.setAttribute("ispostRelationdept", ispostRelationdept);
		// 判断是否需要关联部门名称
		String isUniqueGroupName = new HrOrganizationPersoninfoSetting()
				.getIsUniqueGroupName();
		request.setAttribute("isUniqueGroupName", isUniqueGroupName);

        return hrOrganizationPostForm;
    }

	public ActionForward updateOrgPostPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				IExtendForm rtnForm = null;
				IBaseModel model = getServiceImp(request).findByPrimaryKey(fdId, null, true);
				if (model != null) {
					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					HrOrganizationPostForm postForm = (HrOrganizationPostForm) rtnForm;
					postForm.setMethod_GET("edit");
				}
				if (rtnForm == null) {
					throw new NoRecordException();
				}
				request.setAttribute(getFormName(rtnForm, request), rtnForm);
				HrOrganizationPost post = (HrOrganizationPost) model;

				if (post.getFdRankMax() != null) {
					if (post.getFdRankMax().getFdGrade() != null) {
						request.setAttribute("grademaxName",
								post.getFdRankMax().getFdGrade().getFdName());
						request.setAttribute("grademaxId",
								post.getFdRankMax().getFdGrade().getFdId());
						request.setAttribute("grademaxWeight",
								post.getFdRankMax().getFdGrade().getFdWeight());
					}

				}
				if (post.getFdRankMix() != null) {
					if (post.getFdRankMix().getFdGrade() != null) {
						request.setAttribute("grademixName",
								post.getFdRankMix().getFdGrade().getFdName());
						request.setAttribute("grademixId",
								post.getFdRankMix().getFdGrade().getFdId());
						request.setAttribute("grademixWeight",
								post.getFdRankMix().getFdGrade().getFdWeight());
					}

				}
			} else {
				ActionForm newForm = createNewForm(mapping, form, request, response);
				HrOrganizationPostForm postForm = (HrOrganizationPostForm) newForm;
				postForm.setMethod_GET("add");
				if (newForm != form) {
					request.setAttribute(getFormName(newForm, request), newForm);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("updateOrgPostPage", mapping, form, request, response);
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
			changeFindPageHQLInfo(request, hqlInfo);
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

	public ActionForward deleteOrgPostPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			List<HrOrganizationPost> list = getServiceImp(request).findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("deleteOrgPostPage", mapping, form, request, response);
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
	 */
	public ActionForward updateCompilePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			IExtendForm rtnForm = null;
			if (!StringUtil.isNull(id)) {
				HrOrganizationPost model = (HrOrganizationPost) getServiceImp(request).findByPrimaryKey(id, null, true);
				if (model != null) {

					if (StringUtil.isNull(model.getFdIsCompileOpen())) {
						model.setFdIsCompileOpen("false");
					}
					if (StringUtil.isNull(model.getFdIsLimitNum())) {
						model.setFdIsLimitNum("false");
					}
					if (StringUtil.isNull(model.getFdIsFullCompile())) {
						model.setFdIsFullCompile("false");
					}

					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					Map<String, String> map = getHrStaffPersonInfoServiceImp().getPersonNum(id);
					HrOrganizationPost post = (HrOrganizationPost) getServiceImp(request).findByPrimaryKey(id);
					HrOrganizationElement fdParent = post.getFdParent();
					if(null != fdParent) {
						id=fdParent.getFdId();
					}
					List persons = post.getFdPersons();
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
							}else {
								SysOrgElement sysOrgElement = hrStaffPersonInfo.getFdOrgParent();
								if(null != sysOrgElement && sysOrgElement.getFdId().equals(id)) {
									personCount++;
								}
							}
						}
					}
					map.put("onpost", personCount+"");
					request.setAttribute("map", map);
				}
			}
			if (rtnForm == null) {
				throw new NoRecordException();
			}
			request.setAttribute(getFormName(rtnForm, request), rtnForm);

		} catch (Exception e) {
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

	/**
	 * <p>下载导入模板</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		HSSFWorkbook workbook=null;
		try {
			// WorkBook wb =
			// getServiceImp(request).buildTemplateWorkbook(request);
			// ExcelOutput output = new ExcelOutputImp();
			// output.output(wb, response);
			// 模板名称
			String templetName = ResourceUtil
					.getString("hr-organization:hrOrganizationPost.import.templateFile.templetName");
			// 构建模板文件
			workbook = getServiceImp(request)
					.buildTemplateWorkbook(request);

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
		}finally {
			IOUtils.closeQuietly(workbook);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}

	public ActionForward importOrgPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			importResult.put("otherErrors", new JSONArray());
			HrOrganizationPostForm mainForm = (HrOrganizationPostForm) form;
			FormFile file = mainForm.getFile();
			importResult = getServiceImp(request).addImportData(file.getInputStream(), request.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			importResult.put("hasError", 1);
			importResult.put("importMsg", ResourceUtil.getString("hr-organization:hr.organization.import.fail"));
			importResult.getJSONArray("otherErrors").add(e.getMessage());
		}
		String result = HrOrgUtil.replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	/**
	 * <p>设置岗位上级部门</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward setPostParent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String postString = request.getParameter("postJson");
			String deletePostStr = request.getParameter("deletePostJson");
			String[] deletePostArr = deletePostStr.split(";");
			JSONArray postJson = JSONArray.fromObject(postString);
			for (int j = 0; j < deletePostArr.length; j++) {
				if (StringUtil.isNotNull(deletePostArr[j])) {
					updatePostParent(request, form, deletePostArr[j], "");
				}
			}
			for (int i = 0; i < postJson.size(); i++) {
				JSONObject obj = postJson.getJSONObject(i);
				updatePostParent(request, form, obj.get("postId").toString(),
						obj.get("parentId").toString());
			}
			json.put("result", true);
		} catch (Exception e) {
			messages.addError(e);
			json.put("result", false);
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

	private void updatePostParent(HttpServletRequest request, ActionForm form,
			String postId, String parentId) throws Exception {
		IBaseModel model = getServiceImp(request)
				.findByPrimaryKey(postId, null,
						true);
		HrOrganizationElementForm rtnForm = null;
		if (model != null) {
			rtnForm = (HrOrganizationElementForm) getServiceImp(request)
					.convertModelToForm((IExtendForm) form,
							model, new RequestContext(request));
			rtnForm.setFdParentId(parentId);
			getServiceImp(request).update(rtnForm, new RequestContext(request));
		}
	}

	/**
	 * <p>删除岗位部门</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delPostParent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String postId = request.getParameter("postId");
			HrOrganizationElementForm rtnForm = null;
			IBaseModel model = getServiceImp(request).findByPrimaryKey(postId, null, true);
			if (model != null) {
				rtnForm = (HrOrganizationElementForm) getServiceImp(request).convertModelToForm((IExtendForm) form,
						model, new RequestContext(request));
				rtnForm.setFdParentId("");
				getServiceImp(request).update(rtnForm, new RequestContext(request));
			}
			json.put("result", true);
		} catch (Exception e) {
			messages.addError(e);
			json.put("result", false);
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

	/**
	 * <p>禁用岗位</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward changeDisabled(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			String fdId = request.getParameter("fdId");
			boolean flag = getServiceImp(request).updateInvalidated(fdId, new RequestContext(request));
			json.put("flag", flag);
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
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

	public void checkFdNoUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		boolean result = true;
		try {
			String fdNo = request.getParameter("fdNo");
			String fdId = request.getParameter("fdId");
			getServiceImp(request).checkFdNo(fdId, HrOrgConstant.HR_TYPE_POST, fdNo);
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

	public void checkNameUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		boolean result = true;
		try {
			String fdName = request.getParameter("fdName");
			String fdId = request.getParameter("fdId");
			getServiceImp(request).checkFdName(fdId, HrOrgConstant.HR_TYPE_POST, fdName);
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
	 * <p>获取岗位编制</p>
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
			HrOrganizationPost post = (HrOrganizationPost) getServiceImp(request).findByPrimaryKey(fdOrgPostsIds);
			json.put("fdIsLimitNum", post.getFdIsLimitNum());
			if (StringUtil.isNull(post.getFdIsCompileOpen()) || "false".equals(post.getFdIsCompileOpen())) {
				json.put("fdCompileNum", "未设置");
			} else {
				if (StringUtil.isNull(post.getFdIsLimitNum()) || "false".equals(post.getFdIsLimitNum())) {
					json.put("fdCompileNum", "不限人数");
					json.put("postsNum", post.getFdBePersons().size());
				} else {
					json.put("fdCompileNum", post.getFdCompileNum());
					json.put("postsNum", post.getFdBePersons().size());
				}
			}
			if (post.getFdParent() != null) {
				json.put("parentId", post.getFdParent().getFdId());
				json.put("parentName", post.getFdParent().getFdName());
			}

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
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
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
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List list = page.getList();
			List personList = null;
			List persons = null;
			List postList =new LinkedList();
			String id = null;
			// 获取isNew参数，isNew表示走新的逻辑
			String isNew = request.getParameter("isNew");
			// isNew=true，走新的逻辑，否则走原来的逻辑
			if ("true".equals(isNew)) {
				Map<String, Long> onAllPostCountMap = new HashMap<String, Long>();
				Map<String, Long> onCompilingMap = new HashMap<String, Long>();
				// 针对岗位进行处理
				for (Object obj : list) {
					if (null == obj) {
						continue;
					}
					HrOrganizationPost post = (HrOrganizationPost) obj;
					List personInfos = post.getFdPersons();
					if (personInfos == null || personInfos.isEmpty()) {
						onAllPostCountMap.put(post.getFdId(), Long.valueOf(0));
						onCompilingMap.put(post.getFdId(), Long.valueOf(0));
					} else {
						List<String> personInfoIds = this
								.getPersonInfoIds(personInfos);
						// 统计在职人数
						Long onAllPostCount = this.getOnAllPost(personInfoIds);
						onAllPostCountMap.put(post.getFdId(), onAllPostCount);
						// 统计在编人数
						Long onCompilingCount = this
								.getonCompiling(personInfoIds);
						onCompilingMap.put(post.getFdId(), onCompilingCount);
					}


				}
				request.setAttribute("onAllPosts", onAllPostCountMap);
				request.setAttribute("onCompiling", onCompilingMap);
			} else {
				for (Object object1 : list) {
					if (null == object1) {
						continue;
					}
					HrOrganizationPost post = (HrOrganizationPost) object1;
					HrOrganizationElement fdParent = post.getFdParent();
					if (null != fdParent) {
						id = fdParent.getFdId();
					}
					personList = new ArrayList();
					persons = post.getFdPersons();
					int personCount = 0;
					for (Object object : persons) {
						if (null == object) {
							continue;
						}
						HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) object;
						if (!"dismissal".equals(hrStaffPersonInfo.getFdStatus())
								&& !"leave"
										.equals(hrStaffPersonInfo.getFdStatus())
								&& !"retire".equals(
										hrStaffPersonInfo.getFdStatus())) {
							HrOrganizationElement element = hrStaffPersonInfo
									.getFdParent();
							if (null != element
									&& element.getFdId().equals(id)) {
								personCount++;
								personList.add(hrStaffPersonInfo);
							} else {
								SysOrgElement sysOrgElement = hrStaffPersonInfo
										.getFdOrgParent();
								if (null != sysOrgElement
										&& sysOrgElement.getFdId().equals(id)) {
									personCount++;
									personList.add(hrStaffPersonInfo);
								}
							}
						}
					}
					post.setFdPersons(personList);
					postList.add(post);
				}
				page.setList(postList);
			}
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}


	/**
	 * 获取对应的人员列表id集
	 * 
	 * @param personInfos
	 * @return
	 */
	private List<String> getPersonInfoIds(List personInfos) {
		List<String> personInfoIds = new ArrayList<String>();
		for (Object object : personInfos) {
			if (null == object) {
				continue;
			}
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) object;
			personInfoIds.add(hrStaffPersonInfo.getFdId());
		}
		return personInfoIds;
	}

	/**
	 * 统计在职人数
	 * 
	 * @param personInfoIds
	 * @return
	 * @throws Exception
	 */
	private Long getOnAllPost(List<String> personInfoIds) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(
				"com.landray.kmss.hr.staff.model.HrStaffPersonInfo");
		hqlInfo.setGettingCount(true);
		StringBuffer whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
		whereBlock.append(" and " + HQLUtil
				.buildLogicIN("hrStaffPersonInfo.fdId", personInfoIds));
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<Long> onAllPost = this.getHrStaffPersonInfoServiceImp()
				.findValue(hqlInfo);
		return onAllPost.get(0);
	}

	/**
	 * 统计在编人数
	 * 
	 * @param personInfoIds
	 * @return
	 * @throws Exception
	 */
	private Long getonCompiling(List<String> personInfoIds) throws Exception {
		// 统计部门编制人数
		HrOrganizationCompilingSum hrOrganizationCompilingSum = new HrOrganizationCompilingSum();
		List list = new ArrayList();
		if ("true"
				.equals(hrOrganizationCompilingSum.getCompilationOfficial())) {
			list.add("official");
		}
		if ("true".equals(hrOrganizationCompilingSum.getCompilationTrial())) {
			list.add("trial");
		}
		if ("true".equals(
				hrOrganizationCompilingSum.getCompilationTrialDelay())) {
			list.add("trialDelay");
		}
		if ("true"
				.equals(hrOrganizationCompilingSum.getCompilationPractice())) {
			list.add("practice");
		}
		if ("true"
				.equals(hrOrganizationCompilingSum.getCompilationTemporary())) {
			list.add("temporary");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(
				"com.landray.kmss.hr.staff.model.HrStaffPersonInfo");
		hqlInfo.setGettingCount(true);
		StringBuffer whereBlock = new StringBuffer(
				HQLUtil.buildLogicIN("hrStaffPersonInfo.fdStatus",
						list));
		whereBlock.append(" and " + HQLUtil
				.buildLogicIN("hrStaffPersonInfo.fdId", personInfoIds));
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<Long> onCompiling = this.getHrStaffPersonInfoServiceImp()
				.findValue(hqlInfo);

		return onCompiling.get(0);
	}

}
