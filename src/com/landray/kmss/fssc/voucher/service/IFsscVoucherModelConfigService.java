package com.landray.kmss.fssc.voucher.service;

import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscVoucherModelConfigService extends IExtendDataService {

    /**
     * 初始化
     * @return
     * @throws Exception
     */
    public String updateInit() throws Exception;

    /**
     * 根据fdModelName获取凭证规则模块配置
     * @param fdModelName
     * @return
     * @throws Exception
     */
    public FsscVoucherModelConfig getFsscVoucherModelConfig(String fdModelName) throws Exception;
}
