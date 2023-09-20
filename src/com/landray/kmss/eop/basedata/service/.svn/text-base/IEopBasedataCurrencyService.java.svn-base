package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;

/**
 * @author wangwh
 * @description:货币业务类
 * @date 2021/5/7
 */
public interface IEopBasedataCurrencyService extends IExtendDataService {

    /**
     * 根据中文名称查找货币
     * @param fdName
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataCurrency> findByName(String fdName) throws Exception;

    /**
     * 根据货币简称查找货币
     * @param fdAbbreviation
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataCurrency> findByAbbreviation(String fdAbbreviation) throws Exception;
}
