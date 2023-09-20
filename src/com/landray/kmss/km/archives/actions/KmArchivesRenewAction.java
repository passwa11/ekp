package com.landray.kmss.km.archives.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.archives.forms.KmArchivesDetailsForm;
import com.landray.kmss.km.archives.forms.KmArchivesRenewForm;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesRenew;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesRenewService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class KmArchivesRenewAction extends ExtendAction {

    private IKmArchivesRenewService kmArchivesRenewService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesRenewService == null) {
            kmArchivesRenewService = (IKmArchivesRenewService) getBean("kmArchivesRenewService");
        }
        return kmArchivesRenewService;
    }

	private IKmArchivesBorrowService kmArchivesBorrowService;

	public IKmArchivesBorrowService getKmArchivesBorrowServiceImp() {
		if (kmArchivesBorrowService == null) {
			kmArchivesBorrowService = (IKmArchivesBorrowService) getBean(
					"kmArchivesBorrowService");
		}
		return kmArchivesBorrowService;
	}

	private IKmArchivesDetailsService kmArchivesDetailsService;

	public IBaseService getKmArchivesDetailsServiceImp() {
		if (kmArchivesDetailsService == null) {
			kmArchivesDetailsService = (IKmArchivesDetailsService) getBean(
					"kmArchivesDetailsService");
		}
		return kmArchivesDetailsService;
	}

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesRenew.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesRenewForm kmArchivesRenewForm = (KmArchivesRenewForm) super.createNewForm(mapping, form, request, response);
		String fdBorrowId = request.getParameter("fdBorrowId");
		KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) getKmArchivesBorrowServiceImp()
				.findByPrimaryKey(fdBorrowId);
		request.setAttribute("fdBorrowDate", DateUtil.convertDateToString(
				kmArchivesBorrow.getFdBorrowDate(), DateUtil.PATTERN_DATE));
		List<KmArchivesDetails> details = kmArchivesBorrow
				.getFdBorrowDetails();
		AutoArrayList detailsList = new AutoArrayList(
				KmArchivesDetailsForm.class);
		// 过期范围
		String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		int range = 0;
		if (StringUtil.isNotNull(expireRange)) {
			try {
				range = Integer.parseInt(expireRange);
			} catch (Exception e) {

			}
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, range);

		for (KmArchivesDetails detail : details) {

			KmArchivesMain kmArchivesMain = detail.getFdArchives();
			Boolean fdExpired = true;
			String authUrl = ModelUtil.getModelUrl(kmArchivesMain);
			if (UserUtil.checkAuthentication(authUrl, "GET")) {
				Boolean fdDstroyed = kmArchivesMain.getFdDestroyed();
				if (kmArchivesMain.getFdValidityDate() == null || (kmArchivesMain.getFdValidityDate() != null
						&& (kmArchivesMain.getFdValidityDate().getTime() > cal.getTimeInMillis()))) {
					fdExpired = false;
				}
				if (!detail.getFdStatus().equals(KmArchivesConstant.BORROW_STATUS_EXPIRED) && !fdExpired
						&& !fdDstroyed) {
					KmArchivesDetailsForm kmArchivesDetailsForm = new KmArchivesDetailsForm();
					kmArchivesDetailsForm.setFdId(detail.getFdId());
					kmArchivesDetailsForm.setFdArchId(detail.getFdArchives().getFdId());
					kmArchivesDetailsForm.setFdArchives(detail.getFdArchives());
					kmArchivesDetailsForm.setFdAuthorityRange(detail.getFdAuthorityRange());
					kmArchivesDetailsForm.setFdReturnDate(DateUtil.convertDateToString(detail.getFdReturnDate(),
							DateUtil.TYPE_DATETIME,
									ResourceUtil.getLocaleByUser()));
					if (detail.getFdRenewReturnDate() != null) {
						kmArchivesDetailsForm.setFdRenewReturnDate(DateUtil.convertDateToString(
								detail.getFdRenewReturnDate(), DateUtil.TYPE_DATETIME, ResourceUtil.getLocaleByUser()));
					}
					kmArchivesDetailsForm.setFdBorrowerId(detail.getFdBorrower().getFdId());
					kmArchivesDetailsForm.setFdStatus(KmArchivesConstant.BORROW_STATUS_LOANING);
					detailsList.add(kmArchivesDetailsForm);
				}
			}
		}
		kmArchivesRenewForm.setDetailsList(detailsList);
        ((IKmArchivesRenewService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesRenewForm;
    }

	/**
	 * 保存续借记录，一条明细对应一个续借记录
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmArchivesRenewForm kmArchivesRenewForm = (KmArchivesRenewForm) form;
			AutoArrayList detailsList = kmArchivesRenewForm.getDetailsList();
			for (int i = 0; i < detailsList.size(); i++) {
				KmArchivesDetailsForm detailsForm = (KmArchivesDetailsForm) detailsList
						.get(i);
				if (StringUtil.isNull(detailsForm.getFdArchId())) {
					detailsList.remove(i);
				}
			}
			if (detailsList == null || detailsList.size() == 0) {
				throw new NoRecordException();
			}
			StringBuffer fdRemarks = new StringBuffer();
			for (int i = 0; i < detailsList.size(); i++) {
				KmArchivesDetailsForm detailsForm = (KmArchivesDetailsForm) detailsList
						.get(i);
				KmArchivesDetails kmArchivesDetails = (KmArchivesDetails) getKmArchivesDetailsServiceImp()
						.findByPrimaryKey(detailsForm.getFdId());
				Date oldReturnDate = kmArchivesDetails
						.getFdRenewReturnDate() == null
								? kmArchivesDetails.getFdReturnDate()
								: kmArchivesDetails.getFdRenewReturnDate();
				getKmArchivesDetailsServiceImp().update(
						(IExtendForm) detailsForm,
						new RequestContext(request));
				KmArchivesRenew renew = new KmArchivesRenew();
				renew.setDocCreateTime(new Date());
				renew.setFdReason(kmArchivesRenewForm.getFdReason());
				renew.setDocCreator(UserUtil.getUser());
				renew.setFdDetailsId(kmArchivesDetails.getFdId());
				getServiceImp(request).add((IBaseModel) renew);
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_ADD,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putAdd(renew, "fdDetailsId",
							"docCreateTime", "fdReason", "docCreator");
				}
				fdRemarks.append(
						kmArchivesRenewForm.getDocCreatorName() + "于"
								+ kmArchivesRenewForm.getDocCreateTime()
								+ "对\""
								+ kmArchivesDetails.getFdArchives()
										.getDocSubject()
								+ "\"进行续借,归还时间由\""
								+ DateUtil.convertDateToString(oldReturnDate,
										DateUtil.PATTERN_DATE)
								+ "\"变更为\"" + detailsForm.getFdRenewReturnDate()
								+ "\"\r\n");
			}
			String fdBorrowId = request.getParameter("fdBorrowId");
			KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) getKmArchivesBorrowServiceImp()
					.findByPrimaryKey(fdBorrowId);
			if (StringUtil.isNotNull(kmArchivesBorrow.getFdRemarks())) {
				kmArchivesBorrow.setFdRemarks(
						kmArchivesBorrow.getFdRemarks() + fdRemarks.toString());
			} else {
				kmArchivesBorrow.setFdRemarks(fdRemarks.toString());
			}
			getKmArchivesBorrowServiceImp().update(kmArchivesBorrow);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}
}
