package com.landray.kmss.km.calendar.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.calendar.model.KmCalendarMainGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarMainGroupService;

public class KmCalendarMainGroupServiceImp extends BaseServiceImp
		implements IKmCalendarMainGroupService {

	@Override
	public KmCalendarMainGroup findMainGroupByMainId(String fdMainId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join kmCalendarMainGroup.fdMainList fdMainList");
		hqlInfo.setWhereBlock("fdMainList.fdId=:fdMainId");
		hqlInfo.setParameter("fdMainId", fdMainId);
		List<KmCalendarMainGroup> list = findList(hqlInfo);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
