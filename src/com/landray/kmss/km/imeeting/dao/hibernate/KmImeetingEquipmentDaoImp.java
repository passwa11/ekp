package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingEquipmentDao;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.UserUtil;


/**
 * 会议辅助设备数据访问接口实现
 * 
 * @author 
 * @version 1.0 2016-01-25
 */
public class KmImeetingEquipmentDaoImp extends ExtendDataDaoImp implements IKmImeetingEquipmentDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingEquipment kmImeetingEquipment = (KmImeetingEquipment) modelObj;
		if (kmImeetingEquipment.getDocCreator() == null) {
			kmImeetingEquipment.setDocCreator(UserUtil.getUser());
		}
		if (kmImeetingEquipment.getDocCreateTime() == null) {
			kmImeetingEquipment.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

}
