package com.landray.kmss.km.imeeting.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncMappingService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;

/**
 * 会议同步映射关联业务接口实现
 */
public class KmImeetingSyncMappingServiceImp extends ExtendDataServiceImp implements IKmImeetingSyncMappingService {

	@Override
    @SuppressWarnings("unchecked")
	public List<String> findImeetingIds(String appKey, String uuid)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingSyncMapping.fdMeetingId");
		// hqlInfo
		// .setWhereBlock(
		// "kmImeetingSyncMapping.fdAppKey=:fdAppKey and
		// kmImeetingSyncMapping.fdAppUuid like :fdAppUuid");
		hqlInfo
				.setWhereBlock(
						"kmImeetingSyncMapping.fdAppUuid = :fdAppUuid");
		// hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdAppUuid", uuid);
		List<String> list = (List<String>) findList(hqlInfo);
		return list;
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<String> findImeetingIds(String appKey, String uuid,
			String personId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingSyncMapping.fdMeetingId");
		hqlInfo
				.setFromBlock(
						"KmImeetingSyncMapping kmImeetingSyncMapping,KmImeetingMain kmImeetingMain");
		// hqlInfo
		// .setWhereBlock(
		// "kmImeetingSyncMapping.fdMeetingId = kmImeetingMain.fdId and
		// kmImeetingMain.docCreator.fdId = :personId and
		// kmImeetingSyncMapping.fdAppKey=:fdAppKey and
		// kmImeetingSyncMapping.fdAppUuid like :fdAppUuid");
		//
		hqlInfo
				.setWhereBlock(
						"kmImeetingSyncMapping.fdMeetingId = kmImeetingMain.fdId and kmImeetingMain.docCreator.fdId = :personId and kmImeetingSyncMapping.fdAppUuid = :fdAppUuid");

		hqlInfo.setParameter("personId", personId);
		// hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdAppUuid", uuid);
		List<String> list = (List<String>) findList(hqlInfo);
		return list;
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<String> findImeetingIdsByIcalId(String appKey, String icalId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingSyncMapping.fdMeetingId");
		hqlInfo
				.setWhereBlock(
						"kmImeetingSyncMapping.fdAppIcalId = :fdAppIcalId");
		hqlInfo.setParameter("fdAppIcalId", icalId);
		List<String> list = (List<String>) findList(hqlInfo);
		return list;
	}

}
