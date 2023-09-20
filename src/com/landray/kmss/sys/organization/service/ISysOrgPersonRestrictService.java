package com.landray.kmss.sys.organization.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.service.IBaseService;

public interface ISysOrgPersonRestrictService  extends IBaseService {
	
	public void refreshPersonRestrict();
	
	public void addRestrict(String personId);
	
	public boolean isRestrict(String personId);

	public List<Map<String, String>> getAllData();

}
