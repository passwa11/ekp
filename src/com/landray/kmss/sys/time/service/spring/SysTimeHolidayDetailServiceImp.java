package com.landray.kmss.sys.time.service.spring;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.service.ISysTimeHolidayDetailService;
/**
 * 节假日明细设置业务接口实现
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayDetailServiceImp extends ExtendDataServiceImp
		implements
			ISysTimeHolidayDetailService {

	@Override
	public List<SysTimeHolidayDetail> getDatasByMainId(String fdMainId) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("sysTimeHolidayDetail.fdHoliday.fdId=:fdMainId");
		hqlInfo.setParameter("fdMainId", fdMainId);
		return this.findList(hqlInfo);
	}

}
