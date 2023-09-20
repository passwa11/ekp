package com.landray.kmss.eop.basedata.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.web.upload.FormFile;

public interface IEopBasedataBusinessService extends IExtendDataService{
	/**
     * 启用档案
     * @param ids
     * @throws Exception
     */
	public abstract void saveEnable(String ids,String modelName) throws Exception;
	/**
     * 禁用档案
     * @param ids
     * @throws Exception
     */
	public abstract void saveDisable(String ids,String modelName) throws Exception;
	/**
	 * 复制档案
	 * @param ids
	 * @param modelName
	 * @param fdCompanyIds
	 * @throws Exception
	 */
	public abstract void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception;
	/**
	 * 导入档案
	 * @param response
	 * @param fdCompanyId
	 * @param fdFile
	 * @return 
	 */
	public abstract List<EopBasedataImportMessage> saveImport(FormFile fdFile,String modelName) throws Exception;
	
	/**
	 * 导入数据校验前对数据进行的预先处理，如果有特殊逻辑的请在自己的service中重写此方法
	 * @param modelName
	 * @param dataList
	 * @throws Exception
	 */
	public abstract void beforeImportValidate(String modelName,List<List<Object>> dataList,EopBasedataImportContext context) throws Exception;
	/**
	 * 导入数据校验通过后，保存数据前对数据进行的业务操作，如果有特殊业务的请在自己的service中重写此方法
	 * @param modelList
	 * @throws Exception
	 */
	public abstract void  afterImportValidate(String modelName,List<IBaseModel> addList,List<IBaseModel> updateList, List<List<Object>> dataList) throws Exception;
	/**
	 * 下载模板
	 * @param response
	 * @param modelName
	 * @throws Exception
	 */
	public abstract void downloadTemplate(HttpServletResponse response, String modelName) throws Exception;
	/**
	 * 导出
	 * @param response
	 * @param modelName
	 * @throws Exception
	 */
	public abstract void exportData(HttpServletResponse response, String modelName,String fdCompanyId) throws Exception;
	
	/**
	 * 启用档案验证是否已经存在重复的编码
	 * @param ids
	 * @param modelName
	 * @return
	 */
	public abstract List<String> checkEnable(String ids, String modelName) throws Exception;
	/**
	 * 导入明细
	 * @param fdFile
	 * @param modelName
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String, Object> detailImport(FormFile fdFile, String modelName, String properties,String fdCompanyId,String docTemplateId) throws Exception;
	/**
	 * 下载明细模板
	 * @param response
	 * @param modelName
	 * @param properties
	 * @throws Exception
	 */
	public abstract void downloadDetailTemplate(HttpServletResponse response, String modelName, String properties)throws Exception;
	
	
}
