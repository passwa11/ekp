package com.landray.kmss.sys.attachment.dao;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.IBaseCoreDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;

/**
 * 创建日期 2006-九月-04
 * 
 * @author 叶中奇 附件数据访问接口
 */
public interface ISysAttMainCoreInnerDao extends IBaseCoreDao {
	public List findByModelKey(String modelName, String modelId, String key)
			throws Exception;

	public List findModelKeys(String modelName, String modelId)
			throws Exception;
	
	public List findAttListByModel(String modelName,String modelId) throws Exception;

	public List findAttData(Date begin, Date end) throws Exception;

	public List findModelsByIds(String[] fdId) throws Exception;

	public List getCorePropsModels(IBaseModel mainModel, String key)
			throws Exception;

	public int clearAttachment(IBaseModel model, String fdKey) throws Exception;

	public SysAttRtfData findRtfDataByPrimaryKey(String fdId) throws Exception;

}
