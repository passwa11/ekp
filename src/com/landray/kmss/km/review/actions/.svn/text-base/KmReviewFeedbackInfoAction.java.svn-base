package com.landray.kmss.km.review.actions;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.review.forms.KmReviewFeedbackInfoForm;
import com.landray.kmss.km.review.model.KmReviewFeedbackInfo;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewFeedbackInfoService;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌
 */
public class KmReviewFeedbackInfoAction extends ExtendAction

{
	protected IKmReviewFeedbackInfoService kmReviewFeedbackInfoService;

	protected IKmReviewMainService kmReviewMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmReviewFeedbackInfoService == null) {
            kmReviewFeedbackInfoService = (IKmReviewFeedbackInfoService) getBean("kmReviewFeedbackInfoService");
        }
		return kmReviewFeedbackInfoService;
	}

	public IKmReviewMainService getKmReviewMainService() {
		if (kmReviewMainService == null) {
            kmReviewMainService = (IKmReviewMainService) getBean("kmReviewMainService");
        }
		return kmReviewMainService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		KmReviewFeedbackInfoForm feedbackInfo = (KmReviewFeedbackInfoForm) form;
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		String mainId = feedbackInfo.getFdMainId();
		if (StringUtil.isNotNull(mainId)) {
			KmReviewMain main = (KmReviewMain) getKmReviewMainService()
					.findByPrimaryKey(mainId);
			List readerList = main.getAuthAllReaders();
			for (Iterator it = readerList.iterator(); it.hasNext();) {
				SysOrgElement reader = (SysOrgElement) it.next();
				buffer.append(reader.getFdName()).append("; ");
			}
			if (buffer.length() > 0) {
				feedbackInfo.setFdReaderNames(buffer.substring(0,
						buffer.length() - 1));
			}

		}
		feedbackInfo.setDocCreatorName(UserUtil.getUser().getFdName());
		feedbackInfo.setDocCreatorId(UserUtil.getUser().getFdId().toString());
		feedbackInfo.setDocCreatorTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		return feedbackInfo;
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.add(mapping, form, request, response);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String mainId = request.getParameter("fdModelId");
		if (StringUtil.isNotNull(mainId)) {
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " kmReviewFeedbackInfo.kmReviewMain.fdId=:mainId";
			} else {
				whereBlock += " and kmReviewFeedbackInfo.kmReviewMain.fdId=:mainId";
			}
			hqlInfo.setParameter("mainId", mainId);
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	public ActionForward listdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		return mapping.findForward("listdata");
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmReviewFeedbackInfoForm mainform = (KmReviewFeedbackInfoForm) form;
		mainform.setFdId(null);//解决移动端错误传递fdId的问题
		System.out.println(JSON.toJSONString(mainform));
		AttachmentDetailsForm af = (AttachmentDetailsForm) mainform
				.getAttachmentForms().get("feedBackAttachment");
		if (null != af.getAttachmentIds()
				&& af.getAttachmentIds().length() > 0) {
			mainform.setFdHasAttachment("true");
		} else {
			mainform.setFdHasAttachment("false");
		}
		return super.save(mapping, form, request, response);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm( mapping,  form,
				 request,  response);
		KmReviewFeedbackInfo kmReviewFeedbackInfo =(KmReviewFeedbackInfo) getServiceImp(request).findByPrimaryKey(request.getParameter("fdId"),
				null, true);

		//主文档创建人信息
		SysOrgPerson kmReviewMainSysOrgPerson=((KmReviewMain)getKmReviewMainService().findByPrimaryKey(kmReviewFeedbackInfo.getKmReviewMain().getFdId())).getDocCreator();

		String fdNotifyPeople = kmReviewFeedbackInfo.getFdNotifyPeople();
		List<String> feedbackFdNotifyPeopleList = StringUtil.isNotNull(fdNotifyPeople) ? Arrays.asList(fdNotifyPeople.split(";")).stream().filter(s-> !s.isEmpty()).collect(Collectors.toList()): new ArrayList<>();
		request.setAttribute("kmReviewFdId",kmReviewFeedbackInfo.getKmReviewMain().getFdId());// 文档主Id
		request.setAttribute("kmReviewDocSubject",kmReviewFeedbackInfo.getKmReviewMain().getDocSubject());// 文档主名称
		request.setAttribute("kmReviewCreatorHeadUrl",PersonInfoServiceGetter.getPersonHeadimageUrl(kmReviewMainSysOrgPerson.getFdId()).substring(1));// 文档创建人头像url
		request.setAttribute("kmReviewCreatorFdName",kmReviewMainSysOrgPerson.getFdName());// 文档创建人名称
		request.setAttribute("feedbackCreatorHeadUrl",PersonInfoServiceGetter.getPersonHeadimageUrl(kmReviewFeedbackInfo.getFdCreator().getFdId()).substring(1));// 反馈人头像url
		request.setAttribute("feedbackFdNotifyPeopleList",feedbackFdNotifyPeopleList);// 反馈人头像url

	}
}
