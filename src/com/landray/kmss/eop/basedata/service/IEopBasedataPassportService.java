package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataPassport;

public interface IEopBasedataPassportService extends IEopBasedataBusinessService {

    /**
     * 根据员工id获取员工护照
     * @param fdPersonId
     * @return
     * @throws Exception
     */
    public EopBasedataPassport getEopBasedataPassport(String fdPersonId) throws Exception;
}
