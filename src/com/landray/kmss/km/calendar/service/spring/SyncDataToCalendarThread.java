package com.landray.kmss.km.calendar.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.landray.kmss.km.calendar.cms.CMSPlugin;
import com.landray.kmss.km.calendar.cms.CMSPluginData;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarOutCache;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarOutCacheService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SyncDataToCalendarThread extends Thread {

	private String modelName;
	private String docId;
	private List<String> ownerIdList;

	private IKmCalendarSyncMappingService kmCalendarSyncMappingService;

	public IKmCalendarSyncMappingService getKmCalendarSyncMappingService() {
		if (kmCalendarSyncMappingService == null) {
			kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil
					.getBean("kmCalendarSyncMappingService");
		}
		return kmCalendarSyncMappingService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private IKmCalendarMainService kmCalendarMainService;

	public IKmCalendarMainService getKmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
					.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	private IKmCalendarOutCacheService kmCalendarOutCacheService;

	public IKmCalendarOutCacheService getKmCalendarOutCacheService() {
		if (kmCalendarOutCacheService == null) {
			kmCalendarOutCacheService = (IKmCalendarOutCacheService) SpringBeanUtil
					.getBean("kmCalendarOutCacheService");
		}
		return kmCalendarOutCacheService;
	}

	public SyncDataToCalendarThread(String modelName, String docId,
			List<String> ownerIdList, String method) {
		this.modelName = modelName;
		this.docId = docId;
		this.ownerIdList = ownerIdList;
	}

	private static String sql_add_KmCalendarOutCache = "insert into km_calendar_out_cache(fd_id,fd_calendar_id,fd_app_key,fd_app_uuid,fd_operation_type,fd_operation_date,fd_owner_id) values(?,?,?,?,?,?,?)";

	private static String sql_update_KmCalendarOutCache = "update km_calendar_out_cache set fd_calendar_id=?,fd_app_key=?,fd_app_uuid=?,fd_operation_type=?,fd_operation_date=?,fd_owner_id=? where fd_id = ?";

	private static String sql_delete_kmCalendarMain = "delete from km_calendar_main where fd_id = ?";

	private static String sql_delete_KmCalendarSyncMapping = "delete from km_calendar_sync_mapping where fd_calendar_id=?";

	private static String sql_delete_KmCalendarOutCache = "delete from km_calendar_out_cache where fd_calendar_id=? and fd_app_key=?";

	private static String sql_delete_KmCalendarOutCache2 = "delete from km_calendar_out_cache where fd_id=?";

	private static String sql_delete_areader = "delete from km_calendar_main_auth_areader where fd_doc_id = ?";

	private static String sql_delete_aeditor = "delete from km_calendar_main_auth_aeditor where fd_doc_id = ?";

	private static String sql_delete_readers = "delete from km_calendar_main_auth_readers where fd_doc_id = ?";

	private static String sql_delete_editors = "delete from km_calendar_main_auth_editors where fd_doc_id = ?";

	private static String sql_delete_quartz_job = "delete from sys_quartz_job where fd_model_name = 'com.landray.kmss.km.calendar.model.KmCalendarMain' and fd_model_id = ?";

	private static String sql_delete_sys_notify_todo = "delete from sys_notify_todo where fd_model_name = 'com.landray.kmss.km.calendar.model.KmCalendarMain' and fd_model_id = ?";

	private static String sql_delete_sysNotifyRemindMain = "delete from sys_notify_remind_main where fd_model_id=?";

	private static String sql_delete_sysNotifyRemindCommon = "delete from sys_notify_remind_common where fd_model_id=?";

	private Connection conn = null;

	private PreparedStatement ps_add_KmCalendarOutCache;

	private PreparedStatement ps_update_KmCalendarOutCache;

	private PreparedStatement ps_delete_kmCalendarMain;

	private PreparedStatement ps_delete_KmCalendarSyncMapping;

	private PreparedStatement ps_delete_KmCalendarOutCache;

	private PreparedStatement ps_delete_KmCalendarOutCache2;

	private PreparedStatement ps_delete_areader;

	private PreparedStatement ps_delete_aeditor;

	private PreparedStatement ps_delete_readers;

	private PreparedStatement ps_delete_editors;

	private PreparedStatement ps_delete_quartz_job;

	private PreparedStatement ps_delete_sys_notify_todo;

	private PreparedStatement ps_delete_sysNotifyRemindMain;

	private PreparedStatement ps_delete_sysNotifyRemindCommon;

	private void init() throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		ps_add_KmCalendarOutCache = conn
				.prepareStatement(sql_add_KmCalendarOutCache);
		ps_update_KmCalendarOutCache = conn
				.prepareStatement(sql_update_KmCalendarOutCache);
		ps_delete_kmCalendarMain = conn
				.prepareStatement(sql_delete_kmCalendarMain);
		ps_delete_KmCalendarSyncMapping = conn
				.prepareStatement(sql_delete_KmCalendarSyncMapping);
		ps_delete_KmCalendarOutCache = conn
				.prepareStatement(sql_delete_KmCalendarOutCache);
		ps_delete_KmCalendarOutCache2 = conn
				.prepareStatement(sql_delete_KmCalendarOutCache2);
		ps_delete_areader = conn.prepareStatement(sql_delete_areader);
		ps_delete_aeditor = conn.prepareStatement(sql_delete_aeditor);
		ps_delete_readers = conn.prepareStatement(sql_delete_readers);
		ps_delete_editors = conn.prepareStatement(sql_delete_editors);
		ps_delete_quartz_job = conn.prepareStatement(sql_delete_quartz_job);
		ps_delete_sys_notify_todo = conn
				.prepareStatement(sql_delete_sys_notify_todo);
		ps_delete_sysNotifyRemindMain = conn
				.prepareStatement(sql_delete_sysNotifyRemindMain);
		ps_delete_sysNotifyRemindCommon = conn
				.prepareStatement(sql_delete_sysNotifyRemindCommon);
	}

	private void commit() throws SQLException {
		try {
			ps_add_KmCalendarOutCache.executeBatch();
			ps_update_KmCalendarOutCache.executeBatch();
			ps_delete_KmCalendarSyncMapping.executeBatch();
			ps_delete_KmCalendarOutCache.executeBatch();
			ps_delete_KmCalendarOutCache2.executeBatch();
			ps_delete_aeditor.executeBatch();
			ps_delete_areader.executeBatch();
			ps_delete_editors.executeBatch();
			ps_delete_readers.executeBatch();
			ps_delete_quartz_job.executeBatch();
			ps_delete_sys_notify_todo.executeBatch();
			ps_delete_sysNotifyRemindMain.executeBatch();
			ps_delete_sysNotifyRemindCommon.executeBatch();
			ps_delete_kmCalendarMain.executeBatch();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		}
	}

	@Override
	public void run() {
		try {
			init();
			delete();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteKmCalendarOutCacheBatch(String fdId)
			throws SQLException {
		ps_delete_KmCalendarOutCache2.setString(1, fdId);
		ps_delete_KmCalendarOutCache2.addBatch();
	}

	private void deleteKmCalendarOutCacheBatch(String appKey,
			String calendarId)
			throws SQLException {
		ps_delete_KmCalendarOutCache.setString(1, calendarId);
		ps_delete_KmCalendarOutCache.setString(2, appKey);
		ps_delete_KmCalendarOutCache.addBatch();
	}

	private void addKmCalendarOutCacheBatch(
			KmCalendarOutCache kmCalendarOutCache) throws SQLException {
		ps_add_KmCalendarOutCache.setString(1,
				kmCalendarOutCache.getFdId());
		ps_add_KmCalendarOutCache.setString(2, kmCalendarOutCache
				.getFdCalendarId());
		ps_add_KmCalendarOutCache
				.setString(3, kmCalendarOutCache.getFdAppKey());
		ps_add_KmCalendarOutCache.setString(4, kmCalendarOutCache
				.getFdAppUuid());
		ps_add_KmCalendarOutCache.setString(5, kmCalendarOutCache
				.getFdOperationType());
		ps_add_KmCalendarOutCache.setTimestamp(6, new Timestamp(
				kmCalendarOutCache.getFdOperationDate().getTime()));
		ps_add_KmCalendarOutCache.setString(7,
				kmCalendarOutCache.getFdOwner()
						.getFdId());
		ps_add_KmCalendarOutCache.addBatch();

	}

	private void updateKmCalendarOutCacheBatch(
			KmCalendarOutCache kmCalendarOutCache) throws SQLException {
		ps_update_KmCalendarOutCache.setString(1, kmCalendarOutCache
				.getFdCalendarId());
		ps_update_KmCalendarOutCache.setString(2, kmCalendarOutCache
				.getFdAppKey());
		ps_update_KmCalendarOutCache.setString(3, kmCalendarOutCache
				.getFdAppUuid());
		ps_update_KmCalendarOutCache.setString(4, kmCalendarOutCache
				.getFdOperationType());
		ps_update_KmCalendarOutCache.setTimestamp(5, new Timestamp(
				kmCalendarOutCache.getFdOperationDate().getTime()));
		ps_update_KmCalendarOutCache.setString(6, kmCalendarOutCache
				.getFdOwner().getFdId());
		ps_update_KmCalendarOutCache.setString(7,
				kmCalendarOutCache.getFdId());
		ps_update_KmCalendarOutCache.addBatch();
	}

	private void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
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
								.equals(kmCalendarMain
										.getFdRecurrenceStr())) {
					continue;
				}
				if (appKey.equals(operationAppKey)) {
					// 删除对应的cache记录
					if (!KmCalendarConstant.OPERATION_TYPE_ADD
							.equals(operationType)) {
						deleteKmCalendarOutCacheBatch(appKey, kmCalendarMain
								.getFdId());
					}
				} else {
					KmCalendarOutCache cache = getKmCalendarOutCacheService()
							.findByCalendarIdAndAppKey(
									kmCalendarMain.getFdId(), appKey);

					// 如果接出表里面没有找到对应记录，新增一条记录
					if (cache == null) {
						String appUuid = getKmCalendarSyncMappingService()
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
							cache.setFdOwner(getSysOrgCoreService()
									.findByPrimaryKey(personId));
							// add(cache);
							addKmCalendarOutCacheBatch(cache);
						}
					} else {
						String lastOperationType = cache
								.getFdOperationType();
						cache.setFdOperationDate(kmCalendarMain
								.getDocAlterTime());
						// 上次操作是新增
						if (KmCalendarConstant.OPERATION_TYPE_ADD
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								// this.update(cache);
								updateKmCalendarOutCacheBatch(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								// this.delete(cache);
								deleteKmCalendarOutCacheBatch(
										cache.getFdId());
							}
						}
						// 上次操作是更新
						else if (KmCalendarConstant.OPERATION_TYPE_UPDATE
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								// this.update(cache);
								updateKmCalendarOutCacheBatch(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								cache.setFdOperationType(operationType);
								// this.update(cache);
								updateKmCalendarOutCacheBatch(cache);
							}
						}
						// 上次操作是删除
						else if (KmCalendarConstant.OPERATION_TYPE_DELETE
								.equals(lastOperationType)) {
							if (KmCalendarConstant.OPERATION_TYPE_UPDATE
									.equals(operationType)) {
								cache.setFdOperationType(operationType);
								// this.update(cache);
								updateKmCalendarOutCacheBatch(cache);
							} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
									.equals(operationType)) {
								// this.update(cache);
								updateKmCalendarOutCacheBatch(cache);
							}
						}
					}
				}
			}
		}
	}

	private void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
			String operationType, String personId, String operationAppKey)
			throws Exception {
		List<CMSPluginData> plugins = CMSPlugin.getExtensionList();
		List<String> appKeys = new ArrayList<String>();
		String appKey;
		for (CMSPluginData pluginData : plugins) {
			ICMSProvider provider = pluginData.getCmsProvider();
			if (!provider.isSynchroEnable()) {
				continue;
			}
			appKey = pluginData.getAppKey();
			if (pluginData.getCmsProvider()
					.isNeedSyncro(personId)) {
				appKeys.add(appKey);
			}
		}
		updateCalendarOutCaches(kmCalendarMain, operationType, personId,
				operationAppKey, appKeys);
	}

	private void delete() throws Exception {
		try {
			List<KmCalendarMain> kmCalendarMainList = getKmCalendarMainService()
					.findCalendars(modelName, docId, ownerIdList);
			for (KmCalendarMain kmCalendarMain : kmCalendarMainList) {
				// 更新接出cache
				updateCalendarOutCaches(kmCalendarMain,
						KmCalendarConstant.OPERATION_TYPE_DELETE,
						kmCalendarMain.getDocOwner().getFdId(), null);
				// 删除映射表
				ps_delete_KmCalendarSyncMapping.setString(1,
						kmCalendarMain.getFdId());
				ps_delete_KmCalendarSyncMapping.addBatch();
				// 删除提醒定时任务
				ps_delete_quartz_job.setString(1, kmCalendarMain.getFdId());
				ps_delete_quartz_job.addBatch();
				// 删除发出去的待办
				ps_delete_sys_notify_todo.setString(1,
						kmCalendarMain.getFdId());
				ps_delete_sys_notify_todo.addBatch();
				ps_delete_sysNotifyRemindCommon.setString(1,
						kmCalendarMain.getFdId());
				ps_delete_sysNotifyRemindCommon.addBatch();
				// 删除主表
				ps_delete_aeditor.setString(1, kmCalendarMain.getFdId());
				ps_delete_aeditor.addBatch();
				ps_delete_areader.setString(1, kmCalendarMain.getFdId());
				ps_delete_areader.addBatch();
				ps_delete_editors.setString(1, kmCalendarMain.getFdId());
				ps_delete_editors.addBatch();
				ps_delete_readers.setString(1, kmCalendarMain.getFdId());
				ps_delete_readers.addBatch();
				ps_delete_sysNotifyRemindMain.setString(1,
						kmCalendarMain.getFdId());
				ps_delete_sysNotifyRemindMain.addBatch();
				ps_delete_kmCalendarMain.setString(1,
						kmCalendarMain.getFdId());
				ps_delete_kmCalendarMain.addBatch();
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
