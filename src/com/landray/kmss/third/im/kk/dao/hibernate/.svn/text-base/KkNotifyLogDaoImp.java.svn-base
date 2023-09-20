package com.landray.kmss.third.im.kk.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.im.kk.dao.IKkNotifyLogDao;
import com.landray.kmss.third.im.kk.util.NotifyConfigUtil;
import com.landray.kmss.util.StringUtil;

/**
 * kk待办集成日志数据访问接口实现
 * 
 * @author
 * @version 1.0 2012-04-13
 */
public class KkNotifyLogDaoImp extends BaseDaoImp implements IKkNotifyLogDao {

	private static int days = 30;

	

	@Override
    public void backUp() throws Exception {
		String[] bak_field = { "fdId", "fdSubject", "kkNotifyData", "sendTime",
				"rtnTime", "kkRtnMsg", "fdParams" };
		String bak_field_String = StringUtils.join(bak_field, ",");
		Calendar backupCal = Calendar.getInstance();
		// 默认设定归档时间
		int day = Integer.valueOf(NotifyConfigUtil
				.getNotifyConfig("kmss.ims.notify.kk.backup"));
		backupCal.add(Calendar.DAY_OF_MONTH, -day);
		Date backupDate = backupCal.getTime();
		super.getSession().createQuery(
				"insert into com.landray.kmss.third.im.kk.model.KkNotifyLogBak ("
						+ bak_field_String + ") select " + bak_field_String
						+ " from " + this.getModelName()
						+ " where sendTime<:backupdate").setDate("backupdate",
				backupDate).executeUpdate();
		super.getSession()
				.createQuery(
						"delete " + this.getModelName()
								+ " where sendTime<:backupdate").setDate(
						"backupdate", backupDate).executeUpdate();
	}

	@Override
	public void clean(String logDays) throws Exception {
		String logbak_days = logDays;
		if (StringUtil.isNotNull(logbak_days)) {
			try {
				days = Integer.parseInt(logbak_days);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.im.kk.model.KkNotifyLogBak where sendTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}

}
