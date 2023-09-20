package com.landray.kmss.km.imeeting.service;

import java.util.List;
import java.util.Locale;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
/**
 * 会议辅助设备业务对象接口
 * 
 * @author 
 * @version 1.0 2016-01-25
 */
public interface IKmImeetingEquipmentService extends IBaseService {

	public List<KmImeetingEquipment>
			findConflictEquipment(String fdHoldDate, String fdFinishDate,
					String meetingId, Locale locale) throws Exception;

	public String buildLogicNotIN(String item,
			List<KmImeetingEquipment> equipments) throws Exception;
}
