package com.landray.kmss.third.pda.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.pda.loader.PdaConfig;
import com.landray.kmss.third.pda.loader.PdaSysConfig;
import com.landray.kmss.third.pda.loader.PdaSysConfigs;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import java.util.*;

/**
 * 获取系统内部提供的PDA窗口数据
 * 
 * @author shouyu
 * 
 */
public class PdaSysConfigDialog implements IXMLDataBean {
	private IPdaModuleConfigMainService pdaModuleConfigMainService;

	public IPdaModuleConfigMainService getPdaModuleConfigMainService() {
		return pdaModuleConfigMainService;
	}

	public void setPdaModuleConfigMainService(
			IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String moduleId = requestInfo.getParameter("moduleId");
		String urlPrefix = requestInfo.getParameter("urlPrefix");
		String tmpType = requestInfo.getParameter("type");
		tmpType = StringUtil.isNull(tmpType) ? "list;pic" : tmpType;
		List typeList = ArrayUtil.convertArrayToList(tmpType.split(";"));
		List retList = null;
		if (StringUtil.isNull(urlPrefix)) {
			if (StringUtil.isNotNull(moduleId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("pdaModuleConfigMain.fdStatus='1' "
						+ "and pdaModuleConfigMain.fdId=:moduleId");
				hqlInfo.setParameter("moduleId", moduleId);
				PdaModuleConfigMain configMain = (PdaModuleConfigMain) pdaModuleConfigMainService.findFirstOne(hqlInfo);
				if (configMain != null) {
					urlPrefix = configMain.getFdUrlPrefix();
				}
			}
		}
		if (StringUtil.isNotNull(urlPrefix)) {
			retList = new ArrayList();
			PdaSysConfigs pdaSysConfig = PdaSysConfigs.getInstance();
			PdaSysConfig module = pdaSysConfig.getModuleCfg(urlPrefix);
			if (module != null) {
				List<PdaConfig> list = module.getConfigs();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					PdaConfig pdaCfg = (PdaConfig) iterator.next();
					if (typeList.contains(pdaCfg.getType())) {
						Map<String, String> dataMap = new HashMap<String, String>();
						dataMap.put("id", IDGenerator.generateID());
						dataMap.put("name", ResourceUtil.getString(pdaCfg
								.getMessageKey()));
						dataMap.put("type", pdaCfg.getType());
						dataMap.put("tmpClass", pdaCfg.getTemplateClass());
						dataMap.put("url", pdaCfg.getDataUrl());
						dataMap.put("countUrl", pdaCfg.getCountUrl());
						retList.add(dataMap);
					}
				}
			}
		}
		return retList;
	}
}
