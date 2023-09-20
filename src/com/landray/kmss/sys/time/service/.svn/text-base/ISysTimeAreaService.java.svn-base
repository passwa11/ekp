package com.landray.kmss.sys.time.service;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.forms.SysTimeAreaForm;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;

import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 区域组设置业务对象接口
 */
public interface ISysTimeAreaService extends IBaseService {
	/**
	 * @param json
	 * @param fdId
	 * @param fdHolidayId
	 * @param fdOperType
	 *            操作类型 1:批量排班,2:按人员排班
	 * @throws Exception
	 */
	public void updateCalendar(String json, String fdId, String fdHolidayId,
			String fdOperType) throws Exception;

	SysTimeAreaForm cloneModel(IBaseModel model, IExtendForm form)
			throws Exception;

	public boolean equalList(List<SysTimeWorkDetail> times,
			List<SysTimeWorkDetail> times2);

	/**
	 * 获取人员排班的人员对应关系列表
	 * 只获取按个人排班的数据
	 * @param orgElementList
	 * @return
	 * @throws Exception
	 */
	public List<SysTimeOrgElementTime> getOrgElementTimes(List<String> orgElementList) throws Exception;
}
