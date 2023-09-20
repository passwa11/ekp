package com.landray.kmss.sys.praise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigForm;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigMainForm;

import net.sf.json.JSONObject;

public interface ISysPraiseInfoConfigService extends IBaseService{

	List<SysPraiseInfoConfigForm> updateFindConfigList() throws Exception;

	List<JSONObject> getShowModule() throws Exception;

	List<JSONObject> updateCache(String moduleInfo);
	
	String getUsefulLink() throws Exception;

	void updateConfig(SysPraiseInfoConfigMainForm sysPraiseInfoConfigMainForm, HttpServletRequest request) throws Exception;

	void updateToCluster(IBaseModel baseModel) throws Exception;
}
