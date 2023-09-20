package com.landray.kmss.km.imeeting.synchro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncBind;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncBindService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncMappingService;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingResponseType;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroProvider;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroCommonMetting;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroMeetingResponse;
import com.landray.kmss.sys.agenda.model.SysAgendaMain;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class IMeetingSynchroInThread implements Runnable {



	private SysQuartzJobContext sysQuartzJobContext;

	private CountDownLatch countDownLatch;

	private Date lastSyncroDate = null;


	private IKmImeetingMainService kmImeetingMainService;

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
					.getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	public void
			setKmImeetingMainService(
					IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private IKmImeetingSyncMappingService kmImeetingSyncMappingService;

	public IKmImeetingSyncMappingService getKmImeetingSyncMappingService() {
		if (kmImeetingSyncMappingService == null) {
			kmImeetingSyncMappingService = (IKmImeetingSyncMappingService) SpringBeanUtil
					.getBean("kmImeetingSyncMappingService");
		}
		return kmImeetingSyncMappingService;
	}

	public void setKmImeetingSyncMappingService(
			IKmImeetingSyncMappingService kmImeetingSyncMappingService) {
		this.kmImeetingSyncMappingService = kmImeetingSyncMappingService;
	}

	private IKmImeetingSyncBindService kmImeetingSyncBindService;

	public IKmImeetingSyncBindService getKmImeetingSyncBindService() {
		if (kmImeetingSyncBindService == null) {
			kmImeetingSyncBindService = (IKmImeetingSyncBindService) SpringBeanUtil
					.getBean("kmImeetingSyncBindService");
		}
		return kmImeetingSyncBindService;
	}



	public IMeetingSynchroInThread() {
		super();
	}

	public IMeetingSynchroInThread(SysQuartzJobContext sysQuartzJobContext,
			CountDownLatch countDownLatch) {
		this.sysQuartzJobContext = sysQuartzJobContext;
		this.countDownLatch = countDownLatch;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(IMeetingSynchroInThread.class);


	private ISysOrgCoreService sysOrgCoreService;

	private ISysOrgPersonService sysOrgPersonService;

	// private ISysAttUploadService sysAttUploadService;

	private ICoreOuterService sysAttachmentService;

	// private ISysAttMainCoreInnerService sysAttMainService;
	//
	// public ISysAttMainCoreInnerService getSysAttMainService() {
	// if (sysAttMainService == null) {
	// sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
	// .getBean("sysAttMainService");
	// }
	// return sysAttMainService;
	// }

	public ICoreOuterService getSysAttachmentService() {
		if (sysAttachmentService == null) {
			sysAttachmentService = (ICoreOuterService) SpringBeanUtil
					.getBean("sysAttachmentService");
		}
		return sysAttachmentService;
	}

	// public void setSysAttachmentService(
	// SysAttachmentCoreOuterService sysAttachmentService) {
	// this.sysAttachmentService = sysAttachmentService;
	// }


	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}


	/**
	 * 用户ID
	 */
	private String personId;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public List<String> getAppKeys() {
		return appKeys;
	}

	public void setAppKeys(List<String> appKeys) {
		this.appKeys = appKeys;
	}

	private List<Exception> exceptions = new ArrayList<Exception>();

	/**
	 * 用户需要同步的应用
	 */
	private List<String> appKeys = null;

	@SuppressWarnings("unchecked")
	private void init() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingAuth.fdId");
		hqlInfo.setWhereBlock("kmImeetingAuth.docCreator.fdId =:fdId");
		hqlInfo.setParameter("fdId", getPersonId());

	}

	private void initPreparedStatement() throws SQLException {


	}

	//
	// @SuppressWarnings("unchecked")
	// private void addKmImeetingMainBatch(KmImeetingMain kmImeetingMain)
	// throws SQLException {
	//
	//
	// }
	//
	// private void updateKmImeetingMainBatch(KmImeetingMain kmImeetingMain)
	// throws SQLException {
	//
	//
	// }
	//
	// private void deleteKmImeetingMainBatch(String fdId) throws SQLException {
	// logger.debug("删除：" + fdId);
	// }
	//
	// private void addKmImeetingSyncMappingBatch(
	// KmImeetingSyncMapping kmImeetingSyncMapping) throws SQLException {
	//
	// }
	//
	// private void deleteKmImeetingSyncMappingBatch(String appKey, String uuid)
	// throws SQLException {
	//
	// }


	private void updateSynchroDate(String appKey, String personId,
			Date synchroDate) throws Exception {
		if (lastSyncroDate != null) {
			getKmImeetingSyncBindService().updateSyncroDate(personId, appKey,
					synchroDate);
			// ps_update_synchro_date.setTimestamp(1, new Timestamp(new Date()
			// .getTime()));
			// ps_update_synchro_date.setString(2, appKey);
			// ps_update_synchro_date.setString(3, personId);
			// ps_update_synchro_date.addBatch();
		} else {
			// ps_add_synchro_date.setString(1, IDGenerator.generateID());
			// ps_add_synchro_date.setString(2, appKey);
			// ps_add_synchro_date.setString(3, personId);
			// ps_add_synchro_date.setTimestamp(4, new Timestamp(new Date()
			// .getTime()));
			// ps_add_synchro_date.addBatch();

			KmImeetingSyncBind bind = new KmImeetingSyncBind();
			bind.setFdAppKey(appKey);
			bind.setFdOwner((SysOrgElement) getSysOrgPersonService()
					.findByPrimaryKey(personId));
			bind.setFdSyncTimestamp(synchroDate);
			getKmImeetingSyncBindService().add(bind);
		}
	}



	public Map<String, Object> syncro() {
		Date start = new Date();
		logJobMessage("用户" + personId + "同步开始,时间：" + DateUtil.convertDateToString(start, DateUtil.TYPE_DATETIME, null));
		try {
			init();
			initPreparedStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		IMeetingSynchroService.addSyncroingPerson(personId);
		List<String> syncOutAppKeys = new ArrayList<String>();

			for (String appKey : appKeys) {
				if (syncroIn(appKey)) {
				result.put("km.imetting.synchro.in.success", appKey);
					// result += "从 " + appKey + " 接入数据成功;"
					// + System.getProperty("line.separator");
					logJobMessage(
						"用户 " + personId + " 从 " + appKey + " 接入会议数据成功;");
					syncOutAppKeys.add(appKey);
				} else {
				result.put("km.imetting.synchro.in.failure", appKey);
					logJobMessage(
						"用户 " + personId + " 从 " + appKey + " 接入会议数据失败;");
					// result += "从 " + appKey + " 接入数据失败;"
					// + System.getProperty("line.separator");
				}
			}

		IMeetingSynchroService.removeSyncroingPerson(personId);
		if (exceptions.size() > 0) {
			result.put("exceptions", exceptions);
		}

		Date end = new Date();
		logJobMessage("用户" + personId + "同步结束,时间：" + DateUtil.convertDateToString(end, DateUtil.TYPE_DATETIME, null)
				+ "   总耗时："
				+ (end.getTime() - start.getTime()) + "ms");
		return result;
	}

	@Override
    public void run() {
		// TODO 自动生成的方法存根
		logJobMessage("同步用户：" + personId);
		syncro();
		countDownLatch.countDown();
	}

	private void upateResponse(KmImeetingMain kmImeetingMain,
			List<SynchroMeetingResponse> result)
			throws HibernateException, Exception {
		List<String> attendIds = new ArrayList<String>();
		List<String> unAttendIds = new ArrayList<String>();
		for (SynchroMeetingResponse response : result) {
			// 同意参加
			if (IMeetingResponseType.ACCEPT.equals(response
					.getResponseType())) {
				if (StringUtil.isNotNull(response.getPersonId())) {
					attendIds.add(response.getPersonId());
				}
			}
			// 不同意参加
			else if (IMeetingResponseType.DECLINE.equals(response
					.getResponseType())) {
				if (StringUtil.isNotNull(response.getPersonId())) {
					unAttendIds.add(response.getPersonId());
				}
			}
		}

		if (!attendIds.isEmpty()) {
			String inBlock = HQLUtil.buildLogicIN(
					"kmImeetingMainFeedback.docCreator.fdId", attendIds);
			Query query = getKmImeetingMainService().getBaseDao()
					.getHibernateSession()
					.createQuery(
							"update "
									+ KmImeetingMainFeedback.class
											.getName()
									+ " kmImeetingMainFeedback set kmImeetingMainFeedback.fdOperateType='01'  where kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and "
									+ inBlock);
			query.setParameter("meetingId", kmImeetingMain.getFdId());
			query.executeUpdate();
		}
		if (!unAttendIds.isEmpty()) {
			String inBlock = HQLUtil.buildLogicIN(
					"kmImeetingMainFeedback.docCreator.fdId", unAttendIds);
			Query query = getKmImeetingMainService().getBaseDao()
					.getHibernateSession()
					.createQuery(
							"update "
									+ KmImeetingMainFeedback.class
											.getName()
									+ " kmImeetingMainFeedback set kmImeetingMainFeedback.fdOperateType='02'  where kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and "
									+ inBlock);
			query.setParameter("meetingId", kmImeetingMain.getFdId());
			query.executeUpdate();
		}
	}

	private void setKmImeetingMainValue(KmImeetingMain kmImeetingMain,
			SynchroCommonMetting synchroCommonMetting) throws Exception {
		String creatorId = synchroCommonMetting.getCreatorId();
		SysOrgPerson creator = (SysOrgPerson) getSysOrgPersonService()
				.findByPrimaryKey(creatorId);
		// Date start = synchroCommonMetting.getStart();
		// Date end = synchroCommonMetting.getEnd();
		kmImeetingMain.setDocCreator(creator);
		kmImeetingMain.setFdEmcee(creator);
		kmImeetingMain.setDocStatus("30");
		kmImeetingMain.setFdChangeMeetingFlag(false);
		kmImeetingMain.setFdNeedFeedback(true);
		kmImeetingMain.setFdNotifyWay("todo");
		kmImeetingMain.setDocDept(creator.getFdParent());
		kmImeetingMain.setDocSubject(synchroCommonMetting.getSubject());
		kmImeetingMain.setFdName(synchroCommonMetting.getSubject());
		Set<String> requiredAttendeeIds = synchroCommonMetting
				.getRequiredAttendees();
		if (requiredAttendeeIds != null && requiredAttendeeIds.size() > 0) {
			List<SysOrgElement> attendPersons = new ArrayList<SysOrgElement>();
			for (String personId : requiredAttendeeIds) {
				attendPersons.add((SysOrgElement) getSysOrgPersonService()
						.findByPrimaryKey(personId));
			}
			kmImeetingMain.setFdAttendPersons(attendPersons);
		}

		kmImeetingMain.setFdFinishDate(synchroCommonMetting.getEnd());
		kmImeetingMain.setFdHoldDate(synchroCommonMetting.getStart());
		kmImeetingMain.setFdHost(creator);
		kmImeetingMain.setFdOtherPlace(synchroCommonMetting.getLocation());
		kmImeetingMain.setSynchroIn(true);
		// kmImeetingMain.setFdMeetingAim(synchroCommonMetting.getBody());

		Long holdDuration = kmImeetingMain.getFdFinishDate().getTime()
				- kmImeetingMain.getFdHoldDate().getTime();
		kmImeetingMain
				.setFdHoldDuration(holdDuration.doubleValue());
	}

	private void handleAddedMeeting(String appKey,
			SynchroCommonMetting synchroCommonMetting)
			throws Exception {
		logger.debug("增加会议：" + synchroCommonMetting.getSubject() + "---"
				+ synchroCommonMetting.getStart() + "---"
				+ synchroCommonMetting.getEnd());
		// 增加数据到日程模块
		KmImeetingMain kmImeetingMain = new KmImeetingMain();
		String calendarId = IDGenerator.generateID();
		kmImeetingMain.setFdId(calendarId);
		setKmImeetingMainValue(kmImeetingMain, synchroCommonMetting);
		Date current = new Date();
		kmImeetingMain.setDocCreateTime(current);
		kmImeetingMain.setFdLastModifiedTime(current);
		kmImeetingMain.setSysAgendaMain(new SysAgendaMain());
		kmImeetingMain.setKmImeetingAgendas(new ArrayList<KmImeetingAgenda>());
		try {
			getKmImeetingMainService().add(kmImeetingMain);
		} catch (Exception e) {
			logJobMessage(
					"增加会议失败：" + personId + " " + kmImeetingMain.getDocSubject()
					+ "    " + kmImeetingMain.getFdHoldDate() + "    "
					+ kmImeetingMain.getFdFinishDate() + "  "
					+ synchroCommonMetting.getUuid());
			throw e;
		}
		// getKmCalendarMainService().addSelf(kmCalendarMain);

		// 增加映射数据
		KmImeetingSyncMapping kmImeetingSyncMapping = new KmImeetingSyncMapping();
		kmImeetingSyncMapping.setFdId(IDGenerator.generateID());
		// kmImeetingSyncMapping.setFdAppKey(appKey);
		kmImeetingSyncMapping.setFdAppUuid(synchroCommonMetting.getUuid());
		kmImeetingSyncMapping
				.setFdAppIcalId(synchroCommonMetting.getFdAppIcalId());
		kmImeetingSyncMapping.setFdMeetingId(calendarId);

		getKmImeetingSyncMappingService().add(kmImeetingSyncMapping);
		// addKmImeetingSyncMappingBatch(kmImeetingSyncMapping);
		
		upateResponse(
				(KmImeetingMain) getKmImeetingMainService()
						.findByPrimaryKey(calendarId),
				synchroCommonMetting.getMeetingResponseList());
	}

	private void handleUpdatedMeeting(String appKey,
			SynchroCommonMetting synchroCommonMetting)
			throws Exception {
		logger.debug("更新会议：" + synchroCommonMetting.getSubject() + "---"
				+ synchroCommonMetting.getStart() + "---"
				+ synchroCommonMetting.getEnd());

		List<String> mettingIds = getKmImeetingSyncMappingService()
				.findImeetingIds(appKey, synchroCommonMetting.getUuid());
		if (mettingIds != null && mettingIds.size() > 0) {
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainService()
					.findByPrimaryKey(mettingIds.get(0), null, true);
			if (kmImeetingMain == null) {
				logger.warn("找不到对应的会议记录:" + synchroCommonMetting.getUuid());
				return;
			}
			setKmImeetingMainValue(kmImeetingMain, synchroCommonMetting);
			Date current = new Date();
			kmImeetingMain.setFdLastModifiedTime(current);
			try {
				getKmImeetingMainService().update(kmImeetingMain);
				upateResponse(kmImeetingMain,
						synchroCommonMetting.getMeetingResponseList());

			} catch (Exception e) {
				logJobMessage("更新会议失败：" + personId + " "
						+ kmImeetingMain.getDocSubject()
						+ "    " + kmImeetingMain.getFdHoldDate() + "    "
						+ kmImeetingMain.getFdFinishDate() + "  "
						+ synchroCommonMetting.getUuid());
				throw e;
			}

		} else {
			logger.warn("找不到对应的会议记录:" + synchroCommonMetting.getUuid());
		}
	}

	private void handleDeletedMeeting(String appKey,
			String uuid)
			throws Exception {
		logger.debug("删除会议：" + uuid);

		List<String> mettingIds = null;
		if ("exchangeMeeting".equals(appKey)) {
			mettingIds = getKmImeetingSyncMappingService()
					.findImeetingIdsByIcalId(appKey, uuid);
		} else {
			mettingIds = getKmImeetingSyncMappingService()
					.findImeetingIds(appKey, uuid);
		}
		if (mettingIds != null && mettingIds.size() > 0) {
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainService()
					.findByPrimaryKey(mettingIds.get(0), null, true);
			if (kmImeetingMain == null) {
				logger.warn("找不到对应的会议记录:" + mettingIds.get(0));
				return;
			}
			if (!personId.equals(kmImeetingMain.getDocCreator().getFdId())) {
				logger.debug("不是会议发起人删除，不删除会议");
				return;
			}
			kmImeetingMain.setSynchroIn(true);
			try {
				getKmImeetingMainService().delete(kmImeetingMain);
				// kmImeetingSyncMapping
				List<String> ids = getKmImeetingSyncMappingService()
						.findImeetingIds(appKey, uuid);
				String[] idsArray = new String[ids.size()];
				ids.toArray(idsArray);
				getKmImeetingSyncMappingService()
						.delete(idsArray);
				ids.toArray();
			} catch (Exception e) {
				logJobMessage("删除会议失败：" + personId + " "
						+ kmImeetingMain.getDocSubject()
						+ "    " + kmImeetingMain.getFdHoldDate() + "    "
						+ kmImeetingMain.getFdFinishDate() + "  "
						+ uuid);
			}
		} else {
			logger.warn("找不到对应的会议记录:" + uuid);
		}
	}

	private boolean syncroIn(String appKey) {
		Date current = new Date();
		Date syncroTimeStamp = null;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		boolean syncInSuccess = true;
		ImeetingSynchroPluginData data = SynchroPlugin.getPluginData(appKey);
		try {
			lastSyncroDate = getKmImeetingSyncBindService().getSyncroDate(
					personId, appKey);
		} catch (Exception e2) {
			logger.error("",e2);
			logJobMessage("用户 " + personId + " 从 " + appKey + " 接入会议数据失败;---"
					+ e2.getMessage());
			return false;
		}
		// 超过1个月没同步，只同步1个月的数据
		if (lastSyncroDate == null || c.getTime().after(lastSyncroDate)) {
			syncroTimeStamp = c.getTime();
		} else {
			syncroTimeStamp = lastSyncroDate;
		}
		IMeetingSynchroProvider provider = data.getProvider();
		try {
			provider = provider.getNewInstance(personId);
		} catch (Exception e1) {
			e1.printStackTrace();
			logJobMessage("用户 " + personId + " 从 " + appKey + " 接入数据失败;---"
					+ e1.getMessage());
			exceptions.add(e1);
			return false;
		}
		if (provider == null) {
			logJobMessage("provider为NULL，同步无法执行，用户ID为:" + personId);
			return false;
		}

		// 如果是第一次同步
		if (lastSyncroDate == null) {
			TransactionStatus syncroIn = TransactionUtils.beginNewTransaction();
			try {
				List<SynchroCommonMetting> commonMeetings = provider
						.getMeetings(personId, syncroTimeStamp);
				for (SynchroCommonMetting commonMetting : commonMeetings) {
					handleAddedMeeting(appKey, commonMetting);
				}
				TransactionUtils.getTransactionManager().commit(syncroIn);
			} catch (Exception e) {
				e.printStackTrace();
				logJobError("用户 " + personId + " 从 " + appKey + " 接入数据失败;", e);
				exceptions.add(e);
				syncInSuccess = false;
				TransactionUtils.getTransactionManager().rollback(syncroIn);
			} finally {

			}
		} else {
			TransactionStatus syncroIn = TransactionUtils.beginNewTransaction();
			try {
				List<SynchroCommonMetting> syncroCommonMeetings_updated = provider
						.getMeetings(personId, syncroTimeStamp);
				List<String> syncroCommonMeetings_deleted = provider
						.getDeletedMeetings(personId, syncroTimeStamp);
				if (syncroCommonMeetings_updated != null) {
					for (SynchroCommonMetting syncroCommonMeeting : syncroCommonMeetings_updated) {
						List<String> calendarIds = getKmImeetingSyncMappingService()
								.findImeetingIds(appKey,
										syncroCommonMeeting.getUuid());
						if (calendarIds != null && calendarIds.size() > 0) {
							handleUpdatedMeeting(appKey, syncroCommonMeeting);
						} else {
							handleAddedMeeting(appKey, syncroCommonMeeting);
						}
					}
				}

				if (syncroCommonMeetings_deleted != null) {
					for (String uuid : syncroCommonMeetings_deleted) {
						logger.debug("接入删除数据："
								+ uuid);
						handleDeletedMeeting(appKey, uuid);
					}
				}
				TransactionUtils.getTransactionManager().commit(syncroIn);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logJobError("用户 " + personId + " 从 " + appKey + " 接入数据失败;", e);
				exceptions.add(e);
				syncInSuccess = false;
				TransactionUtils.getTransactionManager().rollback(syncroIn);
			}
		}
		if (syncInSuccess) {
			// 更新同步时间戳
			try {
				updateSynchroDate(appKey, personId, current);
				logger.info("用户 " + personId + " 从应用  " + appKey
						+ " 接入会议数据成功，耗时："
						+ (new Date().getTime() - current.getTime()) + "ms");
			} catch (Exception e) {
				logger.error("用户 " + personId + "," + " 从应用  " + appKey
						+ " 接入会议数据失败。");
				logJobError("用户 " + personId + " 从 " + appKey + " 接入会议数据失败;",
						e);
				exceptions.add(e);
				e.printStackTrace();
			}

		} else {
			logger.error("用户 " + personId + " 从应用  " + appKey + " 接入会议数据失败。");
			// TransactionUtils.getTransactionManager().rollback(syncroIn);
		}
		return syncInSuccess;
	}


	private void logJobMessage(String message) {
		if (sysQuartzJobContext != null) {
			sysQuartzJobContext.logMessage(message);
		}
	}

	private void logJobError(String message, Throwable e) {
		if (sysQuartzJobContext != null) {
			sysQuartzJobContext.logError(message, e);
		}
	}

}
