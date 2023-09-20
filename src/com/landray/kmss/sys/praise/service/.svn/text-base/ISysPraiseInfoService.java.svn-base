package com.landray.kmss.sys.praise.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.praise.model.SysPraiseInfo;

public interface ISysPraiseInfoService extends IExtendDataService {

	List<String> getExtendTypes();

	Integer getPraiseNum(HttpServletRequest request) throws Exception;

	Integer getSumRich(Date fdStart, String userId, String fdType) throws Exception;

	List<SysPraiseInfo> getInfoByParams(Date lastTime, Date nowTime, boolean withoutSelf) throws Exception;

	Date getLastExecuteTime() throws Exception;

	void saveReply(HttpServletRequest request) throws Exception;

	void addCheckPraiseNotify(SysPraiseInfo sysPraiseInfo) throws Exception;
}
