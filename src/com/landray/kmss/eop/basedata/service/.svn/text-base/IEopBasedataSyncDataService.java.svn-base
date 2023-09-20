package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;

/**
 * @author chenzk
 * @description:eop基础数据模块与业务模块数据同步
 * @date 2021/11/17
 */
public interface IEopBasedataSyncDataService {

    /**
     * 供应商信息同步到业务模块
     * @param eopBasedataSupplier
     * @return
     * @throws Exception
     */
    public boolean updateEopSupplierDataToBusi(EopBasedataSupplier eopBasedataSupplier) throws Exception;

    /**
     * 客户信息同步到业务模块
     * @param eopBasedataCustomer
     * @return
     * @throws Exception
     */
    public boolean updateEopCustomerDataToBusi(EopBasedataCustomer eopBasedataCustomer) throws Exception;

}
