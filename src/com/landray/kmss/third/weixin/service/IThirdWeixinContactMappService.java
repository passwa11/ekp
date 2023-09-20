package com.landray.kmss.third.weixin.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;

import java.util.List;

public interface IThirdWeixinContactMappService extends IExtendDataService {

    public List<ThirdWeixinContactMapp> findByContactId(String contactId) throws Exception;

    public ThirdWeixinContactMapp findByContactAndOrgType(String contactId, String orgTypeId) throws Exception;

    Long getMappRecordCount() throws Exception;

    List<ThirdWeixinContactMapp> findContactMapp(String orgTypeId,String tagId) throws Exception;


}
