package com.landray.kmss.km.calendar.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.calendar.dao.IKmCalendarSyncMappingDao;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.util.TransactionUtils;
import org.springframework.transaction.TransactionStatus;

/**
 * 同步映射关联业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarSyncMappingServiceImp extends BaseServiceImp implements
		IKmCalendarSyncMappingService {

	@Override
	@SuppressWarnings("unchecked")
	public List<String> findCalendarIds(String appKey, String uuid)
			throws Exception {
		if (uuid.indexOf("###") > 0) {
			uuid = uuid.substring(uuid.indexOf("###") + 3);
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmCalendarSyncMapping.fdCalendarId");
		hqlInfo
				.setWhereBlock("kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid like :fdAppUuid");
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdAppUuid", "%" + uuid);
		List<String> list = (List<String>) findList(hqlInfo);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> findCalendarIds(String appKey, String uuid,
			String personId) throws Exception {
		if (uuid.indexOf("###") > 0) {
			uuid = uuid.substring(uuid.indexOf("###") + 3);
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmCalendarSyncMapping.fdCalendarId");
		hqlInfo
				.setFromBlock("KmCalendarSyncMapping kmCalendarSyncMapping,KmCalendarMain kmCalendarMain");
		hqlInfo
				.setWhereBlock("kmCalendarSyncMapping.fdCalendarId = kmCalendarMain.fdId and kmCalendarMain.docCreator.fdId = :personId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid like :fdAppUuid");
		hqlInfo.setParameter("personId", personId);
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdAppUuid", "%" + uuid);
		List<String> list = (List<String>) findList(hqlInfo);
		return list;
	}

	@Override
	public List<String> getOwnerIds(String appKey, String uuid)
			throws Exception {
		if (uuid.indexOf("###") > 0) {
			uuid = uuid.substring(uuid.indexOf("###") + 3);
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setFromBlock("KmCalendarSyncMapping kmCalendarSyncMapping,KmCalendarMain kmCalendarMain");
		hqlInfo.setSelectBlock("kmCalendarMain.docOwner.fdId");
		hqlInfo
				.setWhereBlock("kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid like :fdAppUuid");
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdAppUuid", "%" + uuid);
		List<String> list = null;
		//开启只读事务，避免读写事务嵌套异常 #166413
		Boolean isException = false;
		TransactionStatus status = TransactionUtils.beginNewReadTransaction();
		try {
			list = (List<String>) findList(hqlInfo);
		}catch (Exception e){
			isException =true;
			e.printStackTrace();
		} finally {
			if(isException && status !=null){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}

		if (list != null && list.size() > 0) {
            return list;
        }
		return null;
	}

	@Override
	public List<KmCalendarSyncMapping> findByCalendarId(String calendarId)
			throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmCalendarSyncMapping.fdCalendarId=:calendarId");
		hqlInfo.setParameter("calendarId", calendarId);

		List<KmCalendarSyncMapping> list = (List<KmCalendarSyncMapping>) findList(hqlInfo);
		return list;
	}

	@Override
	public KmCalendarSyncMapping findByAppKeyAndCalendarId(String appKey,
														   String calendarId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("kmCalendarSyncMapping.fdAppKey=:appKey and kmCalendarSyncMapping.fdCalendarId=:calendarId ");
		hqlInfo.setParameter("appKey", appKey);
		hqlInfo.setParameter("calendarId", calendarId);
		List<KmCalendarSyncMapping> list = (List<KmCalendarSyncMapping>) findList(hqlInfo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getAppUuid(String appKey, String calendarId) throws Exception {
		KmCalendarSyncMapping mapping = findByAppKeyAndCalendarId(appKey,
				calendarId);
		if (mapping != null) {
            return mapping.getFdAppUuid();
        }
		return null;
	}

	@Override
	public void delete(String appKey, String uuid) {
		((IKmCalendarSyncMappingDao) getBaseDao()).delete(appKey, uuid);
	}

	@Override
	public void delete(String appKey, String uuid, String calendarId) {
		((IKmCalendarSyncMappingDao) getBaseDao()).delete(appKey, uuid,
				calendarId);
	}

	@Override
	public void deleteByCalendarId(String calendarId) {
		((IKmCalendarSyncMappingDao) getBaseDao()).delete(calendarId);
	}

}
