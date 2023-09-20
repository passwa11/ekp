package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthListService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;

public class KmCalendarAuthListServiceImp extends BaseServiceImp implements IKmCalendarAuthListService {

	private IKmCalendarMainService kmCalendarMainService;

	public void setKmCalendarMainService(
			IKmCalendarMainService kmCalendarMainService) {
		this.kmCalendarMainService = kmCalendarMainService;
	}

	@Override
	public void updateCalendarByAddList(KmCalendarAuth auth,
			KmCalendarAuthList authList) throws Exception {
		Boolean isShare = authList.getFdIsShare();
		if (BooleanUtils.isTrue(isShare)) {
			// 变更人员
			List<SysOrgElement> elementList = authList.getFdPerson();
			Boolean isRead = authList.getFdIsRead();
			Boolean isModify = authList.getFdIsModify();
			Date shareDate = authList.getFdShareDate();
			List<KmCalendarMain> calendars = kmCalendarMainService
					.getHistoryCalendars(shareDate, "event", true,
							auth.getDocCreator().getFdId(), null);
			if (calendars != null && !calendars.isEmpty()) {
				for (KmCalendarMain calendar : calendars) {
					List authReaders = calendar.getAuthReaders();
					List authEditors = calendar.getAuthEditors();
					for (SysOrgElement orgElement : elementList) {
						// 增加可阅读者
						if (BooleanUtils.isTrue(isRead)
								&& !authReaders.contains(orgElement)) {
							calendar.getAuthReaders().add(orgElement);
						}
						// 增加可维护者
						if (BooleanUtils.isTrue(isModify)
								&& !authEditors.contains(orgElement)) {
							calendar.getAuthEditors().add(orgElement);
						}
					}
					kmCalendarMainService.update(calendar);
				}
			}
		}
	}

	private Map<String, List> getFilterDataMap(KmCalendarAuth kmCalendarAuth,
			KmCalendarAuthList kmCalendarAuthList) {
		Map<String, List> rtnMap = new HashMap<>();
		List<SysOrgElement> authReaders = new ArrayList<>();
		List<SysOrgElement> authModifiers = new ArrayList<>();
		List<KmCalendarAuthList> authLists = new ArrayList<>();
		List<KmCalendarAuthList> filterList = kmCalendarAuth
				.getKmCalendarAuthList();
		if (filterList != null && !filterList.isEmpty()) {
			for (KmCalendarAuthList list : filterList) {
				if (list.getFdId().equals(kmCalendarAuthList.getFdId())) {
					continue;
				}
				if (BooleanUtils.isTrue(list.getFdIsPartShare())) {
					continue;
				}
				authLists.add(list);
				if (BooleanUtils.isTrue(list.getFdIsRead())) {
					ArrayUtil.concatTwoList(list.getFdPerson(), authReaders);
				}
				if (BooleanUtils.isTrue(list.getFdIsModify())) {
					ArrayUtil.concatTwoList(list.getFdPerson(), authModifiers);
				}
			}
		}
		rtnMap.put("authReaders", authReaders);
		rtnMap.put("authModifiers", authModifiers);
		rtnMap.put("authLists", authLists);
		return rtnMap;
	}

