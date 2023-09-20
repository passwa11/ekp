package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingEquipmentService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.StringUtil;
/**
 * 会议辅助设备业务接口实现
 * 
 * @author 
 * @version 1.0 2016-01-25
 */
public class KmImeetingEquipmentServiceImp extends ExtendDataServiceImp
		implements IKmImeetingEquipmentService {

	private IKmImeetingMainService kmImeetingMainService;

	public void
			setKmImeetingMainService(
					IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	// 获得not in查询语句
	@Override
	public String buildLogicNotIN(String item,
								  List<KmImeetingEquipment> kmeqEquipments) {
		int n = (kmeqEquipments.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? kmeqEquipments.size() : (i + 1) * 1000;
			if (i > 0) {
                rtnStr.append(" or ");
            }
			rtnStr.append(item + " not  in (");
			StringBuffer tmpBuf = new StringBuffer();
			for (int j = i * 1000; j < size; j++) {
				tmpStr = kmeqEquipments.get(j).getFdId().replaceAll("'", "''");
				tmpBuf.append(",'").append(tmpStr).append("'");
			}
			tmpStr = tmpBuf.substring(1);
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
            return "(" + rtnStr.toString() + ")";
        } else {
            return rtnStr.toString();
        }
	}

	@Override
	public List<KmImeetingEquipment> findConflictEquipment(
			String fdHoldDate, String fdFinishDate, String meetingId,
			Locale locale) throws Exception {
		List<KmImeetingEquipment> conflictEquipments = new ArrayList<KmImeetingEquipment>();
		if (StringUtil.isNotNull(fdHoldDate)
				&& StringUtil.isNotNull(fdFinishDate)) {
			Date start = DateUtil.convertStringToDate(fdHoldDate,
					DateUtil.TYPE_DATETIME, locale);
			Date end = DateUtil.convertStringToDate(fdFinishDate,
					DateUtil.TYPE_DATETIME, locale);
			conflictEquipments = findConflictEquipmentInMain(meetingId, start,
					end);
		}
		return conflictEquipments;
	}

	public List<KmImeetingEquipment> findConflictEquipmentInMain(
			String meetingId, Date start, Date end) throws Exception {
		HQLInfo hql = new HQLInfo();
		String whereBlock = " kmImeetingMain.fdHoldDate<:fdFinishDate and kmImeetingMain.fdFinishDate>:fdHoldDate and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null) ");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock += " and kmImeetingMain.fdId<>:meetingId ";
			hql.setParameter("meetingId", meetingId);
		}
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		List<KmImeetingMain> kmImeetingMains = kmImeetingMainService
				.findList(hql);
		List<KmImeetingEquipment> equipments = new ArrayList<KmImeetingEquipment>();
		for (KmImeetingMain kmImeetingMain : kmImeetingMains) {
			ArrayUtil.concatTwoList(kmImeetingMain.getKmImeetingEquipments(),
					equipments);
		}

		HQLInfo hql2 = new HQLInfo();
		String whereBlock2 = hql2.getWhereBlock();
		whereBlock2 = StringUtil.linkString(whereBlock2, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null) ");
		whereBlock2 = StringUtil.linkString(whereBlock2, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdFinishDate>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and kmImeetingMain.fdPlace is not null and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock2 = StringUtil.linkString(whereBlock2, " and ",
					" kmImeetingMain.fdId<>:meetingId ");
			hql2.setParameter("meetingId", meetingId);
		}
		hql2.setParameter("fdHoldDate", start);
		hql2.setParameter("fdFinishDate", end);
		hql2.setWhereBlock(whereBlock2);
		List<KmImeetingMain> matchMainModels = kmImeetingMainService
				.findList(hql2);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			long rangeTime = mainEndDateTime.getTime()
					- mainStartDateTime.getTime();
			Date searchStart = new Date(start.getTime() - rangeTime);
			Date searchEnd = new Date(end.getTime() + rangeTime);
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					mainStartDateTime, searchStart, searchEnd);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + mainEndDateTime.getTime()
								- mainStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime()
						&& newEndDate.getTime() >= start.getTime()) {
					ArrayUtil.concatTwoList(main.getKmImeetingEquipments(),
							equipments);
				}
			}
		}
		return equipments;
	}

}

