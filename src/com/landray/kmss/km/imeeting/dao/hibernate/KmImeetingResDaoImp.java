package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingResDao;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.util.UserUtil;

/**
 * 会议室信息数据访问接口实现
 */
public class KmImeetingResDaoImp extends BaseDaoImp implements
		IKmImeetingResDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingRes kmImeetingRes = (KmImeetingRes) modelObj;
		if (kmImeetingRes.getDocCreator() == null) {
			kmImeetingRes.setDocCreator(UserUtil.getUser());
		}
		if (kmImeetingRes.getDocCreateTime() == null) {
			kmImeetingRes.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

}
