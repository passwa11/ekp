package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthListService;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmCalendarUserShareGroupDataBean implements IXMLDataBean {

	private IKmCalendarAuthService kmCalendarAuthService;

	public void
			setKmCalendarAuthService(
					IKmCalendarAuthService kmCalendarAuthService) {
		this.kmCalendarAuthService = kmCalendarAuthService;
	}

	private IKmCalendarAuthListService kmCalendarAuthListService;

	public void
			setKmCalendarAuthListService(
					IKmCalendarAuthListService kmCalendarAuthListService) {
		this.kmCalendarAuthListService = kmCalendarAuthListService;
	}

	@Override
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		List<SysOrgElement> totalPersons = new ArrayList<>();
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setSelectBlock("kmCalendarAuth.docCreator");
		hqlInfo.setJoinBlock(
				"left join kmCalendarAuth.authReaders readers left join kmCalendarAuth.authEditors editors left join kmCalendarAuth.authModifiers modifiers");
		String whereBlock = "kmCalendarAuth.docCreator.fdIsAvailable=:fdIsAvailable and ("
				+ HQLUtil.buildLogicIN("readers.fdId", orgIds);
		whereBlock = StringUtil.linkString(whereBlock, " or ",
				HQLUtil.buildLogicIN("editors.fdId", orgIds));
		whereBlock = StringUtil.linkString(whereBlock, " or ",
				HQLUtil.buildLogicIN("modifiers.fdId", orgIds));
		whereBlock = whereBlock + ")";
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(whereBlock);
		List<SysOrgElement> authPersons = kmCalendarAuthService
				.findList(hqlInfo);
		ArrayUtil.concatTwoList(authPersons, totalPersons);

		// 增加单独共享人员
		List<SysOrgElement> partSharePersons = new ArrayList<SysOrgElement>();
		List<KmCalendarAuthList> authList = kmCalendarAuthListService
				.getPartShareAuthList(orgIds);
		if (authList != null && !authList.isEmpty()) {
			for (KmCalendarAuthList kmCalendarAuthList : authList) {
				KmCalendarAuth auth = kmCalendarAuthList.getFdAuth();
				if (auth != null) {
					partSharePersons.add(auth.getDocCreator());
				}
			}
		}
		ArrayUtil.concatTwoList(partSharePersons, totalPersons);

		if (totalPersons != null && !totalPersons.isEmpty()) {
			for (SysOrgElement element : totalPersons) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", element.getFdId());
				map.put("name", element.getFdName());
				rtnList.add(map);
			}
		}
		return rtnList;
	}

}
