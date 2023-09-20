package com.landray.kmss.sys.attend.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendSynDingBak;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.sunbor.web.tag.Page;

public interface ISysAttendSynDingBakService extends IExtendDataService {
	public List<String> getRemainYears() throws Exception;

	public Page findPage(SQLInfo sqlInfo) throws Exception;

	public SysAttendSynDingBak findByPrimaryKey(String id,
			Map<String, Object> params) throws Exception;

	public boolean isExist(SQLInfo sqlInfo) throws Exception;
}
