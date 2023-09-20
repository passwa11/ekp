package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.actions.SimpleCategoryNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.forms.KmImeetingTopicForm;
import com.landray.kmss.km.imeeting.model.KmImeetingTopic;
import com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicCategoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicService;
import com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class KmImeetingTopicAction extends SimpleCategoryNodeAction {
	
	private ICoreOuterService dispatchCoreService;
	
	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	protected IKmImeetingTopicCategoryService kmImeetingTopicCategoryService;

	protected IKmImeetingTopicCategoryService getKmImeetingTopicCategoryService() {
		if (kmImeetingTopicCategoryService == null) {
            kmImeetingTopicCategoryService = (IKmImeetingTopicCategoryService) getBean(
                    "kmImeetingTopicCategoryService");
        }
		return kmImeetingTopicCategoryService;
	}
	protected IKmImeetingTopicService kmImeetingTopicService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingTopicService == null) {
            kmImeetingTopicService = (IKmImeetingTopicService) getBean("kmImeetingTopicService");
        }
		return kmImeetingTopicService;
	}

	@Override
	protected String getParentProperty() {
		return "fdTopicCategory";
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		KmImeetingTopicForm kmImeetingTopicForm = (KmImeetingTopicForm) form;
		kmImeetingTopicForm.reset(mapping, request);

		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		String templateId = request.getParameter("fdTemplateId");
		if (StringUtil.isNotNull(templateId)) {
			KmImeetingTopicCategory kmImeetingTopicCategory = (KmImeetingTopicCategory) getKmImeetingTopicCategoryService()
					.findByPrimaryKey(templateId);
			if (kmImeetingTopicCategory != null) {
				kmImeetingTopicForm.setFdTopicCategoryId(templateId);
				kmImeetingTopicForm
						.setFdTopicCategoryName(SimpleCategoryUtil.getCategoryPathName(kmImeetingTopicCategory));
				KMSSUser user = UserUtil.getKMSSUser();
				kmImeetingTopicForm.setDocCreatorId(user.getUserId());
				kmImeetingTopicForm.setDocCreatorName(user.getUserName());
			}
			getDispatchCoreService().initFormSetting(kmImeetingTopicForm, "mainTopic", kmImeetingTopicCategory,
					"mainTopic",
					new RequestContext(request));
		}
		return kmImeetingTopicForm;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		String except = cv.poll("except");
		String[] exceptValue = null;
		if (StringUtil.isNotNull(except) && except.indexOf(":") > -1) {
			exceptValue = except.split(":");
		}

		if ("0".equals(request.getParameter("isDialog"))) {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingTopic.docStatus = '30'");
			if ("false".equals(KmImeetingConfigUtil.isTopicAcceptRepeat())) {
				String fdOsAccept = cv.poll("fdIsAccept");
				if ("1".equals(fdOsAccept)) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" 1 = 2");
				} else {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							"kmImeetingTopic.fdIsAccept =:fdIsAccept");
					hqlInfo.setParameter("fdIsAccept", Boolean.FALSE);
				}
			}
			if (StringUtil.isNotNull(whereBlock)) {
				hqlInfo.setWhereBlock(whereBlock);
			}
		}

		if (exceptValue != null) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " 1=1 ";
			}
			if (exceptValue[1].indexOf("_") > -1) {
				String[] _exceptValue = exceptValue[1].split("_");
				for (int i = 0; i < _exceptValue.length; i++) {
					whereBlock = whereBlock + " and kmImeetingTopic.docStatus != :docStatus" + i;
					hqlInfo.setParameter("docStatus" + i, _exceptValue[i]);
				}
			} else {
				whereBlock = whereBlock + " and kmImeetingTopic.docStatus != :docStatus";
				hqlInfo.setParameter("docStatus", exceptValue[1]);
			}
			if (StringUtil.isNotNull(whereBlock)) {
				hqlInfo.setWhereBlock(whereBlock);
			}
		}
		String docStatus = cv.poll("docStatus");
		if (StringUtil.isNull(docStatus)) {
			cv.remove("docStatus");
		} else {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ", " kmImeetingTopic.docStatus = :docStatus ");
			hqlInfo.setParameter("docStatus", docStatus);
			hqlInfo.setWhereBlock(whereBlock);
		}

		String mytopic = cv.poll("mytopic");
		if (StringUtil.isNotNull(mytopic)) {
			buildMyTopicHql(request, hqlInfo, mytopic);
		}

		// 移动端弹出框搜索
		String skw = request.getParameter("q._keyword");
		if (StringUtil.isNotNull(skw)) {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingTopic.docSubject like :docSubject ");
			hqlInfo.setParameter("docSubject", "%" + skw + "%");
			hqlInfo.setWhereBlock(whereBlock);
		}

		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingTopic.class);
	}

	/**
	 * 我的议题HQL
	 */
	private void buildMyTopicHql(HttpServletRequest request, HQLInfo hqlInfo, String mytopic) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		// 我录入的
		if ("myCreate".equals(mytopic)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", " kmImeetingTopic.docCreator.fdId=:creatorId ");
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
			hqlInfo.setWhereBlock(whereBlock);
		} else if ("myApproved".equals(mytopic)) {
			// 待我审的
			SysFlowUtil.buildLimitBlockForMyApproved("kmImeetingTopic", hqlInfo);
		} else if ("myApproval".equals(mytopic)) {
			// 我已审的
			SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingTopic", hqlInfo);
		}
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward list = super.list(mapping, form, request, response);
		if ("0".equals(request.getParameter("isDialog"))) {
			return getActionForward("listDialog", mapping, form, request, response);
		}
		return list;
	}

	/**
	 * 根据id值加载采购明细信息，ajax调用
	 */
	public ActionForward loadTopicList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ids = request.getParameter("ids");
		String[] fdIds = ids.split(";");
		if (StringUtil.isNotNull(ids)) {
			JSONArray jsonArr = ((IKmImeetingTopicService) getServiceImp(request)).loadTopicList(fdIds, request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArr.toString());
		}
		return null;
	}

	protected ISysOrgCoreService sysOrgCoreService;

	protected ISysOrgCoreService getSysOrgServiceImp() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	public ActionForward getDept(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getDept", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdOrgId = request.getParameter("fdOrgId");
		String deptId = "";
		String deptName = "";
		try {
			if (StringUtil.isNotNull(fdOrgId)) {
				SysOrgElement org = getSysOrgServiceImp().findByPrimaryKey(fdOrgId);
				if (org != null && org.getFdParent() != null) {
					deptId = org.getFdParent().getFdId();
					deptName = org.getFdParent().getFdName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getDept", false, getClass());
		JSONObject json = new JSONObject();
		json.put("deptId", deptId);
		json.put("deptName", deptName);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}


	@Override
	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getKmImeetingTopicCategoryService();
	}
}
