package com.landray.kmss.km.calendar.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventAsyncCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.km.calendar.cms.CMSThreadPoolManager;
import com.landray.kmss.km.calendar.cms.interfaces.ISyncOutLabelProvider;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签业务接口实现
 *
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarLabelServiceImp extends BaseServiceImp implements
		IKmCalendarLabelService,IEventMulticasterAware {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendarLabelServiceImp.class);

	protected IKmCalendarMainService kmCalendarMainService;

	public IKmCalendarMainService getKmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
					.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	protected IKmCalendarAgendaLabelService kmCalendarAgendaLabelService;

	public IKmCalendarAgendaLabelService getKmCalendarAgendaLabelService() {
		if (kmCalendarAgendaLabelService == null) {
			kmCalendarAgendaLabelService = (IKmCalendarAgendaLabelService) SpringBeanUtil
					.getBean("kmCalendarAgendaLabelService");
		}
		return kmCalendarAgendaLabelService;
	}

	protected ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	@Override
	public List<KmCalendarLabel> getLabelsByPerson(String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		//个人标签
		hqlInfo.setWhereBlock("kmCalendarLabel.fdCreator.fdId=:personId");
		hqlInfo.setParameter("personId", personId);
		hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
		List<KmCalendarLabel> calendarLabelList = new ArrayList<KmCalendarLabel>();
		calendarLabelList = findList(hqlInfo);
		return calendarLabelList;

	}

	@Override
	public KmCalendarLabel addAgendaLabel(String modelName, String personId)
			throws Exception {
		KmCalendarAgendaLabel agendaLabel = getKmCalendarAgendaLabelService()
				.getAgendaLabel(modelName);
		if (agendaLabel != null) {
			KmCalendarLabel kmCalendarLabel = new KmCalendarLabel();
			kmCalendarLabel.setFdId(IDGenerator.generateID());
			kmCalendarLabel.setFdColor(agendaLabel.getFdColor());
			kmCalendarLabel
					.setFdCreator((SysOrgPerson) getSysOrgPersonService()
							.findByPrimaryKey(personId));
			kmCalendarLabel.setFdDescription(null);
			kmCalendarLabel.setFdModelName(modelName);
			kmCalendarLabel.setFdName(agendaLabel.getFdName());
			kmCalendarLabel.setFdOrder(200);
			add(kmCalendarLabel);
			return kmCalendarLabel;
		}
		return null;
	}

	@Override
	public KmCalendarLabel findLabel(String modelName, String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("kmCalendarLabel.fdModelName=:modelName and kmCalendarLabel.fdCreator.fdId=:personId");
		hqlInfo.setParameter("modelName", modelName);
		hqlInfo.setParameter("personId", personId);
		List<KmCalendarLabel> calendarLabelList = new ArrayList<KmCalendarLabel>();
		calendarLabelList = findList(hqlInfo);
		if (calendarLabelList != null && calendarLabelList.size() > 0) {
			return calendarLabelList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String fdId =  super.add(modelObj);
		doSyncAfterCommit(modelObj.getFdId(),"add");
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		doSyncAfterCommit(modelObj.getFdId(),"update");
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		UserOperHelper.logDelete(modelObj);
		if (dispatchCoreService != null) {
			dispatchCoreService.delete(modelObj);
		}
		getKmCalendarMainService().clearCalendarLabel(modelObj.getFdId());
		getBaseDao().delete(modelObj);
		doSyncAfterCommit(modelObj.getFdId(),"delete");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteBatch(List<String> labelIds) throws Exception {
		List<KmCalendarLabel> kmCalendarLabels = getBaseDao().findByPrimaryKeys(labelIds.toArray(new String[0]));
		for (KmCalendarLabel kmCalendarLabel : kmCalendarLabels) {
			delete(kmCalendarLabel);
		}
	}

	@Override
	public void updLabelSelect(KmCalendarLabel kmCalendarLabel, String updateType) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Map> paramList = new ArrayList<>();
		if("add".equals(updateType)){
			sqlBuilder.append("insert into km_calendar_label");
			sqlBuilder.append(" (fd_id,fd_name,fd_creator_id,fd_selected_flag,fd_common_flag) ");
			sqlBuilder.append(" values (?,?,?,?,?)");
			//参数列表
			Map pMap1 = new HashMap();
			pMap1.put("dataType","String");
			pMap1.put("dataValue",kmCalendarLabel.getFdId());
			paramList.add(pMap1);
			Map pMap2 = new HashMap();
			pMap2.put("dataType","String");
			pMap2.put("dataValue",kmCalendarLabel.getFdName());
			paramList.add(pMap2);
			Map pMap3 = new HashMap();
			pMap3.put("dataType","String");
			pMap3.put("dataValue",kmCalendarLabel.getFdCreator().getFdId());
			paramList.add(pMap3);
			Map pMap4 = new HashMap();
			pMap4.put("dataType","Boolean");
			pMap4.put("dataValue",kmCalendarLabel.getFdSelectedFlag());
			paramList.add(pMap4);
			Map pMap5 = new HashMap();
			pMap5.put("dataType","String");
			pMap5.put("dataValue",kmCalendarLabel.getFdCommonFlag());
			paramList.add(pMap5);

		} else if("update".equals(updateType)){
			sqlBuilder.append("update km_calendar_label SET fd_selected_flag = ?");
			sqlBuilder.append(" WHERE fd_id = ? ");
			Map pMap1 = new HashMap();
			pMap1.put("dataType","Boolean");
			pMap1.put("dataValue",kmCalendarLabel.getFdSelectedFlag());
			paramList.add(pMap1);
			Map pMap3 = new HashMap();
			pMap3.put("dataType","String");
			pMap3.put("dataValue",kmCalendarLabel.getFdId());
			paramList.add(pMap3);
		}

		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		PreparedStatement kmCalendarLabelStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			kmCalendarLabelStatement = conn.prepareStatement(sqlBuilder.toString());
			int i = 1;
			for(Map map : paramList){
				if("String".equals(map.get("dataType"))){
					kmCalendarLabelStatement.setString(i, (String) map.get("dataValue"));
				} else if("Date".equals(map.get("dataType"))){
					kmCalendarLabelStatement.setDate(i, (java.sql.Date) map.get("dataValue"));
				} else if("Boolean".equals(map.get("dataType"))){
					kmCalendarLabelStatement.setBoolean(i, (Boolean) map.get("dataValue"));
				}

				i++;
			}

			kmCalendarLabelStatement.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			logger.error("更新标签选中取消发生异常："+ex);
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(kmCalendarLabelStatement);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void doSyncAfterCommit(String labelId,String optType){
		multicaster.attatchEvent(new EventOfTransactionCommit(new SyncLabelModel(labelId, optType)), new IEventAsyncCallBack() {
			@Override
			public void execute(ApplicationEvent event) throws Throwable {
				Object obj = event.getSource();
				if (obj instanceof SyncLabelModel) {
					SyncLabelModel model = (SyncLabelModel) obj;
					SyncOutThread t = new SyncOutThread(model.getLabelId(),model.getOptType());
					CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
					if (!manager.isStarted()) {
						manager.start();
					}
					manager.submit(t);
				}
			}
		});
	}

	class SyncLabelModel {
		private String labelId;
		private String optType;
		public SyncLabelModel(String labelId,String optType) {
			this.labelId = labelId;
			this.optType = optType;
		}
		public String getLabelId() {
			return labelId;
		}
		public String getOptType() {
			return optType;
		}
	}

	private static final String CMS_POINT = "com.landray.kmss.km.calendar.cms";
	private static final String CMS_POINT_LABEL_ITEM = "syncOutLabel";
	private static IExtension[] extensions = null;

	class SyncOutThread extends Thread {

		private String labelId;

		private String optType;

		public SyncOutThread(String labelId,String optType) {
			this.labelId = labelId;
			this.optType = optType;
		}

		@Override
		public void run(){
			if (extensions == null) {
				extensions = Plugin.getExtensions(CMS_POINT, "*", CMS_POINT_LABEL_ITEM);
			}
			if (extensions != null && extensions.length > 0) {
				for (IExtension extension : extensions) {
					ISyncOutLabelProvider provider = (ISyncOutLabelProvider) Plugin.getParamValue(extension, "provider");
					if (provider.isNeedSyncro(labelId)) {
						operateLabel(provider);
					}
				}
			}
		}

		private void operateLabel(ISyncOutLabelProvider provider){
			try{
				if("add".equals(optType)){
					provider.addLabel(labelId);
				}else if("update".equals(optType)){
					provider.updateLabel(labelId);
				}else if("delete".equals(optType)){
					provider.deleteLabel(labelId);
				}
			}catch (Exception e) {
				logger.error("", e);
			}
		}
	}

}
