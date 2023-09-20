package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.sunbor.web.tag.Page;

import java.util.Map;

/**
 * 组织矩阵
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public interface ISysOrgMatrixDao extends IBaseDao {

	/**
	 * 获取矩阵数据（分页）
	 * 
	 * @param matrix
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param filter    数据筛选，{'列ID':['abc','def'],'列ID':'123'}
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPage(SysOrgMatrix matrix, String fdVersion,
			int pageno, int rowsize, String filter) throws Exception;

	/**
	 * 获取矩阵数据（新增方法，用于查询关联类别的矩阵信息）
	 * 
	 * @param matrix
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param fdDataCateId 数据类别id
	 * @param filter       数据筛选，{'列ID':['abc','def'],'列ID':'123'}
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPageByType(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize,
			String fdDataCateId, String filter) throws Exception;

	/**
	 * 获取矩阵数据（分页）,数据初始化导出专用
	 *
	 * @param matrix
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param filter    数据筛选，{'列ID':['abc','def'],'列ID':'123'}
	 * @param map 记录查询字段列名与顺序
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPageToExport(SysOrgMatrix matrix, String fdVersion,
									   int pageno, int rowsize, String filter, Map<Integer,String> columnMap) throws Exception;

	/**
	 * 获取矩阵数据（新增方法，用于查询关联类别的矩阵信息）,数据初始化导出专用
	 *
	 * @param matrix
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param fdDataCateId 数据类别id
	 * @param filter       数据筛选，{'列ID':['abc','def'],'列ID':'123'}
	 * @param map 记录查询字段列名与顺序
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPageByTypeToExport(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize,
									 String fdDataCateId, String filter, Map<Integer,String> columnMap) throws Exception;

}
