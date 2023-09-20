package com.landray.kmss.fssc.fee.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public interface IFsscFeeMainService extends IExtendDataService {

    public abstract List<FsscFeeMain> findByDocTemplate(FsscFeeTemplate docTemplate) throws Exception;
    /**
     * 获取事前台账
     * @param fdFeeId 事前申请ID
     * @param fdType 台账类型，如不传则查询所有类型
     * @return
     * @throws Exception
     */
	public abstract List<FsscFeeLedger> getLedgerData(String fdFeeId, String  fdType)throws Exception;
	/**
	 * 查询是否可以关闭事前
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public abstract Boolean checkCanCloseFee(String fdId)throws Exception;
	/**
	 * 关闭事前
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public abstract void updateCloseFee(String fdId)throws Exception;
	/**
	 * 查询事前申请的总额
	 * @param fdId
	 * @return 
	 * @throws Exception
	 */
	public abstract Double getTotalMoney(String fdId)throws Exception;
	/**
	 * 
	 * Description:事前申请porlet：获取当前登录人事前列表，porlet展现  
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract Page listPortlet(HttpServletRequest request)throws Exception;
	
	/**
	 * 获取查看页面附件
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public abstract List<Map<String,String>> getAttData(HttpServletRequest request) throws Exception;
	/**
	 * 获取对应的费用类型是否必须要有预算
	* Description:  
	* @author xiexingxing
	* @date 2020年4月8日
	 */
	public abstract Boolean getIsNeedBudgetByItem(JSONObject row) throws Exception;
}
