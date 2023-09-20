package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;

/**
 * @author wangwh
 * @description:物料单位业务类
 * @date 2021/5/7
 */
public interface IEopBasedataMateUnitService extends IExtendDataService {

    /**
     * 通过单位名称查询物料单位
     * @param fdName
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataMateUnit> findByName(String fdName) throws Exception;
}
