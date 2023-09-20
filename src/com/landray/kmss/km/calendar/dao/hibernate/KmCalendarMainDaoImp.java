package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.dao.IKmCalendarMainDao;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 日程管理主文档数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarMainDaoImp extends BaseDaoImp implements
		IKmCalendarMainDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
		if (kmCalendarMain.getDocCreateTime() == null) {
			kmCalendarMain.setDocCreateTime(new Date());
		}
		if (kmCalendarMain.getDocCreator() == null) {
			kmCalendarMain.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
		if (kmCalendarMain.getDocCreateTime() == null) {
			kmCalendarMain.setDocCreateTime(new Date());
		}
		if (kmCalendarMain.getDocCreator() == null) {
			kmCalendarMain.setDocCreator(UserUtil.getUser());
		}
		super.update(modelObj);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List findList(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		List rtnList = createHbmQuery(hqlInfo).list();
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());
		return rtnList;
	}

	@Override
	public void clearCalendarLabel(String labelId) throws Exception {
		String hql = "update com.landray.kmss.km.calendar.model.KmCalendarMain kmCalendarMain set kmCalendarMain.docLabel.fdId = null"
				+ " where kmCalendarMain.docLabel.fdId='" + labelId + "'";
		super.getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void batchClearCalendarLabel(List<String> labelId) throws Exception {
		if (labelId.size() == 0) {
            return;
        }
		String hql = "update com.landray.kmss.km.calendar.model.KmCalendarMain kmCalendarMain set kmCalendarMain.docLabel.fdId = null"
				+ " where " + HQLUtil.buildLogicIN("kmCalendarMain.docLabel.fdId", labelId);
		super.getSession().createQuery(hql).executeUpdate();
	}

}
