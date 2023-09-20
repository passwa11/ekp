package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;

/**
 * @author chenzk
 * @description:验证客户是否被业务模块关联的接口
 * @date 2021/11/17
 */
public interface IEopBasedataCustomerCheckService {

    /**
     * 验证客户信息是否可以被删除
     * true可删，false不可删
     * @param eopBasedataCustomer
     * @return
     * @throws Exception
     */
    public boolean checkCustomerCanDelete(EopBasedataCustomer eopBasedataCustomer) throws Exception;

}
