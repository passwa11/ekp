package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingOutsignDao;
import com.landray.kmss.km.imeeting.model.KmImeetingOutsign;

public class KmImeetingOutsignDaoImp extends BaseDaoImp
		implements IKmImeetingOutsignDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
		KmImeetingOutsign kmImeetingOutsign = (KmImeetingOutsign) modelObj;
		if (kmImeetingOutsign.getDocCreateTime() == null) {
			kmImeetingOutsign.setDocCreateTime(new Date());
        }
		return super.add(kmImeetingOutsign);
    }
}
