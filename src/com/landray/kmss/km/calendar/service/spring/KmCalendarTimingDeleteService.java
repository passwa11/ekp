package com.landray.kmss.km.calendar.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarTimingDeleteService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;
import org.springframework.transaction.TransactionStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class KmCalendarTimingDeleteService implements
		IKmCalendarTimingDeleteService {

	private IKmCalendarMainService kmCalendarMainService = null;

	public void setKmCalendarMainService(
			IKmCalendarMainService kmCalendarMainService) {
		this.kmCalendarMainService = kmCalendarMainService;
	}

	@Override
    public void deleteCalendarTiming() throws Exception {
		KmCalendarBaseConfig config = new KmCalendarBaseConfig();
		String keepDay = config.getFdKeepDay();
		if (StringUtil.isNull(keepDay)) {
			keepDay = "1095";
		}
		Calendar agoCal = Calendar.getInstance();
		agoCal.setTime(new Date());
		agoCal.add(Calendar.DATE, -Integer.parseInt(keepDay));
		agoCal.set(Calendar.MILLISECOND, 0);
		Date time = agoCal.getTime();
		HQLInfo mainHqlInfo=new HQLInfo();
		mainHqlInfo.setWhereBlock("kmCalendarMain.docFinishTime<= :time");
		mainHqlInfo.setParameter("time",time);
		mainHqlInfo.setRowSize(500);
		mainHqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		deleteRecords(mainHqlInfo);
	}

	private void deleteRecords(HQLInfo mainHqlInfo) throws Exception {
		boolean hasNext = false;
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			Page mainPage = kmCalendarMainService.findPage(mainHqlInfo);
			if(mainPage.getList() != null){
				Iterator mainIterator = mainPage.getList().iterator();
				while(mainIterator.hasNext()){
					KmCalendarMain main = (KmCalendarMain) mainIterator.next();
					kmCalendarMainService.getBaseDao().delete(main);
				}
			}
			TransactionUtils.commit(status);
			hasNext = mainPage.isHasNextPage();
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
			else if(hasNext){
				//一直循环删除数据，直到没有下一页数据为止
				deleteRecords(mainHqlInfo);
			}
		}
	}
}
