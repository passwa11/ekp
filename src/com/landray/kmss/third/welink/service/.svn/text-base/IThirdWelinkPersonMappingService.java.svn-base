package com.landray.kmss.third.welink.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;

public interface IThirdWelinkPersonMappingService extends IExtendDataService {

	public ThirdWelinkPersonMapping findByEkpId(String ekpId) throws Exception;

	public ThirdWelinkPersonMapping findByUserId(String userId)
			throws Exception;

	public void addMapping(SysOrgPerson person) throws Exception;

	public void addMapping(SysOrgPerson person, String userId) throws Exception;

	public List<String> getCorpUseridsWithoutUserId() throws Exception;

	public void addMapping(SysQuartzJobContext jobContext) throws Exception;
}
