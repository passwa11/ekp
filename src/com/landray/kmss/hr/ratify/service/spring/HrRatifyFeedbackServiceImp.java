package com.landray.kmss.hr.ratify.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.ratify.Constant;
import com.landray.kmss.hr.ratify.dao.IHrRatifyFeedbackDao;
import com.landray.kmss.hr.ratify.forms.HrRatifyFeedbackForm;
import com.landray.kmss.hr.ratify.model.HrRatifyFeedback;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.service.IHrRatifyFeedbackService;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import org.springframework.transaction.TransactionStatus;

public class HrRatifyFeedbackServiceImp extends ExtendDataServiceImp implements IHrRatifyFeedbackService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrRatifyFeedback) {
            HrRatifyFeedback hrRatifyFeedback = (HrRatifyFeedback) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyFeedback hrRatifyFeedback = new HrRatifyFeedback();
        hrRatifyFeedback.setDocCreateTime(new Date());
        hrRatifyFeedback.setDocCreator(UserUtil.getUser());
        HrRatifyUtil.initModelFromRequest(hrRatifyFeedback, requestContext);
        return hrRatifyFeedback;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyFeedback hrRatifyFeedback = (HrRatifyFeedback) model;
    }

    @Override
    public List<HrRatifyFeedback> findByFdDoc(HrRatifyMain fdDoc) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyFeedback.fdDoc.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdDoc.getFdId());
        return this.findList(hqlInfo);
    }

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
    }

	private IHrRatifyMainService hrRatifyMainService;

	public void
			setHrRatifyMainService(IHrRatifyMainService hrRatifyMainService) {
		this.hrRatifyMainService = hrRatifyMainService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysOrgElement receiver;
		HrRatifyMain main = null;
		List notifyList = new ArrayList();
		HrRatifyFeedbackForm feedbackForm = (HrRatifyFeedbackForm) form;
		String mainId = feedbackForm.getFdDocId();
		String num = super.add(form, requestContext);
		String[] receiveIds = feedbackForm.getFdNotifyId().split(";");
		if (StringUtil.isNotNull(mainId)) {
			main = hrRatifyMainService.findEntity(mainId);
			if (main != null) {
				if (!"".equals(receiveIds[0])
						&& StringUtil.isNotNull(((HrRatifyFeedbackForm) form)
						.getFdNotifyType())) {
					for (int i = 0; i < receiveIds.length; i++) {
						receiver = sysOrgCoreService.findByPrimaryKey(receiveIds[i]);
						notifyList.add(receiver);
					}

					// 添加通知人到可阅读者里
					ArrayUtil.concatTwoList(notifyList, main.getAuthOtherReaders());
				}
				NotifyReplace notifyReplace = new NotifyReplace();
				notifyReplace.addReplaceText(
						"hr-ratify:hrRatifyFeedback.fdSummary",
						((HrRatifyFeedbackForm) form).getFdSummary());
				NotifyContext notifyContext = sysNotifyMainCoreService
						.getContext("hr-ratify:hrRatify.feedbackLog.notify");
				notifyContext.setKey("passreadKey");
				notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
				notifyContext.setNotifyType(((HrRatifyFeedbackForm) form)
						.getFdNotifyType());
				notifyContext.setNotifyTarget(notifyList);
				sysNotifyMainCoreService.sendNotify(main, notifyContext,
						notifyReplace);

				if (!Constant.STATUS_FEEDBACK.equals(main.getDocStatus())) {
					TransactionStatus status = null;
					try {
						status = TransactionUtils.beginNewTransaction();
						String updateSql = "update hr_ratify_main set doc_status = :docStatus where fd_id = :mailId";
						hrRatifyMainService.getBaseDao().getHibernateSession().createSQLQuery(updateSql)
								.setParameter("docStatus", Constant.STATUS_FEEDBACK).setParameter("mailId", mainId)
								.executeUpdate();
						// 提交事务
						TransactionUtils.commit(status);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						if (status != null) {
							TransactionUtils.rollback(status);

						}
					}
				}
			}
		}
		return num;
	}

	@Override
	public int getKmReviewFeedbackInfoCount(String reviewId) throws Exception {
		IHrRatifyFeedbackDao hrRatifyFeedbackDao = (IHrRatifyFeedbackDao) getBaseDao();
		return hrRatifyFeedbackDao.getKmReviewFeedbackInfoCount(reviewId);
	}
}
