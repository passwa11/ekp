package com.landray.kmss.sys.attachment.borrow.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.borrow.forms.SysAttBorrowForm;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCoreService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysAttBorrowServiceImp extends ExtendDataServiceImp
		implements ISysAttBorrowService, ApplicationListener<ApplicationEvent> {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ILbpmProcessCoreService lbpmProcessCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
                                            ConvertorContext context) throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof SysAttBorrow) {
			SysAttBorrow sysAttBorrow = (SysAttBorrow) model;
			SysAttBorrowForm sysAttBorrowForm = (SysAttBorrowForm) form;
			if (sysAttBorrow.getDocStatus() == null
					|| sysAttBorrow.getDocStatus().startsWith("1")) {
				if (sysAttBorrowForm.getDocStatus() != null && (sysAttBorrowForm
						.getDocStatus().startsWith("1")
						|| sysAttBorrowForm.getDocStatus().startsWith("2"))) {
					sysAttBorrow.setDocStatus(sysAttBorrowForm.getDocStatus());
				}

			}

			// 设置失效时间
			if (sysAttBorrow.getFdBorrowEffectiveTime() != null) {
				if (sysAttBorrow.getFdDuration() > 0L) {

					Date fdBorrowExpireTime = DateUtil.getNextDay(
							sysAttBorrow.getFdBorrowEffectiveTime(),
							sysAttBorrow.getFdDuration().intValue());
					sysAttBorrow.setFdBorrowExpireTime(fdBorrowExpireTime);

				}
			}

		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		SysAttBorrow sysAttBorrow = new SysAttBorrow();
		sysAttBorrow.setDocCreateTime(new Date());

		String fdReadEnable = requestContext.getParameter("fdReadEnable");
		sysAttBorrow.setFdReadEnable("true".equals(fdReadEnable));

		String fdDownloadEnable =
				requestContext.getParameter("fdDownloadEnable");
		sysAttBorrow.setFdDownloadEnable("true".equals(fdDownloadEnable));

		String fdCopyEnable = requestContext.getParameter("fdCopyEnable");
		sysAttBorrow.setFdCopyEnable("true".equals(fdCopyEnable));
		
		String fdPrintEnable = requestContext.getParameter("fdPrintEnable");
		sysAttBorrow.setFdPrintEnable("true".equals(fdPrintEnable));
		
		sysAttBorrow.setFdStatus(SysAttBorrow.STATUS_UNDO);
		sysAttBorrow.setDocCreator(UserUtil.getUser());
		List<SysOrgElement> fdBorrowers = new ArrayList<SysOrgElement>();
		fdBorrowers.add(UserUtil.getUser());
		sysAttBorrow.setFdBorrowers(fdBorrowers);

		// 附件主键
		String fdAttId = requestContext.getParameter("fdAttId");
		if (StringUtil.isNotNull(fdAttId)) {

			SysAttMain main =
					(SysAttMain) sysAttMainService.findByPrimaryKey(fdAttId);
			sysAttBorrow.setFdAttId(fdAttId);
			sysAttBorrow.setDocSubject(main.getFdFileName());

		}

		return sysAttBorrow;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model,
                                           RequestContext requestContext) throws Exception {
		getLbpmProcessCoreService().initFormDefaultSetting(form, "sysAttBorrow",
				"sysAttBorrow", requestContext);
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService =
					(ISysNotifyMainCoreService) SpringBeanUtil
							.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	public ILbpmProcessCoreService getLbpmProcessCoreService() {
		if (lbpmProcessCoreService == null) {
			lbpmProcessCoreService = (ILbpmProcessCoreService) SpringBeanUtil
					.getBean("lbpmProcessCoreService");
		}
		return lbpmProcessCoreService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		SysAttBorrowForm sysAttBorrowForm =
				(SysAttBorrowForm) super.convertModelToForm(form, model,
						requestContext);

		Map attForms = sysAttBorrowForm.getAttachmentForms();

		SysAttMain main = (SysAttMain) sysAttMainService
				.findByPrimaryKey(sysAttBorrowForm.getFdAttId());

		AttachmentDetailsForm attForm =
				(AttachmentDetailsForm) attForms.get(main.getFdKey());

		attForm.setFdModelId(main.getFdModelId());
		attForm.setFdModelName(main.getFdModelName());
		attForm.setFdKey(main.getFdKey());
		requestContext.setAttribute("fdKey", main.getFdKey());
		if (!attForm.getAttachments().contains(main)) {
			attForm.getAttachments().add(main);
		}
		return sysAttBorrowForm;
	}

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttBorrowServiceImp.class);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		if (event == null) {
			return;
		}

		Object obj = event.getSource();

		if (!(obj instanceof SysAttBorrow)) {
			return;
		}

		if (!(event instanceof Event_SysFlowFinish)) {
			return;
		}

		try {

			SysAttBorrow borrow = (SysAttBorrow) obj;

			if (borrow.getFdBorrowEffectiveTime() == null) {
				borrow.setFdBorrowEffectiveTime(DateUtil.getDate(0));
			}

			String fdStatus = getStatus(borrow);
			borrow.setFdStatus(fdStatus);

			// 设置失效时间
			if (borrow.getFdBorrowExpireTime() == null) {

				if (borrow.getFdDuration() > 0L) {

					Date fdBorrowExpireTime = DateUtil.getNextDay(
							borrow.getFdBorrowEffectiveTime(),
							borrow.getFdDuration().intValue());
					borrow.setFdBorrowExpireTime(fdBorrowExpireTime);

				}
			}

			this.getBaseDao().update(borrow);
			this.updateBorrowNum(borrow.getFdAttId());

		} catch (Exception e) {
			logger.error("更新附件借阅状态报错：", e);
		}

	}

	/**
	 * 更新附件借阅数
	 * 
	 * @param attId
	 * @throws Exception
	 */
	private void updateBorrowNum(String attId) throws Exception {
		String hql =
				"update SysAttMain set fdBorrowCount = fdBorrowCount + 1 where fdId = :fdId";
		getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdId", attId).executeUpdate();
	}

	/**
	 * 获取借阅状态
	 * 
	 * @param borrow
	 * @return
	 */
	private String getStatus(SysAttBorrow borrow) {

		// 未发布
		if (!SysDocConstant.DOC_STATUS_PUBLISH.equals(borrow.getDocStatus())) {
			return SysAttBorrow.STATUS_UNDO;
		}

		Long fdDuration = borrow.getFdDuration();

		// 今天
		Date fdNow = DateUtil.getDate(0);
		Long fdNowTime = fdNow.getTime();

		// 生效时间
		Date fdEffectiveStart = borrow.getFdBorrowEffectiveTime();

		// 未生效
		if (fdEffectiveStart.getTime() < fdNowTime) {
			return SysAttBorrow.STATUS_UNDO;
		}

		// ----生效后----

		// 不限期
		if (fdDuration == 0L) {
			return SysAttBorrow.STATUS_DOING;
		}

		// 失效时间
		Date fdEffectiveEnd =
				DateUtils.addDays(fdEffectiveStart, fdDuration.intValue());

		// 失效了
		if (fdNowTime >= fdEffectiveEnd.getTime()) {
			return SysAttBorrow.STATUS_DONE;
		}

		// 未失效
		return SysAttBorrow.STATUS_DOING;

	}

	/**
	 * 生效更新语句<br>
	 * 未生效 且 发布 且 生效时间后 且 过期时间前
	 */
	private final String UPDATE_DOING_SQL =
			"update sys_att_borrow set fd_status = 1 "
					+ "where fd_status = 0 and doc_status = '30' and"
					+ " (fd_borrow_effective_time <= ? and fd_borrow_expire_time > ? or fd_duration = 0)";

	/**
	 * 失效更新语句<br>
	 * 未过期 且 发布 且 过期时间后
	 * 
	 */
	private final String UPDATE_DONE_SQL =
			"update sys_att_borrow set fd_status = 2 "
					+ "where doc_status = '30' and (fd_status = 1 or fd_status = 0) and fd_borrow_expire_time <= ?";

	/**
	 * 关闭更新语句
	 */
	private final String UPDATE_CLOSE_SQL =
			"update sys_att_borrow set fd_status = 3 where fd_id in (:fdIds)";

	@Override
    public void updateBorrowFdStatus(SysQuartzJobContext context)
			throws Exception {

		try {
			// 当前时间
			Date now = DateUtil.getDate(0);

			Session session = getBaseDao().getHibernateSession();
			// 更新今天生效的借阅
			NativeQuery nativeQuery = session.createNativeQuery(UPDATE_DOING_SQL);
			nativeQuery.addSynchronizedQuerySpace("update sys_att_borrow").setDate(0, now)
					.setDate(1, now).executeUpdate();
			// 查询今天过期的借阅
			NativeQuery nativeQuery2 = session.createNativeQuery(UPDATE_DONE_SQL);
			nativeQuery2.addSynchronizedQuerySpace("update sys_att_borrow").setDate(0, now)
					.executeUpdate();

		} catch (Exception e) {
			context.logError("定时任务更新借阅状态报错：", e);
			throw e;
		}

	}

	@Override
    public void updateCloseStatus(String[] fdIds) throws Exception {

		Session session = getBaseDao().getHibernateSession();
		// 更新今天生效的借阅
		session.createNativeQuery(UPDATE_CLOSE_SQL)
				.setParameterList("fdIds", fdIds).executeUpdate();
	}

}
