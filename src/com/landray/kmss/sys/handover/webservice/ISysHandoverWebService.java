package com.landray.kmss.sys.handover.webservice;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import javax.jws.WebService;

@WebService
public interface ISysHandoverWebService extends ISysWebservice {

    /**
     * 工作交接
     * @param req
     * @return
     * @throws Exception
     */
    public SysHandoverResp addHandover(SysHandoverReq req) throws Exception;

}
