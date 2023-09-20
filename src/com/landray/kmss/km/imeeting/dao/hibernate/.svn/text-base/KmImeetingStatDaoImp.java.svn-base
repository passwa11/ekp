package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingStatDao;
import com.landray.kmss.km.imeeting.model.KmImeetingStat;
import com.landray.kmss.util.UserUtil;

/**
 * 会议统计数据访问接口实现
 */
public class KmImeetingStatDaoImp extends BaseDaoImp implements IKmImeetingStatDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingStat kmImeetingStat = (KmImeetingStat) modelObj;
		if (kmImeetingStat.getDocCreator() == null) {
			kmImeetingStat.setDocCreator(UserUtil.getUser());
		}
		if (kmImeetingStat.getDocCreateTime() == null) {
			kmImeetingStat.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

}
