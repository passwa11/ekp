package com.landray.kmss.third.feishu.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;

import net.sf.json.JSONObject;

public interface IThirdFeishuPersonMappingService extends IExtendDataService {

	public ThirdFeishuPersonMapping findByEkpId(String ekpId) throws Exception;

	public ThirdFeishuPersonMapping findByOpenId(String openId)
			throws Exception;

	public ThirdFeishuPersonMapping findByEmployeeId(String employeeId)
			throws Exception;

	public void addMapping(SysOrgPerson person) throws Exception;

	public void addMapping(SysOrgPerson person, String openId) throws Exception;

	public void updatePerson(JSONObject json) throws Exception;

	public void updatePersonMapping(SysQuartzJobContext context);


}
