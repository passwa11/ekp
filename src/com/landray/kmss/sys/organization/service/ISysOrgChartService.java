package com.landray.kmss.sys.organization.service;

import com.landray.kmss.common.service.IBaseService;

public interface ISysOrgChartService extends IBaseService {

	/**
	 * 获取所有该部门/机构的下结构
	 * 返回JSON格式数据
	 * @param fdId 机构ID
	 * @param isFirstTimeLoad 是否页面初始化时首次加载机构数据（true:是，false：否）
	 * @param expandLevel 展开层级（显示几层）
	 * @return
	 */
	String getChartData(String fdId, boolean isFirstTimeLoad, int expandLevel) throws Exception;

	/**
	 * 定时任务计算人员总数
	 * @throws Exception
	 */
	void countPersonsNumber() throws Exception;

}
