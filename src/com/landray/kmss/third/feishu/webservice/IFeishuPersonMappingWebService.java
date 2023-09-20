package com.landray.kmss.third.feishu.webservice;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
import org.springframework.web.bind.annotation.RequestBody;

import javax.jws.WebService;

@WebService
public interface IFeishuPersonMappingWebService extends ISysWebservice {

    /**
     * 根据ekp组织架构ids查询映射用户信息
     * @param sysOrgIds
     * @return
     */
    public JSONArray getBySysOrgIds(@RequestBody String[] sysOrgIds) throws Exception;
}
