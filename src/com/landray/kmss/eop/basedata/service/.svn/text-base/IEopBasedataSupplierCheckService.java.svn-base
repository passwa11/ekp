package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;

/**
 * @author wangwh
 * @description:验证供应商是否被采购业务使用业务类
 * @date 2021/5/7
 */
public interface IEopBasedataSupplierCheckService {

    /**
     * 验证供应商是否可以被删除
     * 如果被采购供应商使用了，则不允许删除
     * true可删，false不可删
     * @param eopBasedataSupplier
     * @return
     * @throws Exception
     */
    public boolean checkSupplierCanDelete(EopBasedataSupplier eopBasedataSupplier) throws Exception;

}
