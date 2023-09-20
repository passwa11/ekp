package com.landray.kmss.km.calendar.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;

import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.type.StandardBasicTypes;

public class KmCalendarLabelTransferTask extends KmCalendarLabelTransferChecker
		implements ISysAdminTransferTask {

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			updateAgendaLabel();
			updateLabel();
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}

	/**
	 * 更新用户标签表km_calendar_label 与 日程表km_calendar_main
	 * 
	 * @throws Exception
	 */
	private void updateLabel() throws Exception {
		IKmCalendarLabelService kmCalendarLabelService = (IKmCalendarLabelService) SpringBeanUtil
				.getBean("kmCalendarLabelService");
		IKmCalendarMainService kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
				.getBean("kmCalendarMainService");
		// 更新用户标签表km_calendar_label 与 日程表km_calendar_main
		String selectSql = "select a.fd_id,a.fd_model_name,a.fd_creator_id from km_calendar_label a "
				+ "where a.fd_model_name <>' ' and a.fd_model_name is not null and "
				+ "exists(select 1 from km_calendar_label b where "
				+ "a.fd_id<>b.fd_id and a.fd_creator_id=b.fd_creator_id and a.fd_model_name=b.fd_model_name )";
		NativeQuery sqlQuery = kmCalendarLabelService.getBaseDao()
				.getHibernateSession().createNativeQuery(selectSql);
		sqlQuery.setCacheable(true);
		sqlQuery.setCacheMode(CacheMode.NORMAL);
		sqlQuery.setCacheRegion("km-calendar");
		sqlQuery.addScalar("fd_id", StandardBasicTypes.STRING);
		sqlQuery.addScalar("fd_model_name", StandardBasicTypes.STRING);
		sqlQuery.addScalar("fd_creator_id", StandardBasicTypes.STRING);
		// map: fdCreatorId --> Map<fdModelName, fdId;fdId;...>
		Map<String, Map<String, String>> map = new HashMap<>();
		for (Object obj : sqlQuery.list()) {
			Object[] k = (Object[]) obj;
			String fdId = k[0].toString();
			String fdModelName = k[1].toString();
			String fdCreatorId = k[2].toString();
			if (!map.containsKey(fdCreatorId)) {
				Map<String, String> valueMap = new HashMap<>();
				valueMap.put(fdModelName, fdId);
				map.put(fdCreatorId, valueMap);
			} else {
				Map<String, String> valueMap = map.get(fdCreatorId);
				if (!valueMap.containsKey(fdModelName)) {
					valueMap.put(fdModelName, fdId);
				} else {
					valueMap.put(fdModelName,
							valueMap.get(fdModelName) + ";" + fdId);
				}
				map.put(fdCreatorId, valueMap);
			}
		}
		for (Map.Entry<String, Map<String, String>> entry : map
				.entrySet()) {
			String creatorId = entry.getKey();
			Map<String, String> mapValue = entry.getValue();
			for (Map.Entry<String, String> valueEntry : mapValue
					.entrySet()) {
				String modelName = valueEntry.getKey();
				if (StringUtil.isNotNull(valueEntry.getValue())) {
					// 更新日程表关联标签
					List<String> idList = Arrays
							.asList(valueEntry.getValue().split(";"));
					String updateToId = idList.get(0);
					String hql = "update com.landray.kmss.km.calendar.model.KmCalendarMain kmCalendarMain set kmCalendarMain.docLabel.fdId = :labelId"
							+ " where " + HQLUtil.buildLogicIN(
									"kmCalendarMain.docLabel.fdId", idList);
					Query query = kmCalendarMainService.getBaseDao()
							.getHibernateSession().createQuery(hql);
					query.setParameter("labelId", updateToId);
					query.executeUpdate();

					// 删除用户标签表的多余标签
					NativeQuery deleteQuery = kmCalendarLabelService
							.getBaseDao()
							.getHibernateSession()
							.createNativeQuery(
									"delete from km_calendar_label where fd_creator_id=:creatorId and fd_model_name=:modelName and fd_id != :fdId");
					deleteQuery.addSynchronizedQuerySpace("km_calendar_label");
					deleteQuery.setParameter("creatorId", creatorId);
					deleteQuery.setParameter("modelName", modelName);
					deleteQuery.setParameter("fdId", updateToId);
					deleteQuery.executeUpdate();
				}
			}
		}
	}

	/**
	 * 更新系统标签表km_calendar_agenda_label
	 * 
	 * @throws Exception
	 */
	private void updateAgendaLabel() throws Exception {
		IKmCalendarAgendaLabelService kmCalendarAgendaLabelService = (IKmCalendarAgendaLabelService) SpringBeanUtil
				.getBean("kmCalendarAgendaLabelService");
		String hql = "select a from com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel a "
				+ "where exists(select 1 from com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel b where "
				+ "a.fdId<>b.fdId and a.fdAgendaModelName=b.fdAgendaModelName)";
		Query query = kmCalendarAgendaLabelService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-calendar");
		List<KmCalendarAgendaLabel> agendaLabels  = query.list();
		if (agendaLabels != null && !agendaLabels.isEmpty()) {
			Map<String, List<KmCalendarAgendaLabel>> map = new HashMap<>();
			for (int i = 0; i < agendaLabels.size(); i++) {
				KmCalendarAgendaLabel agendaLabel = agendaLabels.get(i);
				List<KmCalendarAgendaLabel> list = null;
				if (!map.containsKey(agendaLabel.getFdAgendaModelName())) {
					list = new ArrayList<>();
				} else {
					list = map.get(agendaLabel.getFdAgendaModelName());
				}
				list.add(agendaLabel);
				map.put(agendaLabel.getFdAgendaModelName(), list);
			}
			for (Map.Entry<String, List<KmCalendarAgendaLabel>> entry : map.entrySet()) {
				String modelName = entry.getKey();
				List<KmCalendarAgendaLabel> list = entry.getValue();
				KmCalendarAgendaLabel isAgendalabel = null;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIsAgendaLabel()) {
						isAgendalabel = list.get(i);
						break;
					}
				}
				if (isAgendalabel == null) {
					isAgendalabel = list.get(0);
				}
				// 更新系统标签状态为启用
				if (!isAgendalabel.getFdIsAvailable()) {
					isAgendalabel.setFdIsAvailable(true);
					kmCalendarAgendaLabelService.update(isAgendalabel);
				}
				// 删除系统标签表的多余标签
				NativeQuery deleteQuery = kmCalendarAgendaLabelService
						.getBaseDao()
						.getHibernateSession()
						.createNativeQuery(
								"delete from km_calendar_agenda_label where fd_model_name=:modelName and fd_id != :fdId");
				deleteQuery.addSynchronizedQuerySpace("km_calendar_agenda_label");
				deleteQuery.setParameter("modelName", modelName);
				deleteQuery.setParameter("fdId", isAgendalabel.getFdId());
				deleteQuery.executeUpdate();
			}
		}
	}

}
