package com.landray.kmss.km.calendar.cms;

import com.google.common.util.concurrent.RateLimiter;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarOutCache;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarOutCacheService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncBindService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.spring.SysAttachmentCoreOuterService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CMSSynchroThread implements Runnable {

	private Connection conn = null;

	//添加日程限速器
	private static final RateLimiter addCalElementRateLimiter = RateLimiter.create(25.0);
	//删除日程限速器
	private static final RateLimiter delCalElementRateLimiter = RateLimiter.create(25.0);
	//更新日程限速器
	private static final RateLimiter updateCalElementRateLimiter = RateLimiter.create(25.0);

	private static String sql_add_kmCalendarMain = "insert into km_calendar_main("
			+ "fd_id,doc_subject,doc_content,doc_create_time,doc_alter_time,doc_start_time,doc_finish_time,fd_is_AllDayEvent,fd_recurrence_str,fd_is_lunar,"
			+ "fd_authority_type,fd_location,fd_relation_url,fd_type,doc_creator_id,doc_owner_id,fd_recurrence_last_start,fd_recurrence_last_end,fd_compatible_state) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static String sql_add_KmCalendarRelPerson ="insert into km_calendar_rel_person(fd_source_id,fd_target_id) values(?,?)";

    private static String sql_add_KmRelPersonDetails ="insert into km_calendar_details(fd_id,fd_is_delete,fd_calendar_id,fd_person_id) values(?,?,?,?)";

    private static String sql_update_kmCalendarMain = "update km_calendar_main set doc_subject=?,doc_content=?,doc_alter_time=?,doc_start_time=?,doc_finish_time=?,fd_is_AllDayEvent=?,fd_recurrence_str=?,fd_is_lunar=?,fd_location=?,fd_relation_url=?,fd_type=?,fd_recurrence_last_start=?,fd_recurrence_last_end=? where fd_id=?";

	private static String sql_delete_kmCalendarMain = "delete from km_calendar_main where fd_id = ?";

	private static String sql_add_KmCalendarSyncMapping = "insert into km_calendar_sync_mapping(fd_id,fd_calendar_id,fd_app_key,fd_app_uuid) values(?,?,?,?)";

	private static String sql_delete_KmCalendarSyncMapping = "delete from km_calendar_sync_mapping where fd_app_key = ? and fd_app_uuid =?";

	private static String sql_add_KmCalendarOutCache = "insert into km_calendar_out_cache(fd_id,fd_calendar_id,fd_app_key,fd_app_uuid,fd_operation_type,fd_operation_date,fd_owner_id) values(?,?,?,?,?,?,?)";

	private static String sql_update_KmCalendarOutCache = "update km_calendar_out_cache set fd_calendar_id=?,fd_app_key=?,fd_app_uuid=?,fd_operation_type=?,fd_operation_date=?,fd_owner_id=? where fd_id = ?";

	private static String sql_delete_KmCalendarOutCache = "delete from km_calendar_out_cache where fd_calendar_id=? and fd_app_key=?";

	private static String sql_delete_KmCalendarOutCache2 = "delete from km_calendar_out_cache where fd_id=?";

	private static String sql_add_synchro_date = "insert into km_calendar_sync_bind(fd_id,fd_app_key,fd_owner_id,fd_sync_timestamp) values(?,?,?,?)";

	private static String sql_update_synchro_date = "update km_calendar_sync_bind set fd_sync_timestamp=? where fd_app_key=? and fd_owner_id=?";

	private static String sql_add_reader = "insert into km_calendar_main_auth_readers(fd_doc_id,fd_org_id) values (?,?)";

	private static String sql_add_editor = "insert into km_calendar_main_auth_editors(fd_doc_id,fd_org_id) values (?,?)";

	private static String sql_add_areader = "insert into km_calendar_main_auth_areader(fd_doc_id,auth_reader_id) values (?,?)";

	private static String sql_add_aeditor = "insert into km_calendar_main_auth_aeditor(fd_doc_id,auth_editor_id) values (?,?)";

	private static String sql_delete_areader = "delete from km_calendar_main_auth_areader where fd_doc_id = ?";

	private static String sql_delete_aeditor = "delete from km_calendar_main_auth_aeditor where fd_doc_id = ?";

	private static String sql_delete_readers = "delete from km_calendar_main_auth_readers where fd_doc_id = ?";

	private static String sql_delete_editors = "delete from km_calendar_main_auth_editors where fd_doc_id = ?";

	private static String sql_delete_quartz_job = "delete from sys_quartz_job where fd_model_name = 'com.landray.kmss.km.calendar.model.KmCalendarMain' and fd_model_id = ?";

	private PreparedStatement ps_add_kmCalendarMain;

	private PreparedStatement ps_update_kmCalendarMain;

	private PreparedStatement ps_delete_kmCalendarMain;

	private PreparedStatement ps_add_KmCalendarSyncMapping;

	private PreparedStatement ps_delete_KmCalendarSyncMapping;

	private PreparedStatement ps_add_KmCalendarOutCache;

	private PreparedStatement ps_update_KmCalendarOutCache;

	private PreparedStatement ps_delete_KmCalendarOutCache;

	private PreparedStatement ps_delete_KmCalendarOutCache2;

	private PreparedStatement ps_add_synchro_date;

	private PreparedStatement ps_update_synchro_date;

    private PreparedStatement ps_add_releatedPerson;

    private PreparedStatement ps_add_relPersonDetails;

    private PreparedStatement ps_add_reader;

	private PreparedStatement ps_add_editor;

	private PreparedStatement ps_add_areader;

	private PreparedStatement ps_add_aeditor;

	private PreparedStatement ps_delete_areader;

	private PreparedStatement ps_delete_aeditor;

	private PreparedStatement ps_delete_readers;

	private PreparedStatement ps_delete_editors;

	private PreparedStatement ps_delete_quartz_job;

	private SysQuartzJobContext sysQuartzJobContext;

	private CountDownLatch countDownLatch;

	private Date lastSyncroDate = null;

	private String synchroDirect = null;

	private IKmCalendarOutCacheService kmCalendarOutCacheService;

	private KmCalendarAuth kmCalendarAuth = null;

	public IKmCalendarOutCacheService getKmCalendarOutCacheService() {
		if (kmCalendarOutCacheService == null) {
			kmCalendarOutCacheService = (IKmCalendarOutCacheService) SpringBeanUtil
					.getBean("kmCalendarOutCacheService");
		}
		return kmCalendarOutCacheService;
	}

	private IKmCalendarMainService kmCalendarMainService;

	public IKmCalendarMainService getKmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
					.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	private IKmCalendarSyncMappingService kmCalendarSyncMappingService;

	public IKmCalendarSyncMappingService getKmCalendarSyncMappingService() {
		if (kmCalendarSyncMappingService == null) {
			kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil
					.getBean("kmCalendarSyncMappingService");
		}
		return kmCalendarSyncMappingService;
	}

	private IKmCalendarSyncBindService kmCalendarSyncBindService;

	public IKmCalendarSyncBindService getKmCalendarSyncBindService() {
		if (kmCalendarSyncBindService == null) {
			kmCalendarSyncBindService = (IKmCalendarSyncBindService) SpringBeanUtil
					.getBean("kmCalendarSyncBindService");
		}
		return kmCalendarSyncBindService;
	}

	private IKmCalendarAuthService kmCalendarAuthService;

	public IKmCalendarAuthService getKmCalendarAuthService() {
		if (kmCalendarAuthService == null) {
			kmCalendarAuthService = (IKmCalendarAuthService) SpringBeanUtil.getBean("kmCalendarAuthService");
		}
		return kmCalendarAuthService;
	}

	public CMSSynchroThread() {
		super();
	}

	public CMSSynchroThread(SysQuartzJobContext sysQuartzJobContext,
			CountDownLatch countDownLatch) {
		this.sysQuartzJobContext = sysQuartzJobContext;
		this.countDownLatch = countDownLatch;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CMSSynchroThread.class);

	private ISysOrgCoreService sysOrgCoreService;

	private ISysOrgPersonService sysOrgPersonService;

	private ICoreOuterService sysAttachmentService;

	private ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	public ICoreOuterService getSysAttachmentService() {
		if (sysAttachmentService == null) {
			sysAttachmentService = (ICoreOuterService) SpringBeanUtil
					.getBean("sysAttachmentService");
		}
		return sysAttachmentService;
	}

	public void setSysAttachmentService(
			SysAttachmentCoreOuterService sysAttachmentService) {
		this.sysAttachmentService = sysAttachmentService;
	}

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
		hqlInfo.setSelectBlock("kmCalendarAuth.fdId");
		hqlInfo.setWhereBlock("kmCalendarAuth.docCreator.fdId =:fdId");
		hqlInfo.setParameter("fdId", getPersonId());
		List<String> authIds = getKmCalendarAuthService().findList(hqlInfo);
		String authId = null;
		if (authIds != null && authIds.size() > 0) {
			authId = authIds.get(0);
		}
		if (StringUtil.isNotNull(authId)) {
			kmCalendarAuth = (KmCalendarAuth) getKmCalendarAuthService().findByPrimaryKey(authId, null, true);
		}
		try {
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			synchroDirect = config.getSynchroDirect();
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	private void initPreparedStatement() throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");

		conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		ps_add_kmCalendarMain = conn.prepareStatement(sql_add_kmCalendarMain);
        ps_add_releatedPerson = conn.prepareStatement(sql_add_KmCalendarRelPerson); //相关人sql
        ps_add_relPersonDetails = conn.prepareStatement(sql_add_KmRelPersonDetails); //相关人详情表
        ps_update_kmCalendarMain = conn
				.prepareStatement(sql_update_kmCalendarMain);
		ps_delete_kmCalendarMain = conn
				.prepareStatement(sql_delete_kmCalendarMain);
		ps_add_KmCalendarSyncMapping = conn
				.prepareStatement(sql_add_KmCalendarSyncMapping);
		ps_delete_KmCalendarSyncMapping = conn
				.prepareStatement(sql_delete_KmCalendarSyncMapping);
		ps_add_KmCalendarOutCache = conn
				.prepareStatement(sql_add_KmCalendarOutCache);
		ps_update_KmCalendarOutCache = conn
				.prepareStatement(sql_update_KmCalendarOutCache);
		ps_delete_KmCalendarOutCache = conn
				.prepareStatement(sql_delete_KmCalendarOutCache);
		ps_delete_KmCalendarOutCache2 = conn
				.prepareStatement(sql_delete_KmCalendarOutCache2);
		ps_add_synchro_date = conn.prepareStatement(sql_add_synchro_date);
		ps_update_synchro_date = conn.prepareStatement(sql_update_synchro_date);

		ps_add_reader = conn.prepareStatement(sql_add_reader);
		ps_add_editor = conn.prepareStatement(sql_add_editor);

		ps_add_areader = conn.prepareStatement(sql_add_areader);

		ps_add_aeditor = conn.prepareStatement(sql_add_aeditor);

		ps_delete_areader = conn.prepareStatement(sql_delete_areader);

		ps_delete_aeditor = conn.prepareStatement(sql_delete_aeditor);

		ps_delete_readers = conn.prepareStatement(sql_delete_readers);

		ps_delete_editors = conn.prepareStatement(sql_delete_editors);

		ps_delete_quartz_job = conn.prepareStatement(sql_delete_quartz_job);

	}

	private void commitPreparedStatementIn() throws SQLException {
		ps_add_kmCalendarMain.executeBatch();
        ps_add_releatedPerson.executeBatch();
        ps_add_relPersonDetails.executeBatch();
        ps_update_kmCalendarMain.executeBatch();
		ps_delete_areader.executeBatch();
		ps_delete_aeditor.executeBatch();
		ps_delete_readers.executeBatch();
		ps_delete_editors.executeBatch();
		ps_delete_quartz_job.executeBatch();
		ps_delete_kmCalendarMain.executeBatch();
		ps_add_KmCalendarSyncMapping.executeBatch();
		ps_delete_KmCalendarSyncMapping.executeBatch();
		ps_add_KmCalendarOutCache.executeBatch();
		ps_update_KmCalendarOutCache.executeBatch();
		ps_delete_KmCalendarOutCache.executeBatch();
		ps_delete_KmCalendarOutCache2.executeBatch();
		ps_add_synchro_date.executeBatch();
		ps_update_synchro_date.executeBatch();
		ps_add_areader.executeBatch();
		ps_add_aeditor.executeBatch();
		ps_add_reader.executeBatch();
		ps_add_editor.executeBatch();
		conn.commit();
	}

	private void commitPreparedStatementOut() throws SQLException {
		ps_add_KmCalendarSyncMapping.executeBatch();
		ps_delete_KmCalendarOutCache2.executeBatch();
		conn.commit();
	}

	@SuppressWarnings("unchecked")
	private void addKmCalendarMainBatch(KmCalendarMain kmCalendarMain)
			throws SQLException {
		try {
			ps_add_kmCalendarMain.setString(1, kmCalendarMain.getFdId());
			ps_add_kmCalendarMain.setString(2, kmCalendarMain.getDocSubject());
			ps_add_kmCalendarMain.setString(3, kmCalendarMain
					.getDocContent(false));
			ps_add_kmCalendarMain.setTimestamp(4, new Timestamp(kmCalendarMain
					.getDocCreateTime().getTime()));
			ps_add_kmCalendarMain.setTimestamp(5, new Timestamp(kmCalendarMain
					.getDocAlterTime().getTime()));
			ps_add_kmCalendarMain.setTimestamp(6, new Timestamp(kmCalendarMain
					.getDocStartTime().getTime()));
			ps_add_kmCalendarMain.setTimestamp(7, new Timestamp(kmCalendarMain
					.getDocFinishTime().getTime()));
			ps_add_kmCalendarMain.setBoolean(8, kmCalendarMain
					.getFdIsAlldayevent());
			ps_add_kmCalendarMain.setString(9, kmCalendarMain
					.getFdRecurrenceStr());
			ps_add_kmCalendarMain.setBoolean(10, kmCalendarMain.getFdIsLunar());
			ps_add_kmCalendarMain.setString(11, kmCalendarMain
					.getFdAuthorityType());
			ps_add_kmCalendarMain.setString(12, kmCalendarMain.getFdLocation());
			ps_add_kmCalendarMain.setString(13, kmCalendarMain
					.getFdRelationUrl());
			ps_add_kmCalendarMain.setString(14, kmCalendarMain.getFdType());
			ps_add_kmCalendarMain.setString(15, personId);
			ps_add_kmCalendarMain.setString(16, personId);
			if (kmCalendarMain.getFdRecurrenceLastStart() != null) {
				ps_add_kmCalendarMain.setTimestamp(17, new Timestamp(
						kmCalendarMain.getFdRecurrenceLastStart().getTime()));
				ps_add_kmCalendarMain.setTimestamp(18, new Timestamp(
						kmCalendarMain.getFdRecurrenceLastEnd().getTime()));
			} else {
				ps_add_kmCalendarMain.setTimestamp(17, null);
				ps_add_kmCalendarMain.setTimestamp(18, null);
			}
			ps_add_kmCalendarMain.setString(19, kmCalendarMain
					.getFdCompatibleState());
			ps_add_kmCalendarMain.addBatch();

            //添加到相关人和详情表
            List<SysOrgPerson> fdRelatedPersons =  kmCalendarMain.getFdRelatedPersons();
            if(!ArrayUtil.isEmpty(fdRelatedPersons)){
                for(SysOrgPerson person:fdRelatedPersons) {
                    ps_add_releatedPerson.setString(1, kmCalendarMain.getFdId());
                    ps_add_releatedPerson.setString(2, person.getFdId());
                    ps_add_releatedPerson.addBatch();
                    //添加到相关人详情表
                    ps_add_relPersonDetails.setString(1,IDGenerator.generateID());
                    ps_add_relPersonDetails.setBoolean(2,Boolean.FALSE);
                    ps_add_relPersonDetails.setString(3,kmCalendarMain.getFdId());
                    ps_add_relPersonDetails.setString(4,person.getFdId());
                    ps_add_relPersonDetails.addBatch();
                    //添加可阅读者
                    ps_add_areader.setString(1, kmCalendarMain.getFdId());
                    ps_add_areader.setString(2, person.getFdId());
                    ps_add_areader.addBatch();
                }
            }


            ps_add_areader.setString(1, kmCalendarMain.getFdId());
			ps_add_areader.setString(2, personId);
			ps_add_areader.addBatch();

			ps_add_aeditor.setString(1, kmCalendarMain.getFdId());
			ps_add_aeditor.setString(2, personId);
			ps_add_aeditor.addBatch();

			try {

				if (kmCalendarAuth != null) {
					List<SysOrgElement> authReaders = kmCalendarAuth.getAuthReaders();
					List<SysOrgElement> authModifier = kmCalendarAuth.getAuthModifiers();
					if (authReaders != null) {
						for (SysOrgElement reader : authReaders) {
							ps_add_reader.setString(1, kmCalendarMain.getFdId());
							ps_add_reader.setString(2, reader.getFdId());
							ps_add_reader.addBatch();
							if (KmCalendarConstant.AUTHORITY_TYPE_DEFAULT.equals(kmCalendarMain.getFdAuthorityType())) {
								ps_add_areader.setString(1, kmCalendarMain.getFdId());
								ps_add_areader.setString(2, reader.getFdId());
								ps_add_areader.addBatch();
							}
						}
					}
					if (authModifier != null) {
						for (SysOrgElement editor : authModifier) {
							ps_add_editor.setString(1, kmCalendarMain.getFdId());
							ps_add_editor.setString(2, editor.getFdId());
							ps_add_editor.addBatch();
							if (KmCalendarConstant.AUTHORITY_TYPE_DEFAULT.equals(kmCalendarMain.getFdAuthorityType())) {
								ps_add_aeditor.setString(1, kmCalendarMain.getFdId());
								ps_add_aeditor.setString(2, editor.getFdId());
								ps_add_aeditor.addBatch();
								ps_add_areader.setString(1, kmCalendarMain.getFdId());
								ps_add_areader.setString(2, editor.getFdId());
								ps_add_areader.addBatch();
							}
						}
					}
				}
			} catch (Exception e) {
				// ps_add_kmCalendarMain.setString(11,
				// KmCalendarConstant.AUTHORITY_TYPE_PRIVATE);
			}

		} catch (SQLException e) {
			logJobMessage(personId + " " + kmCalendarMain.getDocSubject()
					+ "    " + kmCalendarMain.getDocStartTime() + "    "
					+ kmCalendarMain.getDocFinishTime());
			throw e;
		}

	}

	private void updateKmCalendarMainBatch(KmCalendarMain kmCalendarMain)
			throws SQLException {
		// String sql_update_kmCalendarMain =
		// "update km_calendar_main set doc_subject=?,doc_content=?,doc_alter_time=?,doc_start_time=?,"
		// +
		// "doc_finish_time=?,fd_is_AllDayEvent=?,fd_recurrence_str=?,fd_is_lunar=?,fd_location=?,fd_relation_url=?,fd_type=?,"
		// +
		// "fd_recurrence_last_start=?,fd_recurrence_last_end=? where fd_id=?";

		ps_update_kmCalendarMain.setString(1, kmCalendarMain.getDocSubject());
		ps_update_kmCalendarMain.setString(2, kmCalendarMain
				.getDocContent(false));
		ps_update_kmCalendarMain.setTimestamp(3, new Timestamp(kmCalendarMain
				.getDocAlterTime().getTime()));
		ps_update_kmCalendarMain.setTimestamp(4, new Timestamp(kmCalendarMain
				.getDocStartTime().getTime()));
		ps_update_kmCalendarMain.setTimestamp(5, new Timestamp(kmCalendarMain
				.getDocFinishTime().getTime()));
		ps_update_kmCalendarMain.setBoolean(6, kmCalendarMain
				.getFdIsAlldayevent());
		ps_update_kmCalendarMain.setString(7, kmCalendarMain
				.getFdRecurrenceStr());
		ps_update_kmCalendarMain.setBoolean(8, kmCalendarMain.getFdIsLunar());
		ps_update_kmCalendarMain.setString(9, kmCalendarMain.getFdLocation());
		ps_update_kmCalendarMain.setString(10, kmCalendarMain
				.getFdRelationUrl());
		ps_update_kmCalendarMain.setString(11, kmCalendarMain.getFdType());
		if (kmCalendarMain.getFdRecurrenceLastStart() != null) {
			ps_update_kmCalendarMain.setTimestamp(12, new Timestamp(
					kmCalendarMain.getFdRecurrenceLastStart().getTime()));
			ps_update_kmCalendarMain.setTimestamp(13, new Timestamp(
					kmCalendarMain.getFdRecurrenceLastEnd().getTime()));
		} else {
			ps_update_kmCalendarMain.setTimestamp(12, null);
			ps_update_kmCalendarMain.setTimestamp(13, null);
		}
		ps_update_kmCalendarMain.setString(14, kmCalendarMain.getFdId());
		ps_update_kmCalendarMain.addBatch();

	}

	private void deleteKmCalendarMainBatch(String fdId) throws SQLException {
		logger.debug("删除：" + fdId);
		ps_delete_areader.setString(1, fdId);
		ps_delete_areader.addBatch();
		ps_delete_aeditor.setString(1, fdId);
		ps_delete_aeditor.addBatch();
		ps_delete_readers.setString(1, fdId);
		ps_delete_readers.addBatch();
		ps_delete_editors.setString(1, fdId);
		ps_delete_editors.addBatch();
		ps_delete_quartz_job.setString(1, fdId);
		ps_delete_quartz_job.addBatch();
		ps_delete_kmCalendarMain.setString(1, fdId);
		ps_delete_kmCalendarMain.addBatch();

	}

	private void addKmCalendarSyncMappingBatch(
			KmCalendarSyncMapping kmCalendarSyncMapping) throws SQLException {
		ps_add_KmCalendarSyncMapping.setString(1, kmCalendarSyncMapping
				.getFdId());
		ps_add_KmCalendarSyncMapping.setString(2, kmCalendarSyncMapping
				.getFdCalendarId());
		ps_add_KmCalendarSyncMapping.setString(3, kmCalendarSyncMapping
				.getFdAppKey());
		ps_add_KmCalendarSyncMapping.setString(4, kmCalendarSyncMapping
				.getFdAppUuid());
		ps_add_KmCalendarSyncMapping.addBatch();
	}

	private void deleteKmCalendarSyncMappingBatch(String appKey, String uuid)
			throws SQLException {
		ps_delete_KmCalendarSyncMapping.setString(1, appKey);
		ps_delete_KmCalendarSyncMapping.setString(2, uuid);
		ps_delete_KmCalendarSyncMapping.addBatch();
	}

	private void addKmCalendarOutCacheBatch(
			KmCalendarOutCache kmCalendarOutCache) throws SQLException {
		if ("toEkp".equals(synchroDirect)) {
			return;
		}

		ps_add_KmCalendarOutCache.setString(1, kmCalendarOutCache.getFdId());
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
		ps_add_KmCalendarOutCache.setString(7, kmCalendarOutCache.getFdOwner()
				.getFdId());
		ps_add_KmCalendarOutCache.addBatch();

	}

	private void updateKmCalendarOutCacheBatch(
			KmCalendarOutCache kmCalendarOutCache) throws SQLException {
		if ("toEkp".equals(synchroDirect)) {
			return;
		}
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
		ps_update_KmCalendarOutCache.setString(7, kmCalendarOutCache.getFdId());
		ps_update_KmCalendarOutCache.addBatch();

	}

	private void deleteKmCalendarOutCacheBatch(String fdId) throws SQLException {
		ps_delete_KmCalendarOutCache2.setString(1, fdId);
		ps_delete_KmCalendarOutCache2.addBatch();
	}

	private void deleteKmCalendarOutCacheBatch(String appKey, String calendarId)
			throws SQLException {
		ps_delete_KmCalendarOutCache.setString(1, calendarId);
		ps_delete_KmCalendarOutCache.setString(2, appKey);
		ps_delete_KmCalendarOutCache.addBatch();
	}

	private void updateSynchroDate(String appKey, String personId,
			Date synchroDate) throws SQLException {
		// String sql_update_synchro_date =
		// "update km_calendar_sync_bind set fd_sync_timestamp=? where fd_app_key=? and fd_owner_id=?";
		if (lastSyncroDate != null) {
			ps_update_synchro_date.setTimestamp(1, new Timestamp(new Date()
					.getTime()));
			ps_update_synchro_date.setString(2, appKey);
			ps_update_synchro_date.setString(3, personId);
			ps_update_synchro_date.addBatch();
		} else {
			// "insert into km_calendar_sync_bind(fd_id,fd_app_key,fd_owner_id,fd_sync_timestamp) values(?,?,?,?)";
			ps_add_synchro_date.setString(1, IDGenerator.generateID());
			ps_add_synchro_date.setString(2, appKey);
			ps_add_synchro_date.setString(3, personId);
			ps_add_synchro_date.setTimestamp(4, new Timestamp(new Date()
					.getTime()));
			ps_add_synchro_date.addBatch();
		}

	}

	public void updateCalendarOutCaches(KmCalendarMain kmCalendarMain,
			String operationType, String personId, String operationAppKey,
			List<String> appKeys) throws Exception {
		if ("toEkp".equals(synchroDirect)) {
			return;
		}
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
						String lastOperationType = cache.getFdOperationType();
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
								deleteKmCalendarOutCacheBatch(cache.getFdId());
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

	private void handleAddedCal(String appKey, SyncroCommonCal syncroCommonCal)
			throws Exception {
		// 增加数据到日程模块

		KmCalendarMain kmCalendarMain = new KmCalendarMain();
		String calendarId = IDGenerator.generateID();
		kmCalendarMain.setFdId(calendarId);
		setKmCalendarMainValue(kmCalendarMain, syncroCommonCal);
		Date current = new Date();
		kmCalendarMain.setDocCreateTime(current);
		kmCalendarMain.setDocAlterTime(current);
		try {
			addKmCalendarMainBatch(kmCalendarMain);
		} catch (Exception e) {
			logJobMessage(personId + " " + kmCalendarMain.getDocSubject()
					+ "    " + kmCalendarMain.getDocStartTime() + "    "
					+ kmCalendarMain.getDocFinishTime() + "  "
					+ syncroCommonCal.getUuid());
			throw e;
		}
		// getKmCalendarMainService().addSelf(kmCalendarMain);

		// 增加映射数据
		KmCalendarSyncMapping kmCalendarSyncMapping = new KmCalendarSyncMapping();
		kmCalendarSyncMapping.setFdId(IDGenerator.generateID());
		kmCalendarSyncMapping.setFdAppKey(appKey);
		kmCalendarSyncMapping.setFdAppUuid(syncroCommonCal.getUuid());
		kmCalendarSyncMapping.setFdCalendarId(calendarId);

		// getKmCalendarSyncMappingService().add(kmCalendarSyncMapping);
		addKmCalendarSyncMappingBatch(kmCalendarSyncMapping);

		// 增加接出cache
		updateCalendarOutCaches(kmCalendarMain,
				KmCalendarConstant.OPERATION_TYPE_ADD, personId, appKey,
				appKeys);

		// addCalendarOutCaches(appKey, appKeys, kmCalendarMain,
		// syncroCommonCal,
		// KmCalendarConstant.OPERATION_TYPE_ADD);
	}

	private void handleUpdatedCal(String appKey, SyncroCommonCal syncroCommonCal)
			throws Exception {
		KmCalendarMain kmCalendarMain = getKmCalendarMainService()
				.findCalendarByCacheOut(appKey, syncroCommonCal.getUuid());
		// 在接出表里面找到了对应的记录
		if (kmCalendarMain != null) {
			if (syncroCommonCal.getUpdateTime().after(
					kmCalendarMain.getDocAlterTime())) {
				setKmCalendarMainValue(kmCalendarMain, syncroCommonCal);
				// getKmCalendarMainService().updateSelf(kmCalendarMain);
				updateKmCalendarMainBatch(kmCalendarMain);
				updateCalendarOutCaches(kmCalendarMain,
						KmCalendarConstant.OPERATION_TYPE_UPDATE, personId,
						appKey, appKeys);

			}
		} else {
			List<String> calendarIds = getKmCalendarSyncMappingService()
					.findCalendarIds(appKey, syncroCommonCal.getUuid());
			if (calendarIds.size() == 1) {
				kmCalendarMain = (KmCalendarMain) getKmCalendarMainService()
						.findByPrimaryKey(calendarIds.get(0), null, true);
				try {
					kmCalendarMain.getDocSubject();
					// 更新日程记录
					setKmCalendarMainValue(kmCalendarMain, syncroCommonCal);
					// getKmCalendarMainService().updateSelf(kmCalendarMain);
					updateKmCalendarMainBatch(kmCalendarMain);
					// 更新映射数据
					// KmCalendarSyncMapping kmCalendarSyncMapping = new
					// KmCalendarSyncMapping();
					// kmCalendarSyncMapping.setFdId(IDGenerator.generateID());
					// kmCalendarSyncMapping.setFdAppKey(appKey);
					// kmCalendarSyncMapping.setFdAppUuid(syncroCommonCal.getUuid());
					// kmCalendarSyncMapping.setFdCalendarId(calendarIds.get(0));
					//
					// getKmCalendarSyncMappingService().update(kmCalendarSyncMapping);

					// 添加cache
					updateCalendarOutCaches(kmCalendarMain,
							KmCalendarConstant.OPERATION_TYPE_UPDATE, personId,
							appKey, appKeys);
				} catch (NullPointerException e) {
					logJobError("找不到对应的日程记录", e);
				}

			}
		}
	}

	private void handleDeletedCal(String appKey, SyncroCommonCal syncroCommonCal)
			throws Exception {
		KmCalendarMain kmCalendarMain = getKmCalendarMainService()
				.findCalendarByCacheOut(appKey, syncroCommonCal.getUuid());
		// 在接出表里面找到了对应的记录
		if (kmCalendarMain != null) {
			if (syncroCommonCal.getUpdateTime().after(
					kmCalendarMain.getDocAlterTime())) {
				// 删除日程记录
				// getKmCalendarMainService().deleteSelf(kmCalendarMain);
				deleteKmCalendarMainBatch(kmCalendarMain.getFdId());

				// 删除提醒信息
				// getKmCalendarMainService().deleteScheduler(kmCalendarMain);

				// 更新接出队列
				updateCalendarOutCaches(kmCalendarMain,
						KmCalendarConstant.OPERATION_TYPE_DELETE, personId,
						appKey, appKeys);
				// 删除映射数据
				// getKmCalendarSyncMappingService().delete(appKey,
				// syncroCommonCal.getUuid());
				deleteKmCalendarSyncMappingBatch(appKey, syncroCommonCal
						.getUuid());
			}
		} else {

			List<String> calendarIds = getKmCalendarSyncMappingService()
					.findCalendarIds(appKey, syncroCommonCal.getUuid(),
							personId);
			if (calendarIds.size() == 1) {
				kmCalendarMain = (KmCalendarMain) getKmCalendarMainService()
						.findByPrimaryKey(calendarIds.get(0), null, true);
				// 更新日程记录
				// getKmCalendarMainService().deleteSelf(kmCalendarMain);
				deleteKmCalendarMainBatch(kmCalendarMain.getFdId());
				// 添加cache
				updateCalendarOutCaches(kmCalendarMain,
						KmCalendarConstant.OPERATION_TYPE_DELETE, personId,
						appKey, appKeys);
				// 删除映射数据
				// getKmCalendarSyncMappingService().delete(appKey,
				// syncroCommonCal.getUuid());
				deleteKmCalendarSyncMappingBatch(appKey, syncroCommonCal
						.getUuid());
			}
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
			logger.error(e.getMessage());
			return null;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		CMSSynchroService.addSyncroingPerson(personId);

		if (!"toExternal".equals(synchroDirect)) {

			for (String appKey : appKeys) {
				if (syncroIn(appKey)) {
					result.put("km.calendar.synchro.in.success", appKey);
					logJobMessage("用户 " + personId + " 从 " + appKey + " 接入数据成功;");
				} else {
					result.put("km.calendar.synchro.in.failure", appKey);
					logJobMessage("用户 " + personId + " 从 " + appKey + " 接入数据失败;");
				}
			}
		}

		if (!"toEkp".equals(synchroDirect)) {
			for (String appKey : appKeys) {
				if (syncroOut(appKey)) {
					result.put("km.calendar.synchro.out.success", appKey);
					logJobMessage("用户 " + personId + " 接出数据到 " + appKey + " 成功;");
				} else {
					result.put("km.calendar.synchro.out.failure", appKey);
					logJobMessage("用户 " + personId + " 接出数据到 " + appKey + " 失败;");
				}
			}
		}
		CMSSynchroService.removeSyncroingPerson(personId);
		if (exceptions.size() > 0) {
			result.put("exceptions", exceptions);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			logger.error(e.getMessage());
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
        logThreadInfo();
		logJobMessage("同步用户：" + personId);
		syncro();
		countDownLatch.countDown();
	}

	private void logThreadInfo() {
		if(logger.isDebugEnabled())
		{
			Thread current = Thread.currentThread();
			StringBuilder threadInfoBuilder = new StringBuilder();
			threadInfoBuilder.append("同步用户["+personId+"]的当前处理线程信息：  ");
			threadInfoBuilder.append("线程id:["+current.getId()+"]");
			threadInfoBuilder.append(",线程名称:["+current.getName()+"]");
			threadInfoBuilder.append(",线程优先级:["+current.getPriority()+"]");
			logger.debug(threadInfoBuilder.toString());
		}
	}

	private boolean syncroIn(String appKey) {
		Date current = new Date();
		Date syncroTimeStamp = null;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		boolean syncInSuccess = true;
		CMSPluginData data = CMSPlugin.getCMSPluginData(appKey);
		// Date lastSyncroDate;
		try {
			lastSyncroDate = getKmCalendarSyncBindService().getSyncroDate(
					personId, appKey);
		} catch (Exception e2) {
			logger.error("",e2);
			logJobMessage("用户 " + personId + " 从 " + appKey + " 接入数据失败;---"
					+ e2.getMessage());
			return false;
		}
		// 超过3个月没同步，只同步3个月的数据
		if (lastSyncroDate == null || c.getTime().after(lastSyncroDate)) {
			syncroTimeStamp = c.getTime();
			logJobMessage("用户 " + personId + ", 超过3个月没同步，只同步3个月的数据["+DateUtil.convertDateToString(syncroTimeStamp, null)+"]");
		} else {
			syncroTimeStamp = lastSyncroDate;
			logJobMessage("用户 " + personId + ", 最近同步时间["+DateUtil.convertDateToString(syncroTimeStamp, null)+"]");
		}
		ICMSProvider cmsProvider = data.getCmsProvider();
		try {
			cmsProvider = cmsProvider.getNewInstance(personId);
		} catch (Exception e1) {
			e1.printStackTrace();
			logJobMessage("用户 " + personId + " 从 " + appKey + " 接入数据失败;---"
					+ e1.getMessage());
			exceptions.add(e1);
			return false;
		}
		if (cmsProvider == null) {
			logJobMessage("cmsProvider为NULL，同步无法执行，用户ID为:" + personId);
			return false;
		}
		// TransactionStatus syncroIn = TransactionUtils.beginNewTransaction();
		sysOrgCoreService = getSysOrgCoreService();

		// 如果是第一次同步
		if (lastSyncroDate == null) {
			try {
				List<SyncroCommonCal> syncroCommonCals = cmsProvider
						.getCalElements(personId, syncroTimeStamp);
				logJobMessage("用户 " + personId + ", 接入数据量是["+syncroCommonCals.size()+"]");
				for (SyncroCommonCal syncroCommonCal : syncroCommonCals) {
					try{
						List<String> calendarIds = getKmCalendarSyncMappingService()
								.findCalendarIds(appKey, syncroCommonCal.getUuid());
						logger.debug("接入数据：" + syncroCommonCal.getSubject());
						if (calendarIds != null && calendarIds.size() > 0) {
							handleUpdatedCal(appKey, syncroCommonCal);
						} else {
							KmCalendarOutCache kmCalendarOutCache = getKmCalendarOutCacheService()
									.findByAppUuidAndAppKey(syncroCommonCal.getUuid(), appKey);

							// 在接出表里面找到了对应的记录
							if (kmCalendarOutCache != null) {
								handleUpdatedCal(appKey, syncroCommonCal);
							} else {
								handleAddedCal(appKey, syncroCommonCal);
							}
						}
					}catch (Exception e){
						e.printStackTrace();
						logJobError("用户 " + personId + " 从 " + appKey + " 接入数据" + syncroCommonCal + "失败;", e);
						exceptions.add(e);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logJobError("用户 " + personId + " 从 " + appKey + " 接入数据失败;", e);
				exceptions.add(e);
			}
		} else {
			try {
				List<SyncroCommonCal> syncroCommonCals_add = cmsProvider
						.getAddedCalElements(personId, syncroTimeStamp);
				List<SyncroCommonCal> syncroCommonCals_updated = cmsProvider
						.getUpdatedCalElements(personId, syncroTimeStamp);
				List<SyncroCommonCal> syncroCommonCals_deleted = cmsProvider
						.getDeletedCalElements(personId, syncroTimeStamp);
				if (syncroCommonCals_add != null) {
					logJobMessage("用户 " + personId + ", 接入新增数据是["+syncroCommonCals_add.size()+"]");
					for (SyncroCommonCal syncroCommonCal : syncroCommonCals_add) {
						try{
							List<String> calendarIds = getKmCalendarSyncMappingService()
									.findCalendarIds(appKey,
											syncroCommonCal.getUuid());
							logger.debug("接入新增数据："
									+ syncroCommonCal.getSubject());
							if (calendarIds != null && calendarIds.size() > 0) {
								handleUpdatedCal(appKey, syncroCommonCal);
							} else {
								KmCalendarOutCache kmCalendarOutCache = getKmCalendarOutCacheService()
										.findByAppUuidAndAppKey(
												syncroCommonCal.getUuid(), appKey);

								// 在接出表里面找到了对应的记录
								if (kmCalendarOutCache != null) {
									handleUpdatedCal(appKey, syncroCommonCal);
								} else {
									handleAddedCal(appKey, syncroCommonCal);
								}
							}
						}catch (Exception e){
							e.printStackTrace();
							logJobError("用户 " + personId + " 从 " + appKey + " 接入数据" + syncroCommonCal + "失败;", e);
							exceptions.add(e);
						}
					}
				}
				if (syncroCommonCals_updated != null) {
					logJobMessage("用户 " + personId + ", 接入更新数据是["+syncroCommonCals_updated.size()+"]");
					for (SyncroCommonCal syncroCommonCal : syncroCommonCals_updated) {
						logger.debug("接入更新数据："
								+ syncroCommonCal.getSubject());
						handleUpdatedCal(appKey, syncroCommonCal);
					}
				}
				if (syncroCommonCals_deleted != null) {
					logJobMessage("用户 " + personId + ", 接入删除数据是["+syncroCommonCals_deleted.size()+"]");
					for (SyncroCommonCal syncroCommonCal : syncroCommonCals_deleted) {
						logger.debug("接入删除数据："
								+ syncroCommonCal.getSubject());
						handleDeletedCal(appKey, syncroCommonCal);
					}
				}
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
				logJobError("用户 " + personId + " 从 " + appKey + " 接入数据失败;", e);
				exceptions.add(e);
			}
		}
		if (syncInSuccess) {
			// 更新同步时间戳
			try {
				// getKmCalendarSyncBindService().updateSyncroDate(personId,
				// appKey, current);
				updateSynchroDate(appKey, personId, current);
				// TransactionUtils.getTransactionManager().commit(syncroIn);
				commitPreparedStatementIn();
				// System.gc();
				logger.info("用户 " + personId + " 从应用  " + appKey
						+ " 接入数据成功，耗时："
						+ (new Date().getTime() - current.getTime()) + "ms");
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO 自动生成 catch 块
					e1.printStackTrace();
					logger.error(e1.getMessage());
				}
				logger.error("用户 " + personId + "," + " 从应用  " + appKey
						+ " 接入数据失败。");
				logJobError("用户 " + personId + " 从 " + appKey + " 接入数据失败;", e);
				exceptions.add(e);
				e.printStackTrace();
			}
		} else {
			logger.error("用户 " + personId + " 从应用  " + appKey + " 接入数据失败。");
		}
		return syncInSuccess;
	}

	/**
	 * 接出到appKey对应的应用
	 * 
	 * @param appKey
	 * @throws SQLException
	 */
	private boolean syncroOut(String appKey) {
		Date current = new Date();
		List<KmCalendarOutCache> kmCalendarOutCaches;
		try {
			kmCalendarOutCaches = getKmCalendarOutCacheService()
					.listByPersonAndApp(personId, appKey);
		} catch (Exception e2) {
			exceptions.add(e2);
			logJobError("用户 " + personId + " 接出数据到 " + appKey + " 失败;", e2);
			e2.printStackTrace();
			return false;
		}
		if (kmCalendarOutCaches == null) {
			return true;
		}
		CMSPluginData data = CMSPlugin.getCMSPluginData(appKey);
		ICMSProvider provider = data.getCmsProvider();
		try {
			provider = new ICMSProviderRateLimiter(provider, addCalElementRateLimiter, delCalElementRateLimiter,
					updateCalElementRateLimiter).getNewInstance(personId);
			logJobMessage("用户 " + personId + ", 接出数据量是["+kmCalendarOutCaches.size()+"]");
			for (KmCalendarOutCache kmCalendarOutCache : kmCalendarOutCaches) {
				try{
					String operationType = kmCalendarOutCache.getFdOperationType();
					logger.debug("接出数据:"
							+ kmCalendarOutCache.getFdCalendarId());
					if (KmCalendarConstant.OPERATION_TYPE_ADD.equals(operationType)) {

						KmCalendarMain kmCalendarMain = (KmCalendarMain) getKmCalendarMainService()
								.findByPrimaryKey(
										kmCalendarOutCache.getFdCalendarId(), null,
										true);
						SyncroCommonCal syncroCommonCal = new SyncroCommonCal();
						syncroCommonCal.setAppKey(appKey);
						setSyncroCommonCalValue(kmCalendarMain, syncroCommonCal);
						String uuid = provider.addCalElement(null, syncroCommonCal);
						if (StringUtil.isNotNull(uuid)) {
							KmCalendarSyncMapping kmCalendarSyncMapping = new KmCalendarSyncMapping();
							kmCalendarSyncMapping.setFdAppKey(appKey);
							kmCalendarSyncMapping.setFdAppUuid(uuid);
							kmCalendarSyncMapping.setFdCalendarId(kmCalendarMain
									.getFdId());
							kmCalendarSyncMapping.setFdId(IDGenerator.generateID());

							addKmCalendarSyncMappingBatch(kmCalendarSyncMapping);
						}

						deleteKmCalendarOutCacheBatch(kmCalendarOutCache.getFdId());

					} else if (KmCalendarConstant.OPERATION_TYPE_UPDATE
							.equals(operationType)) {
						KmCalendarMain kmCalendarMain = (KmCalendarMain) getKmCalendarMainService()
								.findByPrimaryKey(
										kmCalendarOutCache.getFdCalendarId(), null,
										true);
						SyncroCommonCal syncroCommonCal = new SyncroCommonCal();
						syncroCommonCal.setAppKey(appKey);
						setSyncroCommonCalValue(kmCalendarMain, syncroCommonCal);
						syncroCommonCal.setUuid(kmCalendarOutCache.getFdAppUuid());
						provider.updateCalElement(null, syncroCommonCal);
						deleteKmCalendarOutCacheBatch(kmCalendarOutCache.getFdId());

					} else if (KmCalendarConstant.OPERATION_TYPE_DELETE
							.equals(operationType)) {

						provider.deleteCalElement(personId, kmCalendarOutCache
								.getFdAppUuid());
						deleteKmCalendarOutCacheBatch(kmCalendarOutCache.getFdId());
					}
				}catch (Exception e){
					logger.error("用户 " + personId + "," + " 接出到应用 " + appKey + "的数据：" + kmCalendarOutCache + " 失败。");
					logJobError("用户 " + personId + " 接出数据到 " + appKey + "的数据：" + kmCalendarOutCache + " 失败;", e);
					exceptions.add(e);
					e.printStackTrace();
				}
			}
			commitPreparedStatementOut();
			logger.info("用户 " + personId + " 接出到应用 " + appKey + " 成功，耗时："
					+ (new Date().getTime() - current.getTime()) + "ms");
			return true;

		} catch (Exception e1) {
			logger.error("用户 " + personId + "," + " 接出到应用 " + appKey + " 失败。");
			logJobError("用户 " + personId + " 接出数据到 " + appKey + " 失败;", e1);
			exceptions.add(e1);
			e1.printStackTrace();
			return false;
		} finally {
			try {
				// 某条记录同步失败，也应该执行下面的语句，因为前面的记录已经同步成功了。
				// 不执行的话，会导致新增的记录每次同步都会往第三方插入新的数据；另外，因为映射关系也没写会表中，这样，接入同步的时候又会把第三方的记录重新同步回来，数据循环重复。
				commitPreparedStatementOut();
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				logger.error("", e);
			}
		}
	}

	private void setSyncroCommonCalValue(KmCalendarMain kmCalendarMain,
			SyncroCommonCal syncroCommonCal) {
		syncroCommonCal.setAllDayEvent(kmCalendarMain.getFdIsAlldayevent());
		// TODO: 设置附件信息
		try {
			List<SysAttMain> list = getSysAttMainService().findByModelKey(
					ModelUtil.getModelClassName(kmCalendarMain),
					kmCalendarMain.getFdId(), "fdAttachment");
			if (!CollectionUtils.isEmpty(list)) {
				List<String> SysAttMainIds = new ArrayList();
				for (SysAttMain attMain : list) {
					SysAttMainIds.add(attMain.getFdId());
				}
				String[] s = new String[SysAttMainIds.size()];
				getSysAttMainService().findModelsByIds(SysAttMainIds.toArray(s));
				syncroCommonCal.setAttachments(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		syncroCommonCal.setCalType(kmCalendarMain.getFdType());
		syncroCommonCal.setContent(kmCalendarMain.getDocContent(false));
		syncroCommonCal.setCreateTime(kmCalendarMain.getDocCreateTime());
		syncroCommonCal.setEventFinishTime(kmCalendarMain.getDocFinishTime());
		syncroCommonCal.setEventLocation(kmCalendarMain.getFdLocation());
		syncroCommonCal.setEventStartTime(kmCalendarMain.getDocStartTime());
		//日程相关人
		List<SysOrgPerson> fdRelatedPersons = kmCalendarMain.getFdRelatedPersons();
		ArrayList fdRelateIds =new ArrayList();
        if(!ArrayUtil.isEmpty(fdRelatedPersons)) {
            for (SysOrgPerson fdRelatedPerson : fdRelatedPersons) {
                fdRelateIds.add(fdRelatedPerson.getFdId());
            }
        }
		//日程描述
		syncroCommonCal.setFdDecs(kmCalendarMain.getFdDesc());
		syncroCommonCal.setRelatedPersonIds(fdRelateIds);
		if (kmCalendarMain.getFdIsLunar() != null) {
			syncroCommonCal.setLunar(kmCalendarMain.getFdIsLunar());
		}
		syncroCommonCal.setPersonId(personId);
		syncroCommonCal.setRecurrentStr(kmCalendarMain.getFdRecurrenceStr());
		syncroCommonCal.setSubject(kmCalendarMain.getDocSubject());
		syncroCommonCal.setUpdateTime(kmCalendarMain.getDocAlterTime());

		syncroCommonCal.setCompatibleState(kmCalendarMain
				.getFdCompatibleState());
		
		//陈亮添加
		syncroCommonCal.setCalendarId(kmCalendarMain.getFdId());
		syncroCommonCal.setRelationUrl(kmCalendarMain.getFdRelationUrl());		
	}

	private void setKmCalendarMainValue(KmCalendarMain kmCalendarMain,
			SyncroCommonCal syncroCommonCal) throws Exception {
		Date start = syncroCommonCal.getEventStartTime();
		Date end = syncroCommonCal.getEventFinishTime();
		kmCalendarMain.setDocAlterTime(syncroCommonCal.getUpdateTime());
		kmCalendarMain.setDocContent(syncroCommonCal.getContent(), false);
		kmCalendarMain.setDocFinishTime(end);
		kmCalendarMain.setDocStartTime(start);
		String subject = syncroCommonCal.getSubject();
		if (subject == null) {
			subject = "无标题";
		}
		kmCalendarMain.setDocSubject(subject);
        //处理相关人
        List<SysOrgPerson> relatePersonList = new ArrayList<>();
        List<String> relatedPersonIds = syncroCommonCal.getRelatedPersonIds();
        //exchange没有相关人字段
        if(!ArrayUtil.isEmpty(relatedPersonIds)) {
			for (String fdId : relatedPersonIds) {
				SysOrgPerson sysOrgPerson = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(fdId);
				relatePersonList.add(sysOrgPerson);
			}
			kmCalendarMain.setFdRelatedPersons(relatePersonList);
		}
        kmCalendarMain.setFdIsAlldayevent(syncroCommonCal.isAllDayEvent());
		kmCalendarMain.setFdIsLunar(syncroCommonCal.isLunar());
		kmCalendarMain.setFdLocation(syncroCommonCal.getEventLocation());
		kmCalendarMain.setFdRecurrenceStr(syncroCommonCal.getRecurrentStr());
		kmCalendarMain.setFdType(syncroCommonCal.getCalType());
		kmCalendarMain.setFdRelationUrl(syncroCommonCal.getRelationUrl());
		kmCalendarMain.setFdCompatibleState(syncroCommonCal
				.getCompatibleState());
		// TODO 增加附件信息
		try {
			getSysAttachmentService().delete(kmCalendarMain);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		List<SysAttMain> attMains = syncroCommonCal.getAttachments();
		if (attMains != null && attMains.size() > 0) {
			AutoHashMap att = kmCalendarMain.getAttachmentForms();
			AttachmentDetailsForm form = new AttachmentDetailsForm();
			String attIds = "";
			String attId;
			for (SysAttMain attMain : attMains) {
				attMain.setFdModelName(ModelUtil
						.getModelClassName(kmCalendarMain));
				attMain.setFdModelId(kmCalendarMain.getFdId());
				attMain.setFdKey("fdAttachment");
				try {
					attId = getSysAttMainService().add(attMain);
					attIds += attId + ";";
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
			if (attMains != null && attMains.size() > 0) {
				form.setAttachmentIds(attIds);
				att.put("fdAttachment", form);
				kmCalendarMain.setAttachmentForms(att);
			}
		}
		KmCalendarBaseConfig config = new KmCalendarBaseConfig();
		if (StringUtil.isNull(kmCalendarMain.getFdAuthorityType())) {
			kmCalendarMain.setFdAuthorityType(config.getDefaultAuthorityType());
		}
		if (StringUtil.isNotNull(kmCalendarMain.getFdRecurrenceStr())) {
			try {
				getKmCalendarMainService()
						.setRecurrenceLastDate(kmCalendarMain);
			} catch (ParseException e) {
				logJobMessage("解析Recurrence出错："
						+ kmCalendarMain.getFdRecurrenceStr());
				e.printStackTrace();
			}
		}
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
