package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingDeviceDao;
import com.landray.kmss.km.imeeting.model.KmImeetingDevice;
import com.landray.kmss.util.UserUtil;

/**
 * 辅助设备数据访问接口实现
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingDeviceDaoImp extends BaseDaoImp implements
		IKmImeetingDeviceDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingDevice kmImeetingDevice = (KmImeetingDevice) modelObj;
		if (kmImeetingDevice.getDocCreator() == null) {
			kmImeetingDevice.setDocCreator(UserUtil.getUser());
		}
		if (kmImeetingDevice.getDocCreateTime() == null) {
			kmImeetingDevice.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}
}
