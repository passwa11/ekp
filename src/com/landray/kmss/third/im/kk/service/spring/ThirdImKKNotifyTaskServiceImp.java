package com.landray.kmss.third.im.kk.service.spring;

import java.util.Calendar;

import org.hibernate.query.Query;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * <P>新增待办推送表定时清理前一天推送失败的数据</P>
 * @author 孙佳
 * 2019年1月29日
 */
public class ThirdImKKNotifyTaskServiceImp extends BaseServiceImp {

	private IKkImNotifyService kkImNotifyService;

	public IKkImNotifyService getKkImNotifyService() {
		if (kkImNotifyService == null) {
			kkImNotifyService = (IKkImNotifyService) SpringBeanUtil.getBean("kkImNotifyService");
		}
		return kkImNotifyService;
	}

	public void clean() {
		Query query = this.getBaseDao().getHibernateSession()
				.createQuery(
						"delete from com.landray.kmss.third.im.kk.model.KkImNotify where fdFirstTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		query.setParameter("date", c.getTime());
		query.executeUpdate();
	}
}
