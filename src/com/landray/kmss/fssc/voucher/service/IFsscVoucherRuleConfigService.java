package com.landray.kmss.fssc.voucher.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;

public interface IFsscVoucherRuleConfigService extends IExtendDataService {

    public abstract List<FsscVoucherRuleConfig> findByFdVoucherModelConfig(FsscVoucherModelConfig fdVoucherModelConfig) throws Exception;

    public abstract List<FsscVoucherRuleConfig> findByFdVoucherType(EopBasedataVoucherType fdVoucherType) throws Exception;

    public abstract List<FsscVoucherRuleConfig> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

    public abstract List<FsscVoucherRuleConfig> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception;

    /**
     * 初始化
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public String updateInit(String fileName) throws Exception;

    /**
     * 获取凭证规则
     * @param fsscVoucherModelConfig
     * @param fdCategoryId
     * @return
     * @throws Exception
     */
    public List<FsscVoucherRuleConfig> getFsscVoucherRuleConfig(FsscVoucherModelConfig fsscVoucherModelConfig, String fdCategoryId) throws Exception;

    /**
     * 复制
     * @param request
     * @param response
     * @throws Exception 
     */
	public  void updateCopyDoc(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
