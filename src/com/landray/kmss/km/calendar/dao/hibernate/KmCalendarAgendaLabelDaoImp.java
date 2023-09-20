package com.landray.kmss.km.calendar.dao.hibernate;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.calendar.dao.IKmCalendarAgendaLabelDao;

/**
 * 标签数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarAgendaLabelDaoImp extends BaseDaoImp implements
		IKmCalendarAgendaLabelDao {

	@Override
    public void deleteAgendaLabel() throws Exception {

		String hql = "delete KmCalendarAgendaLabel as kmCalendarAgendaLabel where kmCalendarAgendaLabel.isAgendaLabel=?";
		Query query = super.getSession().createQuery(hql);
		query.setBoolean(0, true);
		query.executeUpdate();

	}

}
