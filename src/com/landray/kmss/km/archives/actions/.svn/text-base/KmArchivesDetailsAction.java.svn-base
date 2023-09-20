package com.landray.kmss.km.archives.actions;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.forms.KmArchivesDetailsForm;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesLibrary;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesLibraryService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class KmArchivesDetailsAction extends ExtendAction {

    private IKmArchivesDetailsService kmArchivesDetailsService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesDetailsService == null) {
            kmArchivesDetailsService = (IKmArchivesDetailsService) getBean("kmArchivesDetailsService");
        }
        return kmArchivesDetailsService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock = hqlInfo.getWhereBlock();
		CriteriaValue cv = new CriteriaValue(request);
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesDetails.fdBorrower = :fdBorrower and kmArchivesDetails.fdStatus!=:fdStatus)");
		hqlInfo.setParameter("fdStatus", "0");
		hqlInfo.setParameter("fdBorrower", UserUtil.getKMSSUser().getPerson());
		// 标题
		String docSubject = cv.poll("docSubject");
		if (StringUtil.isNotNull(docSubject)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.docSubject like :docSubject)");
			hqlInfo.setParameter("docSubject", "%" + docSubject + "%");
		}
		// 分类
		String docTemplate = cv.poll("docTemplate");
		if (StringUtil.isNotNull(docTemplate)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.docTemplate.fdId = :docTemplate)");
			hqlInfo.setParameter("docTemplate", docTemplate);
		}
		// 卷库
		String fdLibrary = cv.poll("fdLibrary");
		if (StringUtil.isNotNull(fdLibrary)) {
			IKmArchivesLibraryService kmArchivesLibraryService=(IKmArchivesLibraryService) SpringBeanUtil.getBean("kmArchivesLibraryService");
			KmArchivesLibrary kmArchivesLibrary=(KmArchivesLibrary) kmArchivesLibraryService.findByPrimaryKey(fdLibrary);
			if(kmArchivesLibrary!=null) {
				fdLibrary=kmArchivesLibrary.getFdName();
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.fdLibrary = :fdLibrary)");
			hqlInfo.setParameter("fdLibrary", fdLibrary);
		}
		// 编号
		String docNumber = cv.poll("docNumber");
		if (StringUtil.isNotNull(docNumber)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.docNumber like :docNumber)");
			hqlInfo.setParameter("docNumber", "%" + docNumber + "%");
		}
		// 归档人
		String docCreator = cv.poll("docCreator");
		if (StringUtil.isNotNull(docCreator)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.docCreator.fdId = :docCreator)");
			hqlInfo.setParameter("docCreator", docCreator);
		}
		// 归档日期
		String[] fdFileDate = cv.polls("fdFileDate");
		if (fdFileDate != null) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesDetails.fdArchives.fdFileDate >= :minFileDate and kmArchivesDetails.fdArchives.fdFileDate <= :maxFileDate)");
			hqlInfo.setParameter("minFileDate", DateUtil
					.convertStringToDate(fdFileDate[0], DateUtil.PATTERN_DATE));
			hqlInfo.setParameter("maxFileDate", DateUtil
					.convertStringToDate(fdFileDate[1], DateUtil.PATTERN_DATE));
		}
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesDetails.docMain.docStatus = :mainDocStatus)");
		hqlInfo.setParameter("mainDocStatus",
				SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setWhereBlock(whereBlock);
        //HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesDetails.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesDetailsForm kmArchivesDetailsForm = (KmArchivesDetailsForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesDetailsService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesDetailsForm;
    }

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmArchivesDetailsForm detailsForm = (KmArchivesDetailsForm) form;
		String fdAuthorityRange = detailsForm.getFdAuthorityRange();
		if (StringUtil.isNull(fdAuthorityRange)) {
			KmArchivesConfig config = new KmArchivesConfig();
			detailsForm.setFdAuthorityRange(config.getFdDefaultRange());
		}
	}

	public ActionForward returnBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		KmssMessages messages = new KmssMessages();
		try {
			String fdArchId = request.getParameter("fdArchId");
			String borrowerId = UserUtil.getKMSSUser().getUserId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmArchivesDetails.fdArchives.fdId =:fdArchId and kmArchivesDetails.fdBorrower.fdId =:borrowerId and kmArchivesDetails.fdStatus =:fdStatus");
			hqlInfo.setParameter("fdStatus", "1");
			hqlInfo.setParameter("fdArchId", fdArchId);
			hqlInfo.setParameter("borrowerId", borrowerId);
			List<KmArchivesDetails> details = (List<KmArchivesDetails>) this
					.getServiceImp(request).findList(hqlInfo);
			JSONArray array = new JSONArray();
			for (KmArchivesDetails detail : details) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(
						new String[] { "fdBorrower", "fdArchives", "docMain" });
				JSONObject json = JSONObject.fromObject(detail, jsonConfig);
				json.put("fdBorrowerId", detail.getFdBorrower().getFdId());
				json.put("fdArchId", detail.getFdArchives().getFdId());
				array.add(json);
			}
			PrintWriter out = response.getWriter();
			out.print(array.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}

	public ActionForward comfirmReturnBack(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			String fdId = request.getParameter("fdId");
			KmArchivesDetails kmArchivesDetails = (KmArchivesDetails) this
					.getServiceImp(request).findByPrimaryKey(fdId);
			Calendar calendar = Calendar.getInstance();
			Date oldDate = null;
			if (kmArchivesDetails.getFdRenewReturnDate() != null) {
				calendar.setTime(kmArchivesDetails.getFdRenewReturnDate());
				oldDate = calendar.getTime();
				kmArchivesDetails.setFdRenewReturnDate(new Date());
				if (UserOperHelper.allowLogOper("comfirmReturnBack",
						KmArchivesDetails.class.getName())) {
					UserOperContentHelper.putUpdate(kmArchivesDetails)
							.putSimple("fdRenewReturnDate", oldDate,
									kmArchivesDetails.getFdRenewReturnDate())
							.putSimple("fdStatus",
									kmArchivesDetails.getFdStatus(),
									KmArchivesConstant.BORROW_STATUS_RETURNED);
				}
			} else {
				calendar.setTime(kmArchivesDetails.getFdReturnDate());
				oldDate = calendar.getTime();
				kmArchivesDetails.setFdReturnDate(new Date());
				if (UserOperHelper.allowLogOper("comfirmReturnBack",
						KmArchivesDetails.class.getName())) {
					UserOperContentHelper.putUpdate(kmArchivesDetails)
							.putSimple("fdReturnDate", oldDate,
									kmArchivesDetails.getFdReturnDate())
							.putSimple("fdStatus",
									kmArchivesDetails.getFdStatus(),
									KmArchivesConstant.BORROW_STATUS_RETURNED);
				}
			}
			kmArchivesDetails
					.setFdStatus(KmArchivesConstant.BORROW_STATUS_RETURNED);

			this.getServiceImp(request).update(kmArchivesDetails);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.data(mapping, form, request, response);
		if (UserOperHelper.allowLogOper("detail_data",
				"com.landray.kmss.km.archives.model.KmArchivesDetails")) {
			Page page = (Page) request.getAttribute("queryPage");
			List<KmArchivesDetails> list = page.getList();
			for (KmArchivesDetails detail : list) {
				UserOperContentHelper.putFind(detail.getFdArchives().getFdId(),
						detail.getFdArchives().getDocSubject(),
						detail.getFdArchives().getClass().getName());
			}
		}
		return forward;
	}
}
