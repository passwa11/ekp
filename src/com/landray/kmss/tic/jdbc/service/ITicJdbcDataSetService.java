package com.landray.kmss.tic.jdbc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.service.IBaseService;

/**
 * 数据集管理业务对象接口
 * 
 * @author 
 * @version 1.0 2014-04-15
 */
public interface ITicJdbcDataSetService extends IBaseService {
	public void loadDbtableDatas(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
