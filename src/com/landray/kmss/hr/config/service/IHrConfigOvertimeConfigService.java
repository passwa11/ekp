package com.landray.kmss.hr.config.service;

import java.util.Date;

import com.landray.kmss.hr.config.model.HrConfigOvertimeConfig;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

/**
  * 加班规则配置 服务接口
  */
public interface IHrConfigOvertimeConfigService extends IExtendDataService {
	
	/**
	 * 
	 * @param fdPersonId 特殊人员id
	 * @param fdRank 职级
	 * @param fdOvertimeType 加班类型，工作日，周末，节假日
	 * @return
	 */
	public HrConfigOvertimeConfig getDataByParams(String fdPersonId,String fdRankName,String fdOvertimeType) throws Exception;
	
	public JSONObject getOvertimeType(String fdPersonId,String fdStartTime) throws Exception;

	public JSONObject canSubmit(String fdPersonId,String fdStartTime,String fdEndTime) throws Exception;
	
	public JSONObject canSubmitBatch(Date startDate) throws Exception;

	
	public JSONObject getValueByRank(String fdRankValue,String fdTemplateId,String first,String url) throws Exception;

	public JSONObject checkInvoice(String fdInvoice) throws Exception;

}
