package com.landray.kmss.third.pda.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public interface IPdaDataShowService {

	public List getPdaModuleList() throws Exception;

	public List getPdaModuleListByCate() throws Exception;

	public Map<String, String> getSupportPdaCfgMap() throws Exception;

	public void setViewLabelInfo(HttpServletRequest request) throws Exception;

	/**
	 * 获取移动组件模块前缀
	 * 
	 * @return
	 * @throws Exception
	 */
	public Set<String> getSupportPdaModulesPrefix() throws Exception;
}
