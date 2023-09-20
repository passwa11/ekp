package com.landray.kmss.sys.time.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
/**
 * 节假日明细设置业务对象接口
 * 
 * @author 
 * @version 1.0 2017-09-26
 */
public interface ISysTimeHolidayDetailService extends IBaseService {
	List<SysTimeHolidayDetail> getDatasByMainId(String fdMainId)throws Exception;

}
