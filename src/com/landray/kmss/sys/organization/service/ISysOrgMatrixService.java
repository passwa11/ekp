package com.landray.kmss.sys.organization.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.sunbor.web.tag.Page;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * 组织矩阵
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public interface ISysOrgMatrixService extends IBaseService {

	/**
	 * 批量置为无效
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception;

	/**
	 * 置为无效
	 * 
	 * @param id
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String id, RequestContext requestContext) throws Exception;

	/**
	 * 获取矩阵数据（分页）
	 * 
	 * @param fdMatrixId
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param filter     数据筛选
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPage(String fdMatrixId, String fdVersion, int pageno, int rowsize, String filter)
			throws Exception;

	/**
	 * 下载模板文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public Workbook downloadTemplate(String fdId) throws Exception;

	/**
	 * 导入矩阵数据
	 * 
	 * @param matrixForm
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveMatrixData(SysOrgMatrixForm matrixForm) throws Exception;
	
	/**
	 * 保存矩阵数据（导入失败时，重新在线修改后批量导入）
	 * 
	 * @param fdMatrixId
	 * @param json
	 *           {"V1": [{'id':'value', 'id':'value'}, {'fdId':'value', 'id':'value', 'id':'value'}]}
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveMatrixData(String fdMatrixId, JSONObject json)throws Exception;

	/**
	 * 导出矩阵数据
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public Workbook exportMatrixData(String fdId) throws Exception;
	
	/**
	 * 下载导入失败的数据
	 * 
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public Workbook exportErrorMatrixData(String fdId, JSONObject datas) throws Exception;

	/**
	 * 根据分类查询矩阵
	 * 
	 * @param cateId
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgMatrix> findByCate(String cateId) throws Exception;

	/**
	 * 矩阵计算
	 * 
	 * @param json {'id': '矩阵ID', 'results': '结果1ID;结果2ID', 'conditionals': [{'id': '条件1ID', 'value': '条件值'},{'id': '条件2ID', 'value': '条件值'}]}
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgElement> matrixCalculation(JSONObject json) throws Exception;

	/**
	 * 矩阵计算（旧接口，JSON类型不一样）
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<SysOrgElement> matrixCalculation(net.sf.json.JSONObject json) throws Exception;

	/**
	 * 矩阵计算
	 * 按结果进行分组：
	 * 结果1：
	 * 人员1，人员2，人员3
	 * 结果2：
	 * 人员1，人员4
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public List<List<SysOrgElement>> matrixCalculationByGroup(JSONObject json) throws Exception;

	/**
	 * 矩阵计算（旧接口，JSON类型不一样）
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<List<SysOrgElement>> matrixCalculationByGroup(net.sf.json.JSONObject json) throws Exception;

	/**
	 * 查询矩阵数据某一列是否有数据
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public int countByColumn(String tableName, String fieldName) throws Exception;

	/**
	 * 查询矩阵中所有列表的记录数
	 * 
	 * @param fdMatrixId
	 * @return
	 * @throws Exception
	 */
	public JSONObject countByColumns(String fdMatrixId) throws Exception;

	/**
	 * 获取某一条矩阵数据
	 * 
	 * @param fdMatrixId
	 * @param dataId
	 * @return
	 * @throws Exception
	 */
	public SysOrgMatrix getMatrixData(String fdMatrixId, String dataId) throws Exception;
	
	/**
	 * 删除某一条矩阵数据
	 * 
	 * @param fdMatrixId
	 * @param dataId
	 * @throws Exception
	 */
	public void deleteMatrixData(String fdMatrixId, String dataId) throws Exception;
	
	/**
	 * 批量删除矩阵数据
	 * 
	 * @param fdMatrixId
	 * @param dataIds
	 * @throws Exception
	 */
	public void deleteMatrixDatas(String fdMatrixId, String[] dataIds) throws Exception;
	
	/**
	 * 新增一条矩阵数据
	 * 
	 * @param fdMatrixId
	 * @param fdVersion
	 * @param data
	 *            {'id':'value', 'id':'value'}
	 * @throws Exception
	 */
	public void addMatrixData(String fdMatrixId, String fdVersion, JSONObject data) throws Exception;
	
	/**
	 * 更新一条矩阵数据
	 * 
	 * @param fdMatrixId
	 * @param dataId
	 * @param data
	 *            {'id':'value', 'id':'value'}
	 * @throws Exception
	 */
	public void updateMatrixData(String fdMatrixId, String dataId, JSONObject data) throws Exception;

	/**
	 * 批量新增或更新数据
	 * 
	 * @param fdMatrixId
	 * @param cateId
	 * @param json       {"V1": [{'id':'value', 'id':'value'}, {'fdId':'value',
	 *                   'id':'value', 'id':'value'}]}
	 * @throws Exception
	 */
	public void saveAllMatrixData(String fdMatrixId, String cateId, JSONObject json) throws Exception;
	
	/**
	 * 删除矩阵版本
	 * 
	 * @param fdMatrixId
	 * @param fdVersion
	 * @throws Exception
	 */
	public void deleteVersion(String fdMatrixId, String fdVersion) throws Exception;
	
	/**
	 * 获取所有版本信息
	 * 
	 * @param fdMatrixId
	 * @return
	 * @throws Exception
	 */
	public JSONArray getVersions(String fdMatrixId) throws Exception;

	/**
	 * 初始化系统配置矩阵数据
	 * @throws Exception
	 */
	public void saveInitMatrixData() throws Exception;

	/**
	 * 批量替换矩阵数据
	 * 
	 * @param fdId
	 * @param version
	 * @param json
	 * @throws Exception
	 */
	public void batchReplace(String fdId, String version, JSONObject json) throws Exception;

	/**
	 * 获取矩阵数据（分页）(关联数据类别)
	 * 
	 * @param fdMatrixId
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param fdDataCateId 分组ID
	 * @param filter       数据筛选
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPageByType(String fdMatrixId, String fdVersion, int pageno, int rowsize, String fdDataCateId,
			String filter) throws Exception;

	/**
	 * 查询分组数量
	 * 
	 * @param fdMatrixId
	 * @param fdDataCateId
	 * @return
	 * @throws Exception
	 */
	public long countByType(String fdMatrixId, String fdDataCateId) throws Exception;

	/**
	 * 批量保存矩阵数据（按分组保存）
	 * 
	 * @param matrixId
	 * @param version
	 * @param cateId
	 * @param matrixDatas
	 * @throws Exception
	 */
	public void saveMatrixDataByCate(String matrixId, String version, String cateId, JSONArray matrixDatas)
			throws Exception;

	/**
	 * 更新矩阵数据分组（用于从禁用到开启的数据迁移）
	 * 
	 * @param matrixId
	 * @throws Exception
	 */
	public void updateDefDataCate(String matrixId) throws Exception;

	/**
	 * 数据筛选
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject filterData(RequestContext request) throws Exception;

	/**
	 * 复制版本
	 * 
	 * @param fdMatrixId
	 * @param version    原版本号
	 * @throws Exception
	 */
	public SysOrgMatrixVersion copyMatrixData(String fdMatrixId, String version) throws Exception;

	/**
	 * 获取矩阵数据（分页）,数据初始化专用
	 *
	 * @param fdMatrixId
	 * @param fdVersion
	 * @param pageno
	 * @param rowsize
	 * @param filter     数据筛选
	 * @param columnMap 记录查询字段列名与顺序
	 * @return
	 * @throws Exception
	 */
	public Page findMatrixPageToExport(String fdMatrixId, String fdVersion, int pageno, int rowsize, String filter, Map<Integer,String> columnMap)
			throws Exception;

}
