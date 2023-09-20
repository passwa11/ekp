package com.landray.kmss.fssc.voucher.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscVoucherMainService extends IExtendDataService {

    public abstract List<FsscVoucherMain> findByFdBaseCurrency(EopBasedataCurrency fdBaseCurrency) throws Exception;

    public abstract List<FsscVoucherMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public List<FsscVoucherMain> findByDeleteWhere(String fdVoucherCreateType, String fdModelId, String fdModelName, Date fdVoucherDate) throws Exception;

    /**
     * 生成凭证
     * @param model
     * @throws Exception
     */
    public void addOrUpdateVoucher(IBaseModel model) throws Exception;

    /**
     * 查询未记账的凭证
     * @param fdModelId
     * @param fdModelName
     * @param fdPushType 推送方式
     * @param fdIsAmortize 是否是摊销 1是 0不是 否则不添加此条件
     * @return
     * @throws Exception
     */
    public List<FsscVoucherMain> findNoBookkeeping(String fdModelId, String fdModelName, String fdPushType, String fdIsAmortize) throws Exception;

    /**
     * 查询未记账的满足期间的凭证
     * @param date 期间
     * @param fdPushType 推送方式
     * @return
     * @throws Exception
     */
    public List<FsscVoucherMain> findNoBookkeeping(Date date, String fdPushType) throws Exception;

    /**
     * 记账
     * @param fsscVoucherMainList
     *
     *
     * @return Map<String, String>{
     *              message：提示
     *              countInt：总记账数
     *              successInt：记账成功数
     *              successCodes：成功凭证编号
     *              failureInt：记账失败数
     *              failureCodes：失败凭证编号
     * }
     * @throws Exception
     */
    public Map<String, String> updateBookkeeping(List<FsscVoucherMain> fsscVoucherMainList) throws Exception;

    /**
     *  给U8推送凭证
     * @param fsscVoucherMain
     * @throws Exception
     */
    public void updateBookkeepingU8(FsscVoucherMain fsscVoucherMain) throws Exception;

    /**
     *  给Eas推送凭证
     * @param fsscVoucherMain
     * @throws Exception
     */
    public void updateBookkeepingEas(FsscVoucherMain fsscVoucherMain) throws Exception;
    
    /**
     *  给K3推送凭证
     * @param fsscVoucherMain
     * @throws Exception
     */
    public void updateBookkeepingK3(FsscVoucherMain fsscVoucherMain) throws Exception;

    /**
     * 查询凭证
     * @param fdModelId
     * @param fdModelName
     * @return
     * @throws Exception
     */
    public List<FsscVoucherMain> getFsscVoucherMain(String fdModelId, String fdModelName) throws Exception;

    /**
     *	获取凭证信息
     */
    public List<Map<String, Object>> getVoucherInfo(String fdModelId, String fdModelName) throws Exception;


    /**
     * 【费控系统】凭证中心--摊销凭证自动记账定时任务
     * @param date
     * @return
     * @throws Exception
     */
    public String updateAutoBookkeeping(Date date) throws Exception;

    /**
     * 批量记账
     * @param fdVoucherMainIds
     * @return
     * @throws Exception 
     */
	public  Map<String, String> updateBatchBookkeeping(String fdVoucherMainIds) throws Exception;

    
}
