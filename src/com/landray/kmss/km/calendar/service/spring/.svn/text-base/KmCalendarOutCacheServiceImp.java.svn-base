package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventAsyncCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.km.calendar.cms.CMSPlugin;
import com.landray.kmss.km.calendar.cms.CMSPluginData;
import com.landray.kmss.km.calendar.cms.CMSThreadPoolManager;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.dao.IKmCalendarOutCacheDao;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarOutCache;
import com.landray.kmss.km.calendar.service.IKmCalendarOutCacheService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

/**
 * 日程接出缓存业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarOutCacheServiceImp extends BaseServiceImp implements
		IKmCalendarOutCacheService, IEventMulticasterAware {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private IKmCalendarSyncMappingService kmCalendarSyncMappingService;

	public void setKmCalendarSyncMappingService(
			IKmCalendarSyncMappingService kmCalendarSyncMappingService) {
		this.kmCalendarSyncMappingService = kmCalendarSyncMappingService;
	}

	private IEventMulticaster multicaster;

	@Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	@Override
    public void deleteByAppKeyAndUuid(String appKey, String uuid)
			throws Exception {
		((IKmCalendarOutCacheDao) getBaseDao()).deleteByAppKeyAndUuid(appKey,
				uuid);
	}

	@Override
    @SuppressWarnings("unchecked")
	public KmCalendarOutCache findByCalendarIdAndAppKey(String calendarId,
			String appKey) throws Exception {
		HQLInfo info = new HQLInfo();
		info
				.setWhereBlock("kmCalendarOutCache.fdCalendarId=:fdCalendarId and fdAppKey=:appKey");
		info.setParameter("fdCalendarId", calendarId);
		info.setParameter("appKey", appKey);
		List list = findList(info);
		return (list != null && list.size() > 0) ? (KmCalendarOutCache) list
				.get(0) : null;
	}

	@Override
    public KmCalendarOutCache findByAppUuidAndAppKey(String appUuid,
                                                     String appKey) throws Exception {
		HQLInfo info = new HQLInfo();
		info
				.setWhereBlock("kmCalendarOutCache.fdAppUuid=:appUuid and fdAppKey=:appKey");
		info.setParameter("appUuid", appUuid);
		info.setParameter("appKey", appKey);
		List list = findList(info);
		return (list != null && list.size() > 0) ? (KmCalendarOutCache) list
				.get(0) : null;
	}

	@Override
    public void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
                                        String operationType, String personId, String operationAppKey,
                                        List<String> appKeys) throws Exception {
		for (String appKey : appKeys) {
			CMSPluginData data = CMSPlugin.getCMSPluginData(appKey);
			if (data.getCmsProvider().getCalType().equals(
					kmCalendarMain.getFdType())) {
				// 如果日程设置了农历重复，则不接出到google日历
				if ("gcalendar".equals(appKey)
						&& kmCalendarMain.getFdIsLunar()
						&& kmCalendarMain.getFdRecurrenceStr() != null
						&& KmCalendarConstant.RECURRENCE_FREQ_NO
								.equals(kmCalendarMain.getFdRecurrenceStr())) {
					continue;
				}
				if (appKey.equals(operationAppKey)) {
					// 删除对应的cache记录
					if (!KmCalendarConstant.OPERATION_TYPE_ADD
							.equals(operationType)) {
						KmCalendarOutCache cache = findByCalendarIdAndAppKey(
								kmCalendarMain.getFdId(), appKey);
						if (cache != null) {
							delete(cache);
						}
					}
				} else {
					if (data.getSyncNow()) {
						syncCalendarNow(kmCalendarMain, operationType, data.getAppKey());
						return;
					}
					KmCalendarOutCache cache = findByCalendarIdAndAppKey(
							kmCalendarMain.getFdId(), appKey);

					// 如果接出表里面没有找到对应记录，新增一条记录
					if (cache == null) {
						String appUuid = kmCalendarSyncMappingService
								.getAppUuid(appKey, kmCalendarMain.getFdId());
						if (KmCalendarConstant.OPERATION_TYPE_ADD
								.equals(operationType)
								|| StringUtil.isNotNull(appUuid)) {
							cache = new KmCalendarOutCache();
							cache.setFdAppKey(appKey);
							cache.setFdCalendarId(kmCalendarMain.getFdId());
							cache.setFdId(IDGenerator.generateID());
							cache.setFdOperationDate(kmCalendarMain
									.getDocAlterTime());
							cache.setFdOperationType(operationType);
							cache.setFdAppUuid(appUuid);
							cache.setFdOwner(sysOrgCoreService
									.findByPrimaryKey(personId));
							add(cache);
						}
					} else {
						String lastOperationType = cache.getFdOperationType();
						cache.setFdOperationDate(kmCalendarMain
								.getDocAlterTime());
						// 上次操作是新增
						if (KmCalendarConstant.OPERATION_TYPE_ADD
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								this.update(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								this.delete(cache);
							}
						}
						// 上次操作是更新
						else if (KmCalendarConstant.OPERATION_TYPE_UPDATE
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								this.update(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								cache.setFdOperationType(operationType);
								this.update(cache);
							}
						}
						// 上次操作是删除
						else if (KmCalendarConstant.OPERATION_TYPE_DELETE
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								cache.setFdOperationType(operationType);
								this.update(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								this.update(cache);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 处理接出记录，当一条日程记录更新(新增、更新、删除)时，需更新接出到各个app应用的数据
	 * 
	 * @param kmCalendarMain
	 * @param operationType
	 * @param personId
	 * @param operationAppKey
	 *            更新了日程记录的对应的app应用，如果是在日程管理中手动修改记录，该参数值设置成null
	 * @throws Exception
	 */
	@Override
    public void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
                                        String operationType, String personId, String operationAppKey)
			throws Exception {
		String synchroDirect = "";
		try {
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			synchroDirect = config.getSynchroDirect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("toEkp".equals(synchroDirect)) {
			return;
		}
		List<CMSPluginData> plugins = CMSPlugin.getExtensionList();
		List<String> appKeys = new ArrayList<String>();
		String appKey;
		for (CMSPluginData pluginData : plugins) {
			ICMSProvider provider = pluginData.getCmsProvider();
			if(!provider.isSynchroEnable()){
				continue;
			}
			appKey = pluginData.getAppKey();
			if (pluginData.getCmsProvider().isNeedSyncro(personId)) {
				appKeys.add(appKey);
			}
		}
		updateCalendarOutCaches(kmCalendarMain, operationType, personId,
				operationAppKey, appKeys);
	}

	private void syncCalendarNow(KmCalendarMain kmCalendarMain, String operationType, String operationAppKey)
			throws Exception {
		ICMSProvider provider = null;
		List<CMSPluginData> plugins = CMSPlugin.getExtensionList();
		for (CMSPluginData plugin : plugins) {
			if (plugin.getAppKey().equals(operationAppKey)) {
				provider = plugin.getCmsProvider();
				break;
			}
		}
		if (provider != null) {
			KmCalendarOutCache cache = findByCalendarIdAndAppKey(kmCalendarMain.getFdId(), operationAppKey);
			String uuid = cache != null ? cache.getFdAppUuid() : null;
			String personId = null;
			if (kmCalendarMain.getDocOwner() != null) {
				personId = kmCalendarMain.getDocOwner().getFdId();
			} else if (kmCalendarMain.getDocCreator() != null) {
				personId = kmCalendarMain.getDocCreator().getFdId();
			}
			SyncroCommonCal syncroCommonCal = null;

			if (KmCalendarConstant.OPERATION_TYPE_ADD.equals(operationType)
					|| KmCalendarConstant.OPERATION_TYPE_UPDATE.equals(operationType)) {
				syncroCommonCal = buildSyncroCommonCal(kmCalendarMain, uuid, operationAppKey);
			} else if (KmCalendarConstant.OPERATION_TYPE_DELETE.equals(operationType)) {
				syncroCommonCal = new SyncroCommonCal();
				syncroCommonCal.setCalendarId(kmCalendarMain.getFdId());
			}

			SyncNowThread thread = new SyncNowThread(provider, personId, syncroCommonCal, operationType);
			multicaster.attatchEvent(new EventOfTransactionCommit(thread), new IEventAsyncCallBack() {
				@Override
				public void execute(ApplicationEvent event) throws Throwable {
					Object obj = event.getSource();
					if (obj instanceof SyncNowThread) {
						SyncNowThread t = (SyncNowThread) obj;
						CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
						if (!manager.isStarted()) {
							manager.start();
						}
						manager.submit(t);
					}
				}
			});
		}
	}

	private SyncroCommonCal buildSyncroCommonCal(KmCalendarMain kmCalendarMain, String uuId, String appKey) {
		SyncroCommonCal syncroCommonCal = new SyncroCommonCal();
		syncroCommonCal.setAppKey(appKey);
		syncroCommonCal.setUuid(uuId);
		syncroCommonCal.setCalendarId(kmCalendarMain.getFdId());
		if (kmCalendarMain.getDocLabel() != null) {
			syncroCommonCal.setLabelId(kmCalendarMain.getDocLabel().getFdId());
		}
		syncroCommonCal.setSubject(kmCalendarMain.getDocSubject());
		syncroCommonCal.setContent(kmCalendarMain.getDocContent());
		syncroCommonCal.setAllDayEvent(kmCalendarMain.getFdIsAlldayevent());
		syncroCommonCal.setEventStartTime(kmCalendarMain.getDocStartTime());
		syncroCommonCal.setEventFinishTime(kmCalendarMain.getDocFinishTime());
		if (kmCalendarMain.getDocOwner() != null) {
			syncroCommonCal.setPersonId(kmCalendarMain.getDocOwner().getFdId());
		}
		if (kmCalendarMain.getDocCreator() != null) {
			syncroCommonCal.setCreatorId(kmCalendarMain.getDocCreator().getFdId());
		}
		syncroCommonCal.setCreateTime(kmCalendarMain.getDocCreateTime());
		syncroCommonCal.setUpdateTime(kmCalendarMain.getDocAlterTime());
		syncroCommonCal.setEventLocation(kmCalendarMain.getFdLocation());
		syncroCommonCal.setRelationUrl(kmCalendarMain.getFdRelationUrl());
		syncroCommonCal.setLunar(kmCalendarMain.getFdIsLunar());
		syncroCommonCal.setRecurrentStr(kmCalendarMain.getFdRecurrenceStr());
		syncroCommonCal.setIsShared(kmCalendarMain.getIsShared());
		syncroCommonCal.setCreatedFrom(kmCalendarMain.getCreatedFrom());
		syncroCommonCal.setFdAuthorityType(kmCalendarMain.getFdAuthorityType());
		syncroCommonCal.setCalType(kmCalendarMain.getFdType());
		syncroCommonCal.setAuthReaders(kmCalendarMain.getAuthAllReaders());
		syncroCommonCal.setAuthEditors(kmCalendarMain.getAuthAllEditors());
		return syncroCommonCal;
	}


	@Override
    @SuppressWarnings("unchecked")
	public List<KmCalendarOutCache> listByPersonAndApp(String personId,
			String appKey) throws Exception {
		HQLInfo info = new HQLInfo();
		info
				.setWhereBlock("kmCalendarOutCache.fdOwner.fdId =:personId and kmCalendarOutCache.fdAppKey=:fdAppKey");
		info.setParameter("personId", personId);
		info.setParameter("fdAppKey", appKey);
		List list = findList(info);
		return list;
	}

	class SyncNowThread extends Thread {

		private ICMSProvider provider;
		private String personId;
		private SyncroCommonCal syncroCommonCal;
		private String operationType;

		public SyncNowThread(ICMSProvider provider, String personId, SyncroCommonCal syncroCommonCal,
				String operationType) {
			this.provider = provider;
			this.personId = personId;
			this.syncroCommonCal = syncroCommonCal;
			this.operationType = operationType;
		}

		@Override
		public void run() {
			try {
				if (KmCalendarConstant.OPERATION_TYPE_ADD.equals(operationType)) {
					provider.addCalElement(personId, syncroCommonCal);
				} else if (KmCalendarConstant.OPERATION_TYPE_UPDATE.equals(operationType)) {
					provider.updateCalElement(personId, syncroCommonCal);
				} else if (KmCalendarConstant.OPERATION_TYPE_DELETE.equals(operationType)) {
					// 立即同步的情况下不会在接出表有数据，此时uuId取ekpId，第三方模块拿到ekpId自行处理
					provider.deleteCalElement(personId, syncroCommonCal.getCalendarId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
