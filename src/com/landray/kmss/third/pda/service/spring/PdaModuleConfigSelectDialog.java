package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleLabelList;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.StringUtil;

public class PdaModuleConfigSelectDialog implements IXMLDataBean {

	private IPdaModuleConfigMainService pdaModuleConfigMainService;

	public void setPdaModuleConfigMainService(
			IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}
	
	@Override
    @SuppressWarnings({ "unchecked"})
	public List getDataList(RequestContext requestInfo) throws Exception {
		HttpServletRequest request = requestInfo.getRequest();
		String fdType = request.getParameter("fdType");
		Integer type = StringUtil.isNotNull(fdType) ? Integer.parseInt(fdType): 1;
		List dataList = new ArrayList();
		switch (type) {
		case 1: //获取模块数据的选择框
			List<PdaModuleConfigMain> modulesList = getModulesList();
			if(modulesList!=null && modulesList.size()>0){
				for(PdaModuleConfigMain pdaModuleConfigMain : modulesList){
					String fdName = pdaModuleConfigMain.getFdName();
					String fdId = pdaModuleConfigMain.getFdId();
					String fdUrlPrefix = pdaModuleConfigMain.getFdUrlPrefix();
					Map node = new HashMap();
					node.put("name", fdName);
					node.put("id", fdId);
					node.put("fdUrlPrefix", fdUrlPrefix);
					dataList.add(node);
				}
			}
			break;
		case 2: //获取当前模块的所有标签列表数据的选择框
			String fdModuleId = request.getParameter("fdId");
			List<PdaModuleLabelList> currentModuleLabelList = getCurrentModuleLabelList(fdModuleId);
			if(currentModuleLabelList!=null && currentModuleLabelList.size()>0){
				for(PdaModuleLabelList pdaModuleLabelList:currentModuleLabelList){
					String fdName = pdaModuleLabelList.getFdName();
					String fdId = pdaModuleLabelList.getFdId();
					String fdDataUrl = pdaModuleLabelList.getFdDataUrl();
					Map node = new HashMap();
					node.put("name", fdName);
					node.put("id", fdId);
					node.put("fdDataUrl", fdDataUrl);
					dataList.add(node);
				}
			}
			break;
		default:
			break;
		}
		return dataList;
	}
	
	//获取当前模块的所有标签列表
	@SuppressWarnings({ "unchecked"})
	private List<PdaModuleLabelList> getCurrentModuleLabelList(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus=:fdStatus and pdaModuleConfigMain.fdId=:fdId");
		hqlInfo.setParameter("fdStatus",PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdId",fdId);
		PdaModuleConfigMain currentPdaModuleConfigMain = (PdaModuleConfigMain) pdaModuleConfigMainService.findFirstOne(hqlInfo);
		List<PdaModuleLabelList> pdaModuleLabelList = (currentPdaModuleConfigMain!=null)?currentPdaModuleConfigMain.getFdLabelList():null;
		return pdaModuleLabelList;
	}

	//获取所有启用的模块列表
	@SuppressWarnings("unchecked")
	private List<PdaModuleConfigMain> getModulesList() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus=:fdStatus and pdaModuleConfigMain.fdSubMenuType!=:fdSubType");
		hqlInfo.setParameter("fdStatus",PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdSubType",PdaModuleConfigConstant.PDA_MENUS_MODULE);
		hqlInfo.setOrderBy(" pdaModuleConfigMain.fdOrder asc");
		List<PdaModuleConfigMain> modulesList = pdaModuleConfigMainService.findValue(hqlInfo);
		return modulesList;
	}

}
