package com.landray.kmss.km.calendar.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class KmCalendarShareAuthTransferTask extends
		KmCalendarShareAuthTransferChecker implements ISysAdminTransferTask {

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		IKmCalendarAuthService kmCalendarAuthService = (IKmCalendarAuthService) SpringBeanUtil
				.getBean("kmCalendarAuthService");
		try {
			HQLInfo hqlInfo = new HQLInfo();
			List<KmCalendarAuth> kmCalendarAuths = new ArrayList<KmCalendarAuth>();
			kmCalendarAuths = kmCalendarAuthService.findList(hqlInfo);
			for (int i = 0; i < kmCalendarAuths.size(); i++) {
				KmCalendarAuth kmCalendarAuth = kmCalendarAuths.get(i);
				updateAuthList(kmCalendarAuth);
				kmCalendarAuthService.getBaseDao().update(kmCalendarAuth);
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}

	/**
	 * 以组织架构为单位，合并权限
	 * 
	 * @param kmCalendarAuth
	 * @throws Exception
	 */
	private void updateAuthList(KmCalendarAuth kmCalendarAuth)
			throws Exception {
		List<KmCalendarAuthList> authLists = kmCalendarAuth
				.getKmCalendarAuthList();
		List<KmCalendarAuthList> partShareList = new ArrayList<>();
		if (authLists == null) {
			authLists = new ArrayList<>();
		} else {
			if (!authLists.isEmpty()) {
				for (KmCalendarAuthList kmCalendarAuthList : authLists) {
					if (BooleanUtils
							.isTrue(kmCalendarAuthList.getFdIsPartShare())) {
						partShareList.add(kmCalendarAuthList);
					}
				}
			}
			authLists.clear();
		}
		List<SysOrgElement> editors = kmCalendarAuth.getAuthEditors();
		List<SysOrgElement> readers = kmCalendarAuth.getAuthReaders();
		List<SysOrgElement> modifiers = kmCalendarAuth.getAuthModifiers();
		
		// 所有组织架构
		Map<String, SysOrgElement> totalPersonMap = new HashMap<>();
		List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();
		ArrayUtil.concatTwoList(editors, totalPersons);
		ArrayUtil.concatTwoList(readers, totalPersons);
		ArrayUtil.concatTwoList(modifiers, totalPersons);
		for (SysOrgElement sysOrgElement : totalPersons) {
			totalPersonMap.put(sysOrgElement.getFdId(), sysOrgElement);
		}
		
		// 组织架构id --> 权限类型list
		Map<String, List<String>> map = new HashMap<>();
		if (editors != null && !editors.isEmpty()) {
			ArrayUtil.unique(editors);
			for (SysOrgElement sysOrgElement : editors) {
				List<String> list = new ArrayList<>();
				list.add("edit");
				map.put(sysOrgElement.getFdId(), list);
			}
		}
		if (readers != null && !readers.isEmpty()) {
			ArrayUtil.unique(readers);
			for (SysOrgElement sysOrgElement : readers) {
				if (!map.containsKey(sysOrgElement.getFdId())) {
					List<String> list = new ArrayList<>();
					list.add("read");
					map.put(sysOrgElement.getFdId(), list);
				} else {
					List<String> list = map.get(sysOrgElement.getFdId());
					list.add("read");
					map.put(sysOrgElement.getFdId(), list);
				}
			}
		}
		if (modifiers != null && !modifiers.isEmpty()) {
			ArrayUtil.unique(modifiers);
			for (SysOrgElement sysOrgElement : modifiers) {
				if (!map.containsKey(sysOrgElement.getFdId())) {
					List<String> list = new ArrayList<>();
					list.add("modify");
					map.put(sysOrgElement.getFdId(), list);
				} else {
					List<String> list = map.get(sysOrgElement.getFdId());
					list.add("modify");
					map.put(sysOrgElement.getFdId(), list);
				}
			}
		}

		// 生成kmCalendarAuthList
		KmCalendarAuthList kmCalendarAuthList = null;
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			String id = entry.getKey();
			List<String> type = entry.getValue();
			kmCalendarAuthList = new KmCalendarAuthList();
			List<SysOrgElement> personList = new ArrayList<>();
			personList.add(totalPersonMap.get(id));
			kmCalendarAuthList.setFdPerson(personList);
			kmCalendarAuthList.setFdIsShare(false);
			if (type.contains("edit")) {
				kmCalendarAuthList.setFdIsEdit(true);
			}
			if (type.contains("read")) {
				kmCalendarAuthList.setFdIsRead(true);
			}
			if (type.contains("modify")) {
				kmCalendarAuthList.setFdIsModify(true);
			}
			authLists.add(kmCalendarAuthList);
		}
		if (!partShareList.isEmpty()) {
			authLists.addAll(partShareList);
		}

		kmCalendarAuth.setKmCalendarAuthList(authLists);
	}

}
