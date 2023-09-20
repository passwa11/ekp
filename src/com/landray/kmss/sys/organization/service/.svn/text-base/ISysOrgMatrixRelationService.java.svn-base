package com.landray.kmss.sys.organization.service;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

/**
 * 矩阵关系
 * 
 * @author 潘永辉 2019年6月6日
 *
 */
public interface ISysOrgMatrixRelationService extends IBaseService {

	/**
	 * 更新数据页列宽
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void updateWidth(RequestContext request) throws Exception;

	/**
	 * 检测矩阵字段
	 *
	 * @param matrixId
	 * @return
	 * @throws Exception
	 */
	public JSONArray checkField(String matrixId) throws Exception;

	/**
	 * 删除字段
	 *
	 * @param matrixId
	 * @param field
	 * @throws Exception
	 */
	public void deleteField(String matrixId, String field) throws Exception;

	/**
	 * 深度检测数量，可兼容所有数据库
	 *
	 * @param matrixId
	 * @param field
	 * @throws Exception
	 */
	public int depthCheck(String matrixId, String field) throws Exception;

}
