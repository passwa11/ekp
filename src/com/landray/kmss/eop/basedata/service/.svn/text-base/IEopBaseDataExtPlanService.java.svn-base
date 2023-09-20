package com.landray.kmss.eop.basedata.service;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.eop.basedata.vo.EopBaseDataCollectionOrPaymentVo;
import com.landray.kmss.eop.basedata.vo.EopBaseDataReceiptOrInvoicingVo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;
import java.util.Map;

/**
 * 费控对接相关接口
 */
public interface IEopBaseDataExtPlanService extends IExtendDataService {

    /**
     * 查询结算计划结算金额，收付款金额
     * @param number 结算金额
     * @param period 收付款金额
     * @return
     * @throws Exception
     */
    String getPlanMoney(String number, String period)throws Exception;

    /**
     * 回写开/收票数据
     * @throws Exception
     */
    String saveReceiptOrInvoicing(EopBaseDataReceiptOrInvoicingVo receiptOrInvoicingVo)throws Exception;

    /**
     * 回写收/付款数据
     * @throws Exception
     */
    String saveCollectionOrPayment(EopBaseDataCollectionOrPaymentVo collectionOrPaymentVo)throws Exception;


    /**
     * 查询合同相关信息(返回弹窗页面)
     * @param docNumber 合同编号
     */
    List<Map<String, Object>> queryApplyInfo(String docNumber, String fdSttType);

    /**
     * 查询合同相关信息(返回json)
     * @param jsonObject
     */
    String  queryApplyInfoRsJson(JSONObject jsonObject);
}