	@Override
	public void updateCalendarByDeleteList(KmCalendarAuth auth,
			KmCalendarAuthList authList) throws Exception {
		Map<String, List> map = getFilterDataMap(auth, authList);
		List<SysOrgElement> dbReaders = map.get("authReaders");
		List<SysOrgElement> dbModifiers = map.get("authModifiers");
		List<KmCalendarAuthList> dbList = map.get("authLists");
		if (BooleanUtils.isTrue(authList.getFdIsShare())) {
			List<SysOrgElement> fdPerson = authList.getFdPerson();
			boolean isRead = BooleanUtils.isTrue(authList.getFdIsRead());
			boolean isModify = BooleanUtils.isTrue(authList.getFdIsModify());
			List<KmCalendarMain> calendars = kmCalendarMainService
					.getHistoryCalendars(authList.getFdShareDate(), "event",
							true, auth.getDocCreator().getFdId(), null);
			if (calendars != null && !calendars.isEmpty()) {
				for (SysOrgElement orgElement : fdPerson) {
					Date minShareDate = getMinShareDate(dbList, orgElement);
					if (isRead) {
						if (!dbReaders.contains(orgElement)
								|| (dbReaders.contains(orgElement)
										&& minShareDate == null)) {
							for (KmCalendarMain calendar : calendars) {
								// 删除可阅读者
								List authReaders = calendar.getAuthReaders();
								if (authReaders.contains(orgElement)) {
									calendar.getAuthReaders()
											.remove(orgElement);
									kmCalendarMainService.update(calendar);
								}
							}
						} else {
							if (minShareDate.getTime() > authList
									.getFdShareDate().getTime()) {
								List<KmCalendarMain> allCalendars = new ArrayList<>();
								allCalendars.addAll(calendars);
								List<KmCalendarMain> noUpdateCalendars = kmCalendarMainService
										.getHistoryCalendars(minShareDate,
												"event", true,
												auth.getDocCreator().getFdId(),
												null);
								allCalendars.removeAll(noUpdateCalendars);
								for (KmCalendarMain calendar : allCalendars) {
									// 删除可阅读者
									List authReaders = calendar
											.getAuthReaders();
									if (authReaders.contains(orgElement)) {
										calendar.getAuthReaders()
												.remove(orgElement);
										kmCalendarMainService.update(calendar);
									}
								}
							}
						}
					}
					if (isModify) {
						if (!dbModifiers.contains(orgElement)
								|| (dbModifiers.contains(orgElement)
										&& minShareDate == null)) {
							for (KmCalendarMain calendar : calendars) {
								// 删除可维护者
								List authEditors = calendar.getAuthEditors();
								if (authEditors.contains(orgElement)) {
									calendar.getAuthEditors()
											.remove(orgElement);
									kmCalendarMainService.update(calendar);
								}
							}
						} else {
							if (minShareDate.getTime() > authList
									.getFdShareDate().getTime()) {
								List<KmCalendarMain> allCalendars = new ArrayList<>();
								allCalendars.addAll(calendars);
								List<KmCalendarMain> noUpdateCalendars = kmCalendarMainService
										.getHistoryCalendars(minShareDate,
												"event", true,
												auth.getDocCreator().getFdId(),
												null);
								allCalendars.removeAll(noUpdateCalendars);
								for (KmCalendarMain calendar : allCalendars) {
									// 删除可维护者
									List authEditors = calendar
											.getAuthEditors();
									if (authEditors.contains(orgElement)) {
										calendar.getAuthEditors()
												.remove(orgElement);
										kmCalendarMainService.update(calendar);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private Date getMinShareDate(List<KmCalendarAuthList> lists,
			SysOrgElement orgElement) {
		Date rtnDate = null;
		if (lists != null && !lists.isEmpty()) {
			for (int i = 0; i < lists.size(); i++) {
				KmCalendarAuthList authList = lists.get(i);
				if (BooleanUtils.isTrue(authList.getFdIsShare())
						&& authList.getFdShareDate() != null
						&& authList.getFdPerson().contains(orgElement)) {
					if (rtnDate != null) {
						if (rtnDate.getTime() > authList.getFdShareDate()
								.getTime()) {
							rtnDate = authList.getFdShareDate();
						}
					} else {
						rtnDate = authList.getFdShareDate();
					}
				}
			}
		}
		return rtnDate;
	}

	@Override
	public void updateCalendarByEditList(KmCalendarAuth auth,
			KmCalendarAuthList updateList_pre,
			KmCalendarAuthList updateList_after) throws Exception {
		boolean fdIsShare_pre = BooleanUtils
				.isTrue(updateList_pre.getFdIsShare());
		boolean fdIsShare_after = BooleanUtils
				.isTrue(updateList_after.getFdIsShare());
		Date fdShareDate_pre = updateList_pre.getFdShareDate();
		Date fdShareDate_after = updateList_after.getFdShareDate();
		if (fdIsShare_pre && fdShareDate_pre != null) {
			// 权限更新前同步历史日程
			if (fdIsShare_after && fdShareDate_after != null) {
				updateCalendarByDeleteList(auth, updateList_pre);
				updateCalendarByAddList(auth, updateList_after);
			} else {
				updateCalendarByDeleteList(auth, updateList_pre);
			}
		} else {
			// 权限更新前不同步历史日程
			if (fdIsShare_after && fdShareDate_after != null) {
				updateCalendarByAddList(auth, updateList_after);
			}
		}
	}

	@Override
	public List<KmCalendarAuthList> getPartShareAuthList(List orgIds)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String whereBlock = HQLUtil
				.buildLogicIN("kmCalendarAuthList.fdPerson.fdId", orgIds);
		whereBlock += " and kmCalendarAuthList.fdIsPartShare =:isPartShare ";
		hqlInfo.setParameter("isPartShare", Boolean.TRUE);
		hqlInfo.setWhereBlock(whereBlock);
		List<KmCalendarAuthList> list = findList(hqlInfo);
		return list;
	}

}
