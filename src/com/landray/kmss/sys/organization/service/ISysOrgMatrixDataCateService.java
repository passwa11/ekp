package com.landray.kmss.sys.organization.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.AutoArrayList;

/**
 * 矩阵数据分组
 * 
 * @author panyh
 * @date 2020-05-11
 *
 */
public interface ISysOrgMatrixDataCateService extends IBaseService {

	/**
	 * 根据矩阵id获取数据类别
	 * 
	 * @param matrixId
	 * @return
	 */
	public AutoArrayList getDataCates(String matrixId) throws Exception;

	/**
	 * 删除无法矩阵关联的分组
	 * 
	 * @param matrixId
	 * @throws Exception
	 */
	public void deleteByNotMatrix() throws Exception;

}
